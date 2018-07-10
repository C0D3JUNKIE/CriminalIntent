package cloud.mockingbird.criminalintent.activities;

import android.support.v4.app.Fragment;

import cloud.mockingbird.criminalintent.activities.SingleFragmentActivity;
import cloud.mockingbird.criminalintent.fragments.CrimeListFragment;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }




}
