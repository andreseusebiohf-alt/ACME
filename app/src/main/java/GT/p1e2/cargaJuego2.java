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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;

public class cargaJuego2 extends SurfaceView implements SurfaceHolder.Callback {
Activity activity;

boolean corriendo=true;

    //puntos del juego
    int puntos=0;
    String ptsString="Puntos: ";

//descanso de inicio
    boolean descanso=true;
//hilo
    Thread juego;
//objetos a dibujar
    Bitmap fondo,capibara,
        obstaculo,obstaculo2;

    //aonidos
    MediaPlayer falla,caida;

    //posiciones de los obsjetos
            int capx=350,capy=300,fondox=0;
    int obsx[]={2500,3400,4300};
    int obsyarriba[]={-2800,-2600,-2800};
    int obsyabajo[]={200,400,200};
    //salto
    int vely=0;

    //rect para las coliciones de los objetos
    Rect rectcapibara=new Rect(capx,capy,capx+100,capy+100);

    //valores para aparecer los obstaculos de forma aleatoria
    Random random=new Random();
    int aleatorio,aleatorio2,aleatorio3;
    //velocidad del juego
    int avance=13;

    public cargaJuego2(capFlappyBird context, Activity activity) {
        super(context);
        this.activity=activity;
        getHolder().addCallback(this);

        //poniendo las imagenes a los objetos
        fondo= BitmapFactory.decodeResource(getResources(),R.drawable.fondojuego2);
        capibara=BitmapFactory.decodeResource(getResources(),R.drawable.capicapa);
        obstaculo=BitmapFactory.decodeResource(getResources(),R.drawable.obstaculo1);
        obstaculo2=BitmapFactory.decodeResource(getResources(),R.drawable.obstaculo2);
        ///agregado
        falla=MediaPlayer.create(context,R.raw.efectoerror);

        //se escalar todos los objetos que se mostraran en pantalla
        fondo=Bitmap.createScaledBitmap(fondo,2320,992,false);
        capibara=Bitmap.createScaledBitmap(capibara,330,330,false);
        obstaculo=Bitmap.createScaledBitmap(obstaculo,300,3000,false);
        obstaculo2=Bitmap.createScaledBitmap(obstaculo2,300,3000,false);

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
                    int contador;
                    if (canvas!=null){
                        dibujar(canvas,0);
                        getHolder().unlockCanvasAndPost(canvas);
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
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        juego.start();

    }

    public void actualizar(){
        //metod donde se actualizara la posicicon de los obstaculos capibara y el fondo

        obsx[0]-=avance;
        if (obsx[0]<=-250){
            puntos+=1;
            obsx[0]=2500;
            aleatorio=50+random.nextInt((getHeight()-100)-50+1);
            obsyarriba[0]=aleatorio-3000;
            obsyabajo[0]=aleatorio;
        }
        obsx[1]-=avance;
        if (obsx[1]<=-250){
            puntos+=1;
            obsx[1]=2500;
            aleatorio2=50+random.nextInt((getHeight()-100)-50+1);
            obsyarriba[1]=aleatorio2-3000;
            obsyabajo[1]=aleatorio2;
        }
        obsx[2]-=avance;
        if (obsx[2]<=-250){
            puntos+=1;
            obsx[2]=2500;
            aleatorio3=50+random.nextInt((getHeight()-100)-50+1);
            obsyarriba[2]=aleatorio3-3000;
            obsyabajo[2]=aleatorio3;
        }

        fondox-=avance;
        if (fondox<=getWidth()){
            fondox=0;
        }

        capy+=vely;
        vely+=2;
        rectcapibara.set(capx-5,capy+15,capx+190,capy+210);

        //aplicar set para cambiar de posicion
       if (regreso(rectcapibara)){
           falla.start();
           corriendo=false;
           pantallaresultados();
       }
       if (getHeight() < capy || (capy+200)<0){
           falla.start();
           corriendo=false;
           pantallaresultados();
       }

    }
    public boolean regreso(Rect rectacap){
        Rect rectarriba=new Rect(0,0,0,0);
        Rect rectabajo=new Rect(0,0,0,0);
        for (int i=0; i<3;i++){
            rectarriba.set(obsx[i]-20,obsyarriba[i],obsx[i]+150,obsyarriba[i]+2765);
            rectabajo.set(obsx[i]-20,obsyabajo[i]+167,obsx[i]+150,obsyabajo[i]+1000);
            if (Rect.intersects(rectarriba,rectacap) || Rect.intersects(rectabajo,rectacap)){
                return  true;
            }
        }
        return false;
    }

    public void dibujar(Canvas canvas,int contador){

        //paint que se utiliza en el contador
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);

        //pain del contador de puntos
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

        //dibujos de los objetos
        canvas.drawBitmap(fondo,fondox,0,null);
        canvas.drawBitmap(fondo,fondox+getWidth(),0,null);
        canvas.drawBitmap(capibara,capx,capy,null);
        for (int i=0;i<3;i++){
            canvas.drawBitmap(obstaculo,obsx[i],obsyarriba[i],null);
            canvas.drawBitmap(obstaculo2,obsx[i],obsyabajo[i],null);
        }
        canvas.drawText(ptsString+puntos,2000,200,paint1);
        //en este if se dibuja el contador de inicio que dura 3 segundos
        if(descanso){
            canvas.drawText(String.valueOf(contador),centroX,centroY-20,paint);
        }
    }
    public void pantallaresultados(){
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
                        i.putExtra("juego","flappybird");
                       // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        corriendo =false;
        try { juego.join(); } catch (InterruptedException e) {}
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!descanso){
            if (event.getAction()==MotionEvent.ACTION_DOWN){
                MediaPlayer volar=MediaPlayer.create(activity,R.raw.efectosalto);
                volar.start();
                vely=-25;
            }
        }
        return true;
    }
}
