import java.awt.Rectangle;


public class Ball {
	private int x;
	private int y;
	private int radius;
	private int x_speed;
	private double y_speed;
	private double maxspeed;
	private Rectangle hitzone;
	
	public Ball(double maxspeed, int speedX){
		x = 285;
		y = 375;
		radius = 15;
		
		x_speed = speedX;
		y_speed = maxspeed;
		
		hitzone = new Rectangle(x,y,radius*2,radius*2);
		
	}
	
	public void update(){
		x += x_speed;
		y += y_speed;
		hitzone.setLocation(x, y);
	}
	
	
	//Getters and Setters
	
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

	public int getRadius() {
		return radius;
	}

	public int getX_speed() {
		return x_speed;
	}

	public double getY_speed() {
		return y_speed;
	}

	public double getMaxspeed() {
		return maxspeed;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void setX_speed(int x_speed) {
		this.x_speed = x_speed;
	}

	public void setY_speed(double y_speed) {
		this.y_speed = y_speed;
	}

	public void setMaxspeed(int maxspeed) {
		this.maxspeed = maxspeed;
	}
	
}
