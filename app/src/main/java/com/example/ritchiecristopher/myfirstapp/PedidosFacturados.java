package com.example.ritchiecristopher.myfirstapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PedidosFacturados extends AppCompatActivity {


    private ListView listasaleslinefacturado ;
    private ArrayList<String> Listadodetpedidos ;
    private int salestableid , correlativo ;
    private TextView txttotal , txtcosto , txtprecio , txtcantidad ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_facturados);

        Bundle bu = getIntent().getExtras() ;
        listasaleslinefacturado = (ListView) findViewById(R.id.listasaleslinefacturado) ;
        txttotal = (TextView) findViewById(R.id.txttotal) ;
        txtcosto = (TextView) findViewById(R.id.txtcosto) ;
        txtprecio = (TextView) findViewById(R.id.txtprecio) ;
        txtcantidad = (TextView) findViewById(R.id.txtcantidad) ;

     //   txtcantidad.setText("Cantidad: ");
      //  txtprecio.setText("Precio: ");
      //  txttotal.setText("Total: ");
       // txtcosto.setText("Costo Total: ");

        if(bu != null )
        {
            salestableid = bu.getInt("ID"); // obtiene el numero de el pedido de ventas

        }

        CargarListapedidos( salestableid) ;

        listasaleslinefacturado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                 correlativo = Integer.parseInt( Listadodetpedidos.get(i).split(" ")[0] );

                ListardetallePedidos(correlativo) ;
            }
        });

    }


    private ArrayList<String> ListardetallePedidos(int idsalesline){
        ArrayList<String> Categoria = new ArrayList<String>() ;
        double cantidad = 0 ;
        double precio = 0  ;
        double total = 0 ;
        double costo = 0 ;
        double costototal = 0 ;

        BaseHelper helper = new BaseHelper(this, "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select T1.PRICE, T1.CANTIDAD, T1.SUBTOTAL, T1.COST, T1.ID_SALESTABLE, T1.ID_ITEM, T1.ID from SALESLINE T1 " +
                " where (   T1.ID = " +idsalesline + " ) ";

        Cursor c = db.rawQuery(sql , null ) ;

        if (c.moveToFirst()) {

            do {

                precio =  c.getDouble(0) ;
                cantidad =   c.getDouble(1) ;
                total =  c.getDouble(2) ;
                costo =   c.getDouble(3) ;
              //  String linea =    c.getDouble(2)+ " "+ c.getDouble( 3)+ " "+c.getDouble(4) ;
              //  Categoria.add(linea);

            } while (c.moveToNext());

        }

        db.close();
        costototal = costo * cantidad ;
        txtcantidad.setText("Cantidad: "+ String.valueOf( cantidad ));
        txtprecio.setText("Precio: "+ String.valueOf( precio ));
        txttotal.setText("Total: "+ String.valueOf( total ));
        txtcosto.setText("Costo Total: "+ String.valueOf( costototal ));
        return Categoria ;

    }




    private void CargarListapedidos( int idsalestable ){
        Listadodetpedidos = ListarPedidos( idsalestable ) ;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Listadodetpedidos);
        listasaleslinefacturado.setAdapter(adapter);

    }

    private ArrayList<String> ListarPedidos(int idsalestable){
        ArrayList<String> Categoria = new ArrayList<String>() ;
        BaseHelper helper = new BaseHelper(this, "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select T1.ID, T2.NAME, T1.PRICE, T1.CANTIDAD, T1.SUBTOTAL, T1.ID_SALESTABLE, T1.ID_ITEM from SALESLINE T1 " +
                " INNER JOIN ITEM T2 ON T2.ID = T1.ID_ITEM "+
                " where (   T1.ID_SALESTABLE = " +idsalestable + " ) ";

        Cursor c = db.rawQuery(sql , null ) ;

        if (c.moveToFirst()) {

            do {

                String linea = c.getInt(0) + " " + c.getString(1) ;  //+" "+ c.getDouble(2)+ " "+ c.getDouble( 3)+ " "+c.getDouble(4) ;
                Categoria.add(linea);

            } while (c.moveToNext());

        }

        db.close();

        return Categoria ;

    }




}
