package world;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import entities.Entity;
import json.JSONObject;

public class Block {
		private double temperature=0,humidity=0,life=0;
		private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Block(String jsonString){
		JSONObject json = new JSONObject(jsonString);
		Iterator itr = json.keys();
		Hashtable<String,String> attributes = new Hashtable<String,String>();
		while(itr.hasNext()){
			String key = (String) itr.next();
			attributes.put(key, json.optString(key));
		}
		init(attributes);
		
	}
	
	public Block(Hashtable<String,String> attributes){
		init(attributes);
	}
	
	private void init(Hashtable<String,String> attributes){
		if(attributes.containsKey("temperature"))
			temperature = Double.parseDouble(attributes.get("temperature"));
		if(attributes.containsKey("humidity"))
			humidity = Double.parseDouble(attributes.get("humidity"));
		if(attributes.containsKey("life"))
			life = Double.parseDouble(attributes.get("life"));
	}
	public void addEntity(Entity e){
		entities.add(e);
	}
	public void removeEntity(Entity e){
		entities.remove(e);
	}
	public void removeEntities(){
		entities.clear();
	}
	
	
	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		json.put("temperature", temperature);
		json.put("humidity", humidity);
		json.put("life", life);
		return json;
	}
	
	

}