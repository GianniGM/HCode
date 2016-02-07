package main;

public class PaintLine  {
	private int r1=-1;
	private int c1=-1;
	private int r2=-1;
	private int c2=-1;
	
//	linea verticale
	public PaintLine(int r1, int  r2, int  c1, int c2){ 
		this.r1 = r1;
		this.r2 = r2;
		this.c1 = c1;
		this.c2 = c2;
	}
	
	public static boolean isPaintable(int r1, int  r2, int  c1, int c2, int height, int length){
		
		if(c1 == c2){
			if( (r1 <= r2 && r1 >= 0 && r2 < height && c1 >= 0 && c1 < length)){
				return true;
			}
		} else if (r1 == r2){
			if((c1 < c2 && c1 >= 0 && c2 < length) && (r1 >= 0 && r1 < height)){
				return true;
			}
		}		
		return false;
	}
	
	public String ToString(){
		return "PAINT_LINE " + r1 + " " + c1 + " " + r2 + " " + c2;
	}
}
