package com.example.dogapp.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.databinding.DogsItemBinding;
import com.example.dogapp.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> implements Filterable {

    private ArrayList<DogBreed> dogBreeds;
    private List<DogBreed> dogBreedsFull;

    public DogsAdapter(ArrayList<DogBreed> dogBreeds) {
        this.dogBreedsFull = dogBreeds;
        this.dogBreeds = dogBreeds;
    }

    @NonNull
    @Override
    public DogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dogs_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(dogBreeds.get(position).getName());
        holder.tvBredFor.setText(dogBreeds.get(position).getBredFor());
        holder.dtName.setText(dogBreeds.get(position).getName());
        holder.dtOrigin.setText(dogBreeds.get(position).getOrigin());
        holder.dtLifeSpan.setText(dogBreeds.get(position).getLifeSpan());
        holder.dtTemp.setText(dogBreeds.get(position).getTemperament());
        Picasso.get()
                .load(dogBreeds.get(position).getUrl())
                .placeholder(R.drawable.dog_avatar)
                .fit()
                .into(holder.getIvAvatar());

    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                List<DogBreed> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList.addAll(dogBreedsFull);
                } else {
                    for (DogBreed dog : dogBreedsFull) {
                        if (dog.getName().toLowerCase().contains(charString)) {
                            filteredList.add(dog);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dogBreeds = new ArrayList<>();
                dogBreeds.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

//        public DogsItemBinding binding;

        public TextView tvName;
        public TextView tvBredFor;
        public ImageView ivAvatar;
        public TextView dtName;
        public TextView dtOrigin;
        public TextView dtLifeSpan;
        public TextView dtTemp;
        public LinearLayout layoutMain;
        public LinearLayout layoutDetail;

        public ViewHolder(View view) {
            super(view);
            layoutMain = view.findViewById(R.id.layout_main);
            layoutDetail = view.findViewById(R.id.layout_detail);
            tvName = view.findViewById(R.id.tv_name);
            tvBredFor = view.findViewById(R.id.tv_bredFor);
            ivAvatar = view.findViewById(R.id.iv_avatar);
            dtName = view.findViewById(R.id.dt_name);
            dtOrigin = view.findViewById(R.id.dt_origin);
            dtLifeSpan = view.findViewById(R.id.dt_life_span);
            dtTemp = view.findViewById(R.id.dt_temp);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    DogBreed dogBreed = dogBreeds.get(getAdapterPosition());
                    bundle.putSerializable("dogBreed", dogBreed);
                    Navigation.findNavController(view).navigate(R.id.detailsFragment,bundle);
                }
            });

            view.setOnTouchListener(new OnSwipeTouchListener(){
                @Override
                public boolean onSwipeLeft() {
                    if (layoutMain.getVisibility() == View.GONE) {
                        layoutMain.setVisibility(View.VISIBLE);

                        layoutDetail.setVisibility(View.GONE);
                    } else {
                        layoutMain.setVisibility(View.GONE);
                        layoutDetail.setVisibility(View.VISIBLE);
                    }
                    return true;
                }

                @Override
                public boolean onSwipeRight() {
                    onSwipeLeft();
                    return true;
                }
            });


        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvBredFor() {
            return tvBredFor;
        }

        public ImageView getIvAvatar() {
            return ivAvatar;
        }
    }
}

class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    public boolean onTouch(final View v, final MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            result = onSwipeRight();
                        } else {
                            result = onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            result = onSwipeBottom();
                        } else {
                            result = onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public boolean onSwipeRight() {
        return false;
    }

    public boolean onSwipeLeft() {
        return false;
    }

    public boolean onSwipeTop() {
        return false;
    }

    public boolean onSwipeBottom() {
        return false;
    }
}
