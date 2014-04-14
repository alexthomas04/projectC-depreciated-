package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

// TODO: Auto-generated Javadoc
/**
 * The Class RockMeat.
 */
public class RockMeat extends Entity implements Edible{
	
	/** The Constant TYPE. */
	public final static String	TYPE	= "rock meat";
	
	/**
	 * Instantiates a new rock meat.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 */
	public RockMeat(int identification, int x, int y, Chunk c, World w){
		super(identification, x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock meat.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public RockMeat(int identification, int x, int y, Chunk c, World w, Hashtable<String, String> attributes){
		super(identification, x, y, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock meat.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param jsonString the json string
	 */
	public RockMeat(int identification, Chunk c, World w, String jsonString){
		super(identification, c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock meat.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param json the json
	 */
	public RockMeat(int identification, Chunk c, World w, JSONObject json){
		super(identification, c, w, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock meat.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public RockMeat(int identification, Chunk c, World w, Hashtable<String, String> attributes){
		super(identification, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see entities.Edible#getFoodLevel()
	 */
	@Override
	public int getFoodLevel(){
		// TODO Auto-generated method stub
		return 10;
	}

	/* (non-Javadoc)
	 * @see entities.Edible#getMaxFoodLevel()
	 */
	@Override
	public int getMaxFoodLevel(){
		// TODO Auto-generated method stub
		return 10;
	}
	
	/* (non-Javadoc)
	 * @see entities.Entity#getType()
	 */
	public String getType(){
		return "rock meat";
	}
	
	/**
	 * Static load.
	 */
	public static void staticLoad(){
		EntityTypeManager.storeType(TYPE, Player.class);

	}

}
