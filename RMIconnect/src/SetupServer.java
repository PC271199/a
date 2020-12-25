import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.*;
import javax.swing.JFrame; 
public class SetupServer extends JFrame implements ActionListener {
	public DefaultListModel<String> dlm;
	private JList lst;
	private JLabel lblMessage;
	private JButton btnStart,btnStop,btnExit;
	
	public SetupServer(){
		super("Set up Server");
		lst=new JList<String>(dlm=new DefaultListModel<String>());
	
		this.add(new JScrollPane(lst),BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,300);
		
		JPanel pB=new JPanel(new GridLayout(2,1));
		JPanel pF=new JPanel();
		pB.add(pF);
		pF.add(btnStart=new JButton("Start"));
		pF.add(btnStop=new JButton("Stop"));
		btnStop.setEnabled(false);
		pF.add(btnExit=new JButton("Exit"));
		pB.add(lblMessage=new JLabel());

		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		btnExit.addActionListener(this);

		this.add(pB,BorderLayout.SOUTH);
	}
	public void DislplayInfos(String msg){
		lblMessage.setText(msg);
	}
	public void addItem(String item){
		dlm.addElement(item);
	}
	/*
	 * public void addItem(String item){ dlm.addElement(item); }
	 */
	public static void main(String[]args){
		try{
			SetupServer ui=new SetupServer();
			LocateRegistry.createRegistry(1099);
			ui.DislplayInfos("QLSV server is stop…");
			ui.setVisible(true);
		}catch(Exception x){
			x.printStackTrace();
		}
	}
		/*
		 * public static void main(String[] args) throws RemoteException,
		 * MalformedURLException { try { AtServer u=new AtServerImpl();
		 * Naming.rebind("rmi://localhost/uconnect", u);
		 * System.out.println("Waiting for client request"); } catch (Exception e) {
		 * 
		 * }
		 * 
		 * }
		 */

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==btnStart){
			new Thread(new Runnable(){
				public void run(){
					try {
						AtServer as=null;
						as=new AtServerImpl(SetupServer.this);
						//setupserver.this=ui (line 52)
						Naming.rebind("rmi://localhost/unew", as);
//						Naming.rebind("rmi://localhost:1099/unew", as);
						lblMessage.setText("QLSV server is running…");
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}).start();

			btnStop.setEnabled(true);
			btnStart.setEnabled(false);
		}
		if(e.getSource()==btnStop){
			try {
				Naming.unbind("rmi://localhost/unew");
				btnStop.setEnabled(false);
				btnStart.setEnabled(true);
				lblMessage.setText("QLSV server is stopped…");
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if(e.getSource()==btnExit){
			System.exit(1);
			}
		}

}
