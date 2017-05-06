package fr.amu.univ.smartcalendar.model.bean;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elbaz on 06/05/2017.
 */

public class ColorEvent {
    private String name;
    private String code;






    public static List<ColorEvent> getEventColors(Context context)
    {
        XmlPullParserFactory pullParserFactory;
        XmlPullParser parser = null;
        ArrayList<ColorEvent> colors = null;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            parser = pullParserFactory.newPullParser();

            InputStream in_s = context.getApplicationContext().getAssets().open("colorsEvent.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);


            int eventType = parser.getEventType();
            ColorEvent currentProduct = null;

            while (eventType != XmlPullParser.END_DOCUMENT){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        colors = new ArrayList();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();

                        if (name.equals("color")){
                            currentProduct = new ColorEvent();
                        } else if (currentProduct != null){
                            if (name.equals("name")){
                                //Log.e("Atr",parser.getAttributeValue(0));
                                currentProduct.name = parser.nextText();
                            } else if (name.equals("code")){
                                currentProduct.code = parser.nextText();
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("color") && currentProduct != null){
                            colors.add(currentProduct);
                        }
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        

    return colors;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}