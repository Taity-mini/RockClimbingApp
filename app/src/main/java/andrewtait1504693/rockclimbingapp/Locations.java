package andrewtait1504693.rockclimbingapp;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Locations extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    public static String routeName;

    NewRoute route;
    LatLng p1;
    Button viewRoute;
    TextView routeNameLabel;

    public Locations() {
        // Required empty public constructor
    }

    private SupportMapFragment mSupportMapFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_locations, container, false);
        DBHandler db = new DBHandler(getContext());
        route = db.getRoute(routeName);


        if (googlePlayServicesCheck()) {
            initMap();

            routeNameLabel = (TextView) v.findViewById(R.id.locationRouteName);
            routeNameLabel.setText(routeName.toString());
            viewRoute = (Button) v.findViewById(R.id.btnLocationReturn);
            viewRoute.setOnClickListener(this);


        } else {

            Toast error = Toast.makeText(getActivity().getApplicationContext(), "Google play services unavailable", Toast.LENGTH_LONG);

            error.show();

            FragmentTransaction transaction = ((FragmentActivity) this.getContext()).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, new Home());
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
            transaction.addToBackStack(null);
            transaction.commit();
        }

        return v;
    }

    public void initMap() {
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.locationMap);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.locationMap, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);

                        p1 = getLocationFromAddress(getContext(), route.getRouteLocation());
                        if(p1 != null)
                        {
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(p1).zoom(15.0f).build();
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                            googleMap.moveCamera(cameraUpdate);
                            googleMap.addMarker(new MarkerOptions().position(p1).title(route.getRouteLocation()));
                        }
                        else
                        {
                            Toast error = Toast.makeText(getActivity().getApplicationContext(), "Location not available", Toast.LENGTH_LONG);
                            error.show();
                        }
                    }
                }
            });
        }
    }

    //Check if google play services are running correctly:
    public boolean googlePlayServicesCheck() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();

        int status = api.isGooglePlayServicesAvailable(getActivity());

        if (status != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(status)) {
                api.getErrorDialog(getActivity(), status, 2404).show();
            }
            return false;
        }
        return true;
    }


    //Get map coordinates from route location
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            if (address != null && address.size() > 0) {
                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                p1 = new LatLng(location.getLatitude(), location.getLongitude());

            }
            else {
                return null;
            }
        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onClick(View v) {
        ViewRoute viewRouteFragment = new ViewRoute();

        FragmentTransaction transaction = ((FragmentActivity) this.getContext()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, viewRouteFragment);

        viewRouteFragment.routeName = route.getRouteName();
        transaction.commit();
    }
}
