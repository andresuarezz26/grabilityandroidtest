package model;

/**
 * Created by gerardosuarez on 17/12/16.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import api.pojo.AttributesCategory;
import api.pojo.Entry;

/**
 * Created by gerardosuarez on 14/12/16.
 */
public class CategoryEntryMapper
{

    private ArrayList <AttributesCategory> categories;

    public CategoryEntryMapper()
    {
    }

    /**
     * Return the category List from an entry List
     * @return
     */
    public ArrayList <AttributesCategory> getCategories(List<Entry> entryList)
    {
        //HashSet to prevent repeated categories
        HashSet<AttributesCategory> listCategory = new HashSet<AttributesCategory>();

        for (int i = 0; i < entryList.size(); i++)
        {
            listCategory.add(entryList.get(i).getCategory().getAttributes());
        }

        categories = new ArrayList<>(listCategory);

        return categories;
    }

    /**
     * Return the list of app per category
     * @param entryList
     * @param categoryId
     * @return
     */
    public ArrayList <Entry> getEntryPerCategory (ArrayList <Entry> entryList, String categoryId)
    {
        ArrayList <Entry> entryPerCategory = new ArrayList <Entry> ();
        for (int i = 0; i < entryList.size() ; i++)
        {
            if(entryList.get(i).getCategory().getAttributes().getLabel().equalsIgnoreCase(categoryId))
            {
                entryPerCategory.add(entryList.get(i));
            }
        }
        return entryPerCategory;
    }

    /**
     * Return a String with the categories list (id + label)
     * @return
     */
    @Override
    public String toString()
    {
        String temp = "";

        for (int i = 0; i < categories.size() ; i++)
        {
            temp += "Id: "+categories.get(i).getImId() + " " +
                    "Label: "+categories.get(i).getLabel() + "\n ";
        }

        return temp;
    }

    //Getter and Setter


    public ArrayList<AttributesCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<AttributesCategory> categories) {
        this.categories = categories;
    }
}
