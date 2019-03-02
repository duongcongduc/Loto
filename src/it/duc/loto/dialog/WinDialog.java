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
import android.widget.TextView;

public class WinDialog extends Dialog{

	private ImageButton btnYes;
	private ImageButton btnNo;
	private TextView tv_stop;
	private Context mContext;
	
	public WinDialog(Context context) {
		super(context);
		mContext = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stop);
		
		WinDialog.this.setOwnerActivity((Activity)mContext);
		
		tv_stop = (TextView)findViewById(R.id.tv_stop);
		btnYes = (ImageButton)findViewById(R.id.bt_yess);
		btnNo = (ImageButton)findViewById(R.id.bt_noo);
		
		tv_stop.setText("Congratulation!");
		
		btnYes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
				((LototetActivity)getOwnerActivity()).win();
			}
		});
		
		btnNo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	

}
