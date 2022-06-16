package android.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.predictions.models.LanguageType;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;

public class TaskDetail extends AppCompatActivity  implements OnMapReadyCallback {

    private final MediaPlayer mp = new MediaPlayer();
    private TextView textView2;
    private TextView textView;
    private TextView textView3;



    // initializing
    // FusedLocationProviderClient
    // object
    private FusedLocationProviderClient mFusedLocationClient;

    private int PERMISSION_ID = 44;

    private double latitude;
    private double longitude;

    private GoogleMap googleMap;

    private Button toggle;

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            LatLng coordinate = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(coordinate)
                    .title("Marker"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 12.0f));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        textView = findViewById(R.id.textView5);
        textView2 = findViewById(R.id.textView8);
        textView3 = findViewById(R.id.textView6);
        Button speech=findViewById(R.id.button8);
        Button Translating=findViewById(R.id.button9);
        ImageView ImageView=findViewById(R.id.imageView3);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        ActionBar actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);
speech.setOnClickListener(view ->Text_to_speech() );
        Translating.setOnClickListener(view ->Text_to_Translating() );

        // method to get the location
        getLastLocation();


        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent passedIntent = getIntent();
        String tasks = passedIntent.getStringExtra("task");
        String taskState = passedIntent.getStringExtra("taskstate");
        String taskBody = passedIntent.getStringExtra("taskBody");

        String taskuri = passedIntent.getStringExtra("taskuri");
//        Uri ImageUri=(Uri) Uri.parse(taskuri);

        textView.setText(tasks);
        textView2.setText(taskState);
        textView3.setText(taskBody);
if (taskuri!=null) {
//    ImageView.setImageURI(ImageUri);
}
}

    private void Text_to_Translating() {

        Amplify.Predictions.translateText(
                textView3.getText().toString() ,
                LanguageType.ENGLISH,
                LanguageType.ITALIAN,
                result -> Log.i("MyAmplifyApp", result.getTranslatedText()),
                error -> Log.e("MyAmplifyApp", "Translation failed", error)
        );



    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;


        }


        switch (item.getItemId()) {
            case R.id.action_mainpage:
                navigateToMain();
                return true;
            case R.id.action_SettingsDetail:
                Toast.makeText(this, "Copyright 2022", Toast.LENGTH_SHORT).show();
                navigateToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.taskdetail, menu);
        return true;
    }

    private void navigateToSettings() {
        Intent weatherDetailsIntent = new Intent(this, Settings.class);
        startActivity(weatherDetailsIntent);
    }

    private void navigateToMain() {
        Intent settingsIntent = new Intent(this, MainActivity.class);
        startActivity(settingsIntent);
    }

    private void playAudio(InputStream data) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
        }

    }

public void Text_to_speech(){
    Amplify.Predictions.convertTextToSpeech(
            textView.getText().toString(),
            result -> playAudio(result.getAudioData()),
            error -> Log.e("MyAmplifyApp", "Conversion failed", error)
    );

    Amplify.Predictions.convertTextToSpeech(
            textView2.getText().toString(),
            result -> playAudio(result.getAudioData()),
            error -> Log.e("MyAmplifyApp", "Conversion failed", error)
    );
    Amplify.Predictions.convertTextToSpeech(
            textView3.getText().toString(),
            result -> playAudio(result.getAudioData()),
            error -> Log.e("MyAmplifyApp", "Conversion failed", error)
    );

}


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setBuildingsEnabled(true);
        this.googleMap.setTrafficEnabled(true);
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            LatLng coordinate = new LatLng(latitude, longitude);

                            googleMap.addMarker(new MarkerOptions()
                                    .position(coordinate)
                                    .title("Marker"));

//                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 12.0f));
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }

    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat
                        .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}