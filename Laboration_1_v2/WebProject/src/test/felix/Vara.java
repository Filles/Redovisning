package test.felix;

/*
 * Den här klassen hanterar varorna som användaren har köpt
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
