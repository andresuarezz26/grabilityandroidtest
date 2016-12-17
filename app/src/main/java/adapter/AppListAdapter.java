package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grability.gerardosuarez.grabilityandroidtest.R;

import java.util.ArrayList;

import api.pojo.AttributesCategory;
import api.pojo.Entry;


/**
 * Created by gerardosuarez
 * Adapter to manage the App Category List
 */
public class AppListAdapter extends RecyclerView.Adapter <AppListAdapter.ViewHolder>
{
    private ArrayList<Entry> categoryList;

    public AppListAdapter(ArrayList <Entry> categoryList)
    {
        this.categoryList = categoryList;
    }

    @Override
    public AppListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from( parent.getContext( ) )
                .inflate( R.layout.category_item, parent, false );
        ViewHolder viewHolder = new ViewHolder( itemView );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AppListAdapter.ViewHolder holder, int position)
    {
        holder.textCategory.setText( categoryList.get(position).getCategory().getAttributes().getLabel() );

    }

    @Override
    public int getItemCount()
    {
        return categoryList.size();
    }

    /**
     * The ViewHolder pattern improve the code performance decreasing the number of findViwById executions
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textCategory;

        public ViewHolder(View itemView)
        {
            super(itemView);

            textCategory = (TextView)itemView.findViewById(R.id.text_category_name);

        }
    }
}