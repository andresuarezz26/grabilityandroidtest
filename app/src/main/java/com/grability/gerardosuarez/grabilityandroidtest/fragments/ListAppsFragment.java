package com.grability.gerardosuarez.grabilityandroidtest.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grability.gerardosuarez.grabilityandroidtest.R;
import com.squareup.otto.Subscribe;

/**
 * Created by gerardosuarez on 17/12/16.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.AppListAdapter;
import adapter.CategoryAdapter;
import api.pojo.AttributesCategory;
import api.pojo.Entry;
import bus.BusManager;
import events.AppListEventFromActivityFirstLoad;
import events.AppListEventFromFragment;
import listener.RecyclerItemClickListener;
import model.CategoryEntryMapper;

public class ListAppsFragment extends Fragment
{
    private static final String TAG = ListAppsFragment.class.getSimpleName();

    public static final String EXTRA_URL ="url";

    //RecyclerView parametters
    private RecyclerView mRecyclerView;

    private AppListAdapter adapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Entry> entriesByCategory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list_apps,
                container, false);

        BusManager.getInstance().getBus().register(this);

        initComponents(view);

        return view;
    }


    @Override
    public void onResume()
    {
        super.onResume();

        //BusManager.getInstance().getBus().post(new AppListEventFromFragment());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        //BusManager.getInstance().getBus().unregister(this);
    }

    /**
     * Obtain entryes by category for the first time
     */
    @Subscribe
    public void obtainEntriesByCategoryFirstLoad (AppListEventFromActivityFirstLoad event)
    {
        this.entriesByCategory.clear();
        this.entriesByCategory.addAll(event.getEntry());
        adapter.notifyDataSetChanged();
    }

    /**
     * Initialize toolbar,recyclerView, adapters and assign list to adapter
     */
    public void initComponents(View view)
    {
        entriesByCategory = new ArrayList<Entry>();

        //RecyclerView components
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list_apps);

        mLayoutManager = new LinearLayoutManager(view.getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        // mRecyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );

        adapter = new AppListAdapter(entriesByCategory);

        mRecyclerView.setAdapter(adapter);

        //Listener recycler View
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position)
                    {
                        // do whatever
                        Toast.makeText(getActivity(), "Entro al click" + position,Toast.LENGTH_LONG).show();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }


    public ArrayList<Entry> getEntriesByCategory()
    {
        return entriesByCategory;
    }

    public void setEntriesByCategory(ArrayList<Entry> entriesByCategory)
    {
        this.entriesByCategory = entriesByCategory;
    }
}