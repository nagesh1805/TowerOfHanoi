import java.awt.Color;

import acm.graphics.G3DRect;
import acm.graphics.GCompound;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import acm.util.RandomGenerator;

public class TOHC extends GCompound{
	
	public TOHC(int n) {
		
		activeDisk=null;
		peg1= new PEG( P1_X_OFFSET, P1_Y_OFFSET,PEG_WIDTH,PEG_HEIGHT);
		peg1.setCx(P1_X_OFFSET);
		peg1.setNumber(1);
		peg2= new PEG( P2_X_OFFSET, P2_Y_OFFSET,PEG_WIDTH,PEG_HEIGHT);
		peg2.setCx(P2_X_OFFSET);
		peg2.setNumber(2);
		peg3= new PEG( P3_X_OFFSET, P3_Y_OFFSET,PEG_WIDTH,PEG_HEIGHT);
		peg3.setCx(P3_X_OFFSET);
		peg3.setNumber(3);
		base = new GRect(BASE_X, BASE_Y,1110,5);
		base.setFilled(true);
		base.setColor(Color.DARK_GRAY);
		peg1.setColor(Color.BLUE);
		peg2.setColor(Color.GREEN);
		peg3.setColor(Color.RED);
		add(base);
		add(peg1);
		add(peg2);
		add(peg3);
		disks=new DISK[n];
		shuffle(diskColrs);
		for (int i=0;i<n;i++) {

			disks[i]=new DISK(P1_X_OFFSET-(DISK_WIDTH-(12-n+i)*DISK_HEIGHT)/2.0+4,P1_Y_OFFSET+PEG_HEIGHT-DISK_HEIGHT*(i+1),DISK_WIDTH-(12-n+i)*DISK_HEIGHT);
			disks[i].setFilled(true);
			disks[i].setColor(diskColrs[i]);
			disks[i].setPeg(peg1);
			if(i==0) {
				disks[i].setBelowDisk(null);
			}
			else {
				disks[i].setBelowDisk(disks[i-1]);
			}
			add(disks[i]);
		}
		peg1.setTop(disks[n-1]);
		
		

		
		
	}
	
	public static  void shuffle(Color[] colrs) {
		for(int i=0;i<colrs.length;i++) {
			int ix=gen.nextInt(12);
			Color tmp=colrs[i];
			colrs[i]=colrs[ix];
			colrs[ix]=tmp;
		}
	}
	
	public PEG getPeg1() {
		return peg1;
	}
	
	public PEG getPeg2() {
		return peg2;
	}
	
	public PEG getPeg3() {
		return peg3;
	}
	
	public DISK getActiveDisk() {
		return activeDisk;
	}
	public void setActiveDisk(DISK d) {
		activeDisk=d;
	}
	
	public boolean isActiveDiskatRest() {
		if(activeDisk.getY()<=200) {
			return false;
		}
		else {
			return true;
		}
	}
	

	
	
	
	
	
	private PEG peg1,peg2,peg3;
	private DISK[] disks;
	private GRect base;
	private DISK activeDisk;
	private static final Color[] diskColrs= {Color.RED,Color.YELLOW,Color.PINK,Color.GREEN,Color.MAGENTA,
			new Color(255,102,9),Color.BLUE,Color.CYAN,new Color(0,102,0),new Color(51,0,0),new Color(128,0,128),new Color(0,139,139)};
	public static final int P1_X_OFFSET=150;
	public static final int P1_Y_OFFSET=200;
	public static final int P2_X_OFFSET=500;
	public static final int P2_Y_OFFSET=200;
	public static final int P3_X_OFFSET=850;
	public static final int P3_Y_OFFSET=200;
	public static final int PEG_HEIGHT=250;
	public static final int PEG_WIDTH=10;
	public static final int DISK_HEIGHT=20;
	public static final int DISK_WIDTH=300;
	public static final int BASE_X=P1_X_OFFSET-200;
	public static final int BASE_Y=P1_Y_OFFSET+PEG_HEIGHT;

	private static RandomGenerator gen=RandomGenerator.getInstance();

}
