package cloud.mockingbird.criminalintent.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cloud.mockingbird.criminalintent.database.CrimeBaseHelper;
import cloud.mockingbird.criminalintent.database.CrimeCursorWrapper;
import cloud.mockingbird.criminalintent.database.CrimeDbSchema;
import cloud.mockingbird.criminalintent.database.CrimeDbSchema.CrimeTable;
import cloud.mockingbird.criminalintent.models.Crime;

public class CrimeLab {

    private Context context;
    private SQLiteDatabase database;

    private static CrimeLab crimeLab;

    private static ContentValues getContentValues(Crime crime){

        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1:0);

        return values;
    }

    public static CrimeLab get(Context context){
        if(crimeLab == null){
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }

    private CrimeLab(Context context){

        this.context = context.getApplicationContext();
        database = new CrimeBaseHelper(context).getWritableDatabase();

    }

    public Crime getCrime(UUID id){

        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString() });

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally{
            cursor.close();
        }

    }

    public Map<UUID, Crime> getCrimes(){
        Map<UUID, Crime> crimes = new LinkedHashMap<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.put(cursor.getCrime().getId(), cursor.getCrime());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }

        return crimes;
    }

    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);
        database.insert(CrimeTable.NAME, null, values);
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        database.update(CrimeTable.NAME , values, CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){

        Cursor cursor = database.query(CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new CrimeCursorWrapper(cursor);
    }

}
