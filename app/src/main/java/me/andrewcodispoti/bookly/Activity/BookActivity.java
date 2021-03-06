package me.andrewcodispoti.bookly.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.joanzapata.pdfview.PDFView;

import java.io.File;

import me.andrewcodispoti.bookly.R;

/**
 * Created by andrewcodispoti on 2015-08-15.
 * Holds the view to display a book.
 */
public class BookActivity extends Activity {
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklayout);
        PDFView pdfView = (PDFView)findViewById(R.id.pdfView);
        // add code to load pdf from file
        String pathToFile = getIntent().getStringExtra("filePath");
        file = new File(pathToFile);
        pdfView.fromFile(file).
                defaultPage(1).
                showMinimap(true).
                enableSwipe(true).
                load();
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
