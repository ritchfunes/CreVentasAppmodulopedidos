package com.example.ritchiecristopher.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import android.support.design.widget.FloatingActionButton ;

public class MostrarCategoria extends AppCompatActivity {

        ListView Listview ;
        ArrayList<String> Listado ;

    FloatingActionButton btnagregar ;
    @Override
    protected void onPostResume() {
        super.onPostResume();
        CargarLista();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_categoria);

       Listview = (ListView) findViewById(R.id.Listview) ;
       btnagregar = (FloatingActionButton) findViewById(R.id.btnadd) ;

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   startActivity( new Intent( MostrarCategoria.this , MostrarCliente.class)    );

                Intent intent = new Intent ( MostrarCategoria.this , MostrarCliente.class );
                startActivityForResult(intent,0);
            }
        });


        CargarLista() ;
        Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             //   Toast.makeText( MostrarCategoria.this, MostrarCategoria.get(i), Toast.LENGTH_SHORT ).show();

                int clave = Integer.parseInt( Listado.get(i).split(" ")[0] );
                String nombre = Listado.get(i).split(" ")[1] ;
                String descripcion = Listado.get(i).split(" ")[2] ;
                Intent intent = new Intent(MostrarCategoria.this , UpdateCategoria.class ) ;
                intent.putExtra("ID", clave) ;
                intent.putExtra("nombre", nombre);
                intent.putExtra("descripcion", descripcion) ;
                startActivity(intent);

            }
        });
        if( getSupportActionBar() != null )
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {

            finish();
        }
        return super.onOptionsItemSelected(item) ;

    }

    private void CargarLista(){
        Listado = ListarCategorias() ;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Listado ) ;
        Listview.setAdapter(adapter);
    }

   private ArrayList<String> ListarCategorias(){
   ArrayList<String> Categoria = new ArrayList<String>() ;
   BaseHelper helper = new BaseHelper(this, "APPVentas" , null , 1);
       SQLiteDatabase db = helper.getReadableDatabase() ;
       String sql = "Select ID, NAME, DESCRIPTION from CATEGORIA";
       Cursor c = db.rawQuery(sql , null ) ;
       if ( c.moveToFirst())
       {

           do{
            String linea = c.getInt(0)+ " "+ c.getString(1) + " "+ c.getString(2) ;
               Categoria.add(linea) ;


           } while (c.moveToNext() ) ;
       }
        db.close();
       return Categoria ;

   }
}
