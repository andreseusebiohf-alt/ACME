package GT.p1e2;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;


public class audio implements DefaultLifecycleObserver {

    private static MediaPlayer mu;
    Context context;
    //constructor para poder dar un context
    public audio(Context context){
        this.context=context;
    }
    //en este metodo se ejecuta el audio
    public static void audio1(Context context){
        variables_g g1=new variables_g(context);
        //primero revisa si el audio esta en null, si es hasi lo crea y lo deja en bucle
        if (mu==null){
            mu=MediaPlayer.create(context,R.raw.mirainoyoru);
            mu.setLooping(true);
        }
        //despues revisa si esta reproduciendo en caso de no, lo reproduce
        if (!mu.isPlaying()){
            mu.start();
            g1.n_musica(true);
        }
    }
    //metodo que pausa el audio
    public void pause(){
        //revisa si el audio es diferente a null y si ejecuta, al cumplir las dos consiciones lo pausa y reinicia 
        if (mu !=null && mu.isPlaying()){
            mu.pause();
            mu.seekTo(0);
        }

    }
//metodo que detecta si la app fue puesta en primer plano y ejecuta el audio
    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        variables_g g1=new variables_g(context);
        Log.d("MiApp", "inicio");
        if (g1.o_musica()){

            if (mu==null){
                mu=MediaPlayer.create(context,R.raw.mirainoyoru);
                mu.setLooping(true);
            }
            if (!mu.isPlaying()){
                mu.start();
                g1.n_musica(true);
            }
        }
    }
//metodo que detecta si la app fue puesta en segundo plano y pausa el audio
    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.d("MiApp", "pausa");
        if (mu !=null && mu.isPlaying()){
            mu.pause();
            mu.seekTo(0);
        }
    }
}
