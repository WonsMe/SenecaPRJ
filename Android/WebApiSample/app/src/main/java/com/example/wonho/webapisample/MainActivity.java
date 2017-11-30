package com.example.wonho.webapisample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wonho.webapisample.Models.Title;
import com.example.wonho.webapisample.Utils.TitleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";
    static final String API_URL = "http://senecaprj.azurewebsites.net/api/titles";

    TextView textView;
    Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // URL
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(API_URL);

        // Button
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTitleTask(API_URL);
            }
        });

        // ListView
        listView = (ListView)findViewById(R.id.listView);
    }

    private void runTitleTask(String inputUrl) {
        try {
            URL url = new URL(inputUrl);
            new TitleTask().execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readStream(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        try {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private ArrayList<Title> parseTitleData(String data) {
        ArrayList<Title> titles = new ArrayList<Title>();

        try {
            /*
                [
                    {
                        "Id":1,
                        "Name":"Emperor"
                    },
                    {
                        "Id":2,
                        "Name":"Empress"
                    },
                    {
                        "Id":3,
                        "Name":"King"
                    },
                    { ... }
                ]
             */

            JSONArray array = new JSONArray(data);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                Title title = new Title();
                title.setId(object.getInt("Id"));
                title.setName(object.getString("Name"));
                titles.add(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return titles;
    }

    private class TitleTask extends AsyncTask<URL, Void, ArrayList<Title>> {
        @Override
        protected ArrayList<Title> doInBackground(URL... urls) {
            ArrayList<Title> titles = null;

            try {
                // Http connction
                HttpURLConnection urlConnection = (HttpURLConnection)urls[0].openConnection();
                urlConnection.setRequestMethod("GET");

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // Get data and convert the data to String
                    String result = readStream(urlConnection.getInputStream());
                    // Log retrieved data
                    Log.d(TAG, result);

                    // Parse JSON data
                    titles = parseTitleData(result);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return titles;
        }

        @Override
        protected void onPostExecute(ArrayList<Title> titles) {
            super.onPostExecute(titles);

            // Add titles to ListView
            TitleAdapter titleAdapter = new TitleAdapter(getApplicationContext());
            for (int i = 0; i < titles.size(); i++) {
                titleAdapter.addItem(titles.get(i));
            }
            listView.setAdapter(titleAdapter);
        }
    }
}
