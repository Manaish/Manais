package com.marketsimplified.afinal;

import android.app.Dialog;
import android.content.ClipData;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycle;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    String url = "https://cuglaemobapp.angelbroking.com/AngelService/MoversNews/Movers/1.0.0";
    JSONObject jsonObject;
    JSONObject jsonObject1;
    JSONArray jsonArray;
    String first;
    String second;
    String third,four;
    Details details;
    DetailsAdapter detailsAdapter;
    List<Details> listdata;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle = findViewById(R.id.recycler);
        listdata = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject requestBody=new JSONObject();
        JSONObject dataBody=new JSONObject();
        JSONObject request=new JSONObject();

        try {
            requestBody.put("appID","f363c1745f5f63433a57e369a01c5752");
            requestBody.put("formFactor","M");
            requestBody.put("futures","0");
            requestBody.put("response_format", "json");
            dataBody.put("category","TOPGAINER");
            dataBody.put("sessionID", "guest");
            dataBody.put("usrID","guest");
            requestBody.put("data",dataBody);
            request.put("request",requestBody);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("dfghkmj",response.toString());

                        try {
                            JSONObject wholejsonobject = response.getJSONObject("response");
                            JSONObject infojsonobject = wholejsonobject.getJSONObject("data");
                            jsonArray= infojsonobject.getJSONArray("bse");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                jsonObject1 = jsonArray.getJSONObject(i);
                                first = jsonObject1.getString("symbolName");
                                second = jsonObject1.getString("changePer");
                                third = jsonObject1.getString("change");
                                four=jsonObject1.getString("ltp");

                                Details details = new Details();
                                details.setSymbolname(first);
                                details.setChangeper(second);
                                details.setChange(third);
                                details.setLtp(four);
                                listdata.add(details);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        detailsAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
        detailsAdapter = new DetailsAdapter(listdata, MainActivity.this);
        detailsAdapter.setMyAdapterClick(new DetailsAdapter.MyAdapterClick() {
            @Override
            public void onItemClick(Details item) {

                Bottomsheet  details = new Bottomsheet();
                details.show(getSupportFragmentManager(),null);
            }
        });
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(detailsAdapter);

//        recycle.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycle, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//                Details details = listdata.get(position);
//
//                Toast.makeText(getApplicationContext(), (details.getSymbolname()) + " is selected!", Toast.LENGTH_SHORT).show();
//                final Dialog dialog = new Dialog(MainActivity.this,
//                        android.R.style.Theme_Translucent_NoTitleBar);
//
//                // Setting dialogview
//                Window window = dialog.getWindow();
//                window.setGravity(Gravity.END);
//
//                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//                dialog.setTitle(null);
//                dialog.setContentView(R.layout.activity_bottomsheet);
//                dialog.setCancelable(true);
//
//                dialog.show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
    }
//    @Override
//    public void onItemClick(Item item)
//    {
//        Log.d("volley_test", "onItemClick: "+item.symbolName);
//        final BuySell buySell=new BuySell();
//        buySell.show(getChildFragmentManager(),null);
//    }


}
