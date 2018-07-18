package com.androindian.raj.registrationjson;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonList extends AppCompatActivity {

    String j2=null;
    String url=" http://androindian.com/apps/blog_links/api.php";
    ArrayList Titlearray=new ArrayList();
    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_list);

        listView=findViewById(R.id.list);

        arrayAdapter=new ArrayAdapter(JsonList.this,
                android.R.layout.simple_list_item_1,Titlearray);


        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("action","get_all_links");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonListload jsonListload=new JsonListload();
        jsonListload.execute(jsonObject.toString());
    }

    private class JsonListload extends AsyncTask<String,
            String,String> {
        ProgressDialog progressDialog=new ProgressDialog(JsonList.this);
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
                JSONObject j3=new JSONObject(j2.toString());

                JSONArray jsonArray=j3.getJSONArray("data");
                Log.i("Array",jsonArray.toString());

                for (int i=0; i<jsonArray.length();i++){

                    JSONObject j4=jsonArray.getJSONObject(i);

                    String title=j4.getString("title");
                    Log.i("title",title);
                    Titlearray.add(title);
                }
                listView.setAdapter(arrayAdapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
