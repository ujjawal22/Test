package com.example.apple.test;

import android.app.Activity;


import android.app.DialogFragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {


    private static final String TAG = "sitemessage";
    EditText e1,e2,e3,e4,e5,e6,e7;
    Button btn;
    String s1="",s2="",s3="",s4="",s5="",s6="",s7="";
    RequestQueue requestQueue;
    String url = "http://agni.iitd.ernet.in/cop290/assign0/register/";
    TextView textView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        e1 = (EditText) findViewById(R.id.t1);
        e2 = (EditText) findViewById(R.id.t2);
        e3 = (EditText) findViewById(R.id.t3);
        e4 = (EditText) findViewById(R.id.t4);
        e5 = (EditText) findViewById(R.id.t5);
        e6 = (EditText) findViewById(R.id.t6);
        e7 = (EditText) findViewById(R.id.t7);
        textView = (TextView) findViewById(R.id.textView);

        //instantiate the RequestQueue
        requestQueue = Volley.newRequestQueue(this);



        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                s1 = e1.getText().toString();
                s2 = e2.getText().toString();
                s3 = e3.getText().toString();
                s4 = e4.getText().toString();
                s5 = e5.getText().toString();
                s6 = e6.getText().toString();
                s7 = e7.getText().toString();



                //request a string response from provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //instantiate the dialog

                      String s = response.toString();

                        Log.i(TAG, "Response : " + response.toString());
                        Toast toast = Toast.makeText(MainActivity.this, OutputMessage.out(s), Toast.LENGTH_LONG);
                        toast.show();



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                }) {

                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("teamname", s1);
                        params.put("entry1", s3);
                        params.put("name1", s2);
                        params.put("entry2", s5);
                        params.put("name2", s4);
                        params.put("entry3", s7);
                        params.put("name3", s6);

                        return params;
                    }
                };

                //Add the request to the RequestQueue
                requestQueue.add(stringRequest);


            }
        });
    }
}
