package com.grability.gerardosuarez.grabilityandroidtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import adapter.ActivityMainAdapter;
import api.ApiManager;
import api.pojo.AttributesCategory;
import api.pojo.ObjectRoot;
import model.CategoryEntryMapper;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<ObjectRoot> {

    private final String BASE_URL = "https://itunes.apple.com/";

    private final String TAG = MainActivity.class.getSimpleName();

    //Object that converts the app list to category List
    private CategoryEntryMapper categoryMapper;

    //RecyclerView parametters

    private RecyclerView mRecyclerView;

    private ActivityMainAdapter adapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<AttributesCategory> categoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

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

        //RecyclerView components
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        categoryList = new ArrayList<AttributesCategory>(  );

        // mRecyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );

        adapter = new ActivityMainAdapter(categoryList);

        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onResponse(Response<ObjectRoot> response, Retrofit retrofit)
    {
        categoryList.clear();
        categoryList.addAll(categoryMapper.getCategories(response.body().getFeed().getEntry()));
        adapter.notifyDataSetChanged();

        //Log.e(TAG, "size: "+categoryList.size());
        //Log.e(TAG,categoryMapper.toString());
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG,"failure");
    }
}
