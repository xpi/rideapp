package com.example.riderapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Locale;

import com.example.riderapp.R;
import com.example.riderapp.model.Artical;
import com.example.riderapp.model.RideActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends FragmentActivity implements
		MainActivityCallBacks {
	private Fragment f1 = new ArticalPage();
	private Fragment f2 = new ActivityPage();
	private Fragment f3 = new MyInfoPage();
	private FragmentManager fragmentManager;
	private Fragment[] fras = { f1, f2, f3 };
	private static final int CAMERA_REQUEST = 1888;
	private static final int ADD_ACTIVITY_CODE = 1002;
	private int currentNoteId;

	

	/**
	 * The number of pages (wizard steps) to show in this demo.
	 */
	private static final int NUM_PAGES = 3;

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;
	Button btn1, btn2, btn3;
	int current = 0;
	int next = 0;
	int state = 0;

	Button[] btns = new Button[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		// hackOverFlow();
		// viewpager使用方法类似ListView
		fragmentManager = this.getSupportFragmentManager();
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pagenum) {
				current = pagenum;

				for (int i = 0; i < btns.length; i++) {
					if (i == current) {
						btns[i].setTextColor(Color.rgb(29, 199, 3));
						btns[i].getBackground().setAlpha(255);
					} else {
						btns[i].setTextColor(Color.rgb(89, 89, 89));

						btns[i].getBackground().setAlpha(0);

					}

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				int orien;
				if (state == 1 && arg1 != 0) {
					if (current == arg0) {
						orien = 1;
						next = arg0 + 1;
						changeBtnStyle(arg1, current, next, orien);
						return;
					}
					orien = -1;
					next = arg0;
					changeBtnStyle(arg1, current, next, orien);
				}

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

				state = arg0;
				if (state == 2) {
					btns[current].getBackground().setAlpha(255);
					btns[next].getBackground().setAlpha(0);
				}

			}

			// 渐变底部bar样式
			private void changeBtnStyle(float size, int current, int next,
					int orien) {
				float fadeIn = (float) (Math.round(size * 100)) / 100;
				float fadeOut = (float) (Math.round((1 - size) * 100)) / 100;
				if (orien == 1) {

					btns[current].getBackground().setAlpha(
							(int) (255 * fadeOut));
					btns[next].getBackground().setAlpha((int) (255 * fadeIn));

					// btns[next].setText(fadeIn + "");
					// btns[current].setText(fadeOut + "");

				}
				if (orien == -1) {

					btns[current].getBackground()
							.setAlpha((int) (255 * fadeIn));

					btns[next].getBackground().setAlpha((int) (255 * fadeOut));

					// btns[next].setText(fadeOut + "");
					// btns[current].setText(fadeIn + "");

				}

			}

		});

	}

	private void initUI() {
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn1.getBackground().setAlpha(255);
		btn2.getBackground().setAlpha(0);
		btn3.getBackground().setAlpha(0);

		btns[0] = btn1;
		btns[1] = btn2;
		btns[2] = btn3;

		btn1.setOnClickListener(new BtnListener());
		btn2.setOnClickListener(new BtnListener());
		btn3.setOnClickListener(new BtnListener());
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Intent cameraIntent = new
		// Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// startActivityForResult(cameraIntent, CAMERA_REQUEST);

		switch (item.getItemId()) {
		case R.id.artical_add:
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);

			// Intent intent1 = new Intent(MainActivity.this,
			// CreateArticalActivity.class);
			// startActivity(intent1);
			break;
		case R.id.activity_add:
			Intent intent2 = new Intent(MainActivity.this,
					CreateActivityActivity.class);
			startActivity(intent2);
			break;

		default:
			break;
		}
		return true;
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	// viewpager scroll listener
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		// without do any anything when destroyed,in case to holding the hiden
		// page
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return fras[0];
			case 1:
				return fras[1];
			case 2:
				return fras[2];
			}
			return fras[0];
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	// bottom bar lisetner
	class BtnListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int bid = v.getId();
			switch (bid) {
			case R.id.button1:
				next = 0;
				mPager.setCurrentItem(0, false);
				break;
			case R.id.button2:
				next = 1;
				mPager.setCurrentItem(1, false);
				break;
			case R.id.button3:
				next = 2;
				mPager.setCurrentItem(2, false);
				break;

			}
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		setOverflowIconVisible(featureId, menu);
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public void onActivitySelected(RideActivity rideActivity) {
		Intent intent = new Intent(this, ActivityDetialActivity.class);
		intent.putExtra("rideactivity", rideActivity);
		startActivity(intent);
	}

	@Override
	public void onArticalSelected(Artical artical) {
		Intent intent = new Intent(this, ArticalDetialActivity.class);
		intent.putExtra("artical", artical);
		startActivity(intent);
	}

	@Override
	public void onActivityIntent(Class<?> activity) {
		Intent intent = new Intent(this, activity);
		startActivity(intent);
	}

	// 显示actionbar中下拉菜单的图标
	public static void setOverflowIconVisible(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
	}

}
