package theirfanullah.com.eventsapp;

/**
 * Created by Irfan Ullah on 03/01/2018.
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SatFragment extends Fragment {
    public final static String DAY = "Sat";
    String WEEK = "";
    Calendar calendar;
    GregorianCalendar gregorianCalendar;
    ListView lv;
    DbHelper db;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> lsName, lsDesc, lsDate,lsId, lsColor;
    int i = 0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        lsName = new ArrayList<>();
        lsDesc = new ArrayList<>();
        lsDate = new ArrayList<>();
        lsId = new ArrayList<>();
        lsColor = new ArrayList<>();

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        gregorianCalendar = new GregorianCalendar(year, month,dayOfMonth-1);
        int w = gregorianCalendar.get(GregorianCalendar.WEEK_OF_YEAR);
        WEEK = Integer.toString(w);
        try
        {
            db = new DbHelper(getContext());
            lv = (ListView) rootView.findViewById(R.id.eventsList);

            Cursor cursor = db.fetchEventsByDayName(DAY, WEEK);

            if(cursor.getCount()> 0)
            {

                while(cursor.moveToNext())
                {


                    lsId.add(cursor.getString(0));
                    lsName.add(cursor.getString(1));
                    lsDesc.add(cursor.getString(2));
                    lsDate.add(cursor.getString(3));
                    lsColor.add(cursor.getString(8));

                }
            }
            listAdapter = new MyAdapter(getContext(),android.R.layout.simple_list_item_1, lsId , lsName, lsDesc, lsDate, lsColor);
            lv.setAdapter(listAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView eventId = view.findViewById(R.id.event_id);
                    TextView t = view.findViewById(R.id.event_title);
                    String Id  =eventId.getText().toString();
                    Intent single = new Intent(getContext(),SingleEvent.class);
                    single.putExtra("event_id",Id);
                    single.putExtra("event_name",t.getText().toString());
                    startActivity(single);
                }
            });
        }
        catch(Exception ex)
        {
            Toast.makeText(getContext(), ex.toString(),Toast.LENGTH_LONG).show();
            //Log.e("error: ",ex.toString());
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        lsId.clear();
        lsName.clear();
        lsDesc.clear();
        lsDate.clear();
        lsColor.clear();
        try
        {

            Cursor cursor = db.fetchEventsByDayName(DAY, WEEK);

            if(cursor.getCount()> 0)
            {

                while(cursor.moveToNext())
                {


                    lsId.add(cursor.getString(0));
                    lsName.add(cursor.getString(1));
                    lsDesc.add(cursor.getString(2));
                    lsDate.add(cursor.getString(3));
                    lsColor.add(cursor.getString(8));

                }
            }
            listAdapter = new MyAdapter(getContext(),android.R.layout.simple_list_item_1, lsId , lsName, lsDesc, lsDate, lsColor);
            listAdapter.notifyDataSetChanged();
            lv.setAdapter(listAdapter);
        }
        catch(Exception ex)
        {
            Toast.makeText(getContext(), ex.toString(),Toast.LENGTH_LONG).show();
        }
    }

}
