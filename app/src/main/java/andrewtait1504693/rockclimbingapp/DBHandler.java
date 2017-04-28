package andrewtait1504693.rockclimbingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Andrew Tait (1504693) on 13/04/2017.
 * Database handler
 */

public class DBHandler extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "routes.db";
    public static final String TABLE_ROUTES = "routesTable";


    //Database Columns
    public static String KEY_NAME = "routeName";
    public static final String KEY_LOCATION = "routeLocation";
    public static final String KEY_GRADE = "routeGrade";
    public static final String KEY_DATE = "routeDate";
    public static final String KEY_STYLE = "routeStyle";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_ROUTES + " " + "(" +
                        KEY_NAME + " TEXT, " +
                        KEY_LOCATION + " TEXT, " +
                        KEY_GRADE + " TEXT, " +
                        KEY_DATE + " TEXT, " +
                        KEY_STYLE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
        onCreate(db);
    }

    public boolean checkRouteExists(String name) {


        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_ROUTES + " WHERE " + KEY_NAME + " =?";


        Cursor cursor = db.rawQuery(selectString, new String[] {name});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
            Log.d(TAG, String.format("%d records found", count));

        }

        cursor.close();
        db.close();
        return hasObject;
    }

    //Create new route
    public void newRoute(NewRoute route) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, route.getRouteName());
        values.put(KEY_LOCATION, route.getRouteLocation());
        values.put(KEY_GRADE, route.getRouteGrade());
        values.put(KEY_DATE, route.getRouteDate());
        values.put(KEY_STYLE, route.getRouteStyle());

        db.insert(TABLE_ROUTES, null, values);
        db.close();
    }

    //Create route object from row in db.
    public NewRoute getRoute(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        cursor = db.query(TABLE_ROUTES, new String[]{
                        KEY_NAME, KEY_LOCATION, KEY_GRADE, KEY_DATE, KEY_STYLE},
                KEY_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        NewRoute route = new NewRoute(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
        );

        return route;
    }

    public String getFavouriteStyle()
    {
        String style ="";

        String selectQuery = "SELECT" + KEY_STYLE +"FROM" + TABLE_ROUTES +
                "GROUP BY" + KEY_STYLE +
                "HAVING COUNT(*) = (" +
                "                   SELECT MAX(Cnt)" +
                "                   FROM(" +
                "                         SELECT COUNT(*) as Cnt" +
                "                         FROM " + TABLE_ROUTES +
                "                         GROUP BY " + KEY_STYLE +
                "                        ) tmp" +
                "                    )";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        style = cursor.getString(0);

        return style;
    }

    //Return a list of the routes from the database
    public List<NewRoute> getAllEvents() {
        List<NewRoute> allRoutes = new ArrayList();

        String selectQuery = "SELECT * FROM " + TABLE_ROUTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Loop through database

        if (cursor.moveToFirst()) {
            do {
                NewRoute route = new NewRoute();

                route.setRouteName(cursor.getString(0));
                route.setRouteLocation(cursor.getString(1));
                route.setRouteGrade(cursor.getString(2));
                route.setRouteDate(cursor.getString(3));
                route.setRouteStyle(cursor.getString(4));

                allRoutes.add(route);
            }
            while (cursor.moveToNext());
        }

        return allRoutes;
    }

    //Searching routes

    public List<NewRoute> searchRouteResults(String search) {
        List<NewRoute> searchResults = new ArrayList<>();

        String searchQuery = "SELECT * FROM " + TABLE_ROUTES + " WHERE " + KEY_NAME + " LIKE '%" + search + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NewRoute route = new NewRoute();

                route.setRouteName(cursor.getString(0));
                route.setRouteLocation(cursor.getString(1));
                route.setRouteGrade(cursor.getString(2));
                route.setRouteDate(cursor.getString(3));
                route.setRouteStyle(cursor.getString(4));

                searchResults.add(route);
            }
            while (cursor.moveToNext());
        }

        return searchResults;
    }

//    public int getRoutesCount() {
//        String counterQuery = "SELECT * FROM" + TABLE_ROUTES;
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(counterQuery, null);
//
//        int count = 0;
//        if(null != cursor){
//            if(cursor.getCount() > 0){
//                cursor.moveToFirst();
//                count = cursor.getInt(0);
//            }
//        cursor.close();
//    }
//        return count;
//    }

    //update route
    public int updateRoute(NewRoute route) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, route.getRouteName());
        values.put(KEY_LOCATION, route.getRouteLocation());
        values.put(KEY_GRADE, route.getRouteGrade());
        values.put(KEY_DATE, route.getRouteDate());
        values.put(KEY_STYLE, route.getRouteStyle());

        return db.update(TABLE_ROUTES, values, KEY_NAME + " = ?",
                new String[]{String.valueOf(route.getRouteName())});
    }

    //Delete route
    public boolean deleteRoute(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ROUTES, KEY_NAME + "='" + name + "' ;", null) > 0;
    }

    public long getRouteCount() {
        return DatabaseUtils.queryNumEntries(this.getReadableDatabase(), TABLE_ROUTES);
    }


}