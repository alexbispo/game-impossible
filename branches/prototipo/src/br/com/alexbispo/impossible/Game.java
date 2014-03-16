package br.com.alexbispo.impossible;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class Game extends Activity implements OnTouchListener {
	
	Impossible view;
	Display display;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		display = ((WindowManager) getSystemService(this.WINDOW_SERVICE))  .getDefaultDisplay();
		
		System.out.println("Display: " + display.getWidth() + " x " + display.getWidth());
		
		view = new Impossible(this);
		
		view.setOnTouchListener(this);
		
		setContentView(view);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		view.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//Restart
		if( event.getX() < 80 && event.getY() > 200 ) {
			view.init();			
		}
		
		//Exit
		if(event.getX() > 185 && event.getY() > 200){
			System.exit(0);
		}
		
		if (event.getY() <= 190) {
			view.moveDown(5);
			view.addScore(100);
		}
		//icrementa em 5 pixels a posição vertical do player e o placar.
		
		return true;
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.game, menu);
//		return true;
//	}

}
