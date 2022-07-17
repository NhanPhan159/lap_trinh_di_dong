package com.example.dogapp.view;

import android.database.DatabaseUtils;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapp.R;
import com.example.dogapp.databinding.FragmentDetailsBinding;
import com.example.dogapp.model.DogBreed;
import com.squareup.picasso.Picasso;


public class DetailsFragment extends Fragment {
    private DogBreed dog;
    private FragmentDetailsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dog = (DogBreed) getArguments().getSerializable("dogBreed");
//            Log.d("DEBUG1",dog.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.fragment_details,null,false);
        View viewRoot = binding.getRoot();
        binding.setDog(dog);
        Picasso.get()
                .load(dog.getUrl())
                .placeholder(R.drawable.dog_avatar)
                .fit()
                .into(binding.ivAvatar);
        return viewRoot;
    }
}