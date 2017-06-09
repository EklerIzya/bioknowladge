package com.izya.ekler.bioknowladge;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Анна on 09.06.2017.
 */


public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.ViewHolder> {

    private Activity activity;
    private HashMap<String,String>[] data;


    public TestsAdapter(Activity activity,HashMap<String,String>[] data) {
        this.activity = activity;
        this.data = data;
    }


    class mListner implements TextWatcher{

        private ViewHolder holder;
        private String answer;
        private InputMethodManager imm;

        public mListner(ViewHolder holder,String answer){
            this.holder = holder;
            this.answer = answer;
            imm  = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (answer.equals(s.toString()))
            {
                holder.cardView.setBackgroundColor(Color.parseColor("#DDFFDB"));
                holder.editText.setKeyListener(null);
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        }
    }


    @Override
    public TestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_quest, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(data[position].get("question"));
        if (!data[position].get("img").equals(""))
        Picasso.with(activity).load(data[position].get("img")).into(holder.pic);
        holder.editText.addTextChangedListener(new mListner(holder,data[position].get("answer")));

    }

    @Override
    public int getItemCount() {
        return data.length;
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



