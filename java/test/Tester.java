package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import world.Chunk;
import json.JSONArray;

public class Tester {
	public static void main(String[] args){
		Chunk c = new Chunk(1000,1000);
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("chunk1.txt")));
			bw.write(c.getJson().toString(1));
			bw.flush();
			bw.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
