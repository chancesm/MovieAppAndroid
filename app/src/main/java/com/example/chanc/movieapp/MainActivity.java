package com.example.chanc.movieapp;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    public static final String SEARCHED_TERM= "com.example.chanc.movieapp.SEARCHED";
    public static final String TITLE= "com.example.chanc.movieapp.TITLE";
    public static final String PATH= "com.example.chanc.movieapp.PATH";
    public static final String RATING= "com.example.chanc.movieapp.RATING";
    public static final String PLOT= "com.example.chanc.movieapp.PLOT";
    public static final String YEAR= "com.example.chanc.movieapp.YEAR";
    public static final String RUNTIME= "com.example.chanc.movieapp.RUNTIME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Glide.with(this).load("https://www.guiamania.com%2Fwp-content%2Fuploads%2F2010%2F08%2F&psig=AOvVaw2VlEQtbLKRsfFoy0O0khJw&ust=1539035921400467").into( (ImageView) findViewById(R.id.testImage));
    }
    // FUNCTION THAT RUNS WHEN SEARCH BUTTON IS CLICKED
    public void startSearch(View view) {
        final ProgressBar myprogress = findViewById(R.id.search_progress);
        myprogress.setIndeterminate(true);
        EditText searchinput = (EditText) findViewById(R.id.editText);
        final String term = (String) searchinput.getText().toString();
        // My URL Endpoint
        String url = this.getApplication().getString(R.string.endpoint) + "&t=" + term;
        // New Activity for the next page
        final Intent nextPage = new Intent(this, movieActivity.class);
        nextPage.putExtra(SEARCHED_TERM, term);
        // Creating a request with the Volley Library
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest searchRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            // Success Callback
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Response Handler", response.toString());
                String Title;
                String ImagePath;
                String Year;
                String Rated;
                String Runtime;
                String Plot;
                try {
                    if(response.getString("Response").equals("True")) {
                        ImagePath = response.getString("Poster");
                        Title = response.getString("Title");
                        Year = response.getString("Year");
                        Rated = response.getString("Rated");
                        Runtime = response.getString("Runtime");
                        Plot = response.getString("Plot");
                    }
                    else {
                        ImagePath = "http://barfutura.com/wp-content/uploads/2012/07/THETITLE_CARTEL_WEBBAR.jpg";
                        Title = "No Movie Found";
                        Year = "N/A";
                        Rated = "N/A";
                        Runtime = "N/A";
                        Plot = "No Movie Found";
                    }
                    // Send Results to the next activity.
                    nextPage.putExtra(TITLE, Title);
                    nextPage.putExtra(PATH, ImagePath);
                    nextPage.putExtra(RATING, Rated);
                    nextPage.putExtra(RUNTIME, Runtime);
                    nextPage.putExtra(YEAR, Year);
                    nextPage.putExtra(PLOT,Plot);
                    myprogress.setIndeterminate(false);
                    startActivity(nextPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(searchRequest);
    }
}
