package com.example.superheroapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Results extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private RequestQueue m2Queue;
    private String token = "4536662989746198";
    ArrayList<String> superhero_list;
    ArrayList<String> names_list = new ArrayList<String>();
    ArrayList<String> id_list = new ArrayList<String>();
    ListView superhero_view;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            superhero_list = b.getStringArrayList("superhero_list");
            for (int i = 0, l = superhero_list.size(); i < l; i++) {
                String[] parts = superhero_list.get(i).split(",");
                names_list.add(parts[0]);
                id_list.add(parts[1]);

            }
        }





        superhero_view = (ListView) findViewById(R.id.superhero_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,names_list);


        superhero_view.setAdapter(adapter);
        superhero_view.setOnItemClickListener(this);


        //superhero_view.addTouchables(superhero_list);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String url = "https://www.superheroapi.com/api.php/"+token+"/id/"+names_list.get(i);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {


//Intent resultsScreen = new Intent(getBaseContext(), Results.class);
                            //resultsScreen.putExtra("superhero_liost", (Parcelable) superhero_list);
                           // Bundle b=new Bundle();
                          //  b.putStringArrayList("superhero_list",superhero_list);
                         //   resultsScreen.putExtras(b);
                           // startActivity(resultsScreen);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog alertDialog = new
                        AlertDialog.Builder(Results.this).create();
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage("Credenciales Incorrectas");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int
                                    which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        m2Queue.add(request);

    }
}