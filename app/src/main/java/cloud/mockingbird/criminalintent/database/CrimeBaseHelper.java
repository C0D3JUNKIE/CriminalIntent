package cloud.mockingbird.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static cloud.mockingbird.criminalintent.database.CrimeDbSchema.CrimeTable.*;

public class CrimeBaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + NAME +
            "(" + " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Cols.UUID + ", " +
            Cols.TITLE + ", " +
            Cols.DATE + ", " +
            Cols.SOLVED + ", " +
            Cols.SUSPECT + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
