package api.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectRoot {

    @SerializedName("feed")
    @Expose
    private Feed feed;

    /**
     *
     * @return
     *     The feed
     */
    public Feed getFeed() {
        return feed;
    }

    /**
     *
     * @param feed
     *     The feed
     */
    public void setFeed(Feed feed) {
        this.feed = feed;
    }

}

