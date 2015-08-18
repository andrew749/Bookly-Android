package me.andrewcodispoti.bookly.Model;

import java.io.Serializable;

/**
 * Created by andrewcodispoti on 2015-08-15.
 */
public class SearchResult implements Serializable {
    public String title;
    public String magnet;
    public String link;
    public String size;
    public String page;
    public int quality;
    public int type;
    @Override
    public String toString(){
        return title;
    }

}
