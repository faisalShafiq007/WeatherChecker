package com.example.weatherchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
   Button button;
  EditText editText;
 ImageView imageView;

     TextView descption;
    TextView temperature;
    TextView visblity;
    class Weather extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... address) {
            //First string means URL isin string,void means nothing,third string means data return in form of string

            try {
                URL url=new URL(address[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                //Establish connection with adress
                connection.connect();
                //retrieve data from url
                InputStream is=connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);

                //retrieve data and return as a string
                int data =isr.read();
                String content="";
                char ch;
                while (data!=-1){
                    ch= (char) data;
                    content=content+ch;
                    data=isr.read();


                }
                return content;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
    public void search(){
        String content="";
        Weather weather =new Weather();

        try {
            if(editText.getText().toString().isEmpty()){
                editText.setError("please enter city");
            }
            if(!editText.getText().toString().isEmpty()){
                content=weather.execute("https://openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&appid=b6907d289e10d714a6e88b30761fae22")
                        .get();

            }

            //First we check data is retrieve succesfully or not
           Log.e("Content",content);
            //Json
            JSONObject jsonObject=new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String temp1=jsonObject.getString("main");
            String visibility=jsonObject.getString("visibility");

            Log.i("Weather data",weatherData);

            //weather data is in array
            JSONArray array=new JSONArray(weatherData);
            String main="";
            String description = "";

            for(int i=0;i<array.length();i++){
                JSONObject weatherpart= array.getJSONObject(i);
                main=weatherpart.getString("main");
                description=weatherpart.getString("description");

            }

            JSONObject json= (JSONObject) new JSONTokener(temp1).nextValue();
         String temperarture= String.valueOf( json.get("temp"));
           String finalMain = main;

            String finalDescription1 = description;

            //temp is also an array
            Log.i("main",main);
            Log.i("description: ",description);

            Log.i("visiblilty: ",visibility);

            Log.i("temperature: ", String.valueOf(temperarture));


            if(finalMain.equals("Drizzle")){
                imageView.setImageResource(R.drawable.drizzle);

            }
            if(finalMain.equals("Smoke")){
                imageView.setImageResource(R.drawable.smoke);

            }
            if(finalMain.equals("Clouds")){
                imageView.setImageResource(R.drawable.cloudy);

            }
            if(finalMain.equals("Clear")){
                imageView.setImageResource(R.drawable.clear);

            }
            if(finalMain.equals("Mist")){
                imageView.setImageResource(R.drawable.mist);

            }
            if(finalMain.equals("Haze")){
                imageView.setImageResource(R.drawable.haze);

            }
            if(finalMain.equals("Rain")){
                imageView.setImageResource(R.drawable.raining);

            }
            if(!editText.getText().toString().isEmpty()){
                descption.setBackgroundResource(R.drawable.background);
                temperature.setBackgroundResource(R.drawable.background);
                visblity.setBackgroundResource(R.drawable.background);
                descption.setText("DESCRIPTION: "+String.valueOf(finalDescription1));

                temperature.setText("TEMPERATURE: "+String.valueOf(temperarture)+" C");
                visblity.setText("VISIBILITY: "+visibility);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         button=findViewById(R.id.button);
         editText=findViewById(R.id.editText);
       imageView=findViewById(R.id.imageview);
        descption=findViewById(R.id.descrition);
         temperature=findViewById(R.id.temperature);
         visblity=findViewById(R.id.visibily);
         getSupportActionBar().setTitle("Weather Checker");
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 search();
             }
         });

    }



}
