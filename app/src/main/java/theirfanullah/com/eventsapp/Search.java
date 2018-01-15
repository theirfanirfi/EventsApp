package theirfanullah.com.eventsapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    ListView lv;
    DbHelper db;
    String sdate;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> lsName, lsDesc, lsDate,lsId, lsColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle bundle = getIntent().getExtras();
        sdate = bundle.getString("event_date");
        lv = (ListView) findViewById(R.id.searchList);
        db = new DbHelper(this);
        lsName = new ArrayList<>();
        lsDesc = new ArrayList<>();
        lsDate = new ArrayList<>();
        lsId = new ArrayList<>();
        lsColor = new ArrayList<>();
        Cursor cursor = db.searchEvent(sdate);
        try
        {



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
            listAdapter = new MyAdapter(Search.this,android.R.layout.simple_list_item_1, lsId , lsName, lsDesc, lsDate, lsColor);
            lv.setAdapter(listAdapter);
        }
        catch(Exception ex)
        {
            Toast.makeText(Search.this, ex.toString(),Toast.LENGTH_LONG).show();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView eventId = view.findViewById(R.id.event_id);
                TextView t = view.findViewById(R.id.event_title);
                String Id  =eventId.getText().toString();
                Intent single = new Intent(Search.this,SingleEvent.class);
                single.putExtra("event_id",Id);
                single.putExtra("event_name",t.getText().toString());
                startActivity(single);
            }
        });

    }
}
