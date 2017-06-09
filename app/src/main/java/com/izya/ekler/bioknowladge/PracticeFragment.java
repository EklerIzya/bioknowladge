package com.izya.ekler.bioknowladge;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
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
public class PracticeFragment extends Fragment {

    private RecyclerView rv;
    private Activity activity;
    private static String q;

    public PracticeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=getActivity();
    }

    public void setQ(String q) {
        this.q = q;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View res = inflater.inflate(R.layout.fragment_practice, container, false);
        rv = (RecyclerView) res.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return res;

    }

    @Override
    public void onResume() {
        super.onResume();
        new GetTest(q).execute();
    }

    private class GetTest extends AsyncTask<Void,Void,HashMap<String,String>[]> {

        private ProgressDialog pd;
        private String query;

        public GetTest(String query){
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
        protected HashMap<String,String>[] doInBackground(Void... params) {
            try {
                Scanner in = new Scanner(new URL("http://shvedcom.esy.es/bio/getTest.php?title="+ URLEncoder.encode(query)).openConnection().getInputStream());
                JSONArray jsonArray = new JSONArray(in.nextLine());
                HashMap<String,String>[] res = new HashMap[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String,String> temp = new HashMap();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    temp.put("question",jsonObject.getString("question"));
                    temp.put("img",jsonObject.getString("img"));
                    temp.put("answer",jsonObject.getString("answer"));
                    res[i]=temp;
                }

                return res;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String,String>[] strings) {
            super.onPostExecute(strings);
            pd.dismiss();
            Log.i("kek",strings.toString());
            if (strings!=null) {
                rv.setAdapter(new TestsAdapter(activity,strings));
            }
        }


    }


}
