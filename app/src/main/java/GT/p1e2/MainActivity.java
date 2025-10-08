package GT.p1e2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ProcessLifecycleOwner;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editar;
    String booleanEdad="booleanedad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("edad",MODE_PRIVATE);
        editar=sharedPreferences.edit();

        //instancia de audio para poder detectar al cerrar la app y poder detener los audios
        audio audioObserver = new audio(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(audioObserver);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Runnable r= new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5100);
                    Runnable rr=new Runnable() {
                        @Override
                        public void run() {

                            if (!inicio()){
                                editar.putBoolean(booleanEdad,true);
                                editar.apply();
                                Intent i =new Intent(MainActivity.this,registro_edad.class);
                                startActivity(i);
                                finish();
                            }else {
                                Intent i =new Intent(MainActivity.this,menu.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    };
                    runOnUiThread(rr);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread mithread=new Thread(r);
        mithread.start();
    }

    public boolean inicio(){
        return sharedPreferences.getBoolean(booleanEdad,false);
    }
}