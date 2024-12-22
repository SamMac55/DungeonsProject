package sauronsDungeons;
import java.util.Random;
public class Weapon extends Item{

	public Weapon(int durability, int damage, String type) {
		super(durability, damage, type);
	}
	
	public static Weapon createRandomWeapon() {
		String name=FileHandler.getRandomType("weapons.txt");// this will come later when you figure out how to grab a name from the files
		//damage 1-10
		Random rand = new Random();
		int damage;
		damage = rand.nextInt(10)+1;
		//uses/durability 1-30
		int uses;
		uses = rand.nextInt(30)+1;
		return new Weapon(uses,damage,name);
	}
	public String toString() {
		return uses + "," + effect + "," + type;
	}
	//basically a setter for the uses (durability)
	public void useWeapon() {
		this.use();
	}
	public void repair(int num) {
		uses+=num;
	}
}
