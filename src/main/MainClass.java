package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Scanner in = new Scanner(System.in);
		Scanner in = null;
		ArrayList<Paint> comandiRighe = new ArrayList<Paint>();
		ArrayList<Paint> comandiColonne = new ArrayList<Paint>();

		int[][] matrix = null;
		
		try {
			in = new Scanner(new BufferedReader(new FileReader(args[0])));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(args.length > 0)
				System.err.println("Error: file not found!");
			else
				System.err.println("Error: insert path of file!");
			return;
		}
		
		int height = in.nextInt();
		int length = in.nextInt();
        in.nextLine();
        
        matrix = new int[height][length];
//        debug
//        System.out.println(height + " " + length);
        
	    for (int i = 0; i < height; i++) {
	        matrix[i] =  convert(in.nextLine());
	    	toPaintHorizontalLine(matrix[i], i, height, length, comandiRighe);
	    }

	    for(int col = 0; col < length; col++)	
	    	toPaintVerticalLine(matrix, col, height, length , comandiColonne);
    
	    
	    System.out.println(comandiRighe.size());
	    for (Iterator<Paint> it = comandiRighe.iterator(); it.hasNext();) {
			System.out.println(it.next().toString());			
		}    
	    
	    System.out.println("------------------------------------------------");
	    
	    System.out.println(comandiColonne.size());
	    for (Iterator<Paint> it = comandiColonne.iterator(); it.hasNext();) {
			System.out.println(it.next().toString());			
		}    
     
        in.close(); 
	}



	private static void toPaintVerticalLine(int[][] is, int col, int h, int l, ArrayList<Paint> lista) {
		int r1=0, r2=-1;
		
		while (r1 < h) {
			if(is[r1][col] == 1){
				int j = r1;
				while(j < h){
					if(is[j][col] == 1){
						r2=j;
						if(r2==h-1){
							lista.add(new Paint(r1, r2, col, col));
						}
					}else{
						if(Paint.isPaintable(r1, r2, col, col, h, l))
							lista.add(new Paint(r1, r2, col, col));
						j=h;
						r1=r2;
					}	
					j++;
				}
			}
			r1++;
		}
	}



	//d sistemare è tutto sbagliato ragionarci sopra sono invertiti i valori di colonne e righe
	private static void toPaintHorizontalLine(int[] row,  int indexRow, int h, int l, ArrayList<Paint> lista) {
		int c1 = 0, c2 = -1;

		while (c1 < row.length) {
			if(row[c1] == 1){
				int j = c1;
				while(j < row.length){
					if(row[j] == 1){
						c2=j;
						if(c2==row.length-1){
							lista.add(new Paint(indexRow, indexRow, c1, c2));
						}
					}else{
						if(Paint.isPaintable(indexRow, indexRow, c1, c2, h, l))
							lista.add(new Paint(indexRow, indexRow, c1, c2));
						j=row.length;
						c1=c2;
					}	
					j++;
				}
			}
			c1++;
		}
	}
		

	
	private static int[] convert(String row) {
		if (row.length()<= 0 ) return null;		
		
		int[] riga = new int[row.length()];
		
		for(int i = 0; i < riga.length; i++){
			if(row.charAt(i)=='#')
				riga[i]=1;
			else
				riga[i]=0;
		}
		
		return riga;
	}
}

