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
import android.widget.TextView;

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

public class UserDetail extends AppCompatActivity {

    String url;
    SharedPreferences sp;
    Integer id, status;
    RequestQueue requestQueue;
    Intent intent;
    AlertDialog.Builder alertbuilder;
    ProgressDialog pg;
    JSONObject user_det;
    TextView id_user,admin,email,confirm,pass,anno,result,risk,created;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        sp = getSharedPreferences("saved", Context.MODE_PRIVATE);
        intent = getIntent();
        id = intent.getIntExtra("iduser",0);
        requestQueue = Volley.newRequestQueue(this);

        id_user = findViewById(R.id.id_userd);
        admin = findViewById(R.id.admin);
        email = findViewById(R.id.email_det);
        confirm = findViewById(R.id.confirm_det);
        pass = findViewById(R.id.pass_sts);
        anno = findViewById(R.id.announ);
        result = findViewById(R.id.res);
        risk = findViewById(R.id.newrisk_det);
        created = findViewById(R.id.create);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Detail");

        new async_userdetail().execute();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private class async_userdetail extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            pg = new ProgressDialog(UserDetail.this);
            pg.setMessage("Please Wait");
            pg.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            url ="https://api.cloudsploit.com/v2/users/"+id;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                    response -> {
  // Handle the JSON response
                        try {
                            status = response.getInt("status");
                            if (status == 200){
                                user_det = response.getJSONObject("data");
                                Log.e("aaaaaa",user_det.getString("id"));
                                getuser();

                            } else {

                                alertbuilder
                                        .setCancelable(false)
                                        .setTitle("Information")
                                        .setMessage(response.getString("message"));
                                startActivity(new Intent(UserDetail.this,LoginPage.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        void getuser(){
            try {

                id_user.setText(user_det.getString("id"));
                email.setText(user_det.getString("email"));

                if (user_det.getBoolean("confirmed") == true){
                    confirm.setText("Confirmed");
                } else {
                    confirm.setText("Uncofirmed");
                }
                if (user_det.getBoolean("password_reset") == true){
                    pass.setText("Reseted");
                } else {
                    pass.setText("Not Reseted");
                }
                if (user_det.getBoolean("send_announcements") == true){
                    anno.setText("Yes");
                } else {
                    anno.setText("No");
                }
                if (user_det.getBoolean("send_scan_results") == true){
                    result.setText("Yes");
                } else {
                    result.setText("No");
                }
                if (user_det.getBoolean("send_new_risks") == true){
                    risk.setText("Yes");
                } else {
                    risk.setText("No");
                }
                if (user_det.getBoolean("account_admin") == true){
                   admin.setText("Admin");
                } else {
                    admin.setText("");
                }
                created.setText(user_det.getString("created"));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
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