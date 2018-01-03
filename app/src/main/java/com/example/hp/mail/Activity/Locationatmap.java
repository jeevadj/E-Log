package com.example.hp.mail.Activity;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.DT;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Locationatmap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button b;
    Spinner spinner;
    String mtype = "",loca="";
    Double latlng=11.456, lglng=79.987;
    Marker marker;
    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.hp.mail.R.layout.activity_locationatmap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        b=(Button)findViewById(R.id.Ok);
//        ed=(EditText)findViewById(R.id.searcharea);
        spinner = (Spinner) findViewById(R.id.type);
        ArrayList<String> type = new ArrayList<>();
        type.add("Terrain");
        type.add("Satellite");
        type.add("Traffic");
        ArrayAdapter<String> tt = new ArrayAdapter<String>(Locationatmap.this, android.R.layout.simple_spinner_dropdown_item, type);
        tt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(tt);
//        b.setEnabled(false);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loca.isEmpty()){
                    Toast.makeText(Locationatmap.this,"Change the marker location", Toast.LENGTH_SHORT).show();
                }
                else{
                    create_event ce=new create_event();
                    ce.loc.setText(loca);
                    Toast.makeText(Locationatmap.this,loca, Toast.LENGTH_SHORT).show();
                    DT.setLat(latlng);
                    DT.setLng(lglng);
                    finish();

                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pondy = new LatLng(11.9139, 79.8145);
        marker=mMap.addMarker(new MarkerOptions().position(pondy).title("Marker in Puducherry"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pondy));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mtype=spinner.getSelectedItem().toString();

                if(mtype.equals("Terrain")){ mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }

                if(mtype.equals("Satellite")){ mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);}

                if(mtype.equals("Traffic")) {mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latlng=latLng.latitude;
                lglng=latLng.longitude;

                Toast.makeText(Locationatmap.this, ""+latlng+""+lglng, Toast.LENGTH_SHORT).show();
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                lglng=latLng.longitude;
                latlng=latLng.latitude;
                marker.remove();
                Toast.makeText(getApplicationContext(),"Marker changed to the selected Location",
                        Toast.LENGTH_SHORT).show();
               // b.setEnabled(true);


                try {
                    geocoder = new Geocoder(Locationatmap.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latlng,lglng , 1);
                    StringBuilder str = new StringBuilder();
                    System.out.println("bow"+geocoder.isPresent());
                    if (geocoder.isPresent()) {
                  Toast.makeText(getApplicationContext(),
                          "geocoder present", Toast.LENGTH_SHORT).show();
                        Address returnAddress = addresses.get(0);
                        String locality="";
                        if(returnAddress.getLocality().equals(null)){
                            locality=returnAddress.getLocality();
                        }
                        String featurename=returnAddress.getFeatureName();
                       // String premises=returnAddress.getSubLocality();
                        //String throughfare=returnAddress.getThoroughfare();
                        String area=returnAddress.getAdminArea();
                        String city = returnAddress.getCountryName();
                        // String region_code = returnAddress.getCountryCode();
                        // String zipcode = returnAddress.getPostalCode();
                        if(featurename.equals(null)){}else{  str.append(featurename+" ");}
                        if(locality.equals(null)){}else if(locality.equals(featurename)){} else { str.append(locality+" ");}
                        if(area.equals(null)){}else { str.append(area+" ");}
                        if(city.equals(null)){}else if(city.equals(area)){} else { str.append(city + " ");}
                        // str.append(zipcode + " " );

                        marker=mMap.addMarker(new MarkerOptions().position(latLng).title(str.toString()));
                        loca=str.toString();


                        //address.setText(str);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "geocoder not present", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("bow"+e);
                    //Toast.makeText(Locationatmap.this, e.toString(), Toast.LENGTH_SHORT).show();
                    if(e.toString().equals("java.io.IOException: Service not Available")){
                        Toast.makeText(Locationatmap.this, "Geocoder is Unavailable...\n So please reboot your SmartPhone", Toast.LENGTH_SHORT).show();
                    }

                                  }


            }
        });
    }

}

