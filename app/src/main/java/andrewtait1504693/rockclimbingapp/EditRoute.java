package andrewtait1504693.rockclimbingapp;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditRoute extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static String routeName;

    EditText ROUTE_LOCATION, ROUTE_GRADE;
    TextView ROUTE_NAME, ROUTE_DATE;
    Button BUTTON_EDIT, BUTTON_DELETE, BUTTON_DATE;
    Spinner ROUTE_STYLE;
    FragmentTransaction transaction;
    NewRoute editRoute;


    //Get current date
    Calendar calendar = Calendar.getInstance();
    int YEAR = calendar.get(Calendar.YEAR);
    int MONTH = calendar.get(Calendar.MONTH);
    int DAY = calendar.get(Calendar.DAY_OF_MONTH);

    public EditRoute() {
        // Required empty public constructor
    }

    private int getIndex(Spinner spinner, String searchString) {

        if (searchString == null || spinner.getCount() == 0) {

            return -1; // Not found

        }
        else {

            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).toString().equals(searchString)) {
                    return i; // Found!
                }
            }

            return -1; // Not found
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_route, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Update Route");

        DBHandler db = new DBHandler(getContext());
        editRoute = db.getRoute(routeName);

        ROUTE_NAME = (TextView) v.findViewById(R.id.editRouteName);
        ROUTE_NAME.setText(editRoute.getRouteName());

        ROUTE_LOCATION = (EditText) v.findViewById(R.id.editRouteLocation);
        ROUTE_LOCATION.setText(editRoute.getRouteLocation());

        ROUTE_GRADE = (EditText) v.findViewById(R.id.editRouteGrade);
        ROUTE_GRADE.setText(editRoute.getRouteGrade());

        ROUTE_DATE = (TextView) v.findViewById(R.id.editRouteDate);
        ROUTE_DATE.setText(editRoute.getRouteDate());

        ROUTE_STYLE = (Spinner) v.findViewById(R.id.editRouteStyle);

       //int selectionPosition= v.findViewById(R.array.styles).getPosition("YOUR_VALUE");
        ROUTE_STYLE.setSelection(getIndex(ROUTE_STYLE,editRoute.getRouteStyle()));

        //ROUTE_STYLE.setSelection();

        //Buttons
        BUTTON_EDIT = (Button) v.findViewById(R.id.editRouteUpdateBtn);
        BUTTON_DELETE = (Button) v.findViewById(R.id.editRouteDeleteBtn);
        BUTTON_DATE = (Button) v.findViewById(R.id.editRouteDateBtn);

        //Add on click listeners
        BUTTON_EDIT.setOnClickListener(this);
        BUTTON_DELETE.setOnClickListener(this);
        BUTTON_DATE.setOnClickListener(this);

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        transaction = ((FragmentActivity) this.getContext()).getSupportFragmentManager().beginTransaction();
    }


    public boolean FieldValidation(EditText[] fields) {

        for (EditText e : fields) {
            if (e.getText().toString().length() == 0) {
                return false;
            }
        }

        return true;
    }

    public void updateRoute(String name, String location, String grade, String date, String style) {
        DBHandler db = new DBHandler(getActivity().getApplicationContext());


        boolean validatedFields = FieldValidation(new EditText[]{ROUTE_LOCATION, ROUTE_GRADE});

        if (validatedFields) {
            if (ROUTE_DATE.length() != 0) {
                NewRoute route = new NewRoute(name, location, grade, date, style);


                db.updateRoute(route);

                Toast success = Toast.makeText(getActivity().getApplicationContext(), "Route successfully updated!", Toast.LENGTH_LONG);

                success.show();
                db.close();

                ViewRoute viewRouteFragment = new ViewRoute();

                FragmentTransaction transaction = ((FragmentActivity) this.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, viewRouteFragment);

                viewRouteFragment.routeName = route.getRouteName();

                transaction.addToBackStack(null);
                transaction.commit();
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

    }

    private void showAlertDialog() {
        Builder alertDialog = new Builder(this.getContext());
        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure you want delete this?");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DBHandler db = new DBHandler(getActivity().getApplicationContext());
                db.deleteRoute(ROUTE_NAME.getText().toString());
                db.close();
                Toast.makeText(getContext(), "Route successfully deleted.", Toast.LENGTH_SHORT).show();

                displayRouteList();

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void displayRouteList()
    {
        //Switch to routes view
        Routes listRoutes = new Routes();
        transaction.replace(R.id.main_container, listRoutes);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Routes");
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        ROUTE_DATE.setText(dayOfMonth + "/" + month + "/" + year);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.editRouteDateBtn:
                DatePickerDialog date = new DatePickerDialog(getContext(), this, YEAR, MONTH, DAY);
                date.show();
                break;

            case R.id.editRouteUpdateBtn:
                updateRoute(
                        ROUTE_NAME.getText().toString(),
                        ROUTE_LOCATION.getText().toString(),
                        ROUTE_GRADE.getText().toString(),
                        ROUTE_DATE.getText().toString(),
                        ROUTE_STYLE.getSelectedItem().toString()
                );
                break;

            case R.id.editRouteDeleteBtn:
                showAlertDialog();
                break;
        }
    }
}
