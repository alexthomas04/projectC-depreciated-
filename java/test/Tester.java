package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import entities.Rock;
import entities.RockWithLegs;
import world.Chunk;
import world.World;
import json.JSONArray;

public class Tester {
	public static void main(String[] args){
		
		try{
			Rock.staticLoad();
			World w = new World(2);
			//w.loadChunks("world/");
			//w.loadEntities("world/")
			Chunk c = w.getChunk(0);
			Rock r = new Rock(0,10,10,c,w);
			RockWithLegs rwl = new RockWithLegs(0,15,10,c,w);
			w.addEntity(rwl);
			w.addEntity(r);
			w.save("world/");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
