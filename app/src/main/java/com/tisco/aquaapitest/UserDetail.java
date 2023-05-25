package com.tisco.aquaapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserDetail extends AppCompatActivity {

    String url;
    SharedPreferences sp;
    Integer id;
    RequestQueue requestQueue;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        sp = getSharedPreferences("saved", Context.MODE_PRIVATE);
        intent = getIntent();
        id = intent.getIntExtra("iduser",0);
        requestQueue = Volley.newRequestQueue(this);

        user();
    }

    void user(){

        url ="https://api.cloudsploit.com/v2/users/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {

                    // Handle the JSON response
                    try {
                        JSONObject baru = response.getJSONObject("data");

                        Log.e("isi",baru.toString());

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