package com.grability.gerardosuarez.grabilityandroidtest;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.grability.gerardosuarez.grabilityandroidtest.fragments.CategoryFragment;
import com.grability.gerardosuarez.grabilityandroidtest.fragments.DetailAppFragment;
import com.grability.gerardosuarez.grabilityandroidtest.fragments.ListAppsFragment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import api.ApiManager;
import api.pojo.AttributesCategory;
import api.pojo.Entry;
import api.pojo.ObjectRoot;
import bus.BusManager;
import events.AppListEventFromActivityFirstLoad;
import events.AppListEventFromFragment;
import events.CategoryListEventFromActivity;
import events.CategoryListEventFromFragment;
import events.DetailAppEventFromActivity;
import events.DetailAppEventFromFragment;
import model.CategoryEntryMapper;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity
        implements Callback<ObjectRoot>, CategoryFragment.OnItemSelectedListener, ListAppsFragment.OnItemSelectedListener {

    private final String TAG = MainActivity.class.getSimpleName();

    //Object that converts the app list to category List
    private CategoryEntryMapper categoryMapper;

    //List of entries per category
    private ArrayList<Entry> entries;

    private ArrayList<AttributesCategory> categoryList;

    private static int position;

    private static Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        //Register the activity in the event bus
        BusManager.getInstance().getBus().register(this);

        if (getResources().getBoolean(R.bool.twoPaneMode))
        {
            // all good, we use the fragments defined in the layout
            return;
        }

        if (savedInstanceState != null)
        {
            // cleanup any existing fragments in case we are in detailed mode
            getFragmentManager().executePendingTransactions();

            Fragment fragmentById = getFragmentManager().
                    findFragmentById(R.id.fragment_container);

            if (fragmentById!=null)
            {
                getFragmentManager().beginTransaction()
                        .remove(fragmentById).commit();
            }
        }

        CategoryFragment listFragment = new CategoryFragment();

        FrameLayout viewById = (FrameLayout) findViewById(R.id.fragment_container);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, listFragment).commit();

        ApiManager.getInstance().getLoadEntry(this);
    }

    /**
     * Initialize toolbar,recyclerView, adapters and assign list to adapter
     */
    public void initComponents()
    {

        //Initialize Toolbar
        Toolbar toolbar = (Toolbar)findViewById( R.id.toolbar );

        setSupportActionBar( toolbar );

        categoryMapper = new CategoryEntryMapper();

        categoryList = new ArrayList<AttributesCategory>(  );

        entries = new ArrayList<Entry>();
    }

    /**
     * Callback with a successfull response from the API
     * @param response
     * @param retrofit
     */
    @Override
    public void onResponse(Response<ObjectRoot> response, Retrofit retrofit)
    {
        entries.clear();
        categoryList.clear();

        entries.addAll((ArrayList <Entry>)response.body().getFeed().getEntry());
        categoryList.addAll(categoryMapper.getCategories(entries));

        BusManager.getInstance().getBus()
                .post(categoryList);

    }

    /**
     * Listen the fragment to send the category list for reload it
     * @param event
     */
    @Subscribe
    public void reloadCategoryFragment (CategoryListEventFromFragment event)
    {
        BusManager.getInstance().getBus()
                .post(new CategoryListEventFromActivity(categoryList));
    }

    /**
     * Send entries to List Apps Fragment
     * @param event
     */
    @Subscribe
    public void loadLisAppFragment (AppListEventFromFragment event)
    {
        //Send the entry categories
        BusManager.getInstance().getBus().post(new AppListEventFromActivityFirstLoad(entries,
                categoryList.get(position).getLabel()));
    }

    /**
     * Send entry to Fragment Detail
     * @param event
     */
    @Subscribe
    public void sendEntryFragmentDetail (DetailAppEventFromFragment event)
    {
        BusManager.getInstance().getBus().post(new DetailAppEventFromActivity (entry) );

    }

    /**
     * Callback with the a failure response from the API
     * @param t
     */
    @Override
    public void onFailure(Throwable t)
    {
        Log.e(TAG,"failure");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onCategorySelectedListener(String position)
    {
        //Validate if the
        if (getResources().getBoolean(R.bool.twoPaneMode))
        {
            ListAppsFragment fragment = (ListAppsFragment) getFragmentManager()
                    .findFragmentById(R.id.fragment_container);

            fragment.setEntriesByCategory(entries);
        } else
        {
            // Create fragment and give it an argument for the selected article
            ListAppsFragment newFragment = new ListAppsFragment();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
     }
        Log.e(TAG, "size entries"+ entries.size());

        this.position = Integer.valueOf(position);


    }


    @Override
    public void onCategorySelectedListener(Entry entry)
    {
        // Create fragment and give it an argument for the selected article
        DetailAppFragment newFragment = new DetailAppFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        this.entry = entry;
    }
}
