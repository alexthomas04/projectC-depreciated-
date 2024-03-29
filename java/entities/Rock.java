package entities;

import java.util.Hashtable;

import json.JSONObject;
import lombok.Data;
import world.Chunk;
import world.World;

/**
 * The Class Rock.
 */

public class Rock extends Entity {
	
	/** The Constant TYPE. */
	public final static String TYPE="rock";
	
	/**
	 * Instantiates a new rock.
	 *
	 * @param identification the identification
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 */
	public Rock(int identification,int  x, int y, Chunk c, World w) {
		super(identification,x, y, c, w);
	}

	/**
	 * Instantiates a new rock.
	 *
	 * @param identification the identification
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param jsonString the JSON data
	 */
	public Rock(int identification,Chunk c, World w, String jsonString) {
		super(identification,c, w, jsonString);
	}

	/**
	 * Instantiates a new rock.
	 *
	 * @param identification the identification
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param json the JSON data
	 */
	public Rock(int identification,Chunk c, World w, JSONObject json) {
		super(identification,c, w, json);
	}
	
	/**
	 * Instantiates a new rock.
	 *
	 * @param identification the identification
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param attributes the attributes of the entity
	 */
	public Rock(int identification,int x,int y,Chunk c,World w,Hashtable<String,String> attributes){
		super(identification,x,y,c,w,attributes);
		
	}
	
	

	/**
	 * Static load.
	 * Stores the class in the EntityTypeManager for loading from a file
	 */
	public static void staticLoad() {
		EntityTypeManager.storeType(TYPE, Rock.class);

	}
	
	/* (non-Javadoc)
	 * @see entities.Entity#move(world.Chunk, int, int)
	 */
	@Override
	public boolean move(Chunk c,int x,int y){
		return false;
	}
	
	/* (non-Javadoc)
	 * @see entities.Entity#move(int, int)
	 */
	@Override
	public boolean move(int x,int y){
		return false;
	}
	
	/* (non-Javadoc)
	 * @see entities.Entity#getJson()
	 */
	@Override
	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("type", TYPE);
		return json;
	}

	/**
	 * Gets the standard.
	 *
	 * @return the standard Hashtable of stats
	 */
	public static Hashtable<String, String> getStandard() {
		Hashtable<String,String> table= new Hashtable<String,String>();
		table.put("weight", "20");
        table.put("pickupAble","True");
		return table;
	}
	
	/* (non-Javadoc)
	 * @see entities.Entity#getType()
	 */
	public String getType(){
		return "rock";
	}

}
