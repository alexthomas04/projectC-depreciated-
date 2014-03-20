package entities;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import json.JSONArray;
import json.JSONObject;
import world.Chunk;
import world.World;

public class Player extends Entity {

	public final static String TYPE="player";
	private ArrayList<Entity> inventory = new ArrayList<Entity>();
	private int food=10;
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
		EntityTypeManager.storeType(TYPE, Player.class);

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
		else if(command.length>=2 && command[0].equalsIgnoreCase("pick") && command[1].equalsIgnoreCase("up"))
			return pickUp(Arrays.copyOfRange(command, 2, command.length));
		else if(command[0].equalsIgnoreCase("inventory"))
			return inventory(Arrays.copyOfRange(command, 1, command.length));
		else if(command[0].equalsIgnoreCase("eat"))
			return eat(Arrays.copyOfRange(command, 1, command.length));
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
				return "You are at ("+locX+", "+locY+")."+ ((chunk.getSizeY()-1==locY)? "You are at the bottom edge." : "");
			}
			else
				return "You are already at the left edge";
		}
		else if(command[0].equalsIgnoreCase("up"))
		{
			if(0<=locY-1){
				move(locX,locY-1);
				return "You are at ("+locX+", "+locY+")."+ ((0==locY)? "You are at the top edge." : "");
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
			String data="";
			for(int i=vision*-1;i<=vision;i++){
				for(int k=vision*-1;k<=vision;k++){
					if(locX+i<chunk.getSizeX() && locY+k<chunk.getSizeY() && locX+i>=0 && locY+k>=0 ){//&& locY+k != locY && locX+i!=locX){
						for(Entity e : chunk.getEntities(locX+i, locY+k))
							data+=e.getType() + " , " + e.getId() + ". At "+(locX+i)+", " +(locY+k)+"\n";
					}
				}
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
		else if(command[0].equalsIgnoreCase("Up"))
			return "It's a bird, It's a plane, It's .... \n nope it just a cloud.";
		else if(command[0].equalsIgnoreCase("closely")){
			if(command.length<2)
				return "Not enough arguments";
			int searchId;
			try{
			 searchId = Integer.parseInt(command[1]);
			}catch(Exception ex){
				return "Not enough arguments";
			}
			for(int i=vision*-1;i<=vision;i++){
				for(int k=vision*-1;k<=vision;k++){
					if(locX+i<chunk.getSizeX() && locY+k<chunk.getSizeY() && locX+i>=0 && locY+k>=0 ){//&& locY+k != locY && locX+i!=locX){
						for(Entity e : chunk.getEntities(locX+i, locY+k))
						{
							if(e.getId()==searchId){
								String details="";
								Hashtable<String,String> attributes = e.getDetails();
								Enumeration<String> keys = attributes.keys();
								while(keys.hasMoreElements()){
									String key = keys.nextElement();
									details+=key+" = "+attributes.get(key)+"\n";
								}
								return details;
							}
								
						}
					}
				}
			}
			return "Could not see the entity";
			
		}
		return "Command did not execute";
		
	}
	
	public String pickUp(String[] command){
		if(command.length>=1){
			int searchId;
			try{
			 searchId = Integer.parseInt(command[0]);
			}catch(Exception ex){
				return "Not enough arguments";
			}
			ArrayList<Entity> entities = chunk.getEntities(locX, locY);
			for(Entity e : entities){
				if(e.getId()==searchId){
					chunk.removeEntity(e, locX, locY);
					world.removeEntity(e);
					inventory.add(e);
				}
					
			}
		return "";	
		}
		else{
			ArrayList<Entity> entities = chunk.getEntities(locX, locY);
			for(Entity e : entities){
					if(!(e instanceof Player)){
						chunk.removeEntity(e, locX, locY);
						world.removeEntity(e);
						inventory.add(e);
					}
			}
			return "";
		}
	}
	
	public String inventory(String[] command){
		
		if(command.length>=1){
			int searchId;
			try{
			 searchId = Integer.parseInt(command[0]);
			}catch(Exception ex){
				return "Not enough arguments";
			}
			for(Entity e : inventory){
				if(e.getId()==searchId){
					String details="";
					Hashtable<String,String> attributes = e.getDetails();
					Enumeration<String> keys = attributes.keys();
					while(keys.hasMoreElements()){
						String key = keys.nextElement();
						details+=key+" = "+attributes.get(key)+"\n";
					}
					return details;
				}
					
			}
		}
		String inven="";
		for(Entity e : inventory)
			inven+=e.getId()+" "+e.getType()+"\n";
		return inven;
		
		
	}
	public String eat(String[] command){
		if(command.length>=1){
			int searchId;
			try{
			 searchId = Integer.parseInt(command[0]);
			}catch(Exception ex){
				return "Not enough arguments";
			}
			for(Entity e : inventory){
				if(e.getId()==searchId && e instanceof Edible){
					inventory.remove(e);
					food += ((Edible)e).getFoodLevel();
					return "You ate a "+e.getType() + ". Which had "+((Edible)e).getFoodLevel()+". Your current food is "+food;
					
				}
					
			}
		}
		else{
			for(Entity e : inventory){
				if( e instanceof Edible){
					inventory.remove(e);
					food += ((Edible)e).getFoodLevel();
					return "You ate a "+e.getType() + ". Which had "+((Edible)e).getFoodLevel()+". Your current food is "+food;
					
				}
					
			}
		}
		return "You had nothing edible in your inventory";
	}
	
	@Override
	protected void init(Hashtable<String,String> attributes){
		if(attributes!=null){
			if(inventory == null)
				inventory = new ArrayList<Entity>();
		super.init(attributes);
		if(attributes.containsKey("food"))
			food = Integer.parseInt(attributes.get("food"));
		if(attributes.containsKey("invnetory")){
			JSONArray invn = new JSONArray(attributes.get("invnetory"));
			for(int i=0;i<invn.length();i++){
				JSONObject entityJson = invn.optJSONObject(i);
				Class type = EntityTypeManager.GetEntityType(entityJson.optString("type"));
				try {
					Entity e = (Entity) type.getConstructor(Integer.TYPE,Chunk.class,World.class,JSONObject.class).newInstance(world.getEntities().size(),world.getChunk(0),world,entityJson);
					inventory.add(e);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}}
	}
	
	@Override
	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("type", TYPE);
		JSONArray invn = new JSONArray();
		for(Entity e : inventory)
			invn.put(e.getJson());
		json.put("invnetory",invn);
		json.put("food", food);
		return json;
	}
	public String getType(){
		return "player";
	}
	
	@Override 
	public void tick(){
		food--;
		if(food<0)
			System.out.println("You ran out of food " +food);
	}

}
