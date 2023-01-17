package com.example.productivityapp_ver12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SpisakTaskovaActivity extends AppCompatActivity {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisak_taskova);

        Intent ITD = new Intent(this , ToDoActivity2.class);

        Button BNazad = findViewById(R.id.BBack);
        Button BSacuvaj = findViewById(R.id.Bsave);
        EditText Naslov = findViewById(R.id.naslov_spiska);
        EditText TaskList = findViewById(R.id.taskview);
        TextView ErrorMessage = findViewById(R.id.err);

        Intent IRDL = new Intent(SpisakTaskovaActivity.this, ToDoActivity2.class);

        String naslov = getIntent().getStringExtra("listaime");
        String id = getIntent().getStringExtra("listaid");
        String task = getIntent().getStringExtra("listatask");
        Naslov.setText(naslov);
        TaskList.setText(task);

        BNazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(IRDL);
            }
        });

        BSacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Naslov.getText().toString().isEmpty()){
                    ErrorMessage.setVisibility(View.VISIBLE);
                    ErrorMessage.setText("Morate uneti ime liste");
                    ErrorMessage.setTextColor(0x99990800);
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://192.168.1.11:5000/json/edit/";
                    StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Toast.makeText(SpisakTaskovaActivity.this, "Data sent to API", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject respObj = new JSONObject(response);

                                String name = respObj.getString("ime_liste_edit");
                                String id = respObj.getString("id_liste_edit");
                                String tasks = respObj.getString("task_liste_edit");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SpisakTaskovaActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                            dbHandler = new DBHandler(SpisakTaskovaActivity.this);
                            dbHandler.updateCourse(id, Naslov.getText().toString(), TaskList.getText().toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("ime_liste_edit", Naslov.getText().toString());
                            params.put("id_liste_edit", id);
                            params.put("task_liste_edit",TaskList.getText().toString());
                            return params;
                        }
                    };
                    queue.add(request);
                    finish();
                    startActivity(IRDL);

                }
            }

        });

    }
}