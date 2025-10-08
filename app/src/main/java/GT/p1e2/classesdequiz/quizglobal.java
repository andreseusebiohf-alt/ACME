package GT.p1e2.classesdequiz;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import GT.p1e2.R;
import GT.p1e2.variables_g;

/*en la carpeta assets crea una carpeta para guardar los txt, crea una nueva clase de java en la carpeta de classesdequiz
de preferencia que se llame parecido a esta clase, solo crea la clase sin el layout
*  ya ahi copias y pegas este codigo, en la linea 253 hay otro comentario, leelo*/

public class quizglobal extends Fragment {

    int quest;
    String seleccion,seleccion2;
    String keyobjeto,quizelegido;
    TextView preguntaele,numeropregunta;
    int numeropgnttxt=1;
    int pregunta=-1;
    int elegir=0,elegir2;
    RadioButton[] radioButtons=new RadioButton[4];
    int contador=0;
    List<String> lista2;
    RadioGroup grupoderb;
    ProgressBar barrapro;
    String preguntastxt,respuestastxt;

    TextView txt2,txt3,txt4;
    Button btnR,btnO;
    ImageView im1;

    public quizglobal(){}

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        });
        return inflater.inflate(R.layout.layout_quizgeneral,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            quest = getArguments().getInt("quest", 2);
        }
        radioButtons[0]=view.findViewById(R.id.rb1);
        radioButtons[1]=view.findViewById(R.id.rb2);
        radioButtons[2]=view.findViewById(R.id.rb3);
        radioButtons[3]=view.findViewById(R.id.rb4);

        preguntaele=view.findViewById(R.id.preguntaelegida);
        numeropregunta=view.findViewById(R.id.numeropregunta);
        grupoderb=view.findViewById(R.id.rg1);
        barrapro=view.findViewById(R.id.brrprogreso);

        im1=view.findViewById(R.id.im1);

        barrapro.setMax(100);

        elegirquiz();
        cmpbr_respuesta();
    }
    public void obtener(){
        try {
            InputStream archivo=requireContext().getAssets().open(preguntastxt);
            BufferedReader palabraarchivo=new BufferedReader(new InputStreamReader(archivo));
            List<String> lista=new ArrayList<>();
            String linea;
            while ((linea=palabraarchivo.readLine())!=null){
                lista.add(linea);
            }
            palabraarchivo.close();
            pregunta++;
            seleccion=lista.get(pregunta);
            preguntaele.setText(seleccion);
            numeropregunta.setText("Pregunta "+numeropgnttxt);
            numeropgnttxt++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void cargarespuestas(){
        try {
            InputStream archivo=requireContext().getAssets().open(respuestastxt);
            BufferedReader palabraarchivo=new BufferedReader(new InputStreamReader(archivo));
            lista2=new ArrayList<>();
            String linea;
            while ((linea=palabraarchivo.readLine())!=null){
                lista2.add(linea);
            }
            palabraarchivo.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void respuestas(){
        for (int i=0;i<=3;i++){
            while (radioButtons[i].getText().toString().isEmpty()){
                seleccion2=lista2.get(numeroAleatorioEntre(elegir,elegir2));
                int x=3;
                for (int j=0;j<=3;j++){
                    if (radioButtons[j].getText().toString().equals(seleccion2)){
                        x--;
                    }
                }
                if (x==3){
                    radioButtons[i].setText(seleccion2);
                }
            }
        }
    }
    public int numeroAleatorioEntre(int minimo, int maximo) {
        return (int)(Math.random() * (maximo - minimo + 1)) + minimo;
    }

    public void cmpbr_respuesta() {
        grupoderb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == -1) return;

                RadioButton respuesta = group.findViewById(checkedId);
                if (respuesta != null && respuesta.getText().toString().equals(lista2.get(elegir))) {
                    contador++;
                }
                for (int i=0;i<=3;i++){
                    radioButtons[i].setText("");
                }
                grupoderb.clearCheck();
                barra();
                if (pregunta==9){
                    dialogShow();
                }else {
                    inicio();
                }
            }
        });
    }
    public void barra() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                barrapro.setProgress(barrapro.getProgress() + 10);
            }
        }
        ).start();
    }

    public void dialogShow() {
        String j = String.valueOf(contador);
        AlertDialog.Builder b = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.resultado, null);

        txt2 = v.findViewById(R.id.txt2);
        txt3 = v.findViewById(R.id.txt3);
        txt4 = v.findViewById(R.id.txt4);

        txt2.setText(quizelegido);
        txt3.setText(j + " /10");
        txt4.setText("¡Felicidades!");
        btnR = v.findViewById(R.id.btnReg);
        btnO = v.findViewById(R.id.btnOk);
        TextView txtDesbloqueo;
        txtDesbloqueo=v.findViewById(R.id.textObjeto);
        if (contador==10){
            txtDesbloqueo.setVisibility(View.VISIBLE);
            variables_g v1=new variables_g(requireContext());
            v1.g_valor(keyobjeto);
        }

        b.setView(v);

        AlertDialog al = b.create();
        al.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        al.setCancelable(false);

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador=0;
                al.cancel();
                quizglobal quizfragment = new quizglobal();
                Bundle args = new Bundle();
                args.putInt("quest", quest);
                quizfragment.setArguments(args);
                Cambiar(quizfragment);
            }
        });
        btnO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                al.cancel();
                Cambiar(new MenuQuiz());
            }
        });
        Invisible();
        al.show();
    }

    public void Cambiar(Fragment f) {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.HojaPrincipal, f).commit();
    }

    public void Invisible(){
        View v = new View(requireContext());
        im1.setVisibility(v.GONE);
        grupoderb.setVisibility(v.GONE);
        barrapro.setVisibility(v.GONE);
        preguntaele.setVisibility(v.GONE);
        numeropregunta.setVisibility(v.GONE);
    }
    public  void  inicio(){
        obtener();
        elegir=elegir+4;
        elegir2=elegir+3;
        respuestas();
    }
    public void elegirquiz(){
        switch (quest){
            case 1:
                //deja la parte de key vacia, y pon la direccion de acuerdo a el nombre de la carpeta y el archivo, al final pon el .txt
                keyobjeto="Mat";
                quizelegido="Quiz Matematico";
                preguntastxt= "nivel secundaria/preg_matematicas.txt";
                respuestastxt= "nivel secundaria/res_matematicas.txt";
                break;
            case 2:
                keyobjeto="Geo";
                quizelegido="Quiz Geografia";
                preguntastxt= "nivel secundaria/preg_geografia.txt";
                respuestastxt= "nivel secundaria/res_geografia.txt";
                break;
            case 3:
                keyobjeto="Ing";
                quizelegido="Quiz Ingles";
                preguntastxt= "nivel secundaria/preg_ingles.txt";
                respuestastxt= "nivel secundaria/res_ingles.txt";
                break;
            case 4:
                keyobjeto="HU";
                quizelegido="Quiz HistoriaUniversal";
                preguntastxt= "nivel secundaria/preg_historiauniversal.txt";
                respuestastxt= "nivel secundaria/res_historiauniversal.txt";
                break;
            case 5:
                keyobjeto="CP";
                quizelegido="Quiz CulturaPopular";
                preguntastxt= "nivel secundaria/preg_culturapopular.txt";
                respuestastxt= "nivel secundaria/res_culturapopular.txt";
                break;
            case 6:
                keyobjeto="Gra";
                quizelegido="Quiz Gramatica";
                preguntastxt= "nivel secundaria/preg_gramatica.txt";
                respuestastxt= "nivel secundaria/res_gramatica.txt";
                break;
            case 7:
                keyobjeto="CN";
                quizelegido="Quiz CienciasNaturales";
                preguntastxt= "nivel secundaria/preg_cienciasnaturales.txt";
                respuestastxt= "nivel secundaria/res_cienciasnaturales.txt";
                break;
            case 8:
                keyobjeto="Dep";
                quizelegido="Quiz Deportes";
                preguntastxt= "nivel secundaria/preg_deportes.txt";
                respuestastxt= "nivel secundaria/res_deportes.txt";
                break;
        }
        cargarespuestas();
        inicio();
    }
}