package com.example.riderapp.uiutils;

import android.app.ProgressDialog;
import android.util.DisplayMetrics;
import android.view.Window;

import com.example.riderapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

/**
 * 左边侧滑Activity基类
 * 
 * @author steven-pan
 * 
 */
public class QuickSlidingActivity extends SlidingActivity implements
		OnOpenedListener, OnClosedListener {

	private boolean isOpen = false;

	private ProgressDialog mProgressDialog = null;

	public void setContentView(int resId, int behindId) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setBehindContentView(behindId);
		super.setContentView(resId);

		SlidingMenu sm = getSlidingMenu();// more option see PropertiesActivity
											// sample
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 获取屏幕分辨率
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;

		sm.setBehindWidth((int) (widthPixels * 0.25));
		sm.setBehindScrollScale(0.3f);
		setSlidingActionBarEnabled(false);
		getSlidingMenu().setOnOpenedListener(this);
		getSlidingMenu().setOnClosedListener(this);
	}

	@Override
	public void onOpened() {
		this.isOpen = true;
	}

	@Override
	public void onClosed() {
		this.isOpen = false;
	}

	public boolean isSlidingMenuOpen() {
		return this.isOpen;
	}

	/**
	 * show progress dialog
	 * 
	 * @param message
	 *            message
	 * @param cancel
	 *            cancelable
	 */
	public void showDialog(String message) {
		if (isFinishing()) {
			return;
		}
		if (mProgressDialog == null) {
			mProgressDialog = ProgressDialog.show(this, "", message);
			mProgressDialog.setCanceledOnTouchOutside(false);
		} else {
			mProgressDialog.setMessage(message);
			mProgressDialog.show();
		}
	}

	public boolean isDialogShowing() {
		return (mProgressDialog != null && mProgressDialog.isShowing());
	}

	public void dismissDialog() {
		if (isFinishing()) {
			return;
		}
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

}
