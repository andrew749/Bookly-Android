package me.andrewcodispoti.bookly.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
//    Client client;
    public DownloadingManagerService() {
        super("Downloading Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        result = (SearchResult)intent.getSerializableExtra("bookData");
//        new TorrentFileDownloader().execute(result.link);
        if (result.type == 1){
//            getTorrentReady(downloadTorrentFile(result.link));
        }else{
            downloadFile(result.link);
        }
    }

    public File downloadTorrentFile(String stringUrl){
        File file = null;
        try{
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            String name = result.title.replaceAll("\\W+", "_");
            file = new File(getCacheDir().getAbsolutePath()+File.separator + name + ".torrent");
            copyInputStreamToFile(is, file);
        }catch(Exception e){
            e.printStackTrace();
        }
        return file;
    }
    public File downloadFile(String stringUrl){
        File file = null;
        try{
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            String name = result.title.replaceAll("\\W+", "_");
            File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            file = new File(dir,name);
            copyInputStreamToFile(is, file);
        }catch(Exception e){
            e.printStackTrace();
        }
        return file;
    }
    private void copyInputStreamToFile( InputStream in, File file ) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void getTorrentReady(File file){
//        if (file!= null){
//            try {
//                client = new Client(InetAddress.getLocalHost(), SharedTorrent.fromFile(file, getFilesDir()));
//                client.setMaxDownloadRate(0);
//                client.setMaxUploadRate(0);
//                Log.d("BOOKLY", "Started download");
//                downloadThread.start();
//                downloadFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
