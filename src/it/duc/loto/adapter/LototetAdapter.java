package it.duc.loto.adapter;

import it.duc.loto.activity.LototetActivity;
import it.duc.loto.activity.R;
import it.duc.loto.data.Constant;
import it.duc.loto.data.Numbers;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LototetAdapter extends BaseAdapter{
	
	private int[] mNumberText = Numbers.NumbText;
	private Context mContext;
	private int mLayout;
	private LayoutInflater mInflater;
	
	public LototetAdapter(Context c, int layout){
		mContext = c;
		mLayout = layout;
		mInflater = LayoutInflater.from(mContext);
	}

	public int getCount() {
		return mNumberText.length;
	}

	public Object getItem(int position) {
		return mNumberText[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View view = mInflater.inflate(mLayout, parent, false);
		TextView tv = (TextView)view.findViewById(R.id.tv_result);
		tv.setText(String.valueOf(mNumberText[position]));
		
		for(int i = 0; i<LototetActivity.arrayResults.size();i++){
			Constant cons = (Constant)LototetActivity.arrayResults.get(i);
			int j = Integer.valueOf(cons.getNumb());
			if(mNumberText[position] == j){
				tv.setBackgroundResource(R.drawable.bg_gv_press);
				tv.setTextColor(Color.parseColor("#c50f0f"));
			}
		}
		
		return view;
	}

}
