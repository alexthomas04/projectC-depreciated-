package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import entities.GenericPlant;
import entities.Plant;
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
			RockWithLegs.staticLoad();
			Player.staticLoad();
			Plant.staticLoad();
			GenericPlant.staticLoad();
			World w = new World();
			if(!w.loadChunks("world/")){
				w=new World(2);
				Chunk c = w.getChunk(0);
				Player p = new Player(2,10,11,c,w);
				w.addPlayer(p);
			}
			else{
			w.loadEntities("world/");
			w.loadPlayers("world/");
			Player p = w.getPlayers().get(0);
			}
			
			
//			Rock r = new Rock(0,10,10,c,w);
//			Rock r1 = new Rock(3,10,12,c,w);
//			RockWithLegs rwl = new RockWithLegs(1,15,10,c,w);
//			Player p = new Player(2,10,11,c,w);
//			w.addEntity(rwl);
//			w.addEntity(r);
//			w.addEntity(r1);
//			w.addPlayer(p);
			Player p = w.getPlayers().get(0);
			Scanner s = new Scanner(System.in);
			System.out.println("Hello Player");
			while(true){
				w.tick();
				w.save("world/");
				System.out.println(p.handleCommand(s.nextLine().split(" ")));
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
