package com.example.vggananesh.frdtracker;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adp extends ArrayAdapter<adapt>
{
    private final Context context;
    private final ArrayList<adapt> val;

    public adp(Context context, ArrayList<adapt> val) {
        super(context,R.layout.conlist,val);
        this.context = context;
        this.val = val;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ml=inflater.inflate(R.layout.conlist,parent,false);
        TextView tv1 =(TextView) ml.findViewById(R.id.tv1);
        TextView tv2 =(TextView) ml.findViewById(R.id.tv2);
        ImageView iv =(ImageView) ml.findViewById(R.id.iv1);
        tv1.setText(val.get(position).getName());
        tv2.setText(val.get(position).getPhno());
        return ml;
    }

}
