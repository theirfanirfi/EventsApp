package theirfanullah.com.eventsapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Irfan Ullah on 07/01/2018.
 */

public class MyAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> lsName, lsDesc, lsDate,lsId, lsColor;
    public MyAdapter(@NonNull Context context,int textViewResourceId, ArrayList<String> Id ,ArrayList<String> name, ArrayList<String> desc, ArrayList<String> date, ArrayList<String> color) {
        super(context, R.layout.custom_list_view_row, name);
        this.context = context;
        this.lsName = name;
        this.lsDesc = desc;
        this.lsDate = date;
        this.lsId = Id;
        this.lsColor = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View custom = inflater.inflate(R.layout.custom_list_view_row, parent, false);
        String title = getItem(position);
        LinearLayout layout = (LinearLayout) custom.findViewById(R.id.rlayout);
        layout.setBackgroundColor(Color.parseColor(this.lsColor.get(position)));
        TextView titl = (TextView) custom.findViewById(R.id.event_title);
        TextView desc = (TextView) custom.findViewById(R.id.event_desc);
        TextView date = (TextView) custom.findViewById(R.id.event_dat);
        TextView eid = (TextView) custom.findViewById(R.id.event_id);
        titl.setText(lsName.get(position));
        desc.setText(lsDesc.get(position));
        date.setText("Date: "+lsDate.get(position));
        eid.setText(lsId.get(position));
        eid.setBackgroundColor(Color.parseColor(this.lsColor.get(position)));
        eid.setVisibility(View.GONE);

        return custom;
    }
}
