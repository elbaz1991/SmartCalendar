package fr.amu.univ.smartcalendar.utils;


import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by j.Katende on 24/04/2017.
 */

public class SmartCalendarMemoryCache {
    private Map<String, SoftReference<Bitmap>> cache = Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());

    public Bitmap get(String key){
        if(!cache.containsKey(key)){
            return null;
        }
        SoftReference<Bitmap> reference = cache.get(key);
        return reference.get();
    }

    public void put(String key, Bitmap bitMap){
        cache.put(key, new SoftReference<Bitmap>(bitMap));
    }

    public void clear(){
        cache.clear();
    }
}