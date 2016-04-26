package edu.asu.msse.ser516.speechrecognitionsoftware;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
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
    ArrayList<String> inputVoiceCommand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Log.w("matched text: " , inputVoiceData);
            if(inputVoiceData.contains("maps")){
                Toast.makeText(getApplicationContext(), "maps", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            if (inputVoiceData.contains("emergency call")) {
                Toast.makeText(getApplicationContext(), "I am sexy and I know it :P", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 911"));
                startActivity(intent);
            }
            if (inputVoiceData.contains("bluetooth on")) {
                Toast.makeText(getApplicationContext(), "Turned Bluetooth On", Toast.LENGTH_SHORT).show();
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                }
            }
            if (inputVoiceData.contains("bluetooth off")) {
                Toast.makeText(getApplicationContext(), "Turned Bluetooth Off", Toast.LENGTH_SHORT).show();
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                }
            }
            if (inputVoiceData.contains("music on")) {
                Intent i = new Intent(this, MusicActivity.class);
                startActivity(i);
            }
            if (inputVoiceData.contains("settings")) {
                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            if (inputVoiceData.contains("message")) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "12125551212");
                smsIntent.putExtra("sms_body", "Body of Message");
                startActivity(smsIntent);
            }if (inputVoiceData.contains("meeting")) {
                Intent i = new Intent(this, Meetings.class);
                startActivity(i);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
