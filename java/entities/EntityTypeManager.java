/**
 * 
 */
package entities;

import java.util.HashMap;
import java.util.Map;

import json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityTypeManager.
 *
 * @author Alex Thomas
 */
public class EntityTypeManager {
	
	/** The entity types. */
	private static Map<String,Class> entityTypes = new HashMap<String,Class>();
	
	/**
	 * Store type.
	 *
	 * @param name the type as stored in the JSON data
	 * @param c the Class of entity associated with that type
	 */
	public static void storeType(String name,Class c){
		entityTypes.put(name, c);
	}
	
	/**
	 * Gets the entity type.
	 *
	 * @param type the type
	 * @return the class
	 */
	public static Class GetEntityType(String type){
		return entityTypes.get(type);
		
		
		
	}
	
}
