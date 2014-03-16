package br.com.alexbispo.impossible;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Impossible extends SurfaceView implements Runnable {
	
	boolean running = false;
	Thread renderThread = null;
	SurfaceHolder holder;
	Paint paint;
	
	private int enemyX, enemyY, enemyRadius = 5;
	private int playerX = 140, playerY = 50, playerRadius = 10;
	private double distance;
	private boolean gameover;
	
	private int score;

	public Impossible(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
		
	}

	@Override
	public void run() {
		while(running){
			
			//verifica se a tela já está pronta
			if(!holder.getSurface().isValid())
				continue;
			
			// bloqueia o canvas
			Canvas canvas = holder.lockCanvas();
//			canvas.drawColor(Color.BLACK);
			canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sky), 0, 0, null);
			
			// desenha o player e o inimigo
			drawPlayer(canvas);
			drawEnemy(canvas);
			
			//detecta colisão
			checkCollision(canvas);
			
			if(gameover) {
				stopGame(canvas);
				drawScore(canvas);
				drawButtons(canvas);
				holder.unlockCanvasAndPost(canvas);
				break;
			}
			
			//atualiza placar
			drawScore(canvas);
			
			drawButtons(canvas);
			
			//atualiza e libera o canvas			
			holder.unlockCanvasAndPost(canvas);
		}
		
	}
	
	public void init() {
		enemyRadius = 5;
		playerY = 100;		
		gameover = false;
		score = 0;
		resume();
	}
	
	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	
	private void drawPlayer(Canvas canvas) {
		paint.setColor(Color.GREEN);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nave), playerX, playerY, null);
//		canvas.drawCircle(playerX, playerY, 10, paint);
	}
	
	private void drawEnemy(Canvas canvas) {
		paint.setColor(Color.RED);
		enemyRadius++;
		canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
	}
	
	private void drawScore(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		canvas.drawText(String.valueOf(score), 20, 70, paint);
	}
	
	private void drawButtons(Canvas canvas) {
		//Restart
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(20);
		canvas.drawText("Restart", 10, 250, paint);
		
		//Exit
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(20);
		canvas.drawText("Exit", 195, 250, paint);
	}
	
	private void stopGame(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.LTGRAY);
		paint.setTextSize(40);
		canvas.drawText("GAME OVER!", 5, 120, paint);
		
	}
	
	public void moveDown(int pixels) {
		playerY += pixels;
	}
	
	private void checkCollision(Canvas canvas) {
		// calcula a hipotenusa
		distance = Math.pow(playerY - enemyY, 2) 
				+ Math.pow(playerX - enemyX, 2);
		distance = Math.sqrt(distance);
		
		//verifica a distância entre os raios
		if(distance <= playerRadius + enemyRadius) {
			gameover = true;
		}
	}
	
	public void addScore(int points){
		score += points;
	}

}
