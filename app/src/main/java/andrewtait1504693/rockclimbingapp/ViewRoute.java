package andrewtait1504693.rockclimbingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewRoute extends Fragment implements View.OnClickListener {

    public static String routeName;

    TextView ROUTE_NAME, ROUTE_LOCATION, ROUTE_GRADE, ROUTE_DATE, ROUTE_STYLE;
    Button BUTTON_EDIT, BUTTON_LOCATION;
    NewRoute viewRoute;


    public ViewRoute() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_route, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("View Route");
        DBHandler db = new DBHandler(getContext());
        viewRoute = db.getRoute(routeName);

        //Set Fields from route table row

        ROUTE_NAME = (TextView) v.findViewById(R.id.viewRouteName);
        ROUTE_NAME.setText(viewRoute.getRouteName());

        ROUTE_LOCATION = (TextView) v.findViewById(R.id.viewRouteLocation);
        ROUTE_LOCATION.setText(viewRoute.getRouteLocation());

        ROUTE_GRADE = (TextView) v.findViewById(R.id.viewRouteGrade);
        ROUTE_GRADE.setText(viewRoute.getRouteGrade());

        ROUTE_DATE = (TextView) v.findViewById(R.id.viewRouteDate);
        ROUTE_DATE.setText(viewRoute.getRouteDate());

        ROUTE_STYLE = (TextView) v.findViewById(R.id.viewRouteStyle);
        ROUTE_STYLE.setText(viewRoute.getRouteStyle());


        //Buttons
        BUTTON_EDIT = (Button) v.findViewById(R.id.viewRouteEditBtn);
        BUTTON_LOCATION = (Button) v.findViewById(R.id.viewRouteLocationBtn);

        //Add on click listeners
        BUTTON_EDIT.setOnClickListener(this);
        BUTTON_LOCATION.setOnClickListener(this);

        return v;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewRouteEditBtn:
                EditRoute editRouteFragment = new EditRoute();

                FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, editRouteFragment);

                editRouteFragment.routeName = viewRoute.getRouteName();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Update Route");
                transaction.addToBackStack(null);
                transaction.commit();

                break;

            case R.id.viewRouteLocationBtn:

                Locations locationFragement = new Locations();
                transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, locationFragement);

                locationFragement.routeName = viewRoute.getRouteName();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Route Location");
                transaction.addToBackStack(null);
                transaction.commit();

                break;
        }
    }

}
