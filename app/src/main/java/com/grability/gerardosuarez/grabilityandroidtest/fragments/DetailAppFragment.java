package com.grability.gerardosuarez.grabilityandroidtest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.grability.gerardosuarez.grabilityandroidtest.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import adapter.AppListAdapter;
import api.pojo.Entry;
import bus.BusManager;
import events.AppListEventFromActivityFirstLoad;
import events.AppListEventFromFragment;
import events.DetailAppEventFromActivity;
import events.DetailAppEventFromFragment;
import listener.RecyclerItemClickListener;
import model.CategoryEntryMapper;

/**
 * Created by gerardosuarez on 17/12/16.
 */

public class DetailAppFragment extends Fragment
{
    private static final String TAG = DetailAppFragment.class.getSimpleName();

    public static final String EXTRA_URL ="url";

    //UI Components
    private TextView name;
    private TextView title;
    private TextView amount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_detail,
                container, false);

        initComponents(view);

        return view;
    }


    /**
     * Set the text based on the activity response
     * @param event
     */
    @Subscribe
    public void obtainEntryFromActivity (DetailAppEventFromActivity event)
    {
        name.setText(event.getEntry().getImName().getLabel());
        title.setText(event.getEntry().getTitle().getLabel());
        amount.setText(event.getEntry().getImPrice().getAttributes().getAmount());
    }

    /**
     * Publish event to alert the activity that this Fragment is ready to receive data
     */
    @Override
    public void onResume()
    {
        super.onResume();
        BusManager.getInstance().getBus().register(this);
        BusManager.getInstance().getBus().post(new DetailAppEventFromFragment());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        BusManager.getInstance().getBus().unregister(this);
    }

    /**
     * Initialize toolbar,recyclerView, adapters and assign list to adapter
     */
    public void initComponents(View view)
    {
        name = (TextView) view.findViewById(R.id.text_app_name);
        title = (TextView) view.findViewById(R.id.text_app_title);
        amount = (TextView) view.findViewById(R.id.text_app_amount);

    }
}