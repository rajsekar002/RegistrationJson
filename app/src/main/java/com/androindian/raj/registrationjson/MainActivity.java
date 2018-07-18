package com.androindian.raj.registrationjson;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    ConnectionDetector cd;
    EditText name,mobile,email,pass;
    String j2=null;
    String url="http://androindian.com/apps/example_app/api.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.reg);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.mail);
        pass=findViewById(R.id.pass);
        cd=new ConnectionDetector();
        cd.isConnectingToInternet(MainActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet(MainActivity.this)) {

                    JSONObject j1 = new JSONObject();
                    try {
                        j1.put("name", name.getText().toString().trim());
                        j1.put("mobile", mobile.getText().toString().trim());
                        j1.put("email", email.getText().toString().trim());
                        j1.put("pswrd", pass.getText().toString().trim());
                        j1.put("baction", "register_user");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Regtration regtration = new Regtration();
                    regtration.execute(j1.toString());
                }

            else{

                    AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("No Network ");
                    alert.show();
            }
            }
        });
    }

    private class Regtration extends AsyncTask<String,String,String> {

        ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Content Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
          j2=JsonFunction.getJsonFromUrlparam(url,params[0]);
            Log.i("result",j2.toString());
            return j2.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.i("result1",j2.toString());
            Log.i("result2",s.toString());
            try {
                JSONObject j3=new JSONObject(j2.toString());
                //JSONObject j3=new JSONObject(s.toString());

                String res1=j3.getString("response");

                if(res1.equalsIgnoreCase("success")){

                    String res2=j3.getString("user");

                    Toast.makeText(MainActivity.this,
                            res2,Toast.LENGTH_LONG).show();

                    /*Intent intent=new Intent(MainActivity.this,Login.class);
                    startActivity(intent);*/

                }else if(res1.equalsIgnoreCase("failed")){
                    String res2=j3.getString("user");

                    Toast.makeText(MainActivity.this,
                            res2,Toast.LENGTH_LONG).show();

                }else {
                    String res2=j3.getString("user");

                    Toast.makeText(MainActivity.this,
                            res2,Toast.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

        /*@Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }*/
    }
}
