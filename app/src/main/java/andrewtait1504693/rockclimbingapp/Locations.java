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
public class Locations extends Fragment implements OnMapReadyCallback {

    public static String routeName;

    NewRoute route;
    GoogleMap map;
    MapView mapView;
    Marker marker;
    LatLng p1;
    FragmentTransaction fragmentTransaction;

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
        } else {

            Toast success = Toast.makeText(getActivity().getApplicationContext(), "Google play services unavailable", Toast.LENGTH_LONG);

            success.show();

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

                        LatLng sydney = new LatLng(-34, 151);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(p1).zoom(15.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.moveCamera(cameraUpdate);
                        googleMap.addMarker(new MarkerOptions().position(p1).title(route.getRouteLocation()));

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
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
