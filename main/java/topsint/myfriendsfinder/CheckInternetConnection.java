package topsint.myfriendsfinder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AlertDialog;

import topsint.myfriendsfinder.SnackBar;

/**
 * Created by hp on 27/05/17.
 */

public class CheckInternetConnection
{
    private final Context mContext;
    private Activity context;
    public CheckInternetConnection(Context mContext, Activity context){
        this.mContext = mContext;
        this.context =context;
    }
    public boolean isCheckInternetcon(){
        ConnectivityManager connectyvity=(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            if (connectyvity !=null){
                NetworkInfo[] info = connectyvity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
                Handler handler = new Handler(context.getMainLooper());
                handler.post(new Runnable()
                {
                    public void run()
                    {
                        SnackBar.makeLong(context,"No Internet Connection");
                    }
                });
            }

        return false;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
