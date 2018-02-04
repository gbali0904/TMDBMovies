package tmdb.android.com.tmdbmoviesapplictaion.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import tmdb.android.com.tmdbmoviesapplictaion.main.model.ModelForMoviesList;

/**
 * Created by Nikunj on 27-08-2015.
 */
public class MoviesDataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";
    public static final String TABLE_NAME = "MOVIES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_VOTE_AVERAGE = "VOTE_AVERAGE";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_POSTER_PATH = "POSTER_PATH";
    public static final String COLUMN_LANGUAGE = "LANGUAGE";
    public static final String COLUMN_ORIGINAL_TITLE = "ORIGINAL_TITLE";
    public static final String COLUMN_OVERVIEW = "OVERVIEW";
    public static final String COLUMN_RELEASE_DATE = "RELEASE_DATE";
    public static final String COLUMN_LIKE = "LIKE";
    String like_status=null;
    private SQLiteDatabase database;

    public MoviesDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER UNIQUE," + COLUMN_VOTE_AVERAGE + " VARCHAR, " +
                COLUMN_TITLE + " VARCHAR , "+ COLUMN_POSTER_PATH + " VARCHAR ,"+
                COLUMN_LANGUAGE + " VARCHAR ," +COLUMN_ORIGINAL_TITLE + " VARCHAR ," +
                COLUMN_OVERVIEW + " VARCHAR ," +COLUMN_RELEASE_DATE + " VARCHAR ," +
                COLUMN_LIKE + " VARCHAR " +
                ");");
    }

    public void insertRecord(List<ModelForMoviesList.ResultsBean> resultsBeen) {
        database = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        for (ModelForMoviesList.ResultsBean contact: resultsBeen)
        {
            contentValues.put(COLUMN_ID, contact.getId());
            contentValues.put(COLUMN_VOTE_AVERAGE, contact.getVote_average());
            contentValues.put(COLUMN_TITLE, contact.getTitle());
            contentValues.put(COLUMN_POSTER_PATH, contact.getPoster_path());
            contentValues.put(COLUMN_LANGUAGE, contact.getOriginal_language());
            contentValues.put(COLUMN_ORIGINAL_TITLE, contact.getOriginal_title());
            contentValues.put(COLUMN_OVERVIEW, contact.getOverview());
            contentValues.put(COLUMN_RELEASE_DATE, contact.getRelease_date());
            contentValues.put(COLUMN_LIKE, "0");

            if (isRecordExistInDatabase(contact.getId()))
            {
                Log.e("this","Alreay Id Exists");
            }
            else {
                database.insert(TABLE_NAME, null, contentValues);
            }
        }

        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private boolean isRecordExistInDatabase(int id) {
        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null);
        if (c.moveToFirst()) {
            //Record exist
            c.close();
            return true;
        }
        //Record available
        c.close();
        return false;
    }

    public void updateRecord(int ID , String like) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LIKE,like);
        database.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(ID)});
        database.close();
    }

    public String  getAllRecordsAlternate(int ID) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + ID, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() != true) {
                like_status =  cursor.getString(cursor.getColumnIndex(COLUMN_LIKE));
                return like_status;
            }
        }
        cursor.close();
        database.close();
        return like_status;
    }

}
