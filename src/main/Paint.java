package main;

public class Paint{
	private int r1=-1;
	private int c1=-1;
	private int r2=-1;
	private int c2=-1;
	private String type=null;
	private int s;
	
	public Paint(int r1, int  r2, int  c1, int c2){
		this.r1 = r1;
		this.r2 = r2;
		this.c1 = c1;
		this.c2 = c2;
		this.type = "LINE";	
	}
	
	public Paint(int r, int c, int s){
		this.r1 = r;
		this.c1 = c;
		this.s = s;
		this.type = "SQUARE";
	}
	
	public boolean isPoint(){
		if(type.compareTo("LINE")==0){
			if(r1 == r2 && c1 == c2){
				return true;
			}
		}else if(type.compareTo("SQUARE") == 0){
			if(s == 0)return true;
		}
		return false;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String toString(){
		if(type == null){
			return "";
		}
		if(type.compareTo("LINE") == 0){
			return "PAINT_LINE " + r1 + " " + c1 + " " + r2 + " " + c2;
		}
		if(type.compareTo("SQUARE")==0){
			return "PAINT_SQUARE " + r1 + " " + c1 + " " + s;
		}
		return "";
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
