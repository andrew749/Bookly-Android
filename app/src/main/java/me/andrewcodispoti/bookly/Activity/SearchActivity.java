package me.andrewcodispoti.bookly.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import me.andrewcodispoti.bookly.Adapters.BookListAdapter;
import me.andrewcodispoti.bookly.Constants;
import me.andrewcodispoti.bookly.Model.SearchResult;
import me.andrewcodispoti.bookly.R;

/**
 * Created by andrewcodispoti on 2015-08-15.
 */
public class SearchActivity extends AppCompatActivity implements  ListView.OnItemClickListener {
    ListView resultsList = null;
    BookListAdapter adapter;
    ArrayList<SearchResult> results = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);

        resultsList = (ListView) findViewById(R.id.searchResultsList);
        adapter = new BookListAdapter(results, this);
        resultsList.setAdapter(adapter);
        resultsList.setOnItemClickListener(this);

        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(query);
            new SearchBooksTask().execute(query);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookData", results.get(position));
        startActivity(intent);
    }

    class SearchBooksTask extends AsyncTask<String, Void, ArrayList<SearchResult>>{
        @Override
        protected ArrayList<SearchResult> doInBackground(String... params) {
            URL url = null;
            HttpURLConnection conn = null;
            ArrayList<SearchResult> results = new ArrayList<>();
            try {
                url = new URL(Constants.searchURL(params[0]));
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                String jsonResult = convertStreamToString(is);
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray contentArray = parser.parse(jsonResult).getAsJsonArray();
                for (int i = 0 ;i<contentArray.size(); i++){
                    SearchResult result = gson.fromJson(contentArray.get(i).getAsString(), SearchResult.class);
                    results.add(result);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            }finally{
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<SearchResult> searchResults) {
            super.onPostExecute(searchResults);
            results.clear();
            results.addAll(searchResults);
            adapter.notifyDataSetChanged();
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
