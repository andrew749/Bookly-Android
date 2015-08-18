package me.andrewcodispoti.bookly.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.andrewcodispoti.bookly.Model.SearchResult;
import me.andrewcodispoti.bookly.R;
import me.andrewcodispoti.bookly.Services.DownloadingManagerService;

/**
 * Created by andrewcodispoti on 2015-08-17.
 */
public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
    SearchResult result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_layout);
        TextView titleField = (TextView)findViewById(R.id.detailTitleField);
        TextView detailField = (TextView)findViewById(R.id.detailDescriptionField);
        result = (SearchResult)getIntent().getSerializableExtra("bookData");
        titleField.setText(result.title);
        Button downloadButton = (Button)findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(result != null){
            // launch intent service to download
            Intent intent = new Intent(this, DownloadingManagerService.class);
            intent.putExtra("bookData", result);
            startService(intent);
        }
        finish();
    }
}
