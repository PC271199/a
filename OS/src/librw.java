import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JComboBox;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
class mon
{
	private Lock lock = null;
    private String[] state;
    private int[] id;
    public JTextField[] statetf;
    public boolean l = false;
    
    private void outputState(int id){
    	statetf[id].setText(state[id]);
        StringBuffer line = new StringBuffer();
        for(int i=0; i<6; i++) {
            line.append(state[i] + " ");
            if(state[i] == "Waiting") {
	        	statetf[i].setForeground(Color.BLACK);
	        }
	        else if(state[i] == "Ready-to-read") {
	        	statetf[i].setForeground(Color.RED);
	        }
	        else if(state[i] == "Ready-to-write") {
	        	statetf[i].setForeground(Color.RED);
	        }
	        else if(state[i] == "Reading") {
	        	statetf[i].setForeground(Color.GREEN);
	        }
	        else if(state[i] == "Writing") {
	        	statetf[i].setForeground(Color.GREEN);
	        }
        }
        System.out.println(line + "(" + (id) + ")");
    }

    public mon(JTextField[] statetf){
        id = new int[6];
        lock = new ReentrantLock();
        state = new String[6];
        this.statetf = statetf;
        for(int i=0; i<6; i++){
            id[i] = i;
            state[i] = "Waiting";
            statetf[i].setText(state[i]);
            statetf[i].setFont(new Font("Calibri", Font.BOLD, 20));
        }
    }

    public void setState(int id, String s){
        state[id] = s;
    }

    public void active(int id,boolean type){
    	if(type==true) {
    		setState(id, "Ready-to-read");
            outputState(id);
            lock.lock();
            try{
                setState(id, "Reading");
                outputState(id);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
            }
    	}
    	else {
    		setState(id, "Ready-to-write");
            outputState(id);
            lock.lock();
            try{
                setState(id, "Writing");
                outputState(id);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
            }
    	}
    }


    public void release(int id){
        try{
            setState(id, "Waiting");
            outputState(id);
        }finally{
            lock.unlock();
        }
    }
	
}

class Work implements Runnable{
    private mon M;
    private int id;
    private boolean type;
    public Work(int id, mon M,boolean type){
        this.M = M;
        this.id = id;
        this.type=type;
    }

    private void working(){
        try{
        	int sleepTime = ThreadLocalRandom.current().nextInt(2, 5);
            Thread.sleep(sleepTime*1000);
        }catch(InterruptedException e){}
    }

    private void waiting(){
        try{
        	int sleepTime = ThreadLocalRandom.current().nextInt(4, 9);
            Thread.sleep(sleepTime*1000);
//            System.out.println("Philosopher "+(id+1)+" think for "+sleepTime+"s");
        }catch(InterruptedException e){}
    }

    public void run(){
    	int sleepTime = ThreadLocalRandom.current().nextInt(1, 5);
        try {
			Thread.sleep(sleepTime*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        while(true){
            M.active(id,type);
            working();
            M.release(id);
            waiting();
        }
    }
}

class librw extends JFrame
{
    private Work[] w;
    private mon M;
    private JFrame fr;
    private JPanel[][] pn;
    private JPanel[] statepns;
    private ImageIcon[] icons;
    private JLabel[] labels;
    public JTextField[] statefields;
    
    private void gui() {
    	fr = new JFrame();
    	pn = new JPanel[3][2];
    	icons = new ImageIcon[6];
    	labels = new JLabel[6];
    	statefields = new JTextField[6];
    	statepns = new JPanel[6];
    	
    	fr.setLayout(new GridLayout(5, 2));
    	
    	for(int m = 0; m < 3; m++) {
		   for(int n = 0; n < 2; n++) {
		      pn[m][n] = new JPanel(new GridLayout(2,1));
		      fr.add(pn[m][n]);
		   }
		}
    	
    	pn[0][0].setSize(120, 144);
    	pn[0][1].setSize(120, 144);
    	pn[2][0].setSize(120, 144);
    	pn[2][1].setSize(120, 144);
//    	pn[3][0].setSize(120, 144);
//    	pn[3][1].setSize(120, 144);
//    	pn[4][0].setSize(120, 144);
//    	pn[4][1].setSize(120, 144);
    	
//    	pn[0][1].setSize(120, 144*3);
//    	pn[2][1].setSize(120, 144*3);
//    	pn[3][1].setSize(120, 144*3);
//    	pn[4][1].setSize(120, 144*3);
    	
    	pn[1][0].setSize(120*3, 144);
    	pn[1][1].setSize(120*3, 144);
//    	pn[1][1].setSize(120*3, 144*3);
    	
    	for(int i=0; i<6; i++) {
    		icons[i] = new ImageIcon("./img/p" + (i+1) + ".png");
    		Image img = icons[i].getImage();
    		labels[i] = new JLabel(new ImageIcon(img.getScaledInstance(120, 144, Image.SCALE_SMOOTH)));
    		statefields[i] = new JTextField();
    		statefields[i].setEditable(false);
    		statefields[i].setHorizontalAlignment(JTextField.CENTER);
    		statepns[i] = new JPanel();
    		statepns[i].add(statefields[i]);
    		statepns[i].setBounds(50,100,80,30);
    	}
    	
    	pn[0][1].add(labels[0]);
    	pn[0][1].add(statefields[0]);
    	pn[0][0].add(labels[1]);
    	pn[0][0].add(statefields[1]);
    	pn[1][1].add(labels[2]);
    	pn[1][1].add(statefields[2]);
    	pn[1][0].add(labels[3]);
    	pn[1][0].add(statefields[3]);
    	pn[2][1].add(labels[4]);
    	pn[2][1].add(statefields[4]);
    	pn[2][0].add(labels[5]);
    	pn[2][0].add(statefields[5]);
    	
    	
    	
//    	fr.add(pn);
    	fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	fr.pack();
    	fr.setSize(720,720);
		fr.setVisible(true);

    }
    private void init(){
        w = new Work[6];
        M = new mon(statefields);

        for(int i=0; i<6; i++){
            if(i%2==0) {
            	w[i] = new Work(i, M,true);
            }
            else {
            	w[i] = new Work(i, M,false);
            }
            new Thread(w[i]).start();
        }

    }

    public librw(){
    	gui();
        init();
    }

    public static void main(String[] args){
        new librw();
    }


}
