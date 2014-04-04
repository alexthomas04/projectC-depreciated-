package world;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import json.JSONArray;
import json.JSONObject;
import lombok.Getter;
import entities.Entity;
import entities.EntityTypeManager;
import entities.Player;
import entities.Rock;

// TODO: Auto-generated Javadoc
/**
 * The Class World.
 */
public class World {
	
	/** The Constant FILE_EXTENTION. */
	private static final String FILE_EXTENTION = ".json";
	
	/** The entities. */
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	/** The chunks. */
	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	
	@Getter private ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * Instantiates a new world.
	 *
	 * 
	 */
	public World(){	}
	
	/**
	 * Instantiates a new world.
	 *
	 * @param numChunks the number chunks
	 */
	public World(int numChunks){
		for(int i=0;i<numChunks;i++){
			genNewChunk(i);
		}
	}
	
	/**
	 * Load chunks.
	 *
	 * @param dir the dir
	 * @return true, if successful
	 */
	public boolean loadChunks(String dir){
		try{
			File directory = new File(dir);
			if(!directory.isDirectory())
			{
				return false;
			}
			int id=0;
			File chunk = new File(dir+"chunk"+id+FILE_EXTENTION);
			while(chunk.exists()){
				char[] charBuffer = new char[(int)chunk.length()];
				new FileReader(chunk).read(charBuffer);
				chunks.add(new Chunk(new String(charBuffer)));
				id++;
				chunk = new File(dir+"chunk"+id+FILE_EXTENTION);
			}
			if(id==0)
				return false;
			
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Load entities.
	 *
	 * @param dir The Directory of the entities(FILE_EXTENTION) file
	 * @return If entities were loaded
	 */
	public boolean loadEntities(String dir){
		try{
			File directory = new File(dir);
			if(!directory.exists()){
				directory.mkdir();
			}
			if(!directory.isDirectory())
			{
				return false;
			}
			File f = new File(dir+"entities"+FILE_EXTENTION);
			char[] charBuffer = new char[(int)f.length()];
			new FileReader(f).read(charBuffer);
			JSONArray entityArray = new JSONArray(new String(charBuffer));
			if(entityArray.length()==0)
				return false;
			for(int i=0;i<entityArray.length();i++){
				JSONObject entityData = entityArray.getJSONObject(i);
				int chunkId=entityData.optInt("chunk");
				Class type = EntityTypeManager.GetEntityType(entityData.optString("type"));
				Entity e = (Entity) type.getConstructor(Integer.TYPE,Chunk.class,World.class,JSONObject.class).newInstance(entities.size(),chunks.get(chunkId),this,entityData);
				entities.add(e);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Did you forget to static load somthing ....");
			return false;
		}
		
		return true;
		
	}
	
	public boolean loadPlayers(String dir){
		try{
			File directory = new File(dir);
			if(!directory.exists()){
				directory.mkdir();
			}
			if(!directory.isDirectory())
			{
				return false;
			}
			File f = new File(dir+"players"+FILE_EXTENTION);
			char[] charBuffer = new char[(int)f.length()];
			new FileReader(f).read(charBuffer);
			JSONArray playerArray = new JSONArray(new String(charBuffer));
			if(playerArray.length()==0)
				return false;
			for(int i=0;i<playerArray.length();i++){
				JSONObject entityData = playerArray.getJSONObject(i);
				int chunkId=entityData.optInt("chunk");
				Class type = EntityTypeManager.GetEntityType(entityData.optString("type"));
				for(Object o : type.getConstructors())
					System.out.println(o.toString());
				Player p = (Player) type.getConstructor(Integer.TYPE,Chunk.class,World.class,JSONObject.class).newInstance(entities.size()+players.size(),chunks.get(chunkId),this,entityData);
				players.add(p);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * Save.
	 *
	 * @param dir the dir
	 * @return true, if successful
	 */
	public boolean save(String dir){
		try{
			File directory = new File(dir);
			if(!directory.exists()){
				directory.mkdir();
			}
			for(int i=0;i<chunks.size();i++){
				String data = chunks.get(i).getJson().toString();
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dir+"chunk"+i+FILE_EXTENTION)));
				bw.write(data);
				bw.flush();
				bw.close();
			}
			JSONArray entityArray = new JSONArray();
			for(Entity e : entities){
				entityArray.put(e.getJson());
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dir+"entities"+FILE_EXTENTION)));
			bw.write(entityArray.toString());
			bw.flush();
			bw.close();
			
			JSONArray playerArray = new JSONArray();
			for(Player p : players){
				playerArray.put(p.getJson());
			}
			 bw = new BufferedWriter(new FileWriter(new File(dir+"players"+FILE_EXTENTION)));
			bw.write(playerArray.toString());
			bw.flush();
			bw.close();
		}catch(Exception ex){
			return false;
		}
		return true;
	}
	
	/**
	 * Generate new chunk.
	 *
	 * @param id the id of the chunk
	 */
	public void genNewChunk(int id){
		chunks.add(Chunk.generateRandomChunk(100 , 100, id, this, 1));
	}
	
	public Chunk getChunk(int id){
		return chunks.get(id);
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	public void addEntity(Entity e){
		entities.add(e);
	}
	public void removeEntity(Entity e){
		entities.remove(e);
	}
	public void addPlayer(Player p){
		players.add(p);
	}
	public void tick(){
		for(Entity e : entities)
			e.tick();
		for(Player p : players)
			p.tick();
	}
	
	public Player getPlayer(String name){
		for(Player p : players){
			if(p.getName()!=null && p.getName().equals(name))
				return p;
		}
		return null;
	}
	
	public boolean hasPlayer(String name){
		return !(getPlayer(name)==null);
	}

}
