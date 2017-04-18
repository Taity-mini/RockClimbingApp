package andrewtait1504693.rockclimbingapp;

/**
 * Created by Andrew Tait (1504693) on 12/04/2017.
 * New routes class used to insert new routes in the database
 */

public class NewRoute {

    //Local  route variables

    String routeName, routeLocation, routeGrade, routeDate, routeStyle;

    //Blank constructor

    public NewRoute(){}

    public NewRoute(String routeName, String routeLocation, String routeGrade, String routeDate, String routeStyle) {
        this.routeName = routeName;
        this.routeLocation = routeLocation;
        this.routeGrade = routeGrade;
        this.routeDate = routeDate;
        this.routeStyle = routeStyle;
    }

    //Getters

    public String getRouteName() {
        return routeName;
    }

    public String getRouteLocation() {
        return routeLocation;
    }

    public String getRouteGrade() {
        return routeGrade;
    }

    public String getRouteDate() {
        return routeDate;
    }

    public String getRouteStyle() {
        return routeStyle;
    }

    //Setters

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setRouteLocation(String routeLocation) {
        this.routeLocation = routeLocation;
    }

    public void setRouteGrade(String routeGrade) {
        this.routeGrade = routeGrade;
    }

    public void setRouteDate(String routeDate) {
        this.routeDate = routeDate;
    }

    public void setRouteStyle(String routeStyle) {
        this.routeStyle = routeStyle;
    }
}
