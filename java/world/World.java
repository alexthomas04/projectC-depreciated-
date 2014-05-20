package world;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.lang.reflect.*;
import java.util.Hashtable;
import java.util.Map;

import json.JSONArray;
import json.JSONObject;
import lombok.Getter;
import entities.Entity;
import entities.EntityTypeManager;
import entities.Player;


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
	 * Gets the players.
	 *
	 * @return the players
	 */
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
                Chunk c = getChunk(chunkId);
				String entityType = entityData.optString("type");
                spawnEntity(entityType,c,entityData);
            }
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Did you forget to static load something ...");
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * Load players.
	 *
	 * @param dir the dir
	 * @return true, if successful
	 */
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
                Chunk c = getChunk(chunkId);
                String entityType = entityData.optString("type");
				spawnEntity(entityType,c,entityData);

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
	
	/**
	 * Gets the chunk.
	 *
	 * @param id the id
	 * @return the chunk
	 */
	public Chunk getChunk(int id){
		return chunks.get(id);
	}
	
	/**
	 * Gets the entities.
	 *
	 * @return the entities
	 */
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	/**
	 * Adds the entity.
	 *
	 * @param e the e
	 */
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	/**
	 * Removes the entity.
	 *
	 * @param e the e
	 */
	public void removeEntity(Entity e){
		entities.remove(e);
	}
	
	/**
	 * Adds the player.
	 *
	 * @param p the p
	 */
	public void addPlayer(Player p){
		players.add(p);
	}
	
	/**
	 * Tick.
	 */
	public void tick(){
		for(Entity e : entities)
			e.tick();
		for(Player p : players)
			p.tick();
	}
	
	/**
	 * Gets the player.
	 *
	 * @param name the name
	 * @return the player
	 */
	public Player getPlayer(String name){
		for(Player p : players){
			if(p.getName()!=null && p.getName().equals(name))
				return p;
		}
		return null;
	}
	
	/**
	 * Checks for player.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public boolean hasPlayer(String name){
		return !(getPlayer(name)==null);
	}

	public int getNumEntities(){
		return players.size()+entities.size();
	}

    public  Entity spawnEntity(String entityType,int x,int y,Chunk c){
        try{
        Class type = EntityTypeManager.GetEntityType(entityType);
        Method getStandards = type.getMethod("getStandard");
        Hashtable<String,String> standards = (Hashtable<String,String>) getStandards.invoke(null);
        Entity e = (Entity) type.getConstructor(Integer.TYPE,Integer.TYPE,Integer.TYPE,Chunk.class,World.class, Hashtable.class).newInstance(getNumEntities(),x,y,c,this,standards);
            return  e;
        }catch(NoSuchMethodException nsme){
            nsme.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public  Entity spawnEntity(String entityType,int x,int y,Chunk c,Hashtable<String,String> attr){
        try{
            Class type = EntityTypeManager.GetEntityType(entityType);
            Method getStandards = type.getMethod("getStandard");
            Hashtable<String,String> standards = (Hashtable<String,String>) getStandards.invoke(null);
            for (Map.Entry<String, String> entry : attr.entrySet()) {
                standards.put(entry.getKey(),entry.getValue());
            }
            Entity e = (Entity) type.getConstructor(Integer.TYPE,Integer.TYPE,Integer.TYPE,Chunk.class,World.class, Hashtable.class).newInstance(getNumEntities(),x,y,c,this,standards);
            return  e;
        }catch(NoSuchMethodException nsme){
            nsme.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public  Entity spawnEntity(String entityType,Chunk c,JSONObject entityData){
        try{
            Class type = EntityTypeManager.GetEntityType(entityType);
            Entity e = (Entity) type.getConstructor(Integer.TYPE,Chunk.class,World.class,JSONObject.class).newInstance(getNumEntities(),c,this,entityData);
            return  e;
        }catch(NoSuchMethodException nsme){
            nsme.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
