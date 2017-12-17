package dam.sanpri.jesus.hermanosapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jes on 05/12/2017.
 */

public class DatabaseAdapter {

    private static final String TAG = "DBASE";
    private static DatabaseHelper helper = null;
    private Context context;

    public DatabaseAdapter(Context context){
        if(helper == null){
            helper = new DatabaseHelper(context);
        }
        this.context = context;
    }

    //Crea hermano con dos string nombre y contraseña
    public long crearHermano (String nombre, String pass){
        SQLiteDatabase db = helper.getWritableDatabase();

        //Valores a insertar se meten en un content values
        ContentValues content = new ContentValues();
        content.put(DatabaseHelper.Name, nombre);
        content.put(DatabaseHelper.Pass, pass);

        return db.insert(DatabaseHelper.TABLA_HNOS, null, content);
    }

    //Elimina hermano por nombre
    public long eliminarHermano(String brother){
        SQLiteDatabase db = helper.getWritableDatabase();

        return db.delete(DatabaseHelper.TABLA_HNOS, DatabaseHelper.Name+" = '"+brother+"'", null);
    }

    //actualiza pass nombre como condicion
    public long updatePass(String nombre, String passNew){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(DatabaseHelper.Pass, passNew);

        return db.update(DatabaseHelper.TABLA_HNOS, content, DatabaseHelper.Name+" = '"+nombre+"';", null);
    }

    //Actualiza status nombre como condicion
    public long updateStatus(String nombre, String status){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(DatabaseHelper.Status, status);

        return db.update(DatabaseHelper.TABLA_HNOS, content, DatabaseHelper.Name+" = '"+nombre+"'", null);
    }

    //Da como resultado una lista de hermanos, todos los elementos de la db que tengan el parametro admin false
    public List<Hermano> getBros(){
        List<Hermano>bros = new ArrayList<Hermano>();

        String whereClause = DatabaseHelper.Admin + " = ?";
        String[] whereArg = {"FALSE"};
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columnas = {DatabaseHelper.Name, DatabaseHelper.Status};
        Cursor cursor = db.query(DatabaseHelper.TABLA_HNOS, columnas, whereClause, whereArg, null, null, null);

        while(cursor.moveToNext()){
            String nombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Name));
            String status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Status));
            Log.i(TAG, "getBros  " + status);
            bros.add(new Hermano(nombre, status));
        }

        cursor.close();
        return bros;

    }

    //Verifica usuario y contraseña coincidan en una fila de la db
    public boolean broVerif(String nombre, String password){
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columnas = {DatabaseHelper.UID};
        String selectClause = DatabaseHelper.Name + " = ? AND " + DatabaseHelper.Pass + " = ? AND " + DatabaseHelper.Admin + " = ?";
        String[] selectArg = {nombre, password, "FALSE"};
        //en este caso se pone FALSE en vez de 0 PORQUE YO QUE SE
        Cursor cursor = db.query(DatabaseHelper.TABLA_HNOS, columnas, selectClause, selectArg, null, null, null);

        int numResults = cursor.getCount();
        cursor.close();

        if(numResults > 0){
            return true;
        }
        return false;
    }

    //Comprueba si el nombre existe en la db
    public boolean checkBroName(String nombre){
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columnas = {DatabaseHelper.UID};
        String selectClause = DatabaseHelper.Name + " = ? ";
        String[] selectArg = {nombre};

        Cursor cursor = db.query(DatabaseHelper.TABLA_HNOS, columnas, selectClause, selectArg, null, null, null);

        int numResults = cursor.getCount();
        cursor.close();

        if(numResults > 0){
            return false;
        }
        return true;
    }

    //Verificacion para admin
    public boolean adminVerif(String name, String pass){
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columnas = {DatabaseHelper.UID};
        String selectClause = DatabaseHelper.Name + " = ? AND " + DatabaseHelper.Pass + " = ? AND " + DatabaseHelper.Admin + " = ?";
        //SQLITE GUARDA LOS BOOLEANOS COMO INTEGERS, 0 FALSE, 1 TRUE()()()()()PUES QUE BIEN
        String[] selectArg = {name, pass, "1"};

        Cursor cursor = db.query(DatabaseHelper.TABLA_HNOS, columnas, selectClause, selectArg, null, null, null);

        int numResults = cursor.getCount();
        cursor.close();

        if(numResults > 0){
            return true;
        }
        return false;
    }

    //Creacion de la db con 5 columnas id nombre pass status y si es admin
    private class DatabaseHelper extends SQLiteOpenHelper{
        private static final String db_name = "brotherhooddb";
        private static final int version = 1;

        //Nombre de tabla y columnas
        private static final String TABLA_HNOS = "HNOS";
        private static final String UID = "_id";
        private static final String Name = "Name";
        private static final String Status = "Status";
        private static final String Pass = "Pass";
        private static final String Admin = "Admin";


        public DatabaseHelper(Context context) {
            super(context, db_name, null, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            //Ejecutamos el codigo SQL que creara la tabla con nuestras columnas deseadas
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_HNOS + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Name + " VARCHAR(255) NOT NULL, "+ Status + " VARCHAR(255) DEFAULT 'SUP', "+ Pass + " VARCHAR(255) NOT NULL, "+
                    Admin + " BOOLEAN NOT NULL DEFAULT FALSE" +");");

            //Creamos el admin en la creacion de la base de datos OTRA VEZ EL BOOLEANO TRUE DEBE SER un 1 o dara problemas
            db.execSQL("INSERT INTO " + TABLA_HNOS + "(" + Name + ", " + Pass + ", " + Admin + ") VALUES ('admin', '1234', 1);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

    }
}
