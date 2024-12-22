package sauronsDungeons;
import java.util.*;
public class Room {
	private ArrayList<Item> itemsInRoom= new ArrayList<>();
	private ArrayList<Monster> monstersInRoom = new ArrayList<>();
	private boolean isRoom;
	private Position p;
	private boolean hasPlayer;
	//constructor for random generation
	public Room(boolean room, Position p, boolean hasPlayer) {
		isRoom = room;
		this.p = p;
		this.hasPlayer = hasPlayer;
	}
	//constructor for loading
	public Room(String items, String monsters, Position p) {
		StringTokenizer st;
		StringTokenizer lt;
		this.p = p;
		//using tokenizer to determine different items and also a seperate one to differentiate values within items
			st = new StringTokenizer(items, "\t");
			String first = st.nextToken();
			//the way the file is set up is that if its false its not a room, if its null its a room with no items
			if(first.equals("false")) {
				isRoom = false;
			}else if(first.equals("null")) {
				isRoom = true;
			}else {
				isRoom = true;
				lt = new StringTokenizer(first, ",");
				int uses = Integer.parseInt(lt.nextToken());
				int effect = Integer.parseInt(lt.nextToken());
				String type=lt.nextToken();
				Item temp = new Item(uses,effect,type);
				itemsInRoom.add(temp);
				while(st.hasMoreTokens()) {
					lt = new StringTokenizer(st.nextToken(), ",");
					uses = Integer.parseInt(lt.nextToken());
					effect = Integer.parseInt(lt.nextToken());
					type=lt.nextToken();
					temp = new Item(uses,effect,type);
					itemsInRoom.add(temp);
				}
			}
			//using tokenizer to determine different monsters and also a seperate one to differentiate values within monsters
			//same logic as last one just for the monsters
				st = new StringTokenizer(monsters, "\t");
				String initial = st.nextToken();
				if(initial.equals("false")) {
					isRoom = false;
				}else if(initial.equals("null")) {
					isRoom=true;	
				}else {
					isRoom = true;
					lt = new StringTokenizer(initial, ",");
					String type=lt.nextToken();
					int health = Integer.parseInt(lt.nextToken());
					Weapon weapon= new Weapon(Integer.parseInt(lt.nextToken()),Integer.parseInt(lt.nextToken()), lt.nextToken());
					Monster temp = new Monster(type,health,weapon);
					monstersInRoom.add(temp);
					while(st.hasMoreTokens()) {
						lt = new StringTokenizer(st.nextToken(), ",");
						type = lt.nextToken();
						health = Integer.parseInt(lt.nextToken());
						weapon= new Weapon(Integer.parseInt(lt.nextToken()),Integer.parseInt(lt.nextToken()), lt.nextToken());
						temp = new Monster(type,health,weapon);
						monstersInRoom.add(temp);
					}
			}
		}
//getter and setter	
	public void setIsRoom(boolean room) {
		isRoom = room;
	}
	public boolean getIsRoom() {
		return isRoom;
	}
	public void setHasPlayer(boolean player) {
		hasPlayer=player;
	}
	public boolean getHasPlayer() {
		return hasPlayer;
	}
	public Position getPosition() {
		return p;
	}
	//this is for generating 
	public void addItemsToRoom() {
		Random rand = new Random();
		int numItems = rand.nextInt(4);
		for(int i = 0; i<numItems; i++) {
			int probability = rand.nextInt(100);
			if(probability%2==0) {
				itemsInRoom.add(Weapon.createRandomWeapon());
			}else {
				itemsInRoom.add(Potion.createRandomPotion());
			}
		}
	}
	//generating
	public void addMonstersToRoom() {
		Random rand = new Random();
		int numMonsters = rand.nextInt(4);
		for(int i = 0; i<numMonsters; i++) {
			monstersInRoom.add(Monster.createRandomMonster());
		}
	}
	//for loading
	public String displayItems() {
		String returnVal ="";
		if(itemsInRoom.size()==0) {
			return null;
		}
		for(int i = 0; i<itemsInRoom.size();i++) {
			returnVal+=itemsInRoom.get(i);
			if((i+1)<itemsInRoom.size()) {
				returnVal += ", ";
			}
		}
		return returnVal;
	}
	//loading
	public String displayMonsters() {
		String returnVal ="";
		if(monstersInRoom.size()==0) {
			return null;
		}
		for(int i = 0; i<monstersInRoom.size();i++) {
			returnVal+=monstersInRoom.get(i);
			if((i+1)<monstersInRoom.size()) {
				returnVal +=", ";
			}
		}
		return returnVal;
	}//for displaying
	public String itemsInRoomString() {
		String returnVal ="";
		if(itemsInRoom.size()==0) {
			return null;
		}
		for(int i = 0; i<itemsInRoom.size();i++) {
			returnVal+=itemsInRoom.get(i).getType() + " " + i;
			if((i+1)<itemsInRoom.size()) {
				returnVal += ", ";
			}
		}
		return returnVal;
	}
	public String monstersInRoomString() {
		String returnVal ="";
		if(monstersInRoom.size()==0) {
			return null;
		}
		for(int i = 0; i<monstersInRoom.size();i++) {
			returnVal+=monstersInRoom.get(i).getType() + " " + i;
			if((i+1)<monstersInRoom.size()) {
				returnVal += ", ";
			}
		}
		return returnVal;
	}
	//examining
	public void examineMonster(int index) {
		if(index>monstersInRoom.size()-1 || index<0) {
			System.out.println("Invalid index");
		}else {
			Monster curr = monstersInRoom.get(index);
			Weapon currWeapon = curr.getWeapon();
			System.out.println("Monster at index " + index + "-- Type: " + curr.getType() + ", Health: " + curr.getHealth() + "\nWeapon type: " + currWeapon.getType() + ", durability: " + currWeapon.getUses() + ", damage: " + currWeapon.getEffect());
		}
	}
	public void examineItem(int index) {
		if(index>itemsInRoom.size()-1 || index<0) {
			System.out.println("Invalid index");
		}else {
			//System.out.println("HERE");
			Item curr = itemsInRoom.get(index);
			if(curr instanceof Weapon) {
				System.out.println("Item at index " + index + " is a weapon of type " + curr.getType() + ", durability " + curr.getUses() + ", damage " + curr.getEffect());
			}else if(curr instanceof Potion) {
				System.out.println("Item at index " + index + " is a potion of type " + curr.getType() + ", uses " + curr.getUses() + ", effect " + curr.getEffect());
			}else {
				System.out.println("Item at index " + index + " is an item of type " + curr.getType() + ", uses " + curr.getUses() + ", effect " + curr.getEffect());
			}
	
		}
	}
	//taking
	public void takeItem(Player p, int index) {
		p.addToInventory(itemsInRoom.get(index));
		itemsInRoom.remove(index);
	}
	//allows us to display everything about a room 
	public String toString() {
		String returnval = "Position: " + p + "\nIs Room: " + isRoom + "\nItems: " + itemsInRoom + "\nMonsters: " + monstersInRoom + "\n";
		return returnval;
	}
	//get the arrayLists
	public ArrayList<Monster> getMonstersInRoom(){
		return monstersInRoom;
	}
	public ArrayList<Item> getItemsInRoom(){
		return itemsInRoom;
	}
	//just a loop 
	public void allMonstersAttack(Player p) {
		for(Monster m: monstersInRoom) {
			m.attack(p);
		}
	}
}
