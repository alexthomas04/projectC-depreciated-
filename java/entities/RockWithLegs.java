/**
 * 
 */
package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

// TODO: Auto-generated Javadoc
/**
 * The Class RockWithLegs.
 *
 * @author Sonicdeadlock
 */
public class RockWithLegs extends Entity {

	/** The Constant TYPE. */
	public final static String TYPE="rockwithlegs";
	
	/**
	 * Instantiates a new rock with legs.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 */
	public RockWithLegs(int identification, int x, int y, Chunk c, World w) {
		super(identification, x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock with legs.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public RockWithLegs(int identification, int x, int y, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, x, y, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock with legs.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param jsonString the json string
	 */
	public RockWithLegs(int identification, Chunk c, World w, String jsonString) {
		super(identification, c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock with legs.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param json the json
	 */
	public RockWithLegs(int identification, Chunk c, World w, JSONObject json) {
		super(identification, c, w, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock with legs.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public RockWithLegs(int identification, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, c, w, attributes);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Static load.
	 */
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
	
	/* (non-Javadoc)
	 * @see entities.Entity#getJson()
	 */
	@Override
	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("type", TYPE);
		return json;
	}
	
	/* (non-Javadoc)
	 * @see entities.Entity#getType()
	 */
	public String getType(){
		return "rock with legs";
	}

}
