package com.example.ritchiecristopher.myfirstapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Unidadedit extends AppCompatActivity {


    EditText edt_nombreunidad , edt_descripcionunidad ;
    FloatingActionButton btnaddunidad , btndeleteunit , btneditunit ;
    LinearLayout layoutunidad  ;
    int id , correlativo;
    String nombreund , descpund ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidadedit);

        Bundle bu = getIntent().getExtras() ;
        Bundle buedit = getIntent().getExtras() ;

        if(bu != null )
        {
            id = bu.getInt("ESTATUS");

        }



        edt_nombreunidad = (EditText) findViewById(R.id.edtNombreunit);
        edt_descripcionunidad = (EditText) findViewById(R.id.edtDescripcionunit) ;
        // btnguardar = (Button) findViewById(R.id.btnguardar) ;

        btnaddunidad = (FloatingActionButton) findViewById(R.id.btnaddunidad) ;
        btneditunit = (FloatingActionButton) findViewById(R.id.btneditunit) ;
        btndeleteunit = (FloatingActionButton) findViewById(R.id.btndeleteunit) ;
        layoutunidad = (LinearLayout) findViewById(R.id.lyeditunidad) ;




        if (id == 1)
        {
            layoutunidad.setVisibility(View.INVISIBLE); // modo para agregar


        }
        else {

            btnaddunidad.hide();

            if(buedit != null )
            {
                correlativo = buedit.getInt("ID");
                nombreund = bu.getString("nombre") ;
                descpund = bu.getString("descripcion");
            }
        }


        edt_nombreunidad.setText(nombreund);
        edt_descripcionunidad.setText(descpund);


        btnaddunidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar(edt_nombreunidad.getText().toString(), edt_descripcionunidad.getText().toString());

                Intent intent = new Intent( getBaseContext() , UnidadaddFragment.class ) ;
                startActivityForResult(intent,0);
            }
        });


        btndeleteunit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Unidadedit.this);
                builder.setMessage("Desea Eliminar este registro");
                builder.setTitle("Eliminar");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminar(correlativo);
                        Intent intent = new Intent( getBaseContext() , UnidadaddFragment.class ) ;
                        startActivityForResult(intent,0);
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

            }
        });


        btneditunit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar(correlativo , edt_nombreunidad.getText().toString() , edt_descripcionunidad.getText().toString() );
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
        String sql = "delete from UNIT  where ID ="+id ;
        db.execSQL(sql);
        db.close();


    }


    private void modificar ( int id , String nombre  , String descripcion)
    {
        BaseHelper helper  = new BaseHelper(this , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        String sql = "UPDATE UNIT SET NAME= '"+nombre+"' , DESCRIPTION= '"+ descripcion+ "' where ID ="+id ;
        db.execSQL(sql);
        db.close();


    }


    private void guardar (String nombre  , String descripcion)
    { // nombre de la base de datos APPVentas
        BaseHelper helper  = new BaseHelper(this , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        try{
            ContentValues c = new ContentValues() ;
            c.put("NAME", nombre);
            c.put("DESCRIPTION", descripcion);
            db.insert("UNIT" , null , c);
            db.close();


        }
        catch(Exception e)
        {
            Toast.makeText(this, "Erro al agregar categoria"+ e.getMessage() , Toast.LENGTH_SHORT).show();

        }


    }




}
