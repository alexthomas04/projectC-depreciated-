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
import game.GameManager;
import world.Chunk;
import world.World;
import json.JSONArray;

public class Tester {
	public static void main(String[] args){
		
		GameManager gm = new GameManager();
		gm.run();
		
	}
}
