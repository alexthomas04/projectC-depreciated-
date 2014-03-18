package world;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import json.JSONArray;
import json.JSONObject;
import entities.Entity;
import entities.EntityTypeManager;
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
	
	/**
	 * Instantiates a new world.
	 *
	 * 
	 */
	public World(){	}
	
	/**
	 * Instantiates a new world.
	 *
	 * @param numChunks the num chunks
	 */
	public World(int numChunks){
		for(int i=0;i<numChunks;i++){
			genNewChunk(i);
		}
		Rock r = new Rock(5,93,chunks.get(0),this);
		Rock n = new Rock(54,33,chunks.get(0),this);
		entities.add(n);
		entities.add(r);
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
			for(int i=0;i<entityArray.length();i++){
				JSONObject entityData = entityArray.getJSONObject(i);
				int chunkId=entityData.optInt("chunk");
				Class type = EntityTypeManager.GetEntityType(entityData.optString("type"));
				Entity e = (Entity) type.getConstructor(Chunk.class,World.class,JSONObject.class).newInstance(chunks.get(chunkId),this,entityData);
				entities.add(e);
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
		chunks.add(new Chunk(Chunk.DEFAULT_X,Chunk.DEFAULT_Y,id));
	}
	

}
