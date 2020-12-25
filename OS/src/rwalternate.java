import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Monitoral
{
	public static int[] nr; //so nguoi dang doc
	public static int nw; //so nguoi dang viet 0 or 1
	public static int nwtotal;// so nguoi dang viet va waiting for viet
	public static int thisbatch;
	public static int nextbatch;
	private String[] state;
    private int[] id;
    public JTextField[] statetf;
    
	public Monitoral(JTextField[] statetf)
	{
		nr = new int[2];
		nr[0]=0;
		nr[1]=0;
		thisbatch=0;
		nextbatch=1-thisbatch;
		nw = 0;
		nwtotal=0;
		id = new int[6];
        state = new String[6];
        this.statetf = statetf;
        for(int i=0; i<6; i++){
            id[i] = i;
            state[i] = "Waiting";
            statetf[i].setText(state[i]);
            statetf[i].setFont(new Font("Calibri", Font.BOLD, 20));
        }
	}
	private void outputState(int id){
    	statetf[id].setText(state[id]);
//        StringBuffer line = new StringBuffer();
        for(int i=0; i<6; i++) {
//            line.append(state[i] + " ");
            if(state[i] == "Waiting") {
	        	statetf[i].setForeground(Color.BLACK);
	        }
	        else if(state[i] == "ready-to-read") {
	        	statetf[i].setForeground(Color.RED);
	        }
	        else if(state[i] == "ready-to-write") {
	        	statetf[i].setForeground(Color.RED);
	        }
	        else if(state[i] == "Reading") {
	        	statetf[i].setForeground(Color.GREEN);
	        }
	        else if(state[i] == "Writing") {
	        	statetf[i].setForeground(Color.GREEN);
	        }
        }
//        System.out.println(line + "(" + (id) + ")");
    }
	public void setState(int id, String s){
        state[id] = s;
    }
	public synchronized void Start_Read(int id) throws InterruptedException
	{
		System.out.println("entered start read of thread "+id);
		setState(id, "ready-to-read");
		outputState(id);
		if (nwtotal==0) nr[thisbatch]++;
	    else {
	        nr[nextbatch]++;
	        int myBatch=nextbatch;
	        while (thisbatch!=myBatch) wait();
	    }
		setState(id, "Reading");
		outputState(id);
	}
	
	public synchronized void End_Read(int id)
	{
		System.out.println("entered end read of thread "+id);
		nr[thisbatch]--; 
		setState(id, "Waiting");
		outputState(id);
		if (nr[thisbatch]==0) {
	        notifyAll();
	    }
	}

	public synchronized void Start_Write(int id) throws InterruptedException
	{
		nwtotal++;
		System.out.println("entered start write of thread "+id);
		setState(id, "ready-to-write");
		outputState(id);
		nwtotal++;
	    while (nr[thisbatch]+nw != 0) wait();
	    nw=1;
		setState(id, "Writing");
		outputState(id);
	}
	
	public synchronized void End_Write(int id)
	{

		System.out.println("entered end write of thread "+id);
		nw=0; nwtotal--;
	    int tmp=thisbatch; thisbatch=nextbatch; nextbatch=tmp;
		setState(id, "Waiting");
		outputState(id);
		notifyAll();
	
	}
	
}


class Readeral implements Runnable
{

	private static Monitoral M;
	private int id;
	public Readeral(int id,Monitoral c)
	{
		this.id=id;
		M=c;
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
	public void run()
	{
		try{
        	int sleepTime = ThreadLocalRandom.current().nextInt(4, 9);
            Thread.sleep(sleepTime*1000);
//            System.out.println("Philosopher "+(id+1)+" think for "+sleepTime+"s");
        }catch(InterruptedException e){}
		while(true)
		{
				try {
					M.Start_Read(id);
					working();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				System.out.println("Reader "+getName()+" is retreiving "+i);
				finally {
					M.End_Read(id);
					waiting();
				}
				
		}
	}
}

class Writeral implements Runnable
{

	private static Monitoral M;
	private int id;
	public Writeral(int id, Monitoral d)
	{
		this.id=id;
		M = d;
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
	public void run()
	{
		try{
        	int sleepTime = ThreadLocalRandom.current().nextInt(4, 9);
            Thread.sleep(sleepTime*1000);
//            System.out.println("Philosopher "+(id+1)+" think for "+sleepTime+"s");
        }catch(InterruptedException e){}
//		for(int i=0;i<10;i++)
		//int i=0;
		while(true)
		{
			
				//System.out.println(M.nw);
				try {
					M.Start_Write(id);
					working();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finally {
					M.End_Write(id);
					waiting();
				}
		}
	}
}

class rwalternate
{
	private Writeral[] ws;
	private Readeral[] rs;
    private Monitoral M;
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
    	
    	pn[1][0].setSize(120*3, 144);
    	pn[1][1].setSize(120*3, 144);
    	
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
    	
    	pn[0][0].add(labels[3]);
    	pn[0][0].add(statefields[3]);
    	pn[0][1].add(labels[0]);
    	pn[0][1].add(statefields[0]);
    	pn[1][0].add(labels[4]);
    	pn[1][0].add(statefields[4]);
    	pn[1][1].add(labels[1]);
    	pn[1][1].add(statefields[1]);
    	pn[2][0].add(labels[5]);
    	pn[2][0].add(statefields[5]);
    	pn[2][1].add(labels[2]);
    	pn[2][1].add(statefields[2]);
    	
    	
    	
    	fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	fr.pack();
    	fr.setSize(720,720);
		fr.setVisible(true);

    }
    
	private void init(){
        M = new Monitoral(statefields);
        ws = new Writeral[3];
        rs = new Readeral[3];
        for(int i=0; i<3; i++){
        	rs[i] = new Readeral(i, M);
        	new Thread(rs[i]).start();
        }
        for(int i=0; i<3; i++){
        	ws[i] = new Writeral(i+3, M);
        	new Thread(ws[i]).start();
        }

    }
	public rwalternate(){
		gui();
        init();
    }
	public static void main(String [] args)
	{
		new rwalternate();
	}


}
