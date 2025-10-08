package GT.p1e2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registro_edad extends AppCompatActivity {

    SeekBar baredad;
    Button boton;
    TextView textoedad;
    ImageView imagenedad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_edad);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        boton=findViewById(R.id.botonaceptar);
        baredad=findViewById(R.id.idseekbar);
        baredad.setMin(10);
        baredad.setMax(40);
        baredad.setProgress(18);
        textoedad=findViewById(R.id.texto1);
        imagenedad=findViewById(R.id.imagenedadd);

        variables_g v1=new variables_g(registro_edad.this);

        baredad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progreso=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progreso=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (progreso==10 || progreso==40){
                    if (progreso==10){
                        textoedad.setText(String.valueOf(progreso)+" o -");
                    }if (progreso==40){
                        textoedad.setText(String.valueOf(progreso)+" o +");
                    }
                }else {
                    textoedad.setText(String.valueOf(progreso));
                }
                elegirimagen(progreso);
                v1.g_valor_edad(progreso);
            }
        });
    }
    public void elegirimagen(int progreso){
        if(progreso>0 && progreso<12){
            imagenedad.setImageResource(R.drawable.peter);
        };
        if (progreso>11 && progreso <16){
            imagenedad.setImageResource(R.drawable.gary);
        };
        if (progreso>15 && progreso <19){
            imagenedad.setImageResource(R.drawable.capiimg);
        };
        if (progreso>18 && progreso <25){
            imagenedad.setImageResource(R.drawable.imgclash);
        };
        if (progreso>24 && progreso<30){
            imagenedad.setImageResource(R.drawable.capicapa);
        };
        if (progreso>30){
            imagenedad.setImageResource(R.drawable.ajustescapi);
        };
    }
    public void btnaceptar(View view) {
        Intent i =new Intent(registro_edad.this,menu.class);
        startActivity(i);
        finish();
    }
}