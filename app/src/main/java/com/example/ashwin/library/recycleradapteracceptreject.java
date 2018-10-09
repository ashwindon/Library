package com.example.ashwin.library;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


class recycleradapteracceptreject extends RecyclerView.Adapter<recycleradapteracceptreject.ViewHolder> {
    String uid;
    String uuid;
    String u;
    DatabaseReference ref;
    int g = 0;
    int o = 0;
    addreqlistdata d = new addreqlistdata();
    private ArrayList<addreqlistdata> values;

    recycleradapteracceptreject(ArrayList<addreqlistdata> values) {
        this.values = values;
    }

    public recycleradapteracceptreject getThis() {
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.acceptrejectcard, parent, false));
    }

    public void callme(final int position, final books b, final String userid) {
        g = 0;

        ref = FirebaseDatabase.getInstance().getReference("student/" + userid + "/blist");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref = FirebaseDatabase.getInstance().getReference("student/" + userid + "/blist");
                if (g == 0) {
                    Log.d("issuebtn", "HERE4");

                    ref.child("").push().setValue(b);

                    g = 1;

                    values.remove(position);
                    getThis().notifyItemRemoved(position);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Log.d("acceptbtn", "go");
        Log.d("acceptbtn", values.get(position).getBname());
        holder.bname.setText(values.get(position).getBname());

        Log.d("acceptbtn", "there");

        holder.stid.setText(values.get(position).getStid());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uuid = values.get(position).getUuid();
                final books d = new books();

                //String name = x;
                //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // uid = user.getUid();
                DatabaseReference sref = FirebaseDatabase.getInstance().getReference("Requestlist/" + uuid + "/-LOO5J7zUmGWCh3SZZmQ");
                sref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (o == 0) {
                            books d = new books();
                            Log.d("stuid", dataSnapshot.getValue().toString());
                            //int y;

                            String doi, dor;


                            Date c = Calendar.getInstance().getTime();
                            //System.out.println("Current time => " + c);

                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            doi = df.format(c);

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date());
                            cal.add(Calendar.DAY_OF_YEAR, 7);
                            Date sevenlater = cal.getTime();


                            dor = df.format(sevenlater);


                            o = 1;
                            d.setBKname(values.get(position).getBname());
                            d.setDoi(doi);
                            d.setDor(dor);

                            Log.d("acceptbtn", "HERE2");

                            callme(position, d, uuid);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                Log.d("issuebtn", "HERE");


                Log.d("issuebtn", String.valueOf(u));


            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button accept, reject;
        private TextView bname, stid;

        ViewHolder(View itemView) {
            super(itemView);
            bname = itemView.findViewById(R.id.bkname);
            stid = itemView.findViewById(R.id.stid);
            accept = itemView.findViewById(R.id.acceptbtn);
            reject = itemView.findViewById(R.id.rejectbtn);

        }
    }
}
