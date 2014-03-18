/**
 * 
 */
package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

/**
 * @author Sonicdeadlock
 *
 */
public class RockWithLegs extends Entity {

	private final static String TYPE="rockwithlegs";
	/**
	 * @param identification
	 * @param x
	 * @param y
	 * @param c
	 * @param w
	 */
	public RockWithLegs(int identification, int x, int y, Chunk c, World w) {
		super(identification, x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param identification
	 * @param x
	 * @param y
	 * @param c
	 * @param w
	 * @param attributes
	 */
	public RockWithLegs(int identification, int x, int y, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, x, y, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param identification
	 * @param c
	 * @param w
	 * @param jsonString
	 */
	public RockWithLegs(int identification, Chunk c, World w, String jsonString) {
		super(identification, c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param identification
	 * @param c
	 * @param w
	 * @param json
	 */
	public RockWithLegs(int identification, Chunk c, World w, JSONObject json) {
		super(identification, c, w, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param identification
	 * @param c
	 * @param w
	 * @param attributes
	 */
	public RockWithLegs(int identification, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, c, w, attributes);
		// TODO Auto-generated constructor stub
	}
	
	public static void staticLoad() {
		EntityTypeManager.storeType(TYPE, RockWithLegs.class);

	}
	
	@Override
	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("type", TYPE);
		return json;
	}

}
