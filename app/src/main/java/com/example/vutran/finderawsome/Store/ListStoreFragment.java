package com.example.vutran.finderawsome.Store;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vutran.finderawsome.Adapter.AdapterStore;
import com.example.vutran.finderawsome.Model.ModelStore;
import com.example.vutran.finderawsome.R;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by VuTran on 5/25/2017.
 */

public class ListStoreFragment extends Fragment implements View.OnClickListener {
    public ListStoreFragment() {
    }

    private TextView textViewName, textViewAddress;
    private ListView listView;
    private ImageView imageViewLogo;
    ArrayList<ModelStore> arrayStore;
    AdapterStore adapter;

    private LocationManager mLocationManager;
    private String provider;

    String urlGetData = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=10.813186,106.692180&radius=1800&types=convenience_store&sensor=true&key=AIzaSyAKs8S1YN0HEbaY34at6sYh4nDDywkge2s";
    public static final String ID = "ID address store";
    public static final String BUNDLE = "bundle";
    public static final String IDIMAGE = "https://www.webnames.ca/images/wn/tld-landing/dot-store-logo.png";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_store, container, false);
        init(view);
        arrayStore = new ArrayList<>();
        adapter = new AdapterStore(getContext(), R.layout.item_list_store, arrayStore);
        listView.setAdapter(adapter);

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = mLocationManager.getBestProvider(criteria, false);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null) {
            new getDataByAsync().execute(String.valueOf(sbMethod(location)));
        } else {
            Toast.makeText(getContext(), "Location Null", Toast.LENGTH_SHORT).show();
        }
        /**
         * Click vào từng item
         * Đổ dữ liệu vào bundle rồi chuyển bên Detail
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailStoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ID, arrayStore.get(position).getId().toString());
                bundle.putString(IDIMAGE, arrayStore.get(position).getIdImage().toString());
                intent.putExtra(BUNDLE, bundle);
                startActivity(intent);
            }
        });
        return view;

    }

    /**
     * Khởi tạo find ID
     *
     * @param view
     */
    private void init(View view) {
        listView = (ListView) view.findViewById(R.id.listViewStore);
        textViewName = (TextView) view.findViewById(R.id.textViewNameSave);
        textViewAddress = (TextView) view.findViewById(R.id.textViewAddressSave);
        imageViewLogo = (ImageView) view.findViewById(R.id.imageViewLogoSave);
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * Tạo URL để request lên Server
     *
     * @return StringBuiler
     */
    public StringBuilder sbMethod(Location location) {
        double latitude = location.getLatitude();
        double longtitude = location.getLongitude();

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + latitude + "," + longtitude);
        sb.append("&radius=1000");
        sb.append("&types=" + "convenience_store");
        sb.append("&sensor=true");

        sb.append("&key=AIzaSyAKs8S1YN0HEbaY34at6sYh4nDDywkge2s");

        Log.d("Map", "<><>api: " + sb.toString());
        return sb;
    }

    /**
     * Lấy dữ liệu và đọc JSON từ reponse
     */
    private class getDataByAsync extends AsyncTask<String, Void, String> {
        StringBuilder content = new StringBuilder();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                String idimage = "";
                JSONObject Object = new JSONObject(s);
                JSONArray ArrayResults = Object.getJSONArray("results");

                for (int i = 0; i < ArrayResults.length(); i++) {
                    JSONObject objectResult = ArrayResults.getJSONObject(i);
                    String name = objectResult.getString("name");
                    String address = objectResult.getString("vicinity");
                    String id = objectResult.getString("place_id");
                    if (objectResult.optJSONArray("photos") == null) {
                        idimage = "CmRaAAAAbOpEo3OY2RnJthHfCalBrQwqCtw_AaTxxPqY_LpQN9CRShB8hsbYLvI9JLXI_p9j5Kr1RhF5n3kMtZ3IfxJ_HeQUYJvdA1w7--dF07fpsojA9xoCX-Ah7KXKdOz7VRTdEhA3uVdGnvZbcu7Ek8Njl9MvGhRP-G9GRjOHEx29nb1t9dVvCJ2VmA";
                    } else {
                        JSONArray arrayPhotos = objectResult.getJSONArray("photos");
                        JSONObject objectPhoto = arrayPhotos.getJSONObject(0);
                        idimage = objectPhoto.getString("photo_reference");
                    }
                    arrayStore.add(new ModelStore(name, address, id, idimage));
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
