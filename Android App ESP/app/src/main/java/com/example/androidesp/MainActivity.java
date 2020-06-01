package com.example.androidesp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonfrente, buttonesquerda, buttondireita, buttonre;
    String comando = "S";
    EditText urlcompleta;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonfrente = (Button) findViewById(R.id.buttonfrente);
        buttonesquerda = (Button) findViewById(R.id.buttonesquerda);
        buttondireita = (Button) findViewById(R.id.buttondireita);
        buttonre = (Button) findViewById(R.id.buttonre);
        urlcompleta = (EditText) findViewById(R.id.urlcompleta);

        buttonfrente.setOnTouchListener(btnClick);
        buttonesquerda.setOnTouchListener(btnClick);
        buttondireita.setOnTouchListener(btnClick);
        buttonre.setOnTouchListener(btnClick);
    }

    View.OnTouchListener btnClick = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            String endereco = "http://192.168.4.1:8080/?";
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Ao apertar um botão, carrega o comando na variável
                switch (v.getId()) {
                    case R.id.buttonfrente:
                        comando = "dir=F";
                        break;
                    case R.id.buttonesquerda:
                        comando = "dir=L";
                        break;
                    case R.id.buttondireita:
                        comando = "dir=R";
                        break;
                    case R.id.buttonre:
                        comando = "dir=B";
                        break;
                }
            }
            // Ao soltar o botão, envia o comando de parada
            if (event.getAction() == MotionEvent.ACTION_UP) {
                comando = "dir=S";
            }
            String url = endereco + comando;
            MyClientTask taskEsp = new MyClientTask(url);
            taskEsp.execute();
            return true;
        }
    };

    public class MyClientTask extends AsyncTask<String,Void,String>{

        String url;
        MyClientTask(String url){
            this.url = url;
        }

        @Override
        protected String doInBackground(String... params) {
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    urlcompleta.setText(url);
                }
            });
            String serverResponse = "";
            try {
                URL urlCompleta = new URL(url);
                HttpURLConnection connection = (HttpURLConnection)urlCompleta.openConnection();
                connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                serverResponse = e.getMessage();
            }

            return serverResponse;
        }

        @Override
        protected void onPostExecute(String s) {
        }
    }
}

