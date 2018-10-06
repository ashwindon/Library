package com.example.ashwin.library;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JEFRI SINGH(ஜெப்ரி சிங்) on 11/4/2016.
 * Organization "The Tuna Group" - Kerala
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<BookInfo> values;

    RecyclerViewAdapter(ArrayList<BookInfo> values) {
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(values.get(position).getTitle());
        holder.athr.setText(values.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, athr;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bname);
            athr = itemView.findViewById(R.id.athr);
        }
    }
}
