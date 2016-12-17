
package api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImArtist {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("attributes")
    @Expose
    private Attributes_____ attributes;

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes_____ getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes_____ attributes) {
        this.attributes = attributes;
    }

}
