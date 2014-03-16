package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import entities.Rock;
import world.Chunk;
import world.World;
import json.JSONArray;

public class Tester {
	public static void main(String[] args){
		
		try{
			Rock.staticLoad();
			World w = new World();
			w.loadChunks("world/");
			w.loadEntities("world/");
			w.save("world/");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
