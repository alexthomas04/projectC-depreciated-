package world;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import entities.Entity;
import json.JSONObject;
import lombok.Getter;
import lombok.Setter;


/**
 * The Class Block.
 */
public class Block {
		
		/** The temperature. */
		
		/**
		 * Gets the temperature.
		 *
		 * @return the temperature
		 */
		@Getter 
 /**
  * Sets the temperature.
  *
  * @param temperature the new temperature
  */
 @Setter private double temperature=0;
		
		/** The humidity. */
		
		/**
		 * Gets the humidity.
		 *
		 * @return the humidity
		 */
		@Getter 
 /**
  * Sets the humidity.
  *
  * @param humidity the new humidity
  */
 @Setter private double humidity=0;
		
		/** The life. */
		
		/**
		 * Gets the life.
		 *
		 * @return the life
		 */
		@Getter 
 /**
  * Sets the life.
  *
  * @param life the new life
  */
 @Setter private double life=0;
		
		/** The entities on that block. */
		
		/**
		 * Gets the entities.
		 *
		 * @return the entities
		 */
		@Getter private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * Instantiates a new block.
	 *
	 * @param jsonString the json string
	 */
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
	
	/**
	 * Instantiates a new block.
	 *
	 * @param attributes the attributes
	 */
	public Block(Hashtable<String,String> attributes){
		init(attributes);
	}
	
	/**
	 * Inits the attributes.
	 *
	 * @param attributes the attributes
	 */
	private void init(Hashtable<String,String> attributes){
		if(attributes.containsKey("temperature"))
			temperature = Double.parseDouble(attributes.get("temperature"));
		if(attributes.containsKey("humidity"))
			humidity = Double.parseDouble(attributes.get("humidity"));
		if(attributes.containsKey("life"))
			life = Double.parseDouble(attributes.get("life"));
	}
	
	/**
	 * Adds the entity.
	 *
	 * @param e the e
	 */
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	/**
	 * Removes the entity.
	 *
	 * @param e the e
	 */
	public void removeEntity(Entity e){
		entities.remove(e);
	}
	
	/**
	 * Removes the entities.
	 */
	public void removeEntities(){
		entities.clear();
	}
	
	
	/**
	 * Gets the block as json.
	 *
	 * @return the json
	 */
	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		json.put("temperature", temperature);
		json.put("humidity", humidity);
		json.put("life", life);
		return json;
	}
	
	
	

}
