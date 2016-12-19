package com.grability.gerardosuarez.grabilityandroidtest.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grability.gerardosuarez.grabilityandroidtest.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import adapter.CategoryAdapter;
import api.pojo.AttributesCategory;
import bus.BusManager;
import events.CategoryListEventFromActivity;
import events.CategoryListEventFromFragment;
import listener.RecyclerItemClickListener;
import model.CategoryEntryMapper;

/**
 * Created by gerardosuarez on 17/12/16.
 */
public class CategoryFragment extends Fragment
{
    private final static String TAG = CategoryFragment.class.getSimpleName();

    private OnItemSelectedListener listener;

    //Object that converts the app list to category List
    private CategoryEntryMapper categoryMapper;

    //RecyclerView parametters
    private RecyclerView mRecyclerView;

    private CategoryAdapter adapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<AttributesCategory> categoryList;

    /**
     * Initialize the components
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_category,
                container, false);

        initComponents(view);

        return view;
    }

    /**
     * Get the cateories from the activity for the first time
     * @param categoryList
     */
    @Subscribe
    public void getCategories(ArrayList <AttributesCategory> categoryList)
    {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
        adapter.notifyDataSetChanged();
    }

    /**
     * Reload Category List from activity
     * @param event
     */
    @Subscribe
    public void reloadCategories (CategoryListEventFromActivity event)
    {
        this.categoryList.clear();
        this.categoryList.addAll(event.getCategories());
        adapter.notifyDataSetChanged();
    }

    /**
     * Interface for capturing events
     */
    public interface OnItemSelectedListener
    {
        void onCategorySelectedListener(String link);
    }

    /**
     * Initialize toolbar,recyclerView, adapters and assign list to adapter
     */
    public void initComponents(View view)
    {
         categoryMapper = new CategoryEntryMapper();

         categoryList = new ArrayList<AttributesCategory>(  );

         //RecyclerView components
         mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_main);

         mLayoutManager = new LinearLayoutManager(view.getContext());

         mRecyclerView.setLayoutManager(mLayoutManager);

         // mRecyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );

         adapter = new CategoryAdapter(categoryList);

         mRecyclerView.setAdapter(adapter);

         //Listener recycler View
         mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position)
                    {
                        // do whatever
                        Toast.makeText(getActivity(), "Entro al click" + position,Toast.LENGTH_LONG).show();
                        sendPosition(position+"");
                    }

                    @Override public void onLongItemClick(View view, int position)
                    {
                        // do whatever
                    }
                })
         );
    }

    /**
     * Reload categoryList on onResume
     */
    @Override
    public void onResume()
    {
        super.onResume();
        BusManager.getInstance().getBus().register(this);

        if(categoryList.size() == 0)
        {
           BusManager.getInstance().getBus().post(new CategoryListEventFromFragment());
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        BusManager.getInstance().getBus().unregister(this);
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
     * Send the position to the MainActivity
     * @param position
     */
    public void sendPosition(String position)
    {
        listener.onCategorySelectedListener(position);
    }
}
