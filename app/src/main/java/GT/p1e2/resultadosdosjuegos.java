package GT.p1e2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class resultadosdosjuegos extends AppCompatActivity {

    int puntos;
    String juego;
    TextView textopuntos,comidaobtenida;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultadosdosjuegos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         comidaobtenida=findViewById(R.id.totalComida);
         textopuntos=findViewById(R.id.resultados);
         puntos= getIntent().getIntExtra("puntuacion",0);
         juego=getIntent().getStringExtra("juego");
         resultado();
    }

    public void resultado(){
       textopuntos.setText(puntos+" puntos");
       comidaobtenida.setText(puntos+"/10 = "+puntos/10);
       switch (juego){
           case "freefood":
               guardar("cd1");
               break;
           case "flappybird":
               guardar("cd6");
               break;
           case "capichrome":
               guardar("cd3");
               break;
       }
    }
    public void guardar(String llave){
        variables_g v1=new variables_g(this);
        int r=v1.o_ncomida(llave)+(puntos/10);
        v1.g_ncomida(llave,r);
    }


    public void reintentar(View view) {
        Intent i=null;
        if (juego.equals("capichrome")){
            i=new Intent(this, capChrome.class);
        }
        if (juego.equals("flappybird")){
            i=new Intent(this, capFlappyBird.class);
        }
        if (juego.equals("freefood")){
            i=new Intent(this, freefood.class);
        }
        finish();
        startActivity(i);
    }

    public void salir(View view) {
        finish();
    }
}