package GT.p1e2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menujuegos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menujuegos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void juegoChrome(View view) {
        Intent i=new Intent(this,capChrome.class);
        startActivity(i);
    }

    public void juegomemorama(View view) {
        Intent i =new Intent(this,juegoMemorama.class);
        startActivity(i);
    }

    public void juegoPPT(View view) {
        Intent i=new Intent(this, juegoPPT.class);
        startActivity(i);
    }

    public void juegoflappy(View view) {
        Intent i=new Intent(this, capFlappyBird.class);
        startActivity(i);
    }

    public void juegoGato(View view) {
        Intent i=new Intent(this, juegoGato.class);
        startActivity(i);
    }

    public void juegofreefood(View view) {
        Intent i=new Intent(this, freefood.class);
        startActivity(i);
    }
}