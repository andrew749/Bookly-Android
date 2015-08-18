package me.andrewcodispoti.bookly.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import me.andrewcodispoti.bookly.Model.SearchResult;

/**
 * Created by andrewcodispoti on 2015-08-17.
 */
public class BookListAdapter extends BaseAdapter {

    ArrayList<SearchResult> results  = new ArrayList<>();
    Context context = null;

    public BookListAdapter(ArrayList<SearchResult> results, Context context){
        this.results = results;
        this.context = context;
    }
    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView titleView = (TextView) convertView.findViewById(android.R.id.text1);
        titleView.setText(results.get(position).title);
        return convertView;
    }

}
