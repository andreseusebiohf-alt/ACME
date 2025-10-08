package GT.p1e2;

import static android.os.Build.VERSION_CODES.O;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import GT.p1e2.classesdequiz.Principal;
import GT.p1e2.classesdequiz.quizglobal;

public class menu extends AppCompatActivity {

    private ImageView imagen,capibaraimagen;
    int gt=1, g=0, gt2;
    int nc=0, vc=0;
    private View vcolicion;
    //valores de localizacion de la imagen
    private float dx,dy;
    //valores de localizacion del inicio
    private float fx,fy;
    TextView nombre;
    boolean corriendo=false;
    MediaPlayer sonidocomida;

    //boolean para abrir las demas opciones de los quiz
    boolean abrir=false;
    ImageView[] botones;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        //obciones de quiz
        botones= new ImageView[]{
                findViewById(R.id.btnopcion1),
                findViewById(R.id.btnopcion2),
                findViewById(R.id.btnopcion3),
                findViewById(R.id.btnopcion4),
                findViewById(R.id.btnopcion5)
        };

        nombre=findViewById(R.id.textonombre);
        sonidocomida=MediaPlayer.create(this,R.raw.comer);
        //metodo para comprobar si esta usando un gorro el capibara
        capibaraimagen=findViewById(R.id.capibara);
        capibaraimagen.setImageResource(R.drawable.capiimg);
        comprobacion();
        imagenessecretas();
        //instancia de la clase con los valores guardados
        variables_g g1=new variables_g(this);
        // iniciar el class que da una cantidad de acomida por defecto
        g1.iniciopredeterminado();
        //inicializacion de la barra de progreso y el texto
        
        barradeprogreso(0);
        nuevabarra();
        resbar();
        mus();
        metodonombre();

        imagen = findViewById(R.id.comida);
        imagen.setImageResource(R.drawable.imgsincomida);
        vcolicion = findViewById(R.id.colicion);

        //guardado de los valores iniciales de localizacion
        imagen.post(new Runnable() {
            @Override
            public void run() {
               fx=imagen.getX();
               fy=imagen.getY();
            }
        });

        //metodo que permite arrastrar la imagen
        imagen.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint({"ClickableViewAccessibility", "WrongViewCast"})
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               //switch que detecta la accion hecha
                if (corriendo) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            dx = v.getX() - event.getRawX();
                            dy = v.getY() - event.getRawY();
                            gt2 = 0;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            v.setX((event.getRawX() + dx));
                            v.setY((event.getRawY() + dy));
                            //if que comprueba si v esta en colicion con el view y regresa la imagen a su localizacion original
                            if (HC(v, vcolicion)) {
                                v.setX(fx);
                                v.setY(fy);
                                gt2 = 1 + gt2;
                                if (gt == gt2 && g1.o_ncomida("cd7") <= 70) {
                                    sonidocomida.start();
                                    g = g1.o_ncomida("cd7");
                                    g = g + vc;
                                    g1.g_ncomida("cd7", g);
                                    barradeprogreso(vc);
                                    int nvalorc = g1.o_ncomida("cd" + String.valueOf(nc));
                                    g1.g_ncomida("cd" + String.valueOf(nc), nvalorc - 1);
                                    ifcomida(false);
                                }
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            v.setX(fx);
                            v.setY(fy);
                            break;
                    }
                }
                return true;
            }
        });
    }
    private boolean HC(View view1,View view2){
        //comprueba si los view colicionan
        Rect rect1 = new Rect();
        view1.getGlobalVisibleRect(rect1);
        Rect rect2 = new Rect();
        view2.getGlobalVisibleRect(rect2);
        //si el valor el true colicionan
        return rect1.intersect(rect2);
    }

    public void ajustess(View view) {
        //abre el adialogfragment"ajustes"
          f_ajustes aju=new f_ajustes();
          aju.show(getSupportFragmentManager(),"Ajustes");
    }

    public void armarioo(View view) {
        armarioSombreros arm=new armarioSombreros();
        arm.show(getSupportFragmentManager(),"Armario");
    }

    public void cizq(View view) {
        if(nc>0){
            nc--;
        }
        ifcomida(true);
    }

    public void cder(View view) {
        if (nc<6){
            nc++;
        }
        ifcomida(false);
    }
     //en esta funcion hay un if que selecciona el tipo de comida
    public void ifcomida(boolean re_c){
        variables_g g1=new variables_g(this);
        //for que da una segunda vuelta para encontrar el valor correcto
        for (int i=0;i<=2;i++) {
            //if anidado que busca el valor correcto a seleccionar
            if (nc == 0) {
                imagen.setImageResource(R.drawable.imgsincomida);
                vc = 0;
                corriendo=false;
            }
            if (nc == 1) {
               if (g1.o_ncomida("cd1")>0){
                   imagen.setImageResource(R.drawable.imgagua);
                   vc = 1;
                   corriendo=true;
               }else {
                   if (re_c == true) {
                       nc = 0;
                   } else {
                       nc = 2;
                   }
               }
            }
            if (nc == 2) {
                if (g1.o_ncomida("cd2")>0){
                    imagen.setImageResource(R.drawable.imgzanahoria);
                    vc = 3;
                    corriendo=true;
                }else {
                    if (re_c == true) {
                        nc = 1;
                    } else {
                        nc = 3;
                    }
                }
            }
            if (nc == 3) {
                if (g1.o_ncomida("cd3")>0){
                    int j=g1.o_ncomida("cd3");
                    imagen.setImageResource(R.drawable.imglechuga);
                    vc = 5;
                    corriendo=true;
                }else {
                    if (re_c == true) {
                        nc = 2;
                    } else {
                        nc = 4;
                    }
                }
            }
            if (nc == 4) {
                if (g1.o_ncomida("cd4")>0){
                    int j=g1.o_ncomida("cd4");
                    imagen.setImageResource(R.drawable.imgelote);
                    vc = 3;
                    corriendo=true;
                }else {
                    if (re_c == true) {
                        nc = 3;
                    } else {
                        nc = 5;
                    }
                }
            }
            if (nc == 5) {
                if (g1.o_ncomida("cd5")>0){
                    int j=g1.o_ncomida("cd5");
                    imagen.setImageResource(R.drawable.imgsandia);
                    vc = 5;
                    corriendo=true;
                }else {
                    if (re_c == true) {
                        nc = 4;
                    } else {
                        nc = 6;
                    }
                }
            }
            if (nc == 6) {
                if (g1.o_ncomida("cd6")>0){
                    int j=g1.o_ncomida("cd6");
                    imagen.setImageResource(R.drawable.imgplanta);
                    vc = 10;
                    corriendo=true;
                }else {
                    if (re_c == true) {
                        nc = 5;
                    } else {
                        nc = 0;
                    }
                }
            }
        }
    }
    public void juegoss(View view) {

        Intent i=new Intent(this, menujuegos.class);
        startActivity(i);
    }
    //barra de progreso que suma los valores de comidad dados
    public void barradeprogreso(int v){
        variables_g g1=new variables_g(this);
        ProgressBar barradep=findViewById(R.id.progressBar);
        barradep.setMax(70);
        barradep.setProgress(g1.o_ncomida("cd7")+v);
        g1.g_ncomida("cd7",g1.o_ncomida("cd7")+v);
    }

    //metodo que reduce la barra respecto al tiempo fuera
    public void nuevabarra() {
        variables_g g1 = new variables_g(this);
        ProgressBar barradep = findViewById(R.id.progressBar);
        barradep.setMax(70);

        SharedPreferences pref = getSharedPreferences("datos", MODE_PRIVATE);
        int contador = pref.getInt("barrag", 0);
        if (contador < 0) contador = 0;

        long guardado = pref.getLong("ahora", System.currentTimeMillis());
        long ahora = System.currentTimeMillis();
        long diferencia = (ahora - guardado) / 1000; // en segundos

        int unidades_a_restar = (int)(diferencia / 120); // cada 2 minutos baja 1

        int nuevabarra = contador - unidades_a_restar;
        if (nuevabarra < 0) nuevabarra = 0;

        barradep.setProgress(nuevabarra);
        g1.g_ncomida("cd7", nuevabarra);

        // Debug opcional
        Log.d("DEBUGh", "contador=" + contador + ", diferencia=" + diferencia + "s, restar=" + unidades_a_restar + ", nueva=" + nuevabarra);
    }

    //funcion para disminuir la comida cada 2 min
 public void resbar() {
     Handler handler = new Handler();
     Handler handler1 = new Handler();
     variables_g g1 = new variables_g(this);
     ProgressBar barradep = findViewById(R.id.progressBar);
     barradep.setMax(70);

     int t = 120000; // 2 minutos
     Runnable r = new Runnable() {
         @Override
         public void run() {
             int actual = g1.o_ncomida("cd7");

             if (actual > 0) {
                 int nuevo = actual - 1;

                 barradep.setProgress(nuevo);
                 g1.g_ncomida("cd7", nuevo);

                 handler.postDelayed(this, t);
             } else {
                 barradep.setProgress(0);
                 g1.g_ncomida("cd7", 0);
             }
         }
     };

     /*Runnable rr = new Runnable() {
         @Override
         public void run() {
             int barraac = g1.o_ncomida("cd7");

             if (barraac <= 0) {
                 // Detener el guardado si el valor llegó a 0
                 handler1.removeCallbacks(this);
                 handler.removeCallbacks(r);
                 return;
             }

             long ahora = System.currentTimeMillis();
             SharedPreferences pref = getSharedPreferences("datos", MODE_PRIVATE);
             pref.edit().putInt("barrag", barraac).putLong("ahora", ahora).apply();

             handler1.postDelayed(this, 1000);
         }
     };*/

     if (g1.o_ncomida("cd7") > 0) {
         handler.post(r);
     }
    // handler1.post(rr);
 }

    public void mus(){
        variables_g g1=new variables_g(this);
        audio au=new audio(this);
        if (g1.o_musica()==true){
            au.audio1(this);
        }
    }

    public void desafios(View view) {
        if (abrir){
            for (int i=0;i< botones.length;i++){
                ImageView b=botones[i];
                ObjectAnimator mover= ObjectAnimator.ofFloat(b, "translationY",O);
                ObjectAnimator alpha=ObjectAnimator.ofFloat(b, "alpha", 0f);
                AnimatorSet animacion=new AnimatorSet();
                animacion.playTogether(mover,alpha);
                animacion.setDuration(300);
                animacion.start();
                int index=i;
                animacion.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        if (index== botones.length -1){
                            for (ImageView btn : botones) btn.setVisibility(View.GONE);
                        }
                    }
                });
            }
            abrir=false;
        }else {
            for (int i=0; i<botones.length;i++){
                ImageView b=botones[i];
                b.setVisibility(View.VISIBLE);
                int distancia=(i+1)*250;
                ObjectAnimator mover=ObjectAnimator.ofFloat(b, "translationY", -distancia);
                ObjectAnimator alpha=ObjectAnimator.ofFloat(b,"alpha", 1f);
                AnimatorSet animacion=new AnimatorSet();
                animacion.playTogether(mover,alpha);
                animacion.setDuration(300);
                animacion.start();
            }
            abrir=true;
        }
    }
    public void metodonombre(){
        variables_g g1=new variables_g(this);
        nombre.setText(g1.o_nombre());
    }

    public void comprobacion(){
        Log.i("menumensaje","metodo comprobacion");
        variables_g v1=new variables_g(this);

        if (v1.o_valor_g("llave1")){
            capibaraimagen.setImageResource(R.drawable.imgitca);
            Log.i("menumensaje","if");
        }
        if (v1.o_valor_g("llave2")){
            capibaraimagen.setImageResource(R.drawable.imgzelda);
            Log.i("menumensaje","if");
        }
        if (v1.o_valor_g("llave3")){
            capibaraimagen.setImageResource(R.drawable.imgluigi);
            Log.i("menumensaje","if");
        }
        if (v1.o_valor_g("llave4")){
            capibaraimagen.setImageResource(R.drawable.imgmario);
            Log.i("menumensaje","if");
        }if (v1.o_valor_g("llave5")){
            capibaraimagen.setImageResource(R.drawable.imgperu);
            Log.i("menumensaje","if");
        }if (v1.o_valor_g("llave6")){
            capibaraimagen.setImageResource(R.drawable.imgmexa);
            Log.i("menumensaje","if");
        }if (v1.o_valor_g("llave7")){
            capibaraimagen.setImageResource(R.drawable.imgwicked);
            Log.i("menumensaje","if");
        }
        if (v1.o_valor_g("llave8")){
            capibaraimagen.setImageResource(R.drawable.imgclash);
            Log.i("menumensaje","if");
        }
        if (v1.o_valor_g("llave8")){
            capibaraimagen.setImageResource(R.drawable.imgclash);
            Log.i("menumensaje","if");
        }
    }
    public void imagenessecretas(){
        variables_g v1=new variables_g(this);
        if (v1.o_nombre().equals("Peter Parker")){
            capibaraimagen.setImageResource(R.drawable.peter);
        }
        if (v1.o_nombre().equals("Gary")){
            capibaraimagen.setImageResource(R.drawable.gary);
        }


    }
    @Override
    protected void onStop() {
        super.onStop();

        variables_g g1 = new variables_g(this);
        SharedPreferences pref = getSharedPreferences("datos", MODE_PRIVATE);
        int barraac = g1.o_ncomida("cd7");
        long ahora = System.currentTimeMillis();

        pref.edit().putInt("barrag", barraac).putLong("ahora", ahora).apply();
    }

    public void opcion1(View view) {
       variables_g v1= new variables_g(this);
        v1.guardar_quiz_elegido(1);
        Intent i= new Intent(this,Principal.class);
        startActivity(i);
    }

    public void opcion2(View view) {
        variables_g v1= new variables_g(this);
        if (v1.o_valor_edad()>11){
            v1.guardar_quiz_elegido(2);
            Intent i= new Intent(this, Principal.class);
            startActivity(i);
        }else{
            Toast.makeText(this,"opcion no desbloqueada", Toast.LENGTH_SHORT).show();
        }
    }

    public void opcion3(View view) {
        variables_g v1= new variables_g(this);
        if (v1.o_valor_edad()>15){
            v1.guardar_quiz_elegido(3);
            Intent i= new Intent(this,Principal.class);
            startActivity(i);
        }else{
            Toast.makeText(this,"opcion no desbloqueada", Toast.LENGTH_SHORT).show();
        }
    }

    public void opcion4(View view) {
        variables_g v1= new variables_g(this);
        if (v1.o_valor_edad()>18){
            v1.guardar_quiz_elegido(4);
            Intent i= new Intent(this,Principal.class);
            startActivity(i);
        }else{
            Toast.makeText(this,"opcion no desbloqueada", Toast.LENGTH_SHORT).show();
        }
    }

    public void opcion5(View view) {
       /* variables_g v1= new variables_g(this);
        if (v1.o_valor_edad()>24){
            v1.guardar_quiz_elegido(5);
            Intent i= new Intent(this,Principal.class);
            startActivity(i);
        }else{*/
            Toast.makeText(this,"opcion no desbloqueada", Toast.LENGTH_SHORT).show();
        //}
    }
}