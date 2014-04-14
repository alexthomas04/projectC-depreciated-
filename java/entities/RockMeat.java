package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

public class RockMeat extends Entity implements Edible{
	public final static String	TYPE	= "rock meat";
	public RockMeat(int identification, int x, int y, Chunk c, World w){
		super(identification, x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	public RockMeat(int identification, int x, int y, Chunk c, World w, Hashtable<String, String> attributes){
		super(identification, x, y, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	public RockMeat(int identification, Chunk c, World w, String jsonString){
		super(identification, c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	public RockMeat(int identification, Chunk c, World w, JSONObject json){
		super(identification, c, w, json);
		// TODO Auto-generated constructor stub
	}

	public RockMeat(int identification, Chunk c, World w, Hashtable<String, String> attributes){
		super(identification, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getFoodLevel(){
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getMaxFoodLevel(){
		// TODO Auto-generated method stub
		return 10;
	}
	
	public String getType(){
		return "rock meat";
	}
	public static void staticLoad(){
		EntityTypeManager.storeType(TYPE, Player.class);

	}

}
