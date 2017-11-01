package topsint.myfriendsfinder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import topsint.myfriendsfinder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View view;

    CircleImageView  circleImageView;
    TextView username;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_profile, container, false);
        username=(TextView) view.findViewById(R.id.Name_for_profile);
        username.setText(StoreData.username);
        circleImageView=(CircleImageView)view.findViewById(R.id.image_for_profile_fragment);
        circleImageView.setImageBitmap(HomeActivity.bm);

        return view;
    }

}
