import java.awt.Rectangle;

public class Schieber {
	private int x, y;
	private int width, height;
	private int lifes;
	private Rectangle hitzone;
	private int speed;
	boolean rightPressed, leftPressed;

	public Schieber(String player){
		
		if(player.equals("player1"))
			y = 720;
			else if(player.equals("player2"))
				y = 20;	
		
		rightPressed = false;
		leftPressed = false;
		x = (600/2)-40;			// x ist bei Player1 und Player 2 gleich, da beide Schieber in der Mitte starten sollen
		width  = 80;
		height =  15;
		speed = 0;
		lifes = 5;
		hitzone = new Rectangle(x,y,width,height);	
	}
	
	public void update(){
		hitzone.setLocation(x, y);
	}
	
	public void looseLife(int life){
		lifes -= life;
	}
	
	public void moveLeft(){
		speed = 20;
		if(x == 0)
			x -= 2*speed;
		x -= speed;
		speed = 0;
	}
	
	public void moveRight(){
		speed = 20;
		if(x == 0)
			x += 2*speed;
		x += speed;
		speed = 0;
	}
	
	
	//Getters and Setters
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public Rectangle getHitzone() {
		return hitzone;
	}

	public void setHitzone(Rectangle hitzone) {
		this.hitzone = hitzone;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLifes() {
		return lifes;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	
	
	
	
}
