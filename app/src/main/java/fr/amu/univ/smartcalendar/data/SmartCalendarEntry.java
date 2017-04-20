package fr.amu.univ.smartcalendar.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 *
 * Created by j.Katende on 19/04/2017.
 */

public class SmartCalendarEntry{
    public static final String CONTENT_AUTHORITY =  "fr.amu.univ.smartcalendar";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String ADDRESS_PATH = "address";

    public static final String ACTIVITY_PATH = "activity";

    public static final class SmartCalendarActivityEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(ACTIVITY_PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ACTIVITY_PATH;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ACTIVITY_PATH;

        public static final String TABLE_NAME = "activity";

        public static final String COLUMN_ACTIVITY_ID = "activity_id";
    }

    public static final class SmartCalendarAddressEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(ADDRESS_PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ADDRESS_PATH;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ADDRESS_PATH;

        public static final String TABLE_NAME = "address";

        public static final String COLUMN_ADDRESS_ID = "address_id";

        //public static final String
        public static final String COLUMN_ADDRESS_LATITUDE = "latitude";

        public static final String COLUMN_ADDRESS_LONGITUDE = "longitude";

        //public static final String

        public static Uri buildAddressUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
