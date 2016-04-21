package edu.asu.msse.ser516.speechrecognitionsoftware;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE =1;
    Dialog match_text_dialog;
    ListView textlist;
    ArrayList<String> matches_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchVoiceRecognition(View v) {
        Log.w("Here", "ad");

        if(isConnected()) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(intent, REQUEST_CODE);
        }
        else{
            Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
        }
    }

    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            matches_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String xyz;
            xyz = matches_text.toString();
            if(xyz.contains("maps")){

                Toast.makeText(getApplicationContext(), "I am sexy and I know it :P", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
