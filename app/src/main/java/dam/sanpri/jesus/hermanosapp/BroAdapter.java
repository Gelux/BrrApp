package dam.sanpri.jesus.hermanosapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jes on 08/12/2017.
 */

public class BroAdapter extends BaseAdapter {

    private List<Hermano> hermanoInfo;
    private LayoutInflater inflater;

    BroAdapter(List<Hermano> hermanoInfo, Context context){
        this.hermanoInfo = hermanoInfo;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return hermanoInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return hermanoInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View recycleView, ViewGroup parent) {
        View layout = recycleView;
        HermanoViewHolder hermanoHolder = null;
        if(layout == null){
            layout = inflater.inflate(R.layout.list_hermanos, parent, false);
            hermanoHolder = new HermanoViewHolder(layout);
            layout.setTag(hermanoHolder);
        }else{
            hermanoHolder = (HermanoViewHolder)layout.getTag();
        }

        hermanoHolder.nombre.setText(hermanoInfo.get(position).getNombre());
        hermanoHolder.estado.setText(hermanoInfo.get(position).getStatus());

        return layout;
    }
}
