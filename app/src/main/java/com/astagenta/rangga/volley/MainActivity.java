package com.astagenta.rangga.volley;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private ImageView mImage;
    private TextView mText;
    private Button mButton;
    private RequestQueue mRequestQueue;
    private Button mMapsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = findViewById(R.id.image);
        mText = findViewById(R.id.text_view_result);
        mButton = findViewById(R.id.parseButton);
        mMapsButton = findViewById(R.id.mapsButton);

        mRequestQueue = Volley.newRequestQueue(this);

        mMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });

    }

    private void jsonParse() {
//        String url = "https://api.myjson.com/bins/kp9wz";
        String url = "http://nearyou.ranggasatria.com/index.php/json/read";
//        String url = "http://192.168.1.5/nearyou/index.php/json/read";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("tempat");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);
                                String firstName = employee.getString("tempat_nama");
                                String imageUrl = employee.getString("kategori_icon");

                                mText.append(firstName + ", " + imageUrl + "\n\n");

                                Picasso.with(MainActivity.this).load("http://nearyou.ranggasatria.com/assets/"+imageUrl).fit().centerInside().into(mImage);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        try {
//                            String nama = response.getString("tempat_nama");
//                            mText.append(nama + "\n\n");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

//                        for (int i = 0; i < response.length() ; i++) {
//                            try {
//                                JSONObject json = response.getJSONObject(String.valueOf(i));
//
//                                String nama = json.getString("tempat_nama");
//                                mText.append(nama + "\n\n");
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }

//                        try {
//                            JSONArray jsonArray = response.getJSONArray("employees");
//                            for (int i = 0; i < jsonArray.length() ; i++) {
//                                JSONObject employee = jsonArray.getJSONObject(i);
//                                String firstName = employee.getString("firstname");
//                                String age = employee.getString("age");
//                                String mail = employee.getString("mail");
//
//                                mText.append(firstName + ", " + String.valueOf(age) + ", " + mail + "\n\n");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);

    }
}
