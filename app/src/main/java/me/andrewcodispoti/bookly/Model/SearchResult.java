package me.andrewcodispoti.bookly.Model;

/**
 * Created by andrewcodispoti on 2015-08-15.
 */
public class SearchResult {
    public String title;
    public String magnet;
    public String link;
    public String size;
    public int quality;

    @Override
    public String toString(){
        return title;
    }

}
