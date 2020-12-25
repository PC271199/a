import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Client extends Thread {
	AtClient ac;
	AtServer as;
	//guitotal
	guitotal total;
	//guilogin
	guilogin login;
	//guiaddsv
	guiadd add;
	//guidis
	guidiss dis;
	//guisearch
	guisearch search;
	/*
	 * public void guitotal() { frtotal=new JFrame(); lb=new
	 * JLabel("Student management"); btnadd=new JButton("Add a new student");
	 * btndis=new JButton("Display list student"); btnsearch=new
	 * JButton("Search student by Masv"); btnexit=new JButton("Log out");
	 * btnadd.addActionListener(this); btndis.addActionListener(this);
	 * btnsearch.addActionListener(this); btnexit.addActionListener(this); pn1=new
	 * JPanel(new FlowLayout()); pn2=new JPanel(new FlowLayout()); pn3=new
	 * JPanel(new FlowLayout()); pn4=new JPanel(new FlowLayout()); pn5=new
	 * JPanel(new FlowLayout()); pn=new JPanel(new GridLayout(5,1)); pn1.add(lb);
	 * pn2.add(btnadd); pn3.add(btndis); pn4.add(btnsearch); pn5.add(btnexit);
	 * pn.add(pn1); pn.add(pn2); pn.add(pn3); pn.add(pn4); pn.add(pn5);
	 * frtotal.add(pn); frtotal.setVisible(true); frtotal.setSize(500, 300); }
	 */
	
	protected Client(String ip) throws RemoteException, MalformedURLException, NotBoundException {
		super();
		// TODO Auto-generated constructor stub
		this.ac=new AtClientImpl();
		this.as=(AtServer) Naming.lookup("rmi://"+ip+"/unew");
		this.as.registryClient(this.ac);
		start();
	}
	public void run() {
		this.login=new guilogin(this);
		this.login.gui();
	}
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		//Client c=new Client("localhost");
		//c.login=new guilogin(c);
		//c.login.gui();
		//SinhVien s1=new SinhVien(1, "new", "13", 10);
		//SinhVien ant=c.as.test(s1);
		//System.out.println(ant);
		//c.as.display();
	}
}
