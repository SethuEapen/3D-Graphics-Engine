package gameEngine;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;



public class Game extends Canvas implements Runnable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3997796135074783199L;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int FRAME_WIDTH = (int) screenSize.getWidth();
	public static int FRAME_HEIGHT = (int) screenSize.getHeight(); 
	
	public static int monitorX = 1920/2;
	public static int monitorY = 1080/2;
	public static double playerX = 0;
	public static double playerY = 0;
	public static double playerZ = 0;
	public static double PvelX = 0;
	public static double PvelY = 0;
	public static double PvelZ = 0;
	public static double Pyaw = 0;
	public static double Ppitch = 0;
	public static double PyawVel = 0;
	public static double PpitchVel = 0;
	public static int FOV = 120;
	public static double FPS = 120.0;
	public static double scaling = FPS/30;
	public static double sprint = 1;//(Game.FPS/Game.scaling);
	public static boolean paused = false;
	public static boolean dontRender = false;
	
	int centerX = Game.FRAME_WIDTH/2;
	int centerY = Game.FRAME_HEIGHT/2;

	static Light[] lights = new Light[5];
	
	private Thread thread;
	private boolean running = false;
	private Handler handler;

	
	public Game() {
		Window window = new Window(FRAME_WIDTH, FRAME_HEIGHT, "BlockSim", this);
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler, window));
		
		MouseInput mouseInput = new MouseInput(handler, window);
		this.addMouseMotionListener(mouseInput);
		
		//add lights
		lights[0] = new Light(-100,1000,-400, 10);
		handler.addObject(new Dot(-100, 1000, -400, Color.red));

		//handler.addObject(new Box(50, 50, 50));
		//handler.addObject(new Box(400, 200, 15, 70));
		drawStuff();
		//handler.addObject(new Triangle(100, 200, 10, 300, 100, 30, 200, 40, 1000, Color.red));
		//handler.addObject(new Point(FRAME_WIDTH/2, FRAME_HEIGHT/2, 300, Color.red));
		//handler.addObject(new Point(FRAME_WIDTH/2-100, FRAME_HEIGHT/2, 300, Color.red));
		//handler.addObject(new Point(FRAME_WIDTH/2-100, FRAME_HEIGHT/2-100, 300, Color.red));
		//handler.addObject(new Point(FRAME_WIDTH/2, FRAME_HEIGHT/2-100, 300, Color.red));
		//new Box(0,0,0,700,1000,700, Color.blue, handler);
		new Box(200, 300, 100, 400, 300, 200, Color.blue, handler);

		//handler.addObject(new Dot(200,300, 100+300, Color.green));
		//handler.addObject(new Dot(150,250, 50+300, Color.green));

		
		//handler.addObject(new Box(100, 0, 1000));
	}
	
	public void drawStuff() {
		for(int x = 0; x < 300; x = x + 50) {
			for(int z = 0; z < 1000; z = z + 50) {
				for(int y = 0; y < 500; y = y + 50) {
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
		double amountOfTicks = FPS;
		double ns = 1000000000 /amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		//int frames = 0;
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
			//frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				//frames = 0;
			}
		}
		stop();
	}

	private void tick() {//send tick to handler
		handler.tick();
	}
	
	private void render() {
		if(!dontRender) {
			BufferStrategy bs = this.getBufferStrategy();
			if(bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			
			Graphics g = bs.getDrawGraphics();
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
			
			handler.render(g);
			
			g.dispose();
			bs.show();
		}
	}
	
	
	public static void main(String[] args) {
		new Game();
	}
}
