
package api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Link_ {

    @SerializedName("attributes")
    @Expose
    private Attributes________ attributes;

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes________ getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes________ attributes) {
        this.attributes = attributes;
    }

}
