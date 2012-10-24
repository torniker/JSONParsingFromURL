package ge.longman.myfirstapp;

public class Currency {
	
	public int amount;
	public String code;
	public String name;
	public Double rate;
	
	public String toString() {
		return code+" "+name+" "+amount+" "+rate;
		
	}
}
