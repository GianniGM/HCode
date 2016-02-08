package main2;

public class Paint{
	private int r1=-1;
	private int c1=-1;
	private int r2=-1;
	private int c2=-1;
	private int s;
	private boolean removable = false;
	
	private String type=null;
	
	public Paint(int r1, int  r2, int  c1, int c2){
		this.r1 = r1;
		this.r2 = r2;
		this.c1 = c1;
		this.c2 = c2;
		if(r2 > r1 && c2 > c1)this.type = "RECTANGLE";
		else this.type = "LINE";
	}
	
	public Paint(int r, int c, int s){
		this.r1 = r;
		this.c1 = c;
		this.s = s;
		this.type = "SQUARE";
	}
	
	public void setRemovable() {
		this.removable = true;
	}
	
	public boolean isRemovable(){
		return this.removable;
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
	
	public int getC1(){
		if(type.compareTo("LINE") == 0){
			return c1;
		}else if(type.compareTo("SQUARE") == 0){
			return c1;
		}
		return -1;
	}
	
	public int getC2(){
		if(type.compareTo("LINE") == 0){
			return c2;
		} else {
			return -1;
		}
	}
	
	public int getR1(){
		if(type.compareTo("LINE") == 0){
			return r1;
		} else {
			return -1;
		}	
	}
	
	public int getR2(){
		if(type.compareTo("LINE") == 0){
			return r2;
		} else {
			return -1;
		}
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
	
	public static boolean isPaintable(int r1, int  r2, int  c1, int c2, int height, int length){
		
		if(c1 == c2){
			if( (r1 <= r2 && r1 >= 0 && r2 < height) && (c1 >= 0 && c1 < length)){
				return true;
			}
		} else if (r1 == r2){
			if((c1 <= c2 && c1 >= 0 && c2 < length) && (r1 >= 0 && r1 < height)){
				return true;
			}
		}		
		return false;
	}
}
