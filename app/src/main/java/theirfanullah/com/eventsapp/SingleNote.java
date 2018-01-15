 package theirfanullah.com.eventsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

 public class SingleNote extends AppCompatActivity {
    Bundle bundle;
    String note_id = ""; //in this variable the @note_id, sent by the previous activity, will be stored.
    DbHelper db; //reference object of the @DbHelper #Database class. This object will be initialized in @onCreate method.
     TextView title, description, datee, idd; // these are the reference objects of the text view class and will be initialize in @onCreate method
    String toolbartitle = "";
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DbHelper(this); // @db variable of the @DbHelper class is initialized.
        bundle = getIntent().getExtras();
        note_id = bundle.getString("note_id"); //so the note is pulled out from the bundle and stored in the @note_id variable

        title = (TextView) findViewById(R.id.nt); //textview for @note_title
        description = (TextView) findViewById(R.id.nde); // for @note_description
        datee = (TextView) findViewById(R.id.nda); //for @note_date
        idd = (TextView) findViewById(R.id.nid); //for @note_id
        toolbar = (Toolbar) findViewById(R.id.toolbar);





        // now a note matching the @note_id will be fetched from the database.

        Cursor res = db.fetchNoteById(note_id); // it is a method into the @DbHelper class - fetches note from database by @note_id

        if(res.getCount() > 0)
        {
            res.moveToFirst(); //the cursor will be moved to the first row, so there will be no need of looping.

            //now we will assign the @note details to their respective @TextViews.
            idd.setText(res.getString(0)); // @note_id
            idd.setVisibility(View.GONE); //the textview of the @note_id will be made hidden, because we will need only during deletion and updation.
            title.setText(res.getString(1)); //@note_title
            toolbartitle = res.getString(1);
            description.setText(res.getString(2)); //@note_description
            datee.setText(res.getString(3)); //@note_date
        }
        else
        {
            Toast.makeText(SingleNote.this,"No note exist", Toast.LENGTH_LONG).show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(toolbartitle.length()> 11) {
            getSupportActionBar().setTitle(toolbartitle.substring(0, 11));
        }
        else
        {
            getSupportActionBar().setTitle(toolbartitle);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu_single_note, menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.deleteNote:
                AlertDialog.Builder builder = new AlertDialog.Builder(SingleNote.this);
                builder.setTitle("Delete Action");
                builder.setMessage("Are you sure to delete the note?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     boolean isDeleted =   db.deleteNoteById(note_id);
                     if(isDeleted)
                     {
                         Toast.makeText(SingleNote.this,"Noted Deleted",Toast.LENGTH_LONG).show();
                         final Thread tt  = new Thread()
                         {
                             @Override
                             public void run() {
                                 super.run();
                                 try {

                                     sleep(2500);
                                     finishActivityAfterDeletion(); // function is called to finish the current activity.
                                    // NavUtils.navigateUpFromSameTask(SingleNote.this);

                                 } catch (InterruptedException e) {
                                     //e.printStackTrace();
                                 }
                             }
                         };
                         tt.start();

                     }
                     else
                     {
                         Toast.makeText(SingleNote.this,"Error occurred in deleting the note. Try again later.",Toast.LENGTH_LONG).show();

                     }
                    }
                });
                builder.show();
                break;
            case R.id.editNote:

                Intent edit = new Intent(SingleNote.this,EditNote.class);
                edit.putExtra("note_id",note_id);
                startActivity(edit);
                break;
        }
         return true;
     }

     public void finishActivityAfterDeletion()
     {
         finish();
     }


     @Override
     protected void onResume() {
         super.onResume();

         Cursor res = db.fetchNoteById(note_id); // it is a method into the @DbHelper class - fetches note from database by @note_id

         if(res.getCount() > 0)
         {
             res.moveToFirst(); //the cursor will be moved to the first row, so there will be no need of looping.

             //now we will assign the @note details to their respective @TextViews.
             idd.setText(res.getString(0)); // @note_id
             idd.setVisibility(View.GONE); //the textview of the @note_id will be made hidden, because we will need only during deletion and updation.
             title.setText(res.getString(1)); //@note_title
             description.setText(res.getString(2)); //@note_description
             datee.setText(res.getString(3)); //@note_date
         }
         else
         {
             Toast.makeText(SingleNote.this,"No note exist", Toast.LENGTH_LONG).show();
         }
     }
 }
