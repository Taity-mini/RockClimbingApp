package andrewtait1504693.rockclimbingapp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Tait (1504693) on 13/04/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private List<NewRoute> routes = new ArrayList<>();

    //Constructor
    RecycleViewAdapter(List<NewRoute> routeList) {
        this.routes = routeList;
    }

    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.MyViewHolder holder, final int position) {
        holder.NAME.setText(routes.get(position).getRouteName());
        holder.LOCATION.setText(routes.get(position).getRouteLocation());
        holder.DATE.setText(routes.get(position).getRouteDate());

        //on click listeners

        holder.BUTTON_VIEW.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewRoute viewRouteFragment = new ViewRoute();

                FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, viewRouteFragment);

                viewRouteFragment.routeName = routes.get(position).getRouteName();

                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        holder.BUTTON_EDIT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditRoute editRouteFragment = new EditRoute();

                FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, editRouteFragment);

                editRouteFragment.routeName = routes.get(position).getRouteName();
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        //Display variables on card
        private TextView NAME;
        private TextView LOCATION;
        private TextView DATE;
        private Button BUTTON_VIEW;
        private Button BUTTON_EDIT;

        public MyViewHolder(View itemView) {
            super(itemView);

            NAME = (TextView) itemView.findViewById(R.id.card_Name);
            LOCATION = (TextView) itemView.findViewById(R.id.card_Location);
            DATE = (TextView) itemView.findViewById(R.id.card_date);
            BUTTON_EDIT = (Button) itemView.findViewById(R.id.card_btnEdit);
            BUTTON_VIEW = (Button) itemView.findViewById(R.id.card_btnView);
        }
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }
}
