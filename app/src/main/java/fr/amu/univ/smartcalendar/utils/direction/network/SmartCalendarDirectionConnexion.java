package fr.amu.univ.smartcalendar.utils.direction.network;

import fr.amu.univ.smartcalendar.utils.direction.config.SmartCalendarGoogleDirectionConfiguration;
import fr.amu.univ.smartcalendar.utils.direction.constants.SmartCalendarDirectionUrl;
import fr.amu.univ.smartcalendar.utils.direction.network.api.SmartCalendarDirectionService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by j.Katende on 22/04/2017.
 */

public class SmartCalendarDirectionConnexion {
    private static SmartCalendarDirectionConnexion instance;

    private SmartCalendarDirectionService service;

    public static SmartCalendarDirectionConnexion getInstance(){
        if(instance == null){
            instance = new SmartCalendarDirectionConnexion();
        }
        return instance;
    }

    public SmartCalendarDirectionService createService(){
        if(service == null){
            Retrofit retrofit = new Retrofit.Builder().client(getClient())
                    .baseUrl(SmartCalendarDirectionUrl.MAPS_API_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(SmartCalendarDirectionService.class);
        }
        return service;
    }

    private OkHttpClient getClient(){
        OkHttpClient client = SmartCalendarGoogleDirectionConfiguration.getInstance().getCustomClient();
        if(client != null){ return client; }
        return createDefaultClient();
    }

    private OkHttpClient createDefaultClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(SmartCalendarGoogleDirectionConfiguration.getInstance().isLogEnabled()){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }
}
