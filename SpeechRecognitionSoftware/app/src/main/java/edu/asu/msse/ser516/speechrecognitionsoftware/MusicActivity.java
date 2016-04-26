package edu.asu.msse.ser516.speechrecognitionsoftware;

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
import android.widget.Toast;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {
    private final int REQUEST_CODE =1;
    ArrayList<String> inputVoiceCommand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
    }

    public void launchVoiceRecognition(View v) {
        if(isConnected()) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(intent, REQUEST_CODE);
        }
        else{
            Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_LONG).show();
        }
    }

    public  boolean isConnected() {
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
            inputVoiceCommand = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String inputVoiceData;
            inputVoiceData = inputVoiceCommand.toString();
            Log.w("matched text: ", inputVoiceData);
            if (inputVoiceData.contains("music off")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (inputVoiceData.toLowerCase().contains("next")) {
                Toast.makeText(getApplicationContext(), "Playing next", Toast.LENGTH_LONG).show();
            } else if (inputVoiceData.toLowerCase().contains("previous")) {
                Toast.makeText(getApplicationContext(), "Playing previous", Toast.LENGTH_LONG).show();
            } else if (inputVoiceData.toLowerCase().contains("stop")) {
                Toast.makeText(getApplicationContext(), "Music stopped", Toast.LENGTH_LONG).show();
            } else if (inputVoiceData.toLowerCase().contains("play")) {
                Toast.makeText(getApplicationContext(), "Played", Toast.LENGTH_LONG).show();
            } else if (inputVoiceData.toLowerCase().contains("pause")) {
                Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
