package topsint.myfriendsfinder;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;


public class  SnackBar {
    public static void makeShort(Activity _context, String text)
    {
        try {
            Snackbar snack = Snackbar.make(_context.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT);
            View view = snack.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.CYAN);
            snack.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void makeLong(Activity _context, String text)
    {
        try {
            Snackbar snack = Snackbar.make(_context.findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG);
            View view = snack.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.CYAN);
            snack.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
