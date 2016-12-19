package events;

import java.util.ArrayList;

import api.pojo.AttributesCategory;

/**
 * Created by gerardosuarez on 18/12/16.
 */
public class CategoryListEventFromActivity {

    private ArrayList <AttributesCategory> categories;

    public CategoryListEventFromActivity(ArrayList<AttributesCategory> categories) {
        this.categories = categories;
    }

    public ArrayList<AttributesCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<AttributesCategory> categories) {
        this.categories = categories;
    }
}
