package events;

import java.util.ArrayList;

import api.pojo.Entry;

/**
 * Created by gerardosuarez on 18/12/16.
 */
public class DetailAppEventFromActivity {

    private Entry entry;

    public DetailAppEventFromActivity(Entry entry) {
        this.entry = entry;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }
}
