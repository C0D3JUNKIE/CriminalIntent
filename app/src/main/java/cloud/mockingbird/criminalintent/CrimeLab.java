package cloud.mockingbird.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cloud.mockingbird.criminalintent.model.Crime;

public class CrimeLab {

    private static CrimeLab crimeLab;

    //To track order of the listed events utilize map instead of list.
//    private List<Crime> crimes;
    private Map<UUID, Crime> crimes;

    public static CrimeLab get(Context context){
        if(crimeLab == null){
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }

    private CrimeLab(Context context){

      crimes = new LinkedHashMap<>();

      for(int i =  0; i < 100; i++){
          Crime crime = new Crime();
          crime.setTitle("Crime #" + i);
          crime.setSolved(i % 2 == 0);
          crimes.put(crime.getId(), crime);
      }

    }

    public Crime getCrime(UUID id){
        return crimes.get(id);
    }

    public List<Crime> getCrimes(){
        return new ArrayList<>(crimes.values());
    }

}
