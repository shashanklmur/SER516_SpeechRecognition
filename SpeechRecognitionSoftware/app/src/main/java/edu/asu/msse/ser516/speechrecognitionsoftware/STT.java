package edu.asu.msse.ser516.speechrecognitionsoftware;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/*
 *  Nothing to see here.  all the code for speech is in the MainFragment.
 */

public class STT extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stt);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
		}
	}
}
