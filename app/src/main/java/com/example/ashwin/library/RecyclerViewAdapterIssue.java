package com.example.ashwin.library;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapterIssue extends RecyclerView.Adapter<RecyclerViewAdapterIssue.ViewHolder> {
    private ArrayList<books> values;

    RecyclerViewAdapterIssue(ArrayList<books> values) {
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.issued_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bkname.setText(values.get(position).getBKname());
        Log.d("issuedlist", "" + values.get(position).getBKname());
        holder.doi.setText(values.get(position).getDoi());
        holder.dor.setText(values.get(position).getDor());
        holder.Retuenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bkname, doi, dor;
        private Button Retuenbtn;

        ViewHolder(View itemView) {
            super(itemView);
            bkname = (TextView) itemView.findViewById(R.id.issued_book_name);
            doi = (TextView) itemView.findViewById(R.id.issue_id);
            dor = (TextView) itemView.findViewById(R.id.return_id);
            Retuenbtn = (Button) itemView.findViewById(R.id.return_btn_id);
        }
    }
}
