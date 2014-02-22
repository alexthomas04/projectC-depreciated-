package world;

import java.util.Hashtable;

import json.JSONArray;
import json.JSONObject;

public class Chunk {
	private int sizeX=0,sizeY=0,id;
	private Block[][] blocks;
	public Chunk(String json){
		JSONObject j = new JSONObject(json);
		sizeX = j.optInt("sizeX",0);
		sizeY = j.optInt("sizeY",0);
		blocks = new Block[sizeX][sizeY];
		JSONArray jsonColumns = j.optJSONArray("blocks");
		for(int x=0;x<jsonColumns.length();x++){
			JSONArray jsonCells = jsonColumns.optJSONArray(x);
			for(int y=0; y<jsonCells.length();y++){
				blocks[x][y]=new Block(jsonCells.optString(y));
			}
		}
		
	}
	public Chunk(int x,int y){
		sizeX=x;
		sizeY=y;
		blocks = new Block[x][y];
		for(int i=0;i<x;i++){
			for(int k=0;k<y;k++){
				Hashtable<String,String> attributes = new Hashtable<String,String>();
				attributes.put("temperature", Math.random()+"");
				attributes.put("humidity", Math.random()+"");
				attributes.put("life", Math.random()+"");
				blocks[i][k]= new Block(attributes);
			}
		}
	}
	
	
	
	
	
	
	public JSONObject getJson(){
		JSONObject json = new JSONObject();
		json.put("sizeX", sizeX);
		json.put("sizeY", sizeY);
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
