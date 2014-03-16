/**
 * 
 */
package entities;

import java.util.HashMap;
import java.util.Map;

import json.JSONObject;

/**
 * @author Alex Thomas
 *
 */
public class EntityTypeManager {
	private static Map<String,Class> entityTypes = new HashMap<String,Class>();
	
	public static void storeType(String name,Class c){
		entityTypes.put(name, c);
	}
	public static Class GetEntityType(String type){
		return entityTypes.get(type);
		
		
		
	}
	
}
