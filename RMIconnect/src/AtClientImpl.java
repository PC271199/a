import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;


public class AtClientImpl extends UnicastRemoteObject implements AtClient {
	

	String user;
	String pass;
	int ID;
	boolean taken=false;
	
	String name;
	String lop;
	float diem;
	

	private static final long serialVersionUID = 1L;

	protected AtClientImpl() throws RemoteException {
		super();
		
	}
//	@Override
//	public ArrayList<String> infordangnhap() throws RemoteException {
//		ArrayList<String> infor=new ArrayList<String>();
//		Scanner sc1=new Scanner(System.in);
//		System.out.print("Nhap username: ");
//		String s1=sc1.nextLine();
//		sc1=sc1.reset();
//		System.out.print("Nhap password: ");
//		String s2=sc1.nextLine();
//		infor.add(s1);
//		infor.add(s2);
//		return infor;
//	}

	@Override
	public String getusername() throws RemoteException {
		// TODO Auto-generated method stub
		return this.user;
	}

	@Override
	public String getpassword() throws RemoteException {
		// TODO Auto-generated method stub
		return this.pass;
	}

	@Override
	public void setusername(String user) throws RemoteException {
		// TODO Auto-generated method stub
		this.user=user;
	}

	@Override
	public void setpassword(String pass) throws RemoteException {
		// TODO Auto-generated method stub
		this.pass=pass;
	}
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getLop() throws RemoteException {
		// TODO Auto-generated method stub
		return this.lop;
	}

	@Override
	public float getDiem() throws RemoteException {
		// TODO Auto-generated method stub
		return this.diem;
	}

	@Override
	public void setName(String name) throws RemoteException {
		this.name=name;
		
	}

	@Override
	public void setLop(String lop) throws RemoteException {
		this.lop=lop;
		
	}

	@Override
	public void setDiem(float diem) throws RemoteException {
		this.diem=diem;
		
	}

	@Override
	public Boolean gettaken() throws RemoteException {
		// TODO Auto-generated method stub
		return this.taken;
	}

	@Override
	public void settaken(boolean taken) throws RemoteException {
		this.taken=taken;
		
	}

	



}
