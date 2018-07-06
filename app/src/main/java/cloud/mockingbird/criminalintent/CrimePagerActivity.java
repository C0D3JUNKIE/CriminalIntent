package cloud.mockingbird.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

import cloud.mockingbird.criminalintent.model.Crime;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "cloud.mockingbird.criminalintent.crime_id";

    private ViewPager viewPager;
    private List<Crime> crimes;
    private Button jumpFirstButton;
    private Button jumpLastButton;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        final UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        viewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        crimes = CrimeLab.get(this).getCrimes();

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                Crime crime = crimes.get(i);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return crimes.size();
            }
        });

        for(int i = 0; i < crimes.size(); i++){
            if(crimes.get(i).getId().equals(crimeId)){
                viewPager.setCurrentItem(i);
                break;
            }
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i == 0){
                    jumpFirstButton.setEnabled(false);
                }else if(i == crimes.size() -1){
                    jumpLastButton.setEnabled(false);
                }else{
                    jumpFirstButton.setEnabled(true);
                    jumpLastButton.setEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        jumpFirstButton = (Button) findViewById(R.id.jump_first_button);
        jumpFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        jumpLastButton = (Button) findViewById(R.id.jump_last_button);
        jumpLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(crimes.size() - 1);
            }
        });



    }

}
