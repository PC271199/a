import java.awt.List;import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class AtServerImpl extends UnicastRemoteObject implements AtServer {
	public AtClient ac;
	public SetupServer ui;
	
	private static final long serialVersionUID = 1L;

	public AtServerImpl() throws RemoteException {
		super();
	}

	public AtServerImpl(SetupServer ui) throws RemoteException{
		this.ui=ui;
	}
	public synchronized Connection connection() {
		try {
			System.out.print("vao roi");
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://127.0.0.1:3306/qlhs?serverTimezone=UTC";
			Connection con=DriverManager.getConnection(url,"root","");
			return con;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
		
	}
	public synchronized int login(String username,String pass)  throws RemoteException{
		try {
			System.out.println("vao try");
			Connection con=connection();
			Statement stmt=con.createStatement();	
			String sql=String.format("SELECT * FROM admin where username='%s' and password='%s'", username,pass);
			ResultSet rs=stmt.executeQuery(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			int numberofcolums=rsmd.getColumnCount();
			System.out.println("xong");
			if(rs.next()) {
				if((int)rs.getObject(4)==0) {
					if((int) rs.getObject(1)>0) {
						String sql1=String.format("Update admin SET taken=%d where id=%d",1,rs.getObject(1));
						PreparedStatement pr=con.prepareStatement(sql1);
						pr.executeUpdate();
						this.ui.addItem("Client ID= "+rs.getObject(1)+" login successfully");
						//this.ac.settaken(true);
						return (int) rs.getObject(1);
								
						}
				}
				else {
					return -1;
				}
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}
	@Override
	public synchronized void addsv() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			System.out.println("vao try");
			Connection con=connection();
			String sql=String.format("Insert into sinhvien(name,lop,diem) values('%s','%s',%f)", this.ac.getName(),this.ac.getLop(),this.ac.getDiem());
			PreparedStatement pr=con.prepareStatement(sql);
			pr.executeUpdate();
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}
	public synchronized ArrayList<SinhVien> display() throws RemoteException {
		ArrayList<SinhVien> result=new ArrayList<SinhVien>();
		try {
			Connection con=connection();
			Statement st=con.createStatement();
			String sql="SELECT * FROM sinhvien";
			ResultSet rs=st.executeQuery(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			int socot=rsmd.getColumnCount();
			System.out.println(socot);
			while(rs.next()) {
				SinhVien temp=null;
				temp.setMa(Integer.parseInt(rs.getObject(1).toString()));
				temp.setName(rs.getObject(2).toString());
				temp.setLop(rs.getObject(3).toString());
				temp.setDiem(Float.parseFloat(rs.getObject(4).toString()));
				result.add(temp);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	public synchronized ArrayList<SinhVien> display2() throws RemoteException {
		ArrayList<SinhVien> result=new ArrayList<SinhVien>();
		try {
			Connection con=connection();
			Statement st=con.createStatement();
			String sql="SELECT * FROM sinhvien";
			ResultSet rs=st.executeQuery(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			int socot=rsmd.getColumnCount();
			System.out.println(socot);
			while(rs.next()) {
				SinhVien temp=new SinhVien();
				temp.setMa(Integer.parseInt(rs.getObject(1).toString()));
				temp.setName(rs.getObject(2).toString());
				temp.setLop(rs.getObject(3).toString());
				temp.setDiem(Float.parseFloat(rs.getObject(4).toString()));
				result.add(temp);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	@Override
	public synchronized void registryClient(AtClient c) throws RemoteException {
		this.ac=c;
	}

	@Override
	public synchronized void logout(int ID) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			System.out.println(this.ac.getusername());
			Connection con=connection();
			Statement stmt=con.createStatement();	
			String sql=String.format("SELECT * FROM admin where username='%s' and password='%s'", this.ac.getusername(),this.ac.getpassword());
			ResultSet rs=stmt.executeQuery(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			System.out.println("xong");
			//if(rs.next()) {
				//if((int)rs.getObject(4)==0) {
					//if((int) rs.getObject(1)>0) {
						String sql1=String.format("Update admin SET taken=%d where id=%d",0,ID);
						PreparedStatement pr=con.prepareStatement(sql1);
						pr.executeUpdate();	
						//}
				//}
				
			//}
		} catch (Exception e) {
			System.out.println(e);
		}
		this.ui.addItem("Client ID="+ID+" logout");
	}

	@Override
	public synchronized SinhVien search(int ma) throws RemoteException {
		SinhVien result=new SinhVien();
		try {
			Connection con=connection();
			String sql="SELECT *FROM sinhvien WHERE ma=?";
			PreparedStatement pr=con.prepareStatement(sql);
			pr.setInt(1,ma);
			ResultSet rs=pr.executeQuery();
			if(rs.next()) {
				result.setMa(ma);
				result.setName(rs.getObject(2).toString());
				result.setLop(rs.getObject(3).toString());
				result.setDiem(Float.parseFloat(rs.getObject(4).toString()));
			}
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		return result;
		
	}

	@Override
	public synchronized SinhVien disforup(int index) throws RemoteException {
		SinhVien result=new SinhVien();
		try {
			Connection con=connection();
			Statement st=con.createStatement();
			String sql=String.format("SELECT * FROM sinhvien where ma=%d", index);
			ResultSet rs=st.executeQuery(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			int socot=rsmd.getColumnCount();
			if(rs.next()) {
				result.setMa(index);
				result.setName(rs.getObject(2).toString());
				result.setLop(rs.getObject(3).toString());
				result.setDiem(Float.parseFloat(rs.getObject(4).toString()));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	public synchronized void update(int ma, String name,String lop,Float diem) throws RemoteException{
		try {
			Connection conn=connection();
			String sql="Update sinhvien set name=?,lop=?,diem=? where ma=? ";
			PreparedStatement pr=conn.prepareStatement(sql);
			pr.setString(1, name);
			pr.setString(2, lop);
			pr.setFloat(3, diem);
			pr.setInt(4, ma);
			int temp=pr.executeUpdate();
			System.out.println(temp);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public synchronized SinhVien test(SinhVien s) throws RemoteException {
		SinhVien result=new SinhVien(s.getMa(), s.getName()+"new", s.getLop()+"new", s.getDiem());
		return result;
	}

	@Override
	public synchronized void delete(int ma) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			Connection conn=connection();
			String sql="Delete from sinhvien where ma=?";
			PreparedStatement pr=conn.prepareStatement(sql);
			pr.setInt(1, ma);
			int temp=pr.executeUpdate();
			System.out.println(temp);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
}
