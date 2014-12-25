package com.example.riderapp;import java.text.SimpleDateFormat;import java.util.ArrayList;import java.util.Date;import java.util.List;import com.example.riderapp.model.RideActivity;import com.example.riderapp.uiutils.PullAndLoadListView;import com.example.riderapp.uiutils.PullAndLoadListView.OnLoadMoreListener;import com.example.riderapp.uiutils.PullToRefreshListView.OnRefreshListener;import android.app.Activity;import android.content.Context;import android.os.Bundle;import android.support.annotation.Nullable;import android.support.v4.app.ListFragment;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ArrayAdapter;import android.widget.BaseAdapter;import android.widget.ListView;import android.widget.TextView;public class ActivityPage extends ListFragment {	private List<RideActivity> rideActivities;	private MainActivityCallBacks callback;	@Override	public View onCreateView(LayoutInflater inflater, ViewGroup container,			Bundle savedInstanceState) {		ViewGroup view = (ViewGroup) inflater.inflate(				R.layout.activity_page_layout, container, false);		// Set a listener to be invoked when the list should be refreshed.		((PullAndLoadListView) view.findViewById(android.R.id.list))				.setOnRefreshListener(new OnRefreshListener() {					@Override					public void onRefresh() {						RideActivity ra = new RideActivity();						ra.setTitle("清远一日游");						ra.setFrom_where("广州");						ra.setTo_where("清远");						ra.setTime_count(6);						ra.setBegin_date(new SimpleDateFormat(								"yyyy-MM-dd HH:mm:ss").format(new Date()));						ra.setMembers_count(1);						rideActivities.add(0,ra);						((BaseAdapter) getListAdapter()).notifyDataSetChanged();						((PullAndLoadListView) getListView())								.onRefreshComplete();					}				});		// set a listener to be invoked when the list reaches the end		((PullAndLoadListView) view.findViewById(android.R.id.list))				.setOnLoadMoreListener(new OnLoadMoreListener() {					@Override					public void onLoadMore() {						RideActivity ra = new RideActivity();						ra.setTitle("清远一日游");						ra.setFrom_where("广州");						ra.setTo_where("清远");						ra.setTime_count(6);						ra.setBegin_date(new SimpleDateFormat(								"yyyy-MM-dd HH:mm:ss").format(new Date()));						ra.setMembers_count(1);						rideActivities.add(0,ra);						((BaseAdapter) getListAdapter()).notifyDataSetChanged();						((PullAndLoadListView) getListView())								.onLoadMoreComplete();					}				});		return view;	}	@Override	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		rideActivities = new ArrayList<>();		RideActivity ra = new RideActivity();		ra.setTitle("清远一日游");		ra.setFrom_where("广州");		ra.setTo_where("清远");		ra.setTime_count(6);		ra.setBegin_date(new SimpleDateFormat("yyyy-MM-dd HH:mm")				.format(new Date()));		ra.setMembers_count(1);		rideActivities.add(ra);		rideActivities.add(ra);		ActivityAdapter adapter = new ActivityAdapter(getActivity(),				R.layout.activity_item_layout, rideActivities);		setListAdapter(adapter);	}	@Override	public void onListItemClick(ListView l, View v, int position, long id) {		// TODO Auto-generated method stub		callback.onActivitySelected(rideActivities.get(position - 1));	}	@Override	public void onAttach(Activity activity) {		super.onAttach(activity);		this.callback = (MainActivityCallBacks) activity;	}	@Override	public void onActivityCreated(@Nullable Bundle savedInstanceState) {		super.onActivityCreated(savedInstanceState);	}}class ActivityAdapter extends ArrayAdapter<RideActivity> {	private Context context;	private List<RideActivity> objects;	public ActivityAdapter(Context context, int resource,			List<RideActivity> objects) {		super(context, resource, objects);		this.context = context;		this.objects = objects;	}	@Override	public View getView(int position, View convertView, ViewGroup parent) {		// TODO Auto-generated method stub		RideActivity rideActivity = objects.get(position);		LayoutInflater inflater = (LayoutInflater) context				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);		View view = inflater.inflate(R.layout.activity_item_layout, null);		TextView acti_title = (TextView) view.findViewById(R.id.acti_title);		TextView from_where = (TextView) view				.findViewById(R.id.acti_from_where);		TextView to_where = (TextView) view.findViewById(R.id.acti_to_where);		TextView begin_date = (TextView) view.findViewById(R.id.begin_date);		TextView total_days = (TextView) view.findViewById(R.id.total_day);		TextView members = (TextView) view.findViewById(R.id.total_members);		from_where.setText(rideActivity.getFrom_where());		to_where.setText(rideActivity.getTo_where());		begin_date.setText(rideActivity.getBegin_date());		total_days.setText(rideActivity.getTime_count() + "");		members.setText(rideActivity.getMembers_count() + "");		acti_title.setText(rideActivity.getTitle());		// from_where.setText(artical.getFromWhere());		// to_where.setText(artical.getToWhere());		// leader.setText(artical.getLeader());		// arti_title.setText(artical.getTitle());		return view;	}}