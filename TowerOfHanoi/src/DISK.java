import acm.graphics.G3DRect;
import acm.graphics.GRoundRect;

public class DISK extends GRoundRect {
	
	public DISK(double x, double y, double w) {
		super(x,y,w,DISK_HEIGHT);
		disk_width=w;
		peg=null;
		below_disk=null;
		this.setFilled(true);
	}
	
	public DISK( double w) {
		super(w,DISK_HEIGHT);
		peg=null;
		below_disk=null;
		this.setFilled(true);
	}
	
	public double getDWidth() {
		return disk_width;
	}
	
	public DISK getBelowDisk() {
		return below_disk;
	}
	
	public void setBelowDisk(DISK d) {
		below_disk=d;
	}
	
	public PEG getPeg() {
		return peg;
	}
	public void setPeg(PEG p) {
		peg=p;
	}
	
	public void movebackToPeg() {
		if(this.getX()<peg.getX()) {
			while((this.getX()+this.getWidth()/2)<=peg.getX()) {
			   this.move(10, 0);
			   pause(1);
			}
		}
		else {
			while((this.getX()+this.getWidth()/2-10)>peg.getX()) {
				   this.move(-10, 0);
				   pause(1);
				}
		}
		peg.stackOnPeg(this);
	}
	
	private PEG peg;
	private DISK below_disk;
	private double  disk_width;
	private static final int  DISK_HEIGHT=20;

}
