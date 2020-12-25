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

class Chopstick{
    private boolean availability;

    public Chopstick(){
        availability = true;
    }

    public boolean getAvailability(){
        return availability;
    }

    public void setAvailability(boolean flag){
        availability = flag;
    }
}

class Philosophere implements Runnable{
    private Helper hlp;
    private Chopstick l, r;
    private int id;
    public Philosophere(int id, Chopstick l, Chopstick r, Helper i){
        this.hlp = i;
        this.l = l;
        this.r = r;
        this.id = id;
    }

    private void eat(){
        try{
        	int sleepTime = ThreadLocalRandom.current().nextInt(2, 5);
            Thread.sleep(sleepTime*1000);
//            System.out.println("Philosopher "+(id+1)+" eat for "+sleepTime+"s");
        }catch(InterruptedException e){}
    }

    private void think(){
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
//        System.out.println("Philosopher "+(id+1)+" think for "+sleepTime+"s");
        while(true){
            hlp.grabChopsticks(id, l, r);
            eat();
            hlp.releaseChopsticks(id, l, r);
            think();
        }
    }
}


class Helper{
    private Lock mutex = null;
    private String[] state;
    private int[] id;
    public JTextField[] statetf;

    private void outputState(int id){
    	statetf[id].setText(state[id]);
        StringBuffer line = new StringBuffer();
        
        for(int i=0; i<5; i++) {
            line.append(state[i] + " ");
        
	        if(state[i] == "Think") {
	        	statetf[i].setForeground(Color.BLACK);
	        }
	        else if(state[i] == "Hungry") {
	        	statetf[i].setForeground(Color.RED);
	        }
	        else if(state[i] == "Eat") {
	        	statetf[i].setForeground(Color.GREEN);
	        }
        }
        
        System.out.println(line + "(" + (id+1) + ")");
        
        
    }

    public Helper(JTextField[] statetf){
        id = new int[5];
        mutex = new ReentrantLock();
        state = new String[5];
        this.statetf = statetf;
        for(int i=0; i<5; i++){
            id[i] = i;
            state[i] = "Think";
            statetf[i].setText(state[i]);
            statetf[i].setFont(new Font("Calibri", Font.BOLD, 20));
        }
    }

    public void setState(int id, String s){
        state[id] = s;
    }

    public void grabChopsticks(int id, Chopstick l, Chopstick r){
    	setState(id, "Hungry");
        outputState(id);
        mutex.lock();
        try{

            l.setAvailability(false);
            r.setAvailability(false);
            setState(id, "Eat");
            outputState(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
//        	mutex.unlock();
        }
    }

    public void releaseChopsticks(int id, Chopstick l, Chopstick r){
//    	mutex.lock();
        try{
            setState(id, "Think");
            l.setAvailability(true);
            r.setAvailability(true);
            outputState(id);
        }finally{
            mutex.unlock();
        }
    }
}

public class imit extends JFrame implements ActionListener {
    private Chopstick[] s;
    private Philosophere[] f;
    private Helper hlp;
    private JFrame fr;
    private JPanel[][] pn;
    private JPanel[] statepns;
    private ImageIcon[] icons;
    private JLabel[] labels;
    public JTextField[] statefields;
    
    private void gui() {
    	fr = new JFrame();
    	pn = new JPanel[4][3];
    	icons = new ImageIcon[5];
    	labels = new JLabel[5];
    	statefields = new JTextField[5];
    	statepns = new JPanel[5];
    	
    	fr.setLayout(new GridLayout(4, 3));
    	
    	for(int m = 0; m < 4; m++) {
		   for(int n = 0; n < 3; n++) {
		      pn[m][n] = new JPanel(new GridLayout(2,1));
		      fr.add(pn[m][n]);
		   }
		}
    	
    	pn[0][0].setSize(120, 144);
    	pn[0][2].setSize(120, 144);
    	pn[2][0].setSize(120, 144);
    	pn[2][2].setSize(120, 144);
    	pn[3][0].setSize(120, 144);
    	pn[3][2].setSize(120, 144);
    	
    	pn[0][1].setSize(120, 144*3);
    	pn[2][1].setSize(120, 144*3);
    	pn[3][1].setSize(120, 144*3);
    	
    	pn[1][0].setSize(120*3, 144);
    	pn[1][2].setSize(120*3, 144);
    	
    	pn[1][1].setSize(120*3, 144*3);
    	
    	for(int i=0; i<5; i++) {
    		//
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
    	pn[1][0].add(labels[1]);
    	pn[1][0].add(statefields[1]);
    	pn[1][2].add(labels[2]);
    	pn[1][2].add(statefields[2]);
    	pn[3][0].add(labels[3]);
    	pn[3][0].add(statefields[3]);
    	pn[3][2].add(labels[4]);
    	pn[3][2].add(statefields[4]);
    	
    	
    	
//    	fr.add(pn);
    	fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	fr.pack();
    	fr.setSize(720,720);
		fr.setVisible(true);

    }

    private void init(){
        s = new Chopstick[5];
        f = new Philosophere[5];
        hlp = new Helper(statefields);
        for(int i=0; i<5; i++)
            s[i] = new Chopstick();

        for(int i=0; i<5; i++){
            f[i] = new Philosophere(i, s[i], s[(i+4)%5], hlp);
            new Thread(f[i]).start();
        }

    }

    public imit(){
    	gui();
    	init();
    }

    public static void main(String[] args){
        new imit();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}