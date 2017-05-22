package fr.amu.univ.smartcalendar.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.amu.univ.smartcalendar.R;

/**
 *
 * Created by j.Katende on 01/05/2017.
 */

public class SmartCalendarImageLoader {
    private static final int NUMBER_OF_THREADS = 4;
    private static final int CONNEXION_TIME_OUT = 30000;
    private static final int DATA_READ_TIME_OUT = 30000;

    private final int REQUIRED_SIZE = 70;

    private final int stubId = R.drawable.smart_calendar_no_image;
    SmartCalendarMemoryCache memoryCache = new SmartCalendarMemoryCache();
    SmartCalendarFileCache fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;

    public SmartCalendarImageLoader(Context context){
        fileCache = new SmartCalendarFileCache(context);
        executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    public void displayImage(String filePath, ImageView image){
        imageViews.put(image, filePath);
        Bitmap bitmap = memoryCache.get(filePath);
        Log.d("DEBUG", "image de la météo " + filePath);
        if(bitmap == null){
            image.setImageBitmap(bitmap);
        }else{
            queueImage(image, filePath);
            image.setImageResource(stubId);
        }
    }

    private void queueImage(ImageView image, String filePath){
        SmartCalendarImageToLoad photoToLoad = new SmartCalendarImageToLoad(image, filePath);
        executorService.submit(new SmartCalendarImageDataLoader(photoToLoad));
    }

    private Bitmap getBitMap(String filePath){
        File file = fileCache.getFileByPath(filePath);
        Bitmap fileBitMap = decodeFileToBitMap(file);

        //retrieve from memory cache
        if(fileBitMap != null){ return fileBitMap; }

        //otherwise get data from online
        try{
            URL imageUrl = new URL(filePath);
            HttpURLConnection connection = (HttpURLConnection)imageUrl.openConnection();
            connection.setConnectTimeout(CONNEXION_TIME_OUT);
            connection.setReadTimeout(DATA_READ_TIME_OUT);
            InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = new FileOutputStream(file);
            SmartCalendarUtils.copyStream(inputStream, outputStream);
            outputStream.close();
            fileBitMap = decodeFileToBitMap(file);
            return fileBitMap;
        }catch(Exception ignored){
            ignored.printStackTrace();
            return null;
        }
    }

    private Bitmap decodeFileToBitMap(File file){
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, options);

            int tmpWidth = options.outWidth;
            int tmpHeight = options.outHeight;
            int scale = 1;

            while(true){
                if(tmpWidth/2 < REQUIRED_SIZE || tmpHeight/2 < REQUIRED_SIZE){ break; }
                tmpHeight = tmpHeight/2;
                tmpWidth = tmpWidth / 2;
                scale *= 2;
            }

            BitmapFactory.Options newOptions = new BitmapFactory.Options();
            newOptions.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, newOptions);
        }catch (FileNotFoundException ignored){
            return null;
        }
    }

    public boolean isImageViewReusable(SmartCalendarImageToLoad imageToLoad){
        String tag =  imageViews.get(imageToLoad.getImageView());
        if(tag == null || !tag.equals(imageToLoad.getFilePath())){
            return true;
        }
        return false;
    }

    public void clearCache(){
        memoryCache.clear();
        fileCache.clear();
    }

    private class SmartCalendarImageToLoad{
        private String file_path;

        private ImageView image_view;

        public SmartCalendarImageToLoad(ImageView imageView, String filePath){
            image_view = imageView;
            file_path = filePath;
        }

        public String getFilePath(){ return this.file_path; }

        public void setFilePath(String filePath){ this.file_path = filePath; }

        public ImageView getImageView(){ return this.image_view; }

        public void setImageView(ImageView imageView){ this.image_view = imageView; }
    }

    public class SmartCalendarImageDataLoader implements Runnable{
        private SmartCalendarImageToLoad imageToLoad;

        public SmartCalendarImageDataLoader(SmartCalendarImageToLoad image){
            this.imageToLoad = image;
        }

        @Override
        public void run(){
            if(isImageViewReusable(imageToLoad)){ return; }

            Bitmap bitmap = getBitMap(imageToLoad.getFilePath());
            memoryCache.put(imageToLoad.getFilePath(), bitmap);

            if(isImageViewReusable(imageToLoad)){ return; }
            SmartCalendarBitmapDisplay bitmapDisplay = new SmartCalendarBitmapDisplay(bitmap, imageToLoad);
            Activity activity = (Activity)imageToLoad.getImageView().getContext();
            activity.runOnUiThread(bitmapDisplay);
        }
    }


    class SmartCalendarBitmapDisplay implements Runnable {
        Bitmap bitmap;
        SmartCalendarImageToLoad image_to_load;

        public SmartCalendarBitmapDisplay(Bitmap bitMap, SmartCalendarImageToLoad imageToLoad){
            bitmap = bitMap;
            this.image_to_load = imageToLoad;
        }

        @Override
        public void run(){
            if(isImageViewReusable(image_to_load)){ return; }

            if(this.bitmap != null){
                image_to_load.getImageView().setImageBitmap(bitmap);
            }else{
                image_to_load.getImageView().setImageResource(stubId);
            }
        }
    }
}
