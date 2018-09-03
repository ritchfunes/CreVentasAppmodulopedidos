package com.example.ritchiecristopher.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class BaseHelper extends SQLiteOpenHelper {


    String TablaCategoria = "CREATE TABLE CATEGORIA ( ID INTEGER PRIMARY key, NAME NVARCHAR(3), DESCRIPTION TEXT )" ;
    String TablaUnidades = "CREATE TABLE   UNIDAD ( ID INTEGER PRIMARY key, NAME TEXT, DESCRIPTION TEXT  )";
    String TablaUnidad = "CREATE TABLE   UNIT ( ID INTEGER PRIMARY key, NAME NVARCHAR(3), DESCRIPTION TEXT  )";
    String TablaItem = "CREATE TABLE ITEM (ID INTEGER PRIMARY key , CODIGO_ITEM TEXT ,  NAME TEXT, " +
                        "ID_UNIT INTEGER , ID_CATEGORIA INTEGER, PRICE NUMERIC , COST NUMERIC , FOREIGN KEY  (ID_UNIT) REFERENCES UNIT (ID)," +
                         " FOREIGN KEY (ID_CATEGORIA ) REFERENCES CATEGORIA(ID)  )" ;

   String TablaSalestable = "CREATE TABLE SALESTABLE (SALESTABLEID INTEGER PRIMARY KEY , FECHA DATE DEFAULT CURRENT_DATE, ESTATUS TEXT   );";
   String TablaSalesline = "CREATE TABLE SALESLINE ( ID INTEGER PRIMARY KEY, ID_SALESTABLE INTEGER, ID_ITEM INTEGER,  CANTIDAD NUMERIC, PRICE NUMERIC , COST NUMERIC , " +
           " SUBTOTAL NUMERIC, TOTAL NUMERIC, TAX NUMERIC ,    " +
           " FOREIGN KEY (ID_SALESTABLE) REFERENCES SALESTABLE(SALESTABLEID) , FOREIGN KEY (ID_ITEM) REFERENCES ITEM(ID)  ) ";

   String TablaUsuarios = " CREATE TABLE USUARIOSADM ( ID INTEGER PRIMARY KEY, USER TEXT, PASWORD TEXT, COMPANY TEXT, IMEI TEXT, MES INTEGER  ) ";
    public BaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( TablaCategoria );
        sqLiteDatabase.execSQL( TablaUnidades );
        sqLiteDatabase.execSQL( TablaUnidad );
        sqLiteDatabase.execSQL( TablaItem );
        sqLiteDatabase.execSQL( TablaSalestable );
        sqLiteDatabase.execSQL( TablaSalesline );
        sqLiteDatabase.execSQL( TablaUsuarios );
      //  sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CATEGORIA" );
       // sqLiteDatabase.execSQL("INSERT INTO UNIT  VALUES ( null,'UND', 'unidad' ); ");
    //    sqLiteDatabase.execSQL(" INSERT INTO CATEGORIA (NAME,DESCRIPTION)  VALUES ( 'LAC', 'lacteos' )");
        sqLiteDatabase.execSQL(" INSERT INTO USUARIOSADM (USER,PASWORD,COMPANY,IMEI, MES )  VALUES ( 'rfunes', '1000', 'parador', '863451030326772', 9  )");
      // (ID, NAME,DESCRIPTION) this.guardar(sqLiteDatabase);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}
