package mx.edu.ittepic.baltazar_ramirez_francisco_javier_inmobiliaria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Inmobiliaria extends SQLiteOpenHelper
{
    public Inmobiliaria(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override //onCreate signigica que se ejecuta cuando se abre la aplicacion
    public void onCreate(SQLiteDatabase db) { //SQLiteDatabase db este es el parametro para ejecutar. SQLiteDatabase esta es capas de realizar cualquier tipo de transaccion que nosotros conoscamos
        //Se ejecuta cuando la aplicacion (DADM_U4_Practica1) se ejecuta en el CEL
        //Sirve para construir en el SQLITE que esta en el CEL las tablas que la APP requiere para funcionar
        //Inserta datos de SQL que nosotros conoscamos con los comandos que conocemos
        db.execSQL("CREATE TABLE PROPIETARIO(ID INTEGER PRIMARY KEY, NOMBRE VARCHAR(200), DOMICILIO VARCHAR(500),TELEFONO VARCHAR(1000))");//esta instruccion puede realizartodo mennos el selecct, FUNCIONA PARA insert,create_table,delete,update.
        db.execSQL("CREATE TABLE INMUEBLE(IDM INTEGER PRIMARY KEY, DOMICILIO VARCHAR(200), PRECIOVENTA FLOAT,PRECIORENTA FLOAT,FECHATRANSACCION DATE,ID INTEGER,FOREIGN KEY(ID) REFERENCES PROPIETARIO(ID))");
        //CHAR -> Se utiliza y si no se ocupatodo los demas espacios se ocupan con algo
        //VARCHAR -> Se utiliza solo lo que se va a iimplementar
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//adbdate acuatilizaciones menores y //adgrade actualizacion mayo (modificando datos al modificar las tablas y eso)
        //Las versiones no se decrementan
        //se ejecuta cuando el oncreate crea las tablas, toda la alteracion
        //Solo se puede subir de actualizacion y no se puede poner una actualizacion menor a la actual
    }
}
