import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class guiadd extends JFrame implements ActionListener {
	Client c;
	JFrame fr;
	JLabel lb1,lb2,lb3;
	JTextField txtlop,txtname,txtdiem;
	JButton btnadd,btnclear;
	JPanel pn1,pn2,pn3,pn4,pn;
	
	public guiadd(Client c) throws HeadlessException {
		super();
		this.c = c;
	}
	public void gui() {
		fr=new JFrame();
		lb1=new JLabel("name");
		lb2=new JLabel("lop");
		lb3=new JLabel("diem");
		
		txtname=new JTextField(10);
		txtlop=new JTextField(10);
		txtdiem=new JTextField(10);
		
		btnadd=new JButton("add");
		btnclear=new JButton("clear");
		
		btnadd.addActionListener(this);
		btnclear.addActionListener(this);
		pn1=new JPanel(new FlowLayout());
		pn2=new JPanel(new FlowLayout());
		pn3=new JPanel(new FlowLayout());
		pn4=new JPanel(new FlowLayout());
		pn=new JPanel(new GridLayout(4,1));
		pn1.add(lb1);
		pn1.add(txtname);
		pn2.add(lb2);
		pn2.add(txtlop);
		pn3.add(lb3);
		pn3.add(txtdiem);
		pn4.add(btnadd);
		pn4.add(btnclear);
		pn.add(pn1);
		pn.add(pn2);
		pn.add(pn3);
		pn.add(pn4);
		fr.add(pn);
		fr.setSize(500, 300);
		fr.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnadd) {
			try {
				this.c.ac.setName(txtname.getText());
				this.c.ac.setLop(txtlop.getText());
				this.c.ac.setDiem(Float.parseFloat(txtdiem.getText()));
				this.c.as.addsv();
				fr.dispose();
			} catch (Exception e2) {
				System.out.println(e2);
			}
			
		}
	
	}
}
