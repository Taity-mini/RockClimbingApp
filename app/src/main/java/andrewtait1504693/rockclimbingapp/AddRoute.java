package andrewtait1504693.rockclimbingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRoute extends Fragment implements View.OnClickListener {

    EditText ROUTE_NAME, ROUTE_LOCATION, ROUTE_GRADE;
    TextView ROUTE_DATE;
    Button dateBtn, addBtn, resetBtn;
    Spinner ROUTE_STYLE;

    public AddRoute() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_route, container, false);

        //Assign variables to fields/buttons
        ROUTE_NAME = (EditText) v.findViewById(R.id.txtRouteName);
        ROUTE_LOCATION = (EditText) v.findViewById(R.id.txtRouteLocation);
        ROUTE_GRADE = (EditText) v.findViewById(R.id.txtRouteGrade);
        ROUTE_DATE = (TextView) v.findViewById(R.id.dateView);

        //Buttons
        dateBtn = (Button) v.findViewById(R.id.btnDate);
        addBtn = (Button) v.findViewById(R.id.btnAddNewRoute);
        resetBtn = (Button) v.findViewById(R.id.btnRouteReset);

        //Spinner

        ROUTE_STYLE = (Spinner) v.findViewById(R.id.spinnerRouteStyles);


        //Add on click listeners
        dateBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);

        return v;

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNewRoute:
                break;

            case R.id.btnDate:
                break;

            case R.id.btnRouteReset:
                //Clear all form fields
               // ROUTE_NAME.setText("Test ");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;
        }

    }

}
