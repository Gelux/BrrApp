package dam.sanpri.jesus.hermanosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    private EditText adminName, adminPass;
    private DatabaseAdapter database;
    private String strName, strPass;
    private Toast toastFiller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DatabaseAdapter(this);

        setContentView(R.layout.admin_layout);

        getViews();
    }

    private void getViews(){
        adminName = (EditText) findViewById(R.id.nombreAdmin);
        adminPass = (EditText) findViewById(R.id.passAdmin);
    }

    //Añade usuario si nombre no existe en db
    public void addUser(View view) {
        //Guardo variables de los editText con las que voy a tratar de añadir usuario
        strName = adminName.getText().toString().trim();
        strPass = adminPass.getText().toString().trim();

        //Compruebo que el usuario no exista, e incorporo el usuario en caso de que no
        //No se si se deberia comprobar mejor en la db, igual si ¯\_(ツ)_/¯
        if(!database.checkBroName(strName)){
            toastFiller = Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT);
            toastFiller.show();
        }else{
            database.crearHermano(strName, strPass);
            toastFiller = Toast.makeText(getApplicationContext(), "Usuario añadido", Toast.LENGTH_SHORT);
            toastFiller.show();
        }
    }

    //elimina usuario si existe nombre en db
    public void deleteUser(View view) {

        strName = adminName.getText().toString().trim();

        if(!database.checkBroName(strName)){
            database.eliminarHermano(strName);
            Toast.makeText(getApplicationContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Usuario no existe", Toast.LENGTH_SHORT).show();
        }
    }

    //Cambia contraseña si nombre existe en db
    public void changePass(View view) {
        //Guardo variables de los editText con las que voy a tratar de añadir usuario
        strName = adminName.getText().toString().trim();
        strPass = adminPass.getText().toString().trim();

        //Compruebo que el usuario exista, cambio la contraseña en caso afirmativo
        if(!database.checkBroName(strName)){
            database.updatePass(strName, strPass);

            toastFiller = Toast.makeText(getApplicationContext(), "Contraseña cambiada", Toast.LENGTH_SHORT);
            toastFiller.show();
        }else{
            toastFiller = Toast.makeText(getApplicationContext(), "No se ha podido cambiar la contraseña", Toast.LENGTH_SHORT);
            toastFiller.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        strName = adminName.getText().toString().trim();
        strPass = adminPass.getText().toString().trim();

        outState.putString("nombre", strName);
        outState.putString("pass", strPass);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        strName = savedInstanceState.getString("nombre", "");
        strPass = savedInstanceState.getString("pass", "");

        adminName.setText(strName);
        adminPass.setText(strPass);
    }
}
