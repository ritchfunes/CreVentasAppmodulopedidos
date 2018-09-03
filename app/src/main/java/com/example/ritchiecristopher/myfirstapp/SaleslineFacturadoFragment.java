package com.example.ritchiecristopher.myfirstapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaleslineFacturadoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaleslineFacturadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaleslineFacturadoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnconsultarfacturas ;
    private EditText etPlannedDate , edthasta ;
    private ListView listafacturadosenca ;
    private TextView txttotalm ;
    private ArrayList<String> Listadounit ;
    private String fecdesde , fechasta ;
    private DatePickerDialog.OnDateSetListener sDateSetListner ;
    private DatePickerDialog.OnDateSetListener sDateSetListnerhasta ;

    private OnFragmentInteractionListener mListener;

    /*
    public SaleslineFacturadoFragment() {
        // Required empty public constructor
    }
    */

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaleslineFacturadoFragment.
     */
    // TODO: Rename and change types and number of parameters

  /*  public static SaleslineFacturadoFragment newInstance(String param1, String param2) {
        SaleslineFacturadoFragment fragment = new SaleslineFacturadoFragment();
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
        View vista =  inflater.inflate(R.layout.fragment_salesline_facturado, container, false);


        txttotalm = (TextView) vista.findViewById(R.id.txttotalm) ;
        listafacturadosenca = (ListView) vista.findViewById(R.id.listafacturadosenca) ;
        edthasta = (EditText) vista.findViewById(R.id.edthasta) ;
        etPlannedDate = (EditText) vista.findViewById(R.id.etPlannedDate) ;
        btnconsultarfacturas = (Button) vista.findViewById(R.id.btnconsultarfacturas) ;


        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             Calendar cal = Calendar.getInstance() ;
             int year = cal.get(Calendar.YEAR) ;
                int month = cal.get(Calendar.MONTH) ;
                int day = cal.get(Calendar.DAY_OF_MONTH) ;

                DatePickerDialog dialog = new DatePickerDialog(
                          getActivity() ,
                       // getContext() ,
                        android.R.style.Theme_Material_Light_Dialog_MinWidth ,
                        sDateSetListner ,
                       year , month , day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE) );
                dialog.show();
            }
        });

        sDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1 ;
               String mes ;
               String dia ;
                
                if( month < 10)
                {
                    mes = "0"+month ;
                }
                else {
                    mes = String.valueOf( month ) ;
                }

                if(day < 10)
                {
                    dia = "0"+day ;
                }
                else {
                    dia = String.valueOf( day ) ;
                }

                String date = year+"-"+mes+"-"+dia ;
                fecdesde = date ;
                etPlannedDate.setText(date);

            }
        } ;


        edthasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance() ;
                int year = cal.get(Calendar.YEAR) ;
                int month = cal.get(Calendar.MONTH) ;
                int day = cal.get(Calendar.DAY_OF_MONTH) ;

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity() ,
                        android.R.style.Theme_Material_Light_Dialog_MinWidth ,
                        sDateSetListnerhasta ,
                        year , month , day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE) );
                dialog.show();


            }
        });



        sDateSetListnerhasta = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {



                month = month + 1 ;
                String mes ;
                String dia ;

                if( month < 10)
                {
                    mes = "0"+month ;
                }
                else {
                    mes = String.valueOf( month ) ;
                }

                if(day < 10)
                {
                    dia = "0"+day ;
                }
                else {
                    dia = String.valueOf( day ) ;
                }

                String date = year+"-"+mes+"-"+dia ;
                fechasta = date ;
                edthasta.setText(date);


            }
        } ;


        btnconsultarfacturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CargarListaunidad(fecdesde , fechasta) ;


            }
        });


        listafacturadosenca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int correlativo = Integer.parseInt( Listadounit.get(i).split(" ")[0] );

                Intent intent = new Intent(getContext() , PedidosFacturados.class ) ;
                intent.putExtra("ID", correlativo) ;
                startActivity(intent);
            }
        });



        return vista ;
    }


    private void CargarListaunidad(String desde , String hasta ){
        Listadounit = ListarUnidades(desde , hasta ) ;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Listadounit);
        listafacturadosenca.setAdapter(adapter);

    }



    private ArrayList<String> ListarUnidades(String desde , String hasta){
        ArrayList<String> Categoria = new ArrayList<String>() ;
        int idsalestable = 0  ;
        double totalfactura = 0 ;
        BaseHelper helper = new BaseHelper(getContext(), "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select SALESTABLEID, ESTATUS, FECHA from SALESTABLE where ESTATUS = 'FACTURADO'  AND  FECHA >=  '"+desde +"'  AND FECHA <= '"+hasta+"'  ";
        Cursor c = db.rawQuery(sql , null ) ;

        if (c.moveToFirst()) {

            do {
                idsalestable = c.getInt(0) ;
                String linea = c.getInt(0) + " " + c.getString(1)+" "+ c.getString(2) ;
                Categoria.add(linea);

                String sql2 = "Select sum( TOTAL ) from SALESLINE where (  ID_SALESTABLE = " +idsalestable + " )";

                Cursor ctotal = db.rawQuery(sql2 , null ) ;
                if ( ctotal.moveToFirst())
                {
                    do{
                        totalfactura = totalfactura + ctotal.getDouble(0)  ;
                    } while (ctotal.moveToNext() ) ;
                }


            } while (c.moveToNext());

        }

        db.close();
        txttotalm.setText( String.valueOf( totalfactura ) );

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
