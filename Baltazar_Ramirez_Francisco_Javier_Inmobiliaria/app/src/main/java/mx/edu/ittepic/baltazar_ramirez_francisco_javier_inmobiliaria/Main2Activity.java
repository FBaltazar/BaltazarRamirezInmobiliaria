package mx.edu.ittepic.baltazar_ramirez_francisco_javier_inmobiliaria;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
{
    EditText idp,nombrePropietario,domicilioPropietario,telefonoPropietario;
    private Button insertar,consultar,eliminar,actualizar,limpiars;
    Inmobiliaria base;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        idp = findViewById(R.id.idp);
        nombrePropietario = findViewById(R.id.nombrePropietario);
        domicilioPropietario = findViewById(R.id.domicilioPropietario);
        telefonoPropietario = findViewById(R.id.telefonoPropietario);

        insertar=findViewById(R.id.btnInsertar);
        consultar=findViewById(R.id.btnConsultar);
        eliminar=findViewById(R.id.btnEliminar);
        actualizar=findViewById(R.id.btnActualizar);

        limpiars=findViewById(R.id.btnLimpiar);

        base = new Inmobiliaria(this,"primera",null,1);

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
                pedirID(1);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (actualizar.getText().toString().startsWith("CONFIRMAR ACTUALIZACION")){
                    invocaConfirmacionActualizacion();
                }else{
                    pedirID(2);
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pedirID(3);
            }
        });

        limpiars.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                habilitaBotonesYLimpiarCampos();
            }
        });
    }

    private void codigoInsertar()
    {
        try
        {
            SQLiteDatabase tabla = base.getWritableDatabase();

            String SQL = "INSERT INTO PROPIETARIO VALUES(1,'%2','%3','%4')";
            SQL = SQL.replace("1", idp.getText().toString());
            SQL = SQL.replace("%2", nombrePropietario.getText().toString());
            SQL = SQL.replace("%3", domicilioPropietario.getText().toString());
            SQL = SQL.replace("%4", telefonoPropietario.getText().toString());
            tabla.execSQL(SQL);

            Toast.makeText(this,"Transacción Exitosa",Toast.LENGTH_LONG).show();
            tabla.close();
            habilitaBotonesYLimpiarCampos();
        }catch (SQLiteException e)
        {
            Toast.makeText(this,"Fallo la Transacción",Toast.LENGTH_LONG).show();
        }
    }

    private void buscarDato(String idaBuscar, int origen)
    {
        try
        {
            SQLiteDatabase tabla = base.getReadableDatabase();

            String SQL = "SELECT *FROM PROPIETARIO WHERE ID="+idaBuscar;

            Cursor resultado = tabla.rawQuery(SQL,null);
            if(resultado.moveToFirst())
            {
                if(origen==3)
                {
                    String dato = idaBuscar+"&"+ resultado.getString(1)+"&"+resultado.getString(2)+
                            "&"+resultado.getString(3);
                    invocaConfirmacionEliminacion(dato);
                    return;
                }

                idp.setText(resultado.getString(0));
                nombrePropietario.setText(resultado.getString(1));
                domicilioPropietario.setText(resultado.getString(2));
                telefonoPropietario.setText(resultado.getString(3));
                if(origen==2)
                {
                    insertar.setEnabled(false);
                    consultar.setEnabled(false);
                    eliminar.setEnabled(false);
                    actualizar.setText("CONFIRMAR ACTUALIZACION");
                    idp.setEnabled(false);
                }
            }else {
                Toast.makeText(this,"No se ENCONTRO EL RESULTADO",Toast.LENGTH_LONG).show();
            }
            tabla.close();
        }
        catch (SQLiteException e)
        {
            Toast.makeText(this,"No se Logro la Busqueda",Toast.LENGTH_LONG).show();
        }
    }

    private void pedirID(final int origen)
    {
        final EditText pidoID = new EditText(this);
        pidoID.setInputType(InputType.TYPE_CLASS_NUMBER);
        pidoID.setHint("Valor Entero Mayor a 0");
        String mensaje ="Escriba el ID a Buscar";

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        if(origen ==2) mensaje ="Ecriba el ID a Modificar";

        if(origen ==3)mensaje ="Escriba que Desea Eliminar";

        alerta.setTitle("Atención").setMessage(mensaje)
                .setView(pidoID)
                .setPositiveButton("Buscar", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(pidoID.getText().toString().isEmpty())
                        {
                            Toast.makeText(Main2Activity.this,"Introduce Solo Números",Toast.LENGTH_LONG).show();
                            return;
                        }
                        buscarDato(pidoID.getText().toString(), origen);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar",null).show();
    }

    private void invocaConfirmacionEliminacion(String dato)
    {
        String datos[] = dato.split("&");
        final String id = datos[0];
        String nombre = datos[1];

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Atención").setMessage("Deseas Eliminar al Usuario: "+nombre)
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        eliminarIDTodo(id);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar",null).show();
    }

    private void eliminarIDTodo(String idEliminar)
    {
        try
        {
            SQLiteDatabase tabla = base.getReadableDatabase();

            String SQL = "DELETE FROM PROPIETARIO WHERE ID=" + idEliminar;
            tabla.execSQL(SQL);
            tabla.close();

            Toast.makeText(this, "Dato Eliminado", Toast.LENGTH_LONG).show();
        }
        catch (SQLiteException e)
        {
            Toast.makeText(this, "No se Elimino", Toast.LENGTH_LONG).show();
        }

    }

    private void invocaConfirmacionActualizacion()
    {
        AlertDialog.Builder confir = new AlertDialog.Builder(this);
        confir.setTitle("IMPORTNATE").setMessage("¿Seguro de Aplicar Cambios?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        aplicarActualizar();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                habilitaBotonesYLimpiarCampos();
                dialog.cancel();
            }
        }).show();
    }

    private void aplicarActualizar(){
        try
        {
            SQLiteDatabase tabla = base.getWritableDatabase();

            String SQL= "UPDATE PROPIETARIO SET NOMBRE='"+
                    nombrePropietario.getText().toString()+"', " +
                    "DOMICILIO='"+domicilioPropietario.getText().toString()+"',"+
                    "TELEFONO='"+telefonoPropietario.getText().toString()+
                    "' WHERE ID="+idp.getText().toString();
            tabla.execSQL(SQL);
            tabla.close();
            Toast.makeText(this,"Actualizado",Toast.LENGTH_LONG).show();

        }catch (SQLiteException e){
            Toast.makeText(this,"No se Actualizo",Toast.LENGTH_LONG).show();
        }
        habilitaBotonesYLimpiarCampos();
    }

    private void habilitaBotonesYLimpiarCampos()
    {
        idp.setText("");
        nombrePropietario.setText("");
        domicilioPropietario.setText("");
        telefonoPropietario.setText("");

        insertar.setEnabled(true);
        consultar.setEnabled(true);
        eliminar.setEnabled(true);
        actualizar.setText("ACTUALIZAR");
        idp.setEnabled(true);
    }
}
