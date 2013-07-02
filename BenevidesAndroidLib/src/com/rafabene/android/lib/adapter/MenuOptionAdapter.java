package com.rafabene.android.lib.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rafabene.android.lib.R;
import com.rafabene.android.lib.domain.MenuOption;

public class MenuOptionAdapter extends BaseAdapter {

    private Context context;
    private List<MenuOption> options;
    private LayoutInflater inflater;

    public MenuOptionAdapter(Context ctx, List<MenuOption> options) {
        this.context = ctx;
        this.options = options;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return options.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuOption option = options.get(position);
        LinearLayout linearLayout;
        if (convertView == null) { // if it's not recycled, initialize some attributes
            linearLayout = (LinearLayout) inflater.inflate(R.layout.benevides_menuoptions_item_layout, null);
        } else {
            linearLayout = (LinearLayout) convertView;
        }
        TextView tv = (TextView) linearLayout.findViewById(R.id.txtTextView);
        tv.setText(option.getName());
        ImageView iv = (ImageView) linearLayout.findViewById(R.id.imgImageView);
        iv.setImageResource(option.getIcon());
        return linearLayout;
    }

}
