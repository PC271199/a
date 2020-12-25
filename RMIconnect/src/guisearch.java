import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class guisearch implements ActionListener {
	Client c;
	JLabel lb1,lb2,lb3,lb4;
	JTextField txtcode,txtname,txtlop,txtdiem;
	JButton btnsearch;
	JPanel pn1,pn2,pn3,pn4,pn;
	JFrame fr;
	public guisearch(Client c) {
		super();
		this.c = c;
	}
	public void gui() {
		fr=new JFrame();
		lb1=new JLabel("Enter code");
		lb2=new JLabel("Student name");
		lb3=new JLabel("Lop");
		lb4=new JLabel("Diem");
		
		txtcode=new JTextField(10);
		txtname=new JTextField(10);
		txtlop=new JTextField(10);
		txtdiem=new JTextField(10);
		
		txtlop.setEditable(false);
		txtname.setEditable(false);
		txtdiem.setEditable(false);
		btnsearch=new JButton("Search");
		btnsearch.addActionListener(this);
		pn1=new JPanel(new FlowLayout());
		pn2=new JPanel(new FlowLayout());
		pn3=new JPanel(new FlowLayout());
		pn4=new JPanel(new FlowLayout());
		pn=new JPanel(new GridLayout(4,1));
		pn1.add(lb1);
		pn1.add(txtcode);
		pn1.add(btnsearch);
		pn2.add(lb2);
		pn2.add(txtname);
		pn3.add(lb3);
		pn3.add(txtlop);
		pn4.add(lb4);
		pn4.add(txtdiem);
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
		if(e.getSource()==btnsearch) {
			try {
				SinhVien list=new SinhVien();
				list=this.c.as.search(Integer.parseInt(txtcode.getText()));
				txtname.setText(list.getName().toString());
				txtlop.setText(list.getLop().toString());
				txtdiem.setText(list.getDiem()+"");
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
		
	}
}