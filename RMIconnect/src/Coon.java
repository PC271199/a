import java.io.Serializable;
import java.rmi.Remote;
import java.sql.Connection;
import java.sql.DriverManager;

public class Coon implements Serializable {
	Connection c1;
	public void setconnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://127.0.0.1:3306/qlhs?serverTimezone=UTC";
			Connection con=DriverManager.getConnection(url,"root","");
			this.c1=con;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
