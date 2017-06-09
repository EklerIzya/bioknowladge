package com.izya.ekler.bioknowladge;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Анна on 09.06.2017.
 */


public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.ViewHolder> {

    private Activity activity;
    private List<HashMap<String,String>> data;


    public TestsAdapter(Activity activity,List<HashMap<String,String>> data) {
        this.activity = activity;
        this.data = data;
    }




    @Override
    public TestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_quest, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(data.get(position).get("q"));
        Picasso.with(activity).load(data.get(position).get("img")).into(holder.pic);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView pic;
        CardView cardView;
        EditText editText;

        ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.textView);
            editText = (EditText) v.findViewById(R.id.editText);
            pic = (ImageView) v.findViewById(R.id.imageView);
            cardView = (CardView) v.findViewById(R.id.cw);
        }
    }


}



