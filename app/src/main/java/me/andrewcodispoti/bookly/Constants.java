package me.andrewcodispoti.bookly;

/**
 * Created by andrewcodispoti on 2015-08-16.
 */
public class Constants {
    static String url = "http://192.168.1.15:5000";
    public static String searchURL(String query){
        return url + "/search?q="+query;
    }

}
