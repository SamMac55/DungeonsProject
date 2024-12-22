package sauronsDungeons;

public class Item {
	protected int uses;
	protected String type;
	protected int effect;
	public Item(int uses, int effect, String type) {
		this.uses = uses;
		this.effect = effect;
		this.type = type;
	}
	public int getUses() {
		return uses;
	}
	public int getEffect() {
		return effect;
	}
	public String getType() {
		return type;
	}
	public String toString() {
		return uses + "," + effect + "," + type;
	}
	public void use() {
		uses-=1;
	}
}
