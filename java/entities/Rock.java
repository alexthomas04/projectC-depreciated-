package entities;

import json.JSONObject;
import world.Chunk;
import world.World;

// TODO: Auto-generated Javadoc
/**
 * The Class Rock.
 */
public class Rock extends Entity {
	
	/** The Constant TYPE. */
	private final static String TYPE="rock";
	
	/**
	 * Instantiates a new rock.
	 *
	 * @param x the x coordinate 
	 * @param y the y coordinate
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 */
	public Rock(int x, int y, Chunk c, World w) {
		super(x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock.
	 *
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param jsonString the JSON data
	 */
	public Rock(Chunk c, World w, String jsonString) {
		super(c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new rock.
	 *
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param json the JSON data
	 */
	public Rock(Chunk c, World w, JSONObject json) {
		super(c, w, json);
		// TODO Auto-generated constructor stub
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

}
