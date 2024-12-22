package sauronsDungeons;

import java.util.Random;

public class Potion extends Item{

	public Potion(int uses, int effect, String type) {
		super(uses, effect, type);
		
	}
	public static Potion createRandomPotion() {
		Random rand = new Random();
		String type;
		int probability = rand.nextInt(100);
		if(probability%2==0) {
			type = "healing";
		}else {
			type = "repair";
		}
		//damage 1-10
		int damage;
		damage = rand.nextInt(10)+1;
		//uses/durability 1-30
		int uses;
		uses = rand.nextInt(10)+1;
		
		return new Potion(uses,damage,type);
	}
	public String toString() {
		return uses + "," + effect + "," + type;
	}

}
