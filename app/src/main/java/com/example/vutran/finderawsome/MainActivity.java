package com.example.vutran.finderawsome;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vutran.finderawsome.Store.ListStoreFragment;
import com.example.vutran.finderawsome.User.FavouriteFragment;
import com.example.vutran.finderawsome.User.LoginActivity;
import com.example.vutran.finderawsome.User.ManageUserActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import static com.example.vutran.finderawsome.Store.DetailStoreActivity.BUNDLE_DETAIL;
import static com.example.vutran.finderawsome.Store.DetailStoreActivity.LAT_LNG_DETAIL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Define
    private ViewPager pager;
    private TabLayout tabLayout;
    private FirebaseAuth firebaseAuth;
    private TextView textViewNavEmail, textViewNavName;
    private Button buttonLogOut;
    private ImageView imageViewNavAvatar;
    private NavigationView mNavigationView;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Khởi tạo ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Khởi tạo Drawer giữa hai màn hình
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        // Khởi tạo Navigation View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Khởi tạo ánh xạ tới View
        init();

        FragmentManager manager = getSupportFragmentManager();
        adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.getTabAt(1).select();

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            textViewNavEmail.setText(user.getEmail());
            if (user.getDisplayName() != null) {
                textViewNavName.setText(user.getDisplayName());
            }
            if (user.getPhotoUrl() != null) {
                String urlphoto = user.getPhotoUrl().toString();
                Picasso.with(getApplicationContext()).load(urlphoto).into(imageViewNavAvatar);
            }
        }
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra(BUNDLE_DETAIL)) {
            Bundle bundle = intent.getBundleExtra(BUNDLE_DETAIL);
            String latlng = bundle.getString(LAT_LNG_DETAIL);
            tabLayout.getTabAt(1).select();
        }
    }

    // Khởi tạo ánh xạ đến View
    private void init() {
        pager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        textViewNavEmail = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.textViewNavEmail);
        textViewNavName = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.textViewNavName);
        buttonLogOut = (Button) mNavigationView.getHeaderView(0).findViewById(R.id.buttonNavLogOut);
        imageViewNavAvatar = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.imageViewNavAvatar);
    }

    // Chọn item trong tab menu: List Store, Map, Saved Places
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_action_list_stores) {
            tabLayout.getTabAt(0).select();
        }
        if (id == R.id.menu_action_map) {
            tabLayout.getTabAt(1).select();
        }
        if (id == R.id.menu_action_my_saved_places) {
            tabLayout.getTabAt(2).select();
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
