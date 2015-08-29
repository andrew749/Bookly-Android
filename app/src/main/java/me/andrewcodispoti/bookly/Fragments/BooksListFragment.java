package me.andrewcodispoti.bookly.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import me.andrewcodispoti.bookly.Adapters.BookListAdapter;
import me.andrewcodispoti.bookly.R;

/**
 * Created by andrewcodispoti on 2015-08-29.
 */
public class BooksListFragment extends Fragment implements ListView.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> books = new ArrayList<>();
    TextView warningLabel;
    Context c;
    FileSelection fileSelection;
    public BooksListFragment (Context c){
        this.c = c;
    }


    public void setData(ArrayList<String> books, FileSelection fileSelection) {
        this.books.clear();
        this.books.addAll(books);
        if (books.size() > 0){
            if(warningLabel !=  null){
                warningLabel.setVisibility(View.INVISIBLE);
            }
        }
        if(adapter == null){
            adapter = new ArrayAdapter(c, android.R.layout.simple_list_item_1, books);
        }
        adapter.notifyDataSetChanged();
        this.fileSelection = fileSelection;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bookly_main, container, false);
        if (adapter == null)
            adapter = new ArrayAdapter(c, android.R.layout.simple_list_item_1, books);
        if (books.size() == 0){
            warningLabel = (TextView)rootView.findViewById(R.id.warningLabel);
            warningLabel.setVisibility(View.VISIBLE);
        }
        lv = (ListView) rootView.findViewById(R.id.booksList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fileSelection.didSelectFileAtIndex(position );
    }

    public interface FileSelection{
        void didSelectFileAtIndex(int index);
    }
}
