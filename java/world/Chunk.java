package world;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import entities.Entity;
import entities.GenericPlant;
import entities.Rock;
import entities.RockWithLegs;
import json.JSONArray;
import json.JSONObject;
import lombok.Getter;

// TODO: Auto-generated Javadoc
/**
 * The Class Chunk.
 */
public class Chunk {
	/////////////////Feilds\\\\\\\\\\\\\\\\\
	/** The id. */
	@Getter private int id;
	@Getter private int sizeX=0;
	@Getter private int	sizeY=0;	
	
	/** The blocks. */
	private Block[][] blocks;
	
	/** The entities. */
	@Getter private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/** The Constant DEFAULT_X. */
	public static final int DEFAULT_X=100;
	
	/** The Constant DEFAULT_Y. */
	public static final int DEFAULT_Y=100;
	
	
	////////////////Constructors\\\\\\\\\\\\\\
	/**
	 * Instantiates a chunk from JSON.
	 *
	 * @param jsonString the JSON string
	 */
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
	
	/**
	 * Instantiates a new chunk.
	 *
	 * @param x the width
	 * @param y the height
	 * @param identification the id 
	 */
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
	
	

	
	
	
	
	///////////////Methods\\\\\\\\\\\\\\\
	/**
	 * Adds the entity.
	 *
	 * @param e the entity that is being added
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void addEntity(Entity e,int x,int y){
		entities.add(e);
		blocks[x][y].addEntity(e);
	}
	
	/**
	 * Removes the entity.
	 *
	 * @param e the entity
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void removeEntity(Entity e,int x, int y){
		blocks[x][y].removeEntity(e);
	}
	
	/**
	 * Removes the all the entities from the block.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void removeEntities(int x , int y){
		blocks[x][y].removeEntities();
	}
	
	public ArrayList<Entity> getEntities(int x,int y){
		ArrayList<Entity> list = new ArrayList<Entity>();
		for(Entity e : blocks[x][y].getEntities())
			list.add(e);
		return list;
	}
	
	
	public static Chunk generateRandomChunk(int x,int y,int identification,World w,int itterations){
		Chunk c = new Chunk(x,y,identification);
		Random rand = new Random();
		for(int z=0;z<itterations;z++){
			for(int i=0;i<x;i++){
				for(int k=0;k<y;k++){
					int times = rand.nextInt(5);
					for(int q=0;q<times;q++){
					if(rand.nextInt(5)==1){
						Entity e = new Rock(w.getEntities().size(),i,k,c,w,Rock.getStandard());
						w.addEntity(e);
						c.addEntity(e, i, k);
						}
					if(rand.nextInt(5)==1){
						Entity e =new RockWithLegs(w.getEntities().size(),i,k,c,w,RockWithLegs.getStandard());
						w.addEntity(e);
						c.addEntity(e, i, k);
						}
					if(rand.nextInt(5)==1){
						Entity e =new GenericPlant(w.getEntities().size(),i,k,c,w,GenericPlant.getStandard());
						w.addEntity(e);
						c.addEntity(e, i, k);
						}
					}
				}
			}
		}
		return c;
	}
	
	
	
	
	
	/**
	 * Gets the json.
	 *
	 * @return the json
	 */
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
