package com.example.vutran.finderawsome.Store;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vutran.finderawsome.Adapter.AdapterReview;
import com.example.vutran.finderawsome.Database.DBAdapterStore;
import com.example.vutran.finderawsome.Database.DBModelStore;
import com.example.vutran.finderawsome.Database.DatabaseManager;
import com.example.vutran.finderawsome.MainActivity;
import com.example.vutran.finderawsome.Model.ModelReview;
import com.example.vutran.finderawsome.Model.ModelStore;
import com.example.vutran.finderawsome.Firebase.Store;
import com.example.vutran.finderawsome.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by VuTran on 5/25/2017.
 */

public class DetailStoreActivity extends AppCompatActivity {

    private TextView textViewAddress, textViewSite, textViewName, textViewSaveThis, textViewDirection;
    private ImageView imageViewCover;
    private ListView listViewReviews;
    private ArrayList<ModelReview> arrayReview;
    private AdapterReview adapterReview;

    private DatabaseManager database;
    private DBModelStore store;
    private DBAdapterStore adapter;
    private FirebaseAuth mFirebaseAuth;
    private LatLng latLngStore;
    public static final String LAT_LNG_DETAIL = "";
    public static final String BUNDLE_DETAIL = "bundle null";
    private DatabaseReference mDatabaseReference;

    public DetailStoreActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);
        init();
        new GetDataDetailPlace().execute(getPlaceDetailById().toString());
        new LoadImage().execute(getPhotoById().toString());

        arrayReview = new ArrayList<>();
        adapterReview = new AdapterReview(this, R.layout.item_comment_store, arrayReview);
        listViewReviews.setAdapter(adapterReview);

        //  DatabaseManager database = new DatabaseManager(this);


        textViewSaveThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedStore();
                saveToFirebase();
            }
        });
        textViewDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailStoreActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(LAT_LNG_DETAIL, latLngStore);
                intent.putExtra(BUNDLE_DETAIL, bundle);
                startActivity(intent);
            }
        });
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

    private void savedStore() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ListStoreFragment.BUNDLE);
        String idPlace = bundle.getString(ListStoreFragment.ID);
        database = new DatabaseManager(this);
        database.updateSaved(idPlace, getIdUser());
        Toast.makeText(DetailStoreActivity.this, "Saved this place", Toast.LENGTH_SHORT).show();
    }

    private void saveToFirebase() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ListStoreFragment.BUNDLE);
        String idPlace = bundle.getString(ListStoreFragment.ID);

        String name = textViewName.getText().toString();
        String address = textViewAddress.getText().toString();

        Store store = new Store(name, address,idPlace);
        mDatabaseReference.child(getIdUser()).child("Favorite Places").child(idPlace).setValue(store);
    }

    private void init() {
        textViewAddress = (TextView) findViewById(R.id.textViewAddressSave);
        textViewSite = (TextView) findViewById(R.id.textViewSite);
        textViewName = (TextView) findViewById(R.id.textViewNameSave);
        imageViewCover = (ImageView) findViewById(R.id.imageViewCover);
        listViewReviews = (ListView) findViewById(R.id.listViewReview);
        textViewSaveThis = (TextView) findViewById(R.id.textViewSaveThis);
        textViewDirection = (TextView) findViewById(R.id.textViewDirection);
    }

    private ModelStore createStore() {
        ModelStore store = new ModelStore("ID4", "NAME4", "ADDRESS3", "SITE3", "1");
        return store;
    }

    public StringBuilder getPlaceDetailById() {
        // Get id place from List Store Fragment
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ListStoreFragment.BUNDLE);
        String idPlace = bundle.getString(ListStoreFragment.ID);
        StringBuilder urlRequest = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        urlRequest.append("placeid=" + idPlace);
        urlRequest.append("&key=AIzaSyAKs8S1YN0HEbaY34at6sYh4nDDywkge2s");
        return urlRequest;
    }

    public class GetDataDetailPlace extends AsyncTask<String, Void, String> {
        StringBuilder content = new StringBuilder();

        /**
         * Hàm lấy dữ liệu JSON về
         *
         * @param params
         * @return
         */
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

        /**
         * Đọc JSON
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject Object = new JSONObject(s);
                JSONObject objectResult = Object.getJSONObject("result");
                String address = objectResult.optString("vicinity");
                String site = objectResult.optString("website");
                String name = objectResult.optString("name");
                String id = objectResult.optString("place_id");
                JSONObject geometry = objectResult.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");

                double latStore = location.getDouble("lat");
                double lngStore = location.getDouble("lng");
                latLngStore = new LatLng(latStore, lngStore);

                textViewAddress.setText(address.toString());
                textViewSite.setText(site.toString());
                textViewName.setText(name.toString());
                JSONArray reviewsArray = objectResult.optJSONArray("reviews");
                if (reviewsArray != null) {
                    for (int i = 0; i < reviewsArray.length(); i++) {
                        JSONObject objectReview = reviewsArray.getJSONObject(i);
                        String authorname = objectReview.optString("author_name");
                        String profilephotourl = objectReview.optString("profile_photo_url");
                        String rating = objectReview.optString("rating");
                        String text = objectReview.optString("text");
                        arrayReview.add(new ModelReview(authorname, profilephotourl, rating, text));
                    }
                }
                //    database.queryData("INSERT INTO Store VALUES('" + id.toString() + "','" + name.toString() + "','" + address.toString() + "','" + site.toString() + "','0')");
                DatabaseManager database = new DatabaseManager(getApplicationContext());

                ModelStore store = new ModelStore(name, address, id, site, "0", getIdUser());
                database.addStore(store);
                adapterReview.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @return String
     * hàm trả về một chuỗi url để request Place Photo
     */
    public StringBuilder getPhotoById() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ListStoreFragment.BUNDLE);
        String idPhoto = bundle.getString(ListStoreFragment.IDIMAGE);

        StringBuilder urlRequestPhoto = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400");
        urlRequestPhoto.append("&photoreference=" + idPhoto);
        urlRequestPhoto.append("&key=AIzaSyAKs8S1YN0HEbaY34at6sYh4nDDywkge2s");
        return urlRequestPhoto;

    }
    /**
     * Return Bitmap
     * Hàm trả về hình ảnh và đã set vào imageview
     */
    private class LoadImage extends AsyncTask<String, Void, byte[]> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        @Override
        protected byte[] doInBackground(String... params) {
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);

            Request request = builder.build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            if (bytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageViewCover.setImageBitmap(bitmap);
            }
            super.onPostExecute(bytes);
        }
    }

}
