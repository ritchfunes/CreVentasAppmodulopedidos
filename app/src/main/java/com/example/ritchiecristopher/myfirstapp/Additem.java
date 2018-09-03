package com.example.ritchiecristopher.myfirstapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Additem extends AppCompatActivity {


    Spinner spcategoriaItem , spunidaditem ;
    ArrayList<String> ListadoCategoria , ListadoUnidades;
    FloatingActionButton btnadditem , btnedititem , btndeleteitem  ;
    EditText edtcoditem , edtnombreitem , edtprecio , edtcosto ;
    String item , itemunidad , nombreitem ;
    int claveunidad , clavecategoria , idstatus , correlativo;
    int idunidad , idcategoria ;
    boolean isfirstc , isfirstu   ;
    double precio , costo ;
    LinearLayout layoutitem  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        Bundle bu = getIntent().getExtras() ;
        Bundle buedit = getIntent().getExtras() ;
        isfirstc = true ;
        isfirstu = true ;
        if(bu != null )
        {
            idstatus = bu.getInt("ESTATUS");

        }


        spcategoriaItem =  (Spinner) findViewById(R.id.spCategoriaItem) ;
        spunidaditem = (Spinner) findViewById(R.id.spUnidaditem) ;
        btnadditem = (FloatingActionButton) findViewById(R.id.btnadditem) ;
        btnedititem = (FloatingActionButton) findViewById(R.id.btnedititem) ;
        btndeleteitem = (FloatingActionButton) findViewById(R.id.btndeleteitem) ;

        edtcoditem = (EditText)  findViewById(R.id.edtcoditem) ;
        edtnombreitem = (EditText)  findViewById(R.id.edtnombreitem) ;
        edtprecio = (EditText)  findViewById(R.id.edtprecio) ;
        edtcosto = (EditText)  findViewById(R.id.edtcosto) ;
        layoutitem = (LinearLayout) findViewById(R.id.layoutitem) ;




        if (idstatus == 1)
        {
            layoutitem.setVisibility(View.INVISIBLE); // modo para agregar


        }
        else {

            btnadditem.hide();
            idunidad = 0 ;
            idcategoria = 0 ;
            correlativo = 0 ;
            if(buedit != null )
            {
                correlativo = buedit.getInt("ID");
            //    nombreund = bu.getString("nombre") ;
            //    descpund = bu.getString("descripcion");
            }



                BaseHelper helper = new BaseHelper(this, "APPVentas" , null , 1);
                SQLiteDatabase db = helper.getReadableDatabase() ;
                String sql = "Select  NAME, ID_CATEGORIA, PRICE , COST , ID_UNIT  from ITEM WHERE ID ="+correlativo;
                Cursor c = db.rawQuery(sql , null ) ;
                if ( c.moveToFirst())
                {

                    do{

                        nombreitem =   c.getString(0)  ;
                        idcategoria = c.getInt(1) ;
                        precio = c.getDouble( 2) ;
                        costo = c.getDouble( 3) ;
                        idunidad = c.getInt(4 );



                    }
                    while (c.moveToNext() ) ;
                }
                db.close();

            edtnombreitem.setText(nombreitem);
            edtprecio.setText( String.valueOf(  precio ));
            edtcosto.setText( String.valueOf( costo) );
            idcategoria = idcategoria - 1 ;
            idunidad = idunidad - 1 ;

        ///    spcategoriaItem.setSelection(idcategoria - 1);
        //    spunidaditem.setSelection(idunidad - 1);
        } // ciera el modo edit

      //  Toast.makeText( getApplicationContext(), String.valueOf(idcategoria) + " unt" + String.valueOf(idunidad ) , Toast.LENGTH_SHORT).show();

        CargarListaCategoria() ;


        spunidaditem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                itemunidad =  spunidaditem.getSelectedItem().toString() ;

                claveunidad = Integer.parseInt( itemunidad.split(" ")[0] );

    //            Toast.makeText( getApplicationContext(),"und"+ String.valueOf( claveunidad ) , Toast.LENGTH_SHORT).show();

              /*  if (idstatus != 1)
                {

                 if(isfirstu == true) {
                     spcategoriaItem.setSelection(idcategoria - 1);
                     isfirstu = false ;
                    }
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spcategoriaItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
  //                Toast.makeText( getApplicationContext(), "cat"+spcategoriaItem.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();

                 item =  spcategoriaItem.getSelectedItem().toString() ;

                clavecategoria = Integer.parseInt( item.split(" ")[0] );


           /*     if (idstatus != 1)
                {

                    if(isfirstc == true) {
                        spunidaditem.setSelection(idunidad - 1);
                        isfirstc = false ;
                    }
                } */
          //      Toast.makeText( getApplicationContext(), String.valueOf( clave ) , Toast.LENGTH_SHORT).show();
        }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtcoditem.setText(item);


        btnedititem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //    int claveunidad = Integer.parseInt( ListadoUnidades.get(i).split(" ")[0] );
                String preciot = edtprecio.getText().toString() ;
                String costot = edtcosto.getText().toString() ;
                Double preciod = Double.parseDouble(preciot) ;
                Double costod = Double.parseDouble(costot) ;



                 modificar(correlativo , edtnombreitem.getText().toString() , claveunidad , clavecategoria , preciod , costod);
                Intent intent = new Intent( getBaseContext() , UnidadaddFragment.class ) ;
                startActivityForResult(intent,0);


            }
        });

        btndeleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Additem.this);
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

        btnadditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //    int claveunidad = Integer.parseInt( ListadoUnidades.get(i).split(" ")[0] );
                String preciot = edtprecio.getText().toString() ;
                String costot = edtcosto.getText().toString() ;
                Double preciod = Double.parseDouble(preciot) ;
                Double costod = Double.parseDouble(costot) ;

                guardar(edtcoditem.getText().toString() , edtnombreitem.getText().toString() ,claveunidad , clavecategoria ,
                        preciod, costod  );

                Intent intent = new Intent( getBaseContext() , UnidadaddFragment.class ) ;
                startActivityForResult(intent,0);
            }
        });

    }

    private void CargarListaCategoria() {
        ListadoCategoria = ListarCategorias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListadoCategoria);
        spcategoriaItem.setAdapter(adapter);

        if (idstatus != 1) {
            spcategoriaItem.setSelection(idcategoria);

        }
        ListadoUnidades = ListarUnidades();
        ArrayAdapter<String> adapterunidad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListadoUnidades);
        spunidaditem.setAdapter(adapterunidad);

        if (idstatus != 1) {
            spunidaditem.setSelection(idunidad);
        }

    }


    private ArrayList<String> ListarCategorias(){
        ArrayList<String> Categoria = new ArrayList<String>() ;
        BaseHelper helper = new BaseHelper(this, "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select ID, NAME from CATEGORIA";
        Cursor c = db.rawQuery(sql , null ) ;
        if ( c.moveToFirst())
        {

            do{
                String linea = c.getInt(0)+ " "+ c.getString(1)  ;
                Categoria.add(linea) ;


            } while (c.moveToNext() ) ;
        }
        db.close();
        return Categoria ;

    }

    private void modificar ( int id , String nombre  , int unidad , int categoria , double precioitem , double costoiten  )
    {
        BaseHelper helper  = new BaseHelper(this , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        String sql = "UPDATE ITEM SET NAME= '"+nombre+"' , ID_UNIT= "+ unidad+" , ID_CATEGORIA= "+categoria+" , PRICE= "+precioitem+ " , COST= "+costoiten +"  where ID= "+id ;
        db.execSQL(sql);
        db.close();


    }


    private ArrayList<String> ListarUnidades(){
        ArrayList<String> Unidad= new ArrayList<String>() ;
        BaseHelper helper = new BaseHelper(this, "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select ID, NAME from UNIT";
        Cursor c = db.rawQuery(sql , null ) ;
        if ( c.moveToFirst())
        {

            do{
                String linea = c.getInt(0)+ " "+ c.getString(1)  ;
                Unidad.add(linea) ;


            } while (c.moveToNext() ) ;
        }
        db.close();
        return Unidad ;

    }




    private void eliminar ( int id )
    {
        BaseHelper helper  = new BaseHelper(this , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        String sql = "delete from ITEM  where ID ="+id ;
        db.execSQL(sql);
        db.close();


    }


    private void guardar (  String coditem , String nombre, int idunit , int idcategoria ,double  price , double cost)
    { // nombre de la base de datos APPVentas
        BaseHelper helper  = new BaseHelper(this , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        try{
            ContentValues c = new ContentValues() ;
            c.put("CODIGO_ITEM", coditem);
            c.put("NAME", nombre);
            c.put("ID_UNIT",idunit );
            c.put("ID_CATEGORIA",idcategoria );
            c.put("PRICE",price );
            c.put("COST",cost );

            db.insert("ITEM" , null , c);
            db.close();

//            Toast.makeText(this, "Item"+ coditem+ " "+nombre+ String.valueOf( idunit)+" "+String.valueOf(  idcategoria) + " "+" "+String.valueOf(  price)+String.valueOf( cost), Toast.LENGTH_SHORT).show();

        }
        catch(Exception e)
        {
            Toast.makeText(this, "Erro al agregar categoria"+ e.getMessage() , Toast.LENGTH_SHORT).show();

        }


    }



}
