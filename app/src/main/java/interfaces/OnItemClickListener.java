package interfaces;

import android.view.View;

/**
 * Created by gerardosuarez on 18/12/16.
 */
public interface OnItemClickListener {
    public void onItemClick(View view, int position);

    public void onLongItemClick(View view, int position);
}
