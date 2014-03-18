package entities;

import java.util.Hashtable;
import java.util.Iterator;

import json.JSONObject;
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
	protected int locX=0,locY=0,id;
	
	/** The chunk. */
	protected Chunk chunk;
	
	/** The world. */
	protected World world;
	
	/** The Constant TYPE. */
	protected final static String TYPE="ABSTRACT";
	
	/**
	 * Instantiates a new entity.
	 *
	 * @param x the x coordinate 
	 * @param y the y coordinate
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 */
	public Entity(int x,int y,Chunk c,World w){
		locX=x;
		locY=y;
		chunk=c;
		world=w;
		init(null);
		
	}
	
	/**
	 * Instantiates a new entity.
	 *
	 * @param c the Chunk that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param jsonString the JSON data
	 */
	public Entity(Chunk c,World w,String jsonString){
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
	 * @param c the Chunk d that the entity will be put in
	 * @param w the World that the entity will be put in
	 * @param json the JSON data
	 */
	public Entity(Chunk c,World w,JSONObject json){
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
		if(attributes.containsKey("id"))
			id = Integer.parseInt(attributes.get("id"));
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
		json.put("id", id);
		json.put("chunk", chunk.getId());
		
		
		return json;
	}
	
	
	

}
