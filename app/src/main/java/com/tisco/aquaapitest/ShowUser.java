package com.tisco.aquaapitest;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.Adapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tisco.aquaapitest.Adapter.UserAdapter;
import com.tisco.aquaapitest.Models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowUser extends AppCompatActivity {

    String url;
    RequestQueue requestQueue;
    SharedPreferences sp;
    Integer i, status;
    List<UserModel> userlist = new ArrayList<>();
    private UserAdapter adapter;
    private UserModel model;
    AlertDialog alertDialog;
    AlertDialog.Builder alertbuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        sp = getSharedPreferences("saved", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);


        new async_userlist().execute();


    }

    private class async_userlist extends AsyncTask<Void, Void, Void>{
        JSONArray baru;

        private void getuser(){
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
}