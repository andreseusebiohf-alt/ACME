package GT.p1e2.classesdequiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import GT.p1e2.R;


public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        cambio(new MenuQuiz());
    }

    public void cambio (Fragment fg){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.HojaPrincipal,fg).commit();
    }
}