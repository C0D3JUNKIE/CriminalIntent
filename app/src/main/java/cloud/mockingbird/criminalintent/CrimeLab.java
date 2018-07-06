package cloud.mockingbird.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cloud.mockingbird.criminalintent.model.Crime;

public class CrimeLab {

    private static CrimeLab crimeLab;

    private List<Crime> crimes;

    public static CrimeLab get(Context context){
        if(crimeLab == null){
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }

    private CrimeLab(Context context){

      crimes = new ArrayList<>();

      for(int i =  0; i < 100; i++){

          Crime crime = new Crime();
          crime.setTitle("Crime #" + i);
//          crime.setSolved(i % 2 == 0);
          crimes.add(crime);

      }

    }

    public Crime getCrime(UUID id){
        for(Crime crime: crimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }

    public List<Crime> getCrimes(){
        return crimes;
    }

}
