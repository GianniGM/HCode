package main2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MainClass {
	public static int[][] matrix = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Scanner in = new Scanner(System.in);
		Scanner in = null;
		ArrayList<Paint> cSquare = new ArrayList<Paint>();

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
	    }
	    
	    in.close();
	    
	    int subLenght;
	    int subHeight;
	    int somma = 0;
	    int mask = 3; 
	    
	    for(int i = 1; i < height -1; i++){
	    	//TODO ciclo da sistemare
	    	System.out.print("processing row (" + i + ")<");
	    	subHeight = height;
	    	
	    	for(int j= 1; j< length -1; j++){
	    		subLenght = length;
	    		
	    		while(subLenght - j > mask && subHeight - i> mask){
	    			
	    			//indici
	    			somma = sum(matrix, i-1, j-1, subHeight, subLenght);
	    			System.out.print(":"+ (somma - ((subHeight-i+1) * (subLenght-j+1))));
	    			//dimensioni
	    			if(somma == ((subHeight-i+1) * (subLenght-j+1))){
		    			System.out.print(somma + ((subHeight-i+1) * (subLenght-j+1)) + " -> ok: ");
	    				cSquare.addAll(squareDetection(i-1, j-1, subHeight-1, subLenght-1));
	    				blank(matrix,i-1, j-1, subHeight, subLenght);
//	    			}else if( somma-1 == subHeight * subLenght){
//	    				cSquare.addAll(squareDetection(i-1, j-1, i-1+subHeight, j-1+subLenght));
//	    				blank(matrix, i-1, j-1, subHeight, subLenght);
//	    				new erase point
			    		System.out.print("#");
	    			}
	    			
	    		    if(subLenght > subHeight){
	    		    	subLenght --;
				    }else if(subHeight == subLenght){
				    	subHeight --;
				    	subLenght --;
				    }else{
				    	subHeight--;
				    }
	    		    
   	    		}
	    		
	    	}
		    System.out.println(">");
	    }
	    
	    
	    
	    ArrayList<Paint> comandiColonne = new ArrayList<Paint>();
		ArrayList<Paint> comandiRighe = new ArrayList<Paint>();
		
//		for(int i = 0; i < height; i++){
//			toPaintHorizontalLine(matrix[i], i, height, length, comandiRighe);
//		}
//		
//		for(int i = 0; i < length; i++){
//			toPaintVerticalLine(matrix, i, height, length, comandiColonne);
//		}
		
		if(comandiRighe.size()< comandiColonne.size()){
			cSquare.addAll(comandiRighe);
	    }else{
	    	cSquare.addAll(comandiColonne);
	    }
	    
	    PrintWriter out = null;  
	    try {
			out = new PrintWriter(new File("output.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    

	    out.println(cSquare.size());
	    for (Iterator<Paint> it = cSquare.iterator(); it.hasNext();) {
			out.println(it.next().toString());			
		}    
	    
	    System.out.println("done");
	    
	    
        out.close();
	}


	private static void blank(int[][] matrix, int i, int j, int subHeight,int subLenght) {
		
		for(int k = i; k < subHeight; k++){
			for(int z = j; z < subLenght; z++){
				matrix[k][z]=0;
			}
		}
		
	}


	private static int sum(int[][] matrix, int i, int j, int subHeight, int subLenght) {
		int sum = 0;

		for(int k = i; k < subHeight; k++){
			for(int z = j; z < subLenght; z++){
				sum += matrix[k][z];
			}
		}
		return sum;
	}


	private static void toPaintVerticalLine(int[][] matrix, int col, int h, int l, ArrayList<Paint> lista) {
		int r1=0, r2=-1;

		while (r1 < h) {
			if(matrix[r1][col] == 1){
				int j = r1;
				r2=-1;
				while(j < h && matrix[j][col] == 1){
					r2=j;
					j++;
				}
				
				if(r2 != -1 && Paint.isPaintable(r1, r2, col, col, h, l)){
					lista.add(new Paint(r1, r2, col, col));
					r1=r2;	
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
				c2=-1;
				while(j < row.length && row[j] == 1){
					c2=j;
					j++;
				}
				
				if(c2 != -1 && Paint.isPaintable(indexRow, indexRow, c1, c2, h, l)){
					lista.add(new Paint(indexRow, indexRow, c1, c2));
					c1=c2;
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

	
	private static ArrayList<Paint> squareDetection(int r1,int c1,int r4,int c4){
		//TODO sistemare la ricorsione: non stampa due quadrati stampa linee
		int base = c4 - c1;
		int altezza = r4 - r1;
		ArrayList<Paint> nuova = new ArrayList<Paint>();
		
		// controllo utile per capire se conviene disegnare un quadrato o delle paint_line
		//IL CASO BASE E' FINCHE LTEZZA E BASE SONO MAGGIORI UGUALI A ZERO
		//ciclo!!! smusso i lati finché non ho base == altezza
		System.out.print("-");
		
		if (base > 0 && altezza > 0) {
			if (base == altezza) {
				int s;
				if(altezza % 2 != 0){
					nuova.add(new Paint(r1, r4, c1, c1));
					nuova.add(new Paint(r1, r1, c1, c4));
					c1++;
					r1++;
					altezza--;
					base--;
				}
				s = altezza / 2;
				nuova.add(new Paint(r1 + s, c1 + s, s));
	
			} else if (base < altezza) {
				if (base < altezza / base) {
					//costruisco #base righe verticali di lunghezza #altezza
					nuova.add(new Paint(r1, r4, c1, c1));
					nuova.addAll(squareDetection(r1, c1 + 1, r4, c4));

				} else {
					//disegno la prima riga orizzontale 
					//incremento r1
					//costruisco quadrato ricorsivamente richiamo questa funzione con altezza aggiornata
					nuova.add(new Paint(r1, r1, c1, c4));
					nuova.addAll(squareDetection(r1 + 1, c1, r4, c4));
				}
			} else {
				//altezza < base
				if (altezza < base / altezza) {
					//disegno la prima riga (r1, r4, c1, c1)
					//incremento c1
					//chiamo questa funziona con le coordinate aggiorante
					nuova.add(new Paint(r1, r1, c1, c4));
					nuova.addAll(squareDetection(r1 + 1, c1, r4, c4));
				} else {
					//costruisco #altezza righe orizzontali di lunghezza#altezza
					nuova.add(new Paint(r1, r4, c1, c1));
					nuova.addAll(squareDetection(r1, c1 + 1, r4, c4));
				}
			}
		}else{
			//caso base
			if (base == altezza && altezza % 2 == 0) {
				int s = altezza / 2;
				nuova.add(new Paint(r1 + s, c1 + s, s));
			} else if (base <= altezza) {
				nuova.add(new Paint(r1, r4, c1, c1));
			} else {
				//altezza < base
				nuova.add(new Paint(r1, r1, c1, c4));
			}
		}
		return nuova;
	}

}

