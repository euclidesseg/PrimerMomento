package com.example.primermomento;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etcodigo, etciudad, etcantidad, etvalor;
    //conectar base de datos sql
    Viaje admin = new Viaje(this, "viaje.db", null, 1);
    long resp;
    String codigo, ciudad, cantidad, valor;
    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ocultar titulo que esta en morado.

        getSupportActionBar().hide();

        //este es para oculatar el titulo morado

        etcodigo=findViewById(R.id.etcodigo);
        etciudad=findViewById(R.id.etciudad);
        etcantidad=findViewById(R.id.etcantidad);
        etvalor=findViewById(R.id.etvalor);
        sw=0;
    }
    public void Guardar(View view){
        codigo=etcodigo.getText().toString();
        ciudad=etciudad.getText().toString();
        cantidad=etcantidad.getText().toString();
        valor=etvalor.getText().toString();
        if(codigo.isEmpty() || ciudad.isEmpty() ||
                cantidad.isEmpty() || valor.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            etcodigo.requestFocus();
        }
        else{
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("codViaje",codigo);
            registro.put("ciudadDestino",Integer.parseInt(cantidad));
            registro.put("cantidadPersonas",cantidad);
            registro.put("valorPersona",Integer.parseInt(valor));
            if (sw== 0)
                resp = db.insert("tblviaje",null,registro);
            else{
                resp = db.update("tblviaje",registro, "codViaje='"+codigo+"'", null);
                sw=0;
            }

            if (resp > 0){
                Toast.makeText(this,"registro guardado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            else
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            db.close();
        }
    }

    public void Consultar(View view){
        codigo = etcodigo.getText().toString();
        if (codigo.isEmpty()){
            Toast.makeText(this, "El codigo es requerido", Toast.LENGTH_SHORT).show();
            etcodigo.requestFocus();
        }
        else{
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery("select * from tblviaje where codViaje ='" + codigo + "'",null);
            //solo se colocan comillas simples cuando son texto.
            if (fila.moveToNext()) {
                sw=1;
                etciudad.setText(fila.getString(1));
                etcantidad.setText(fila.getString(2));
                etvalor.setText(fila.getString(3));
            }
            else
                Toast.makeText(this, "Viaje no registrado", Toast.LENGTH_SHORT).show();
           db.close();
        }
    }

    public void Anular(View view){
        codigo = etcodigo.getText().toString();
        if (sw == 0){
            Toast.makeText(this, "Primero debe de consultar", Toast.LENGTH_SHORT).show();
            etcodigo.requestFocus();
        }else{
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            resp =  db.delete("tblviaje","codViaje ='"+etcodigo.getText().toString()+ "'",null);
            if (resp>0){
                Toast.makeText(this, "Registro Anulado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            else{
                Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
                db.close();
            }
        }
    }
    private void Limpiar_campos() {
        etcodigo.setText("");
        etciudad.setText("");
        etcantidad.setText("");
        etvalor.setText("");
        sw = 0;
    }
}