package topsint.myfriendsfinder;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hitesh on 17 May. 2016.
 */
public class ImgAdapter extends BaseAdapter {

    Context context;
    ArrayList<bean> arrayList;

    public ImgAdapter(Context context, ArrayList<bean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.fragment_profile, null);

        TextView username = (TextView) convertView.findViewById(R.id.Name_for_profile);
        ImageView profilepic = (ImageView) convertView.findViewById(R.id.image_for_profile_fragment);

        username.setText("ID : " + arrayList.get(position).getId());
        profilepic.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(arrayList.get(position).getPath(), 0), 0, Base64.decode(arrayList.get(position).getPath(), 0).length));
//        Picasso.with(context).load(arrayList.get(position).getPath()).into(imageView);

        return convertView;
    }
}
