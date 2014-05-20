package skills;

import json.JSONObject;
import lombok.Getter;
import entities.Entity;
import entities.Player;


/**
 * The Class Skill.
 */
public class Skill{
	
	/**
	 * Gets the current xp.
	 *
	 * @return the current xp
	 */
	@Getter
	private long	currentXP		= 0;
	
	/**
	 * Gets the next level xp.
	 *
	 * @return the next level xp
	 */
	@Getter
	private long	nextLevelXP		= 0;
	
	/**
	 * Gets the current level.
	 *
	 * @return the current level
	 */
	@Getter
	private int		currentLevel	= 0;
	
	/** The player. */
	private Player	player;
	
	/** The is player. */
	private boolean	isPlayer		= false;
	
	/** The name. */
	private String	name			= "";

	/**
	 * Instantiates a new skill.
	 *
	 * @param level the level
	 * @param xp the xp
	 * @param s the skill name
	 * @param p the player
	 */
	public Skill(int level, long xp, String s, Player p){
		currentLevel = level;
		currentXP = xp;
		player = p;
		isPlayer = true;
		name = s;
		calcNextLevel();

	}
	
	/**
	 * Instantiates a new skill.
	 *
	 * @param level the level
	 */
	public Skill(int level){
		currentLevel = level;
	}
	
	/**
	 * Instantiates a new skill.
	 *
	 * @param json the json
	 * @param e the owner of the skill
	 */
	public Skill(JSONObject json, Entity e){
		currentLevel = json.optInt("currentLevel");
		currentXP = json.optLong("currentXP");
		name = json.optString("name");
		if (e instanceof Player){
			player = (Player) e;
			isPlayer = true;
		}
		calcNextLevel();
	}
	
	/**
	 * Instantiates a new skill.
	 *
	 * @param jsonString the json string
	 * @param e the owner of the skill
	 */
	public Skill(String jsonString, Entity e){
		this(new JSONObject(jsonString), e);
	}
	
	/**
	 * Calculates the next level.
	 */
	private void calcNextLevel(){
		nextLevelXP = (long) Math.pow(currentLevel, 5);
	}
	
	/**
	 * Progress.
	 *
	 * @return the progress
	 */
	public double progress(){
		return 1 - ((double) (nextLevelXP - currentXP) / nextLevelXP);
	}
	
	/**
	 * Adds the xp.
	 *
	 * @param xp the xp
	 */
	public void addXP(long xp){
		currentXP += xp;
		if (isPlayer && currentXP >= nextLevelXP){
			currentXP = currentXP % nextLevelXP;
			currentLevel++;
			calcNextLevel();

			player.levelUp(name, currentLevel);
		}
	}
	
	/**
	 * Gets the json.
	 *
	 * @return the json
	 */
	public JSONObject getJSON(){
		JSONObject json = new JSONObject();
		json.put("currentLevel", currentLevel);
		json.put("currentXP", currentXP);
		json.put("name", name);
		return json;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return getJSON().toString(1);
	}

}
