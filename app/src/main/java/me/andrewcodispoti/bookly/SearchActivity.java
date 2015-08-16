package me.andrewcodispoti.bookly;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import me.andrewcodispoti.bookly.Model.SearchResult;
import me.andrewcodispoti.bookly.Model.SearchResultList;

/**
 * Created by andrewcodispoti on 2015-08-15.
 */
public class SearchActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("BOOKLY","Got query "+ query);
            new SearchBooksTask().execute(query);
        }

    }
    class SearchBooksTask extends AsyncTask<String, Void, ArrayList<SearchResult>>{
        @Override
        protected ArrayList<SearchResult> doInBackground(String... params) {
            URL url = null;
            HttpURLConnection httpURLConnection = null;
            ArrayList<SearchResult> results = new ArrayList<>();
            try {
                url = new URL(Constants.searchURL(params[0]));
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream is = httpURLConnection.getInputStream();
                String jsonResult = convertStreamToString(is);
                Gson gson = new Gson();
                SearchResultList resultList = gson.fromJson(jsonResult, SearchResultList.class);
                Log.d("Bookly","Parsed");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<SearchResult> searchResults) {
            super.onPostExecute(searchResults);
        }
        public String convertStreamToString(InputStream is) throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder out = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            String line;
            while((line = br.readLine()) != null){
                out.append(line);
            }
            return out.toString();
        }
    }
}
