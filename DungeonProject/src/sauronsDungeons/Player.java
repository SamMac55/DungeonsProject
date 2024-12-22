package sauronsDungeons;
import java.util.ArrayList;
public class Player {
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private ArrayList<Boolean> inHand = new ArrayList<Boolean>();
	private ArrayList<Monster> defeatedMonsters = new ArrayList<Monster>();
	private Room r;
	private int health = 200;
	private Weapon equippedWeapon = null;
	//give a player a random weapon and potion
	public Player(Room r) {
		inventory.add(Weapon.createRandomWeapon());
		inHand.add(false);
		inventory.add(Potion.createRandomPotion());
		inHand.add(false);
		this.r = r;
	}
	//getter and setters
	public void setPlayerRoom(Room r) {
		this.r = r;
	}
	public String getDefeatedTypes(){
		if(defeatedMonsters.size()==0) {
			return "no monsters defeated";
		}
		String returnVal = "[";
		for(Monster m: defeatedMonsters) {
			returnVal += m.getType() + ", " ;
		}
		returnVal = returnVal.substring(0,returnVal.length()-2);
		return returnVal + "]";
	}
	public Weapon getEquipped() {
		return equippedWeapon;
	}
	//add durability to weapon
	public void repairWeapon(int num) {
		equippedWeapon.repair(num);
	}
	//allow us to hold weapon
	public boolean equipWeapon(int index) {
		if(equippedWeapon!=null) {
			int oldIndex = inventory.indexOf(equippedWeapon);
			inHand.set(oldIndex, false);
			equippedWeapon = null;
		}

		if(inventory.get(index) instanceof Weapon) {
			inHand.set(index, true);
			equippedWeapon = (Weapon) inventory.get(index);
			return true;
		}
		return false;//use this so we can check if its a weapon or potion
	}
	//add to our inventory
	public void addToInventory(Item i) {
		inventory.add(i);
		inHand.add(false);
	}
	//more getters
	public int getHealth() {
		return health;
	}
	public Position getPosition() {
		return r.getPosition();
	}
	//basically setters for health
	public void heal(int num) {
		health+= num;
	}
	public void subtractFromHealth(int num) {
		health -=num;
	}
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	//overloaded methods for inventory getting
	public void inventoryString(int index) {
		System.out.println("Item at index: " + index + "-- Type: " + inventory.get(index).getType() + ", Uses/Durability: " + inventory.get(index).getUses() + ", Effect/Damage: " + inventory.get(index).getEffect());
	}
	public String inventoryString() {
		String returnval = "Inventory: ";
		for(int i = 0; i<inventory.size(); i++) {
			returnval+= "Index " + i + "-- Type: " + inventory.get(i).getType() + ", Uses/Durability: " + inventory.get(i).getUses() + ", Effect/Damage: " + inventory.get(i).getEffect() + "; ";
		}
		return returnval;
	}
	//attacking
	public void attack(ArrayList<Monster> monsters, int index) {
		Monster toBeAttacked = monsters.get(index);
		if(toBeAttacked.getHealth()<=1) {
			System.out.println("Monster sleeping, cannot attack (health is less than 1)");
		}else {
			if(equippedWeapon==null) {
				toBeAttacked.subtractFromHealth(1);
				System.out.println("Attacked " + toBeAttacked.getType() + " with a slap (no weapon equipped). Monster health: " + toBeAttacked.getHealth());
			}else {
				if(equippedWeapon.getUses()>0) {
					equippedWeapon.use();
					toBeAttacked.subtractFromHealth(equippedWeapon.getEffect());
					System.out.println("Attacked " + toBeAttacked.getType() + " with a " + equippedWeapon.getType() +  ". Monster health: " + toBeAttacked.getHealth());
					if(toBeAttacked.getHealth()<=1) {
						defeatedMonsters.add(toBeAttacked);
					}
				}else {
					toBeAttacked.subtractFromHealth(1);
					inventory.remove(equippedWeapon);
					inHand.remove(true);
					equippedWeapon = null;
					System.out.println("Attacked " + toBeAttacked.getType() + " with a slap (weapon broken). Monster health: " + toBeAttacked.getHealth());
					if(toBeAttacked.getHealth()<=1) {
						defeatedMonsters.add(toBeAttacked);
					}
				}
			}		
		}
	}
}
