package gameEngine;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;



public class Game extends Canvas implements Runnable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3997796135074783199L;
	public static final int FRAME_WIDTH = 1800, FRAME_HEIGHT = 900; 
	
	public static int monitorX = 1920/2;
	public static int monitorY = 1080/2;
	public static int playerX = 0;
	public static int playerY = 0;
	public static int playerZ = 0;
	public static int PvelX = 0;
	public static int PvelY = 0;
	public static int PvelZ = 0;
	public static int Pyaw = 0;
	public static int Ppitch = 0;
	public static int PyawVel = 0;
	public static int PpitchVel = 0;
	public static int FOV = 120;
	public static boolean paused = false;
	
	int centerX = Game.FRAME_WIDTH/2;
	int centerY = Game.FRAME_HEIGHT/2;

	private Thread thread;
	private boolean running = false;
	private Handler handler;

	
	public Game() {
		Window window = new Window(FRAME_WIDTH, FRAME_HEIGHT, "BlockSim", this);
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		
		//MouseInput mouseInput = new MouseInput(handler, window);
		//this.addMouseMotionListener(mouseInput);
		//handler.addObject(new Box(50, 50, 50));
		//handler.addObject(new Box(400, 200, 15, 70));
		drawStuff();
		//handler.addObject(new Triangle(100, 200, 10, 300, 100, 30, 200, 40, 1000, Color.red));
		//handler.addObject(new Point(FRAME_WIDTH/2, FRAME_HEIGHT/2, 300, Color.red));
		//handler.addObject(new Point(FRAME_WIDTH/2-100, FRAME_HEIGHT/2, 300, Color.red));
		//handler.addObject(new Point(FRAME_WIDTH/2-100, FRAME_HEIGHT/2-100, 300, Color.red));
		//handler.addObject(new Point(FRAME_WIDTH/2, FRAME_HEIGHT/2-100, 300, Color.red));

		//new Box(200, 300, 100, 400, 300, 200, Color.blue, handler);
		//handler.addObject(new Box(100, 0, 1000));
	}
	
	public void drawStuff() {
		for(int x = 0; x < 500; x = x + 50) {
			for(int z = 0; z < 1000; z = z + 50) {
				for(int y = 0; y < 300; y = y + 50) {
					handler.addObject(new Dot(x, y, z, Color.red));
				}
			}
		}
	}
	
	public synchronized void start() {//on start
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {//on stop
		try {
			thread.join();//killing the thread
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {//on run
		long lastTime = System.nanoTime();
		double amountOfTicks = 30.0;
		double ns = 1000000000 /amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){//tick fucntion
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime =  now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {//send tick to handler
		handler.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	
	public static void main(String[] args) {
		new Game();
	}
}
