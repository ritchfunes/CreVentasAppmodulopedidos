package com.example.ritchiecristopher.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UnidadaddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UnidadaddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnidadaddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int cantidad = 0 ;

    FloatingActionButton btncalluni ;
    ListView Listview ;
    ArrayList<String> Listadounit ;



    public UnidadaddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnidadaddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UnidadaddFragment newInstance(String param1, String param2) {
        UnidadaddFragment fragment = new UnidadaddFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_unidadadd, container, false);

        Listview = (ListView) vista.findViewById(R.id.ListUnidad) ;
        btncalluni = (FloatingActionButton) vista.findViewById(R.id.btncallunit) ;

        btncalluni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //
                //    startActivity( new Intent( getContext() , MostrarCliente.class)    );
                int clave =  1 ;
                Intent intent = new Intent (  getContext() , Unidadedit.class );
                intent.putExtra("ESTATUS" , clave);
                startActivityForResult(intent,0);
            }
        });


        CargarListaunidad();


       Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText( MostrarCategoria.this, MostrarCategoria.get(i), Toast.LENGTH_SHORT ).show();
                int clave =  2 ;
                int correlativo = Integer.parseInt( Listadounit.get(i).split(" ")[0] );
                String nombre = Listadounit.get(i).split(" ")[1] ;
                String descripcion = Listadounit.get(i).split(" ")[2] ;
                Intent intent = new Intent(getContext() , Unidadedit.class ) ;
                intent.putExtra("ID", correlativo) ;
                intent.putExtra("nombre", nombre);
                intent.putExtra("descripcion", descripcion) ;
                intent.putExtra("ESTATUS" , clave);
                startActivity(intent);

            }
        });

        return vista ;

    }

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

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {

            // finish();
        }
        return super.onOptionsItemSelected(item) ;

    }


    private void CargarListaunidad(){
        Listadounit = ListarUnidades() ;

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Listadounit);
            Listview.setAdapter(adapter);

    }

    private ArrayList<String> ListarUnidades(){
        ArrayList<String> Categoria = new ArrayList<String>() ;

        BaseHelper helper = new BaseHelper(getContext(), "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select ID, NAME, DESCRIPTION from UNIT";
        Cursor c = db.rawQuery(sql , null ) ;
        cantidad = c.getCount() ;


     //  if(cantidad > 0 ) {
           if (c.moveToFirst()) {

               do {
                   String linea = c.getInt(0) + " " + c.getString(1) + " " + c.getString(2);
                   Categoria.add(linea);


               } while (c.moveToNext());

           }
   //    }


        db.close();
        return Categoria ;

    }


}
