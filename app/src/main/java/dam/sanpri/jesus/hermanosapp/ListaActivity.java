package dam.sanpri.jesus.hermanosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ListaActivity extends AppCompatActivity {
    ListView lista;
    BroAdapter bropter;
    DatabaseAdapter db;
    private final String TAG = "LISTACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        //creo objeto db para usar su metodo que da la lista de hermanos
        db = new DatabaseAdapter(this);
        //cojo el ListView del layout de la actividad
        lista = (ListView)findViewById(R.id.lista);
        //creo objeto del Adapter reimplementado y optimizado
        bropter = new BroAdapter(db.getBros(), this);
        Log.i(TAG, "ANTES SET ADAPTER" + db.getBros().get(0).getNombre());

        //a la lista le pongo el listAdapter reimplementado que le dara todos los elementos(hermanos) a listar y mostrar
        lista.setAdapter(bropter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
