/**
 * 
 */
package entities;

import java.util.Hashtable;

import json.JSONObject;
import world.Chunk;
import world.World;


/**
 * The Class Animal.
 *
 * @author alexthomas
 */
public abstract class Animal extends Entity {

	/**
	 * Instantiates a new animal.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 */
	public Animal(int identification, int x, int y, Chunk c, World w) {
		super(identification, x, y, c, w);
	}

	/**
	 * Instantiates a new animal.
	 *
	 * @param identification the identification
	 * @param x the x
	 * @param y the y
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public Animal(int identification, int x, int y, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, x, y, c, w, attributes);
	}

	/**
	 * Instantiates a new animal.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param jsonString the json string
	 */
	public Animal(int identification, Chunk c, World w, String jsonString) {
		super(identification, c, w, jsonString);
	}

	/**
	 * Instantiates a new animal.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param json the json
	 */
	public Animal(int identification, Chunk c, World w, JSONObject json) {
		super(identification, c, w, json);
	}

	/**
	 * Instantiates a new animal.
	 *
	 * @param identification the identification
	 * @param c the c
	 * @param w the w
	 * @param attributes the attributes
	 */
	public Animal(int identification, Chunk c, World w,
			Hashtable<String, String> attributes) {
		super(identification, c, w, attributes);
	}

}
