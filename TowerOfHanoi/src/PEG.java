import acm.graphics.G3DRect;

public class PEG extends G3DRect{
	
	public PEG(double x, double y, double w, double h) {
		super(x,y,w,h);
		top=null;
		this.setFilled(true);
		
	}
	
	public PEG(double w, double h) {
		super(w,h);
		top=null;
		this.setFilled(true);
		
	}
	
	public void setTop(DISK disk) {
		top=disk;
	}
	public DISK getTop() {
		return top;
	}
	
	public double getBaseY() {
		return (this.getY()+this.getHeight());
	}
	
	public void setNumber(int i) {
		number=i;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setCx(double cx) {
		c_x=cx;
	}
	
	public double getCx() {
		return c_x;
	}
	
	public void stackOnPeg(DISK d) {
		if(this!=d.getPeg()) {
			TOH.incrementMoves();
		}
		if(top!=null) {
			while(d.getY()<top.getY()-20) {
				    d.move(0, 10);
				    pause(20);    
				}

			d.setBelowDisk(this.getTop());
			this.setTop(d);
		}
		else {
			while(d.getY()<(TOHC.BASE_Y-20)) {
			    d.move(0, 10);
			    pause(20);
				}


			d.setBelowDisk(null);
			this.setTop(d);
		}
		
		top=d;
	}
	
	public void stackOnPeg2(DISK d) {
		if(this!=d.getPeg()) {
			TOH.incrementMoves();
		}
		if(top!=null) {
			while(d.getY()<top.getY()-20) {
				    d.move(0, 10);
				       
				}

			d.setBelowDisk(this.getTop());
			this.setTop(d);
		}
		else {
			while(d.getY()<(TOHC.BASE_Y-20)) {
			    d.move(0, 10);
				}


			d.setBelowDisk(null);
			this.setTop(d);
		}
		
		top=d;
	}
	
	
	
	
	
	private DISK top;
	private int number;
	private double c_x;

}
