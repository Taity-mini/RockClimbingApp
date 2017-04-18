package andrewtait1504693.rockclimbingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Routes extends Fragment {

    public static ArrayList<NewRoute> routes;
    FragmentTransaction ft;
    private RecyclerView recyclerView;

    public Routes() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_routes, container, false);
        // Inflate the layout for this fragment

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        DisplayEvents();
    }



    public void refresh() {
        ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public void DisplayEvents() {

        DBHandler db = new DBHandler(getActivity().getApplicationContext());

        routes = new ArrayList<NewRoute>();

        for (NewRoute route : db.getAllEvents()) {
            routes.add(new NewRoute(route.getRouteName(), route.getRouteLocation(), route.getRouteGrade(), route.getRouteDate(), route.getRouteStyle()));
        }


        for (NewRoute n : routes){
            Log.d("My array list content: ",n.getRouteName());
        }

        //Check if there's no routes

        if(routes.size() == 0)
        {
            Toast.makeText(getContext(), "No Climbing Routes in database!", Toast.LENGTH_SHORT).show();
        }

        //Setup recycler view

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView)getActivity().findViewById(R.id.routeList);
        recyclerView.setLayoutManager(manager);

        RecyclerView.Adapter adapter = new RecycleViewAdapter(routes);
        recyclerView.setAdapter(adapter);
        db.close();
    }


}
