package theirfanullah.com.eventsapp;

/**
 * Created by Irfan Ullah on 03/01/2018.
 */

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SingleEventFragment extends Fragment {

    DbHelper db;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single_event, container, false);
        TextView desc = (TextView) rootView.findViewById(R.id.Edesc);
        TextView lect = (TextView) rootView.findViewById(R.id.Elect);
        TextView loc = (TextView) rootView.findViewById(R.id.Eloc);
        TextView eventDayDate = (TextView) rootView.findViewById(R.id.Edateday);
        TextView tiime = (TextView) rootView.findViewById(R.id.Etime);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.ELayout);

       String eid = getArguments().getString("event_id");
        try
        {
            db = new DbHelper(getContext());


           Cursor cursor = db.fetchEventById(eid);

            if(cursor.getCount()> 0)
            {

                cursor.moveToFirst();
                linearLayout.setBackgroundColor(Color.parseColor(cursor.getString(8)));
                desc.setText(cursor.getString(2));
                lect.setText(cursor.getString(4));
                loc.setText(cursor.getString(5));
                eventDayDate.setText(cursor.getString(3));
                tiime.setText(cursor.getString(6));

            }

        }
        catch(Exception ex)
        {
            Toast.makeText(getContext(), ex.toString(),Toast.LENGTH_LONG).show();
        }


        return rootView;
    }
}
