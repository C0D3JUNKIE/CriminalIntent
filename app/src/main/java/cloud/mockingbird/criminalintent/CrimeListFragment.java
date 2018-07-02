package cloud.mockingbird.criminalintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CrimeListFragment extends Fragment {

    private RecyclerView crimeRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        crimeRecyclerView = (RecyclerView) view.findViewById(R.id.rv_crime_item);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;

    }

    private class CrimeHolder extends RecyclerView.ViewHolder{

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
        }
    }



}
