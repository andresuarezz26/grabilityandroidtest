package events;

import java.util.ArrayList;

import api.pojo.Entry;

/**
 * Created by gerardosuarez on 18/12/16.
 */
public class AppListEventFromActivityFirstLoad {

    private ArrayList <Entry> entry;

    public AppListEventFromActivityFirstLoad(ArrayList<Entry> entry) {
        this.entry = entry;
    }

    public ArrayList<Entry> getEntry() {
        return entry;
    }

    public void setEntry(ArrayList<Entry> entry) {
        this.entry = entry;
    }
}
