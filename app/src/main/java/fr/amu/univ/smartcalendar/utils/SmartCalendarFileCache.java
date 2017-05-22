package fr.amu.univ.smartcalendar.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 *
 * Created by j.Katende on 24/04/2017.
 */

public class SmartCalendarFileCache {
    private File cache_directory;

    public SmartCalendarFileCache(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            this.cache_directory = new File(Environment.getExternalStorageDirectory(), "sc_list");
        }else{
            this.cache_directory = context.getCacheDir();
        }

        if(!this.cache_directory.exists()){ this.cache_directory.mkdirs(); }
    }

    public File getFileByPath(String path){
        String fileName = String.valueOf(path.hashCode());
        File file = new File(this.cache_directory, fileName);
        return file;
    }

    public void clear(){
        File[] files = this.cache_directory.listFiles();
        if(files != null){
            for (File file : files){ file.delete(); }
        }
    }
}
