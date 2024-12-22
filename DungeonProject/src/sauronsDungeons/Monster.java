package sauronsDungeons;

import java.util.Random;

public class Monster {
	private String type;
	private int health;
	private Weapon weapon;
	public Monster(String type,int health, Weapon weapon) {
		this.type = type;
		this.health = health;
		this.weapon = weapon;
	}
	public static Monster createRandomMonster() {
		String type= FileHandler.getRandomType("monsters.txt");// this will come later when you figure out how to grab a name from the files
		//damage 1-10
		Random rand = new Random();
		int health;
		health = rand.nextInt(10)+10;
		//uses/durability 1-30
		Weapon weapon = Weapon.createRandomWeapon();
		return new Monster(type,health,weapon);
	}
	//getters and setters
	public String getType() {
		return type;
	}
	public int getHealth() {
		return health;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public String toString() {
		return type + "," + health + "," + weapon.toString();
	}
	//allow monster to attack
	public void attack(Player p) {
		if(health>1) {
			if(weapon.getUses()>0) {
				weapon.use();
				p.subtractFromHealth(weapon.getEffect());
				System.out.println(type + " attacks with " + weapon.getEffect() + " damage. Player health: " +p.getHealth());
			}else {
				p.subtractFromHealth(1);
				System.out.println(type + " attacks with 1 damage. Player health: " +p.getHealth());
			}
			
		}
	}
	//take away monster health
	public void subtractFromHealth(int num) {
		health -=num;
	}
}
