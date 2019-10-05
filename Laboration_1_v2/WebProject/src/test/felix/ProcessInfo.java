package test.felix;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
/**
 * Servlet implementation class ProcessInfo
 */
@WebServlet("/ProcessInfo")
public class ProcessInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 *Klassen som hanterar/reagerar på knapptryckningar
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bt = request.getParameter("bt");
		if (bt!=null) {
		String anvandarnamn = request.getParameter("anvandarnamn");
		String losenord = request.getParameter("losenord");
		if (bt.equals("Back")){
			// hamta productlista
			ArrayList<Product> produktLista = getProductListaInDB();
			if (produktLista!=null) {
				request.setAttribute("produktlista", produktLista);
  			} else {
				
			}
			
			getServletContext().getRequestDispatcher("/Shop.jsp").forward(request, response);
		} else if (bt.equals("Logout")){
			String antalVarorIVarukorg = ""+0;	
			request.getSession().setAttribute("antalVarorIVarukorg", antalVarorIVarukorg);
			request.getSession().setAttribute("user", "");
			getServletContext().getRequestDispatcher("/Index.jsp").forward(request, response);
		} else if (bt.equals("Visa")){
			String user = (String) request.getSession().getAttribute("user");
			if (user!=null) {
			// hamta varukorglista for anvandare
			ArrayList<Vara> varukorgsLista = getVarukorgsListaInDB(user);
			if (varukorgsLista!=null) {
				request.setAttribute("varukorgslista", varukorgsLista);
				getServletContext().getRequestDispatcher("/Varukorg.jsp").forward(request, response);
			} else {
				// nagot blev fel informera 
			}
			}
		} else if (bt.equals("buy")){
			String user = (String) request.getSession().getAttribute("user");
			if (user!=null) {
				String vara = request.getParameter("vara");
				// lägg till vara för user i varukorg tabell
				updateVarukorgInDB(user, vara);
				String antalVarorIVarukorg = ""+checkVarukorgInDB(user);
				
				request.getSession().setAttribute("antalVarorIVarukorg", antalVarorIVarukorg);
				// hamta productlista
				ArrayList<Product> produktLista = getProductListaInDB();
				if (produktLista!=null) {
					request.setAttribute("produktlista", produktLista);
      			} else {
					// nagot blev fel informera 
				}
				getServletContext().getRequestDispatcher("/Shop.jsp").forward(request, response);
			}
		} else {
		if ((anvandarnamn!=null)&&(anvandarnamn.length()>0)&&(losenord!=null)&&(losenord.length()>0))
		{	
			if (bt.equals("Skapa")) {
				String url = "/DisplayInfo.jsp";
				int res = updateAnvandareInDB(anvandarnamn, losenord);
				if (res == 0) {
					Anvandare anv = new Anvandare(anvandarnamn, losenord);
					request.setAttribute("anv", anv);
					getServletContext().getRequestDispatcher(url).forward(request, response);
				} else if (res == 1) {
					request.getSession().setAttribute("errorMessage", "Skapa: anvandarnamn finns redan");
					getServletContext().getRequestDispatcher("/Index.jsp").forward(request, response);
				} else {
					request.getSession().setAttribute("errorMessage", "Skapa: okänt fel");
					getServletContext().getRequestDispatcher("/Index.jsp").forward(request, response);
				}
			} else if (bt.equals("Login")){
			
				int res = checkAnvandareInDB(anvandarnamn, losenord);
				if (res == 0) {
					String url = "/Shop.jsp";
					Anvandare anv = new Anvandare(anvandarnamn, losenord);
					request.setAttribute("anv", anv);
					//add user to session
					request.getSession().setAttribute("user", anvandarnamn);
					String antalVarorIVarukorg = ""+checkVarukorgInDB(anvandarnamn);
					
					// hamta productlista
					ArrayList<Product> produktLista = getProductListaInDB();
					if (produktLista!=null) {
						request.setAttribute("produktlista", produktLista);
	      			} 
	
					request.getSession().setAttribute("antalVarorIVarukorg", antalVarorIVarukorg);
					getServletContext().getRequestDispatcher(url).forward(request, response);
				} else if (res == 1) {
					request.getSession().setAttribute("errorMessage", "Login: felaktigt losenord");
					getServletContext().getRequestDispatcher("/Index.jsp").forward(request, response);
				} else if (res == 2) {
					request.getSession().setAttribute("errorMessage", "Login: okänd anvandare");
					getServletContext().getRequestDispatcher("/Index.jsp").forward(request, response);
				} else {
					request.getSession().setAttribute("errorMessage", "Skapa: okänt fel");
					getServletContext().getRequestDispatcher("/Index.jsp").forward(request, response);
				}	
			} 
		} else 	{
			// Skriv ut felmeddelande
			if (bt.equals("Skapa")) {
				request.getSession().setAttribute("errorMessage", "Skapa: ange anvandarnamn och losenord");
			} else {
				request.getSession().setAttribute("errorMessage", "Login: ange anvandarnamn och losenord");
			}
			response.sendRedirect(request.getHeader("Referer"));
		}
		}
		}
	}
	
	/*
	 * Hanterar kontoskapning för nya användare.
	 */
	protected int updateAnvandareInDB(String anvandarnamn, String losenord) 
	{
		Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/felixwebbbutik";
			String user = "felixServer";
			String pw = "admin";
			con = DriverManager.getConnection(url, user, pw);
			Statement s = con.createStatement();
			String query = "INSERT INTO anvandare " + "(anvandarnamn, losenord) "
			+ "VALUES ('" + anvandarnamn + "', '" + losenord + "')";
			s.executeUpdate(query);
			return 0;
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			return 3;
		
		} catch (java.sql.SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			return 1;
		} 
		catch(SQLException e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	/*
	 * Hanterar inloggning för användare
	 */
	protected int checkAnvandareInDB(String anvandarnamn, String losenord) 
	{
		Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/felixwebbbutik";
			String user = "felixServer";
			String pw = "admin";
			con = DriverManager.getConnection(url, user, pw);
			Statement s = con.createStatement();
			String query = "SELECT * FROM anvandare WHERE anvandarnamn = \"" + anvandarnamn + "\"";
		
			ResultSet rs = s.executeQuery(query);
			if (rs.next()) {
				String losenordInDb = rs.getString("losenord");
				if (losenord.equals(losenordInDb)){
					// OK 
					return 0;
				} else {
					// felaktigt losenord
					return 1;
				}		
			} else {
				// empty result - anvandare saknas
				return 2;
			}
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			return 3;
		} 
		catch(SQLException e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	/*
	 * Den här metoden lägger till en vara till en användares varukorg
	 */
	protected int updateVarukorgInDB(String anvandarnamn, String vara) 
	{
		Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/felixwebbbutik";
			String user = "felixServer";
			String pw = "admin";
			con = DriverManager.getConnection(url, user, pw);
			Statement s = con.createStatement();
			String query = "INSERT INTO varukorg " + "(anvandarnamn, vara) "
			+ "VALUES ('" + anvandarnamn + "', '" + vara + "')";
			s.executeUpdate(query);
			return 0;
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			return 3;
		
		} catch (java.sql.SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			return 1;
		} 
		catch(SQLException e) {
			e.printStackTrace();
			return 2;
		}
	}

	/*
	 * Den här metoden räknar antal varor i varukorgen för en specifik användare.
	 */
	protected int checkVarukorgInDB(String anvandarnamn) 
	{
		Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/felixwebbbutik";
			String user = "felixServer";
			String pw = "admin";
			con = DriverManager.getConnection(url, user, pw);
			Statement s = con.createStatement();
			String query = "SELECT * FROM varukorg WHERE anvandarnamn = \"" + anvandarnamn + "\"";
		
			ResultSet rs = s.executeQuery(query);

			int i = 0;
			while(rs.next()) {
				i++;
			}
			return i;
			
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			return -9;
		} 
		catch(SQLException e) {
			e.printStackTrace();
			return -9;
		}
	}
	/*
	 * Den här metoden hämtar varukorgslistan för en specifik användare
	 */
	protected ArrayList<Vara> getVarukorgsListaInDB(String anvandarnamn) 
	{
		Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/felixwebbbutik";
			String user = "felixServer";
			String pw = "admin";
			con = DriverManager.getConnection(url, user, pw);
			Statement s = con.createStatement();
			String query = "SELECT * FROM varukorg WHERE anvandarnamn = \"" + anvandarnamn + "\"";
		
			ResultSet rs = s.executeQuery(query);
			
			ArrayList<Vara> varor = new ArrayList<>();
			int i = 0;
			while(rs.next()) {
				String varunamn = rs.getString("vara");
				Vara vara = new Vara(varunamn);
				varor.add(vara);
			}
		
			return varor;
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * Den är metoden hämtar vilka varor som finns att köpa i butiken.
	 */
	protected ArrayList<Product> getProductListaInDB() 
	{
		Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/felixwebbbutik";
			String user = "felixServer";
			String pw = "admin";
			con = DriverManager.getConnection(url, user, pw);
			Statement s = con.createStatement();
			String query = "SELECT * FROM varor";
		
			ResultSet rs = s.executeQuery(query);
			
			ArrayList<Product> products = new ArrayList<>();
			//int i = 0;
			while(rs.next()) {
				String varunamn = rs.getString("namn");
				Product product = new Product(varunamn);
				products.add(product);
			}
		
			return products;
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} 
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
