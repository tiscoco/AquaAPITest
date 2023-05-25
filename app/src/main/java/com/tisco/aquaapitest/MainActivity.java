package com.tisco.aquaapitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button user,scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        user = findViewById(R.id.user_list);
        scan = findViewById(R.id.scan_data);

        user.setOnClickListener(v -> {
            startActivity(new Intent(this, ShowUser.class));
        });

        scan.setOnClickListener(v -> {
            startActivity(new Intent(this, Scans.class));
        });
    }
}