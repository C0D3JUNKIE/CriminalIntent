package cloud.mockingbird.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cloud.mockingbird.criminalintent.model.Crime;

public class CrimeListFragment extends Fragment {

    private RecyclerView crimeRecyclerView;
    private CrimeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        crimeRecyclerView = view.findViewById(R.id.rv_crime_item);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if(adapter == null){
            adapter = new CrimeAdapter(crimes);
            crimeRecyclerView.setAdapter(adapter);
        }else{
          adapter.notifyDataSetChanged();
        }

    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      private Crime crime;
      private TextView titleTextView;
      private TextView dateTextView;

      public CrimeHolder(@NonNull LayoutInflater inflater, ViewGroup parent){

          super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            titleTextView = itemView.findViewById(R.id.tv_crime_title);
            dateTextView = itemView.findViewById(R.id.tv_crime_date);

      }

      public void bind(Crime crime){
          this.crime = crime;
          titleTextView.setText(crime.getTitle());
          dateTextView.setText(getFormattedDate(crime.getDate()));
      }

        @Override
        public void onClick(View view) {
          Intent intent = CrimeActivity.newIntent(getActivity(), crime.getId());
          startActivity(intent);
        }

        private String getFormattedDate(Date date){

            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault());
            return formatter.format(date);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> crimes;

        public CrimeAdapter(List<Crime> crimes){
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

}
