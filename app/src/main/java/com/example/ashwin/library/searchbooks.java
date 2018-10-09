package com.example.ashwin.library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchbooks extends Fragment {
    View view;
    EditText mSearchField;
    Button mSearchBtn;


    RecyclerView recyclerView;

    DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchbooks, container, false);

        Log.d("SEARCHEXACT", "HERE");
        mSearchField = (EditText) view.findViewById(R.id.search_field);
        Log.d("SEARCHEXACT", "HERE4");
        mSearchBtn = (Button) view.findViewById(R.id.search_btn);
        Log.d("SEARCHEXACT", "HERE5");
        recyclerView = (RecyclerView) view.findViewById(R.id.studreq_list);
        Log.d("SEARCHEXACT", "HERE2");
        recyclerView.setHasFixedSize(true);
        Log.d("SEARCHEXACT", "HERE3");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        return view;
    }

    public void search() {
        Log.d("SEARCHEXACT", "HERE69");
        final String searchText = mSearchField.getText().toString().replaceAll("\\s+", "").toUpperCase();
        ref = FirebaseDatabase.getInstance().getReference("Books");
        Log.d("SEARCHEXACT", "HERE70");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("SEARCHEXACT", searchText);

                ArrayList<BookInfo> values = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(searchText.toString())) {
                        values.add(ds.getValue(BookInfo.class));
                        Log.d("SEARCHEXACT", ds.getValue().toString());

                        Log.d("SEARCHEXACT", String.valueOf(values.size()));
                    }

                }
                recyclerView.setAdapter(new recycleradapterissue(values));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search Books");
    }
}
