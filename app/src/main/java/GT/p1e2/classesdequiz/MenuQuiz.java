package GT.p1e2.classesdequiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import GT.p1e2.R;
import GT.p1e2.variables_g;

public class MenuQuiz extends Fragment {

    //Declaracion de componentes y variables
    TextView Menu;
    ImageButton Mate;
    ImageButton Geo;
    ImageButton Ing;
    ImageButton HistUni;
    ImageButton CulPop;
    ImageButton Gram;
    ImageButton CN;
    ImageButton Dep;

    variables_g v1;

    int quest=0;

    public MenuQuiz() {
    }

    //Metodo que hace visible el fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Instanciando los componentes
        Menu = view.findViewById(R.id.textmenu);
        Mate = view.findViewById(R.id.Mate);  //1
        Geo = view.findViewById(R.id.Geo);  //2
        Ing = view.findViewById(R.id.Ing);  //3
        HistUni = view.findViewById(R.id.HistUni);  //4
        CulPop = view.findViewById(R.id.CulPop);  //5
        Gram = view.findViewById(R.id.Gram);  //6
        CN = view.findViewById(R.id.CN);  //7
        Dep = view.findViewById(R.id.Dep);  //8

        v1= new variables_g(requireContext());

        Mate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quest= 1;
                pasarfragment();
            }
        });

        Geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quest=2;
                pasarfragment();
            }
        });

        Ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quest=3;
                pasarfragment();
            }
        });

        HistUni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quest=4;
                pasarfragment();
            }
        });

        CulPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quest=5;
                pasarfragment();
            }
        });

        Gram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quest=6;
                pasarfragment();
            }
        });

        CN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quest=7;
                pasarfragment();
            }
        });

        Dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quest=8;
                pasarfragment();
            }
        });
    }
    public void pasarfragment() {
        if (v1.quiz_elegido()==1){
            quizprima quizfragment = new quizprima();
            Bundle args = new Bundle();
            args.putInt("quest", quest);
            quizfragment.setArguments(args);
            sel(quizfragment);
        }
       if (v1.quiz_elegido()==2){
            quizglobal quizfragment = new quizglobal();
            Bundle args = new Bundle();
            args.putInt("quest", quest);
            quizfragment.setArguments(args);
            sel(quizfragment);
        }
        if (v1.quiz_elegido()==3){
            quizprepa quizfragment = new quizprepa();
            Bundle args = new Bundle();
            args.putInt("quest", quest);
            quizfragment.setArguments(args);
            sel(quizfragment);
        }
        if (v1.quiz_elegido()==4){
            quizuni quizfragment = new quizuni();
            Bundle args = new Bundle();
            args.putInt("quest", quest);
            quizfragment.setArguments(args);
            sel(quizfragment);
        }
        if (v1.quiz_elegido()==5){

        }

    }

    public void sel(Fragment f){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.HojaPrincipal,f).commit();
    }
}