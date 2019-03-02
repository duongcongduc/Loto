package it.duc.loto.dialog;

import it.duc.loto.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class AboutDialog extends Dialog{

	private ImageButton btnOK;
	public AboutDialog(Context context) {
		super(context);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		btnOK = (ImageButton)findViewById(R.id.bt_ok);
		
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	

}
