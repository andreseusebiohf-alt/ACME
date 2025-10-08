package GT.p1e2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

public class juegoMemorama extends AppCompatActivity {

    //Declaracion de variables y componentes
    private GridLayout contenedor;
    private TextView titulo, contador;
    //arreglo que almacena las posiciones de cada imagen: 0 a 7
    private int[] imagenes = {
            R.drawable.m0, R.drawable.m1, R.drawable.m2, R.drawable.m3,
            R.drawable.m4, R.drawable.m5, R.drawable.m6, R.drawable.m7
    };
    private ArrayList<Integer> cartas = new ArrayList<>();
    private ImageView primeraCarta, segundaCarta;
    private int primerIndice = -1;
    private boolean bloqueado = false;
    private int parejasEncontradas = 0;
    private Handler retraso = new Handler();
    private Runnable volteoAutomatico;// Hilo asincrono
    private MediaPlayer acierto, error, victoria;
    int contadorErrores=0;
    TextView textoErrores;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_juego_memorama);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Referencia a los componentes

        textoErrores=findViewById(R.id.textoerrores);
        contenedor = findViewById(R.id.gridLayout);
        contenedor.setColumnCount(4);

        titulo = findViewById(R.id.tMemorama);
        contador = findViewById(R.id.tConta);

        ViewGroup.LayoutParams params1=contenedor.getLayoutParams();
        params1.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        params1.height= (int) (Resources.getSystem().getDisplayMetrics().widthPixels*1.5);
        contenedor.setLayoutParams(params1);

        //llamada a los metodos
        inicio();
    }
    //Metodo que inicia el juego
    private void inicio(){
        cargarSonidos();
        prepararCartas();
        mostrarCartas();
        contador();
    }
    //Metodo que se encarga de cargar los sonidos para los eventos de las cartas
    private void cargarSonidos() {
        acierto = MediaPlayer.create(this, R.raw.correct);
        error = MediaPlayer.create(this, R.raw.error);
        victoria = MediaPlayer.create(this, R.raw.victory);
    }

    //Metodo que se encarga de añadir cada carta con su par al contenedor y las mezcla automaticamente
    private void prepararCartas() {
        for (int i = 0; i < 2; i++) {
            for (int imagen : imagenes) {
                cartas.add(imagen);
            }
        }
        Collections.shuffle(cartas);
    }

    //Metodo que muestra las cartas en la pantalla con su respectivo diseño y ubicacion
    private void mostrarCartas() {
        contenedor.removeAllViews(); // Limpia el contenedor
        ArrayList<ImageView> imagenesTemporales = new ArrayList<>();

        for (int i = 0; i < cartas.size(); i++) {
            //Diseño y ubicacion de cada carta
            final ImageView img = new ImageView(this);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.rowSpec = GridLayout.spec(i / 4, 1f);
            params.columnSpec = GridLayout.spec(i % 4, 1f);
            params.setMargins(10, 10, 10, 10);
            img.setLayoutParams(params);

            final int index = i;
            img.setEnabled(false); //Desactiva el click en las imagenes
            imagenesTemporales.add(img);

            // Añade la animacion a las cartas cuando se voltean
            retraso.postDelayed(() -> {
                animar(img);
                img.setImageResource(cartas.get(index));
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }, 100 * i);

            img.setOnClickListener(v -> voltearCarta(img, index));
            contenedor.addView(img);
        }

        // Volteo de nuevo a reverso después de 2 segundos
        retraso.postDelayed(() -> {
            for (int i = 0; i < imagenesTemporales.size(); i++) {
                final ImageView img = imagenesTemporales.get(i);
                final int index = i;

                retraso.postDelayed(() -> {
                    animar(img);
                    img.setImageResource(R.drawable.reverso);
                    img.setEnabled(true); // Ahora sí se pueden tocar
                }, 50 * index); // Volteo con efecto cascada opcional
            }
        }, 2800);
    }


    //Metodo que se encarga de voltear las cartas al dar click sobre ellas
    private void voltearCarta(ImageView carta, int index) {
        /* Este if sirve para evitar que se pueda tocar y voltear más de dos cartas al mismo tiempo
        o cartas ya emparejadas.*/
        if (bloqueado || carta.getTag() != null || carta == primeraCarta || segundaCarta != null)
            return;

        animar(carta);
        //Se le asigna al componente carta la imagen en la posicion en la que esta el indice del arreglo imagenes
        carta.setImageResource(cartas.get(index));
        carta.setScaleType(ImageView.ScaleType.FIT_CENTER);

        //if que comprueba el estado de la primera carta en ser volteada
        if (primeraCarta == null) {
            primeraCarta = carta;
            primerIndice = index;

            // Voltea automaticamente si no se selecciona una segunda carta
            volteoAutomatico = () -> {
                //if que compara los valores de las cartas
                if (segundaCarta == null && primeraCarta != null) {
                    error.start();
                    animar(primeraCarta);
                    primeraCarta.setImageResource(R.drawable.reverso);
                    primeraCarta = null;
                    primerIndice = -1;
                }
            };
            //Retraso de 2 segundos para el volteo
            retraso.postDelayed(volteoAutomatico, 2000);

        } else {
            // Si se toca la segunda carta, se anula el volteo automatico
            retraso.removeCallbacks(volteoAutomatico);
            segundaCarta = carta;
            bloqueado = true;

            //Hilo secundario que ejecuta la coincidencia de dos cartas
            retraso.postDelayed(() -> {
                //if que compara los indices de las imagenes
                if (cartas.get(index).equals(cartas.get(primerIndice))) {
                    primeraCarta.setTag("encontrada");
                    segundaCarta.setTag("encontrada");
                    parejasEncontradas++;
                    acierto.start();
                    contador();

                    //if que compara si se encontraron todas las parejas
                    if (parejasEncontradas == 8) {
                        retraso.postDelayed(this::mostrarVictoria, 1500);
                    }
                } else {
                    //si no son iguales las dos cartas, ejecuta las sigientes acciones
                    error.start();
                    animar(primeraCarta);
                    animar(segundaCarta);
                    primeraCarta.setImageResource(R.drawable.reverso);
                    segundaCarta.setImageResource(R.drawable.reverso);
                    //codigo agregado
                    contadorErrores++;
                    textoErrores.setText("Faltas:" + contadorErrores + "/10");
                    if (contadorErrores == 10) {
                        AlertDialog dialog = new AlertDialog.Builder(this)
                                .setTitle("Perdiste")
                                .setMessage("Fallaste 10 veces\nNo ganaste nada :(")
                                .setCancelable(false)
                                .setPositiveButton("Volver a jugar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        recreate();
                                    }
                                })
                                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();

                        // Aplica el fondo redondeado al dialogo
                        if (dialog.getWindow() != null) {
                            dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
                        }
                    }
                }
//Llamada al metodo que reinicia el turno despues de terminar el juego o seleccionar cartas incorrectas
                reiniciarTurno();

                //Llamada al metodo que reinicia el turno despues de terminar el juego o seleccionar cartas incorrectas
                reiniciarTurno();
            }, 1000);
        }
    }

    //Metodo que se encarga de dar una animacion a de giro a las cartas
    private void animar(View view) {
        AnimatorSet animador = new AnimatorSet();

        // Animación de salida (volteando la carta hacia un lado)
        ObjectAnimator salida = ObjectAnimator.ofFloat(view, "rotationY", 0f, 90f);
        salida.setDuration(150);

        // Animación de entrada (volteando la carta hacia el otro lado)
        ObjectAnimator entrada = ObjectAnimator.ofFloat(view, "rotationY", -90f, 0f);
        entrada.setDuration(150);

        // Ejecuta las animaciones en secuencia: primero salida, luego entrada
        animador.playSequentially(salida, entrada);
        animador.start();
    }

    //Metodo que se encarga de reiniciar el juego una vez que se haya ganado
    private void reiniciarTurno() {
        primeraCarta = null;
        segundaCarta = null;
        primerIndice = -1;
        bloqueado = false;
    }

    //Metodo que muestra un cuadro de dialogo de victoria
    private void mostrarVictoria() {
        variables_g g1 = new variables_g(this);
        int c = g1.o_ncomida("cd4") + 2;
        g1.g_ncomida("cd4", c);
        victoria.start();
        // agregar funciones para aumentar el contador de comida

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("¡Ganaste!")
                .setMessage("¡Felicidades, has encontrado todas las parejas!\nGanaste 2+ de comida!")
                .setCancelable(false)
                .setPositiveButton("Volver a jugar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate(); // reinicia la actividad de forma segura
                    }
                })
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // cierra la actividad de manera suave
                    }
                })
                .show();

        // Aplica el fondo redondeado al AlertDialog
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog_background);
        }
    }


    //Metodo que muestra el total de parejas encontradas
    private void contador(){
        contador.setText("Parejas: " + parejasEncontradas + "/8");
    }

    //Metodo que se encarga de limpiar los sonidos una vez se finalize el juego o se salga la aplicacion
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (acierto != null) acierto.release();
        if (error != null) error.release();
        if (victoria != null) victoria.release();
    }

}