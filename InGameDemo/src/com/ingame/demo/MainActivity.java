package com.ingame.demo;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ingamesdk.manager.IGReceiver;
import com.ingamesdk.manager.InGameSDK;
import com.ingamesdk.model.IGSession;

public class MainActivity extends Activity {

	public static InGameSDK m_InGameSDK = null;
	public String appId = "911fe9dbd0f94625b4b591301c0f3818";
	public String appKey = "a2de0d67a7aecbcc89293fbed6712b1a";
	public static MainActivity instance;
	Button btnLogin, btnPayment, btnShowUser;
	TextView tvInfo;
	private GameReceiver m_gameReceiver;
	private IntentFilter filter;
	private boolean isloggedin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		instance = this;

		m_InGameSDK = InGameSDK.getInstance();
		m_InGameSDK.init(this, true, true, "http://ingame.wc.lt/callbacktest1.php");
		tvInfo = (TextView) findViewById(R.id.tvInfo);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnPayment = (Button) findViewById(R.id.btnPayment);
		btnShowUser = (Button) findViewById(R.id.btnShowUser);
		m_gameReceiver = new GameReceiver();
		filter = new IntentFilter();
	}

	@Override
	protected void onResume() {
		super.onResume();
		filter.addAction(this.getPackageName() + "ingame.login.success");
		filter.addAction(this.getPackageName() + "ingame.logout.success");
		registerReceiver(m_gameReceiver, filter);
		InGameSDK.getInstance().addSDKButton(this);
		InGameSDK.getInstance().setContext(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		InGameSDK.getInstance().removeSDKButton(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(m_gameReceiver);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
		}
		return super.onKeyDown(keyCode, event);
	}

	// Alternative variant for API 5 and higher
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	public void login(View v) {
		if (!isloggedin)
			m_InGameSDK.callLogin();
		else
			m_InGameSDK.callLogout();
	}

	public void Register(View v) {
		m_InGameSDK.callRegister();
	}

	public void showUserInfo(View v) {
		m_InGameSDK.callshowUserInfo();
	}

	public void makePayment(View v) {
		m_InGameSDK.callPayment("ccccccc");
	}

	private class GameReceiver extends IGReceiver {
		@Override
		public void onLoginSuccess(IGSession session) {
			// Toast.makeText(getApplicationContext(),
			// "Trả ra đăng nhập thành công: " + session.getUserName(),
			// Toast.LENGTH_SHORT).show();
			tvInfo.setText("Hello " + session.getUserName());
			tvInfo.setVisibility(View.VISIBLE);
			btnLogin.setText("Logout");
			btnPayment.setVisibility(View.VISIBLE);
			btnShowUser.setVisibility(View.VISIBLE);
			isloggedin = true;
		}

		@Override
		public void onLogoutSuccess() {
			tvInfo.setVisibility(View.GONE);
			btnPayment.setVisibility(View.GONE);
			btnShowUser.setVisibility(View.GONE);
			btnLogin.setText("Login");
			isloggedin = false;
		}

	}
}