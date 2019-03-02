package it.duc.loto.activity;

import it.duc.loto.adapter.LototetAdapter;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;

public class ResultActivity extends Activity{
	
	LototetAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result);
		
		GridView gv = (GridView)findViewById(R.id.gv_result);
		adapter = new LototetAdapter(this, R.layout.result_item);
		gv.setAdapter(adapter);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	

	
}
