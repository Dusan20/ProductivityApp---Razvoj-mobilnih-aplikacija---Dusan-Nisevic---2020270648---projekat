package com.example.productivityapp_ver12;

import static java.lang.System.out;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent ITimer = new Intent(MainActivity.this , TimerActivity.class);
        Intent IToDo = new Intent(MainActivity.this , ToDoActivity2.class);

        Button BT = findViewById(R.id.BTimer);
        Button BToDo = findViewById(R.id.BLists);
        Button BExit = findViewById(R.id.BExit );

        BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ITimer);
            }
        });

        BToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IToDo);
            }
        });

        BExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}