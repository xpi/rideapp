package com.example.riderapp;import com.example.riderapp.model.Artical;import android.app.Activity;import android.content.Intent;import android.os.Bundle;import android.widget.TextView;public class ArticalDetialActivity extends Activity {	private TextView tv_title, tv_fromw, tv_to, tv_leader, tv_content;	@Override	protected void onCreate(Bundle savedInstanceState) {		// TODO Auto-generated method stub		super.onCreate(savedInstanceState);		setContentView(R.layout.artical_details);		tv_title = (TextView) findViewById(R.id.title);		tv_fromw = (TextView) findViewById(R.id.from_where);		tv_to = (TextView) findViewById(R.id.to_where);		tv_leader = (TextView) findViewById(R.id.leader);		tv_content = (TextView) findViewById(R.id.content);		Intent intent = getIntent();		Artical artical = (Artical) intent.getSerializableExtra("artical");		tv_title.setText(artical.getTitle());		tv_fromw.setText(artical.getFromWhere());		tv_to.setText(artical.getToWhere());		tv_leader.setText(artical.getLeader());		tv_content				.setText("天气越来越好，工作之余不免全身躁动，想象那一缕缕清风拂面的瞬间，一幅幅美景划过的时刻。一起去国内那些风景优美的骑行");		// intent.getExtras().		// String title = intent.getStringExtra("title");		// tv_title.setText(title);	}}