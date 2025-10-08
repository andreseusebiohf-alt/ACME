package GT.p1e2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;

public class cargaJuego3 extends SurfaceView implements SurfaceHolder.Callback {


    //hilo del juego
    Thread juego;
    Canvas canvas2;
    //actividad utilizada como referencia
    Activity activity;
    //variable que determina el descanso al inicio del juego
    boolean descanso=true;
    //contador para poder ver el tiempo del descanso
    int contador=0;
    //random utilizado para ubicar la comida callendo
    Random random=new Random();
    Random random2=new Random();
    int corx[]={0,100,500};
    int cory[]={0,-550,-1100};
    int x=0,y=1700;
    float aceleracion=10;
    Rect capi=new Rect(x,y,x+100,y+100);
    Rect obstaculos[]=new Rect[3];
    //contador de errores
    int errores=0;
    //contador de puntos
    int puntos=0;

    long tiempoinicial;

    //bitmasps(dibujos) que apareceran en el juego
    Bitmap canasta,fondo;
    Bitmap alimento[]=new Bitmap[3];

    //variable que determina si el juego esta corriendo
    boolean corriendo=true;
    public cargaJuego3(freefood context, Activity activity) {
        super(context);
        this.activity = activity;
        getHolder().addCallback(this);

        canasta= BitmapFactory.decodeResource(getResources(),R.drawable.canastajuego);
        canasta=Bitmap.createScaledBitmap(canasta,400,400,false);

        alimento[0]=BitmapFactory.decodeResource(getResources(),R.drawable.imglechuga);
        alimento[0]=Bitmap.createScaledBitmap(alimento[0],320,320,false);
        alimento[1]=BitmapFactory.decodeResource(getResources(),R.drawable.imgsandia);
        alimento[1]=Bitmap.createScaledBitmap(alimento[1],320,320,false);
        alimento[2]=BitmapFactory.decodeResource(getResources(),R.drawable.imgelote);
        alimento[2]=Bitmap.createScaledBitmap(alimento[2],280,280,false);

        fondo=BitmapFactory.decodeResource(getResources(),R.drawable.imgfcomida);
        fondo=Bitmap.createScaledBitmap(fondo,1100,2320,false);

        obstaculos[0]= new Rect(corx[0],cory[0],corx[0]+350,cory[0]+350);
        obstaculos[1]= new Rect(corx[1],cory[1],corx[1]+350,cory[1]+350);
        obstaculos[2]= new Rect(corx[2],cory[2],corx[2]+350,cory[2]+350);

        tiempoinicial=System.currentTimeMillis();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        juego=new Thread(new Runnable() {
            @Override
            public void run() {
                while (corriendo){
                   if (!getHolder().getSurface().isValid())continue;
                    actualizar();
                    Canvas canvas=getHolder().lockCanvas();

                    if(canvas!=null){
                        dibujar(canvas,0);
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                   if (descanso){
                        for ( contador=3;contador>=0;contador--){
                            Canvas canvas1=getHolder().lockCanvas();
                            if(!corriendo && !getHolder().getSurface().isValid()){
                                break;
                            }
                            if (canvas1!=null){
                                dibujar(canvas1,contador);
                                getHolder().unlockCanvasAndPost(canvas1);
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        descanso=false;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        juego.start();
    }

    public void actualizar(){


        aceleracion+= 0.1f;
       // long aunmento=(System.currentTimeMillis()-tiempoinicial/1000);
       // aceleracion= (int) (10+aunmento);
        cory[0]+=aceleracion;
        cory[1]+=aceleracion;
        cory[2]+=aceleracion;
        for (int i=0;i<3;i++){

            //verifica si alguno de los alimentos no fue atrapado, lo regresa al inicio y lo cuenta como un error
            if (getHeight()<cory[i]){
                errores++;
                cory[i]=-500;
                corx[i]=50+random.nextInt(getWidth()-300);
                MediaPlayer sonidofallo=MediaPlayer.create(activity,R.raw.efectoerror);
                sonidofallo.start();
            }
        }
        if (errores>=5){
            corriendo=false;
            MediaPlayer sonidoperder=MediaPlayer.create(activity,R.raw.gameover);
            sonidoperder.start();
            pantallaresultados();
        }

        capi.set(x+150,y+(canasta.getHeight()/2),x+250,y+100);
        obstaculos[0].set(corx[0],cory[0],corx[0]+350,cory[0]+350);
        obstaculos[1].set(corx[1],cory[1],corx[1]+350,cory[1]+350);
        obstaculos[2].set(corx[2]-50,cory[2],corx[2]+350,cory[2]+350);
        for (int i=0;i<3;i++){
            if (Rect.intersects(capi,obstaculos[i])){
                cory[i]=-700;
                corx[i]=50+random2.nextInt(getWidth()-300);
                puntos++;
            }
        }

    }
    public  void dibujar(Canvas canvas,int regreso){
        //paint de el contador de descanso
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);

        //pain del contador de puntos y errores
        Paint paint1=new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setTextSize(70);
        paint1.setFakeBoldText(true);
        paint1.setTextAlign(Paint.Align.CENTER);
        paint1.setAntiAlias(true);

        canvas.drawBitmap(fondo,0,0,null);
        for (int i=0;i<3;i++){
            canvas.drawBitmap(alimento[i],corx[i],cory[i],null);
        }
        canvas.drawBitmap(canasta,x,y,null);
        if (descanso){
            canvas.drawText(String.valueOf(contador), (float) getWidth() /2, (float) getHeight() /2,paint);
        }
        canvas.drawText("Errores: "+errores+"/5",getWidth()-250,100,paint1);

        canvas.drawText("Puntos: "+puntos,200,100,paint1);
    }
    public void pantallaresultados(){
        //hilo que manda a la pantalla de resultados de la partida
        Thread l=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        Intent i=new Intent(activity,resultadosdosjuegos.class);
                        i.putExtra("puntuacion",puntos);
                        i.putExtra("juego","freefood");
                        activity.finish();
                        activity.startActivity(i);
                    }
                });

            }
        });
        l.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!descanso){
            int i = canasta.getWidth() / 2;

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    x= (int) event.getRawX()-i;
                    Canvas canvas=getHolder().lockCanvas();
                    if (canvas!=null){
                        dibujar(canvas,0);

                    }
                    getHolder().unlockCanvasAndPost(canvas);
                    break;
                case MotionEvent.ACTION_MOVE:
                    x= (int) (event.getRawX())-i;
            }
        }
        return true;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        corriendo=false;
        try {
            juego.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
