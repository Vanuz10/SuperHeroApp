package com.example.superheroapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private String token = "4536662989746198";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
    }
    public void onClick(View v){
        final EditText superhero = (EditText) findViewById(R.id.superhero);
        String str_superhero = superhero.getText().toString();
        searchHero(str_superhero);
    }
    private void  searchHero(String superhero) {
        String url = "https://www.superheroapi.com/api.php/"+token+"/search/"+superhero;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            ArrayList<String> superhero_list= new ArrayList<>();
                            JSONArray superhero_json = response.getJSONArray("results");

                            for (int i = 0, l = superhero_json.length(); i < l; i++) {
                                superhero_list.add(superhero_json.getJSONObject(i).getString("name")+","+superhero_json.getJSONObject(i).getString("id"));
                            }
                            //System.out.println(superhero_json.getJSONObject(0).getJSONObject());
                            Intent resultsScreen = new Intent(getBaseContext(), Results.class);
                            Bundle b = new Bundle();
                            b.putStringArrayList("superhero_list",superhero_list);
                            resultsScreen.putExtras(b);
                            startActivity(resultsScreen);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog alertDialog = new
                        AlertDialog.Builder(MainActivity.this).create();
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
        mQueue.add(request);
    }
}