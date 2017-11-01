package topsint.myfriendsfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static topsint.myfriendsfinder.setProfileActivity.decodeBase64;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,LocationListener {

    ImageView imgprofile;
    ImageView btnprofile, btnHome,btnLocation;
    TextView username;
    ArrayList<bean> arrayList;
    public static String IMAGE = "";
    public static Bitmap bm;
    ColorFilter colorFilter;
    bean Bean;

    GoogleApiClient googleApiClient;
    private double Latitude;
    private double Longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnLocation=(ImageView) findViewById(R.id.btnlocation);
        username = (TextView) findViewById(R.id.Name_for_profile);
        colorFilter = new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        btnprofile = (ImageView) findViewById(R.id.btnProfile);
        btnHome = (ImageView) findViewById(R.id.btnHome);
        imgprofile = (ImageView) findViewById(R.id.image_for_profile_fragment);
        arrayList = new ArrayList<>();


        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,CurrentLocationMap.class);
                i.putExtra("Lat",Latitude);
                i.putExtra("Long",Longitude);
                startActivity(i);
                HomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TimelineFragment()).commit();


            }
        });


        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("username", PrefUtil.getstringPref("username", HomeActivity.this));
                hashMap.put("password", PrefUtil.getstringPref("password", HomeActivity.this));

                JSONHelper jsonHelper = new JSONHelper(HomeActivity.this, "http://" + StoreData.STATIC_IP_ADDRESS + ":8080/friends_finder/loadimage", hashMap, new OnAsyncLoader() {
                    @Override
                    public void onResult(String result) throws JSONException {
                        Log.e("Result", result + " test");

                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            bean Bean = new bean();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            StoreData.username = jsonObject.getString("username");
                            StoreData.Image = jsonObject.getString("imgPath");
                            Log.e(StoreData.Image + "stored", " Image");
                            Bean.setId(jsonObject.getString("name"));
                            Bean.setName(jsonObject.getString("userid"));
                            Log.e(Bean.getPath() + "image", " test1");
                            Log.e(Bean.getName() + "name", " test1");
                            arrayList.add(Bean);
                            IMAGE = jsonObject.getString("imgPath");
                            bm = decodeBase64(IMAGE);
                            Log.e("test2", jsonObject.getString("imgPath"));
                        }

                    }
                });
                jsonHelper.execute();

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Logout) {
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);

            PrefUtil.RemoveString(HomeActivity.this);
            PrefUtil.putbooleanPref(PrefUtil.PRE_LOGINCHECK, false, getApplicationContext());
            startActivity(i);
            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onLocationChanged(Location location) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "permission error", Toast.LENGTH_SHORT).show();
            return;
        }
        location=LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null){
            Toast.makeText(this, "location is null", Toast.LENGTH_SHORT).show();
        }
        else{
            Latitude= location.getLatitude();
            Longitude= location.getLongitude();
            findAddres(Latitude,Longitude);
            Log.e("Locations ", String.valueOf(location.getLatitude())+location.getLongitude());

        }
    }

    public void findAddres(Double latitude,Double longitude){
        Geocoder geocoder=new Geocoder(HomeActivity.this, Locale.getDefault());
        String s="Addres ---->";{
            try {
                List<Address> list=geocoder.getFromLocation(latitude,longitude,5);

                for (int i=0;i<list.size();i++){
                    Address address=list.get(i);
                    s += "Address " +(i+1) +" : \n";
                    for (int j=0;j<address.getMaxAddressLineIndex(); j++){
                        s += address.getAddressLine(j) + ", ";
                    }
                    s += address.getLocality() + "("+address.getPostalCode()+"), "+ address.getCountryName()+ "("+ address.getCountryCode()+")\n\n";


                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "permission error", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location=LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null){
            Toast.makeText(this, "location is null", Toast.LENGTH_SHORT).show();
        }
        else{
            Latitude=location.getLatitude();
            Longitude=location.getLongitude();
            findAddres(Latitude,Longitude);
            Log.e("LatAndLong"," LAtitude :- "+location.getLatitude()+" Longitute :- "+location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

