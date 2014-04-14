/**
 * 
 */
package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;

/**
 * @author alexthomas
 *
 */
public abstract class Animal extends Entity {

	/**
	 * @param identification
	 * @param x
	 * @param y
	 * @param c
	 * @param w
	 */
	public Animal(int identification, int x, int y, Chunk c, World w) {
		super(identification, x, y, c, w);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param identification
	 * @param x
	 * @param y
	 * @param c
	 * @param w
	 * @param attributes
	 */
	public Animal(int identification, int x, int y, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, x, y, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param identification
	 * @param c
	 * @param w
	 * @param jsonString
	 */
	public Animal(int identification, Chunk c, World w, String jsonString) {
		super(identification, c, w, jsonString);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param identification
	 * @param c
	 * @param w
	 * @param json
	 */
	public Animal(int identification, Chunk c, World w, JSONObject json) {
		super(identification, c, w, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param identification
	 * @param c
	 * @param w
	 * @param attributes
	 */
	public Animal(int identification, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, c, w, attributes);
		// TODO Auto-generated constructor stub
	}

}
