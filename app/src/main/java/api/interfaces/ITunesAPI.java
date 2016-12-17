package api.interfaces;

import api.pojo.Example;
import api.pojo.Feed;
import api.pojo.ObjectRoot;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by gerardosuarez on 16/12/16.
 */
public interface ITunesAPI
{
    @GET("us/rss/topfreeapplications/limit=20/json")
    Call<ObjectRoot> loadApps();
}
