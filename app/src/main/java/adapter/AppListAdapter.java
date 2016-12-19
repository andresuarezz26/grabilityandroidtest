package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grability.gerardosuarez.grabilityandroidtest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import api.pojo.Entry;


/**
 * Created by gerardosuarez
 * Adapter to manage the App Category List
 */
public class AppListAdapter extends RecyclerView.Adapter <AppListAdapter.ViewHolder>
{
    private ArrayList<Entry> entryArrayList;
    private Context context;

    public AppListAdapter(ArrayList <Entry> categoryList, Context context)
    {
        this.entryArrayList = categoryList;
        this.context = context;
    }

    @Override
    public AppListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from( parent.getContext( ) )
                .inflate( R.layout.list_app_item, parent, false );
        ViewHolder viewHolder = new ViewHolder( itemView );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AppListAdapter.ViewHolder holder, int position)
    {
        //holder.textCategory.setText( entryArrayList.get(position).getImName().getLabel());
        holder.textApp.setText( entryArrayList.get(position).getImName().getLabel());

        //Display image effienctly
        Picasso.with(context)
                .load(entryArrayList.get(position).getImImage().get(2).getLabel())
                .into(holder.image);

        holder.entry = entryArrayList.get(position);
    }

    public void updateReceiptsList(ArrayList<Entry> newlist)
    {
        entryArrayList.clear();
        entryArrayList.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return entryArrayList.size();
    }

    /**
     * The ViewHolder pattern improve the code performance decreasing the number of findViwById executions
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textApp;

        public ImageView image;

        Entry entry;

        public ViewHolder(View itemView)
        {
            super(itemView);

            textApp = (TextView)itemView.findViewById(R.id.text_list_app);
            image = (ImageView) itemView.findViewById(R.id.image_app_list);


        }
    }
}