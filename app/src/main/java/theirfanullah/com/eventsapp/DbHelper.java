package theirfanullah.com.eventsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Irfan Ullah on 06/01/2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    /*
    # Database name of the App is events.db
    # There are total tables in the database @event_tbl and @note_tbl
    # These two table are in relation with the help of a Foreign key @event_id
    # The following are the columns of the events_tbl
    @event_id (Primary key)
    @event_name
    @event_description
    @event_color (in this field the value of slected color will be stored.)
    @event_date
    @event_day
    @event_lecutrer
    @event_location
    @event_time

    #The following are the columns of the the second table @note_tbl

    @note_id (Primary key)
    @note_title
    @note_description
    @note_date
    @event_id (Foreign key)

     */
    public static final String DB_NAME = "events.db";
    public static final String EVENTS_TBL = "events_tbl";
    public static final String NOTE_TBL = "note_tbl";

    //Columns of the Events_tbl
    public static final String EVENT_ID = "event_id";
    public static final String EVENT_NAME = "event_name";
    public static final String EVENT_DESCRIPTION = "event_description";
    public static final String EVENT_COLOR = "event_color";
    public static final String EVENT_LECTURER = "event_lecturer";
    public static final String EVENT_LOCATION = "event_location";
    public static final String EVENT_TIME = "event_time";
    public static final String EVENT_DATE = "event_date";
    public static final String EVENT_DAY = "event_day";
    public static final String EVENT_WEEK = "event_week";

    //columns of the second @note_tbl

    public static final String NOTE_ID = "note_id";
    public static final String NOTE_TITLE = "note_title";
    public static final String NOTE_DESCRIPTION = "note_description";
    public static final String NOTE_DATE = "note_date";
    public static final String EVENT_ID_FK = "event_id";


    //@event_tbl creation sql query

    public static final String CREATE_EVENTS_TBL = "CREATE TABLE IF NOT EXISTS "+EVENTS_TBL+
            "("+EVENT_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            EVENT_NAME+" VARCHAR(200), "+
            EVENT_DESCRIPTION+ " TEXT, "
            +EVENT_DATE+ " VARCHAR(255), "
            +EVENT_LECTURER +" VARCHAR(100), "
            +EVENT_LOCATION+" VARCHAR(200), "
            +EVENT_TIME+" VARCHAR(150), "
            +EVENT_DAY+ " VARCHAR(200), " +
            EVENT_COLOR+ " VARCHAR(50), "+
            EVENT_WEEK+" VARCHAR(20)"+
            " );";

    //@note_tbl creation sql query

    public static final String CREATE_NOTE_tBL = "CREATE TABLE IF NOT EXISTS "+NOTE_TBL+
            " ("+NOTE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NOTE_TITLE+" VARCHAR(200), "+NOTE_DESCRIPTION+
            " TEXT, "+NOTE_DATE+" VARCHAR(100), "+
            EVENT_ID_FK+ " INTEGER );";

    public DbHelper(Context context) {
        // in this constructer database will be created with version 1
        super(context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tables in the database will be created.
            db.execSQL(CREATE_EVENTS_TBL);
            db.execSQL(CREATE_NOTE_tBL);

    }

    public String createTB()
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(CREATE_EVENTS_TBL);
            db.execSQL(CREATE_NOTE_tBL);
            return "Created.Created. Created. Created.";
        }
        catch(Exception e)
        {
            return e.toString();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CREATE_EVENTS_TBL);
    }

    // the following function will addEvent.
    public boolean addEvent(ContentValues contentValues)
    {
        // database instance is initiated in writeable mode.
        SQLiteDatabase db = this.getWritableDatabase();

        long res = db.insert(EVENTS_TBL, null, contentValues); // the @insert method will return positive or negative value
        //if the value was negative, so it means failure and if the value was positive it means insertion has taken place

            if(res == -1)
            {
                return false;
            }
            else
            {
                return true;
            }

    }

    public boolean addNote(ContentValues contentValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.insert(NOTE_TBL, null, contentValues);
        if(res == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    //the following function is for fetching event by day name. such as Fri, sat, Sun e.t.c
    public Cursor fetchEventsByDayName(String day, String week)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        // data is fetched and stored in @Cursor class object.
        Cursor res = db.rawQuery("SELECT * FROM "+EVENTS_TBL+" WHERE "+EVENT_DAY+" = '"+day+"' AND "+EVENT_WEEK+" = '"+week+"'",null);
        return res;
    }

    public Cursor fetchEventById(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+EVENTS_TBL+" WHERE "+EVENT_ID+" = '"+id+"'",null);
        return res;
    }

    public Cursor fetchNotesByEventId(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+NOTE_TBL+" WHERE "+EVENT_ID_FK+" = '"+id+"'",null);
        return res;
    }

    public boolean updateEvent(ContentValues contentValues,String id)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(EVENTS_TBL, contentValues, EVENT_ID + " = ?", new String[]{id});
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public boolean deleteEventAndItsNotes(String eid) {
        try
        {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EVENTS_TBL, EVENT_ID + " = ?", new String[]{eid});
        db.delete(NOTE_TBL, EVENT_ID_FK + " = ?", new String[]{eid});
        return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public Cursor searchEvent(String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+EVENTS_TBL+" WHERE "+EVENT_DATE+" like '%"+date+"%'",null);
        return res;
    }

    public Cursor fetchNoteById(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+NOTE_TBL+" WHERE "+NOTE_ID+" = '"+id+"'",null);
        return res;
    }

    public boolean deleteNoteById(String nid) {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(NOTE_TBL, NOTE_ID + " = ?", new String[]{nid});
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean updateNote(ContentValues contentValues,String id)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(NOTE_TBL, contentValues, NOTE_ID + " = ?", new String[]{id});
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
