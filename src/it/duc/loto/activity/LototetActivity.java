package it.duc.loto.activity;

import it.duc.loto.data.Constant;
import it.duc.loto.data.Numbers;
import it.duc.loto.dialog.AboutDialog;
import it.duc.loto.dialog.ExitDialog;
import it.duc.loto.dialog.SelectDialog;
import it.duc.loto.dialog.StopDialog;
import it.duc.loto.dialog.WinDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LototetActivity extends Activity implements MediaPlayer.OnCompletionListener, 
	MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener{
	
	private static final int MAX_LENGTH = 90;
	//private String[] VOICE_PEOPLE = {"Thường", "Hội chợ"};
	
	private Handler mHandler;
	private MediaPlayer mp = null;

	private LinearLayout llFooter;
	private ImageButton btnPlay;
	private ImageButton btnStop;
	private ImageButton btnResult;
	private ImageButton btnWin;
	private ImageButton btnSelect;
	private TextView mTv;
	private TextView mLastNum;
	
	private ExitDialog eDialog;
	private AboutDialog aDialog;
	private StopDialog sDialog;
	private WinDialog wDialog;
	private SelectDialog slDialog;
	
	public static int[] media;
	int m = 0;
	private Random rand;
	private AssetFileDescriptor descriptor;
	ArrayList<Integer> arrayNumbers;
	ArrayList<Integer> arrayLast;
	public static ArrayList<Constant> arrayResults = new ArrayList<Constant>();
	boolean isFirstTime = true;
	boolean isPlaying = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loto);
		
		initCreate();
		
		btnPlay.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playPause();
			}
		});
		
		btnStop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				pause();
				sDialog.show();
			}
		});
		
		btnResult.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LototetActivity.this, ResultActivity.class);
				startActivity(intent);
			}
		});
		
		btnWin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				pause();
				wDialog.show();
			}
		});
		
		btnSelect.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				slDialog.show();
			}
		});
	}
	
	private void initCreate(){
		Log.d("isPlaying", "false");
		
		media = Numbers.sStandard;
		
		eDialog = new ExitDialog(LototetActivity.this);
		aDialog = new AboutDialog(LototetActivity.this);
		sDialog = new StopDialog(LototetActivity.this);
		wDialog = new WinDialog(LototetActivity.this);
		slDialog = new SelectDialog(LototetActivity.this);
		
		mHandler = new Handler();
		mp = new MediaPlayer();
		
		arrayNumbers = new ArrayList<Integer>();
		arrayLast = new ArrayList<Integer>();
		rand = new Random();
		
		mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mp.setOnCompletionListener(this);
		mp.setOnErrorListener(this);
	
		llFooter = (LinearLayout)findViewById(R.id.llFooter);
		
		mTv = (TextView)findViewById(R.id.lbl_number);
		mLastNum = (TextView)findViewById(R.id.lbl_lastnum);
		btnStop = (ImageButton)findViewById(R.id.bt_stop);
		btnPlay = (ImageButton)findViewById(R.id.bt_play);
		btnResult = (ImageButton)findViewById(R.id.bt_result);
		btnWin = (ImageButton)findViewById(R.id.bt_win);
		btnSelect = (ImageButton)findViewById(R.id.bt_select);
		
		btnResult.setEnabled(false);
		btnStop.setEnabled(false);
		btnWin.setEnabled(false);
	}
	
	private void playPause(){
		if(isPlaying == true){
			isPlaying = false;
			Log.d("isPlaying", "false");
			if(mp.isPlaying())
				mp.pause();
			btnPlay.setImageResource(R.drawable.btn_play);
		}
		else{
			isPlaying = true;
			Log.d("isPlaying", "true");
			if(isFirstTime == true){
				isFirstTime = false;
				playLoto();
				btnPlay.setImageResource(R.drawable.btn_pause);
				btnResult.setEnabled(true);
				btnStop.setEnabled(true);
				btnWin.setEnabled(true);
			}else{
				mp.start();
				btnPlay.setImageResource(R.drawable.btn_pause);
			}
		}	
	}
	
  	private void playLoto(){
		if(arrayNumbers.size() < MAX_LENGTH){
			play();
		}else{
			mp.stop();
			isFirstTime = true;
			isPlaying = false;
			Log.d("isPlaying", "false");
			btnPlay.setImageResource(R.drawable.btn_play);
			btnPlay.setEnabled(false);
			mTv.setText("00");
		}
	}
  	
  	private void play(){
  		try {
  			m = getNumber();
  			
  			arrayResults.add(new Constant(String.valueOf(m)));
  			mTv.setText(Integer.toString(m));
  			
			descriptor = getResources().openRawResourceFd(media[m-1]);
			mp.reset();
			mp.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
			descriptor.close();
			mp.setOnPreparedListener(LototetActivity.this);
			mp.prepareAsync();
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch(IllegalStateException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

  	private int getNumber(){
  		int i = rand.nextInt(media.length);
  		int j = i + 1;
		if(arrayNumbers.isEmpty())
			arrayNumbers.add(j);
		else{
			do{
				i = rand.nextInt(media.length);
				j = i + 1;
			}while(!isAvailable(j));
			arrayNumbers.add(j);
		}
		return j;
  	}
	private boolean isAvailable(int j){
		for(int i = 0; i < arrayNumbers.size(); i++){
			if(j == ((Integer)arrayNumbers.get(i)).intValue())
				return false;
		}
		return true;
	}
	
	public void win(){
		try {
			stop();
			
			llFooter.setVisibility(View.INVISIBLE);
			mTv.setText("You win!");
			
			descriptor = getResources().openRawResourceFd(R.raw.you_win);
			mp.reset();
			mp.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
			descriptor.close();
			mp.setOnPreparedListener(LototetActivity.this);
			mp.prepareAsync();

			mHandler.postDelayed(mWin, 7000);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch(IllegalStateException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void stop(){
		mp.stop();
		isFirstTime = true;
		isPlaying = false;
		Log.d("isPlaying", "false");
		
		btnPlay.setImageResource(R.drawable.btn_play);
	
		btnPlay.setEnabled(true);
		btnResult.setEnabled(false);
		btnStop.setEnabled(false);
		btnWin.setEnabled(false);
		
		mLastNum.setText("");
		mTv.setText("00");
		
		arrayLast.clear();
		arrayNumbers.clear();
		arrayResults.clear();
	}
	
	private void pause(){
		if(isPlaying == true){
			isPlaying = false;
			Log.d("isPlaying", "false");
			if(mp.isPlaying())
				mp.pause();
			btnPlay.setImageResource(R.drawable.btn_play);
		}
	}
	
	private Runnable mWin = new Runnable() {
		public void run() {
			mp.stop();
			llFooter.setVisibility(View.VISIBLE);
			mTv.setText("00");
		}
	};
	
	public void onCompletion(MediaPlayer mp) {
		
		if(isPlaying == true){	
			arrayLast.add(m);
			if(arrayLast.size()==6){
				arrayLast.remove(0);
			}
			String last = "";
			for(int k=0 ; k<arrayLast.size(); k++){
	  			last += arrayLast.get(k).toString() + " ";
			}
			mLastNum.setText(last);
			
			playLoto();
		}	
	}
	
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.e("LoToTet", "MediaPlayer error: " + what + ' ' + extra);
		return true;
	}

	public void onPrepared(MediaPlayer mp) {
		mp.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if(item.getItemId() == R.id.menu_about){
			pause();
			aDialog.show();
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			pause();
			eDialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
			mp.release();
			arrayResults.clear();
			arrayLast.clear();
			arrayNumbers.clear();
	}

	@Override
	public void onPause() {
		super.onPause();
		pause();
	}
}
