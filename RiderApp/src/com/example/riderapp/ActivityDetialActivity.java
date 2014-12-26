package com.example.riderapp;import com.example.riderapp.model.RideActivity;import android.app.Activity;import android.content.Intent;import android.os.Bundle;import android.widget.TextView;public class ActivityDetialActivity extends Activity {	private TextView tv_title, tv_leader, tv_others, tv_content;	@Override	protected void onCreate(Bundle savedInstanceState) {		// TODO Auto-generated method stub		super.onCreate(savedInstanceState);		setContentView(R.layout.activity_details);		Intent intent = getIntent();		RideActivity rideactivity = (RideActivity) intent				.getSerializableExtra("rideactivity");		tv_leader = (TextView) findViewById(R.id.ra_leader);		tv_content = (TextView) findViewById(R.id.ra_content);		tv_title = (TextView) findViewById(R.id.ra_title);		tv_others = (TextView) findViewById(R.id.ra_other_info);		tv_title.setText(rideactivity.getTitle());		tv_leader.setText(rideactivity.getLeaderId());		tv_others.setText(rideactivity.getFrom_where() + "→"				+ rideactivity.getTo_where() + "\n"				+ rideactivity.getBegin_date() + "  "				+ rideactivity.getTime_count() + "天 ");		tv_content				.setText("天气越来越好，工作之余不免全身躁动，想象那一缕缕清风拂面的瞬间，一幅幅美景划过的时刻。一起去国内那些风景优美的骑行");		// intent.getExtras().		// String title = intent.getStringExtra("title");		// tv_title.setText(title);	}}