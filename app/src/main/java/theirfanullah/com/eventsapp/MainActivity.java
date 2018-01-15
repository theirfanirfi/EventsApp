package theirfanullah.com.eventsapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

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
    Button btn;
    CoordinatorLayout layout;
    DatePicker datePicker;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int day, month, year;
    String sDay, sMonth, sYear, dayInString, completeDate; //variables for storing the values of choosen date, year, month and Day name


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        datePicker = new DatePicker(MainActivity.this);
        layout = (CoordinatorLayout) findViewById(R.id.main_content);
        calendar = Calendar.getInstance();
         datePickerDialog = new DatePickerDialog(
                MainActivity.this, MainActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent add = new Intent(MainActivity.this,AddEvent.class);
                startActivity(add);
            }
        });

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.fab2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });



    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month,dayOfMonth-1);
        int s = gregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);
        sDay = Integer.toString(dayOfMonth);
        sMonth = Integer.toString(month+1);
        sYear = Integer.toString(year);
        completeDate = sDay+"/"+sMonth+"/"+sYear;
        Intent searchActivity = new Intent(MainActivity.this,Search.class);
        searchActivity.putExtra("event_date",completeDate);
        startActivity(searchActivity);

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch(position)
            {
                case 0:
                    MonFragment mon = new MonFragment();
                    return mon;
                case 1:
                    TueFragment tue = new TueFragment();
                    return tue;
                case 2:
                    WedFragment wed = new WedFragment();
                    return wed;
                case 3:
                    ThuFragment thu = new ThuFragment();
                    return thu;
                case 4:
                    FriFragment fri = new FriFragment();
                    return fri;
                case 5:
                    SatFragment sat = new SatFragment();
                    return sat;
                case 6:
                    SunFragment sun = new SunFragment();
                    return sun;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Mon";
                case 1:
                    return "Tue";
                case 2:
                    return "Wed";
                case 3:
                    return "Thu";
                case 4:
                    return "Fri";
                case 5:
                    return "Sat";
                case 6:
                    return "Sun";

            }
            return null;
        }
    }
}
