package entities;

import java.util.Hashtable;
import java.util.Iterator;

import skills.Skill;
import json.JSONObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import world.Chunk;
import world.World;

// TODO: Auto-generated Javadoc
/**
 * The Class Entity.
 * 
 * @author Alex Thomas
 */

public abstract class Entity{

	/** The id. */
	protected int		id;

	/** The loc x. */
	protected int		locX					= 0;

	/** The loc y. */
	protected int		locY					= 0;

	/** The strength. */
	@Getter
	@Setter
	protected Skill		strength				= new Skill(0);

	/** The power. */
	@Getter
	@Setter
	protected Skill		power					= new Skill(0);

	/** The wisdom. */
	@Getter
	@Setter
	protected Skill		wisdom					= new Skill(0);

	/** The physical endurence. */
	@Getter
	@Setter
	protected Skill		physicalEndurence		= new Skill(0);

	/** The sharp endurence. */
	@Getter
	@Setter
	protected Skill		sharpEndurence			= new Skill(0);

	/** The stab enderence. */
	@Getter
	@Setter
	protected Skill		stabEnderence			= new Skill(0);

	/** The temperature resistance. */
	@Getter
	@Setter
	protected Skill		temperatureResistance	= new Skill(0);

	/** The p h afinity. */
	@Getter
	@Setter
	protected Skill		pHAfinity				= new Skill(7);

	/** The hydration. */
	@Getter
	@Setter
	protected Skill		hydration				= new Skill(0);

	/** The vitality. */
	@Getter
	@Setter
	protected Skill		vitality				= new Skill(0);

	/** The dexterity. */
	@Getter
	@Setter
	protected Skill		dexterity				= new Skill(0);

	/** The speed. */
	@Getter
	@Setter
	protected Skill		speed					= new Skill(0);

	/** The luck. */
	@Getter
	@Setter
	protected Skill		luck					= new Skill(0);

	/** The weight. */
	@Getter
	@Setter
	protected Skill		weight					= new Skill(0);

	@Getter
	@Setter
	protected Skill		vision					= new Skill(1);

	/** The total weight. */
	protected int		totalWeight;

	/** The flat defense. */
	protected int		flatDefense;

	/** The percent reduction defense. */
	protected int		percentReductionDefense;

	/** The stealth. */
	protected int		stealth;

	/** The physical resistance. */
	protected int		physicalResistance;

	/** The sharp resistance. */
	protected int		sharpResistance;

	/** The stab resistance. */
	protected int		stabResistance;

	/** The temperature endurance. */
	protected int		temperatureEndurance;

	/** The p h endurance. */
	protected int		pHEndurance;

	/** The hydration endurence. */
	protected int		hydrationEndurence;

	/** The health points. */
	protected int		healthPoints;

	/** The ben points. */
	protected int		benPoints;

	/** The health recovery rate. */
	protected int		healthRecoveryRate;

	/** The ben points recovery rate. */
	protected int		benPointsRecoveryRate;

	/** The crowd control recovery rate. */
	protected int		crowdControlRecoveryRate;

	/** The crit. */
	protected int		crit;

	/** The hit. */
	protected int		hit;

	/** The chunk. */
	protected Chunk		chunk;

	/** The world. */
	protected World		world;

	protected boolean	dead					= false;

	/**
	 * Instantiates a new entity.
	 * 
	 * @param identification
	 *            the identification
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param c
	 *            the Chunk that the entity will be put in
	 * @param w
	 *            the World that the entity will be put in
	 */
	public Entity(int identification, int x, int y, Chunk c, World w){
		id = identification;
		locX = x;
		locY = y;
		chunk = c;
		world = w;
		init(null);

	}

	/**
	 * Instantiates a new entity.
	 * 
	 * @param identification
	 *            the identification
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param c
	 *            the Chunk that the entity will be put in
	 * @param w
	 *            the World that the entity will be put in
	 * @param attributes
	 *            the attributes of the entity
	 */
	public Entity(int identification, int x, int y, Chunk c, World w,
			Hashtable<String, String> attributes){
		id = identification;
		locX = x;
		locY = y;
		chunk = c;
		world = w;
		init(attributes);

	}

	/**
	 * Instantiates a new entity.
	 * 
	 * @param identification
	 *            the identification
	 * @param c
	 *            the Chunk that the entity will be put in
	 * @param w
	 *            the World that the entity will be put in
	 * @param jsonString
	 *            the JSON data
	 */
	public Entity(int identification, Chunk c, World w, String jsonString){
		id = identification;
		world = w;
		chunk = c;
		JSONObject json = new JSONObject(jsonString);
		Iterator itr = json.keys();
		Hashtable<String, String> attributes = new Hashtable<String, String>();
		while (itr.hasNext()){
			String key = (String) itr.next();
			attributes.put(key, json.optString(key));
		}
		init(attributes);
	}

	/**
	 * Instantiates a new entity.
	 * 
	 * @param identification
	 *            the identification
	 * @param c
	 *            the Chunk that the entity will be put in
	 * @param w
	 *            the World that the entity will be put in
	 * @param json
	 *            the JSON data
	 */
	public Entity(int identification, Chunk c, World w, JSONObject json){
		id = identification;
		world = w;
		chunk = c;
		Iterator itr = json.keys();
		Hashtable<String, String> attributes = new Hashtable<String, String>();
		while (itr.hasNext()){
			String key = (String) itr.next();
			attributes.put(key, json.optString(key));
		}
		init(attributes);
	}

	/**
	 * Instantiates a new entity.
	 * 
	 * @param identification
	 *            the identification
	 * @param c
	 *            the Chunk that the entity will be put in
	 * @param w
	 *            the World that the entity will be put in
	 * @param attributes
	 *            the attributes
	 */
	public Entity(int identification, Chunk c, World w,
			Hashtable<String, String> attributes){
		id = identification;
		world = w;
		chunk = c;
		init(attributes);
	}

	/**
	 * Gets the x.
	 * 
	 * @return the x
	 */
	public int getX(){
		return locX;
	}

	/**
	 * Gets the y.
	 * 
	 * @return the y
	 */
	public int getY(){
		return locY;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId(){
		return id;
	}

	/**
	 * Gets the chunk.
	 * 
	 * @return the chunk
	 */
	public Chunk getChunk(){
		return chunk;
	}

	/**
	 * Static load. Stores the class in the EntityTypeManager for loading from a
	 * file
	 */
	public static void staticLoad(){

	}

	/**
	 * Inits the attributes.
	 * 
	 * @param attributes
	 *            the attributes
	 */
	protected void init(Hashtable<String, String> attributes){
		if (attributes != null){
			if (attributes.containsKey("locationX"))
				locX = Integer.parseInt(attributes.get("locationX"));
			if (attributes.containsKey("locationY"))
				locY = Integer.parseInt(attributes.get("locationY"));

			if (attributes.containsKey("strength"))
				strength = new Skill(attributes.get("strength"), this);
			if (attributes.containsKey("power"))
				power = new Skill(attributes.get("power"), this);
			if (attributes.containsKey("wisdom"))
				wisdom = new Skill(attributes.get("wisdom"), this);
			if (attributes.containsKey("physicalEndurence"))
				physicalEndurence = new Skill(
						attributes.get("physicalEndurence"), this);
			if (attributes.containsKey("sharpEndurence"))
				sharpEndurence = new Skill(attributes.get("sharpEndurence"),
						this);
			if (attributes.containsKey("stabEnderence"))
				stabEnderence = new Skill(attributes.get("stabEnderence"), this);
			if (attributes.containsKey("temperatureResistance"))
				temperatureResistance = new Skill(
						attributes.get("temperatureResistance"), this);
			if (attributes.containsKey("pHAfinity"))
				pHAfinity = new Skill(attributes.get("pHAfinity"), this);
			if (attributes.containsKey("hydration"))
				hydration = new Skill(attributes.get("hydration"), this);
			if (attributes.containsKey("vitality"))
				vitality = new Skill(attributes.get("vitality"), this);
			if (attributes.containsKey("dexterity"))
				dexterity = new Skill(attributes.get("dexterity"), this);
			if (attributes.containsKey("speed"))
				speed = new Skill(attributes.get("speed"), this);
			if (attributes.containsKey("luck"))
				luck = new Skill(attributes.get("luck"), this);
			if (attributes.containsKey("weight"))
				weight = new Skill(attributes.get("weight"), this);
			if (attributes.containsKey("vision"))
				vision = new Skill(attributes.get("vision"), this);
			if (attributes.containsKey("dead"))
				dead = Boolean.parseBoolean(attributes.get("dead"));
		}
		chunk.addEntity(this, locX, locY);
		if(!(this instanceof Player))
			world.addEntity(this);
		else
			world.addPlayer((Player)this);
		calcStats();

	}

	/**
	 * Move to a different chunk.
	 * 
	 * @param c
	 *            the Chunk
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean move(Chunk c, int x, int y){
		chunk.removeEntity(this, locX, locY);
		locX = x;
		locY = y;
		if (c != null)
			c.addEntity(this, locX, locY);
		return true;
	}

	/**
	 * Move within the same chunk.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean move(int x, int y){
		chunk.removeEntity(this, locX, locY);
		locX = x;
		locY = y;
		chunk.addEntity(this, locX, locY);
		return true;
	}

	/**
	 * Gets the entity as json.
	 * 
	 * @return the json
	 */
	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		json.put("locationX", locX);
		json.put("locationY", locY);
		json.put("chunk", chunk.getId());
		json.put("strength", strength);
		json.put("power", power);
		json.put("wisdom", wisdom);
		json.put("physicalEndurence", physicalEndurence);
		json.put("sharpEndurence", sharpEndurence);
		json.put("stabEnderence", stabEnderence);
		json.put("temperatureResistance", temperatureResistance);
		json.put("pHAfinity", pHAfinity);
		json.put("hydration", hydration);
		json.put("vitality", vitality);
		json.put("dexterity", dexterity);
		json.put("speed", speed);
		json.put("luck", luck);
		json.put("weight", weight);
		json.put("vision", vision);
		json.put("dead", dead);
		json.put("total weight", totalWeight);
		json.put("Hit points", healthPoints);
		json.put("hit",hit);
		json.put("crit", crit);

		return json;
	}

	public static Hashtable<String, String> getStandard(){
		return null;
	}

	public String getType(){
		return "ABSTRACT";
	}

	public Hashtable<String, String> getDetails(){
		JSONObject json = getJson();
		Iterator itr = json.keys();
		Hashtable<String, String> attributes = new Hashtable<String, String>();
		while (itr.hasNext()){
			String key = (String) itr.next();
			Object o = json.opt(key);
			if (o instanceof Skill){
				Skill s = (Skill) o;
				attributes.put(key, s.getCurrentLevel() + "");
			} else{
				attributes.put(key, json.optString(key));
			}
		}
		return attributes;
	}

	public void tick(){
	}

	public void calcStats(){
		healthPoints = (int) ((100+dexterity.getCurrentLevel()/10)*Math.pow(1.01, vitality.getCurrentLevel()));//ben calculation
		hit=dexterity.getCurrentLevel()+vision.getCurrentLevel()/4;//half of a ben calculation
		
	}
	public void attack(Entity e){
		int hitRate = 80+this.hit;//-e.flee
		e.healthPoints-=hitRate;
		if(e.healthPoints<=0){
			e.healthPoints=0;
			e.dead=true;
			e.die();
		}
	}
	
	protected void die(){ }
}
