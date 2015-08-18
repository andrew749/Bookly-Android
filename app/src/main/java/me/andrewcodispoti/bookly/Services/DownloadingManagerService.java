package me.andrewcodispoti.bookly.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import me.andrewcodispoti.bookly.Model.SearchResult;

/**
 * Created by andrewcodispoti on 2015-08-17.
 */
public class DownloadingManagerService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    SearchResult result = null;
    Client client;
    public DownloadingManagerService() {
        super("Downloading Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        result = (SearchResult)intent.getSerializableExtra("bookData");
//        new TorrentFileDownloader().execute(result.link);
        getTorrentReady(downloadTorrentFile(result.link));
        downloadFile();
    }

    Thread downloadThread = new Thread(){
        @Override
        public void run() {
           downloadFile();
        }
    };
    public void downloadFile(){
        client.download();
        client.waitForCompletion();
        Log.d("BOOKLY", "completed download");
    }
    public File downloadTorrentFile(String stringUrl){
        File file = null;
        try{
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            String name = result.title.replaceAll("\\W+", "_");
            file = new File(getCacheDir().getAbsolutePath()+File.separator+name+".torrent");
            FileOutputStream os = new FileOutputStream(file);
            IOUtils.copy(is,os);
        }catch(Exception e){
            e.printStackTrace();
        }
        return file;
    }
    public void getTorrentReady(File file){
        if (file!= null){
            try {
                client = new Client(InetAddress.getLocalHost(), SharedTorrent.fromFile(file, getFilesDir()));
                client.setMaxDownloadRate(0);
                client.setMaxUploadRate(0);
                Log.d("BOOKLY", "Started download");
//                downloadThread.start();
                downloadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class TorrentFileDownloader extends AsyncTask<String, Void, File>{
        @Override
        protected File doInBackground(String... params) {
            return downloadTorrentFile(params[0]);
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            getTorrentReady(file);
        }
    }

}
