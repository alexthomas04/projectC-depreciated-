package game;

import java.util.Hashtable;

import communication.Server;
import entities.GenericPlant;
import entities.Player;
import entities.Rock;
import entities.RockWithLegs;
import world.Chunk;
import world.World;

// TODO: Auto-generated Javadoc
/**
 * The Class GameManager.
 */
public class GameManager {

		/** The world. */
		private World world;
	
	/**
	 * Instantiates a new game manager.
	 */
	public GameManager() {
		world = new World();
	}
	
	/**
	 * Run.
	 */
	public void run(){
		GenericPlant.staticLoad();
		Player.staticLoad();
		Rock.staticLoad();
		RockWithLegs.staticLoad();
		if(!world.loadChunks("world/")){
			world=new World(1);
		}
		else{
		world.loadEntities("world/");
		world.loadPlayers("world/");
		}
		world.save("world/");
		CommandHandler ch = new CommandHandler();
		ch.start();
		AutoSave as = new AutoSave();
		as.start();
		Ticker t = new Ticker();
		t.start();
	}
	
	
	
	
	
	
	
	/**
	 * The Class CommandHandler.
	 */
	private class CommandHandler extends Thread{
		
		/** The server. */
		Server server = new Server();
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public  void run(){
			while(true){
				String message = server.getMessage();
				String[] parts = message.split("\\|"); //{s,c} {guid} {add,execute,other} {name} {command}
				for(int i=0;i<parts.length;i++){
					parts[i]=parts[i].trim();
				}
				if(parts[2].equalsIgnoreCase("add")){
					if(world.hasPlayer(parts[3])){
						message = "s"+"|"+parts[1]+"|"+"other"+"|"+"Player already Exists";
						server.sendMessage(message);
						continue;
					}
					else{
						Hashtable<String,String> attr =Player.getStandard();
						attr.put("name", parts[3]);
						attr.put("locationX", "0");
						attr.put("locationY","0");
						Player p = new Player(world.getPlayers().size(),world.getChunk(0),world,attr);
						world.addPlayer(p);
						message = "s"+"|"+parts[1]+"|"+"other"+"|"+parts[3]+" added at 0,0";
						server.sendMessage(message);
						continue;
					}
				}
				else if(parts[2].equalsIgnoreCase("execute")){
					if(world.hasPlayer(parts[3])){
						message=world.getPlayer(parts[3]).handleCommand(parts[4]);
						message =  "s"+"|"+parts[1]+"|"+"other"+"|"+message;
						server.sendMessage(message);
						continue;
					}
					else{
						message =  "s"+"|"+parts[1]+"|"+"other"+"|"+"Player does not exist in world";
						server.sendMessage(message);
						continue;
					}
				}
			
			}
		}
	}

	/**
	 * The Class AutoSave.
	 */
	private class AutoSave extends Thread{
		
		/** The interval. */
		private long interval=60000;
		
		/**
		 * Instantiates a new auto save.
		 *
		 * @param i the i
		 */
		public AutoSave(long i){
			super();
			interval =i;
		}
		
		/**
		 * Instantiates a new auto save.
		 */
		public AutoSave(){
			super();
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run(){
			while(true){
				try {
					Thread.sleep(interval);
					world.save("world/");
					System.out.println("Auto Saved world");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

	/**
	 * The Class Ticker.
	 */
	private class Ticker extends Thread{
		
		/** The interval. */
		private long interval=60000;
		
		/**
		 * Instantiates a new ticker.
		 */
		public Ticker(){
			super();
		}
		
		/**
		 * Instantiates a new ticker.
		 *
		 * @param l the l
		 */
		public Ticker(long l){
			interval=l;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run(){
			while(true){
				try {
					Thread.sleep(interval);
					world.tick();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		}
	}
}
