package com.example.riderapp.adapter;import java.util.List;import android.app.Activity;import android.content.Context;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ArrayAdapter;import android.widget.TextView;import com.example.riderapp.R;import com.example.riderapp.model.RideActivity;public class ActivityAdapter extends ArrayAdapter<RideActivity> {	private Context context;	private List<RideActivity> objects;	public ActivityAdapter(Context context, int resource,			List<RideActivity> objects) {		super(context, resource, objects);		this.context = context;		this.objects = objects;	}	@Override	public View getView(int position, View convertView, ViewGroup parent) {		// TODO Auto-generated method stub		RideActivity rideActivity = objects.get(position);		LayoutInflater inflater = (LayoutInflater) context				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);		View view = inflater.inflate(R.layout.activity_item_layout, null);		TextView acti_title = (TextView) view.findViewById(R.id.acti_title);		TextView from_where = (TextView) view				.findViewById(R.id.acti_from_where);		TextView to_where = (TextView) view.findViewById(R.id.acti_to_where);		TextView begin_date = (TextView) view.findViewById(R.id.begin_date);		TextView total_days = (TextView) view.findViewById(R.id.total_day);		TextView members = (TextView) view.findViewById(R.id.total_members);		from_where.setText(rideActivity.getFrom_where());		to_where.setText(rideActivity.getTo_where());		begin_date.setText(rideActivity.getBegin_date());		total_days.setText(rideActivity.getTime_count() + "天");		members.setText(rideActivity.getMembers_count() + "人");		acti_title.setText(rideActivity.getTitle());		// from_where.setText(artical.getFromWhere());		// to_where.setText(artical.getToWhere());		// leader.setText(artical.getLeader());		// arti_title.setText(artical.getTitle());		return view;	}}