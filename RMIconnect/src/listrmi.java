import java.rmi.registry.*; 
import java.rmi.*;
class listrmi {
public static void main (String args[]) {
System.out.println ("Connecting registry…");
try { 
//Kết nối với registry
Registry reg = LocateRegistry.getRegistry("localhost");
//Lấy về danh sách các đối tượng do registry nắm giữ
String object [] = reg.list();
//Liệt kê danh sách các đối tượng
for (int i=0; i<object.length; i++) 
System.out.println(object[i]);
}
catch (RemoteException e){System.out.println (e);}
}
} 