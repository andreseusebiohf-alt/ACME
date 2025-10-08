package GT.p1e2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class armarioSombreros extends DialogFragment {

    TextView txtArm;
    GridLayout contenedorImg;
    ImageView img1, img2, img3, img4, img5, img6, img7, img8;
    ScrollView bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view= inflater.inflate(R.layout.lyf_armario, container,false);

        //Referencia a los componentes
        txtArm = view.findViewById(R.id.txtArm);
        contenedorImg = view.findViewById(R.id.ContenedorImg);
        bar = view.findViewById(R.id.bar);
        img1 = view.findViewById(R.id.ImgMate);
        img2 = view.findViewById(R.id.ImgIng);
        img3 = view.findViewById(R.id.ImgHistUni);
        img4 = view.findViewById(R.id.ImgGram);
        img5 = view.findViewById(R.id.ImgGeo);
        img6 = view.findViewById(R.id.ImgDep);
        img7 = view.findViewById(R.id.ImgCultPop);
        img8 = view.findViewById(R.id.ImgCN);

        //llamada al metodo click
        click();
        return view;
    }

    //Metodo que permite dar click a las imagenes y muestra un dialogo en la pantalla
    public void click() {
        variables_g v1=new variables_g(requireContext());
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (v1.o_valor("Mat")){
                 img1.setImageResource(R.drawable.imgitca);
                  v1.g_valor_g("llave1",true);

                  img8.setImageResource(R.drawable.imgclash);
                  img2.setImageResource(R.drawable.imgzelda);
                  img3.setImageResource(R.drawable.imgluigi);
                  img4.setImageResource(R.drawable.imgmario);
                  img5.setImageResource(R.drawable.imgperu);
                  img6.setImageResource(R.drawable.imgmexa);
                  img7.setImageResource(R.drawable.imgwicked);
                  elegir(1);

              }else{
                  //Cuadro de dilogo, este se desactiva al dar unicamente click en el boton ok
                  AlertDialog d = new AlertDialog.Builder(requireContext())
                          .setTitle("Objeto 1")
                          .setMessage("bloqueado")
                          .setPositiveButton("Ok", (dialog, which) -> {
                              dialog.dismiss();
                          })
                          .show();
                  d.setCancelable(false);
                  d.setCanceledOnTouchOutside(false);
              }
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v1.o_valor("Geo")){
                   img2.setImageResource(R.drawable.imgzelda);
                    v1.g_valor_g("llave2",true);

                    img1.setImageResource(R.drawable.imgitca);
                    img8.setImageResource(R.drawable.imgclash);
                    img3.setImageResource(R.drawable.imgluigi);
                    img4.setImageResource(R.drawable.imgmario);
                    img5.setImageResource(R.drawable.imgperu);
                    img6.setImageResource(R.drawable.imgmexa);
                    img7.setImageResource(R.drawable.imgwicked);
                    elegir(2);

                }else {
                    AlertDialog d = new AlertDialog.Builder(requireContext())
                            .setTitle("Objeto 2")
                            .setMessage("bloqueado")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                    d.setCancelable(false);
                    d.setCanceledOnTouchOutside(false);
                }
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v1.o_valor("Ing")){
                    img3.setImageResource(R.drawable.imgluigi);
                    v1.g_valor_g("llave3",true);

                    img1.setImageResource(R.drawable.imgitca);
                    img2.setImageResource(R.drawable.imgzelda);
                    img8.setImageResource(R.drawable.imgclash);
                    img4.setImageResource(R.drawable.imgmario);
                    img5.setImageResource(R.drawable.imgperu);
                    img6.setImageResource(R.drawable.imgmexa);
                    img7.setImageResource(R.drawable.imgwicked);
                    elegir(3);
                }else {
                    AlertDialog d = new AlertDialog.Builder(requireContext())
                            .setTitle("Objeto 3")
                            .setMessage("bloqueado")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                    d.setCancelable(false);
                    d.setCanceledOnTouchOutside(false);
                }
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v1.o_valor("HU")){
                    img4.setImageResource(R.drawable.imgmario);
                    v1.g_valor_g("llave4",true);

                    img1.setImageResource(R.drawable.imgitca);
                    img2.setImageResource(R.drawable.imgzelda);
                    img3.setImageResource(R.drawable.imgluigi);
                    img8.setImageResource(R.drawable.imgclash);
                    img5.setImageResource(R.drawable.imgperu);
                    img6.setImageResource(R.drawable.imgmexa);
                    img7.setImageResource(R.drawable.imgwicked);
                    elegir(4);
                }else {
                    AlertDialog d = new AlertDialog.Builder(requireContext())
                            .setTitle("Objeto 4")
                            .setMessage("bloqueado")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                    d.setCancelable(false);
                    d.setCanceledOnTouchOutside(false);
                }
            }
        });

        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v1.o_valor("CP")){
                    img5.setImageResource(R.drawable.imgperu);
                    v1.g_valor_g("llave5",true);

                    img1.setImageResource(R.drawable.imgitca);
                    img2.setImageResource(R.drawable.imgzelda);
                    img3.setImageResource(R.drawable.imgluigi);
                    img4.setImageResource(R.drawable.imgmario);
                    img8.setImageResource(R.drawable.imgclash);
                    img6.setImageResource(R.drawable.imgmexa);
                    img7.setImageResource(R.drawable.imgwicked);
                    elegir(5);

                }else {
                    AlertDialog d = new AlertDialog.Builder(requireContext())
                            .setTitle("Objeto 5")
                            .setMessage("bloqueado")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                    d.setCancelable(false);
                    d.setCanceledOnTouchOutside(false);
                }
            }
        });

        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v1.o_valor("Gra")){
                    img6.setImageResource(R.drawable.imgmexa);
                    v1.g_valor_g("llave6",true);

                    img1.setImageResource(R.drawable.imgitca);
                    img2.setImageResource(R.drawable.imgzelda);
                    img3.setImageResource(R.drawable.imgluigi);
                    img4.setImageResource(R.drawable.imgmario);
                    img5.setImageResource(R.drawable.imgperu);
                    img8.setImageResource(R.drawable.imgclash);
                    img7.setImageResource(R.drawable.imgwicked);
                    elegir(6);

                }else {
                    AlertDialog d = new AlertDialog.Builder(requireContext())
                            .setTitle("Objeto 6")
                            .setMessage("bloqueado")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                    d.setCancelable(false);
                    d.setCanceledOnTouchOutside(false);
                }
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v1.o_valor("CN")){
                    img7.setImageResource(R.drawable.imgwicked);
                    v1.g_valor_g("llave7",true);

                    img1.setImageResource(R.drawable.imgitca);
                    img2.setImageResource(R.drawable.imgzelda);
                    img3.setImageResource(R.drawable.imgluigi);
                    img4.setImageResource(R.drawable.imgmario);
                    img5.setImageResource(R.drawable.imgperu);
                    img6.setImageResource(R.drawable.imgmexa);
                    img8.setImageResource(R.drawable.imgclash);
                    elegir(7);

                }else {
                    AlertDialog d = new AlertDialog.Builder(requireContext())
                            .setTitle("Objeto 7")
                            .setMessage("bloqueado")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                    d.setCancelable(false);
                    d.setCanceledOnTouchOutside(false);
                }
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v1.o_valor("Dep")){
                    Log.i("armario","if inicial");
                    img8.setImageResource(R.drawable.imgclash);

                    img1.setImageResource(R.drawable.imgitca);
                    img2.setImageResource(R.drawable.imgzelda);
                    img3.setImageResource(R.drawable.imgluigi);
                    img4.setImageResource(R.drawable.imgmario);
                    img5.setImageResource(R.drawable.imgperu);
                    img6.setImageResource(R.drawable.imgmexa);
                    img7.setImageResource(R.drawable.imgwicked);
                    elegir(8);

                }else {
                    AlertDialog d = new AlertDialog.Builder(requireContext())
                            .setTitle("Objeto 8")
                            .setMessage("bloqueado")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                    d.setCancelable(false);
                    d.setCanceledOnTouchOutside(false);
                }
            }
        });
    }
    public void elegir(int r){
        variables_g v1=new variables_g(requireContext());
        Log.i("armario","metodo elegir");
        for (int i=1;i<=8;i++){
            if (i==r){
                v1.g_valor_g("llave"+i,true);
                Log.i("armario","valor "+i);
            }else{
                Log.i("armario","else de decicion ");
             v1.g_valor_g("llave"+i,false);
            }

        }
        ((menu)getActivity()).comprobacion();
        dismiss();
    }
}
