package hu.ait.android.locationdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener,
        OnMapReadyCallback {

    private TextView tvLocation;
    private LocationManager locationManager;

    private Location prevLocation = null;
    private float distance = 0;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLocation = findViewById(R.id.tvLocation);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        requestNeededPermission();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Toast...
            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    101);
        } else {
            startLocationMonitoring();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted, jupeee!", Toast.LENGTH_SHORT).show();

                // start our job
                startLocationMonitoring();
            } else {
                Toast.makeText(this, "Permission not granted :(", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startLocationMonitoring() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void stopLocationMonitoring() {
        locationManager.removeUpdates(this);
    }



    @Override
    public void onLocationChanged(Location location) {
        if (prevLocation != null) {
            distance += location.distanceTo(prevLocation);
        }

        prevLocation = location;


        StringBuilder sb = new StringBuilder();
        sb.append("Lat: "+location.getLatitude()+"\n");
        sb.append("Lng: "+location.getLongitude()+"\n");
        sb.append("Provider: "+location.getProvider()+"\n");
        sb.append("Accuracy: "+location.getAccuracy()+"\n");
        sb.append("Altitude: "+location.getAltitude()+"\n");
        sb.append("Distance: "+distance+"\n");
        sb.append("Speed: "+location.getSpeed());

        tvLocation.setText(sb.toString());


        mMap.animateCamera(CameraUpdateFactory.newLatLng(
                new LatLng(location.getLatitude(), location.getLongitude())
        ));
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
    protected void onStop() {
        super.onStop();
        stopLocationMonitoring();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng hungary = new LatLng(47, 19);
        Marker myMarker = mMap.addMarker(
                new MarkerOptions().
                        position(hungary).
                        title("Marker in Hungary").
                        snippet("This is a marker"));
        myMarker.setDraggable(true);


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                try {
                    Geocoder gc = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addrs = null;
                    addrs = gc.getFromLocation(latLng.latitude, latLng.longitude, 2);

                    mMap.addMarker(
                            new MarkerOptions().
                                    position(latLng).
                                    title("Marker").
                                    snippet(
                                            addrs.get(0).getAddressLine(0)+"\n"+
                                            addrs.get(0).getAddressLine(1)+"\n"+
                                            addrs.get(0).getAddressLine(2)
                                    ));

                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });




        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                //marker.getPosition().latitude
            }
        });


        mMap.moveCamera(CameraUpdateFactory.newLatLng(hungary));

        mMap.setTrafficEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

    }
}
