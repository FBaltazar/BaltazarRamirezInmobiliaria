package mx.edu.ittepic.baltazar_ramirez_francisco_javier_inmobiliaria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button btnInmueble,btnPropietario;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInmueble = findViewById(R.id.inmuebles);
        btnPropietario = findViewById(R.id.propietarillo);

        btnPropietario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent ven = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(ven);
            }
        });

        btnInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent ven = new Intent(MainActivity.this, Principal.class);
                startActivity(ven);
            }
        });

    }
}
