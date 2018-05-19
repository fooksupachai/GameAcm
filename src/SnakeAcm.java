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
//goo
	public GOval apple;
	public static GRect[] bodyRect;
	private int bodySnakeX, bodySnakeY, bodySnakeWidth, bodySnakeHeight;
	boolean left = false;
	boolean right = true;
	boolean up = false;
	boolean down = false;
	// Speed of snake
	public Timer timer = new Timer(500, this);

	private boolean openGame, showGameOver;
	private int score, oldScore;
	private GLabel textScore;

	// Numbers of rocks
	ArrayList<GRect> wall = new ArrayList<GRect>();

	public void run() {

		score = 0;
		oldScore = 0;

		showGameOver = false;
		openGame = false;

		addBackground();

		GLabel welcomeText = new GLabel("Click Let's hunt to start game.", getWidth() / 2, (getHeight() / 2));
		welcomeText.move(-welcomeText.getAscent() - 130, -welcomeText.getHeight());
		welcomeText.setFont("Comic Sans MS-24");
		welcomeText.setColor(Color.BLACK);
		add(welcomeText);

		GOval textStart = new GOval((getWidth() / 2) - 30, (getHeight() / 2) + 20, 100, 50);
		textStart.setColor(Color.BLACK);
		textStart.setFilled(true);
		add(textStart);

		GLabel textStart2 = new GLabel("Let's hunt !", 520, 313);
		textStart2.move(-textStart2.getAscent() - 140, -textStart2.getAscent() - 4);
		textStart2.setColor(Color.WHITE);
		textStart2.setFont("Comic Sans MS");
		add(textStart2);

		GLabel textStart3 = new GLabel("Hunter Snake", 500, 313);
		textStart3.move(-textStart3.getAscent() - 400, -textStart3.getAscent() - 155);
		textStart3.setColor(Color.BLACK);
		textStart3.setFont("Rockwell-100");
		add(textStart3);

		GLabel textStart4 = new GLabel("Do not hit the wall.!!", 500, 313);
		textStart4.move(-textStart3.getAscent() - 320, -textStart3.getAscent() + 185);
		textStart4.setColor(Color.RED);
		textStart4.setFont("Rockwell-70");
		add(textStart4);

		GRect line1 = new GRect(70, 50, 645, 10);
		line1.setFilled(true);
		line1.setColor(Color.BLACK);
		add(line1);

		GRect line2 = new GRect(70, 170, 645, 10);
		line2.setFilled(true);
		line2.setColor(Color.BLACK);
		add(line2);

		GRect line3 = new GRect(70, 60, 10, 110);
		line3.setFilled(true);
		line3.setColor(Color.BLACK);
		add(line3);

		GRect line4 = new GRect(705, 60, 10, 110);
		line4.setFilled(true);
		line4.setColor(Color.BLACK);
		add(line4);

		textScore = new GLabel("Yours Score : " + score, getWidth() - 425, 30);
		textScore.move(-textScore.getWidth() / 2, -textScore.getHeight() + 20);
		textScore.setColor(Color.BLACK);
		textScore.setFont("Helvetica-24");

		waitForClick();

		openGame = true;
		remove(welcomeText);
		remove(textStart);
		remove(textStart2);
		remove(textStart3);
		remove(textStart4);
		remove(line1);
		remove(line2);
		remove(line3);
		remove(line4);
		add(textScore);
		wall.add(new GRect(20, 60, 50, 60));
		wall.add(new GRect(50, 190, 70, 60));
		wall.add(new GRect(220, 60, 260, 60));
		wall.add(new GRect(620, 60, 10, 320));
		wall.add(new GRect(150, 300, 60, 60));
		wall.add(new GRect(300, 400, 60, 60));
		wall.add(new GRect(450, 420, 60, 60));
		wall.add(new GRect(700, 60, 10, 320));
		for (int i = 0; i < 8; i++) {
			wall.get(i).setFilled(true);
			wall.get(i).setColor(Color.black);
			add(wall.get(i));
		}

		randomFood();
		timer.start();
		bodySnakesBlocksRects();
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

		apple = new GOval(pointX, pointY, 15, 15);
		apple.setFilled(true);
		apple.setColor(Color.RED);
		add(apple);

		boolean isFoodInRock = false;
		for (int i = 0; i < wall.size(); i++) {
			if (wall.get(i).getBounds().intersects(apple.getBounds())) {
				isFoodInRock = true;
			}
		}
		if (isFoodInRock) {
			remove(apple);
			randomFood();
		}

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

	public void bodySnakesBlocksRects() {

		bodyRect = new GRect[5];
		bodySnakeY = 0;
		bodySnakeWidth = 15;
		bodySnakeHeight = 15;
		bodySnakeX = bodyRect.length * bodySnakeWidth;

		for (int i = 0; i < bodyRect.length; i++) {
			bodyRect[i] = new GRect(bodySnakeX, bodySnakeY, bodySnakeWidth, bodySnakeHeight);
			bodyRect[i].setFilled(true);
			bodyRect[i].setColor(Color.PINK);

			bodySnakeX -= bodySnakeWidth;
		}

		for (int i = 0; i < bodyRect.length; i++) {
			add(bodyRect[i]);
		}
	}

	public void bodySnakesBlocksRects2() {

		for (int i = bodyRect.length - 1; i > 0; i--) {
			bodyRect[i].setLocation(bodyRect[i - 1].getX(), bodyRect[i - 1].getY());
		}
		for (int i = 0; i < bodyRect.length; i++) {
			add(bodyRect[i]);
		}
	}

	public void growUpSnake() {

		ArrayList<GRect> arr = new ArrayList<GRect>();
		for (int i = 0; i < bodyRect.length; i++) {
			arr.add(bodyRect[i]);
		}

		GRect rect = new GRect(bodyRect[bodyRect.length - 1].getX() - bodySnakeWidth,
				bodyRect[bodyRect.length - 1].getY() - bodySnakeHeight, bodySnakeWidth, bodySnakeHeight);
		rect.setFilled(true);
		rect.setColor(Color.PINK);
		arr.add(rect);

		bodyRect = arr.toArray(new GRect[arr.size()]);
		bodySnakesBlocksRects2();
	}

	public void moveUp() {

		bodySnakesBlocksRects2();

		if ((bodyRect[0].getX() + bodySnakeWidth >= 0 && bodyRect[0].getX() + bodySnakeWidth <= getWidth())
				&& bodyRect[0].getY() + bodySnakeWidth >= 0 && bodyRect[0].getY() + bodySnakeWidth <= getHeight()) {
			bodyRect[0].setLocation(bodyRect[0].getX(), bodyRect[0].getY() - bodySnakeWidth);
		} else {
			showGameOver = true;
		}
	}

	public void moveDown() {

		bodySnakesBlocksRects2();

		if ((bodyRect[0].getX() + bodySnakeWidth >= 0 && bodyRect[0].getX() + bodySnakeWidth <= getWidth())
				&& bodyRect[0].getY() + bodySnakeWidth >= 0 && bodyRect[0].getY() + bodySnakeWidth <= getHeight()) {
			bodyRect[0].setLocation(bodyRect[0].getX(), bodyRect[0].getY() + bodySnakeWidth);
		} else {
			showGameOver = true;
		}
	}

	public void moveLeft() {

		bodySnakesBlocksRects2();

		if ((bodyRect[0].getX() + bodySnakeWidth >= 0 && bodyRect[0].getX() + bodySnakeWidth <= getWidth())
				&& bodyRect[0].getY() + bodySnakeWidth >= 0 && bodyRect[0].getY() + bodySnakeWidth <= getHeight()) {
			bodyRect[0].setLocation(bodyRect[0].getX() - bodySnakeWidth, bodyRect[0].getY());
		} else {
			showGameOver = true;
		}
	}

	public void moveRight() {

		bodySnakesBlocksRects2();

		if ((bodyRect[0].getX() + bodySnakeWidth >= 0 && bodyRect[0].getX() + bodySnakeWidth <= getWidth())
				&& bodyRect[0].getY() + bodySnakeWidth >= 0 && bodyRect[0].getY() + bodySnakeWidth <= getHeight()) {
			bodyRect[0].setLocation(bodyRect[0].getX() + bodySnakeWidth, bodyRect[0].getY());
		} else {
			showGameOver = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (!showGameOver) {

			textScore.setLabel("Yours Score : " + score);

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

			oldScore = score;

			if (eatFood()) {
				score += 1;
				growUpSnake();
				remove(apple);
				randomFood();
			}

			if ((hitSelfSnake() && score - oldScore == 0) || hitWall()) {
				showGameOver = true;
			}
		} else {

			timer.stop();

			GLabel gameOverLabel = new GLabel("Game Over!", getWidth() / 2, (getHeight() / 2));
			gameOverLabel.move(-gameOverLabel.getAscent() - 240, -gameOverLabel.getAscent() + 30);
			gameOverLabel.setColor(Color.BLACK);
			gameOverLabel.setFont("Helvetica-100");
			add(gameOverLabel);
		}
	}

	private boolean eatFood() {

		if (apple.getBounds().intersects(bodyRect[0].getBounds())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean hitWall() {
		for (int i = 0; i < wall.size(); i++) {
			if (wall.get(i).getBounds().intersects(bodyRect[0].getBounds())) {
				System.out.println(i + " hit.");
				return true;
			}
		}
		return false;
	}

	private boolean hitSelfSnake() {
		GRect head = null;

		if ((!left && !down && !up && !right) || right) {
			head = new GRect(bodyRect[0].getX() + 15, bodyRect[0].getY() + 5, 5, 5);
		} else if (left) {
			head = new GRect(bodyRect[0].getX() - 5, bodyRect[0].getY() + 5, 5, 5);
		} else if (down) {
			head = new GRect(bodyRect[0].getX() + 5, bodyRect[0].getY() + 15, 5, 5);
		} else if (up) {
			head = new GRect(bodyRect[0].getX() + 5, bodyRect[0].getY() - 5, 5, 5);
		}

		for (int i = 1; i < bodyRect.length; i++) {
			if (head.getBounds().intersects(bodyRect[i].getBounds())) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		new SnakeAcm().start();
	}
}
