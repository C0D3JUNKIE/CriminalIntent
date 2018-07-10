package cloud.mockingbird.criminalintent.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import cloud.mockingbird.criminalintent.utils.CrimeLab;
import cloud.mockingbird.criminalintent.R;
import cloud.mockingbird.criminalintent.activities.CrimePagerActivity;
import cloud.mockingbird.criminalintent.models.Crime;

public class CrimeListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView crimeRecyclerView;
    private CrimeAdapter adapter;
    private boolean subtitleVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        crimeRecyclerView = view.findViewById(R.id.rv_crime_item);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(savedInstanceState != null){
            subtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if(subtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                subtitleVisible = !subtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        Map<UUID, Crime> crimes = crimeLab.getCrimes();

        if(adapter == null){
            adapter = new CrimeAdapter(crimes);
            crimeRecyclerView.setAdapter(adapter);
        }else{
          adapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      private Crime crime;
      private TextView titleTextView;
      private TextView dateTextView;
      private ImageView solvedImageView;

      public CrimeHolder(@NonNull LayoutInflater inflater, ViewGroup parent){

          super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            titleTextView = (TextView) titleTextView.findViewById(R.id.tv_crime_title);
            dateTextView = (TextView) itemView.findViewById(R.id.tv_crime_date);
            solvedImageView = (ImageView) itemView.findViewById(R.id.iv_crime_solved);

      }

      public void bind(Crime crime){
          this.crime = crime;
          titleTextView.setText(crime.getTitle());
          dateTextView.setText(getFormattedDate(crime.getDate()));
          solvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
      }

        @Override
        public void onClick(View view) {
          Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
          startActivity(intent);
        }

        private String getFormattedDate(Date date){

            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault());
            return formatter.format(date);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private Map<UUID, Crime> crimes;

        public CrimeAdapter(Map<UUID, Crime> crimes){
            this.crimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder crimeHolder, int i){
          Crime crime = crimes.get(i);
          crimeHolder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }

    }

    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);
        if(!subtitleVisible){
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

}
