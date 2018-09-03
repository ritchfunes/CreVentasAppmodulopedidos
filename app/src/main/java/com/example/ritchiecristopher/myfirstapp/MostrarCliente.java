package com.example.ritchiecristopher.myfirstapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton ;

public class MostrarCliente extends AppCompatActivity      {

    EditText et_nombre , et_descripcion ;
     Button btnguardar , btnmostrar   ;
     FloatingActionButton btnagregar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cliente);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion) ;
       // btnguardar = (Button) findViewById(R.id.btnguardar) ;

        btnagregar = (FloatingActionButton) findViewById(R.id.btnAgregar) ;


        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 guardar(et_nombre.getText().toString(), et_descripcion.getText().toString());
              //  onBackPressed();
              //  startActivity( new Intent( MostrarCliente.this , unidadMostraer.class) );

             //   Fragment fragmento = null ;
            //    fragmento = new unidadMostraer() ;
            //    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor , fragmento).commit();

            //    onBackPressed();
               Intent intent = new Intent( getBaseContext() , unidadMostraer.class ) ;
                startActivityForResult(intent,0);
            }
        });



    }

    private void guardar (String nombre  , String descripcion)
    { // nombre de la base de datos APPVentas
      BaseHelper helper  = new BaseHelper(this , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        try{
            ContentValues c = new ContentValues() ;
            c.put("NAME", nombre);
            c.put("DESCRIPTION", descripcion);
            db.insert("CATEGORIA" , null , c);
            db.close();
          //  Toast.makeText(this, "Categoria Agregada", Toast.LENGTH_SHORT).show();



        }
        catch(Exception e)
        {
            Toast.makeText(this, "Erro al agregar categoria"+ e.getMessage() , Toast.LENGTH_SHORT).show();

        }


    }




}
