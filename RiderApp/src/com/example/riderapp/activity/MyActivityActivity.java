package com.example.riderapp.activity;import java.text.SimpleDateFormat;import java.util.ArrayList;import java.util.Date;import java.util.List;import android.app.ListActivity;import android.content.Intent;import android.os.AsyncTask;import android.os.Bundle;import android.view.View;import android.widget.BaseAdapter;import android.widget.ListView;import com.example.riderapp.R;import com.example.riderapp.adapter.ActivityAdapter;import com.example.riderapp.model.RideActivity;import com.example.riderapp.uiutils.LoadMoreListView;import com.example.riderapp.uiutils.LoadMoreListView.OnLoadMoreListener;public class MyActivityActivity extends ListActivity {	private List<RideActivity> rideActivities;	private List<RideActivity> rideActivities_temp;	public void notifyDataChanged() {		((BaseAdapter) getListAdapter()).notifyDataSetChanged();		((LoadMoreListView) getListView()).onLoadMoreComplete();	}	public void initLoaderListener() {		// set a listener to be invoked when the list reaches the end		((LoadMoreListView) getListView())				.setOnLoadMoreListener(new OnLoadMoreListener() {					@Override					public void onLoadMore() {						ArcticalRequest ar = new ArcticalRequest();						ar.execute();					}				});	}	@Override	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.load_layout);		rideActivities = new ArrayList<>();		rideActivities_temp = new ArrayList<>();		initLoaderListener();		for (int i = 0; i < 10; i++) {			RideActivity ra = new RideActivity();			ra.setTitle("清远一日游");			ra.setFrom_where("广州");			ra.setTo_where("清远");			ra.setTime_count(6);			ra.setLeaderId("黄小棚");			ra.setBegin_date(new SimpleDateFormat("yy-MM-dd HH:mm")					.format(new Date()));			ra.setMembers_count(1);			rideActivities.add(ra);		}		ActivityAdapter adapter = new ActivityAdapter(MyActivityActivity.this,				R.layout.activity_item_layout, rideActivities);		setListAdapter(adapter);	}	private void addArtical() {		rideActivities.addAll(rideActivities_temp);	}	@Override	public void onListItemClick(ListView l, View v, int position, long id) {		Intent intent = new Intent(this, ActivityDetialActivity.class);		intent.putExtra("rideactivity", rideActivities.get(position));		startActivity(intent);	}	class ArcticalRequest extends AsyncTask<String, String, String> {		@Override		protected String doInBackground(String... params) {			if (isCancelled()) {				return null;			}			try {				Thread.sleep(800);			} catch (InterruptedException e) {				e.printStackTrace();			}			for (int i = 0; i < 8; i++) {				RideActivity ra = new RideActivity();				ra.setTitle("清远一日游");				ra.setFrom_where("广州");				ra.setTo_where("清远");				ra.setTime_count(6);				ra.setLeaderId("黄小棚");				ra.setBegin_date(new SimpleDateFormat("yy-MM-dd HH:mm")						.format(new Date()));				ra.setMembers_count(1);				rideActivities_temp.add(ra);			}			return null;		}		@Override		protected void onPostExecute(String result) {			addArtical();			notifyDataChanged();			super.onPostExecute(result);		}		@Override		protected void onCancelled() {			notifyDataChanged();			super.onCancelled();		}	}}