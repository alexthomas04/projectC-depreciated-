package entities;

import java.util.Hashtable;
import java.util.Iterator;

import json.JSONObject;
import world.Chunk;
import world.World;

public abstract class Entity {
	protected int locX=0,locY=0,id;
	protected Chunk chunk;
	protected World world;
	protected final static String TYPE="ABSTRACT";
	
	public Entity(int x,int y,Chunk c,World w){
		locX=x;
		locY=y;
		chunk=c;
		world=w;
		init(null);
		
	}
	
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
	
	
	public int getX(){
		return locX;
	}
	public int getY(){
		return locY;
	}
	public int getId(){
		return id;
	}
	public Chunk getChunk(){
		return chunk;
	}
	
	
	
	
	public static void staticLoad(){
		EntityTypeManager.storeType(TYPE, Entity.class);
	}
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
	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		json.put("locationX",locX);
		json.put("locationY",locY);
		json.put("id", id);
		json.put("chunk", chunk.getId());
		
		
		return json;
	}
	
	
	

}
