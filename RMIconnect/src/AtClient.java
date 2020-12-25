import java.awt.event.ActionEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface AtClient extends Remote {
	
	
	public String getusername() throws RemoteException;
	public String getpassword() throws RemoteException;
	public int getID() throws RemoteException;
	public void setusername(String user) throws RemoteException;
	public void setpassword(String pass) throws RemoteException;
	public void setID(int iD) throws RemoteException;
	public void settaken(boolean taken) throws RemoteException;
	
	public Boolean gettaken() throws RemoteException;
	public String getName() throws RemoteException;
	public String getLop() throws RemoteException;
	public float getDiem() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public void setLop(String lop) throws RemoteException;
	public void setDiem(float diem) throws RemoteException;
//	public ArrayList<String> infordangnhap() throws RemoteException;
}
