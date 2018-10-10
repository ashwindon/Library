package com.example.ashwin.library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class issuedbooks extends Fragment {

    private DatabaseReference ref;
    private RecyclerView issued_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.issuedbooks, container, false);
        getActivity().setTitle("Issued Books List");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentFirebaseUser.getUid();
        ref = FirebaseDatabase.getInstance().getReference("student").child(uid).child("blist");
        ref.keepSynced(true);

        issued_list = (RecyclerView)view.findViewById(R.id.issued_recycler_view);
        issued_list.setHasFixedSize(true);
        issued_list.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<books> values = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    values.add(ds.getValue(books.class));
                }
                issued_list.setAdapter(new RecyclerViewAdapterIssue(values));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
