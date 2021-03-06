package theirfanullah.com.eventsapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
    # In this @AddEvent Class/Activity, two interfaces are implemented
    # 1. @DatePicker.Dialog.OnDateSetListener : When date is selected so the selected date will be recieved in this listener
    # 2. @TimePickerDialog.OnTimeSetListener : When time is selected so the selected time will be recieved/listened in this listener
    # The listener methods of the above two listener interfaces are implemented in the class
 */
public class EditEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    /*
    #The following are the reference objects of the buttons, which are initialized in the @OnCreate method
    @cpbbtn is the button for choosing event color.
    @ebtn is the button for choosing event date
    @etbn is the button for choosing event timing
    @editEvent is the button for editing the complete details of the event in the database @events_tbl Table
     */
    Button cpbtn, ebtn, etbtn, editEvent;
    Spinner spinner;
    String weekNumber= "";

    /*
    # The following are the reference objects of the text fields which are initialized in the @OnCreate method.
    @ename  : Event name
    @electurer : Lecturer
    @edescription  : Event Description
     */
    EditText ename, electurer,elocation,edescription;

    String choosenColor = ""; //String variable for storing the value of a choosen color.
    int day, month, year;
    String sDay, sMonth, sYear, dayInString, completeDate; //variables for storing the values of choosen date, year, month and Day name
    Calendar calendar; // Calendar for setting the default date in the @DatePickerDialog
    /*
    # The following is the Array of strings, in which the name of the days are stored.
    # When a date is selected in a @DatePickerDialog, so the @dayOfMonth variable will be compared to this array for getting Day name
     */
    public static final String[] DAYS = {"Mon", "Tues", "Wed", "Thu", "Fri", "Sat","Sun"};
    DbHelper Db; //Reference object of the Database class, in this class CRUD operations of the App are stored.

    String event_id = ""; //this variable will recieve the @event_id sent from the previous @SingleEvent Activity for editing.
    //it is initialized in the global scope that it may be accessible from all the methods of this Activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayList<String> l = new ArrayList<>();

        l.add("#000000"); //black
        l.add("#ff0000"); //red
        l.add("#8b8d7a"); //stone
        l.add("#000080");  //navy
        l.add("#e3d6b6");  //latte
        l.add("#7fffd4");  //aqua
        l.add("#0e8c20");  //green
        l.add("#b2e5cb");  //mint
        l.add("#b2e5cb");  //dusty blue
        l.add("#a6c0c5"); //duck egg
        l.add("#834f83"); //plum
        l.add("#b3b3c8"); //Lavendar
        l.add("#e4717a"); //melon
        l.add("#f5fe00"); //yellow

        int[] colorimages = {R.drawable.blackcolor,R.drawable.red,R.drawable.stone,R.drawable.navy,R.drawable.latte,R.drawable.aqua,
                R.drawable.green,R.drawable.mint,R.drawable.dustyblue,R.drawable.duckegg,R.drawable.plum,R.drawable.lavendar,R.drawable.melon,
                R.drawable.yellow
        };
        ArrayAdapter lad = new SpinnerAdapter(this,l,colorimages);
        spinner.setAdapter(lad);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView ctxt = (TextView) view.findViewById(R.id.colorCode);
                // Toast.makeText(AddEvent.this,ctxt.getText().toString(),Toast.LENGTH_LONG).show();
                choosenColor = ctxt.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        ebtn = (Button) findViewById(R.id.event_date);
        etbtn = (Button) findViewById(R.id.event_time);
        editEvent = (Button) findViewById(R.id.editEventBtn);
        calendar = Calendar.getInstance();
        Db = new DbHelper(this);
        ename = (EditText) findViewById(R.id.eventname);
        electurer = (EditText) findViewById(R.id.eventlecturer);
        elocation = (EditText) findViewById(R.id.eventlocation);
        edescription = (EditText) findViewById(R.id.eventdescription);

        // The extras sent from an activity are recieved in @Bundle class object.
        Bundle bundle = getIntent().getExtras(); //all the extras sent by the previous activit will be stored into the @Bundle class object.
        event_id = bundle.getString("event_id"); // here @event_id is pulled out from the @bundle and assigned to @event_id variable.
        /*
        # Now we will use this @event_id variable to fetch an event from the database by the @event_id and edit it.
         */

        try {
            Cursor res = Db.fetchEventById(event_id);
            if (res.getCount() > 0) {
                res.moveToFirst();
                choosenColor = res.getColumnName(8);
                ebtn.setText(res.getString(3));
                completeDate = res.getString(3);
                dayInString = res.getString(7);
                etbtn.setText(res.getString(6));
                ename.setText(res.getString(1));
                edescription.setText(res.getString(2));
                electurer.setText(res.getString(4));
                elocation.setText(res.getString(5));
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(EditEvent.this,ex.toString(),Toast.LENGTH_LONG).show();
        }

        /*
        # The following block of code is for initializing @DatePickerDialog,
        # The @DatePickerDialog takes @5 argument
        # 1. @Context : which is this class @AddEvent
        # 2. @Listener: which is implemented in this class, so we will just pass the class name @AddEvent.this
        # 3. @Year: this argument specify that what should be the default year to display, in this case
        # I have taken the Current year from the @Calender class and pass it to the @DatePickerDialog
        # 4. @Month: specify the default month of the year. I have taken it from the @Calendar class and passed it to the @DatePickerDialog
        # 5. @DAY_OF_MONTH: specify the default day of the month. Similarly, it is taken from the calender and passed to @DatePickerDialog

        --- NOTE ----
        # If you don't want to pass the @Year, @Mont, @Date: so instead of the @3, @4, @5 arguments, you can pass 0s (zeros)
        # But the default date will start form @1900 then, which is pretty akward because the user will then scroll form @1900 to @2018
         */

        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                EditEvent.this, EditEvent.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));



        //in the following block of code the @TimePickerDialog is initialized with default arguments.
        // this dialog will be displayed when the @Event Time button is clicked.

        final TimePickerDialog timePickerDialog = new TimePickerDialog(
                EditEvent.this, EditEvent.this,11,33,false);


        /*
        # When a color is choosen so the following block of code will be called.
        # in this block of code, the value of a choosen color will be recieved and set as a text and background color of the button
         */


        //when the @Event Date button is clicked so the following listener will be called and the @DatePickerDialog will be displayed
        ebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //for showing the @DatePickerDialog
                datePickerDialog.show();
            }
        });

        //when the @Event Time button is clicked, so the following listener will be called and @TimePickerDialog will be displayed
        etbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //for showing @TimePickerDialog
                timePickerDialog.show();
            }
        });

        /*
            # this is the edit Button click event listener.
            # when the button is clicked, so all the values are taken from the text fields and putted into the @ContentValues class object
            # @ContentValues class object takes two paramenters, 1: column Name of the database table 2: Value to be inserted
         */
        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                // here @Db.EVENT_NAME is the column name declared in the @DbHelper class and its value is @event_name
                // and it is column in the database @event_tbl Table too.
                contentValues.put(Db.EVENT_NAME,ename.getText().toString());
                contentValues.put(Db.EVENT_LECTURER,electurer.getText().toString());
                contentValues.put(Db.EVENT_LOCATION,elocation.getText().toString());
                contentValues.put(Db.EVENT_DESCRIPTION,edescription.getText().toString());
                contentValues.put(Db.EVENT_COLOR,choosenColor);
                contentValues.put(Db.EVENT_DATE,completeDate);
                contentValues.put(Db.EVENT_DAY,dayInString);
                contentValues.put(Db.EVENT_TIME,etbtn.getText().toString());
                contentValues.put(Db.EVENT_WEEK,weekNumber);

                //when all the values are putted in the @contentValues object then this object is passed to the function declared
                // in the @DbHelper class.
                boolean isUpdated = Db.updateEvent(contentValues,event_id);
                // when the the updation take places so the function will return a @boolean value. it may be @false or @true
                if(isUpdated)
                {
                    Toast.makeText(EditEvent.this,"Event Updated.",Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    Toast.makeText(EditEvent.this,"Error ocurred in Updating the event. Please try again.",Toast.LENGTH_LONG).show();

                }



            }
        });




    }

    // when date is select so this event will be called.
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month,dayOfMonth-1);
        int s = gregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);
        sDay = Integer.toString(dayOfMonth);

        int w = gregorianCalendar.get(GregorianCalendar.WEEK_OF_YEAR);
        weekNumber = Integer.toString(w);
        sMonth = Integer.toString(month+1);
        sYear = Integer.toString(year);
        dayInString = DAYS[s-1];
        completeDate = sDay+"/"+sMonth+"/"+sYear;
        ebtn.setText(sDay+"/"+sMonth+"/"+sYear+ " "+DAYS[s-1]);
    }

    //when time is selected os the following event will be called.
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String AM_PM = (hourOfDay < 12) ? "AM" : "PM";
        etbtn.setText(Integer.toString(hourOfDay)+" : "+Integer.toString(minute)+" "+AM_PM);
    }
}
