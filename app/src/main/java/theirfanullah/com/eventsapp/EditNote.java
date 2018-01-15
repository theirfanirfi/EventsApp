package theirfanullah.com.eventsapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/*
    # In this @AddEvent Class/Activity, two interfaces are implemented
    # 1. @DatePicker.Dialog.OnDateSetListener : When date is selected so the selected date will be recieved in this listener
    # 2. @TimePickerDialog.OnTimeSetListener : When time is selected so the selected time will be recieved/listened in this listener
    # The listener methods of the above two listener interfaces are implemented in the class
 */
public class EditNote extends AppCompatActivity {
    /*
    #The following are the reference objects of the buttons, which are initialized in the @OnCreate method
    @addNote is the button for adding the complete details of the notes to the database @notes_tbl Table
     */
    Button editNote;
    String event_id = "";
    int dayy,month,year;
    String cDate = "";
    String note_id = "";
    Bundle bundle;

    /*
    # The following are the reference objects of the text fields which are initialized in the @OnCreate method.
    @notetitle  : Note title
    @notedescription  : Note Description
     */
    EditText notetitle, notedcription;

    Calendar calendar; // Calendar for getting the current date - while adding the note

    DbHelper Db; //Reference object of the Database class, in this class CRUD operations of the App are stored.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        bundle = getIntent().getExtras();
        note_id = bundle.getString("note_id");



        /*
        # The following block of code is for making the activity a Dialog.
        # Which will open on the top of the @MainActivity
         */

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width*.92),(int) (height*.92));

        /*
        # The reference objects are initialized in the following block
         */

        editNote = (Button) findViewById(R.id.editNoteBtn);
        calendar = Calendar.getInstance();
        dayy = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        month = month + 1;
        year = calendar.get(Calendar.YEAR);
        cDate = Integer.toString(dayy)+"/"+Integer.toString(month)+"/"+Integer.toString(year);
        //Toast.makeText(AddNote.this,cDate,Toast.LENGTH_LONG).show();
        Db = new DbHelper(this);
        notetitle = (EditText) findViewById(R.id.editnotetitle);
        notedcription = (EditText) findViewById(R.id.editnotedescription);

        Cursor res = Db.fetchNoteById(note_id);
        if(res.getCount() > 0 )
        {
            res.moveToFirst();
            notetitle.setText(res.getString(1));
            notedcription.setText(res.getString(2));
        }
        editNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Db.NOTE_TITLE, notetitle.getText().toString());
                contentValues.put(Db.NOTE_DESCRIPTION, notedcription.getText().toString());
                contentValues.put(Db.NOTE_DATE,cDate);
                boolean isInserted = Db.updateNote(contentValues, note_id);
                if(isInserted)
                {
                    Toast.makeText(EditNote.this,"Note updated.",Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    Toast.makeText(EditNote.this,"Error ocurred in updating the note. Please try again.",Toast.LENGTH_LONG).show();

                }

            }
        });


    }

}
