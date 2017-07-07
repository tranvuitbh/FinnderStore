package com.example.vutran.finderawsome.User;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vutran.finderawsome.Database.DBAdapterStore;
import com.example.vutran.finderawsome.Database.DBModelStore;
import com.example.vutran.finderawsome.Database.DatabaseManager;
import com.example.vutran.finderawsome.Model.ModelStore;
import com.example.vutran.finderawsome.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

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
    private List<DBModelStore> storeList;
    private FirebaseAuth mFirebaseAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        init(view);
        databaseManager = new DatabaseManager(getContext());
        storeList = databaseManager.getAllStores(getIdUser());
        setAdapter();
        listViewStoresSaved.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DBModelStore store = storeList.get(position);
                int result = databaseManager.deleteStore(store.getId());
                if (result > 0) {
                    Toast.makeText(getContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                    updateListStore();
                } else {
                    Toast.makeText(getContext(), "Delete Failure", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        return view;
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
    public void updateListStore() {
        storeList.clear();
        storeList.addAll(databaseManager.getAllStores(getIdUser()));
        if (dbAdapterStore != null) {
            dbAdapterStore.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void init(View view) {
        textViewNameSaved = (TextView) view.findViewById(R.id.textViewNameSave);
        textViewAddressSaved = (TextView) view.findViewById(R.id.textViewAddressSave);
        listViewStoresSaved = (ListView) view.findViewById(R.id.listViewStoresSaved);
    }

    private void setAdapter() {
        if (dbAdapterStore == null) {
            dbAdapterStore = new DBAdapterStore(getContext(), R.layout.item_store_favourite_tab, storeList);
        }
        listViewStoresSaved.setAdapter(dbAdapterStore);
    }
}
