package com.example.riderapp.activity;import java.util.ArrayList;import java.util.List;import com.example.riderapp.R;import com.example.riderapp.adapter.ArticalsAdapter;import com.example.riderapp.model.Artical;import com.example.riderapp.uiutils.LoadMoreListView;import com.example.riderapp.uiutils.LoadMoreListView.OnLoadMoreListener;import android.app.ListActivity;import android.content.Intent;import android.os.AsyncTask;import android.os.Bundle;import android.view.View;import android.widget.BaseAdapter;import android.widget.ListView;public class MyArticalActivity extends ListActivity {	private List<Artical> articals;	private List<Artical> articals_temp;	public void notifyDataChanged() {		((BaseAdapter) getListAdapter()).notifyDataSetChanged();		((LoadMoreListView) getListView()).onLoadMoreComplete();	}	public void initLoaderListener() {		// set a listener to be invoked when the list reaches the end		((LoadMoreListView) getListView())				.setOnLoadMoreListener(new OnLoadMoreListener() {					@Override					public void onLoadMore() {						ArcticalRequest ar = new ArcticalRequest();						ar.execute();					}				});	}	@Override	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.load_layout);		articals = new ArrayList<>();		articals_temp = new ArrayList<>();		initLoaderListener();		for (int i = 0; i < 10; i++) {			Artical a = new Artical();			a.setTitle("清远一日游");			a.setFromWhere("广州");			a.setToWhere("清远清新");			a.setLeader("晓晓棚");			articals.add(a);		}		ArticalsAdapter adapter = new ArticalsAdapter(MyArticalActivity.this,				R.layout.artical_item_layout, articals);		setListAdapter(adapter);	}	private void addArtical() {		articals.addAll(articals_temp);	}	@Override	public void onListItemClick(ListView l, View v, int position, long id) {		Intent intent = new Intent(this, ArticalDetialActivity.class);		intent.putExtra("artical", articals.get(position));		startActivity(intent);	}	class ArcticalRequest extends AsyncTask<String, String, String> {		@Override		protected String doInBackground(String... params) {			if (isCancelled()) {				return null;			}			try {				Thread.sleep(800);			} catch (InterruptedException e) {				e.printStackTrace();			}			for (int i = 0; i < 8; i++) {				Artical a = new Artical();				a.setTitle("清远一日游");				a.setFromWhere("广州");				a.setToWhere("清远清新");				a.setLeader("晓晓棚");				articals_temp.add(a);			}			return null;		}		@Override		protected void onPostExecute(String result) {			addArtical();			notifyDataChanged();			super.onPostExecute(result);		}		@Override		protected void onCancelled() {			notifyDataChanged();			super.onCancelled();		}	}}