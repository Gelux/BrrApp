package dam.sanpri.jesus.hermanosapp;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Jes on 10/12/2017.
 */

public class HermanoViewHolder {
    TextView nombre, estado;

    HermanoViewHolder(View view){
        nombre = (TextView)view.findViewById(R.id.firstLine);
        estado = (TextView)view.findViewById(R.id.secondLine);
    }
}
