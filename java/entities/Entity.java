package entities;

import java.util.Hashtable;
import java.util.Iterator;

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

public abstract class Entity {
	
	/** The id. */
	protected int id;
	
	/** The loc x. */
	protected int locX=0;
	
	/** The loc y. */
	protected int locY=0;
	
	/** The strength. */
	@Getter @Setter protected int strength=0;
	
	/** The power. */
	@Getter @Setter protected int power =0;
	
	/** The wisdom. */
	@Getter @Setter protected int wisdom=0;
	
	/** The physical endurence. */
	@Getter @Setter protected int physicalEndurence=0;
	
	/** The sharp endurence. */
	@Getter @Setter protected int sharpEndurence=0;
	
	/** The stab enderence. */
	@Getter @Setter protected int stabEnderence=0;
	
	/** The temperature resistance. */
	@Getter @Setter protected int temperatureResistance=0;
	
	/** The p h afinity. */
	@Getter @Setter protected int pHAfinity=7;
	
	/** The hydration. */
	@Getter @Setter protected int hydration=0;
	
	/** The vitality. */
	@Getter @Setter protected int vitality=0;
	
	/** The dexterity. */
	@Getter @Setter protected int dexterity=0;
	
	/** The speed. */
	@Getter @Setter protected int speed=0;
	
	/** The luck. */
	@Getter @Setter protected int luck=0;
	
	/** The weight. */
	@Getter @Setter protected int weight=0;
	
	/** The total weight. */
	protected int totalWeight;
	
	/** The flat defense. */
	protected int flatDefense;
	
	/** The percent reduction defense. */
	protected int percentReductionDefense;
	
	/** The stealth. */
	protected int stealth;
	
	/** The physical resistance. */
	protected int physicalResistance;
	
	/** The sharp resistance. */
	protected int sharpResistance;
	
	/** The stab resistance. */
	protected int stabResistance;
	
	/** The temperature endurance. */
	protected int temperatureEndurance;
	
	/** The p h endurance. */
	protected int pHEndurance;
	
	/** The hydration endurence. */
	protected int hydrationEndurence;
	
	/** The health points. */
	protected int healthPoints;
	
	/** The ben points. */
	protected int benPoints;
	
	/** The health recovery rate. */
	protected int healthRecoveryRate;
	
	/** The ben points recovery rate. */
	protected int benPointsRecoveryRate;
	
	/** The crowd control recovery rate. */
	protected int crowdControlRecoveryRate;
	
	/** The crit. */
	protected int crit;
	
	/** The hit. */
	protected int hit;
	
	/** The chunk. */
	protected Chunk chunk;
	
	/** The world. */
	protected World world;
	
	/** The Constant TYPE. */
	protected final static String TYPE="ABSTRACT";
	

	
	/**
	 * Instantiates a new entity.
	 *
	 * @param identification the identification
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 */
	public Entity(int identification,int x,int y,Chunk c,World w){
		id=identification;
		locX=x;
		locY=y;
		chunk=c;
		world=w;
		init(null);
		
	}
	
	/**
	 * Instantiates a new entity.
	 *
	 * @param identification the identification
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param attributes the attributes of the entity
	 */
	public Entity(int identification,int x,int y,Chunk c,World w,Hashtable<String,String> attributes){
		id=identification;
		locX=x;
		locY=y;
		chunk=c;
		world=w;
		init(attributes);
		
	}
	
	/**
	 * Instantiates a new entity.
	 *
	 * @param identification the identification
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param jsonString the JSON data
	 */
	public Entity(int identification,Chunk c,World w,String jsonString){
		id=identification;
		world=w;
		chunk=c;
		JSONObject json = new JSONObject(jsonString);
		Iterator itr = json.keys();
		Hashtable<String,String> attributes = new Hashtable<String,String>();
		while(itr.hasNext()){
			String key = (String) itr.next();
			attributes.put(key, json.optString(key));
		}
		init(attributes);
	}
	
	/**
	 * Instantiates a new entity.
	 *
	 * @param identification the identification
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param json the JSON data
	 */
	public Entity(int identification,Chunk c,World w,JSONObject json){
		id=identification;
		world=w;
		chunk=c;
		Iterator itr = json.keys();
		Hashtable<String,String> attributes = new Hashtable<String,String>();
		while(itr.hasNext()){
			String key = (String) itr.next();
			attributes.put(key, json.optString(key));
		}
		init(attributes);
	}
	
	/**
	 * Instantiates a new entity.
	 *
	 * @param identification the identification
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param attributes the attributes
	 */
	public Entity(int identification,Chunk c,World w,Hashtable<String,String> attributes){
		id=identification;
		world=w;
		chunk=c;
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
	 * Static load.
	 * Stores the class in the EntityTypeManager for loading from a file
	 */
	public static void staticLoad(){
		EntityTypeManager.storeType(TYPE, Entity.class);
	}
	
	/**
	 * Inits the attributes.
	 *
	 * @param attributes the attributes
	 */
	protected void init(Hashtable<String,String> attributes){
		if(attributes!=null){
		if(attributes.containsKey("locationX"))
			locX = Integer.parseInt(attributes.get("locationX"));
		if(attributes.containsKey("locationY"))
			locY = Integer.parseInt(attributes.get("locationY"));
		
		
		
		
		if(attributes.containsKey("strength"))
			strength = Integer.parseInt(attributes.get("strength"));
		if(attributes.containsKey("power"))
			power = Integer.parseInt(attributes.get("power"));
		if(attributes.containsKey("wisdom"))
			wisdom = Integer.parseInt(attributes.get("wisdom"));
		if(attributes.containsKey("physicalEndurence"))
			physicalEndurence = Integer.parseInt(attributes.get("physicalEndurence"));
		if(attributes.containsKey("sharpEndurence"))
			sharpEndurence = Integer.parseInt(attributes.get("sharpEndurence"));
		if(attributes.containsKey("stabEnderence"))
			stabEnderence = Integer.parseInt(attributes.get("stabEnderence"));
		if(attributes.containsKey("temperatureResistance"))
			temperatureResistance = Integer.parseInt(attributes.get("temperatureResistance"));
		if(attributes.containsKey("pHAfinity"))
			pHAfinity = Integer.parseInt(attributes.get("pHAfinity"));
		if(attributes.containsKey("hydration"))
			hydration = Integer.parseInt(attributes.get("hydration"));
		if(attributes.containsKey("vitality"))
			vitality = Integer.parseInt(attributes.get("vitality"));
		if(attributes.containsKey("dexterity"))
			dexterity = Integer.parseInt(attributes.get("dexterity"));
		if(attributes.containsKey("speed"))
			speed = Integer.parseInt(attributes.get("speed"));
		if(attributes.containsKey("luck"))
			luck = Integer.parseInt(attributes.get("luck"));
		if(attributes.containsKey("weight"))
			weight = Integer.parseInt(attributes.get("weight"));
		}
		chunk.addEntity(this, locX, locY);
		
	}
	
	/**
	 * Move to a different chunk.
	 *
	 * @param c the Chunk
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean move(Chunk c , int x, int y){
		chunk.removeEntity(this, locX, locY);
		locX=x;
		locY=y;
		c.addEntity(this, locX, locY);
		return true;
	}
	
	/**
	 * Move within the same chunk.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean move(int x, int y){
		chunk.removeEntity(this, locX, locY);
		locX=x;
		locY=y;
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
		json.put("locationX",locX);
		json.put("locationY",locY);
		json.put("chunk", chunk.getId());
		json.put("strength",strength);
		json.put("power",power);
		json.put("wisdom",wisdom);
		json.put("physicalEndurence",physicalEndurence);
		json.put("sharpEndurence",sharpEndurence);
		json.put("stabEnderence",stabEnderence);
		json.put("temperatureResistance",temperatureResistance);
		json.put("pHAfinity",pHAfinity);
		json.put("hydration",hydration);
		json.put("vitality",vitality);
		json.put("dexterity",dexterity);
		json.put("speed",speed);
		json.put("luck",luck);
		json.put("weight",weight);
		
		
		return json;
	}
	
	 public static Hashtable<String,String> getStandard(){
		 return null;
	 }
	
	

}
