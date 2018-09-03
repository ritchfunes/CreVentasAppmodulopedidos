package com.example.ritchiecristopher.myfirstapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tmpsaleslineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tmpsaleslineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tmpsaleslineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

   private FloatingActionButton btncallsalesline ;
   private ListView Listatmpsalesline ;
   private ArrayList<String> Listadopedidodeta ;
  private int correlativosalesline ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_tmpsalesline, container, false);

        Listatmpsalesline = (ListView) vista.findViewById(R.id.Listatmpsalesline) ;
        btncallsalesline = (FloatingActionButton) vista.findViewById(R.id.btncallsalesline) ;


        cargaritems() ;


        btncallsalesline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int clave =  1 ;
                Intent intent = new Intent(getContext() , Addsalesline.class );
                intent.putExtra("ESTATUS" , clave);
                startActivity(intent);

            }

        });

        Listatmpsalesline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 correlativosalesline = Integer.parseInt( Listadopedidodeta.get(i).split(" ")[0] );
                int clave =  2 ;
                Intent intent = new Intent(getContext() , Addsalesline.class ) ;
                intent.putExtra("ID", correlativosalesline) ;
                intent.putExtra("ESTATUS" , clave);
                startActivity(intent);

            }
        });


        return vista ;
    }




    private void cargaritems(  )
    {
        int idsalestable = 0 ;


        BaseHelper helperd = new BaseHelper(getContext(), "APPVentas" , null , 1);
        SQLiteDatabase dbd = helperd.getReadableDatabase() ;
        String sqld = "Select  SALESTABLEID  from SALESTABLE WHERE ESTATUS = 'PEDIDO_VENTAS' ";
        Cursor cd = dbd.rawQuery(sqld , null ) ;
        if ( cd.moveToFirst())
        {
            do{
                idsalestable = cd.getInt(0) ;

            }
            while (cd.moveToNext() ) ;
        }
        dbd.close();


        Listadopedidodeta = ListarSalesline( idsalestable  );
        ArrayAdapter<String>  adapterd = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Listadopedidodeta);
        Listatmpsalesline.setAdapter(adapterd);

    }



    private ArrayList<String> ListarSalesline( int idsalestable  ){
        ArrayList<String> Unidad= new ArrayList<String>() ;
        BaseHelper helper = new BaseHelper(getContext(), "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select T1.ID, T2.NAME, T1.CANTIDAD , T1.ID_SALESTABLE, ID_ITEM from SALESLINE T1 " +
                " INNER JOIN ITEM T2 ON T2.ID = T1.ID_ITEM "+
                " where (   T1.ID_SALESTABLE = " +idsalestable + " ) ";

        Cursor c = db.rawQuery(sql , null ) ;
      //  String encabezado =  "Articulo"+" "+"Cantidad"   ;
       // Unidad.add(encabezado) ;
        if ( c.moveToFirst())
        {

            do{
                String linea = c.getInt(0)+" " + c.getString(1)+"  "+ c.getDouble(2)  ;
                Unidad.add(linea) ;


            } while (c.moveToNext() ) ;
        }
        db.close();
        return Unidad ;

    }


}
