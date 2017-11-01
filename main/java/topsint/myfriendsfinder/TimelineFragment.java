package topsint.myfriendsfinder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {


    View view;
    ImageView imgOnTimeline;
    public TimelineFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_timeline, container, false);

        imgOnTimeline=(ImageView) view.findViewById(R.id.imageOnTimeline);
        imgOnTimeline.setImageBitmap(HomeActivity.bm);
        // Inflate the layout for this fragment
        return view;
    }

}
