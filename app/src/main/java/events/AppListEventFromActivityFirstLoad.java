package events;

import java.util.ArrayList;

import api.pojo.Entry;

/**
 * Created by gerardosuarez on 18/12/16.
 */
public class AppListEventFromActivityFirstLoad {

    private ArrayList <Entry> entry;

    private String category;

    public AppListEventFromActivityFirstLoad(ArrayList<Entry> entry,String category) {
        this.entry = entry;
        this.category = category;
    }

    public ArrayList<Entry> getEntry() {
        return entry;
    }

    public void setEntry(ArrayList<Entry> entry) {
        this.entry = entry;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
