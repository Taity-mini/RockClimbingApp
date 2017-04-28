package andrewtait1504693.rockclimbingapp;


import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;


/**
 * A simple {@link Fragment} subclass.
 */

public class AddRoute extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText ROUTE_NAME, ROUTE_LOCATION, ROUTE_GRADE;
    TextView ROUTE_DATE;
    Button dateBtn, addBtn, resetBtn;
    Spinner ROUTE_STYLE;
    FragmentTransaction ft;

    //Calender info

    //Get current date
    Calendar calendar = Calendar.getInstance();
    int YEAR = calendar.get(Calendar.YEAR);
    int MONTH = calendar.get(Calendar.MONTH);
    int DAY = calendar.get(Calendar.DAY_OF_MONTH);


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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Route");
        //Add on click listeners
        dateBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);

        return v;

    }

    public boolean FieldValidation(EditText[] fields) {

        for (EditText e : fields) {
            if (e.getText().toString().length() == 0) {
                return false;
            }
        }

        return true;
    }

    public void refresh() {
        ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }


    public void insertNewRoute(String name, String location, String grade, String date, String style) {
        DBHandler db = new DBHandler(getActivity().getApplicationContext());

        if (!db.checkRouteExists(name)) {
            boolean validatedFields = FieldValidation(new EditText[]{ROUTE_NAME, ROUTE_LOCATION, ROUTE_GRADE});

            if (validatedFields) {
                if (ROUTE_DATE.length() != 0) {
                    NewRoute route = new NewRoute(name, location, grade, date, style);


                    db.newRoute(route);

                    Toast success = Toast.makeText(getActivity().getApplicationContext(), "Route successfully added!", Toast.LENGTH_LONG);

                    success.show();

                    Routes listRoutes = new Routes();

                    FragmentTransaction transaction = ((FragmentActivity) this.getContext()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_container, listRoutes);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Routes");

                    transaction.addToBackStack(null);
                    transaction.commit();


                    db.close();
                } else {
                    Toast error = Toast.makeText(getActivity().getApplicationContext(), "Date field is empty, please select one!", Toast.LENGTH_LONG);
                    error.show();
                    db.close();
                }

            } else {
                Toast error = Toast.makeText(getActivity().getApplicationContext(), "Some fields are empty, please enter them all!", Toast.LENGTH_LONG);
                error.show();
                db.close();
            }
        } else {
            Toast error = Toast.makeText(getActivity().getApplicationContext(), "Route name already exists, try another", Toast.LENGTH_LONG);
            error.show();
            db.close();
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNewRoute:
                insertNewRoute(
                        ROUTE_NAME.getText().toString(),
                        ROUTE_LOCATION.getText().toString(),
                        ROUTE_GRADE.getText().toString(),
                        ROUTE_DATE.getText().toString(),
                        ROUTE_STYLE.getSelectedItem().toString()
                );
                break;

            case R.id.btnDate:
                DatePickerDialog date = new DatePickerDialog(getContext(), this, YEAR, MONTH, DAY);
                date.show();
                break;

            case R.id.btnRouteReset:
                refresh();
                break;
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        ROUTE_DATE.setText(dayOfMonth + "/" + month + "/" + year);

    }
}
