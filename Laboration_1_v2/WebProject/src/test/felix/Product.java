package test.felix;
/*
 * Den här klassen hanterar enskilda produkter som finns att köpa
 */
public class Product {
	private  String name;	 
    public Product(String name) {
	        this.name= name;
	}
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
	    this.name=name;
	}  
}
