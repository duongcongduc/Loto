package it.duc.loto.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class SplashActivity extends Activity{

	MediaPlayer mp = null;
	ImageButton btnEnter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		btnEnter =(ImageButton)findViewById(R.id.bt_enter);
				
		btnEnter.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				mp = MediaPlayer.create(SplashActivity.this, R.raw.xu_roi);
				mp.start();
				
				Thread playSound = new Thread() {
					public void run() {
						try {
							int i = 0;
							while(i < 3000){
								sleep(100);
								i += 100;
							}
							Intent intent = new Intent(SplashActivity.this, LototetActivity.class);
							startActivity(intent);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
				playSound.start();
			}
		});
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mp!=null)
			mp.release();
		mp = null;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(mp!= null && mp.isPlaying())
			mp.pause();
	}

}
