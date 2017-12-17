package dam.sanpri.jesus.hermanosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseAdapter database;
    private String strNombre, strPass, status;
    private final String TAG = "MAINACT";
    EditText nombreMain, passMain, statusMain;
    private final int ADMIN_ACTIVITY = 1;
    private final int LISTA_ACTIVITY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creamos la base de datos
        database = new DatabaseAdapter(this);

        //Cogemos las vistas que vamos a usar en las funciones on click
        getViews();
    }

    public void showBros(View view) {
        strNombre = nombreMain.getText().toString().trim();
        strPass = passMain.getText().toString().trim();

        if(database.broVerif(strNombre, strPass)){
            Intent intent = new Intent(this, ListaActivity.class);

            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "No eres un hermano, no puedes ver la lista", Toast.LENGTH_SHORT).show();
        }

    }

    public void changeStatus(View view) {

        strNombre = nombreMain.getText().toString().trim();
        strPass = passMain.getText().toString().trim();
        status = statusMain.getText().toString().trim();

        if(database.broVerif(strNombre, strPass)){
            database.updateStatus(strNombre, status);
            Toast.makeText(getApplicationContext(), "Status cambiado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "User failed login, no puedes cambiar el status", Toast.LENGTH_SHORT).show();
        }


    }

    public void admin(View view) {
        strNombre = nombreMain.getText().toString().trim();
        strPass = passMain.getText().toString().trim();

        if(database.adminVerif(strNombre, strPass)){
            Intent intentAdmin = new Intent(this, AdminActivity.class);

            startActivity(intentAdmin);
        }else{
            Toast.makeText(getApplicationContext(), "Admin failed login", Toast.LENGTH_SHORT).show();
        }

    }

    //Rellena usuarios test
    public void userFillTest(){
        String[] nombres = {"user1", "user2", "user4", "user5","user6", "user7", "user8", "user9", "user10"};
        for (int i = 0; i < nombres.length; i++) {
            database.crearHermano(nombres[i], String.valueOf(i));
        }
    }

    public void getViews(){
        nombreMain = (EditText) findViewById(R.id.nombreMain);
        passMain = (EditText) findViewById(R.id.passMain);
        statusMain = (EditText) findViewById(R.id.statusMain);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        strNombre = nombreMain.getText().toString().trim();
        strPass = passMain.getText().toString().trim();
        status = statusMain.getText().toString().trim();

        outState.putString("nombre", strNombre);
        outState.putString("pass", strPass);
        outState.putString("status", status);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        strNombre = savedInstanceState.getString("nombre", "");
        strPass = savedInstanceState.getString("pass", "");
        status = savedInstanceState.getString("status", "");

        Log.e(TAG, strNombre + " sss" +  strPass + " sssss"  + status);

        nombreMain.setText(strNombre);
        passMain.setText(strPass);
        statusMain.setText(status);
    }
}
