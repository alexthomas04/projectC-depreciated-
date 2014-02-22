package world;

import java.util.Hashtable;

import json.JSONObject;

public class Block {
		private double temperature=0,humidity=0,life=0;
	
	public Block(String json){
		
	}
	
	public Block(Hashtable<String,String> attributes){
		if(attributes.containsKey("temperature"))
			temperature = Double.parseDouble(attributes.get("temperature"));
		if(attributes.containsKey("humidity"))
			humidity = Double.parseDouble(attributes.get("humidity"));
		if(attributes.containsKey("life"))
			life = Double.parseDouble(attributes.get("life"));
	}
	
	
	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		json.put("temperature", temperature);
		json.put("humidity", humidity);
		json.put("life", life);
		return json;
	}
	
	

}
