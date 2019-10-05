package test.felix;

import java.io.Serializable;

/*
 * Klass som hanterare/modelerar användare
 */
public class Anvandare implements Serializable{

	private static final long serialVersionUID = 1L;
	private String anvandarnamn;
	private String losenord;
	
	public Anvandare(String anvandarnamnIn, String losenordIn){
		this.setAnvandarnamn(anvandarnamnIn);
		this.setLosenord(losenordIn);
	}

	public String getAnvandarnamn() {
		return anvandarnamn;
	}

	public void setAnvandarnamn(String anvandarnamn) {
		this.anvandarnamn = anvandarnamn;
	}

	public String getLosenord() {
		return losenord;
	}

	public void setLosenord(String losenord) {
		this.losenord = losenord;
	}

	
	
}
