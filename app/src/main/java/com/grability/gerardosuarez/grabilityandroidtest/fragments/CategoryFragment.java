package com.grability.gerardosuarez.grabilityandroidtest.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.grability.gerardosuarez.grabilityandroidtest.R;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import adapter.CategoryAdapter;
import api.pojo.AttributesCategory;
import bus.BusManager;
import listener.RecyclerItemClickListener;
import model.CategoryEntryMapper;

/**
 * Created by gerardosuarez on 17/12/16.
 */
public class CategoryFragment extends Fragment
    {

    private OnItemSelectedListener listener;

    //Object that converts the app list to category List
    private CategoryEntryMapper categoryMapper;

    //RecyclerView parametters
    private RecyclerView mRecyclerView;

    private CategoryAdapter adapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<AttributesCategory> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_category,
                container, false);

        Button button = (Button) view.findViewById(R.id.updateButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDetail("fake");
            }
        });

        initComponents(view);

        BusManager.getInstance().getBus().register(this);

        return view;
    }

    /**
     * Get the cateories from the activity
     * @param categoryList
     */
    @Subscribe
    public void getCategories(ArrayList <AttributesCategory> categoryList)
    {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
        adapter.notifyDataSetChanged();
    }

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
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Toast.makeText(getActivity(), "Entro al click" + position,Toast.LENGTH_LONG).show();
                        updateDetail(position+"");
                    }

                    @Override public void onLongItemClick(View view, int position) {
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

    // triggers update of the details fragment
    public void updateDetail(String uri)
    {
        // create fake data
        String newTime = String.valueOf(System.currentTimeMillis());
        // send data to activity
        listener.onCategorySelectedListener(newTime);
    }
}