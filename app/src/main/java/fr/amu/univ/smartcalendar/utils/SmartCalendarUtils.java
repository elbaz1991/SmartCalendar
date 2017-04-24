package fr.amu.univ.smartcalendar.utils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * Created by j.Katende on 24/04/2017.
 */

public class SmartCalendarUtils {
    private static final int BUFFER_SIZE = 1024;

    public static void copyStream(InputStream inputStream, OutputStream out){
        try{
            byte[] bytes = new byte[BUFFER_SIZE];
            int count = inputStream.read(bytes, 0, BUFFER_SIZE);
            while(count != -1){
                out.write(bytes, 0, count);
                count = inputStream.read(bytes, 0, BUFFER_SIZE);
            }
        }catch(Exception ignored){}
    }
}
