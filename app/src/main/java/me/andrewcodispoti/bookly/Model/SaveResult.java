package me.andrewcodispoti.bookly.Model;

import java.io.File;

/**
 * Created by andrewcodispoti on 2015-08-29.
 */
public class SaveResult {
    File file;
    String name;
    public SaveResult (String name, File file){
        this.file = file;
        this.name = name;
    }
}
