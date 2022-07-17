package com.example.contactapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.xml.namespace.QName;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    private ArrayList<Contact> contacts;
    private Context context;

    public ContactAdapter(@NonNull ArrayList<Contact> contacts,Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override


    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {

            holder.Name.setText(contacts.get(position).getName());
            Contact c = contacts.get(position);


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        // below text drawable is a circular.
        TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                .width(90)  // width in px
                .height(90) // height in px
                .endConfig()

                .buildRound(contacts.get(position).getName().substring(0, 1), color);
        // setting image to our image view on below line.
        holder.avartar.setImageDrawable(drawable2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, DetailActivity.class);
                String s[] = c.getName().split(" ");
                i.putExtra("fname",s[0]);
             //   i.putExtra("contact", c.getPhone());
                i.putExtra("mail", c.getEmail());
                i.putExtra("lname", s[1]);
                // on below line we are starting a new activity,
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Name;
        public ImageView avartar;
        public ViewHolder(View view) {
            super(view);

          //  Name = view.findViewById(R.id.name);
          //  avartar = view.findViewById(R.id.avatar);
        }


    }
}
