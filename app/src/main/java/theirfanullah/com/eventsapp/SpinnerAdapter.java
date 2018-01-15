package theirfanullah.com.eventsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Irfan Ullah on 15/01/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    int[] images;
    public SpinnerAdapter(@NonNull Context context, ArrayList<String> colorName, int[] images) {
        super(context, R.layout.spinner_custom_layout, colorName);
        this.context = context;
        this.images = images;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);
        TextView cn = (TextView) row.findViewById(R.id.colorCode);
        String c = getItem(position);
        ImageView iv = (ImageView) row.findViewById(R.id.colorImageView);
        cn.setText(c);
        iv.setImageResource(this.images[position]);
        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View row = inflater.inflate(R.layout.spinner_custom_layout, parent, false);
        TextView cn = (TextView) row.findViewById(R.id.colorCode);
        String c = getItem(position);
        cn.setText(c);
        ImageView iv = (ImageView) row.findViewById(R.id.colorImageView);
        iv.setImageResource(this.images[position]);
        return row;
    }
}
