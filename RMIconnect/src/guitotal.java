import java.awt.event.*;
import java.rmi.RemoteException;
import java.awt.*;

import javax.swing.*;

public class guitotal extends JFrame implements ActionListener {
	Client c;
	int ID;
	JLabel lb;
	JButton btnadd,btndis,btnsearch,btnexit;
	JPanel pn1,pn2,pn3,pn4,pn5,pn;
	JFrame fr;
	public guitotal(Client c) {
		super();
		this.c = c;
	}

	public void gui() {
		fr=new JFrame();
		lb=new JLabel("Student management");
		btnadd=new JButton("Add a new student");
		btndis=new JButton("Display list student");
		btnsearch=new JButton("Search student by Masv");
		btnexit=new JButton("Log out");
		btnadd.addActionListener(this);
		btndis.addActionListener(this);
		btnsearch.addActionListener(this);
		btnexit.addActionListener(this);
		pn1=new JPanel(new FlowLayout());
		pn2=new JPanel(new FlowLayout());
		pn3=new JPanel(new FlowLayout());
		pn4=new JPanel(new FlowLayout());
		pn5=new JPanel(new FlowLayout());
		pn=new JPanel(new GridLayout(5,1));
		pn1.add(lb);
		pn2.add(btnadd);
		pn3.add(btndis);
		pn4.add(btnsearch);
		pn5.add(btnexit);
		pn.add(pn1);
		pn.add(pn2);
		pn.add(pn3);
		pn.add(pn4);
		pn.add(pn5);
		fr.add(pn);
		fr.setVisible(true);
		fr.setSize(500, 300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==btnexit) {
			 fr.dispose();
			 try {
				this.c.as.logout(this.ID);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 }
		 if(e.getSource()==btnadd) {
			 this.c.add=new guiadd(this.c);
			 this.c.add.gui();
		 }
		 if(e.getSource()==btndis) {
			 this.c.dis=new guidiss(this.c);
			 this.c.dis.gui();
		 }
		 if(e.getSource()==btnsearch) {
			 this.c.search=new guisearch(this.c);
			 this.c.search.gui();
		 }
	}

}
