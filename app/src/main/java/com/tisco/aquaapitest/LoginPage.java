package com.tisco.aquaapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    EditText email_txt, pass_txt;
    String email, pass;
    Button login_btn;
    RequestQueue requestQueue;
    JSONObject jsonBody;
    JsonObjectRequest jsonObjectRequest;
    StringRequest logins;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        email_txt = findViewById(R.id.email_login);
        pass_txt = findViewById(R.id.pass_login);
        login_btn = findViewById(R.id.login_button);

        email_txt.setText("anggi.fp+api@netpoleons.com");
        pass_txt.setText("Aqua-api1!");

        email = email_txt.getText().toString().trim();
        pass =  pass_txt.getText().toString().trim();;




        login_btn.setOnClickListener(v -> {
            login();
        });

    }

    void login (){

        String url = "https://api.cloudsploit.com/v2/signin";
        requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", pass);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {

                    // Handle the JSON response
                    try {
                        JSONObject dataApi = response.getJSONObject("data");

                        sp = getSharedPreferences("saved",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();

                        editor.putString("token",dataApi.getString("token"));
                        editor.apply();

                        startActivity(new Intent(this,MainActivity.class));


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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", pass);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Content-Type","application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes();
            }
        };

        requestQueue.add(jsonObjectRequest);

    }

}