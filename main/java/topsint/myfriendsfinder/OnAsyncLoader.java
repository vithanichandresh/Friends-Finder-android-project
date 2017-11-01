package topsint.myfriendsfinder;

import org.json.JSONException;

/**
 * Created by hp on 05/04/17.
 */
public interface OnAsyncLoader {

    void onResult(String result) throws JSONException;
}
