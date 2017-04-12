package andrewtait1504693.rockclimbingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRoute extends Fragment {


    public AddRoute() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_route, container, false);

//        Spinner spinner = (Spinner) spinner.findViewById(R.id.spinner_styles);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, android.R.string.route_style_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);



    }

}
