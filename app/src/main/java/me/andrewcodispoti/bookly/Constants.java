package me.andrewcodispoti.bookly;

/**
 * Created by andrewcodispoti on 2015-08-16.
 */
public class Constants {
    static String url = "127.0.0.1:5000";
    public static String searchURL(String query){
        return url + "/search?q="+query;
    }

}
