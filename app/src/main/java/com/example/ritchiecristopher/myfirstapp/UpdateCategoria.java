package com.example.ritchiecristopher.myfirstapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton ;

public class UpdateCategoria extends AppCompatActivity {


    EditText et_nombre , et_descripcion ;
    Button btnguardar , btnmostrar , btneliminar     ;
    FloatingActionButton btnfeliminar , btnfeditar ;

    int id ;
    String nombre , descripcion ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_categoria);
        Bundle bu = getIntent().getExtras() ;

        if(bu != null )
        {
            id = bu.getInt("ID");
            nombre = bu.getString("nombre") ;
            descripcion = bu.getString("descripcion");

        }

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion) ;

        et_nombre.setText(nombre);
        et_descripcion.setText(descripcion);

        btnguardar = (Button) findViewById(R.id.btnguardar) ;
        btneliminar = (Button) findViewById(R.id.btneliminar) ;
        btnfeditar = (FloatingActionButton) findViewById(R.id.fabEditar);
        btnfeliminar = (FloatingActionButton) findViewById(R.id.btnfeliminar);


        btnfeliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCategoria.this);
            builder.setMessage("Desea Eliminar este registro");
            builder.setTitle("Eliminar");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                     eliminar(id);
                    Intent intent = new Intent( getBaseContext() , unidadMostraer.class ) ;
                    startActivityForResult(intent,0);
                   //   onBackPressed();
                }
            }) ;

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();
                }
            }) ;
                AlertDialog dialog = builder.create();
                dialog.show();
              //  eliminar(id);
              //  onBackPressed();
            }
        });

        btnfeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 modificar(id , et_nombre.getText().toString() , et_descripcion.getText().toString() );
             //    onBackPressed();
                Intent intent = new Intent( getBaseContext() , unidadMostraer.class ) ;
                startActivityForResult(intent,0);

            }
        });


    }

    private void eliminar ( int id )
    {
        BaseHelper helper  = new BaseHelper(this , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        String sql = "delete from CATEGORIA  where ID ="+id ;
        db.execSQL(sql);
        db.close();


    }


    private void modificar ( int id , String nombre  , String descripcion)
    {
        BaseHelper helper  = new BaseHelper(this , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        String sql = "UPDATE CATEGORIA SET NAME= '"+nombre+"' , DESCRIPTION= '"+ descripcion+ "' where ID ="+id ;
        db.execSQL(sql);
        db.close();


    }
}
