import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class connectsv extends JFrame implements ActionListener {
	JLabel lb;
	JTextField txtip;
	JButton btnconn;
	JPanel pn1,pn2,pn;
	JFrame fr;
	public void gui() {
		fr=new JFrame();
		lb=new JLabel("Server IP Address");
		txtip=new JTextField(10);

		
		btnconn=new JButton("Connect");
		btnconn.addActionListener(this);

		

		pn1=new JPanel(new FlowLayout());
		pn2=new JPanel(new FlowLayout());
		pn=new JPanel(new GridLayout(2,1));
		
		pn1.add(lb);
		pn1.add(txtip);
		pn2.add(btnconn);
		pn.add(pn1);
		pn.add(pn2);
		
		fr.add(pn);
		fr.setSize(300, 120);
		fr.setVisible(true);
	}
	public static void main(String[] args) {
		new connectsv().gui();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnconn) {
			try {
				Client c=new Client(this.txtip.getText());
				this.fr.setVisible(false);
			} catch (RemoteException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
