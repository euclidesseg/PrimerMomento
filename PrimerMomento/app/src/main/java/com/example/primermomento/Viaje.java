package com.example.primermomento;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class Viaje extends SQLiteOpenHelper {

    public Viaje(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tblviaje(codViaje text primary key," +
                " ciudadDestino text not null, cantidadPersonas iteger not null, valorPersona integer not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE tblviaje");{
            onCreate(db);
        }
    }

}
