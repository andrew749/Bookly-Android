package me.andrewcodispoti.bookly;

import android.app.Activity;
import android.os.Bundle;

import com.joanzapata.pdfview.PDFView;

/**
 * Created by andrewcodispoti on 2015-08-15.
 */
public class BookActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklayout);
        PDFView pdfView = (PDFView)findViewById(R.id.pdfView);
        // add code to load pdf from file
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
