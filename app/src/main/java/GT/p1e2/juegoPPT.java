package GT.p1e2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class juegoPPT extends AppCompatActivity {

    //Declaracion de variables y componentes
    Button btnPiedra, btnPapel, btnTijera;
    TextView contador;
    ImageView imgPlayer, imgPC;
    int punPlayer = 0, punPC = 0;
    int rondasJugadas = 0;
    final int maxRondas = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_juego_ppt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Referencia de los componentes
        btnPapel = findViewById(R.id.Papel);
        btnTijera = findViewById(R.id.Tijera);
        btnPiedra = findViewById(R.id.Piedra);

        contador = findViewById(R.id.txtContador);

        imgPC = findViewById(R.id.ImgPC);
        imgPlayer = findViewById(R.id.ImgJugador);

        //accion que ejecuta el boton de piedra
        btnPiedra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPlayer.setImageResource(R.drawable.piedra);
                turno("Piedra");
                contador.setText("Jugador: " + Integer.toString(punPlayer) + " Pc: " + Integer.toString(punPC));
            }
        });

        //accion que ejecuta el boton de papel
        btnPapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPlayer.setImageResource(R.drawable.papel);
                turno("Papel");
                contador.setText("Jugador: " + Integer.toString(punPlayer) + " Pc: " + Integer.toString(punPC));
            }
        });

        //accion que ejecuta el boton de tijera
        btnTijera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPlayer.setImageResource(R.drawable.tigera);
                turno("Tijera");
                contador.setText("Jugador: " + Integer.toString(punPlayer) + " Pc: " + Integer.toString(punPC));
            }
        });
    }
    //Metodo que se encarga de dar el turno a la opcion elegida
    public void turno(String eleccion) {
        //if que compara si ya supero el limite de rondas
        if (rondasJugadas >= maxRondas) return;

        Random rd = new Random();
        String selecPC = "";
        int selecPcNum = rd.nextInt(3) + 1;

        //if que compara los 3 posibles casos
        if (selecPcNum == 1) {
            selecPC = "Piedra";
        } else if (selecPcNum == 2) {
            selecPC = "Papel";
        } else if (selecPcNum == 3) {
            selecPC = "Tijera";
        }

        //if que compara la seleccion de la pc
        if (selecPC == "Piedra") {
            imgPC.setImageResource(R.drawable.piedra);
        } else if (selecPC == "Papel") {
            imgPC.setImageResource(R.drawable.papel);
        } else if (selecPC == "Tijera") {
            imgPC.setImageResource(R.drawable.tigera);
        }

        //if que compara la seleccion de la pc con el jugador
        if (selecPC == eleccion) {
        } else if (eleccion == "Piedra" && selecPC == "Tijera") {
            punPlayer++;
        } else if (eleccion == "Piedra" && selecPC == "Papel") {
            punPC++;
        } else if (eleccion == "Papel" && selecPC == "Piedra") {
            punPlayer++;
        } else if (eleccion == "Papel" && selecPC == "Tijera") {
            punPC++;
        } else if (eleccion == "Tijera" && selecPC == "Piedra") {
            punPC++;
        } else if (eleccion == "Tijera" && selecPC == "Papel") {
            punPlayer++;
        }

        // contador que aumenta las rondas
        rondasJugadas++;

        // if que verifica si se alcanzaron las 3 rondas
        if (rondasJugadas == maxRondas) {
            String mensajeFinal = "Juego terminado. ";
            if (punPlayer > punPC) {
                mensajeFinal += "¡Ganaste!\n +2 de comida";
                variables_g v1=new variables_g(this);
                int c=v1.o_ncomida("cd2")+2;
                v1.g_ncomida("cd2",c);
                //agregar codigo para aumentar el contador de comida

            } else if (punPlayer < punPC) {
                mensajeFinal += "¡Perdiste!";
            } else {
                mensajeFinal += "¡Empate!";
            }

            Toast.makeText(this, mensajeFinal, Toast.LENGTH_SHORT).show();

            // desacctivar los botones para evitr otra seleccion
            btnPiedra.setEnabled(false);
            btnPapel.setEnabled(false);
            btnTijera.setEnabled(false);


            Toast.makeText(this, "Reiniciando juego en 5 segundos...", Toast.LENGTH_SHORT).show();

            // Reinicio automatico despues de 5 segundos
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    punPlayer = 0;
                    punPC = 0;
                    rondasJugadas = 0;

                    contador.setText("Jugador: 0  PC: 0");
                    imgPlayer.setImageResource(0); // limpia la imagen del jugador
                    imgPC.setImageResource(0); // limpia la imagen de la pc

                    //Habilita nuevamente los botones
                    btnPiedra.setEnabled(true);
                    btnPapel.setEnabled(true);
                    btnTijera.setEnabled(true);
                }
            }, 5000);
        }
    }
}