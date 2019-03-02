package it.duc.loto.dialog;

import it.duc.loto.activity.LototetActivity;
import it.duc.loto.activity.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class StopDialog extends Dialog{

	private ImageButton btnYes;
	private ImageButton btnNo;
	private Context mContext;
	
	public StopDialog(Context context) {
		super(context);
		mContext = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stop);
		
		StopDialog.this.setOwnerActivity((Activity)mContext);
		
		btnYes = (ImageButton)findViewById(R.id.bt_yess);
		btnNo = (ImageButton)findViewById(R.id.bt_noo);
		
		btnYes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
				((LototetActivity)getOwnerActivity()).stop();
			}
		});
		
		btnNo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	

}
