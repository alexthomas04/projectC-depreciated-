package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import entities.Player;
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
			Rock r1 = new Rock(3,10,12,c,w);
			RockWithLegs rwl = new RockWithLegs(1,15,10,c,w);
			Player p = new Player(2,10,11,c,w);
			w.addEntity(rwl);
			w.addEntity(r);
			w.addEntity(r1);
			w.addEntity(p);
			System.out.println(r.getType());
			Scanner s = new Scanner(System.in);
			while(true){
				w.save("world/");
				System.out.println(p.handleCommand(s.nextLine().split(" ")));
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
