package com.grability.gerardosuarez.grabilityandroidtest.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grability.gerardosuarez.grabilityandroidtest.R;
import com.squareup.otto.Subscribe;

/**
 * Created by gerardosuarez on 17/12/16.
 */

import java.util.ArrayList;

import adapter.AppListAdapter;
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

    private OnItemSelectedListener listener;

    //RecyclerView parametters
    private  RecyclerView mRecyclerView;

    private  AppListAdapter adapter;

    private  RecyclerView.LayoutManager mLayoutManager;

    private  ArrayList<Entry> entriesByCategory;

    //Object that allow retrieve the apps for a selected category
    private  CategoryEntryMapper categoryMapper;

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list_apps,
                container, false);
        this.view = view;

        initComponents(view);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        BusManager.getInstance().getBus().register(this);
        BusManager.getInstance().getBus().post(new AppListEventFromFragment());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        BusManager.getInstance().getBus().unregister(this);
    }

    /**
     * Obtain entryes by category for the first time
     */
    @Subscribe
    public void obtainEntriesByCategoryFirstLoad (AppListEventFromActivityFirstLoad event)
    {
        this.entriesByCategory.clear();
        this.entriesByCategory.addAll(categoryMapper.getEntryPerCategory(event.getEntry(),event.getCategory()));
        adapter.notifyDataSetChanged();
    }

    /**
     * Initialize toolbar,recyclerView, adapters and assign list to adapter
     */
    public void initComponents(View view)
    {
        categoryMapper = new CategoryEntryMapper();

        entriesByCategory = new ArrayList<Entry>();

        //RecyclerView components
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list_apps);

        mLayoutManager = new LinearLayoutManager(view.getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new AppListAdapter(entriesByCategory,getActivity());

        mRecyclerView.setAdapter(adapter);

        //Listener recycler View
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position)
                    {
                        // do whatever
                        sendEntry(entriesByCategory.get(position));
                    }

                    @Override public void onLongItemClick(View view, int position)
                    {
                        // do whatever
                    }
                })
        );
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener)
        {
            listener = (OnItemSelectedListener) context;
        } else
        {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    /**
     * Interface for capturing events
     */
    public interface OnItemSelectedListener
    {
        void onAppListSelectedListener(Entry entry);
    }

    /**
     * Send the current Entry to Main Activity
     * @param entry
     */
    public void sendEntry(Entry entry)
    {
        listener.onAppListSelectedListener(entry);
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