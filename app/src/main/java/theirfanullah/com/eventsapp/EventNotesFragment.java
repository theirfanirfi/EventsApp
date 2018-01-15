package theirfanullah.com.eventsapp;

/**
 * Created by Irfan Ullah on 03/01/2018.
 */

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventNotesFragment extends Fragment {

    DbHelper db;
    //the following are the arraylist reference.
    ArrayList<String> lsId;
    ArrayList<String> lsNoteTitle;
    ArrayList<String> lsNoteDescription;
    ArrayList<String> lsDate;
    ArrayAdapter<String> adapter;
    ListView lv;
    String eid = "";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //layout of the fragment is set in the following line of code.
        View rootView = inflater.inflate(R.layout.fragment_event_note, container, false);

        lv = (ListView)  rootView.findViewById(R.id.notelist); //listview object in initialized
        eid = getArguments().getString("event_id");
        try
        {
            db = new DbHelper(getContext());
            //the arraylist objects are initialized and the values fetched from the database table will be added to the array lists
            lsId = new ArrayList<>();
            lsNoteDescription = new ArrayList<>();
            lsNoteTitle = new ArrayList<>();
            lsDate = new ArrayList<>();

           Cursor cursor = db.fetchNotesByEventId(eid);

            if(cursor.getCount()> 0)
            {
                while(cursor.moveToNext())
                {
                    //the values are fetched by index numbers and stored into the arraylist
                    // 0: for first and 1 for second column and so on.
                    lsId.add(cursor.getString(0));
                    lsNoteTitle.add(cursor.getString(1));
                    lsNoteDescription.add(cursor.getString(2));
                    lsDate.add(cursor.getString(3));
                }


            }
            //this is the custom adapter for Notes displaying list view.
            //the arraylists are passed to it.
            adapter = new NoteAdapter(getContext(),lsId,lsNoteTitle,lsNoteDescription,lsDate);
            //the adapter is then set to the listview.
            lv.setAdapter(adapter);
        }
        catch(Exception ex)
        {
            Toast.makeText(getContext(), ex.toString(),Toast.LENGTH_LONG).show();
        }

        //click listener is set on listview items. when an item is clicked so the noteId stored in a text view is taken
        // and by that id a @note content is loaded into the @SingleNote.class activity.
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nid = (TextView) view.findViewById(R.id.nId);
                Intent singleNote = new Intent(getContext(),SingleNote.class);
                singleNote.putExtra("note_id",nid.getText().toString());
                startActivity(singleNote);
               // Toast.makeText(getContext(), nid.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }

    //when the activity resumes, so the follwoing fucntion will be called.
    //the arraylist having data will be cleared first
    //then data will be fetched again from the database and stored into the arraylists
    // it is for the reason to ensure real time data in case of
    //any changes made (addition/deletion/updation)
    @Override
    public void onResume() {
        super.onResume();
        lsId.clear();
        lsNoteTitle.clear();
        lsDate.clear();
        lsNoteDescription.clear();
        Cursor cursor = db.fetchNotesByEventId(eid);

        if(cursor.getCount()> 0)
        {
            while(cursor.moveToNext())
            {
                lsId.add(cursor.getString(0));
                lsNoteTitle.add(cursor.getString(1));
                lsNoteDescription.add(cursor.getString(2));
                lsDate.add(cursor.getString(3));
            }


        }
        adapter = new NoteAdapter(getContext(),lsId,lsNoteTitle,lsNoteDescription,lsDate);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }
}
