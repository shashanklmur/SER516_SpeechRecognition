package edu.asu.msse.ser516.speechrecognitionsoftware;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class IconActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 1;
    ArrayList<String> inputVoiceCommand;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);
        iv = (ImageView) findViewById(R.id.imageView1);
    }

    public void launchVoiceRecognition(View v) {
        if (isConnected()) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 5000000);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net != null && net.isAvailable() && net.isConnected()) {
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
            if (inputVoiceData.contains("go back")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (inputVoiceData.toLowerCase().contains("increase size")) {
//                Toast.makeText(getApplicationContext(), "Playing next", Toast.LENGTH_LONG).show();

                    iv.setImageResource(R.drawable.music_icon);
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.width = params.width+200;
                    params.height = params.height+200;
                    iv.setLayoutParams(params);

//                    RelativeLayout.LayoutParams layoutParams  = new RelativeLayout.LayoutParams(oldh+100,oldw+100);
//                    iv.setLayoutParams(layoutParams);



            } else if (inputVoiceData.toLowerCase().contains("decrease size")) {
//                Toast.makeText(getApplicationContext(), "Playing previous", Toast.LENGTH_LONG).show();
                iv.setImageResource(R.drawable.music_icon);
                ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.width = params.width-100;
                    params.height = params.height-100;
                    iv.setLayoutParams(params);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
