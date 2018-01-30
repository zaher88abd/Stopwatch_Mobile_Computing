package ca.zaher.m.stopwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by zaher on 2018-01-30.
 * I used read this tutorial to create the listview
 * https://www.raywenderlich.com/124438/android-listview-tutorial
 */

public class listview_adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> timers;

    public listview_adapter(Context context, ArrayList<String> timers) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.timers = timers;
    }

    @Override
    public int getCount() {
        return this.timers.size();
    }

    @Override
    public Object getItem(int i) {
        return this.timers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.listview_item_layout, viewGroup, false);
    }
}
