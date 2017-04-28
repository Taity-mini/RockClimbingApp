package andrewtait1504693.rockclimbingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    TextView routeCount, routeBestGrade, routeAVGRoute;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);




        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        DBHandler db = new DBHandler(getContext());

        routeCount = (TextView) getActivity().findViewById(R.id.textTotalRoutes);

        routeCount.setText(String.valueOf(db.getRouteCount()));

//        routeBestGrade =(TextView) getActivity().findViewById(R.id.textFavouriteStyle);
//
//        routeBestGrade.setText(db.getFavouriteStyle());
    }

}
