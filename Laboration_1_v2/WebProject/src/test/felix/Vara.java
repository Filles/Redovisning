package test.felix;

/*
 * Den h�r klassen hanterar varorna som anv�ndaren har k�pt
 */
public class Vara {
	private  String name;	 
    public Vara(String name) {
	        this.name= name;
	}
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
	    this.name=name;
	}  
}
