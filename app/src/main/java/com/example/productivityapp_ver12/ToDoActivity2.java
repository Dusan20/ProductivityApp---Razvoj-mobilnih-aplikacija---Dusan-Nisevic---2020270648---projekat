package com.example.productivityapp_ver12;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ToDoActivity2 extends AppCompatActivity {

    public ArrayList<lista> liste = new ArrayList<>();

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private Button newlpopup_dodaj;
    private Button newlpopup_odustani;
    private EditText newlpopup_ime;
    private TextView newlpopup_title;

    private DBHandler dbHandler;

    public EditText SearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do2);

        Button BBack1 = findViewById(R.id.BBack1);
        Button BAdd = findViewById(R.id.BAdd);

        EditText SearchBar = findViewById(R.id.SearchBar);

        Intent IList = new Intent(ToDoActivity2.this, SpisakTaskovaActivity.class);

        BBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        BAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDialog();
            }
        });

        getData();

    }

    public void createNewDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup, null);

        newlpopup_title = (TextView) contactPopupView.findViewById(R.id.titlemess);

        newlpopup_ime = (EditText) contactPopupView.findViewById(R.id.Edit_ime_nove);

        newlpopup_dodaj = (Button) contactPopupView.findViewById(R.id.BDodaj_popup);
        newlpopup_odustani = (Button) contactPopupView.findViewById(R.id.BOdustani_popup);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newlpopup_dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newlpopup_ime.getText().toString().isEmpty()) {
                    newlpopup_title.setText("Morate uneti ime liste");
                    newlpopup_title.setTextColor(0x99990800);
                } else {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://192.168.1.11:5000/json/add/";
                    StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Toast.makeText(ToDoActivity2.this, "Data sent to API", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject respObj = new JSONObject(response);

                                String name = respObj.getString("ime_liste");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // method to handle errors.
                            Toast.makeText(ToDoActivity2.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                            dbHandler = new DBHandler(ToDoActivity2.this);
                            dbHandler.addnewlist(newlpopup_ime.getText().toString(), "");
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("ime_liste", newlpopup_ime.getText().toString());
                            return params;
                        }
                    };
                    queue.add(request);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            }

        });


        newlpopup_odustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

//    private void setupData() {
//        liste = new ArrayList<lista>();
//        for (int i = 0; i <= 25; i++) {
//            liste.add(new lista(i, "Ime liste " + i));
//        }
//    }

//    public void printData(){
//
//        LayoutInflater inflater = getLayoutInflater();
//        LinearLayout listeLayout = findViewById(R.id.scrollL);
//        for (lista lista : liste) {
//            View red = inflater.inflate(R.layout.sablon, null);
//
//            Button imeListe = red.findViewById(R.id.BList);
//            imeListe.setText(lista.getId()+".  "+lista.getImeL());
//
//            imeListe.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent IList = new Intent(ToDoActivity2.this, SpisakTaskovaActivity.class);
//                    IList.putExtra("listaid", lista.getImeL());
//                    startActivity(IList);
//                }
//            });
//
//            //Search
//             SearchBar = findViewById(R.id.SearchBar);
//             SearchBar.addTextChangedListener(new TextWatcher() {
//                 @Override
//                 public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    }
//
//                 @Override
//                 public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                     for (lista lista: liste){
//                         if(!(imeListe.getText().toString().toLowerCase(Locale.ROOT).contains(SearchBar.getText().toString().toLowerCase(Locale.ROOT)))){
//                             red.setVisibility(View.GONE);
//                             }
//                         else{
//                              red.setVisibility(View.VISIBLE);
//                              }
//
//                          }
//                 }
//
//                 @Override
//                 public void afterTextChanged(Editable editable) {
//                       }
//                  });
//
//
//            Button obrisiListu = red.findViewById(R.id.BObrisi);
//
//            obrisiListu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    liste.remove(lista);
//                    red.setVisibility(View.GONE);
//                }
//            });
//
//
//            listeLayout.addView(red);
//            //listaView.add(red);
//        }
//    }

    public void printData() {
        int delBrojac = -1;
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout listeLayout = findViewById(R.id.scrollL);
        for (lista lista : liste) {
            delBrojac++;
            View red = inflater.inflate(R.layout.sablon, null);

            Button imeListe = red.findViewById(R.id.BList);
            imeListe.setText(lista.getImeL());


            imeListe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent IList = new Intent(ToDoActivity2.this, SpisakTaskovaActivity.class);
                    IList.putExtra("listaid", lista.getId());
                    IList.putExtra("listaime", lista.getImeL());
                    IList.putExtra("listatask", lista.getSpisak());
                    startActivity(IList);
                    finish();
                }
            });

            //Search
            SearchBar = findViewById(R.id.SearchBar);
            SearchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    for (lista lista : liste) {
                        if (!(imeListe.getText().toString().toLowerCase(Locale.ROOT).contains(SearchBar.getText().toString().toLowerCase(Locale.ROOT)))) {
                            red.setVisibility(View.GONE);
                        } else {
                            red.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });


            Button obrisiListu = red.findViewById(R.id.BObrisi);
            int finalDelBrojac = delBrojac;
            obrisiListu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://192.168.1.11:5000/json/del/";
                    StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Toast.makeText(ToDoActivity2.this, "Data sent to API", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject respObj = new JSONObject(response);

                                String name = respObj.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // method to handle errors.
                            Toast.makeText(ToDoActivity2.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("id", String.valueOf(finalDelBrojac));
                            return params;
                        }
                    };
                    queue.add(request);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }

            });


            listeLayout.addView(red);
        }
    }

    private void getData() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.1.11:5000/json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                dbHandler = new DBHandler(ToDoActivity2.this);
                dbHandler.deleteAll();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);

                        String IDL = responseObj.getString("id");
                        String ImeL = responseObj.getString("ime");
                        String TaskL = responseObj.getString("taskovi");

                        liste.add(new lista(IDL, ImeL, TaskL));

                        dbHandler = new DBHandler(ToDoActivity2.this);
                        dbHandler.addnewlist(ImeL,TaskL);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                printData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ToDoActivity2.this, "Nije moguce preuzeti podatke sa servera." + error, Toast.LENGTH_SHORT).show();
                generateSQL();
            }

        });
        queue.add(jsonArrayRequest);

    }

    public void generateSQL() {
        dbHandler = new DBHandler(ToDoActivity2.this);
        List<lista> ListeLocalSQL = dbHandler.readListe();
        for (lista el : ListeLocalSQL) {
            try {
                String IDL = el.getId();
                String ImeL = el.getImeL();
                String TaskL = el.getSpisak();

                liste.add(new lista(IDL, ImeL, TaskL));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        printDataSQL();
    }

    public void printDataSQL() {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout listeLayout = findViewById(R.id.scrollL);
        for (lista lista : liste) {
            View red = inflater.inflate(R.layout.sablon, null);

            Button imeListe = red.findViewById(R.id.BList);
            imeListe.setText(lista.getImeL());


            imeListe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent IList = new Intent(ToDoActivity2.this, SpisakTaskovaActivity.class);
                    IList.putExtra("listaid", lista.getId());
                    IList.putExtra("listaime", lista.getImeL());
                    IList.putExtra("listatask", lista.getSpisak());
                    startActivity(IList);
                    finish();
                }
            });

            //Search
            SearchBar = findViewById(R.id.SearchBar);
            SearchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    for (lista lista : liste) {
                        if (!(imeListe.getText().toString().toLowerCase(Locale.ROOT).contains(SearchBar.getText().toString().toLowerCase(Locale.ROOT)))) {
                            red.setVisibility(View.GONE);
                        } else {
                            red.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });


            Button obrisiListu = red.findViewById(R.id.BObrisi);
            obrisiListu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbHandler.deleteCourse(lista.getId());
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }

            });


            listeLayout.addView(red);
        }
    }
}

