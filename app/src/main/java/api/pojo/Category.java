
package api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("attributes")
    @Expose
    private AttributesCategory attributes;

    /**
     * 
     * @return
     *     The attributes
     */
    public AttributesCategory getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(AttributesCategory attributes) {
        this.attributes = attributes;
    }

}
