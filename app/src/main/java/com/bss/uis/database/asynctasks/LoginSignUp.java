package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import org.json.JSONObject;

public class LoginSignUp extends AsyncTask<String, String, JSONObject> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        String email = args[2];
        String password = args[1];
        String name= args[0];

//        ArrayList params = new ArrayList();
//        params.add(new BasicNameValuePair("username", name));
//        params.add(new BasicNameValuePair("password", password));
//        if(email.length()>0)
//            params.add(new BasicNameValuePair("email",email));
//
//        JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
//

        return null;

    }

    protected void onPostExecute(JSONObject result) {

        // dismiss the dialog once product deleted
        //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

//        try {
//            if (result != null) {
//                Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


    }

}