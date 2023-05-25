package com.tisco.aquaapitest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.tisco.aquaapitest.Adapter.UserAdapter;
import com.tisco.aquaapitest.Models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Scans extends AppCompatActivity {

    String url;
    RequestQueue requestQueue;
    SharedPreferences sp;
    Integer status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scans);
        requestQueue = Volley.newRequestQueue(this);
        sp = getSharedPreferences("saved", Context.MODE_PRIVATE);

        user();

    }

    private class async_scanlist extends AsyncTask<Void, Void, Void> {
        JSONArray baru;

        private void getscanlist(){
            RecyclerView recyclerView = findViewById(R.id.user_list);

            adapter = new UserAdapter(getApplicationContext(),userlist);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);


        }

        @Override
        protected Void doInBackground(Void... voids) {
            url ="https://api.cloudsploit.com/v2/users";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                    response -> {

                        try {
                            status = response.getInt("status");
                            if (status == 200){
                                baru = response.getJSONArray("data");

                                Log.e("aaaaaa",baru.toString());
                                user();
                            } else {

                                alertbuilder
                                        .setCancelable(false)
                                        .setTitle("Information")
                                        .setMessage(response.getString("message"));
                                startActivity(new Intent(ShowUser.this,LoginPage.class));
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
                getuser();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        @SuppressLint("SuspiciousIndentation")
        private void user(){
            UserModel user;
            try {

                for (int x = 0; x < baru.length(); x++){
                    JSONObject a = baru.getJSONObject(x);

                    user = new UserModel(a.getInt("id"),a.getString("email"),a.getBoolean("confirmed"),a.getString("created"));
                    userlist.add(user);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }

    void user(){

        url ="https://api.cloudsploit.com/v2/scans";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {

                    // Handle the JSON response
                    try {

                        status = response.getInt("status");
                        if (status.equals(200)){

                            JSONArray baru = response.getJSONArray("data");

                            Log.e("isi",baru.toString());
                        } else {
                            startActivity(new Intent(this, LoginPage.class));
                            finish();
                        }



                        // Access the dataapi object and extract necessary data
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 422) {
                        // Handle the 422 error (Unprocessable Entity)
                        // You can extract additional information from the response if needed
                        String responseBody = new String(error.networkResponse.data);
                        Log.e("error", responseBody);
                        try {
                            JSONObject errorObject = new JSONObject(responseBody);
                            // Extract specific error information from the errorObject if available
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
    }
}