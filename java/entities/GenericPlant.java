package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

public class GenericPlant extends Plant {
	public final static String TYPE="genericPlant";
	public GenericPlant(int identification, int x, int y, Chunk c, World w) {
		super(identification, x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	public GenericPlant(int identification, int x, int y, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, x, y, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	public GenericPlant(int identification, Chunk c, World w, String jsonString) {
		super(identification, c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	public GenericPlant(int identification, Chunk c, World w, JSONObject json) {
		super(identification, c, w, json);
		// TODO Auto-generated constructor stub
	}

	public GenericPlant(int identification, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("type", TYPE);
		return json;
	}
	public String getType(){
		return "genericPlant";
	}
	public static void staticLoad() {
		EntityTypeManager.storeType(TYPE, GenericPlant.class);

	}
	
	public static Hashtable<String, String> getStandard() {
		Hashtable<String,String> table= new Hashtable<String,String>();
		table.put("foodLevel", ((int)Math.random()*5)+"");
		return table;
	}
	public int getMaxFoodLevel(){
		return 50;
	}

}
