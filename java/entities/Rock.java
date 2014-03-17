package entities;

import json.JSONObject;
import world.Chunk;
import world.World;

public class Rock extends Entity {
	private final static String TYPE="rock";
	public Rock(int x, int y, Chunk c, World w) {
		super(x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	public Rock(Chunk c, World w, String jsonString) {
		super(c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	public Rock(Chunk c, World w, JSONObject json) {
		super(c, w, json);
		// TODO Auto-generated constructor stub
	}

	public static void staticLoad() {
		EntityTypeManager.storeType(TYPE, Rock.class);

	}
	
	@Override
	public boolean move(Chunk c,int x,int y){
		return false;
	}
	
	@Override
	public boolean move(int x,int y){
		return false;
	}
	
	@Override
	public JSONObject getJson(){
		JSONObject json = super.getJson();
		json.put("type", TYPE);
		return json;
	}

}
