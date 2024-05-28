package bral.museum;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RijksServiceFactory {
    public RijksService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.rijksmuseum.nl/")
                // Configure Retrofit to use Gson to turn the Json into Objects
                .addConverterFactory(GsonConverterFactory.create())
                // Configure Retrofit to use Rx
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return retrofit.create(RijksService.class);
    }

}