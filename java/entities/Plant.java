package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

public abstract class Plant extends Entity implements Edible {

	protected int foodLevel=0;
	public Plant(int identification, int x, int y, Chunk c, World w) {
		super(identification, x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	public Plant(int identification, int x, int y, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, x, y, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	public Plant(int identification, Chunk c, World w, String jsonString) {
		super(identification, c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	public Plant(int identification, Chunk c, World w, JSONObject json) {
		super(identification, c, w, json);
		// TODO Auto-generated constructor stub
	}

	public Plant(int identification, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, c, w, attributes);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(Hashtable<String,String> attributes){
		if(attributes!=null){
			super.init(attributes);
			if(attributes.containsKey("foodLevel"))
				foodLevel = Integer.parseInt(attributes.get("foodLevel"));
		}
	}
	
	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("foodLevel", foodLevel);
		return json;
	}
	
	@Override
	public int getFoodLevel() {
		// TODO Auto-generated method stub
		return foodLevel;
	}
	
	@Override 
	public void tick(){
		if(foodLevel!=getMaxFoodLevel())
		foodLevel++;
	}

}
