package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import communication.Database;
import skills.Skill;
import json.JSONArray;
import json.JSONObject;
import world.Chunk;
import world.World;
import lombok.*;

/**
 * The Class Player.
 */
public class Player extends Entity{

	/** The Constant TYPE. */
	public final static String	TYPE	= "player";
	
	/** The food. */
	private int					food;
	
	/** The inventory. */
	private ArrayList<Entity>	inventory;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Getter
	private String				name;
	
	/**
	 * Instantiates a new player.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the Chunk
	 * @param w the World
	 */
	public Player(int identification, int x, int y, Chunk c, World w){
		super(identification, x, y, c, w);

	}

	/**
	 * Instantiates a new player.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the Chunk
	 * @param w the World
	 * @param attributes the attributes
	 */
	public Player(int identification, int x, int y, Chunk c, World w, Hashtable<String, String> attributes){
        super(identification, x, y, c, w, attributes);
	}

	/**
	 * Instantiates a new player.
	 *
	 * @param identification the identification
	 * @param c the Chunk	
	 * @param w the World
	 * @param jsonString the json string
	 */
	public Player(int identification, Chunk c, World w, String jsonString){
		super(identification, c, w, jsonString);
	}

	/**
	 * Instantiates a new player.
	 *
	 * @param identification the identification
	 * @param c the Chunk	
	 * @param w the World
	 * @param json the json
	 */
	public Player(int identification, Chunk c, World w, JSONObject json){
		super(identification, c, w, json);
	}

	/**
	 * Instantiates a new player.
	 *
	 * @param identification the identification
	 * @param c the Chunk
	 * @param w the World
	 * @param attributes the attributes
	 */
	public Player(int identification, Chunk c, World w, Hashtable<String, String> attributes){
		super(identification, c, w, attributes);
	}

	/**
	 * Stores the entity type in the EntityTypeManager
	 */
	public static void staticLoad(){
		EntityTypeManager.storeType(TYPE, Player.class);

	}

	/**
	 * Gets the standard.
	 * 
	 * @return the standard Hashtable of stats
	 */
	public static Hashtable<String, String> getStandard(){
		Hashtable<String, String> table = new Hashtable<String, String>();
		table.put("food", "1000");
        table.put("locationX", "0");
        table.put("locationY","0");
		// table.put("vision", "1");
		return table;
	}

	/**
	 * Handle command.
	 *
	 * @param command the command
	 * @return the result
	 */
	public String handleCommand(String[] command){
		for (int i = 0; i < command.length; i++){
			command[i] = command[i].trim();
		}
		if (dead)
			return "You can't do that ... \nYou're dead";
		else if (command[0].equalsIgnoreCase("move")){
			return move(Arrays.copyOfRange(command, 1, command.length));
		} else if (command[0].equalsIgnoreCase("look")){
			return look(Arrays.copyOfRange(command, 1, command.length));
		} else if (command.length >= 2 && command[0].equalsIgnoreCase("pick") && command[1].equalsIgnoreCase("up"))
			return pickUp(Arrays.copyOfRange(command, 2, command.length));
		else if (command[0].equalsIgnoreCase("inventory"))
			return inventory(Arrays.copyOfRange(command, 1, command.length));
		else if (command[0].equalsIgnoreCase("eat"))
			return eat(Arrays.copyOfRange(command, 1, command.length));
		else if (command[0].equalsIgnoreCase("skill"))
			return skillCheck(Arrays.copyOfRange(command, 1, command.length));
		else if (command[0].equalsIgnoreCase("drop"))
			return drop(Arrays.copyOfRange(command, 1, command.length));
		else if(command[0].equalsIgnoreCase("attack"))
			return attack(Arrays.copyOfRange(command, 1, command.length));
		else if (command[0].equalsIgnoreCase("commands")){
			return "move {'right,'left','up','down'}[,{distance}]" + "\nlook {'around',closely','down','up'}[,{id},{radius}]" + "\npick up [{id}]" + "\ninventory" + "\neat [{id}]" + "\nskill {skill name}" + "\ndrop {id}"+"\nattack {id} [{'blunt'}]";
		}
		return "Command did not execute";
	}

	/**
	 * Move.
	 *
	 * @param command the command
	 * @return the result
	 */
	public String move(String[] command){
		if (command.length < 1)
			return "Not enough arguments";
		int distance = 1;
		if (command.length >= 2){
			try{
				distance = Integer.parseInt(command[1]);
				if (distance > speed.getCurrentLevel())
					distance = speed.getCurrentLevel();
				else if (distance < 1)
					return "Invalid Distance";
			} catch (Exception ex){
				distance = 1;
			}
		}
		speed.addXP(distance);
		if (command[0].equalsIgnoreCase("right")){
			if (chunk.getSizeX() > locX + distance){
				move(locX + distance, locY);
				return "You are at (" + locX + ", " + locY + ")." + ((chunk.getSizeX() - 1 == locX) ? "You are at the right edge." : "");
			} else
				return "You are already at the right edge";
		}

		else if (command[0].equalsIgnoreCase("left")){
			if (0 <= locX - distance){
				move(locX - distance, locY);
				return "You are at (" + locX + ", " + locY + ")." + ((0 == locX) ? "You are at the left edge." : "");
			} else
				return "You are already at the left edge";
		} else if (command[0].equalsIgnoreCase("down")){
			if (chunk.getSizeY() - distance > locY + distance){
				move(locX, locY + distance);
				return "You are at (" + locX + ", " + locY + ")." + ((chunk.getSizeY() - 1 == locY) ? "You are at the bottom edge." : "");
			} else
				return "You are already at the bottom edge";
		} else if (command[0].equalsIgnoreCase("up")){
			if (0 <= locY - distance){
				move(locX, locY - distance);
				return "You are at (" + locX + ", " + locY + ")." + ((0 == locY) ? "You are at the top edge." : "");
			} else
				return "You are already at the top edge";
		}else if(command[0].equalsIgnoreCase("to")){
			if(command.length<3)
				return "Not enough arguments";
			int destX=0;
			int destY=0;
			try{
				destX = Integer.parseInt(command[1]);
				destY= Integer.parseInt(command[2]);
				double distanceToTravle = Math.sqrt(Math.pow(destX-locX, 2)+Math.pow(destY-locY,2));
				if(distance < (int)distanceToTravle)
					return "Too far away";
				move(destX,destY);
				return "You are at (" + locX + ", " + locY + ").";
			}catch(Exception ex){
				return "Invalid arguments";
			}
		}
		return "Comamnd did not execute";
	}

	/**
	 * Look.
	 *
	 * @param command the command
	 * @return the result
	 */
	public String look(String[] command){
		if (command.length < 1)
			return "Not enough arguments";
		int radius = 1;
		if (command[0].equalsIgnoreCase("around")){
			String data = "";
			if (command.length >= 2){
				try{
					radius = Integer.parseInt(command[1]);
					if (radius > vision.getCurrentLevel())
						radius = vision.getCurrentLevel();
				} catch (Exception ex){
					radius = 1;
				}
			}
			if (command.length >= 3){
				Class<?> type = null;
				if (command[2].equalsIgnoreCase("animal"))
					type = Animal.class;
				else if (command[2].equalsIgnoreCase("plant"))
					type = Plant.class;
				else if (command[2].equalsIgnoreCase("edible"))
					type = Edible.class;
				else if (command[2].equalsIgnoreCase("player"))
					type = Player.class;
				else
					return "Invalid type";

				for (int i = radius * -1; i <= radius; i++){
					for (int k = radius * -1; k <= radius; k++){
						if (locX + i < chunk.getSizeX() && locY + k < chunk.getSizeY() && locX + i >= 0 && locY + k >= 0){
							for (Entity e : chunk.getEntities(locX + i, locY + k))
								if (type.isInstance(e)){
									data += "<span><span class='game_entity'>"+e.getType() + "</span> , <span class='game_id'>" + e.getId() + "</span>. At <span class='coordX'>" + e.getX() + "</span>, <span class='coordY'>" + e.getY() + "</span></span>\n";
									vision.addXP(1);
								}
						}
					}
				}
			} else{
				for (int i = radius * -1; i <= radius; i++){
					for (int k = radius * -1; k <= radius; k++){
						if (locX + i < chunk.getSizeX() && locY + k < chunk.getSizeY() && locX + i >= 0 && locY + k >= 0){
							for (Entity e : chunk.getEntities(locX + i, locY + k))
								data += "<span><span class='game_entity'>"+e.getType() + "</span> , <span class='game_id'>" + e.getId() + "</span>. At <span class='coordX'>" + e.getX() + "</span>, <span class='coordY'>" + e.getY() + "</span></span>\n";
							vision.addXP(1);
						}
					}
				}
			}
			return "You found " + ((data.isEmpty()) ? "nothing" : "\n" + data);
		} else if (command[0].equalsIgnoreCase("down")){
			String data = "";
			ArrayList<Entity> entities;
			entities = chunk.getEntities(locX, locY);
			for (Entity e : entities){
				data += e.getType() + " , " + e.getId() + ". At " + (locX) + ", " + (locY) + "\n";
				vision.addXP(1);
			}
			return "You found " + ((data.isEmpty()) ? "nothing" : data);
		} else if (command[0].equalsIgnoreCase("Up"))
			return "It's a bird, It's a plane, It's .... \n nope it just a cloud.";
		else if (command[0].equalsIgnoreCase("closely")){
			if (command.length < 2)
				return "Not enough arguments";
			int searchId;
			try{
				searchId = Integer.parseInt(command[1]);
			} catch (Exception ex){
				return "Not enough arguments";
			}
			radius = vision.getCurrentLevel();
			for (int i = radius * -1; i <= radius; i++){
				for (int k = radius * -1; k <= radius; k++){
					if (locX + i < chunk.getSizeX() && locY + k < chunk.getSizeY() && locX + i >= 0 && locY + k >= 0){
						for (Entity e : chunk.getEntities(locX + i, locY + k)){
							if (e.getId() == searchId){
								String details = "";
								Hashtable<String, String> attributes = e.getDetails();
								String[] keys= new String[attributes.size()];
								keys = attributes.keySet().toArray(keys);
								int numDetails = 2+vision.getCurrentLevel()/2;//ben calculation
								if(numDetails>attributes.size())
									numDetails=attributes.size();
								ArrayList<Integer> usedIndexes = new ArrayList<Integer>();
								for(int q=0;q<numDetails;q++){
									int index=0;
									do{
										index = (int) (Math.random()*attributes.size());
									}while(usedIndexes.contains(index));
									usedIndexes.add(index);
									String key=keys[index];
									if (key == "inventory"){
//										if (e == this){
//											details += "inventory:[\n";
//											for (int u = 0; u < inventory.size(); u++){
//												Entity var = inventory.get(u);
//												details += var.getType() + ", " + var.getId() + ((u < inventory.size() - 1) ? "\n" : "");
//											}
//											details += "]\n";
//										}
									} else{
										details += key + " = " + attributes.get(key) + "\n";
									}
									vision.addXP(1);
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

	/**
	 * Pick up.
	 *
	 * @param command the command
	 * @return the result
	 */
	public String pickUp(String[] command){
		String details = "";
		if (command.length >= 1){
			int searchId;
			try{
				searchId = Integer.parseInt(command[0]);
			} catch (Exception ex){
				return "Not enough arguments";
			}
			ArrayList<Entity> entities = chunk.getEntities(locX, locY);
			for (Entity e : entities){
				if (e.getId() == searchId && !(e instanceof Player)){
					chunk.removeEntity(e, locX, locY);
					world.removeEntity(e);
					inventory.add(e);
					e.move(null, 0, 0);
					details += e.getType() + " ," + e.getId() + "\n";
				}

			}
			if(details=="")
				details="Unable to reach entity.";
			return details;
		} else{
			ArrayList<Entity> entities = chunk.getEntities(locX, locY);
			for (Entity e : entities){
				if (!(e instanceof Player)){
					chunk.removeEntity(e, locX, locY);
					world.removeEntity(e);
					e.move(null, 0, 0);
					inventory.add(e);
					details += e.getType() + " ," + e.getId() + "\n";
				}
			}
			return details;
		}
	}

	/**
	 * Inventory.
	 *
	 * @param command the command
	 * @return the result
	 */
	public String inventory(String[] command){

		if (command.length >= 1){
			int searchId;
			try{
				searchId = Integer.parseInt(command[0]);
			} catch (Exception ex){
				return "Not enough arguments";
			}
			for (Entity e : inventory){
				if (e.getId() == searchId){
					String details = "";
					Hashtable<String, String> attributes = e.getDetails();
					Enumeration<String> keys = attributes.keys();
					while (keys.hasMoreElements()){
						String key = keys.nextElement();
						details += key + " = " + attributes.get(key) + "\n";
					}
					return details;
				}

			}
		}
		String inven = "inventory:\n";
		for (Entity e : inventory)
			inven += "<span><span class='inventory_id'>"+e.getId() + "</span> <span class='inventory_item'>" + e.getType() + "</span></span>\n";
		return inven;

	}

	/**
	 * Eat.
	 *
	 * @param command the command
	 * @return the result
	 */
	public String eat(String[] command){
		if (command.length >= 1){
			int searchId;
			try{
				searchId = Integer.parseInt(command[0]);
			} catch (Exception ex){
				return "Not enough arguments";
			}
			for (Entity e : inventory){
				if (e.getId() == searchId && e instanceof Edible){
					inventory.remove(e);
					food += ((Edible) e).getFoodLevel();
					return "Ate a " + e.getType() + " with " + ((Edible) e).getFoodLevel() + " food. Now you have " + food + " food.";
				}

			}
		}
		for (Entity e : inventory){
			if (e instanceof Edible){
				inventory.remove(e);
				food += ((Edible) e).getFoodLevel();
				return "Ate a " + e.getType() + " with " + ((Edible) e).getFoodLevel() + " food. Now you have " + food + " food.";
			}
		}
		return "Nothing edible in inventory";
	}

	/**
	 * Skill check.
	 *
	 * @param command the command
	 * @return the result
	 */
	public String skillCheck(String[] command){
		Skill skill;
		if (command.length < 1)
			return "Invalid number of arguments";
		if (command[0].equalsIgnoreCase("physicalEndurance"))
			skill = physicalEndurance;
		else if (command[0].equalsIgnoreCase("hydration"))
			skill = hydration;
		else if (command[0].equalsIgnoreCase("luck"))
			skill = luck;
		else if (command[0].equalsIgnoreCase("wisdom"))
			skill = wisdom;
		else if (command[0].equalsIgnoreCase("sharpEndurance"))
			skill = sharpEndurance;
		else if (command[0].equalsIgnoreCase("dexterity"))
			skill = dexterity;
		else if (command[0].equalsIgnoreCase("stabEndurance"))
			skill = stabEndurance;
		else if (command[0].equalsIgnoreCase("power"))
			skill = power;
		else if (command[0].equalsIgnoreCase("pHAffinity"))
			skill = pHAffinity;
		else if (command[0].equalsIgnoreCase("speed"))
			skill = speed;
		else if (command[0].equalsIgnoreCase("vitality"))
			skill = vitality;
		else if (command[0].equalsIgnoreCase("strength"))
			skill = strength;
		else if (command[0].equalsIgnoreCase("vision"))
			skill = vision;
		else if (command[0].equalsIgnoreCase("temperatureResistance"))
			skill = temperatureResistance;
		else
			return "Not a valid skill";

		int current = skill.getCurrentLevel();
		long nextXP = skill.getNextLevelXP();
		long currentXP = skill.getCurrentXP();
		double percent = skill.progress() * 100;

		String response = "Current Level: " + current + "\nCurrent XP: " + currentXP + "\nNext Level: " + nextXP + "\nPercent Done: " + percent + "%";

		return response;
	}

	/**
	 * Drop.
	 *
	 * @param command the command
	 * @return the result
	 */
	public String drop(String[] command){
		if (command.length < 1)
			return "Not enough arguments";
		int entityId = 0;
		try{
			entityId = Integer.parseInt(command[0]);
		} catch (Exception ex){
			return "Invadid Arguments";
		}
		Entity e = null;
		Iterator<Entity> i = inventory.iterator();
		boolean found = false;
		while (i.hasNext() && !found){
			e = i.next();
			if (e.getId() == entityId)
				found = true;
		}
		if (!found)
			return "Entity not in inventory";
		world.addEntity(e);
		chunk.addEntity(e, locX, locY);
		inventory.remove(e);
		e.move(chunk, locX, locY);
		return "Entity removed";
	}

	/**
	 * Attack.
	 *
	 * @param command the command
	 * @return the result
	 */
	public String attack(String[] command){
		if(command.length<1)
			return "Invalid number of aruments";
		Entity victim = null;
		try{
			int i = Integer.parseInt(command[0]);
			for(Entity e : chunk.getEntities()){
				if(e.id==i)
					victim = e;
			}
			if(victim == null)
				return "Entity not found";
		}catch(Exception ex){
			return "Invalid id";
			
		}
		int diffY=Math.abs(locY-victim.locY);
		int diffX=Math.abs(locX-victim.locX);
		if(diffY>1 || diffX>1)
			return "Entity too far away";
		if(!victim.dead)
			attack(victim);
		else
			return "They are already dead";
		strength.addXP(1);
		if(command.length>=2){
			String type = command[1];
			if(type.equalsIgnoreCase("blunt"))
				physicalEndurance.addXP(1);
		}
		return "They are at "+victim.healthPoints+" HP";
	}
	
	/* (non-Javadoc)
	 * @see entities.Entity#init(java.util.Hashtable)
	 */
	@Override
	protected void init(Hashtable<String, String> attributes){
		strength = new Skill(1, 0, "strength", this);
		power = new Skill(1, 0, "power", this);
		wisdom = new Skill(1, 0, "wisdom", this);
		physicalEndurance = new Skill(1, 0, "physicalEndurance", this);
		sharpEndurance = new Skill(1, 0, "sharpEndurance", this);
		stabEndurance = new Skill(1, 0, "stabEndurance", this);
		temperatureResistance = new Skill(1, 0, "temperatureResistance", this);
		pHAffinity = new Skill(1, 0, "pHAffinity", this);
		hydration = new Skill(1, 0, "hydration", this);
		vitality = new Skill(1, 0, "vitality", this);
		dexterity = new Skill(1, 0, "dexterity", this);
		speed = new Skill(1, 0, "speed", this);
		luck = new Skill(1, 0, "luck", this);
		vision = new Skill(1, 0, "vision", this);
		if (inventory == null)
			inventory = new ArrayList<Entity>();
		if (attributes != null){
			super.init(attributes);
			if (attributes.containsKey("food"))
				food = Integer.parseInt(attributes.get("food"));
			if (attributes.containsKey("name"))
				name = attributes.get("name");
			if (attributes.containsKey("inventory")){
				JSONArray invn = new JSONArray(attributes.get("inventory"));
				for (int i = 0; i < invn.length(); i++){
					JSONObject entityJson = invn.optJSONObject(i);
					Class type = EntityTypeManager.GetEntityType(entityJson.optString("type"));
					try{
						int numEntities = world.getNumEntities()+ inventory.size();
						Entity e = (Entity) type.getConstructor(Integer.TYPE, Chunk.class, World.class, JSONObject.class).newInstance(numEntities, world.getChunk(0), world, entityJson);
						inventory.add(e);
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see entities.Entity#getJson()
	 */
	@Override
	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("type", TYPE);
		JSONArray invn = new JSONArray();
		for (Entity e : inventory)
			invn.put(e.getJson());
		json.put("inventory", invn);
		json.put("food", food);
		json.put("name", name);
		return json;
	}

	/* (non-Javadoc)
	 * @see entities.Entity#getType()
	 */
	@Override
	public String getType(){
		return "player";
	}

	/* (non-Javadoc)
	 * @see entities.Entity#tick()
	 */
	@Override
	public void tick(){
		food--;
		if (food < 0)
			dead = true;

	}

	/**
	 * Handle command.
	 *
	 * @param string the string
	 * @return the result
	 */
	public String handleCommand(String string){
		return handleCommand(string.trim().split(" "));

	}
	
	/**
	 * Level up.
	 *
	 * @param skillName the skill name
	 * @param level the level
	 */
	public void levelUp(String skillName, int level){
		String message = "CONGRATULATIONS!! Your " + skillName + " is now at Level " + level;
		//messageWrite(message);
		calcStats();
	}
	
	/**
	 * Stores a message for the player on the database
	 *
	 * @param message the message
	 */
	public void messageWrite(String message){
		Database db = Database.getDatabaseInstance();
		db.insertMessage(name, message);

	}
}
