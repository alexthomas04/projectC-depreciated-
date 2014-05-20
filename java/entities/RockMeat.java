package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

/**
 * The Class RockMeat.
 */
public class RockMeat extends Entity implements Edible{
	
	/** The Constant TYPE. */
	public final static String	TYPE	= "rock meat";

    protected int foodLevel=0;
	
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
	}



	/* (non-Javadoc)
	 * @see entities.Edible#getMaxFoodLevel()
	 */
	@Override
	public int getMaxFoodLevel(){
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
    public static Hashtable<String, String> getStandard(){
        Hashtable<String,String>attributes = new Hashtable<String, String>();
        attributes.put("pickupAble","True");
        attributes.put("foodLevel","5");
        return attributes;
    }

    /* (non-Javadoc)
	 * @see entities.Entity#init(java.util.Hashtable)
	 */
    @Override
    public void init(Hashtable<String,String> attributes){
        if(attributes!=null){
            super.init(attributes);
            if(attributes.containsKey("foodLevel"))
                foodLevel = Integer.parseInt(attributes.get("foodLevel"));
        }
    }

    /* (non-Javadoc)
     * @see entities.Entity#getJson()
     */
    public JSONObject getJson(){
        JSONObject json = super.getJson();
        json.put("foodLevel", foodLevel);
        return json;
    }

    /* (non-Javadoc)
     * @see entities.Edible#getFoodLevel()
     */
    @Override
    public int getFoodLevel() {
        return foodLevel;
    }

}
