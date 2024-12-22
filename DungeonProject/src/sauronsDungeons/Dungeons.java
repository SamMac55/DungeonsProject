package sauronsDungeons;
import java.util.Random;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
public class Dungeons {
	
	private HashMap<Position, Room> dungeon;
	Random rand = new Random();
	
	//individually create rooms for the dungeon based on what the file says
	public void loadDungeon(List<String> items, List<String> monsters, String dungeonMap) {
		int count = 0;
		Scanner map = new Scanner(dungeonMap);
		dungeon = new HashMap<>();
		for(int i = 0; i< 10; i++) {
			String line = map.nextLine();
			for(int j = 0; j<10; j++) {
				Position key = new Position(i,j);
				Room value = new Room(items.get(count),monsters.get(count), key);
				dungeon.put(key,value);
				if(line.substring(j,j+1).equals("⊕")) {
					dungeon.get(key).setHasPlayer(true);
				}
				count++;
			}
		}
		
		
		
			map.close();
	}
	
	public HashMap<Position,Room> getDungeon() {
		return dungeon;
	}
	
	public void generateDungeon() {
		ArrayList<Position> takenRooms = new ArrayList<>();
		//create the rooms and make them all rocks initially
		dungeon = new HashMap<>();
		for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Position key = new Position(i,j);
                Room value = new Room(false, key, false);
                dungeon.put(key, value); 
            }
        }
		//put in rooms in random places
		Position starting = new Position(rand.nextInt(10), rand.nextInt(10));
		dungeon.get(starting).setIsRoom(true);
		dungeon.get(starting).addItemsToRoom();
		dungeon.get(starting).addMonstersToRoom();
		takenRooms.add(starting);
		for(int i = 0; i < 50; i++) {
			int randRoom = rand.nextInt(takenRooms.size());
			int xPos;
			int yPos;
			
			xPos = takenRooms.get(randRoom).getX();
			yPos = takenRooms.get(randRoom).getY();
			
			Position up = new Position(xPos, (yPos-1));
			Position down = new Position(xPos, (yPos+1));
			Position left = new Position((xPos-1), yPos);
			Position right = new Position((xPos+1), yPos);
			if(up.getY()>=0&&dungeon.containsKey(up) && dungeon.get(up).getIsRoom()==false) {
				dungeon.get(up).setIsRoom(true);
				dungeon.get(up).addItemsToRoom();
				dungeon.get(up).addMonstersToRoom();
				takenRooms.add(up);
			}else if(down.getY()<=10 &&dungeon.containsKey(down)&& dungeon.get(down).getIsRoom()==false) {
				dungeon.get(down).setIsRoom(true);
				dungeon.get(down).addItemsToRoom();
				dungeon.get(down).addMonstersToRoom();
				takenRooms.add(down);
				
			}else if(right.getX()<=10 && dungeon.containsKey(right)&& dungeon.get(right).getIsRoom()==false) {
				dungeon.get(right).setIsRoom(true);
				dungeon.get(right).addItemsToRoom();
				dungeon.get(right).addMonstersToRoom();
				takenRooms.add(right);
				
			}else if (left.getX()>=0 && dungeon.containsKey(left)&& dungeon.get(left).getIsRoom()==false) {
				dungeon.get(left).setIsRoom(true);
				dungeon.get(left).addItemsToRoom();
				dungeon.get(left).addMonstersToRoom();
				takenRooms.add(left);
			}
		}
		//add player to room
		int randomRoom = rand.nextInt(takenRooms.size());
		dungeon.get(takenRooms.get(randomRoom)).setHasPlayer(true);
	}
	
	//tester method
	public String itemsInRoomString() {
		String itemsMap ="";
		Position temp;
		//print out the hashmap using the actual position value, not the position in the hash map
		for(int i = 0; i<10; i++) {
			for (int j = 0; j<10; j++) {
				temp = new Position(i,j);
				if(dungeon.get(temp).getIsRoom()== true) {
					itemsMap+=dungeon.get(temp).displayItems() + "\n";
				}else {
			    	itemsMap+="false\n";
			    }	
			}
		}
		return itemsMap;
	}
	
	//tester method
	public String monstersInRoomString() {
		String monstersMap ="";
		Position temp;
		//print out the hashmap using the actual position value, not the position in the hash map
		for(int i = 0; i<10; i++) {
			for (int j = 0; j<10; j++) {
				temp = new Position(i,j);
				if(dungeon.get(temp).getIsRoom()== true) {
					monstersMap+=dungeon.get(temp).displayMonsters() + "\n";
				}else {
			    	monstersMap+="false\n";
			    }	
			}
		}
		return monstersMap;
	}
	
	//this allows us to see if the player is in the initial room for exiting purposes
	public Room findPlayer(){
		Position temp;
	
		for(int i = 0; i<10; i++) {
			for (int j = 0; j<10; j++) {
				temp = new Position(i,j);
				if(dungeon.get(temp).getHasPlayer()) {
					return dungeon.get(temp);
				}
			}
		}
		return null;
	}
	
	//printing out dungeon
	public String toString(){
		String dungeonMap ="";
		Position temp;
		int count = 1;
		//print out the hashmap using the actual position value, not the position in the hash map
		for(int i = 0; i<10; i++) {
			for (int j = 0; j<10; j++) {
				temp = new Position(i,j);
				if(dungeon.get(temp).getHasPlayer()) {
					dungeonMap+="⊕";
				}else if(dungeon.get(temp).getIsRoom()== true) {
					dungeonMap+="⏍";
				}else {
			    	dungeonMap+="⊠";
			    }
				 if(count%10==0) {
				    	dungeonMap+="\n";
			}
				 count++;
			}
		}

		return dungeonMap;
	}
	
	//MAIN
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		System.out.println("generate or load: ");
		String userInput;
		//checking for validity of user input
        while (true) {
            userInput = kb.nextLine().trim().toLowerCase(); // Read and normalize input
            if (userInput.equals("generate")){
            	break;
            }else if (userInput.equals("load")) {
                break; // Exit the loop if input is valid
            } else {
                System.out.println("Invalid input. Please enter 'generate' or 'load':");
            }
        }
        
		Player p;
		Dungeons d = null;
		//generate dungeon
		if(userInput.equalsIgnoreCase("generate")) {
			//stuff for generating
			FileHandler.addToDungeonData();
			System.out.println("Generating dungeon...");
			d = new Dungeons();
			d.generateDungeon();
			System.out.println(d);
			FileHandler.createDungeonFiles(d.toString(), d.itemsInRoomString(), d.monstersInRoomString());
			//load dungeon
		}else if(userInput.equalsIgnoreCase("load")) {
			String userFile;
			System.out.println("Enter file name: ");
			userFile = kb.next();
			try {
				System.out.println(FileHandler.loadDungeon(userFile + ".txt"));
				System.out.println("Loading " + userFile + ".txt...");
				d = new Dungeons();
				d.loadDungeon(FileHandler.whatsInRooms(userFile + "_items.txt"), FileHandler.whatsInRooms(userFile+ "_monsters.txt"), FileHandler.loadDungeon(userFile + ".txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
		//initial game stuff
		try {
		int count = 0; //used for an input buffer
		
		HashMap<Position,Room> dungeons = d.getDungeon();
		p = new Player(d.findPlayer());
		Position initialPos = p.getPosition();
		Room initialRoom = dungeons.get(initialPos);
		ArrayList<Monster> intMonsters = initialRoom.getMonstersInRoom();
		//monsters attack initially when enter first room
		for(Monster m: intMonsters) {
			m.attack(p);
		}
		System.out.println("Player initial Position: ("+ initialPos + ")");
		System.out.println("Items in initial room: " + d.getDungeon().get(initialPos).itemsInRoomString() +"\nMonsters in current room: " + d.getDungeon().get(initialPos).monstersInRoomString() + "\n");
		
		
		//game loop
		while(true) {
			Position currPosition = p.getPosition();//need to know where player is
			Room currRoom = dungeons.get(currPosition);
			//print out the menu
			System.out.println("Menu:\nexamine monster [index]\nexamine item [index]\ntake [index]\ninventory [index]\ninventory\nuse [index]\nattack [index]");
			//input buffer for first time loop runs
			if(count==0 && userInput.equals("load")) {
				kb.nextLine();
				//kb.nextLine();
				count++;
			}
			String input = kb.nextLine();
			Scanner inputScanner = new Scanner(input);//this is used for the menu items that take more words
			String currInput = inputScanner.next();
			if(input.equals("W")) {
				currRoom.allMonstersAttack(p);
				int y = currPosition.getY();
				y-=1;
				if(y>=0) {
					//move player and reset which room has player for map printing purposes
					Position temp = new Position(currPosition.getX(), y);
					if(dungeons.get(temp).getIsRoom()) {
						dungeons.get(currPosition).setHasPlayer(false);
						p.setPlayerRoom(dungeons.get(temp));
						currPosition = temp;
						dungeons.get(currPosition).setHasPlayer(true);
						currRoom = dungeons.get(currPosition);
						System.out.println(d.toString());
						System.out.println("Player current Position: ("+ currPosition + ")");
						System.out.println("Items in current room: " + d.getDungeon().get(currPosition).itemsInRoomString() +"\nMonsters in current room: " + d.getDungeon().get(currPosition).monstersInRoomString() + "\n");
						//when enter room, all monsters attack, if player health dips to one, game ends
						currRoom.allMonstersAttack(p);
						if(p.getHealth()<=1) {
							System.out.println("Game over");
							inputScanner.close();
							System.out.println("Defeated Monsters: " + p.getDefeatedTypes());
							break;
						}
						//catching out of boundness
					}else {
						System.out.println("Not a room.");
					}
				}else {
					System.out.println("Out of bounds.");
				}
				
			}else if(input.equals("E")) {
				currRoom.allMonstersAttack(p);
				int y = currPosition.getY();
				y+=1;
				if(y<=9) {
					Position temp = new Position(currPosition.getX(), y);
					if(dungeons.get(temp).getIsRoom()) {
						dungeons.get(currPosition).setHasPlayer(false);
						p.setPlayerRoom(dungeons.get(temp));
						currPosition = temp;
						dungeons.get(currPosition).setHasPlayer(true);
						currRoom = dungeons.get(currPosition);
						System.out.println(d.toString());
						System.out.println("Player current Position: ("+ currPosition + ")");
						System.out.println("Items in current room: " + d.getDungeon().get(currPosition).itemsInRoomString() +"\nMonsters in current room: " + d.getDungeon().get(currPosition).monstersInRoomString() + "\n");
						currRoom.allMonstersAttack(p);
						if(p.getHealth()<=1) {
							System.out.println("Game over");
							inputScanner.close();
							System.out.println("Defeated Monsters: " + p.getDefeatedTypes());
							break;
						}
					}else {
						System.out.println("Not a room.");
					}
				}else {
					System.out.println("Out of bounds.");
				}
			}else if(input.equals("N")){
				currRoom.allMonstersAttack(p);
				int x = currPosition.getX();
				x-=1;
				if(x>=0) {
					Position temp = new Position(x, currPosition.getY());
					if(dungeons.get(temp).getIsRoom()) {
						dungeons.get(currPosition).setHasPlayer(false);
						p.setPlayerRoom(dungeons.get(temp));
						currPosition = temp;
						dungeons.get(currPosition).setHasPlayer(true);
						currRoom = dungeons.get(currPosition);
						System.out.println(d.toString());
						System.out.println("Player current Position: ("+ currPosition + ")");
						System.out.println("Items in current room: " + d.getDungeon().get(currPosition).itemsInRoomString() +"\nMonsters in current room: " + d.getDungeon().get(currPosition).monstersInRoomString() + "\n");
						currRoom.allMonstersAttack(p);
						if(p.getHealth()<=1) {
							System.out.println("Game over");
							inputScanner.close();
							System.out.println("Defeated Monsters: " + p.getDefeatedTypes());
							break;
						}
					}else {
						System.out.println("Not a room.");
					}
				}else {
					System.out.println("Out of bounds.");
				}
			}else if(input.equals("S")) {
				currRoom.allMonstersAttack(p);
				int x = currPosition.getX();
				x+=1;
				if(x<=9) {
					Position temp = new Position(x, currPosition.getY());
					if(dungeons.get(temp).getIsRoom()) {
						dungeons.get(currPosition).setHasPlayer(false);
						p.setPlayerRoom(dungeons.get(temp));
						currPosition = temp;
						dungeons.get(currPosition).setHasPlayer(true);
						currRoom = dungeons.get(currPosition);
						System.out.println(d.toString());
						System.out.println("Player current Position: ("+ currPosition + ")");
						System.out.println("Items in current room: " + d.getDungeon().get(currPosition).itemsInRoomString() +"\nMonsters in current room: " + d.getDungeon().get(currPosition).monstersInRoomString() + "\n");
						currRoom.allMonstersAttack(p);
						if(p.getHealth()<=1) {
							System.out.println("Game over");
							inputScanner.close();
							System.out.println("Defeated Monsters: " + p.getDefeatedTypes());
							break;
						}
					}else {
						System.out.println("Not a room.");
					}
				}else {
					System.out.println("Out of bounds.");
				}
				//examining things in the room
			}else if(currInput.equals("examine")) {
				String temp = inputScanner.next();
				//if we are doing a monster examine monsters
				if(temp.equals("monster")) {
					if(currRoom.getMonstersInRoom().size()==0) {
						System.out.println("No monsters in this room.");
					}else {
						
						try {//these try catches just check if substring works and if parseInt works
							String index = inputScanner.next();
							int ind = Integer.parseInt(index.substring(1,index.length()-1));
							currRoom.examineMonster(ind);
						}catch(Exception e) {
							System.out.println("Invalid Input");
						}
					}//examine items
				}else if(temp.equals("item")) {
					if(currRoom.getItemsInRoom().size()==0) {
						System.out.println("No items in this room.");
					}else {
						try {
							//System.out.println("FLAGGGGG");
							String index = inputScanner.next();
							int ind = Integer.parseInt(index.substring(1,index.length()-1));
							currRoom.examineItem(ind);
						}catch(Exception e) {
							System.out.println("Invalid Input");
						}
					}
				}
			//take items in the room
			}else if(currInput.equals("take")) {
				if(currRoom.getItemsInRoom().size()==0) {
					System.out.println("No items in this room.");
				}else {
					try {
						String index = inputScanner.next();
						int ind = Integer.parseInt(index.substring(1,index.length()-1));
						currRoom.takeItem(p,ind);
						currRoom.allMonstersAttack(p);
						if(p.getHealth()<=1) {
							System.out.println("Game over");
							inputScanner.close();
							System.out.println("Defeated Monsters: " + p.getDefeatedTypes());
							break;
						}
					}catch(Exception e) {
						System.out.println("Invalid input");
					}
				}
				//print out inventory: two options, specific index or not
			}else if(currInput.equals("inventory")) {
				if(inputScanner.hasNext()) {
					try {
						String index = inputScanner.next();
						int ind = Integer.parseInt(index.substring(1,index.length()-1));
						p.inventoryString(ind);
					}catch(Exception e) {
						System.out.println("Invalid input");
					}
				}else {
					System.out.println(p.inventoryString());
				}
				//use items, if its a weapon equip, if its a potion heal or repair
			}else if(currInput.equals("use")) {
				try {
					String index = inputScanner.next();
					int ind = Integer.parseInt(index.substring(1,index.length()-1));
					Item itemToUse = p.getInventory().get(ind);
					currRoom.allMonstersAttack(p);
					if(p.getHealth()<=1) {
						System.out.println("Game over");
						inputScanner.close();
						System.out.println("Defeated Monsters: " + p.getDefeatedTypes());
						break;
					}
					if(itemToUse instanceof Weapon) {
						p.equipWeapon(ind);
					}else if(itemToUse instanceof Potion) {
						if(itemToUse.getUses()<1) {
							System.out.println("Cannot use potion anymore");
						}else {
							if(itemToUse.getType().equals("healing")) {
								p.heal(itemToUse.getEffect());
								System.out.println("Healed player by " + itemToUse.getEffect() + ". Player Health: " + p.getHealth());
								itemToUse.use();
								System.out.println("Potion uses left: " + itemToUse.getUses());
							}else if(itemToUse.getType().equals("repair")) {
								if(p.getEquipped()==null) {
									System.out.println("No weapon equipped: Cannot repair");
								}else {
									p.repairWeapon(itemToUse.getEffect());
									itemToUse.use();
									System.out.println("Potion uses left: " + itemToUse.getUses());
								}
							}
						}
						
					}
				}catch(Exception e) {
					System.out.println("Invalid input");
				}//attack a monster	
			}else if(currInput.equals("attack")) {
				currRoom.allMonstersAttack(p);
				if(p.getHealth()<=1) {
					System.out.println("Game over");
					inputScanner.close();
					System.out.println("Defeated Monsters: " + p.getDefeatedTypes());
					break;
				}
				try {
					String index = inputScanner.next();
					int ind = Integer.parseInt(index.substring(1,index.length()-1));
					p.attack(currRoom.getMonstersInRoom(),ind);
				}catch(Exception e) {
					System.out.println("Invalid input");
				}
				//escape if in initial room
			}else if(currPosition.equals(initialPos) && currInput.equals("escape")) {
				System.out.println("Successfully explored dungeon.");
				System.out.println("Defeated Monsters: " + p.getDefeatedTypes());
				inputScanner.close();
				break;
			}
			System.out.println();
		}
		}catch (NullPointerException e) {
			System.out.println("No dungeon to play.");
		}
		kb.close();
	}
}
