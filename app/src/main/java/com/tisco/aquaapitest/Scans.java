package com.tisco.aquaapitest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tisco.aquaapitest.Adapter.ScanAdapter;
import com.tisco.aquaapitest.Models.ScanModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scans extends AppCompatActivity {

    String url;
    RequestQueue requestQueue;
    SharedPreferences sp;
    Integer status;
    AlertDialog.Builder alertbuilder;
    ScanAdapter adapter;
    List<ScanModel> scanlist = new ArrayList<>();
    ProgressDialog pg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scans);
        requestQueue = Volley.newRequestQueue(this);
        sp = getSharedPreferences("saved", Context.MODE_PRIVATE);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Scan List");


        new async_scanlist().execute();

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private class async_scanlist extends AsyncTask<Void, Void, Void> {
        JSONArray scanned;

        @Override
        protected void onPreExecute() {
            pg = new ProgressDialog(Scans.this);
            pg.setMessage("Please Wait");
            pg.show();
        }

        private void getscanlist(){
            RecyclerView recyclerView = findViewById(R.id.scan_list);

            adapter = new ScanAdapter(getApplicationContext(),scanlist);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);


        }

        @Override
        protected Void doInBackground(Void... voids) {
            url ="https://api.cloudsploit.com/v2/scans";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                    response -> {

                        try {
                            status = response.getInt("status");
                            if (status == 200){
                                scanned= response.getJSONArray("data");

                                Log.e("aaaaaa",scanned.toString());
                                scan();
                            } else {

                                alertbuilder
                                        .setCancelable(false)
                                        .setTitle("Information")
                                        .setMessage(response.getString("message"));
                                startActivity(new Intent(Scans.this,LoginPage.class));
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                    },
                    error -> {
                        if (error.networkResponse != null && error.networkResponse.statusCode == 422) {

                            String responseBody = new String(error.networkResponse.data);
                            Log.e("error", responseBody);
                            try {
                                JSONObject errorObject = new JSONObject(responseBody);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Handle other types of errors
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer "+sp.getString("token",""));
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            try {
                if (pg.isShowing()){
                    pg.dismiss();
                }
                getscanlist();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        @SuppressLint("SuspiciousIndentation")
        private void scan(){
            ScanModel scan;
            try {

                for (int x = 0; x < scanned.length(); x++){
                    JSONObject a = scanned.getJSONObject(x);
                    JSONObject key = a.getJSONObject("key");

                    scan = new ScanModel(a.getInt("id"),
                            a.getInt("key_id"),
                            a.getInt("num_pass"),
                            a.getInt("num_warn"),
                            a.getInt("num_fail"),
                            a.getInt("num_unknown"),
                            a.getInt("num_new_risks"),
                            a.getString("created"),
                            key.getString("name"));
                    scanlist.add(scan);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }

}