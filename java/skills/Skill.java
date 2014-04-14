package skills;

import json.JSONObject;
import lombok.Getter;
import entities.Entity;
import entities.Player;

public class Skill{
	@Getter
	private long	currentXP		= 0;
	@Getter
	private long	nextLevelXP		= 0;
	@Getter
	private int		currentLevel	= 0;
	private Player	player;
	private boolean	isPlayer		= false;
	private String	name			= "";

	public Skill(int level, long xp, String s, Player p){
		currentLevel = level;
		currentXP = xp;
		player = p;
		isPlayer = true;
		name = s;
		calcNextLevel();

	}
	public Skill(int level){
		currentLevel = level;
	}
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
	public Skill(String jsonString, Entity e){
		this(new JSONObject(jsonString), e);
	}
	private void calcNextLevel(){
		nextLevelXP = (long) Math.pow(currentLevel, 5);
	}
	public double progress(){
		return 1 - ((double) (nextLevelXP - currentXP) / nextLevelXP);
	}
	public void addXP(long xp){
		currentXP += xp;
		if (isPlayer && currentXP >= nextLevelXP){
			currentXP = currentXP % nextLevelXP;
			currentLevel++;
			calcNextLevel();

			player.levelUp(name, currentLevel);
		}
	}
	public JSONObject getJSON(){
		JSONObject json = new JSONObject();
		json.put("currentLevel", currentLevel);
		json.put("currentXP", currentXP);
		json.put("name", name);
		return json;
	}
	public String toString(){
		return getJSON().toString(1);
	}

}
