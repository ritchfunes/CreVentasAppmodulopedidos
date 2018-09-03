package com.example.ritchiecristopher.myfirstapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.EditText ;
import android.widget.TextView ;
import android.support.design.widget.FloatingActionButton;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.content.ContentValues;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.Intent;
import java.util.ArrayList;
import android.database.Cursor;



public class tmpsalestableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    FloatingActionButton  btncallpedioenca , btnfavturarpedido ;
    EditText edtcambio , edtefectivo ;
    ListView listapedidoenca ;
    TextView txttotalresultado ;
    ArrayList<String> Listadounit ;
    int cantidad = 0  ;
    private int idsalestableb = 0  ;
    /*  private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tmpsalestableFragment() {
        // Required empty public constructor
    }
*/
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tmpsalestableFragment.
     */
    // TODO: Rename and change types and number of parameters
   /* public static tmpsalestableFragment newInstance(String param1, String param2) {
        tmpsalestableFragment fragment = new tmpsalestableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_tmpsalestable, container, false);

        listapedidoenca = (ListView) vista.findViewById(R.id.listapedidoenca) ;
        btncallpedioenca = (FloatingActionButton) vista.findViewById(R.id.btncallpedioenca) ;
        btnfavturarpedido = (FloatingActionButton) vista.findViewById(R.id.btnfavturarpedido) ;
        edtcambio = (EditText) vista.findViewById(R.id.edtcambio) ;
        edtefectivo = (EditText) vista.findViewById(R.id.edtefectivo) ;
        txttotalresultado = (TextView) vista.findViewById(R.id.txttotalresultado) ;

        btnfavturarpedido.setEnabled(false);
        CargarListaunidad() ;
        totalfactura();


        btncallpedioenca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                guardar() ;
                btncallpedioenca.setEnabled(false);
                btnfavturarpedido.setEnabled(true);
                //
                //    startActivity( new Intent( getContext() , MostrarCliente.class)    );


                Intent intent = new Intent(getContext() , tabshowsalesorder.class );
                startActivity(intent);



            }
        });


        btnfavturarpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                modificarFacturar(idsalestableb);


                Intent intent = new Intent(  getContext() , MenuprincipalOriginal.class ) ;
                startActivityForResult(intent,0);
            }
        });

        edtefectivo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(edtefectivo.getText().length() >= 0) {
                    Double efectivod = Double.parseDouble(edtefectivo.getText().toString());
                    Double Total = Double.parseDouble(txttotalresultado.getText().toString());

                    Double cambiod  ;
                    cambiod = 0.0 ;
                    if(efectivod >= Total )
                    {
                        cambiod    = efectivod - Total;
                    }

                    edtcambio.setText(String.valueOf(cambiod));

            }
        }
        });

        return vista ;
    }



    private void modificarFacturar ( int idsalestable )
    {
        BaseHelper helper  = new BaseHelper(getContext() , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        String sql = "UPDATE SALESTABLE SET ESTATUS = 'FACTURADO'  where SALESTABLEID ="+idsalestable ;
        db.execSQL(sql);
        db.close();


    }



    private ArrayList<String> totalfactura( ){

        int idsalestable = 0 ;
        double totalfactura = 0  ;

        BaseHelper helperd = new BaseHelper(getContext(), "APPVentas" , null , 1);
        SQLiteDatabase dbd = helperd.getReadableDatabase() ;
        String sqld = "Select  SALESTABLEID  from SALESTABLE WHERE ESTATUS = 'PEDIDO_VENTAS' ";
        Cursor cd = dbd.rawQuery(sqld , null ) ;
        if ( cd.moveToFirst())
        {
            do{
                idsalestable = cd.getInt(0) ;
                idsalestableb =  idsalestable ;
            }
            while (cd.moveToNext() ) ;
        }
        dbd.close();




        ArrayList<String> Unidad= new ArrayList<String>() ;
        BaseHelper helper = new BaseHelper( getContext(), "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select sum( TOTAL ) from SALESLINE where (   ID_SALESTABLE = " +idsalestable + " ) ";

        Cursor c = db.rawQuery(sql , null ) ;
        if ( c.moveToFirst())
        {

            do{
                totalfactura = c.getDouble(0)  ;



            } while (c.moveToNext() ) ;
        }


        db.close();
        txttotalresultado.setText( String.valueOf( totalfactura ) );

        return Unidad ;


    }


    private void guardar (   )
    { // nombre de la base de datos APPVentas
        BaseHelper helper  = new BaseHelper(getContext()  , "APPVentas", null , 1) ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        try{
            ContentValues c = new ContentValues() ;
            c.put("ESTATUS", "PEDIDO_VENTAS");
            db.insert("SALESTABLE" , null , c);
            db.close();

        //    Toast.makeText(getContext(), " AGREGADO ", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e)
        {
            Toast.makeText(getContext(), "Erro al agregar categoria"+ e.getMessage() , Toast.LENGTH_SHORT).show();

        }


    }



    private void CargarListaunidad(){
        Listadounit = ListarUnidades() ;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Listadounit);
        listapedidoenca.setAdapter(adapter);

    }




    private ArrayList<String> ListarUnidades(){
        ArrayList<String> Categoria = new ArrayList<String>() ;

        BaseHelper helper = new BaseHelper(getContext(), "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select SALESTABLEID, ESTATUS from SALESTABLE where ESTATUS = 'PEDIDO_VENTAS' ";
        Cursor c = db.rawQuery(sql , null ) ;

        cantidad =   c.getCount() ;

        if (cantidad > 0)
        {
            btncallpedioenca.setEnabled(false);
            btnfavturarpedido.setEnabled(true);
        }

        //  if(cantidad > 0 ) {
        if (c.moveToFirst()) {

            do {
                String linea = c.getInt(0) + " " + c.getString(1) ;
                Categoria.add(linea);


            } while (c.moveToNext());

        }
        //    }


        db.close();
        return Categoria ;

    }


/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
  /*  public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    */
}
