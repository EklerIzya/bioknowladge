package com.izya.ekler.bioknowladge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity = this;
        listView=(ListView)findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, KnowladgeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                bundle.putString("title", ((TextView) view).getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetTitles().execute();
    }

    private class GetTitles extends AsyncTask<Void,Void,String[]>{
        private ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(activity);
            pd.setMessage("Загрузка");
            pd.show();
        }

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Scanner in = new Scanner(new URL("http://shvedcom.esy.es/bio/getTitles.php").openConnection().getInputStream());
                JSONArray jsonArray = new JSONArray(in.nextLine());
                String[] strings = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    strings[i]=jsonArray.getString(i);
                }
                return strings;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            pd.dismiss();
            if (strings!=null) {
                listView.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, strings));
            }
        }
    }


}
