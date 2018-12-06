package mx.edu.ittepic.baltazar_ramirez_francisco_javier_inmobiliaria;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Principal extends AppCompatActivity
{
    EditText idinmueble,domicilioInmueble,precioVenta,precioRenta,fechaTransaccion;
    private Button insertar,consultar,eliminar,actualizar,limpiars;
    Spinner spinner;
    String [] idp = new String[1000];
    Inmobiliaria base;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        idinmueble = findViewById(R.id.idinmueble);
        domicilioInmueble = findViewById(R.id.domicilioInmueble);
        precioVenta = findViewById(R.id.precioVenta);
        precioRenta = findViewById(R.id.precioVenta);
        fechaTransaccion = findViewById(R.id.fechaTransaccion);

        spinner = findViewById(R.id.idp);

        insertar=findViewById(R.id.btnInsertar);
        consultar=findViewById(R.id.btnConsultar);
        eliminar=findViewById(R.id.btnEliminar);
        actualizar=findViewById(R.id.btnActualizar);

        limpiars=findViewById(R.id.btnLimpiar);

        base = new Inmobiliaria(this,"primera",null,1);

        SQLiteDatabase tabla = base.getReadableDatabase();
        String SQL = "SELECT ID, NOMBRE FROM PROPIETARIO";
        Cursor resultado = tabla.rawQuery(SQL, null);
        int i = 0;
        if (resultado.moveToFirst())
        {
            do {
                idp[i] = resultado.getString(0) + ": " + resultado.getString(1);
                resultado.moveToPosition(1);
                i++;
            }while (resultado.moveToNext());
        }
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,idp));

        insertar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                codigoInsertar();
            }
        });

        consultar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //pedirID(1);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (actualizar.getText().toString().startsWith("CONFIRMAR ACTUALIZACION")){
                    //invocaConfirmacionActualizacion();
                }else{
                  //  pedirID(2);
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //pedirID(3);
            }
        });

        limpiars.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              //  habilitaBotonesYLimpiarCampos();
            }
        });

    }

    private void codigoInsertar()
    {

        try {
            if (idinmueble.getText().toString().isEmpty()) {
                Toast.makeText(this, "INGRESE ID", Toast.LENGTH_LONG).show();
                return;
            }
            if (!idRepe(idinmueble.getText().toString())) {
                String[] fecha = fechaTransaccion.getText().toString().split("/");
                if (!(fecha[0].length() == 4 && fecha[1].length() <= 2 && fecha[2].length() <= 2)) {
                    Toast.makeText(this, "Escribe la fecha (Año/Mes/Dia)", Toast.LENGTH_LONG).show();
                    fechaTransaccion.setText("");
                    return;
                }
                String[] id = spinner.getSelectedItem().toString().split(":");
                SQLiteDatabase tabla = base.getWritableDatabase();
                String SQL = "INSERT INTO INMUEBLE VALUES(1,'%2',3,4,5,6)";
                SQL = SQL.replace("1", idinmueble.getText().toString());
                SQL = SQL.replace("%2", domicilioInmueble.getText().toString());
                SQL = SQL.replace("3", precioVenta.getText().toString());
                SQL = SQL.replace("4", precioRenta.getText().toString());
                SQL = SQL.replace("5", fechaTransaccion.getText().toString());
                SQL = SQL.replace("6", id[0]);
                tabla.execSQL(SQL);

                Toast.makeText(this, "Transacción Exitosa", Toast.LENGTH_LONG).show();
                tabla.close();
                // habilitaBotonesYLimpiarCampos();
            } else {
                Toast.makeText(this,"ID ya Existe, Ingrese un Nuevo",Toast.LENGTH_LONG).show();
            }
        }
            catch (SQLiteException e) {
                Toast.makeText(this, "Fallo la Transacción", Toast.LENGTH_LONG).show();
            }
    }

    private boolean idRepe(String idBuscar)
    {
        SQLiteDatabase tabla = base.getReadableDatabase();
        String SQL = "SELECT *FROM INMUEBLE WHERE ID="+idBuscar;

        Cursor resultado = tabla.rawQuery(SQL,null);

        if (resultado.moveToFirst())
        {
            return true;
        }
        return false;
    }


}
