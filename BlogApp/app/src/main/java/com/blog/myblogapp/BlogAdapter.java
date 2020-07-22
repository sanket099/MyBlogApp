package com.blog.myblogapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Blog> dataModelArrayList;
    private OnNoteList onNoteList;
    private Context context;

    public BlogAdapter(Context ctx, ArrayList<Blog> dataModelArrayList, OnNoteList onNoteList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.onNoteList = onNoteList;
        this.context = ctx;
    }



    @NonNull
    @Override
    public BlogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view,onNoteList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BlogAdapter.MyViewHolder holder, int position) {

       // Picasso.with(context).load(dataModelArrayList.get(position).getImgUrl()).into(holder.imageView);
        holder.title.setText(dataModelArrayList.get(position).getTitle());
        holder.desc.setText(dataModelArrayList.get(position).getDesc());
        System.out.println("dataModelArrayList.get(position).getTitle() = " + dataModelArrayList.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, desc;

        OnNoteList onNoteList;
        public MyViewHolder(@NonNull View itemView, OnNoteList onNoteList) {
            super(itemView);

            title = itemView.findViewById(R.id.et_row_title);
            desc = itemView.findViewById(R.id.et_row_desc);


            this.onNoteList = onNoteList;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            onNoteList.OnnoteClick(dataModelArrayList.get(getAdapterPosition()));

        }
    }
    public interface OnNoteList {
        void OnnoteClick(Blog userClass);


    }
    public void filteredlist(ArrayList<Blog> filterlist){
        dataModelArrayList = filterlist;
        notifyDataSetChanged();
    }
}
