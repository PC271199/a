import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class guilogin extends JFrame implements ActionListener {
	Client c;
	public guilogin(Client c) throws HeadlessException {
		super();
		this.c = c;
	}
	JLabel lb1,lb2,ketqua;
	JTextField txtuser;
	JPasswordField txtpassword;
	JButton btndangnhap;
	JPanel pn1l,pn2l,pn3l,pn4l,pnl;
	JFrame frlg;
	
	public void gui() {
		frlg=new JFrame();
		lb1=new JLabel("Nhap username");
		lb2=new JLabel("Nhap password");
		ketqua=new JLabel("Xac thuc dang nhap");
		
		txtuser=new JTextField(10);
		txtpassword=new JPasswordField(10);

		
		btndangnhap=new JButton("Dang nhap");
		btndangnhap.addActionListener(this);

		

		pn1l=new JPanel(new FlowLayout());
		pn2l=new JPanel(new FlowLayout());
		pn3l=new JPanel(new FlowLayout());
		pn4l=new JPanel(new FlowLayout());
		pnl=new JPanel(new GridLayout(4,1));
		
		pn1l.add(lb1);
		pn1l.add(txtuser);
		pn2l.add(lb2);
		pn2l.add(txtpassword);
		pn3l.add(btndangnhap);
		pn4l.add(ketqua);
		pnl.add(pn1l);
		pnl.add(pn2l);
		pnl.add(pn3l);
		pnl.add(pn4l);
		
		frlg.add(pnl);
		frlg.setSize(500, 300);
		frlg.setVisible(true);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btndangnhap) {
			try {
				int IDtemp;
				//this.c.ac.setusername(txtuser.getText());
				//this.c.ac.setpassword(String.valueOf(txtpassword.getPassword()));
				//while(true) {
					//if(client.getstate()) {
				if((IDtemp=this.c.as.login(txtuser.getText(),String.valueOf(txtpassword.getPassword())))>0) {
					//this.c.ac.setID(IDtemp);
					this.ketqua.setText("Dang nhap thanh cong");
					//this.guitotal();
					this.c.total=new guitotal(this.c);
					this.c.total.ID=IDtemp;
					this.c.total.gui();
					this.txtuser.setText("");
					this.txtpassword.setText("");
				}
				else if((this.c.as.login(txtuser.getText(),String.valueOf(txtpassword.getPassword())))==-1){
					JOptionPane.showMessageDialog(null, "Tài khoản đã đăng nhập");
				}
				else {
					JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu");
				}
				
			} catch (Exception exx) {
				System.out.println(exx);
			}
		}
		
	}
}
