package andrewtait1504693.rockclimbingapp;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

    }

    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
