package main;

public class Paint{
	private int r1=-1;
	private int c1=-1;
	private int r2=-1;
	private int c2=-1;
	private String type=null;
	private int s;
	
	public Paint(){
		
	}
	
//	linea verticale
	public Paint paintLine(int r1, int  r2, int  c1, int c2){ 
		this.r1 = r1;
		this.r2 = r2;
		this.c1 = c1;
		this.c2 = c2;
		this.type = "LINE";
		return this;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String lineToString(){
		if(type == null){
			return "";
		}
		return "PAINT_LINE " + r1 + " " + c1 + " " + r2 + " " + c2;
	}
	
	public Paint PaintSquare(int r, int c, int s, int length){
		this.r1 = r;
		this.c1 = c;
		this.s = s;
		return this;
	}
	
	public String squareToString(){
		return "PAINT_SQUARE " + r1 + " " + c1 + " " + s;
	}
	
	public static boolean isPaintable(int r, int c, int s, int length, int height){
		if( r >= 0 && c >= 0 && c < length && r < height){
			if(c + s < length && c - s >= 0 ){
				if(r + s < height && r - s >= 0){
					return true;
				}
			}
		}
		return false;
	}
}
