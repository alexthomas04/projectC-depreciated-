package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

public class Player extends Entity {

	public final static String TYPE="rock";
	public Player(int identification, int x, int y, Chunk c, World w) {
		super(identification, x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	public Player(int identification, int x, int y, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, x, y, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	public Player(int identification, Chunk c, World w, String jsonString) {
		super(identification, c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	public Player(int identification, Chunk c, World w, JSONObject json) {
		super(identification, c, w, json);
		// TODO Auto-generated constructor stub
	}

	public Player(int identification, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, c, w, attributes);
		// TODO Auto-generated constructor stub
	}
	
	
	public static void staticLoad() {
		EntityTypeManager.storeType(TYPE, RockWithLegs.class);

	}
	
	/**
	 * Gets the standard.
	 *
	 * @return the standard Hashtable of stats
	 */
	public static Hashtable<String, String> getStandard() {
		Hashtable<String,String> table= new Hashtable<String,String>();
		return table;
	}
	
	
	
	public String handleCommand(String[] command){
		if(command[0].equalsIgnoreCase("move")){
			return move(Arrays.copyOfRange(command, 1, command.length));
		}
		else if(command[0].equalsIgnoreCase("look")){
			return look(Arrays.copyOfRange(command, 1, command.length));
		}
		return "Comamnd did not execute";
	}
	
	public String move(String[] command){
		if(command.length<1)
			return "Not enough arguments";
		if(command[0].equalsIgnoreCase("left")){
			if(chunk.getSizeX()>locX+1){
				move(locX+1,locY);
				return "You are at ("+locX+", "+locY+")."+ ((chunk.getSizeX()-1==locX)? "You are at the left edge." : "");
			}
			else
				return "You are already at the left edge";
		}
			
		else if(command[0].equalsIgnoreCase("right"))
		{
			if(0<=locX-1){
				move(locX-1,locY);
				return "You are at ("+locX+", "+locY+")."+ ((0==locX)? "You are at the right edge." : "");
			}
			else
				return "You are already at the right edge";
		}
		else if(command[0].equalsIgnoreCase("down"))
		{
			if(chunk.getSizeY()-1>locY+1){
				move(locX,locY+1);
				return "You are at ("+locX+", "+locY+")."+ ((chunk.getSizeY()-1==locY)? "You are at the left edge." : "");
			}
			else
				return "You are already at the left edge";
		}
		else if(command[0].equalsIgnoreCase("up"))
		{
			if(0<=locY-1){
				move(locX,locY-1);
				return "You are at ("+locX+", "+locY+")."+ ((0==locY)? "You are at the left edge." : "");
			}
			else
				return "You are already at the left edge";
		}
		return "Comamnd did not execute";
	}
	
	public String look(String[] command){
		if(command.length<1)
			return "Not enough arguments";
		if(command[0].equalsIgnoreCase("around")){
			String data = "";
			ArrayList<Entity> entities;
			if(chunk.getSizeX()>locX+1){//check left
				entities=chunk.getEntities(locX+1, locY);
				for(Entity e : entities)
					data+=e.getType() + " , " + e.getId() + ". At "+(locX+1)+", " +locY+"\n";
					
			}
			if(0<=locX-1){//check right
				entities=chunk.getEntities(locX-1, locY);
				for(Entity e : entities)
					data+=e.getType() + " , " + e.getId() + ". At "+(locX-1)+", " +locY+"\n";
					
			}
			if(0<=locY-1){//check up
				entities=chunk.getEntities(locX, locY-1);
				for(Entity e : entities)
					data+=e.getType() + " , " + e.getId() + ". At "+(locX)+", " +(locY-1)+"\n";
					
			}
			if(chunk.getSizeY()>locY+1){//check down
				entities=chunk.getEntities(locX, locY+1);
				for(Entity e : entities)
					data+=e.getType() + " , " + e.getId() + ". At "+(locX)+", " +(locY+1)+"\n";
					
			}
			return "You found " + ((data.isEmpty())?"nothing":data);
		}
		else if(command[0].equalsIgnoreCase("down"))
		{
			String data = "";
			ArrayList<Entity> entities;
			entities=chunk.getEntities(locX, locY);
			for(Entity e : entities)
				data+=e.getType() + " , " + e.getId() + ". At "+(locX)+", " +(locY+1)+"\n";
			return "You found " + ((data.isEmpty())?"nothing":data);
		}
		return "Command did not execute";
	}
	
	
	@Override
	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("type", TYPE);
		return json;
	}
	public String getType(){
		return "player";
	}

}
