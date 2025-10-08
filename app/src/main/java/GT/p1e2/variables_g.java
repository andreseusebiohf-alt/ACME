package GT.p1e2;

import android.content.Context;
import android.content.SharedPreferences;


public class variables_g{

    private static final String COM_REF="micds";

    private static final String PRI_COM="pc";
    //variables del valor de cantidad de las comidas
    private static final String COM_KEY1="cd1";
    private static final String COM_KEY2="cd2";
    private static final String COM_KEY3="cd3";
    private static final String COM_KEY4="cd4";
    private static final String COM_KEY5="cd5";
    private static final String COM_KEY6="cd6";


    private static final String COM_GUAR="cd7";
    private static final String Val_Mus="cd8";
    private static final String NOM_VAL="vNC";
    //valores de desbloqueo de los trajes
    private static final String quizCN="CN";
    private static final String quizCP="CP";
    private static final String quizDep="Dep";
    private static final String quizGeo="Geo";
    private static final String quizGra="Gra";
    private static final String quizHU="HU";
    private static final String quizIng="Ing";
    private static final String quizMat="Mat";

    //valor de elegido de las trajes
    private static final String trajeCN="llave7";
    private static final String trajeCP="llave5";
    private static final String trajeDep="llave8";
    private static final String trajeGeo="llave2";
    private static final String trajeGra="llave6";
    private static final String trajeHU="llave4";
    private static final String trajeIng="llave3";
    private static final String trajeMat="llave1";

    private static final String Edad="Edad";

    private  static  final String QuizElegido="QuizElegido";

    SharedPreferences v_c;
    SharedPreferences.Editor v_ce;

    //metodo de inicio
    public variables_g(Context context){
        v_c=context.getSharedPreferences(COM_REF,context.MODE_PRIVATE);
        v_ce=v_c.edit();
    }

    //metodo que revisa si es la primera vez de la ejecucion
    public boolean primerinicio(){
     return v_c.getBoolean(PRI_COM,false);
    }
    // revisa el metodo primerinicio y si es true da unos valores predeterminados
    public void iniciopredeterminado(){
        if(!primerinicio()){
            v_ce.putInt(COM_KEY1,5);
            v_ce.apply();
            v_ce.putInt(COM_KEY2,3);
            v_ce.apply();
            v_ce.putInt(COM_KEY3,1);
            v_ce.apply();
            //cambia el valor de true a false pra que no se ejecute de nuevo
            v_ce.putBoolean(PRI_COM,true);
            v_ce.apply();
        }

    }
    //metodo para obtener el numero de la comida comida
    public int o_ncomida(String key){
        return v_c.getInt(key,0);
    }
    //metodo para guardar la comida
    public void g_ncomida(String key, int valor){
        v_ce.putInt(key,valor);
        v_ce.apply();
    }
//metodos parar obtener y guardar el valor de la musica
    public boolean o_musica(){
        return v_c.getBoolean("cd8",false);
    }
    public void n_musica(boolean r){
        v_ce.putBoolean("cd8",r);
        v_ce.apply();
    }


    //metodos para guardar y obtener el nombre de la mascota
    public String o_nombre(){
        return  v_c.getString("vNC","Fred");
    }
    public void d_nombre(String nc){
        v_ce.putString("vNC",nc);
        v_ce.apply();
    }
    //metodos para guardar los valores de si es desbloqueado el objeto
    public Boolean o_valor(String llave){
        return v_c.getBoolean(llave,false);
    }
    public void g_valor(String llave){
        v_ce.putBoolean(llave,true);
        v_ce.apply();
    }
    //metodos para obtener y guradar el valor de si es elegida la gorra
    public Boolean o_valor_g(String valorg){
        return v_c.getBoolean(valorg,false);
    }
    public void g_valor_g(String gvalorg,boolean valorgg){
        v_ce.putBoolean(gvalorg,valorgg);
        v_ce.apply();
    }
    //metodo para guardar la edad del usuario
    public int o_valor_edad(){
        return v_c.getInt("Edad",18);
    }
    public void g_valor_edad(int valorgg){
        v_ce.putInt("Edad",valorgg);
        v_ce.apply();
    }

    //metodo que guarda el quiz elegido en otra clase
    public int quiz_elegido(){
        return v_c.getInt("QuizElegido",1);
    }
    public void guardar_quiz_elegido(int valorgg){
        v_ce.putInt("QuizElegido",valorgg);
        v_ce.apply();
    }
}
