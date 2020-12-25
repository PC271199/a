import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

public interface AtServer extends Remote {
	public int login(String username,String pass) throws RemoteException;
	public void addsv() throws RemoteException;
	public SinhVien search(int ma) throws RemoteException;
	public void registryClient(AtClient c) throws RemoteException;
	public void logout(int ID) throws RemoteException;
	public ArrayList<SinhVien> display() throws RemoteException;
	public ArrayList<SinhVien> display2() throws RemoteException;
	public SinhVien disforup(int index) throws RemoteException;
	public Connection connection() throws RemoteException;
	public void update(int ma, String name,String lop,Float diem) throws RemoteException;
	public void delete(int ma) throws RemoteException;
	public SinhVien test(SinhVien s) throws RemoteException;
}
