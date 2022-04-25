package com.example.agrocare.ui.area;

import static com.google.android.material.floatingactionbutton.FloatingActionButton.SIZE_MINI;
import static com.google.android.material.floatingactionbutton.FloatingActionButton.SIZE_NORMAL;
import static com.google.maps.android.SphericalUtil.computeArea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.agrocare.R;
import com.example.agrocare.ui.WeatherFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private Marker marker0;
    private Button autoCompleteButton;
    private Button solarsitecalcButton;
    int PERMISSION_ID = 45;
    FusedLocationProviderClient mFusedLocationClient;
    Double latTextView, lonTextView;
    //j keeps count of the number of the markers
    static int j = -1;
    private double[] distanceArray = new double[200];
    private Polyline polyline;
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> coodlist = new ArrayList<LatLng>();
    private ArrayList<Double> computedArea = new ArrayList<Double>();
    private float[] dis = new float[1];
    private static int current_initial_marker_value = 0;
    private ArrayList<Integer> end_marker_value_array = new ArrayList<Integer>();
    private FloatingActionButton addareafab;
    private FloatingActionButton minusareafab;
    private int firstmarkerno = -1;
    private int lastmarkerno = -1;
    private boolean areacalcflag = false;
    private double temp = 0;
    private double temp1 = 0;
    private int addorsubflag = 1;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            getLastLocation();
            //Button creation for autocomplete and find area


            //setting up custom info window adapter to map
            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));


            addareafab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //flag for indicating that area is under change
                    areacalcflag = false;

                    if (j == -1)
                        Toast.makeText(getContext(), "First calculate area to add", Toast.LENGTH_SHORT).show();

                    else {

                        if (!autoCompleteButton.getText().equals("Auto Complete")) {
                            autoCompleteButton.setText("Auto Complete");
                        }

                        //check if minus area is active while this button is pressed
                        if (minusareafab.getSize() == SIZE_NORMAL)
                            Toast.makeText(getContext(), "Minus Area is active now", Toast.LENGTH_SHORT).show();

                            //if button is inactive (and minus is also inactive as previously checked) make the current one active
                        else if (addareafab.getSize() == SIZE_MINI) {


                            if (computedArea.size() == 0)
                                Toast.makeText(getContext(), "First calculate area to subtract", Toast.LENGTH_SHORT).show();
                            else {


                                //stores marker value as soon as add/sub button is pressed
                                firstmarkerno = j;

                                addareafab.setSize(SIZE_NORMAL);

                                //stores first value of marker of a new group
                                current_initial_marker_value = j + 1;
                            }
                        } else if (addareafab.getSize() == SIZE_NORMAL) {

                            //just a variable to check is no marker is placed between making the button active and inactive
                            lastmarkerno = j;
                            if (firstmarkerno == lastmarkerno) {
                                current_initial_marker_value = end_marker_value_array.get(end_marker_value_array.size() - 1);
                                firstmarkerno = current_initial_marker_value - 1;
                                addareafab.setSize(SIZE_MINI);
                                autoCompleteButton.setText(computedArea.get(computedArea.size() - 1) + " sq ft");
                            } else
                                Toast.makeText(getContext(), "Add area is progress, delete items to cancel", Toast.LENGTH_SHORT).show();


                        }
                    }

                }
            });


            //minusareafab


            minusareafab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //flag for indicating that area is under change
                    areacalcflag = false;


                    if (j == -1)
                        Toast.makeText(getContext(), "First calculate area to subtract", Toast.LENGTH_SHORT).show();

                    else {

                        if (!autoCompleteButton.getText().equals("Auto Complete")) {
                            autoCompleteButton.setText("Auto Complete");
                        }

                        //check if add area is active while this button is pressed
                        if (addareafab.getSize() == SIZE_NORMAL)
                            Toast.makeText(getContext(), "Add Area is active now", Toast.LENGTH_SHORT).show();

                            //if button is inactive and add is also inactive as previously checked make the current one active
                        else if (minusareafab.getSize() == SIZE_MINI) {

                            if (computedArea.size() == 0)
                                Toast.makeText(getContext(), "First calculate area to subtract", Toast.LENGTH_SHORT).show();
                            else {

                                //stores marker value as soon as add/sub button is pressed
                                firstmarkerno = j;

                                minusareafab.setSize(SIZE_NORMAL);

                                //make flag -1 to indicate current option is minus and hence area stored has to be subtracted
                                addorsubflag = -1;

                                //stores first value of marker of a new group
                                current_initial_marker_value = j + 1;
                            }

                        } else if (minusareafab.getSize() == SIZE_NORMAL) {

                            //just a variable to check is no marker is placed between making the button active and inactive
                            lastmarkerno = j;

                            if (firstmarkerno == lastmarkerno) {
                                minusareafab.setSize(SIZE_MINI);
                                current_initial_marker_value = end_marker_value_array.get(end_marker_value_array.size() - 1);
                                firstmarkerno = current_initial_marker_value - 1;
                                autoCompleteButton.setText(computedArea.get(computedArea.size() - 1) + " sq ft");
                                addorsubflag = 1;
                            } else
                                Toast.makeText(getContext(), "Minus area is progress, delete items to cancel", Toast.LENGTH_SHORT).show();


                        }
                    }
                }
            });


            //Long click listener for placing marker on map
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {

                    //a flag to check if area of a group is already calculated
                    if (areacalcflag == true)
                        Toast.makeText(getContext(), "Delete markers to recompute area or use add/sub buttons for additional computations", Toast.LENGTH_LONG).show();
                    else {

                        //areacalcflag = false;


                        ++j;

                        //intialize marker and its attributes
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .visible(true)
                                .draggable(true)
                                .title(String.valueOf(distanceArray[j]) + " m")
                                .snippet("Marker " + (j + 1)));
                        if (j == current_initial_marker_value)
                            marker0 = marker;

                        marker.setDraggable(false);

                        //gets markerposition and stores in array of coords
                        coodlist.add(marker.getPosition());

                        //check if no of points is greater than 1 to find distance
                        if (j > current_initial_marker_value) {

                            //calculate distance between current point and previous point
                            Location.distanceBetween((coodlist.get(j - 1)).latitude, (coodlist.get(j - 1)).longitude, (coodlist.get(j)).latitude, (coodlist.get(j)).longitude, dis);
                            //keep distance in distance array
                            distanceArray[j] = round(dis[0], 2);

                            marker.setTitle(String.valueOf(distanceArray[j]) + " m");

                            //connect line between the two points
                            polylineOptions = new PolylineOptions().add(new LatLng((coodlist.get(j - 1)).latitude, (coodlist.get(j - 1)).longitude)).add(new
                                    LatLng((coodlist.get(j)).latitude, (coodlist.get(j)).longitude));
                            polyline = mMap.addPolyline(polylineOptions.color(Color.BLUE).clickable(true));
                        }
                    }


                }
            });

            mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {

                @Override
                public void onPolylineClick(Polyline polyline) {

                    if (!autoCompleteButton.getText().equals("Auto Complete")) {
                        //if theres more than one computed area, remove the last one, and show the previous computed area
                        if (computedArea.size() > 1) {
                            computedArea.remove(computedArea.size() - 1);
                            autoCompleteButton.setText(computedArea.get(computedArea.size() - 1) + " sq ft");
                            //autoCompleteButton.append(cs);
                        } else
                            autoCompleteButton.setText("Auto Complete");
                    }

                    polyline.remove();
                }
            });


            autoCompleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    //check if minimun of 3 markers are present to compute area
                    if (j > 1 && j - firstmarkerno > 2) {

                        //set true because autocomplete computes area of the group
                        areacalcflag = true;

                        //if there are more than one group, check if marker of last group is equal to the current one, hence to indicate the autocomplete is unknowingly pressed and no need to add marker value to group of new markers
                        if (end_marker_value_array.size() >= 1) {

                            if (end_marker_value_array.get(end_marker_value_array.size() - 1) == current_initial_marker_value)
                                Toast.makeText(getContext(), "No changes made to the area", Toast.LENGTH_SHORT).show();
                        } else
                            //if last added marker and current is different add the current one
                            end_marker_value_array.add(current_initial_marker_value);


                        polylineOptions = new PolylineOptions().add(new LatLng((coodlist.get(j)).latitude, (coodlist.get(j)).longitude)).add(new LatLng((coodlist.get(current_initial_marker_value)).latitude, (coodlist.get(current_initial_marker_value)).longitude));
                        polyline = mMap.addPolyline(polylineOptions.color(Color.BLUE).clickable(true));

                        Location.distanceBetween((coodlist.get(current_initial_marker_value)).latitude, (coodlist.get(current_initial_marker_value)).longitude, (coodlist.get(j)).latitude, (coodlist.get(j)).longitude, dis);

                        distanceArray[j] = round(dis[0], 2);
                        //Toast.makeText(getContext(),""+dis[0],Toast.LENGTH_SHORT).show();

                        marker0.setTitle(String.valueOf(distanceArray[j]) + " m");

                        //converting square meter to square foot and rounding it off to 2 decimal places
                        //converting factor = 10.7639
                        temp = round(computeArea(coodlist) * 10.7639, 2);
                        //if only one computed area exists, show  it
                        if (end_marker_value_array.size() == 1)
                            computedArea.add(temp);
                            //add all stored areas and show it by adding it with the current one
                        else {
                            for (int i = 0; i < computedArea.size(); i++) {
                                temp1 = temp1 + computedArea.get(i) * addorsubflag;
                            }

                            temp1 = temp1 + temp;
                            computedArea.add(temp1);
                        }


                        if (autoCompleteButton.getText().equals("Auto Complete")) {
                            if (computedArea.size() > 1) {
                                autoCompleteButton.setText(computedArea.get(computedArea.size() - 1) + " sq ft");
                                // autoCompleteButton.append(cs);
                            } else {
                                autoCompleteButton.setText(computedArea.get(0) + " sq ft");
                                //autoCompleteButton.append(cs);
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Need atleast three points", Toast.LENGTH_LONG).show();
                    }

                    addareafab.setSize(SIZE_MINI);
                    minusareafab.setSize(SIZE_MINI);
                }
            });


            //remove markers
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    //if current stored value of a marker is of new group that doesnt exist remove it from the array
                    if (current_initial_marker_value > j) {
                        if (end_marker_value_array.size() > 1)
                            end_marker_value_array.remove(end_marker_value_array.size() - 1);
                        //set current marker value to last stored value
                        current_initial_marker_value = end_marker_value_array.get(end_marker_value_array.size() - 1);
                        firstmarkerno = current_initial_marker_value - 1;
                    }


                    //remove marker if its only of the new group ie greater than or the last stored marker value
                    if (coodlist.indexOf(marker.getPosition()) >= current_initial_marker_value) {
                        //remove coordinates of the marker
                        coodlist.remove(marker.getPosition());
                        //remove marker
                        marker.remove();
                        //decrement total marker number
                        --j;

                    } else {
                        Toast.makeText(getContext(), "Delete newly created segments before this ", Toast.LENGTH_SHORT).show();
                    }

                    if (!autoCompleteButton.getText().equals("Auto Complete")) {
                        if (computedArea.size() > 1) {
                            computedArea.remove(computedArea.size() - 1);
                            autoCompleteButton.setText(computedArea.get(computedArea.size() - 1) + " sq ft");
                            //autoCompleteButton.append(cs);
                        } else
                            autoCompleteButton.setText("Auto Complete");
                    }
                }
            });

            //move to solar activity for calculations

            solarsitecalcButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("http://indiagoessolar.com/solar-calculator/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (computedArea.size() >= 1)
                        startActivity(intent);
                    else
                        Toast.makeText(getContext(), "Calculate area to find solar panel details", Toast.LENGTH_SHORT).show();


                }
            });


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_maps, container, false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        autoCompleteButton = (Button) view.findViewById(R.id.autoComplete);
        addareafab = (FloatingActionButton) view.findViewById(R.id.fabadd);
        minusareafab = (FloatingActionButton) view.findViewById(R.id.fabminus);
        solarsitecalcButton = (Button) view.findViewById(R.id.bsolarpanel);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latTextView = (location.getLatitude());
                                    lonTextView = (location.getLongitude());
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                                            .zoom(17)                   // Sets the zoom
                                            .bearing(90)                // Sets the orientation of the camera to east
                                            .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                                            .build();                   // Creates a CameraPosition from the builder
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(getContext(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latTextView = mLastLocation.getLatitude();
            lonTextView = mLastLocation.getLongitude();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }

}