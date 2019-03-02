package it.duc.loto.dialog;

import it.duc.loto.activity.LototetActivity;
import it.duc.loto.activity.R;
import it.duc.loto.data.Numbers;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioGroup;

public class SelectDialog extends Dialog{

	private RadioGroup rgType;
	private ImageButton btnOk;
	
	public SelectDialog(Context context) {
		super(context);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select);

		rgType = (RadioGroup)findViewById(R.id.rg_type);
		btnOk = (ImageButton)findViewById(R.id.bt_okk);
		
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch(rgType.getCheckedRadioButtonId()){
				case R.id.rb_normal:
					LototetActivity.media = Numbers.sStandard;
					break;
				case R.id.rb_hc:
					LototetActivity.media = Numbers.sNumbers;
					break;
				}
				
				dismiss();
			}
		});
		
	}
}
