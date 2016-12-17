package api;

import api.interfaces.ITunesAPI;
import api.pojo.ObjectRoot;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gerardosuarez on 16/12/16.
 */
public class ApiManager {

    private final String BASE_URL = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";

    private final String LOG = ApiManager.class.getSimpleName();

    private static ApiManager apiManager;

    private static ITunesAPI iTunesAPI;

    private static Retrofit retrofit;

    /**
     * Singleton constructor
     * @return
     */
    public static ApiManager getInstance(){

        if(apiManager == null)
        {
            apiManager = new ApiManager();
        }

        return apiManager;
    }

    /**
     * Inicialize retrofit and iTunesAPI classes
     */
    private ApiManager ()
    {
         retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iTunesAPI = retrofit.create(ITunesAPI.class);
    }

    /**
     * Get the load Entry from the API
     * @param callback
     */
    public void getLoadEntry (Callback<ObjectRoot> callback){

        Call<ObjectRoot> call = iTunesAPI.loadApps();

        call.enqueue(callback);
    }




}
