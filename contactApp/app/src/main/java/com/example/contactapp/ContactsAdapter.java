package com.example.contactapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;


    public ContactsAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(""+contacts.get(position).getName());
        holder.ivAvatar.setImageBitmap(DataConverter.convertByteArray2Image(contacts.get(position).getImage()));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("id",contacts.get(position).getId());
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView tvName;
        public ImageView ivAvatar;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_name);
            ivAvatar = view.findViewById(R.id.iv_avatar);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }





}
