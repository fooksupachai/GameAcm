import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GRect;
import java.awt.Font;
import acm.program.GraphicsProgram;


public class SnakeAcm extends GraphicsProgram implements ActionListener {

	public GOval food;

	public static GRect[] rects;

	private int snakeX, snakeY, snakeWidth, snakeHeight;

	boolean left = false;
	
	boolean right = true;
	boolean up = false;
	boolean down = false;

	public Timer timer = new Timer(80, this);

	private boolean isPlaying, isGameOver;
	private int score, previousScore;
	private GLabel scoreLabel;

	public void run() {
		
		score = 0;
		previousScore = 0;
		
		isGameOver = false;
		isPlaying = false;
		
		addBackground();
		
		randomFood();
		
		GLabel startLabel = new GLabel("Click Let's hunt to start game.", getWidth() / 2,
				(getHeight() / 2));
		startLabel.move(-startLabel.getAscent()-130, -startLabel.getHeight());
		startLabel.setFont("Comic Sans MS-24");
		startLabel.setColor(Color.BLACK);
		
		add(startLabel);
		
		GOval textStart = new GOval((getWidth() / 2) - 30,(getHeight() / 2) + 20,100,50);
		textStart.setColor(Color.BLACK);
		textStart.setFilled(true);
		add(textStart);
		
		GLabel textStart2 = new GLabel("Let's hunt !",520,313);
		textStart2.move(-textStart2.getAscent()-140, -textStart2.getAscent()-4);
		textStart2.setColor(Color.WHITE);
		textStart2.setFont("Comic Sans MS");
		add(textStart2);
		
		GLabel textStart3 = new GLabel("Hunter Snake",500,313);
		textStart3.move(-textStart3.getAscent()-400, -textStart3.getAscent()-155);
		textStart3.setColor(Color.BLACK);
		textStart3.setFont("Rockwell-100");
		add(textStart3);
		
		GRect line1 = new GRect(70,50,645,10);
		line1.setFilled(true);
		line1.setColor(Color.BLACK);
		add(line1);
		
		GRect line2 = new GRect(70,170,645,10);
		line2.setFilled(true);
		line2.setColor(Color.BLACK);
		add(line2);
		
		GRect line3 = new GRect(70,60,10,110);
		line3.setFilled(true);
		line3.setColor(Color.BLACK);
		add(line3);
		
		GRect line4 = new GRect(705,60,10,110);
		line4.setFilled(true);
		line4.setColor(Color.BLACK);
		add(line4);




		
		scoreLabel = new GLabel("Yours Score : " + score, getWidth() - 425, 30);
		scoreLabel.move(-scoreLabel.getWidth() / 2, -scoreLabel.getHeight()+20);
		scoreLabel.setColor(Color.BLACK);
		scoreLabel.setFont("Helvetica-24");
		
		
		
		

		
		waitForClick();
		
		isPlaying = true;
		remove(startLabel);
		remove(textStart);
		remove(textStart2);
		remove(textStart3);
		remove(line1);
		remove(line2);
		remove(line3);
		remove(line4);
		add(scoreLabel);
		add(food);
		
		timer.start();
		
		drawSnake();
		
		addKeyListeners();

	}

	
	public GRect background;

	private void addBackground() {
		background = new GRect(0, 0, getWidth(), getHeight());
		background.setFilled(true);
		background.setColor(Color.WHITE);
		add(background);
	}
	

	private void randomFood() {
		Random random = new Random();
		int maxX = getWidth() - 15;
		int maxY = getHeight() - 15;
		int min = 0;
		
		int pointX = random.nextInt((maxX - min) + 1) + min;
		int pointY = random.nextInt((maxY - min) + 1) + min;
		
		food = new GOval(pointX, pointY, 15, 15);
		food.setFilled(true);
		food.setColor(Color.RED);
		add(food);
		
		
	}

	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			
			up = true;
			down = false;
			left = false;
			right = false;
			break;

		case KeyEvent.VK_DOWN:
			
			up = false;
			down = true;
			left = false;
			right = false;
			break;

		case KeyEvent.VK_LEFT:
			
			up = false;
			down = false;
			left = true;
			right = false;
			break;

		case KeyEvent.VK_RIGHT:
			
			up = false;
			down = false;
			left = false;
			right = true;
			break;

		}
	}

	public void drawSnake() {
		
		rects = new GRect[10];
		
		snakeY = 0;
		
		snakeWidth = 15;
		snakeHeight = 15;
		
		snakeX = rects.length * snakeWidth;
		
		for (int i = 0; i < rects.length; i++) {
			rects[i] = new GRect(snakeX, snakeY, snakeWidth, snakeHeight);
			rects[i].setFilled(true);
			rects[i].setColor(Color.PINK);
		
			snakeX -= snakeWidth;
		}
		
		for (int i = 0; i < rects.length; i++) {
			add(rects[i]);
		}
	}

	public void redrawSnake() {
		
		for (int i = rects.length - 1; i > 0; i--) {
			rects[i].setLocation(rects[i - 1].getX(), rects[i - 1].getY());
		}
		for (int i = 0; i < rects.length; i++) {
			add(rects[i]);
		}
	}

	public void growSnake() {
		
		ArrayList<GRect> arr = new ArrayList<GRect>();
		for (int i = 0; i < rects.length; i++) {
			arr.add(rects[i]);
		}
		
		GRect rect = new GRect(rects[rects.length - 1].getX() - snakeWidth,
				rects[rects.length - 1].getY() - snakeHeight, snakeWidth, snakeHeight);
		rect.setFilled(true);
		rect.setColor(Color.PINK);
		arr.add(rect);
		
		rects = arr.toArray(new GRect[arr.size()]);
		redrawSnake();
	}

	public void moveUp() {
		
		redrawSnake();
		
		if ((rects[0].getX() + snakeWidth >= 0 && rects[0].getX() + snakeWidth <= getWidth())
				&& rects[0].getY() + snakeWidth >= 0 && rects[0].getY() + snakeWidth <= getHeight()) {
			rects[0].setLocation(rects[0].getX(), rects[0].getY() - snakeWidth);
		} else {
			isGameOver = true;
		}
	}

	public void moveDown() {
		
		redrawSnake();
		
		if ((rects[0].getX() + snakeWidth >= 0 && rects[0].getX() + snakeWidth <= getWidth())
				&& rects[0].getY() + snakeWidth >= 0 && rects[0].getY() + snakeWidth <= getHeight()) {
			rects[0].setLocation(rects[0].getX(), rects[0].getY() + snakeWidth);
		} else {
			isGameOver = true;
		}
	}

	public void moveLeft() {
		
		redrawSnake();
		
		if ((rects[0].getX() + snakeWidth >= 0 && rects[0].getX() + snakeWidth <= getWidth())
				&& rects[0].getY() + snakeWidth >= 0 && rects[0].getY() + snakeWidth <= getHeight()) {
			rects[0].setLocation(rects[0].getX() - snakeWidth, rects[0].getY());
		} else {
			isGameOver = true;
		}
	}

	public void moveRight() {
		
		redrawSnake();
		
		if ((rects[0].getX() + snakeWidth >= 0 && rects[0].getX() + snakeWidth <= getWidth())
				&& rects[0].getY() + snakeWidth >= 0 && rects[0].getY() + snakeWidth <= getHeight()) {
			rects[0].setLocation(rects[0].getX() + snakeWidth, rects[0].getY());
		} else {
			isGameOver = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (!isGameOver) {
			
			scoreLabel.setLabel("Yours Score : " + score);
			
			if (down) {
				moveDown();
			}
			
			if (up) {
				moveUp();
			}
			
			if (right) {
				moveRight();
			}
			
			if (left) {
				moveLeft();
			}
			
			previousScore = score;
			
			if (intersectsWithFood()) {
				score += 10;
				growSnake();
				remove(food);
				randomFood();
			}
			
			if (intersectsWithSnake() && score - previousScore == 0) {
				isGameOver = true;
			}
		} else {
			
			timer.stop();
			
			GLabel gameOverLabel = new GLabel("Game Over!", getWidth() / 2, (getHeight() / 2));
			gameOverLabel.move(-gameOverLabel.getAscent()-240, -gameOverLabel.getAscent()+30);
			gameOverLabel.setColor(Color.BLACK);
			gameOverLabel.setFont("Helvetica-100");
			
			add(gameOverLabel);
		}
	}

	private boolean intersectsWithFood() {
		
		if (food.getBounds().intersects(rects[0].getBounds())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean intersectsWithSnake() {
		GRect head = null;
		
		if ((!left && !down && !up && !right) || right) {
			head = new GRect(rects[0].getX() + 15, rects[0].getY() + 5, 5, 5);
		} else if (left) {
			head = new GRect(rects[0].getX() - 5, rects[0].getY() + 5, 5, 5);
		} else if (down) {
			head = new GRect(rects[0].getX() + 5, rects[0].getY() + 15, 5, 5);
		} else if (up) {
			head = new GRect(rects[0].getX() + 5, rects[0].getY() - 5, 5, 5);
		}
		
		for (int i = 1; i < rects.length; i++) {
			if (head.getBounds().intersects(rects[i].getBounds())) {
				return true;
			}
		}
		
		return false;
	}

	public static void main(String[] args) {
		new SnakeAcm().start();
	}
}