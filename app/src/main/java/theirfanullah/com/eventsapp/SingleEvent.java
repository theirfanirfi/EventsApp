package theirfanullah.com.eventsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

public class SingleEvent extends AppCompatActivity {
    String event_id = "";
    Bundle bundle;
    DbHelper db;
    AlertDialog.Builder alert; //alert dialog for confirmation, while deleting an event.

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);
        db = new DbHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bundle = getIntent().getExtras();
        String eventn = bundle.getString("event_name");
        event_id = bundle.getString("event_id");
        toolbar.setTitle(eventn);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        alert = new AlertDialog.Builder(this);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab); // this is the floating action button initialized for adding note to the event.


        FloatingActionButton editEvent = (FloatingActionButton) findViewById(R.id.EditBtn); // this is the floating action button initialized for editing event.

        FloatingActionButton deleteEvent = (FloatingActionButton) findViewById(R.id.deleteBtn); // this is the floating action button initialized for deleting the event and its notes.




        //this is the click event set on the @fab floating action button. in this event a new intent will be started for adding a note to
        //the event.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    // @AddNote.class is a note adding activity.
                    Intent note = new Intent(SingleEvent.this, AddNote.class);
                    //@event_id is passed to the AddNote class.
                    note.putExtra("event_id",event_id);
                    startActivity(note);
                }
                catch(Exception ex)
                {
                    Toast.makeText(SingleEvent.this,ex.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });

        //this is click event listener for @editBtn which will edit a current event.

        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent edit = new Intent(SingleEvent.this, EditEvent.class);
             // @event_id is passed to the @EditEvent.class activity for updating the current event.
                edit.putExtra("event_id",event_id);
                startActivity(edit);
            }
        });

        //this is the click listener for @deleteEvent
        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                alert.setTitle("Action");
                alert.setMessage("All the notes of the event will be deleted too. Are you sure to continue?");
                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDeleted = db.deleteEventAndItsNotes(event_id);
                        if(isDeleted)
                        {
                            Snackbar.make(v,"Event and its Notes are deleted",Snackbar.LENGTH_LONG).show();
                            Thread t = new Thread()
                            {
                                @Override
                                public void run() {
                                    super.run();
                                    try
                                    {
                                        sleep(3000);
                                        finish();
                                    }
                                    catch(Exception e)
                                    {

                                    }


                                }
                            };
                            t.start();

                        }
                        else
                        {

                            Snackbar.make(v,"Error Ocurred in deleting the event. Please try again.",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
           alert.show();
            }

        });

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position)
            {
                case 0:
                    SingleEventFragment singleEventFragment = new SingleEventFragment();
                    singleEventFragment.setArguments(bundle);
                    return singleEventFragment;
                case 1:
                    EventNotesFragment eventNotesFragment = new EventNotesFragment();
                    eventNotesFragment.setArguments(bundle);
                    return eventNotesFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages/tabs.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Event";
                case 1:
                    return "Notes";
            }
            return null;
        }
    }
}
