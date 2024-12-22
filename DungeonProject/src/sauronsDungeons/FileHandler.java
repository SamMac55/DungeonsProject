package sauronsDungeons;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.List;
import java.nio.file.Path;
public class FileHandler {
	//this method is going to be used to tell the dungeon what is in each room of any given file for either items or monsters
	public static List<String> whatsInRooms(String fileName){
		List<String> lines;
		try {
			Path fPath = Paths.get(fileName);
			lines = Files.readAllLines(fPath);
			return lines;
			//if not found just throw an error
		}catch(IOException e){
			System.out.println("file not found.");
		}
		lines = new ArrayList<>();
		return lines;
	}
	
	public static String getRandomType(String fileName) {
		Random rand = new Random();
		String type = null;
		
		try {
			Path fPath = Paths.get(fileName);
			List<String> lines = Files.readAllLines(fPath);
			return lines.get(rand.nextInt(lines.size()));
			//if not found just throw an error
		}catch(IOException e){
			System.out.println("file not found.");
		}
		return type;
	}
	
	public static String loadDungeon(String dungeonName) throws FileNotFoundException {
		File dungeon;
		FileInputStream fr = null;
		Scanner scnr = null;
		String loadedDungeon= "";
		//find the file
		try {
			dungeon = new File(dungeonName);
			fr = new FileInputStream(dungeon);
			scnr = new Scanner(fr);
			while(scnr.hasNext()) {
				loadedDungeon+=scnr.nextLine();
				loadedDungeon+="\n";
			}
			scnr.close();
			//if not found just throw an error
		}catch(FileNotFoundException e){
			throw new FileNotFoundException("Error: " + dungeonName + " Not Found");
		}
		return loadedDungeon;
	}
	
	public static void createDungeonFiles(String dungeonMap, String itemsMap, String monstersMap) {
		File newDungeon;
		File dungeonItems;
		File dungeonMonsters;
		File dungeonData;
		FileInputStream fr = null;
		PrintWriter outFS;
		FileOutputStream fileStream= null;
		Scanner scnr = null;
		int currentDungeon;
		//open dungeon_data.txt
		try {
			dungeonData = new File("dungeon_data.txt");
			fr = new FileInputStream(dungeonData);
			scnr = new Scanner(fr);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		//take the current value of the dungeon_data
		currentDungeon = scnr.nextInt();
		scnr.close();
		//create X.txt using the value from dungeon_data and write the dungeon to it
		try {
			//for the layout of the dungeon
			String dungeonName = currentDungeon +".txt";
			newDungeon = new File(dungeonName);
			newDungeon.createNewFile();
			fileStream = new FileOutputStream(dungeonName);
			outFS= new PrintWriter(fileStream);
			outFS.print(dungeonMap);
			outFS.close();
			dungeonName = currentDungeon + "_items.txt";
			dungeonItems = new File(dungeonName);
			dungeonItems.createNewFile();
			fileStream = new FileOutputStream(dungeonName);
			outFS = new PrintWriter(fileStream);
			outFS.print(itemsMap);
			outFS.close();
			dungeonName = currentDungeon + "_monsters.txt";
			dungeonMonsters = new File(dungeonName);
			dungeonMonsters.createNewFile();
			fileStream = new FileOutputStream(dungeonName);
			outFS = new PrintWriter(fileStream);
			outFS.print(monstersMap);
			outFS.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void addToDungeonData(){
		File dungeonData = null;
		FileOutputStream fileStream = null;
		PrintWriter outFS;
		FileInputStream read = null; // File input stream
	    Scanner scnr = null; 
		int writeValue = 0;
		//open dungeon_data.txt or create it
		try { 
            dungeonData = new File("dungeon_data.txt"); 
            dungeonData.createNewFile();
            read = new FileInputStream(dungeonData);
            
	   }catch (Exception e) { 
	            System.out.println(e.getMessage()); 
	    } 
		//read whats currently in the file... if its a new file,
		//we are still running it for the first time so the value we will write is 1
		//if not, value is whatever is there already plus 1
		scnr = new Scanner(read);
		if(scnr.hasNextInt()==false) {
			writeValue = 1;
		}else {
			writeValue = scnr.nextInt() + 1;
		}
		scnr.close();
		//overwrite whats currently in the file with the new value we found
		try {
			fileStream = new FileOutputStream("dungeon_data.txt");
			outFS= new PrintWriter(fileStream);
			outFS.print(writeValue);
			outFS.close();
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
