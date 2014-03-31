package game;

import java.util.Hashtable;

import communication.Server;
import entities.GenericPlant;
import entities.Player;
import entities.Rock;
import entities.RockWithLegs;
import world.Chunk;
import world.World;

public class GameManager {

		private World world;
	public GameManager() {
		world = new World();
	}
	
	public void run(){
		GenericPlant.staticLoad();
		Player.staticLoad();
		Rock.staticLoad();
		RockWithLegs.staticLoad();
		if(!world.loadChunks("world/")){
			world=new World(2);
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
	}
	
	
	
	
	
	
	
	private class CommandHandler extends Thread{
		Server server = new Server();
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

	private class AutoSave extends Thread{
		private long interval=60000;
		public AutoSave(long i){
			super();
			interval =i;
		}
		public AutoSave(){
			super();
		}
		
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
}
