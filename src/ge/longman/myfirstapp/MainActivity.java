package ge.longman.myfirstapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "ge.longman.myfirstapp.MESSAGE";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        GetJson task = new GetJson();
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    private class GetJson extends AsyncTask<Void, Void, ArrayList<Currency>> {

        @Override
        protected ArrayList<Currency> doInBackground(Void... params) {
        	URL url;
        	JSONObject responseObj = null;
        	
        	ArrayList<Currency> curList = new ArrayList<Currency>();
        	
			try {
				URL url1 = new URL("http://null.ge/json.php");
			    HttpURLConnection request = (HttpURLConnection) url1.openConnection();
			
			    request.connect();
			
			    String response = JSONfunctions.convertStreamToString(request.getInputStream());
			    
			    responseObj = new JSONObject(response);
				
				JSONArray curListJson = responseObj.getJSONArray("data");
				JSONObject item = null;
				
				for(int i=0; i < curListJson.length(); i++) {
					item = new JSONObject(curListJson.getString(i));
					
					Currency cur = new Currency();
					
					cur.amount 	= item.getInt("amount");
					cur.code 	= item.getString("code");
					cur.name 	= item.getString("name");
					cur.rate 	= item.getDouble("rate");
					
					curList.add(cur);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
        	
        	return curList;
        }      

        @Override
        protected void onPostExecute(ArrayList<Currency> result) {
        	for(int i=0; i<result.size(); i++) {
        		Log.d("koko", "onpostexec: "+ result.get(i).toString());
        	}
              //might want to change "executed" for the returned string passed into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
        	Log.d("koko","loading..");
        }

        @Override
        protected void onProgressUpdate(Void... values) { }
    }
   
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        String json = "{\"data\":{\"USD\":{\"code\":\"USD\",\"name\":\"\\u10d0\\u10e8\\u10e8 \\u10d3\\u10dd\\u10da\\u10d0\\u10e0\\u10d8\",\"amount\":\"1\",\"rate\":\"1.6557\"},\"EUR\":{\"code\":\"EUR\",\"name\":\"\\u10d4\\u10d5\\u10e0\\u10dd\",\"amount\":\"1\",\"rate\":\"2.1526\"},\"RUB\":{\"code\":\"RUB\",\"name\":\"\\u10e0\\u10e3\\u10e1\\u10e3\\u10da\\u10d8 \\u10e0\\u10e3\\u10d1\\u10da\\u10d8\",\"amount\":\"100\",\"rate\":\"5.3209\"}}}";
    	
    	
    	String toDisplay = "";

        try {
        	String code;
        	String name;
        	
	    	JSONObject jObject = new JSONObject(json);  
	    	JSONObject jobj = jObject.getJSONObject("data");
	    	
	    	Log.d("AAA", ""+jobj.length());
	    	//toDisplay = "AAAAAAAAAAAAAAAA";//jarray.toString();
	    	/*
	    	for (int i = 0; i < jarray.length(); i++) {
	    	    JSONObject row = jarray.getJSONObject(i);
	    	    code = row.getString("id");
	    	    name = row.getString("name");
	    	    toDisplay += name+"\n";
	    	}
	    	*/
	    }
        catch (JSONException e) {  
        	
        	toDisplay = e.toString();//printStackTrace(); 
        } 
        
        //Log.d("AAA", toDisplay);


	    TextView textView = new TextView(this);
	    textView.setTextSize(30);
	    textView.setText(toDisplay);
   
	    // Set the text view as the activity layout
	    setContentView(textView);   
    
    }

}
