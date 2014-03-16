package world;

import java.util.ArrayList;
import java.util.Hashtable;

import entities.Entity;
import json.JSONArray;
import json.JSONObject;

public class Chunk {
	/////////////////Feilds\\\\\\\\\\\\\\\\\
	private int sizeX=0,sizeY=0,id;
	private Block[][] blocks;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	public static final int DEFAULT_X=100,DEFAULT_Y=100;
	
	
	////////////////Constructors\\\\\\\\\\\\\\
	public Chunk(String jsonString){
		JSONObject json = new JSONObject(jsonString);
		sizeX = json.optInt("sizeX",0);
		sizeY = json.optInt("sizeY",0);
		id = json.optInt("id");
		blocks = new Block[sizeX][sizeY];
		JSONArray jsonColumns = json.optJSONArray("blocks");
		for(int x=0;x<jsonColumns.length();x++){
			JSONArray jsonCells = jsonColumns.optJSONArray(x);
			for(int y=0; y<jsonCells.length();y++){
				blocks[x][y]=new Block(jsonCells.optString(y));
			}
		}
		System.out.println("done");
		
	}
	public Chunk(int x,int y,int identification){
		sizeX=x;
		sizeY=y;
		id=identification;
		blocks = new Block[x][y];
		for(int i=0;i<x;i++){
			for(int k=0;k<y;k++){
				Hashtable<String,String> attributes = new Hashtable<String,String>();
				attributes.put("temperature", 0.0+"");
				attributes.put("humidity", 0.0+"");
				attributes.put("life", 0.0+"");
				blocks[i][k]= new Block(attributes);
			}
		}
	}
	
	
	////////////////Accessers\\\\\\\\\\\\\
	public int getId(){
		return id;
	}
	public ArrayList<Entity> getEntites(){
		return entities;
	}
	
	
	
	
	///////////////Methods\\\\\\\\\\\\\\\
	public void addEntity(Entity e,int x,int y){
		entities.add(e);
		blocks[x][y].addEntity(e);
	}
	public void removeEntity(Entity e,int x, int y){
		blocks[x][y].removeEntity(e);
	}
	public void removeEntities(int x , int y){
		blocks[x][y].removeEntities();
	}
	
	
	
	
	
	
	
	
	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		json.put("sizeX", sizeX);
		json.put("sizeY", sizeY);
		json.put("id",id);
		JSONArray[] columns = new JSONArray[sizeX];
		for(int i=0;i<sizeX;i++){
			columns[i]=new JSONArray();
			for(int k=0;k<sizeY;k++){
				columns[i].put(blocks[i][k].getJson());
			}
		}
		json.put("blocks", columns);
		
		
		
		
		return json;
	}
}
