package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 1143847755 on 19/12/2016.
 */

public class Validator
{

    public static Validator validator;

    public static Validator getInstance()
    {
        if(validator == null)
        {
            validator = new Validator();
        }

        return validator;
    }
    public Validator()
    {

    }
    /**
     * Check if internt connection is active
     * @param context
     * @return
     */
    public boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
