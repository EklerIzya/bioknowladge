package com.izya.ekler.bioknowladge;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class TheoryFragment extends Fragment {

    private TextView text1;
    private TextView text2;
    private ImageView img1;
    private ImageView img2;

    private Activity activity;
    private String q;

    public TheoryFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetData(q).execute();
    }

    public void setQ(String q){
        this.q = q;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_theory, container, false);
        text1 = (TextView) v.findViewById(R.id.text1);
        text2 = (TextView) v.findViewById(R.id.text2);
        img1 = (ImageView) v.findViewById(R.id.imageView1);
        img2 = (ImageView) v.findViewById(R.id.imageView2);
        return v;
    }

    private class GetData extends AsyncTask<Void,Void,HashMap<String,String>> {

        private ProgressDialog pd;
        private String query;

        public GetData(String query){
            this.query = query;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(activity);
            pd.setMessage("Загрузка");
            pd.show();
        }

        @Override
        protected HashMap<String,String> doInBackground(Void... params) {
            try {
                Scanner in = new Scanner(new URL("http://shvedcom.esy.es/bio/getInfo.php?title="+ URLEncoder.encode(query)).openConnection().getInputStream());
                JSONObject jsonObject = new JSONObject(in.nextLine());
                HashMap<String,String> res = new HashMap<>();
                res.put("text1",jsonObject.getString("text1"));
                res.put("text2",jsonObject.getString("text2"));
                res.put("image1_url",jsonObject.getString("image1_url"));
                res.put("image2_url",jsonObject.getString("image2_url"));
                return res;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String,String> strings) {
            super.onPostExecute(strings);
            pd.dismiss();
            Log.i("kek",strings.toString());
            if (strings!=null) {
                text1.setText(strings.get("text1"));
                text2.setText(strings.get("text2"));
                Picasso.with(activity).load(strings.get("image1_url")).into(img1);
                Picasso.with(activity).load(strings.get("image2_url")).into(img2);
            }
        }


    }

}
