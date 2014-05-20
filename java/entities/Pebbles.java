/**
 * 
 */
package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

/**
 * The Class Pebbles.
 *
 * @author alexthomas
 */
public class Pebbles extends Entity{
	
	/** The Constant TYPE. */
	public final static String	TYPE	= "pebbles";
	
	/**
	 * Instantiates a new pebbles.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 */
	public Pebbles(int identification, int x, int y, Chunk c, World w){
		super(identification, x, y, c, w);
	}

	/**
	 * Instantiates a new pebbles.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public Pebbles(int identification, int x, int y, Chunk c, World w, Hashtable<String, String> attributes){
		super(identification, x, y, c, w, attributes);
	}

	/**
	 * Instantiates a new pebbles.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param jsonString the json string
	 */
	public Pebbles(int identification, Chunk c, World w, String jsonString){
		super(identification, c, w, jsonString);
	}

	/**
	 * Instantiates a new pebbles.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param json the json
	 */
	public Pebbles(int identification, Chunk c, World w, JSONObject json){
		super(identification, c, w, json);
	}

	/**
	 * Instantiates a new pebbles.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public Pebbles(int identification, Chunk c, World w, Hashtable<String, String> attributes){
		super(identification, c, w, attributes);
	}
	
	/* (non-Javadoc)
	 * @see entities.Entity#getType()
	 */
	public String getType(){
		return "pebbles";
	}
	
	/**
	 * Static load.
	 */
	public static void staticLoad(){
		EntityTypeManager.storeType(TYPE, Player.class);

	}

    public static Hashtable<String, String> getStandard(){
        Hashtable<String,String>attributes = new Hashtable<String, String>();
        attributes.put("pickupAble","True");
        return attributes;
    }

}
