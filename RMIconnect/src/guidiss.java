import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class guidiss extends JFrame implements ActionListener {
	Client c;
	DefaultTableModel dtmd;
	JLabel lbma,lbten,lblop,lbdiem;
	JTextField txtma,txtten,txtlop,txtdiem;
	JButton btnupdate,btnxoa;
	JTable table;
	JFrame fr;
	JLabel lb;
	JTextArea txta;
	JPanel pn1,pn2,pn3,pn4,pn5,pn,pnn;
	
	public guidiss(Client c) {
		super();
		this.c = c;
	}

	public void gui() {
		fr=new JFrame();
		
		lbma=new JLabel("MSV");
		lbten=new JLabel("Tên");
		lblop=new JLabel("Lớp");
		lbdiem=new JLabel("Điểm");
		
		btnupdate=new JButton("Update");
		btnupdate.addActionListener(this);
		btnxoa=new JButton("Delete");
		btnxoa.addActionListener(this);
		
		txtma=new JTextField(10);
		txtma.setEditable(false);
		txtten=new JTextField(10);
		txtlop=new JTextField(10);
		txtdiem=new JTextField(10);
		
		
		lb=new JLabel("List of all students");
		txta=new JTextArea(10,30);
		
		table = new JTable();
		table.setBounds(10, 11, 400, 248);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			   public void mouseClicked(java.awt.event.MouseEvent evt) {
			     lec_tableMouseClicked(evt);
			     }
			});
		
		dtmd=new DefaultTableModel();
		dtmd.addColumn("Mã");
		dtmd.addColumn("Họ và tên");
		dtmd.addColumn("Lớp");
		dtmd.addColumn("Điểm");
		
		/*
		 * pn1=new JPanel(new FlowLayout()); pn2=new JPanel(new FlowLayout()); pn=new
		 * JPanel(new GridLayout(2,1)); pn1.add(lb); pn2.add(table); pn.add(pn1);
		 * pn.add(pn2); fr.add(pn);
		 */
		pn1=new JPanel(new FlowLayout());
		pn1.add(lbma);
		pn1.add(txtma);
		pn2=new JPanel(new FlowLayout());
		pn2.add(lbten);
		pn2.add(txtten);
		pn3=new JPanel(new FlowLayout());
		pn3.add(lblop);
		pn3.add(txtlop);
		pn4=new JPanel(new FlowLayout());
		pn4.add(lbdiem);
		pn4.add(txtdiem);
		pn5=new JPanel(new FlowLayout());
		pn5.add(btnupdate);
		pn5.add(btnxoa);
		pn=new JPanel(new GridLayout(5,1));
		pn.add(pn1);
		pn.add(pn2);
		pn.add(pn3);
		pn.add(pn4);
		pn.add(pn5);
		
		fr.setLayout(new BorderLayout());
		fr.add(lb,BorderLayout.NORTH);
		fr.add(table,BorderLayout.CENTER);
		fr.add(pn,BorderLayout.WEST);
		
		
		fr.setSize(700, 500);
		try {
			dtmd=new DefaultTableModel();
			dtmd.addColumn("Mã");
			dtmd.addColumn("Họ va tên");
			dtmd.addColumn("Lớp");
			dtmd.addColumn("Điểm");
			/*
			 * txta.append("ma"+"\t"); txta.append("name"+"\t"); txta.append("lop"+"\t");
			 * txta.append("diem"+"\t"); txta.append("\n");
			 */
			Vector vector=new Vector();
			vector.add("Mã");
			vector.add("Họ và tên");
			vector.add("Lớp");
			vector.add("Điểm");
			dtmd.addRow(vector);
			ArrayList<SinhVien> receive=this.c.as.display2();
			for(int i=0;i<receive.size();i++) {
				dtmd.addRow(new Object[] {receive.get(i).getMa(),receive.get(i).getName(),receive.get(i).getLop(),receive.get(i).getDiem()});
			}
			table.setModel(dtmd);
			System.out.println(receive);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		fr.setVisible(true);
	}
	 public void lec_tableMouseClicked(java.awt.event.MouseEvent evt) {                                       
		 	try {
		 		int index=table.getSelectedRow();
		        DefaultTableModel model=(DefaultTableModel) table.getModel();
		        String mastring=model.getValueAt(index, 0).toString();
		        int maint=Integer.parseInt(mastring);
		        SinhVien result=this.c.as.disforup(maint);
		        this.txtma.setText(result.getMa()+"");
		        this.txtten.setText(result.getName().toString());
		        this.txtlop.setText(result.getLop().toString());
		        this.txtdiem.setText(result.getDiem()+"");
			} catch (Exception e) {
				// TODO: handle exception
			}
	           
	   }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnupdate) {
			if (!(this.txtma.getText().equals("")||this.txtten.getText().equals("")||this.txtlop.getText().equals("")||this.txtdiem.getText().equals(""))) {
				try {
					this.c.as.update(Integer.parseInt(txtma.getText()), txtten.getText(),
							txtlop.getText(), Float.parseFloat(txtdiem.getText()));
					dtmd=new DefaultTableModel();
					dtmd.addColumn("Mã");
					dtmd.addColumn("Họ va tên");
					dtmd.addColumn("Lớp");
					dtmd.addColumn("Điểm");
					/*
					 * txta.append("ma"+"\t"); txta.append("name"+"\t"); txta.append("lop"+"\t");
					 * txta.append("diem"+"\t"); txta.append("\n");
					 */
					Vector vector=new Vector();
					vector.add("Mã");
					vector.add("Họ và tên");
					vector.add("Lớp");
					vector.add("Điểm");
					dtmd.addRow(vector);
					ArrayList<SinhVien> receive=this.c.as.display2();
					for(int i=0;i<receive.size();i++) {
						dtmd.addRow(new Object[] {receive.get(i).getMa(),receive.get(i).getName(),receive.get(i).getLop(),receive.get(i).getDiem()});
					}
					table.setModel(dtmd);
					txtma.setText("");
					txtten.setText("");
					txtlop.setText("");
					txtdiem.setText("");
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		}
		if (e.getSource()==btnxoa) {
			if (!(this.txtma.getText().equals(""))) {
				try {
					this.c.as.delete(Integer.parseInt(txtma.getText()));
					dtmd=new DefaultTableModel();
					dtmd.addColumn("Mã");
					dtmd.addColumn("Họ va tên");
					dtmd.addColumn("Lớp");
					dtmd.addColumn("Điểm");
					/*
					 * txta.append("ma"+"\t"); txta.append("name"+"\t"); txta.append("lop"+"\t");
					 * txta.append("diem"+"\t"); txta.append("\n");
					 */
					Vector vector=new Vector();
					vector.add("Mã");
					vector.add("Họ và tên");
					vector.add("Lớp");
					vector.add("Điểm");
					dtmd.addRow(vector);
					ArrayList<SinhVien> receive=this.c.as.display2();
					for(int i=0;i<receive.size();i++) {
						dtmd.addRow(new Object[] {receive.get(i).getMa(),receive.get(i).getName(),receive.get(i).getLop(),receive.get(i).getDiem()});
					}
					table.setModel(dtmd);
					txtma.setText("");
					txtten.setText("");
					txtlop.setText("");
					txtdiem.setText("");
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		}
	}
}
