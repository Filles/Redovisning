package test.felix;
/*
 * Den h�r klassen hanterar enskilda produkter som finns att k�pa
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
