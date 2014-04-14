package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericPlant.
 */
public class GenericPlant extends Plant {
	
	/** The Constant TYPE. */
	public final static String TYPE="genericPlant";
	
	/**
	 * Instantiates a new generic plant.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 */
	public GenericPlant(int identification, int x, int y, Chunk c, World w) {
		super(identification, x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new generic plant.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public GenericPlant(int identification, int x, int y, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, x, y, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new generic plant.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param jsonString the json string
	 */
	public GenericPlant(int identification, Chunk c, World w, String jsonString) {
		super(identification, c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new generic plant.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param json the json
	 */
	public GenericPlant(int identification, Chunk c, World w, JSONObject json) {
		super(identification, c, w, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new generic plant.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public GenericPlant(int identification, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see entities.Plant#getJson()
	 */
	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("type", TYPE);
		return json;
	}
	
	/* (non-Javadoc)
	 * @see entities.Entity#getType()
	 */
	public String getType(){
		return "genericPlant";
	}
	
	/**
	 * Static load.
	 */
	public static void staticLoad() {
		EntityTypeManager.storeType(TYPE, GenericPlant.class);

	}
	
	/**
	 * Gets the standard.
	 *
	 * @return the standard
	 */
	public static Hashtable<String, String> getStandard() {
		Hashtable<String,String> table= new Hashtable<String,String>();
		table.put("foodLevel", ((int)Math.random()*5)+"");
		return table;
	}
	
	/* (non-Javadoc)
	 * @see entities.Edible#getMaxFoodLevel()
	 */
	public int getMaxFoodLevel(){
		return 50;
	}

}
