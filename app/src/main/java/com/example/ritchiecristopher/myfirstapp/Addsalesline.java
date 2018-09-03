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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Addsalesline extends AppCompatActivity {


   private Spinner spinnercatpeddetalle , spinnerundpeddetalle , spinneritempeddetalle ;
  private  EditText edtcantpeddetalle , edtpreciopeddetalle , edttotalpeddetalle  ;
  private   FloatingActionButton btnaddsalesline , btndeletesalesline ;
  private  ArrayList<String> ListadoCategories , ListadoUnits , Listadoitems;
  private String item , itemunidad  , obtitem;
  private int claveunidad , clavecategoria , claveitem  ;
  private int idsalestable ;
  private double cantidad , precioitem , total;
  private  int id , correlativo  ;
 private LinearLayout lyoutsaleslineadd ;


  private ArrayAdapter<String> adapterd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsalesline);

        spinnercatpeddetalle =  (Spinner) findViewById(R.id.spinnercatpeddetalle) ;
        spinnerundpeddetalle =  (Spinner) findViewById(R.id.spinnerundpeddetalle) ;
        spinneritempeddetalle =  (Spinner) findViewById(R.id.spinneritempeddetalle) ;
        btnaddsalesline = (FloatingActionButton) findViewById(R.id.btnaddsalesline) ;
        edtcantpeddetalle = (EditText)  findViewById(R.id.edtcantpeddetalle) ;
        edtpreciopeddetalle = (EditText)  findViewById(R.id.edtpreciopeddetalle) ;
        edttotalpeddetalle = (EditText)  findViewById(R.id.edttotalpeddetalle) ;
        btndeletesalesline =  (FloatingActionButton) findViewById(R.id.btndeletesalesline) ;
        lyoutsaleslineadd = (LinearLayout) findViewById(R.id.lyoutsaleslineadd) ;

        Bundle bu = getIntent().getExtras() ;

        if(bu != null )
        {
            id = bu.getInt("ESTATUS");
            correlativo =  bu.getInt( "ID") ;
        }

        if(id == 1) // modo agregar
        {
            btndeletesalesline.hide();
        }
        else
        { // modo eleminar y editar
            lyoutsaleslineadd.setVisibility(View.INVISIBLE);

        }

        CargarLista() ;

        BaseHelper helper = new BaseHelper(this, "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select  SALESTABLEID  from SALESTABLE WHERE ESTATUS = 'PEDIDO_VENTAS' ";
        Cursor c = db.rawQuery(sql , null ) ;
        if ( c.moveToFirst())
        {
            do{
                idsalestable = c.getInt(0) ;

            }
            while (c.moveToNext() ) ;
        }
        db.close();

        spinnercatpeddetalle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                item =  spinnercatpeddetalle.getSelectedItem().toString() ;
                clavecategoria = Integer.parseInt( item.split(" ")[0] );

                cargaritems(1 , clavecategoria) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerundpeddetalle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                itemunidad =  spinnerundpeddetalle.getSelectedItem().toString() ;
                claveunidad = Integer.parseInt( itemunidad.split(" ")[0] );



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        spinneritempeddetalle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                obtitem =  spinneritempeddetalle.getSelectedItem().toString() ;
                claveitem = Integer.parseInt( obtitem.split(" ")[0] );

                infoitem(  claveitem ) ;





            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        edtcantpeddetalle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }



            @Override
            public void afterTextChanged(Editable editable) {

                if(edtcantpeddetalle.getText().length() >= 0) {
                    Double preciod = Double.parseDouble(edtpreciopeddetalle.getText().toString());
                    Double cantidadd = Double.parseDouble(edtcantpeddetalle.getText().toString());

                    Double Totald = preciod * cantidadd;

                    edttotalpeddetalle.setText(String.valueOf(Totald));
                }
            }
        });


        btnaddsalesline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cantidad =      Double.parseDouble(edtcantpeddetalle.getText().toString() );
                precioitem =  Double.parseDouble(edtpreciopeddetalle.getText().toString());
                total =   Double.parseDouble(edttotalpeddetalle.getText().toString());
                guardar( idsalestable , claveitem , cantidad , precioitem , total  );


                Intent intent = new Intent(getBaseContext() , tabshowsalesorder.class );
                startActivity(intent);


            }
        });

        btndeletesalesline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Addsalesline.this);
                builder.setMessage("Desea Eliminar este registro");
                builder.setTitle("Eliminar");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminar(correlativo);
                        Intent intent = new Intent( getBaseContext() , tabshowsalesorder.class ) ;
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



    }


    private void eliminar ( int id )
    {
        BaseHelper helper  = new BaseHelper( this , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        String sql = "delete from SALESLINE  where ID = "+id ;
        db.execSQL(sql);
        db.close();


    }



    private void infoitem( int iditem )
   {
    double precio = 0 ;
    double total = 0  ;

       BaseHelper helperitem = new BaseHelper(this, "APPVentas" , null , 1);
       SQLiteDatabase dbitem = helperitem.getReadableDatabase() ;
       String sqlitem = "Select  PRICE   from ITEM WHERE ID =  "+iditem ;
       Cursor citem = dbitem.rawQuery(sqlitem , null ) ;
       if ( citem.moveToFirst())
       {
           do{
               precio = citem.getDouble(0) ;


           }
           while (citem.moveToNext() ) ;
       }

       edtpreciopeddetalle.setText( String.valueOf( precio ) );
       dbitem.close();




   }


  private void guardar ( int idsalestable , int iditem , double cantidad , double price ,   double subtotal )
  {
      double cost = 0 ;
      double tax = 0 ;
      double total = 0 ;

      total = subtotal ;

      BaseHelper helpitem = new BaseHelper(this, "APPVentas" , null , 1);
      SQLiteDatabase dbitemg = helpitem.getReadableDatabase() ;
      String sqlitem = "Select  COST   from ITEM WHERE ID =  "+iditem ;
      Cursor citem = dbitemg.rawQuery(sqlitem , null ) ;
      if ( citem.moveToFirst())
      {
          do{
              cost = citem.getDouble(0) ;


          }
          while (citem.moveToNext() ) ;
      }
      dbitemg.close();



      BaseHelper helper  = new BaseHelper(this , "APPVentas", null , 1) ;
      SQLiteDatabase db = helper.getWritableDatabase() ;
      try{
          ContentValues c = new ContentValues() ;
          c.put("ID_SALESTABLE", idsalestable);
          c.put("ID_ITEM", iditem);
          c.put("CANTIDAD",cantidad );
          c.put("PRICE",price );
          c.put("COST",cost );
          c.put("SUBTOTAL",subtotal );
          c.put("TOTAL",total );
          c.put("TAX",tax );

          db.insert("SALESLINE" , null , c);
          db.close();

//            Toast.makeText(this, "Item"+ coditem+ " "+nombre+ String.valueOf( idunit)+" "+String.valueOf(  idcategoria) + " "+" "+String.valueOf(  price)+String.valueOf( cost), Toast.LENGTH_SHORT).show();

      }
      catch(Exception e)
      {
          Toast.makeText(this, "Erro al agregar categoria"+ e.getMessage() , Toast.LENGTH_SHORT).show();

      }



  }

  private void cargaritems( int rclaveunidad , int clavecategoria )
  {


      Listadoitems = ListarArticulos( claveunidad , clavecategoria  );
      adapterd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Listadoitems);
      spinneritempeddetalle.setAdapter(adapterd);

  }


    private void CargarLista() {
        ListadoCategories = ListarCategorias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListadoCategories);
        spinnercatpeddetalle.setAdapter(adapter);
/*
        if (idstatus != 1) {
            spcategoriaItem.setSelection(idcategoria);

        }
        */
        ListadoUnits = ListarUnidades();
        ArrayAdapter<String> adapterunidad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListadoUnits);
        spinnerundpeddetalle.setAdapter(adapterunidad);

     /*   if (idstatus != 1) {
            spunidaditem.setSelection(idunidad);
        }
        */
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



    private ArrayList<String> ListarArticulos( int unidad , int categoria ){
        ArrayList<String> Unidad= new ArrayList<String>() ;
        BaseHelper helper = new BaseHelper(this, "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select ID , NAME , ID_UNIT , ID_CATEGORIA from ITEM where (   ID_CATEGORIA = " +categoria + " ) ";

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


}
