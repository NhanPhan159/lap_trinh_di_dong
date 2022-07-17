package com.example.dogapp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.viewmodel.DogsAdapter;
import com.example.dogapp.viewmodel.DogsApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListFragment extends Fragment {

    private DogsApiService apiService;
    private ArrayList<DogBreed> dogBreeds;
    private DogsAdapter dogsAdapter;
    private RecyclerView rvDogs;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }


    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDogs=view.findViewById(R.id.rv_dogs);
        dogBreeds = new ArrayList<DogBreed>();
        dogsAdapter = new DogsAdapter(dogBreeds);

        rvDogs.setAdapter(dogsAdapter);
        rvDogs.setLayoutManager(new GridLayoutManager(getContext(),2));

        apiService = new DogsApiService();
        apiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@NonNull List<DogBreed> dogBreedsList) {
                        Log.d("DEBUG1", "Sucess");
                        for (DogBreed dog : dogBreedsList) {
//                            Log.d("DEBUG1", dog.getName());
                            dogBreeds.add(dog);
                            dogsAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("DEBUG1",e.getMessage());
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull Menu menu, @androidx.annotation.NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_dog).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                dogsAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
}