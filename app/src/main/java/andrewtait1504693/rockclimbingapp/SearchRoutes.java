package andrewtait1504693.rockclimbingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRoutes extends Fragment implements View.OnClickListener {

    EditText searchQuery;
    Button searchButton;

   List<NewRoute> searchResults;
    DBHandler database;

    public SearchRoutes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_routes, container, false);

        database = new DBHandler(getActivity().getApplicationContext());

        searchButton = (Button) v.findViewById(R.id.searchBtn);
        searchQuery = (EditText) v.findViewById(R.id.searchRoute);

        searchButton.setOnClickListener(this);

        return v;
    }

    public void onClick(View v) {
        String searchTerm = searchQuery.getText().toString();
        searchResults = new ArrayList<NewRoute>();

        //Search checks

        if (searchTerm.equals("")) {

            Toast.makeText(getContext(), "Please enter a least one search term", Toast.LENGTH_SHORT).show();

        } else {

            if (database.searchRouteResults(searchTerm).size() == 0) {
                Toast.makeText(getContext(), "There are no search results on that term", Toast.LENGTH_SHORT).show();
            } else {
                if (database.searchRouteResults(searchTerm) != null) {
                    for (NewRoute route : database.searchRouteResults(searchTerm)) {
                        searchResults.add(new NewRoute(route.getRouteName(), route.getRouteLocation(), route.getRouteGrade(), route.getRouteDate(), route.getRouteStyle()));
                        database.close();

                        updateView();
                    }
                }

            }
        }


    }

    public void updateView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.searchResultsList);
        recyclerView.setLayoutManager(manager);

        RecyclerView.Adapter adapter = new RecycleViewAdapter(searchResults);
        recyclerView.setAdapter(adapter);
    }

}
