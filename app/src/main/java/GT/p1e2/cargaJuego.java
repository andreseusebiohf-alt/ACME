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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class cargaJuego extends SurfaceView implements SurfaceHolder.Callback {

    Activity activity;
    boolean descanso=true;

    //variables de posicion del capibara, de los obstaculos, salto y el hilo para poder pasar los fotogramas
    private Thread juegohilo;
    //puntos del juego
    int puntos=0;
    String ptsString="Puntos: ";
    MediaPlayer efError,efSalto;
    boolean corriendo=true;
    boolean salto=false;
    private int capx=300, capy=505;
    private int obsx=2000, obsy=545;
    int velo=0;
    int acel=10;
    long tiempoini;
    int fondox=0;

    //arreglo para las iamgenes de la animacion
    Bitmap[] capanim;
    int fragutil=0;
    long tiempoutil=0;
    int timfot=100;

    //imagenes del fondo y el obstaculo
    Bitmap obs,fondo;


    public cargaJuego(capChrome conetext,Activity activity) {
        super(conetext);
        getHolder().addCallback(this);
        this.activity=activity;


        capanim=new Bitmap[7];
        capanim[0]= BitmapFactory.decodeResource(getResources(),R.drawable.capa1);
        capanim[1]= BitmapFactory.decodeResource(getResources(),R.drawable.capa2);
        capanim[2]= BitmapFactory.decodeResource(getResources(),R.drawable.capa3);
        capanim[3]= BitmapFactory.decodeResource(getResources(),R.drawable.capa4);
        capanim[4]= BitmapFactory.decodeResource(getResources(),R.drawable.capa5);
        capanim[5]= BitmapFactory.decodeResource(getResources(),R.drawable.capa6);
        capanim[6]= BitmapFactory.decodeResource(getResources(),R.drawable.capa7);

        //audios
        efSalto=MediaPlayer.create(conetext,R.raw.efectosalto);
        efError=MediaPlayer.create(conetext,R.raw.efectoerror);
        for (int i=0;i<capanim.length;i++){
            capanim[i]=Bitmap.createScaledBitmap(capanim[i],350,350,false);
        }

        obs=BitmapFactory.decodeResource(getResources(),R.drawable.rocajuego1);
        obs=Bitmap.createScaledBitmap(obs,300,300,false);

        fondo=BitmapFactory.decodeResource(getResources(),R.drawable.fondog1);
        fondo=Bitmap.createScaledBitmap(fondo,2320,982,false);

        tiempoini=System.currentTimeMillis();

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        juegohilo=new Thread(()-> {
            while (corriendo){
                if (!getHolder().getSurface().isValid())continue;

                actualizar();
                Canvas canvas=getHolder().lockCanvas();
                int contador;
                if(canvas!=null){
                    Log.d("JUEGO", "canvas creado");
                    dibujar(canvas,0);
                    getHolder().unlockCanvasAndPost(canvas);
                }else{

                    Log.d("JUEGO", "canvas null");
                }
                if(descanso){
                    for (contador=3;contador>=0;contador--){
                        if (!corriendo && !getHolder().getSurface().isValid()){
                            break;
                        }
                        Canvas canvas2=getHolder().lockCanvas();
                        if (canvas2!=null){
                            dibujar(canvas2,contador);
                            getHolder().unlockCanvasAndPost(canvas2);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                    descanso=false;
                }
                try {
                    Thread.sleep(10);
                } catch (Exception ignored) {
                }

            }
        });

        juegohilo.start();

    }

    public  void actualizar(){

        if (System.currentTimeMillis()-tiempoutil> timfot){

            fragutil=(fragutil+1)% capanim.length;
            tiempoutil=System.currentTimeMillis();
        }

        //movimiento del obstaculo
        obsx-=acel;
        if (obsx<=-120) {
            obsx = 2500;
            puntos+=1;
        }
        //codigo para qeu el fondo regrese al inicio de la pantalla
        fondox-=acel;
        if (fondox<=-getWidth()) fondox=0;

        if (salto){
            capy+=velo;
            velo+=2;
            if (capy>505){
                capy=505;
                salto=false;
                velo=0;
            }

        }
        //aumento de la velocidad del juego
        long  lapsoaumento=(System.currentTimeMillis()-tiempoini)/1000;
        acel=10+(int)(lapsoaumento);


        //detecta la colocion entre el obs y el capibara
        Rect rectcap=new Rect(capx,capy,capx+100,capy+100);
        Rect rectobs=new Rect(obsx-100,obsy,obsx+100,obsy+100);
        if (Rect.intersects(rectcap,rectobs)){
            efError.start();
            corriendo=false;

            //hilo que manda a la pantalla de resultados de la partida
            Thread l=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Intent i=new Intent(activity,resultadosdosjuegos.class);
                            i.putExtra("puntuacion",puntos);
                            i.putExtra("juego","capichrome");
                            activity.finish();
                            activity.startActivity(i);
                        }
                    });

                }
            });
            l.start();
        }
    }

    public void dibujar(Canvas canvas,int contador){
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);

        Paint paint1=new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setTextSize(70);
        paint1.setFakeBoldText(true);
        paint1.setTextAlign(Paint.Align.CENTER);
        paint1.setAntiAlias(true);


        //obtener el centro del dispositivo
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int centroX = metrics.widthPixels / 2;
        int centroY = metrics.heightPixels / 2;


        //dibuja el fondo
        canvas.drawBitmap(fondo,fondox,0,null);
        canvas.drawBitmap(fondo,fondox+getWidth(),0,null);
        //dibuja el capibara y el onstaculo
        canvas.drawBitmap(capanim[fragutil],capx,capy,null);
        canvas.drawBitmap(obs,obsx,obsy,null);
        //dibujo de puntuacion que cada 3 obstaculos saltados da un punto o comida tambien agregar la logica de ello
        canvas.drawText(ptsString+puntos,2000,200,paint1);

        //en este if se dibuja el contador de inicio que dura 3 segundos
        if(descanso){
            canvas.drawText(String.valueOf(contador),centroX,centroY-20,paint);
        }
    }
    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction()==MotionEvent.ACTION_DOWN && !salto && corriendo && !descanso){
            velo=-33;
            salto=true;
            efSalto.start();
        }
        return true;
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        corriendo =false;
        try { juegohilo.join(); } catch (InterruptedException e) {}
    }
}
