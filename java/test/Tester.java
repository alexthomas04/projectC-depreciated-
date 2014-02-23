package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import world.Chunk;
import json.JSONArray;

public class Tester {
	public static void main(String[] args){
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File("chunk.txt")));
			String jsonString ="",line="";
			do{
				line =br.readLine();
				if(line!=null)
					jsonString+=line;
			}while(line!=null);
			new Chunk(jsonString);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
