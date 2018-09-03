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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Showitem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Showitem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Showitem extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ListView listaItem ;
    ArrayList<String> Listadoitems ;
    FloatingActionButton btncallitem ;


    public Showitem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Showitem.
     */
    // TODO: Rename and change types and number of parameters
    public static Showitem newInstance(String param1, String param2) {
        Showitem fragment = new Showitem();
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
        View vista  =  inflater.inflate(R.layout.fragment_showitem, container, false);

        listaItem = (ListView) vista.findViewById(R.id.ListaItem) ;
        btncallitem = (FloatingActionButton) vista.findViewById(R.id.btncallitem) ;

        CargarLista() ;
        btncallitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 int clave =  1 ; // a insertar
                Intent intent = new Intent (  getContext() , Additem.class );
                intent.putExtra("ESTATUS" , clave);
                startActivityForResult(intent,0);
            }
        });

        listaItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int clave =  2 ; // para modificar y eliminar itemis
                int correlativo = Integer.parseInt( Listadoitems.get(i).split(" ")[0] );
                Intent intent = new Intent (  getContext() , Additem.class );
                intent.putExtra("ID", correlativo) ;
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



    private void CargarLista(){
        Listadoitems = ListarItems() ;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Listadoitems ) ;
        listaItem.setAdapter(adapter);
    }

    private ArrayList<String> ListarItems(){
        ArrayList<String> items = new ArrayList<String>() ;
        BaseHelper helper = new BaseHelper(getContext(), "APPVentas" , null , 1);
        SQLiteDatabase db = helper.getReadableDatabase() ;
        String sql = "Select ID, NAME  from ITEM";
        Cursor c = db.rawQuery(sql , null ) ;
        if ( c.moveToFirst())
        {

            do{
                String linea = c.getInt(0)+ " "+ c.getString(1)  ;
                items.add(linea) ;


            } while (c.moveToNext() ) ;
        }
        db.close();
        return items ;

    }

}
