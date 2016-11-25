import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

/*	[X] Ball erstellen und grafisch zeichnen
 * 	[X] Schieber erstellen und grafisch zeichnen
 * 	[X] Hitboxen für Schieber erstellen
 * 	[] Bufferstrategie ändern (flackern nervt)
 * 	[X] KeyListener für beide Schieber schalten
 *  [X] Rectangle Objekt für Ball erstellen
 * 	[X] Kollisionsüberprüfung zwischen Ball und Schiebern
 * 	[X] Lebensanzeige beider Player
 * 	[X] Geschwindigkeit vom Ball in Y-Richtung bei jeder Kollision leicht erhöhen
 * 	[X] Mittellinie zeichnen
 * 	[X] Credits nach dem Spiel anzeigen
 * 	[X] gleichzeitiges erkennen von 2 Tasten ermöglichen
 * 	[X] Kollision zwischen Schiebern und Bounds
 *  [] restart Game möglichkeit hinzufügen
 *  [] Künstliche intelligenz für player 2
 *  [] ein Menu zu Beginn um auszuwählen ob man alleine gegen die KI oder zu zweit gegen einen Freund spielt
 *  [X] Sounds einfügen für kollision zwischen ball und bande/Schieber, für ein Tor, für Spielende
 */

public class Pong extends Canvas implements Runnable, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JFrame window;
	public Ball ball;
	public static Schieber player1, player2;
	public boolean gameLoopOn, gameOver;
	public double schwierigkeitsgrad;
	private Random rnd;
	private AudioClip hitPlayer, hitBounds, goal; 
	
	
	Pong(){
		//instanziert das Spielfenster und richtet es ein
		window = new JFrame();
		window.setSize(600, 800);
		window.setVisible(true);
		window.setTitle("Pong");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.requestFocus();
		
		loadGameElements();
		loadSounds();
	}
	
	public void start(){
		Thread thread = new Thread(this);
		thread.start();
	}
	

	
	
	public void paint(Graphics g){
		
		// Hintergrund zeichnen
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//Mittellinie zeichnen
		g.setColor(Color.white);
		g.drawLine(0, getHeight()/2, 600, getHeight()/2);
		
		//Ball zeichnen
		g.setColor(Color.yellow);
		g.fillOval(ball.getX(), ball.getY(), ball.getRadius()*2, ball.getRadius()*2);
		
		//Schieber zeichnen
		g.setColor(Color.white);
		g.fillRect(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight());
		g.fillRect(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight());
		
		//Hitzonen der Schieber zeichnen
		//g.setColor(Color.red);
		//g.drawRect(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight());
		//g.drawRect(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight());
		
		//Hitzone vom Ball zeichnen
		//g.setColor(Color.red);
		//g.drawOval(ball.getX(), ball.getY(), ball.getRadius()*2, ball.getRadius()*2);
		
		//Lebensanzeige der beiden Spieler im jeweiligen Schieber
		g.setColor(Color.BLUE);
		g.drawString(""+player1.getLifes(), player1.getX()+40, player1.getY()+10);
		
		g.setColor(Color.BLUE);
		g.drawString(""+player2.getLifes(), player2.getX()+40, player2.getY()+10);
		
		//sagt bei vollständigem Verlust aller Leben an wer verloren hat
		if(gameOver){
			//zeichnet den Hintergrund rot
			g.setColor(Color.red);
			g.fillRect(100, 200, 400, 400);
			
			g.getFont().deriveFont(3, 100);
			g.setColor(Color.black);
			g.drawString("Game Over", 300, 300);
			if(player1.getLifes() == 0)
				g.drawString("Player 1 loose!", 300, 315);
			else if(player2.getLifes() == 0)
				g.drawString("Player 2 losse!", 300, 315);
			//zeichnet die Credits
			g.drawString("All Credits go to Thomas Kozsir", 300, 330);
		}
		
	}

	//Das ist der Gameloop, das Herz dieses Spiels
	@Override
	public void run() {
			
		while(gameLoopOn){
			
			ball.update();
			player1.update();
			player2.update();
			checkCollision();
			checkBounds();
			checkRoundEnds();
			render();
			repaint();
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
		
	public void loadGameElements(){
	  
				//fügt den KeyListener für Spielsteuerung ein
				addKeyListener(this);
				
				rnd = new Random();
				
				schwierigkeitsgrad = 5;
				gameOver = false;
				//turnOn = true;
				gameLoopOn = true;
				ball = new Ball(schwierigkeitsgrad, rnd.nextInt()%8);
				player1 = new Schieber("player1");
				player2 = new Schieber("player2");
	}
	
	public void loadSounds(){
		//initialisiert die Sounds	
			hitPlayer = Applet.newAudioClip(Pong.class.getResource("audio/hitPlayer.au"));
			hitBounds = Applet.newAudioClip(Pong.class.getResource("audio/hitBounds.au"));
			goal	  = Applet.newAudioClip(Pong.class.getResource("audio/goal.au"));
	}
	
	public void checkRoundEnds(){
		if(player1.getLifes() == 0 || player2.getLifes() == 0){
			//turnOn = false;
			gameLoopOn = false;
			gameOver = true;
		}
	}
	
	public void checkBounds(){
		//überprüft Ballkollision mit linker oder rechter Bande
		if(ball.getX() <= 0){
			ball.setX_speed(ball.getX_speed()*(-1));
			hitBounds.play();
		}else if(ball.getX()+(2*ball.getRadius()) >= 600){
				ball.setX_speed(ball.getX_speed()*(-1));
				hitBounds.play();
		}
			
		//überprüft ob der Ball das Spielfeld verlässt
		if(ball.getY() <= 0-2*ball.getRadius()){
			ball.setX(300);
			ball.setY(400);
			goal.play();
			player2.looseLife(1);
		}else if( ball.getY() >=800-2*ball.getRadius()){
				ball.setX(300);
				ball.setY(400);
				goal.play();
				player1.looseLife(1);
		}
		
		//überprüft ob player1 am Spielrand ist
		//linke Wand
		if(player1.getX() <= 0){
			player1.setSpeed(0);
			player1.setX(0);
		}
		//rechte Wand
		if(player1.getX()+player1.getWidth() >= 600){
			player1.setSpeed(0);
			player1.setX(600-player1.getWidth());
		}
		
		//überprüft ob player2 am Spielrand ist
		if(player2.getX() <= 0){
			player2.setSpeed(0);
			player2.setX(0);
		}
		
		if(player2.getX()+player2.getWidth() >= 600){
			player2.setSpeed(0);
			player2.setX(600-player2.getWidth());
		}
	}
	
	public void checkCollision(){
		if(player1.getHitzone().intersects(ball.getHitzone())){
			ball.setY_speed(-schwierigkeitsgrad);
			schwierigkeitsgrad += 0.2;
			//System.out.println("Schwierigkeit: "+schwierigkeitsgrad); //zeigt den, mit jedem Treffer steigenden, Schwierigkeitsgrad in der Console an
			ball.setX_speed(rnd.nextInt()%5);
			hitPlayer.play();
		}
		
		if(player2.getHitzone().intersects(ball.getHitzone())){
			ball.setY_speed(schwierigkeitsgrad);
			schwierigkeitsgrad += 0.2;
			//System.out.println("Schwierigkeit: "+schwierigkeitsgrad); 	//siehe eins oben bei player1
			ball.setX_speed(rnd.nextInt()%5);
			hitPlayer.play();
		}
	}
	

	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0, getWidth(), getHeight());		
		g.dispose();
		bs.show();
	}
	
	//KeyListener
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		
			case KeyEvent.VK_LEFT: 
				player1.moveLeft();
				player1.leftPressed = true;
				
				if(player2.leftPressed)
					player2.moveLeft();
				if(player2.rightPressed)
					player2.moveRight();
				break;
				
			case KeyEvent.VK_RIGHT:
				player1.moveRight();
				player1.rightPressed = true;
				
				if(player2.rightPressed)
					player2.moveRight();
				if(player2.leftPressed)
					player2.moveLeft();
				break;	
				
			case KeyEvent.VK_A: 
				player2.moveLeft();
				player2.leftPressed = true;
				
				if(player1.leftPressed)
					player1.moveLeft();
				if(player1.rightPressed)
					player1.moveRight();
				
				break;
				
			case KeyEvent.VK_D:
				player2.moveRight();
				player2.rightPressed = true;
				
				if(player1.leftPressed)
					player1.moveLeft();
				if(player1.rightPressed)
					player1.moveRight();
				break;
			
			case KeyEvent.VK_ENTER:
				
					
				
		}
	}
	
	

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		
		case KeyEvent.VK_LEFT: 
			player1.moveLeft();
			player1.leftPressed = false;
			break;
			
		case KeyEvent.VK_RIGHT:
			player1.moveRight();
			player1.rightPressed = false;
			break;	
			
		case KeyEvent.VK_A: 
			player2.moveLeft();
			player2.leftPressed = false;
			break;
			
		case KeyEvent.VK_D:
			player2.moveRight();
			player2.rightPressed = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static void main(String[] e){
		Pong game = new Pong();
		game.requestFocus();
		//Spielmenu menu = new Spielmenu();
		
		//window.add(menu);
		game.run();		//soll erst gestartet werden wenn im Menu starten angeklickt wird
		
	}
}
