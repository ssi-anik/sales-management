package com.example.anik.shop.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.anik.shop.cache.MemoryCache;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Anik on 05-Aug-15, 005.
 */
public class AppHelper {

    private static MemoryCache memoryCache = new MemoryCache();

    public static boolean isNetworkAvailable() {
        Context context = AppStorage.getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void toggleSoftKeyboard(EditText editText) {
        Context context = AppStorage.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static void setImageToImageView(ImageView imageView, File file) {
        setImageToImageView(imageView, file.getPath());
        return;
    }

    public static void setImageToImageView(ImageView imageView, String filePath) {
        if (memoryCache.get(filePath) == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            File file = new File(filePath);
            int fileSize = (int) file.length() / 1024;
            if (fileSize < 30) // if the file size is less than 30KB
                options.inSampleSize = 1;
            else if (fileSize <= 100) // if the file size is less than 100KB
                options.inSampleSize = 5;
            else // otherwise
                options.inSampleSize = 10;
            //Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
            memoryCache.put(filePath, bitmap);
        }
        imageView.setImageBitmap(memoryCache.get(filePath));

        return;
    }

    public static boolean fileExists(String fileName) {
        return new File(fileName).exists();
    }

    public static String randomUniqueNumber() {
        return UUID.randomUUID().toString();
    }

    public static String formatDate(String date) {
        try {
            return new SimpleDateFormat("E MMM dd, yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid date";
        }
    }

    public static boolean isCameraAvailable() {
        if (AppStorage.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    public static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    public static File getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), AppConstant.APP_STORAGE_FOLDER);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    public static String getFileName(String uri) {
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    public static void sendBroadcast(String broadcastIntentFilter) {
        Intent intent = new Intent();
        intent.setAction(broadcastIntentFilter);
        AppStorage.getContext().sendBroadcast(intent);
    }

    private String now() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String taxDeCalculator(String price, String quantity, String totalPrice) {
        double p = Double.parseDouble(price);
        double q = Double.parseDouble(quantity);
        double t = Double.parseDouble(totalPrice);

        double tax = 0;

        tax = ((t / ( p * q)) * 100) - 100;

        return String.format("%.2f", tax);
    }
}
