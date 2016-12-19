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
import listener.RecyclerItemClickListener;
import model.CategoryEntryMapper;

public class ListAppsFragment extends Fragment
{
    private static final String TAG = ListAppsFragment.class.getSimpleName();

    public static final String EXTRA_URL ="url";

    private ArrayList<Entry> entriesByCategory;

    //RecyclerView parametters
    private RecyclerView mRecyclerView;

    private AppListAdapter adapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list_apps,
                container, false);

        initComponents(view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            String link = bundle.getString("url");
            setText(link);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        BusManager.getInstance().getBus().register(this);

    }

    @Override
    public void onPause()
    {
        super.onPause();
        BusManager.getInstance().getBus().unregister(this);
    }

    public void setText(String url)
    {
        TextView view = (TextView) getView().findViewById(R.id.detailsText);
        view.setText(url);
    }

    /**
     *
     */
    @Subscribe
    public void obtainEntriesByCategory (ArrayList <Entry> entriesByCategory)
    {
        Log.e(TAG, "obtainEntriesByCategory"+ entriesByCategory.size());
        this.entriesByCategory.clear();
        this.entriesByCategory.addAll(entriesByCategory);
        adapter.notifyDataSetChanged();
    }

    /**
     * Initialize toolbar,recyclerView, adapters and assign list to adapter
     */
    public void initComponents(View view)
    {
        entriesByCategory = new ArrayList<Entry>(  );

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