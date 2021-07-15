import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import acm.graphics.G3DRect;
import acm.graphics.GLabel;
import acm.graphics.GMath;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class TOH extends GraphicsProgram {
	
	public void init() {
		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		title=new GLabel("TOWER OF HANOI PUZZLE");
		title.setFont("Times New Roman-BOLD-30");
		title.setColor(Color.DARK_GRAY);
		add(title,350,50);
		numOfDisksLbl=new GLabel("Numberof disks:");
		numOfDisksLbl.setFont("Times New Roman-BOLD-18");
		add(numOfDisksLbl,140,200);
		numOfMovesLbl=new GLabel("Number of moves:");
		numOfMovesLbl.setFont("Times New Roman-BOLD-18");
		numOfMovesLbl.setColor(Color.BLUE);
		add(numOfMovesLbl,530,200);
		
		numOfMoves=new GLabel(""+num_moves);
		numOfMoves.setFont("Times New Roman-BOLD-18");
		numOfMoves.setColor(Color.RED);
		add(numOfMoves,680,200);
		
		minNumMovesLbl=new GLabel("Minimum number of moves required: ");
		minNumMovesLbl.setFont("Times New Roman-BOLD-18");
		minNumMovesLbl.setColor(new Color(128,0,0));
		add(minNumMovesLbl,710,200);
		minNumMoves=new GLabel("");
		minNumMoves.setFont("Times New Roman-BOLD-18");
		minNumMoves.setColor(Color.BLACK);
		add(minNumMoves,1010,200);
		
		numDiskButton= new JComboBox();
		for(int i=1;i<=12;i++) {
			numDiskButton.addItem(i);
		}
		numDiskButton.setSelectedItem(3);
		add(numDiskButton,270,180);
		go_button=new JButton("Go");
		go_button.setSize(50, 25);
		add(go_button,320,180);
		
		solve_button=new JButton("Solve");
		solve_button.setSize(70, 25);
		add(solve_button,375,180);
		

		pause_icon=new ImageIcon(getClass().getClassLoader().getResource("pause26.png"));
		play_icon=new ImageIcon(getClass().getClassLoader().getResource("play26.png"));
		
		pause_button=new JButton();
		pause_button.setIcon(pause_icon);
		pause_button.setActionCommand("Pause");
		pause_button.setSize(70, 25);
		add(pause_button,455,180);
		pause_button.setEnabled(false);
		
		cong=new GLabel("Congratulations! You have finished the puzzle in "+num_moves);
		cong.setFont("Times New Roman-BOLD-25");
		cong.setColor(new Color(0,100,0));
		cong.setVisible(false);
		add(cong,350,650);

		minNumMoves.setLabel(""+Math.round(Math.pow(2, numDiskButton.getSelectedIndex()+1)-1));
		
		clipURL1= getClass().getResource("diskTouchSound.wav");
		clipURL2= getClass().getResource("computerError.wav");
		touchClip=Applet.newAudioClip(clipURL1);
		errClip=Applet.newAudioClip(clipURL2);
		new_setup = true;
		addMouseListeners();
		addActionListeners();

	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Go")) {
			solve_it =false;
			pause_it=false;
			new_setup=false;
			solve_button.setEnabled(true);
			tohc.removeAll();
			numOfDisks=numDiskButton.getSelectedIndex()+1;
			numOfDisksLeft=numOfDisks;
			tohc=new TOHC(numOfDisks);
			minNumMoves.setLabel(""+Math.round(Math.pow(2, numDiskButton.getSelectedIndex()+1)-1));
			num_moves=0;
			puzzle_finished=false;
			cong.setVisible(false);
			numOfMoves.setLabel(""+num_moves);
			add(tohc,100,100);
		}
		else if (e.getActionCommand().equals("Solve")) {
			solve_it =true;
			numOfDisks=numDiskButton.getSelectedIndex()+1;
			solve_button.setEnabled(false);
			tohc.removeAll();
			tohc=new TOHC(numOfDisks);
			minNumMoves.setLabel(""+Math.round(Math.pow(2, numDiskButton.getSelectedIndex()+1)-1));
			num_moves=0;
			numOfMoves.setLabel(""+num_moves);
			add(tohc,100,100);
			new_setup=false;		
			puzzle_finished=false;
			cong.setVisible(false);
			
			
			pause_button.setEnabled(true);
			go_button.setEnabled(false);
			pause(100);
	

		}
		
		
		else if (e.getActionCommand().equals("Pause")) {
			pause_it=true;
			pause_button.setActionCommand("Play");
			pause_button.setIcon(play_icon);
		}
		else if (e.getActionCommand().equals("Play")) {
			pause_it=false;
			pause_button.setActionCommand("Pause");
			pause_button.setIcon(pause_icon);
		}
	}
	
	public void run() {
		numOfDisks=numDiskButton.getSelectedIndex()+1;
		numOfDisksLeft = numOfDisks;
		tohc = new TOHC(numOfDisks);
		
		add(tohc,TOHC_X_OFFSET,TOHC_Y_OFFSET);
		while(true) {
			if(solve_it) {
				solveHanoi(numOfDisks,tohc.getPeg1(), tohc.getPeg2());
				
			}
			
			pause(10);
			if (numOfDisksLeft == 0) {
				go_button.setEnabled(true);
				puzzle_finished=true;
				go_button.setEnabled(true);
				pause(20);
			}
		}


	}
	
	public PEG thirdPeg(PEG first, PEG second) {
		if (first.getNumber() + second.getNumber() == 3) {
			return tohc.getPeg3();
		}
		else if (first.getNumber() + second.getNumber() == 4) {
			return tohc.getPeg2();
			
		}
		else {
			return tohc.getPeg1();
		}
	}
	
	public void moveDisk(PEG src, PEG dest) {
	
	    DISK disk=src.getTop();
	
    	if(disk!=null) {
	    	tohc.setActiveDisk(disk);
	        if (disk.getBelowDisk()!=null) {
		        src.setTop(disk.getBelowDisk());
	        }
	        else {
		        src.setTop(null);
	        }
	
	        for(int i=1;i<=30;i=i+3) {
		       disk.move(0, (src.getY()-disk.getY())-i);
		       pause(30);
	        }
	        if (src.getNumber() < dest.getNumber()) {
	        	double x =disk.getX();
	        	for(int i=0;i<=(dest.getNumber()-src.getNumber())*30;i=i+3) {
		        	disk.setLocation(x+i*(dest.getCx()-(disk.getWidth()-5)/2 -x)/((dest.getNumber()-src.getNumber())*30) ,disk.getY());
		        	pause(20);	
	        	}

	        }
	        else {
	        	double x =disk.getX();
	        	for(int i=0;i<=(src.getNumber()-dest.getNumber())*30;i=i+3) {
		        	disk.setLocation(x-i*(x-dest.getCx()-(disk.getWidth()-5)/2 )/((src.getNumber()-dest.getNumber())*30) ,disk.getY());
		        	pause(20);	
	        	}
	        }
	        
	    	disk.setLocation(dest.getCx()-(disk.getWidth()-5)/2,disk.getY());
	    	 pause(20);
	    	 
	          dest.stackOnPeg(disk);
	          touchClip.play();
	          tohc.setActiveDisk(null);
	          disk.setPeg(dest);
	          numOfMoves.setLabel(""+num_moves);
	          pause(20);
	    	 
	        
    	}	
		
	}
	


	public void solveHanoi(int numDisks,PEG src, PEG dest) {
		while(pause_it) {
			pause(20);
		}
		if (numOfDisksLeft == 0) {
			solve_it=false;
		}
		if (numDisks == 0) {
			

		}
		else {
			PEG third = thirdPeg(src,dest);
			solveHanoi(numDisks-1,src, third);
			pause(50);
			moveDisk(src,dest);
			if ( src == tohc.getPeg1()) {
				numOfDisksLeft--;
			}
			else if (dest == tohc.getPeg1()) {
				numOfDisksLeft++;
			}
			pause(50);
			solveHanoi(numDisks-1,third, dest);
		}

		
		
	}
	
	public static void incrementMoves() {
		num_moves++;
	}
	
	public void mousePressed(MouseEvent e) {
		if((!puzzle_finished )&& (!solve_it) && (numOfDisksLeft >0)) {
		    double x=e.getX()-TOHC_X_OFFSET;
		    PEG peg=closePegFrom(x);
		    DISK disk=peg.getTop();
		
	    	if(disk!=null) {
		    	tohc.setActiveDisk(disk);
		        if (disk.getBelowDisk()!=null) {
			        peg.setTop(disk.getBelowDisk());
		        }
		        else {
			        peg.setTop(null);
		        }
		        
		       /* for(int i=1;i<=30;i=i+3) {
				       disk.move(0, (peg.getY()-disk.getY())-i);
				       pause(30);
			    }*/
		        disk.move(0, ((peg.getY()-disk.getY())-30));
		        /*for(int i=1;i<=10;i++) {
		           //disk.setLocation(disk.getX(), i*((peg.getY()-disk.getY())-30)/10);
			       disk.move(0, ((peg.getY()-disk.getY())-30));
			       pause(10);
		        }*/
		
	    	}
		
		}
		
	}
	
	public void mouseDragged(MouseEvent e) {
		if((!puzzle_finished) && (!solve_it)&& (numOfDisksLeft >0)) {
			 double x=e.getX()-TOHC_X_OFFSET;
		    PEG peg=closePegFrom(x);
		    DISK adisk=tohc.getActiveDisk();
		    if (adisk!=null) {	
			   adisk.setLocation(peg.getCx()-(adisk.getWidth()-5)/2,adisk.getY());
		    }
		}

		
		
	}
	
	public void mouseReleased(MouseEvent e) {
		if((!puzzle_finished) && (!solve_it)&& (numOfDisksLeft >0)) {
			double x=e.getX()-TOHC_X_OFFSET;
		    PEG peg=closePegFrom(x);
		    DISK adisk = tohc.getActiveDisk();
		    if(peg.getTop()!=null) {
		    	if(adisk!=null) {
		    	    if((adisk.getWidth()<peg.getTop().getWidth())) {
			           peg.stackOnPeg2(adisk);
			           touchClip.play();
			           tohc.setActiveDisk(null);
			           adisk.setPeg(peg);
			           numOfMoves.setLabel(""+num_moves);
			         
		           	}
		        	else {
	
				        adisk.movebackToPeg();
				        errClip.play();
		    	    }
		    	
		    	}

		   }
	       else {
	    	   
              if(adisk!=null) {
		          peg.stackOnPeg2(adisk);
			      adisk.setPeg(peg);
			      touchClip.play();
			      adisk.setBelowDisk(null);
              }
		      tohc.setActiveDisk(null);

		      numOfMoves.setLabel(""+num_moves);
		      
		  }

		
		}
		if (puzzleCompleted()) {
			puzzle_finished=true;
			cong.setLabel("Congratulations! You have finished the puzzle in "+num_moves+" moves");
			cong.setVisible(true);
		}
		
		
	
	}
	
	public PEG closePegFrom(double x) {
		PEG p1=tohc.getPeg1();
		PEG p2=tohc.getPeg2();
		PEG p3=tohc.getPeg3();
		double d1= Math.abs(x-p1.getX());
		double d2=Math.abs(x-p2.getX());;
		double d3=Math.abs(x-p3.getX());
		if (d1 < d2) {
			if (d1<d3) {
				return p1;
			}
	
			else {
				return p3;
			}
		}
		else {
			if(d2<d3) {
				return p2;
			}
			else  {
				return p3;
			}
		
		}
	}
	
	public boolean puzzleCompleted() {
		if (tohc.getPeg1().getTop()==null) {
			if((tohc.getPeg2().getTop()==null)||(tohc.getPeg3().getTop()==null)) {
				return true;
			}
			
		}
		return false;
	}
	private static int numOfDisksLeft;
	private TOHC tohc;
	private ImageIcon pause_icon,play_icon;
	private boolean solve_it, pause_it,new_setup;
	private AudioClip touchClip,errClip;
	private URL clipURL1,clipURL2;
	private static boolean puzzle_finished=false;
	private int numOfDisks;
	private static int num_moves=0;
	private GLabel numOfDisksLbl,numOfMovesLbl,numOfMoves,title,cong,minNumMovesLbl,minNumMoves;
	private JButton go_button,solve_button,pause_button;
	private JComboBox numDiskButton;
	private static int TOHC_X_OFFSET=100;
	private static int TOHC_Y_OFFSET=100;
	private static final int APPLICATION_WIDTH=1200;
	private static final int APPLICATION_HEIGHT=800;

}
