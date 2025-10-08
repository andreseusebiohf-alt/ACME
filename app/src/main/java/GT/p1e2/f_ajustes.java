package GT.p1e2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class f_ajustes extends DialogFragment {

     Context context;
    TextView nonmbre_tv;
    EditText nombre_et;
    Button cnombre_btt;
    CheckBox musica_cb;
    ImageView cerrar_iv;
    audio a=new audio(context);
    menu activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view= inflater.inflate(R.layout.lyf_ajustes, container,false);

         nonmbre_tv=view.findViewById(R.id.tv_n);
         nombre_et= view.findViewById(R.id.et_n);
         cnombre_btt=view.findViewById(R.id.btt_cn);
         musica_cb=view.findViewById(R.id.cb_m);
         cerrar_iv=view.findViewById(R.id.iv_s);
        variables_g g1=new variables_g(requireContext());
         musica_cb.setChecked(g1.o_musica());
         nonmbre_tv.setText(g1.o_nombre());
         nombre();
        cerrar();
        m();
        activity= (menu) getActivity();

        return view;
    }
    public void nombre(){
        cnombre_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variables_g g1=new variables_g(requireContext());
                String nm=nombre_et.getText().toString();
                if (nm.equals("")){
                    Toast.makeText(requireContext(), "Campo vacio", Toast.LENGTH_SHORT).show();
                }else {
                    g1.d_nombre(nm);
                    nonmbre_tv.setText(nm);
                    if (activity!=null){
                        activity.metodonombre();
                    }
                    nombre_et.setText("");
                }
                if (activity!=null){
                    activity.imagenessecretas();
                }
            }
        });
    }

    public void cerrar(){
        //metodo que cierra la ventana de ajustes
        cerrar_iv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
             dismiss();
            }
        });
    }

    public void m(){
        musica_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                variables_g g1=new variables_g(requireContext());
                if(isChecked)
                {
                    a.audio1(requireContext());
                }    else {
                    a.pause();
                    g1.n_musica(false);
                }
            }
        });

    }
}
