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

public class World {
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	
	/**
	 * @param dir The directory of the chunk{#}.atf files
	 * @return If the chunks were loaded
	 */
	public World(){	}
	public World(int numChunks){
		for(int i=0;i<numChunks;i++){
			genNewChunk(i);
		}
		Rock r = new Rock(5,93,chunks.get(0),this);
		Rock n = new Rock(54,33,chunks.get(0),this);
		entities.add(n);
		entities.add(r);
	}
	
	public boolean loadChunks(String dir){
		try{
			File directory = new File(dir);
			if(!directory.isDirectory())
			{
				return false;
			}
			int id=0;
			File chunk = new File(dir+"chunk"+id+".atf");
			while(chunk.exists()){
				char[] charBuffer = new char[(int)chunk.length()];
				new FileReader(chunk).read(charBuffer);
				chunks.add(new Chunk(new String(charBuffer)));
				id++;
				chunk = new File(dir+"chunk"+id+".atf");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * @param dir The Directory of the entities.atf file
	 * @return If entities were loaded 
	 */
	public boolean loadEntities(String dir){
		try{
			File directory = new File(dir);
			if(!directory.isDirectory())
			{
				return false;
			}
			File f = new File(dir+"entities.atf");
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
	
	public boolean save(String dir){
		try{
			for(int i=0;i<chunks.size();i++){
				String data = chunks.get(i).getJson().toString();
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dir+"chunk"+i+".atf")));
				bw.write(data);
				bw.flush();
				bw.close();
			}
			JSONArray entityArray = new JSONArray();
			for(Entity e : entities){
				entityArray.put(e.getJson());
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dir+"entities.atf")));
			bw.write(entityArray.toString());
			bw.flush();
			bw.close();
		}catch(Exception ex){
			return false;
		}
		return true;
	}
	
	public void genNewChunk(int id){
		chunks.add(new Chunk(Chunk.DEFAULT_X,Chunk.DEFAULT_Y,id));
	}
	

}
