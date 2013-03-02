package br.ufpr.bioinfo.bak4bio.android.control;

import br.com.agivis.framework.app.AgActivity;
import br.com.agivis.framework.utils.ActivityUtils;
import br.ufpr.bioinfo.bak4bio.android.R;
import android.os.Bundle;
import android.os.Handler;

public class B4BSplashActivity extends AgActivity implements Runnable {

	private static final long SPLASH_MIN_TIME = 2000;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		long startTime = System.currentTimeMillis();
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.b4b_splash);
		
		long endTime = System.currentTimeMillis();
		long waitTime = SPLASH_MIN_TIME - (endTime - startTime);

		if (waitTime < 0) {
			waitTime = 0;
		}
		
		Handler h = new Handler();
		h.postDelayed(this, waitTime);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public void run() {
		ActivityUtils.startActivity(B4BSplashActivity.this, B4BMenuActivity.class);
		this.finish();
	}
}
