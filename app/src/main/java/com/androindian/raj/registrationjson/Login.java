package com.androindian.raj.registrationjson;

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

public class Login extends AppCompatActivity {

    Button button;
    ConnectionDetector cd;
    EditText mobile,pass;
    String j2=null;
    String url="http://androindian.com/apps/example_app/api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button=findViewById(R.id.login);

        mobile=findViewById(R.id.mobile);

        pass=findViewById(R.id.pass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("mobile",mobile.getText().toString().trim());
                    jsonObject.put("pswrd",pass.getText().toString().trim());
                    jsonObject.put("baction","login_user");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                LoginUser loginUser=new LoginUser();
                loginUser.execute(jsonObject.toString());
            }
        });

    }

    private class LoginUser extends AsyncTask<String,String,String> {

        ProgressDialog progressDialog=new ProgressDialog(Login.this);
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

            try {
                JSONObject j4=new JSONObject(j2.toString());

                String r1=j4.getString("response");

                if(r1.equalsIgnoreCase("success")){
                    Intent intent=new Intent(Login.this,JsonList.class);
                    startActivity(intent);
                    finish();

                } else if(r1.equalsIgnoreCase("failed")){
                    Toast.makeText(getApplicationContext(),
                            "error",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),
                            "error",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
