package id.dgd.travelkita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import id.dgd.travelkita.R;

/**
 * By rednull on 9/14/2017.
 */

public class GridAdapter extends BaseAdapter {

    // VARIABLES
    private Context context;
    private final String[] titles;
    private final int[] images;

    public GridAdapter(Context context, String[] titles, int[] images) {
        this.context = context;
        this.titles = titles;
        this.images = images;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        // IF IT'S NOT RECYCLED INITIALIZE SOME ATTRIBUTES
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_grid, null);
        } else {
            view = (View) convertView;
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.img_feature);
        TextView textView = (TextView) view.findViewById(R.id.tv_feature);
        imageView.setImageResource(images[position]);
        textView.setText(titles[position]);
        return view;
    }
}
