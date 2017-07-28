package com.example.vutran.finderawsome.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vutran.finderawsome.Database.DBAdapterStore;
import com.example.vutran.finderawsome.Database.DatabaseManager;
import com.example.vutran.finderawsome.Firebase.AdapterFirebase;
import com.example.vutran.finderawsome.Firebase.Store;
import com.example.vutran.finderawsome.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by VuTran on 5/25/2017.
 */

public class FavouriteFragment extends Fragment implements View.OnClickListener {
    public FavouriteFragment() {
    }

    private TextView textViewNameSaved, textViewAddressSaved;
    private ListView listViewStoresSaved;

    private DBAdapterStore dbAdapterStore;
    private DatabaseManager databaseManager;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mData;

    private ArrayList<Store> storeList = new ArrayList<>();
    private AdapterFirebase adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        init(view);

        mData = FirebaseDatabase.getInstance().getReference();
        mData.child(getIdUser()).child("Favorite Places").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listViewStoresSaved.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = storeList.get(position);
                String idPlace = store.getId();
                mData.child(getIdUser()).child("Favorite Places").child(idPlace).removeValue();
                adapter.notifyDataSetChanged();
                listViewStoresSaved.setAdapter(adapter);
                Toast.makeText(getContext(), "Delete Store Success", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return view;
    }


    private void updateData(DataSnapshot dataSnapshot) {
        Store store = dataSnapshot.getValue(Store.class);
        storeList.add(store);
        adapter = new AdapterFirebase(getContext(), R.layout.item_store_favourite_tab, storeList);
        listViewStoresSaved.setAdapter(adapter);
    }

    public String getIdUser() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth != null) {
            FirebaseUser user = mFirebaseAuth.getCurrentUser();
            String idUser = user.getUid();
            return idUser;
        }
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    private void init(View view) {
        textViewNameSaved = (TextView) view.findViewById(R.id.textViewNameSave);
        textViewAddressSaved = (TextView) view.findViewById(R.id.textViewAddressSave);
        listViewStoresSaved = (ListView) view.findViewById(R.id.listViewStoresSaved);
    }

}
