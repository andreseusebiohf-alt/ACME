package GT.p1e2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class juegoGato extends AppCompatActivity {
    //int de suma de jugadas ganadas
    int j1=0,j2=0;
    //boolean que determina el turno que esta
    boolean turnos=true;
    //imagenes del juego de gato
    ImageButton[] imagenes=new ImageButton[9];
    //booleans de imagenes
    int imagenesint[]={0,0,0,0,0,0,0,0,0};
    boolean corriendo=true;
    TextView textj1,textj2,trn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_juego_gato);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imagenes[0]=findViewById(R.id.izquierda1);
        imagenes[1]=findViewById(R.id.centro1);
        imagenes[2]=findViewById(R.id.derecha1);
        imagenes[3]=findViewById(R.id.izquierda2);
        imagenes[4]=findViewById(R.id.centro2);
        imagenes[5]=findViewById(R.id.derecha2);
        imagenes[6]=findViewById(R.id.izquierda3);
        imagenes[7]=findViewById(R.id.centro3);
        imagenes[8]=findViewById(R.id.derecha3);

        textj1=findViewById(R.id.textoj1);
        textj2=findViewById(R.id.textoj2);
        trn=findViewById(R.id.textoturnos);

        imagenes();
    }

    public void imagenes(){
        for (int i=0;i<imagenes.length;i++){
            imagenes[i].setImageResource(R.drawable.imghcan);
            imagenes[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    public void elegir(int x) {
        //if para verificar si el juego sigue corriendo
        if (corriendo){
            //if para comprovar de quien es el turno
        if (turnos) {
            //if que compruebe si la imagen elegida esta libre y se puede tomar
            if (imagenesint[x] == 0) {
                imagenes[x].setImageResource(R.drawable.imggato1);
                imagenesint[x] = 1;
                //if que comprueba si el jugador gano
                if (imagenesint[0] == 1 && imagenesint[1] == 1 && imagenesint[2] == 1 || imagenesint[3] == 1 && imagenesint[4] == 1 && imagenesint[5] == 1 || imagenesint[6] == 1 && imagenesint[7] == 1 && imagenesint[8] == 1 ||
                        imagenesint[0] == 1 && imagenesint[3] == 1 && imagenesint[6] == 1 || imagenesint[1] == 1 && imagenesint[4] == 1 && imagenesint[7] == 1 || imagenesint[2] == 1 && imagenesint[5] == 1 && imagenesint[8] == 1 ||
                        imagenesint[0] == 1 && imagenesint[4] == 1 && imagenesint[8] == 1 || imagenesint[2] == 1 && imagenesint[4] == 1 && imagenesint[6] == 1) {
                    Toast.makeText(this, "Ganaste J1", Toast.LENGTH_SHORT).show();
                    j1++;
                    textj1.setText("Juegos Ganados J1:"+j1);
                    corriendo=false;
                 //   sumar();
                }
            }
            turnos=false;
            trn.setText("Turno: j2");
            empate();
        } else {
           if (imagenesint[x] == 0) {
                imagenes[x].setImageResource(R.drawable.imggato2);
                imagenesint[x] = 2;
                if (imagenesint[0] == 2 && imagenesint[1] == 2 && imagenesint[2] == 2 || imagenesint[3] == 2 && imagenesint[4] == 2 && imagenesint[5] == 2 || imagenesint[6] == 2 && imagenesint[7] == 2 && imagenesint[8] == 2 ||
                        imagenesint[0] == 2 && imagenesint[3] == 2 && imagenesint[6] == 2 || imagenesint[1] == 2 && imagenesint[4] == 2 && imagenesint[7] == 2 || imagenesint[2] == 2 && imagenesint[5] == 2 && imagenesint[8] == 2 ||
                        imagenesint[0] == 2 && imagenesint[4] == 2 && imagenesint[8] == 2 || imagenesint[2] == 2 && imagenesint[4] == 2 && imagenesint[6] == 2) {
                    Toast.makeText(this, "Ganaste J2", Toast.LENGTH_SHORT).show();
                    j2++;
                    textj2.setText("Juegos Ganados J2:"+j2);
                    corriendo=false;
                }
            }
           turnos=true;
            trn.setText("Turno: j1");
            empate();
        }
        }
}
//metodo que comprueba si hubo un empate
public void empate(){
    if (imagenesint[0]!=0 && imagenesint[1]!=0 && imagenesint[2]!=0 && imagenesint[3]!=0
            && imagenesint[4]!=0 && imagenesint[5]!=0 && imagenesint[6]!=0 && imagenesint[7]!=0
            && imagenesint[8]!=0){
        Toast.makeText(this,"empate",Toast.LENGTH_SHORT).show();
        corriendo=false;
    }
}

    public void primerimagen(View view) {
        elegir(0);
    }
    public void segundaimagen(View view) {
        elegir(1);
    }
    public void tercerimagen(View view) {
        elegir(2);
    }
    public void cuartaimagen(View view) {
        elegir(3);
    }
    public void quintaimagen(View view) {
        elegir(4);
    }
    public void sextaimagen(View view) {
        elegir(5);
    }
    public void septimaimagen(View view) {
        elegir(6);
    }
    public void octavaimagen(View view) {
        elegir(7);
    }
    public void novenaimagen(View view) {
        elegir(8);
    }
    //metodo que reinicia los valores para una nueva partida
    public void reinicio(View view) {
        turnos=true;
        corriendo=true;
        for (int i=0;i< imagenesint.length;i++){
            imagenesint[i]=0;
            imagenes[i].setImageDrawable(null);
            trn.setText("Turno: j1");
        }
        imagenes();

    }
}