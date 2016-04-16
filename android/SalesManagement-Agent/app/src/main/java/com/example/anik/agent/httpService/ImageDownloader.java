package com.example.anik.agent.httpService;

import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Anik on 07-Aug-15, 007.
 */
public class ImageDownloader {
    private static final Queue<DownloadAble> queue = new LinkedList<>();
    private static final List<String> links = new ArrayList<>();
    private static ImageDownloader instance;
    private static Thread executor;

    private ImageDownloader() {

    }

    private static ImageDownloader getInstance() {
        if (null == instance)
            instance = new ImageDownloader();
        return instance;
    }

    public static ImageDownloader pushJob(String url, ImageView imageView) {
        queue.add(new DownloadAble(url, imageView));
        return getInstance();
    }

    public static ImageDownloader pushJob(String url) {
        return pushJob(url, null);
    }

    public void execute() {
        Runnable downloadTask = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    while (!queue.isEmpty()) {
                        DownloadAble job = (DownloadAble) queue.remove(); // get the peek element, as queue removes from top
                        String url = job.url;
                        ImageView imageView = job.imageView;
                        AsyncTask downloading = new DownloadImage(url, imageView).execute();
                        //Log.v(AppConstant.TAG, "Started...");
                        while (downloading.getStatus() == AsyncTask.Status.RUNNING) {
                            // continue getting data
                            // keep waiting till finish
                        }
                        //Log.v(AppConstant.TAG, "...Done");

                    }
                    if (queue.isEmpty()) {
                        executor = null;
                    }
                }
            }
        };
        if (null == executor) {
            executor = new Thread(downloadTask);
            executor.start();
        }

    }

    static class DownloadAble {
        String url;
        ImageView imageView;

        public DownloadAble(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }
    }

    class DownloadImage extends AsyncTask<Void, Void, Boolean> {
        private String url;
        private ImageView imageView;
        private String fileName = "";
        private boolean fileExists = false;
        private String storage_path = "";

        public DownloadImage(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        private String getFileName(String url) {
            return url.substring(url.lastIndexOf("/") + 1);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            this.fileName = this.getFileName(this.url);
            storage_path = AppStorage.getFilesDirectory() + "/" + fileName;
            if (AppHelper.fileExists(this.storage_path)) {
                this.fileExists = true;
                return false;
            }
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(this.url);
            HttpResponse response;
            try {
                response = client.execute(request);
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
                InputStream inputStream = null;
                inputStream = bufferedEntity.getContent();
                OutputStream outputStream = null;
                outputStream = new FileOutputStream(storage_path);
                byte[] b = new byte[2048];
                int length;
                while ((length = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, length);
                }
                outputStream.close();
                return null == inputStream ? false : true;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isSuccessful) {
            super.onPostExecute(isSuccessful);
            if (isSuccessful) {
                if (null != this.imageView) {
                    AppHelper.setImageToImageView(imageView, this.storage_path);
                }
            } else if (this.fileExists) {
                if (null != this.imageView)
                    AppHelper.setImageToImageView(imageView, this.storage_path);
            }
        }
    }

}
