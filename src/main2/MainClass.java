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
	    	System.out.print("processing row (" + i + ")<");
	    	subHeight = height;
	    	
	    	for(int j= 1; j< length -1; j++){
	    		subLenght = length;
	    		
	    		while(subLenght - (j - 1) > mask && subHeight - (i-1)> mask){
	    			
	    		
	    			somma = sum(matrix, i-1, j-1, subHeight, subLenght );
	    			cSquare.addAll(squareDetection(5, 10, 12, 20));
	    			if(somma == (subHeight-i) * (subLenght-j)){
		    			System.out.print(somma + " : ");
	    				cSquare.addAll(squareDetection(i-1, j-1, subHeight, subLenght));
	    				blank(matrix, i-1, j-1, subHeight, subLenght);
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
	    
	    
	    PrintWriter out = null;  
	    try {
			out = new PrintWriter(new File("/Users/Gianni/Desktop/output.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

	    out.println(cSquare.size());
	    for (Iterator<Paint> it = cSquare.iterator(); it.hasNext();) {
			out.println(it.next().toString());			
		}    
	    
	    System.out.println("done");
	    
//	    if(comandiRighe.size()< comandiColonne.size()){
//		    out.println(comandiRighe.size());
//		    for (Iterator<Paint> it = comandiRighe.iterator(); it.hasNext();) {
//				out.println(it.next().toString());			
//			}    
//	    }else{
//		    out.println(comandiColonne.size());
//		    for (Iterator<Paint> it = comandiColonne.iterator(); it.hasNext();) {
//				out.println(it.next().toString());			
//			}    
//	    }
	    
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


	private static void vOptimize(ArrayList<Paint> lista) {
		ArrayList<Paint> toOptimize = new ArrayList<Paint>();
		ArrayList<Paint> nuova = new ArrayList<Paint>();
		Paint temp = null;
		
		for (Paint i : lista) {
			if(!i.isRemovable()){
				for (Paint j : lista) {
					if(i.getR1() == j.getR1() && i.getR2() == j.getR2()){
						temp = i;	
						if(temp.getC1()+1 == j.getC1()){
							//insert first command to optimize						
//							if(!toOptimize.contains(i) && temp == i){
//								toOptimize.add(i);
//							}
							
							temp = j;
							
							if(!toOptimize.contains(temp)){
								toOptimize.add(temp);
							}
						}
						
					}
				}

				if(toOptimize.size() > 1){
					int size = toOptimize.size()-1;
					//se non funziona con c1 e c2 provare ad inserire la dimensione
					nuova.addAll(squareDetection(toOptimize.get(0).getR1(), toOptimize.get(size).getC1(), toOptimize.get(0).getR2(), toOptimize.get(size).getC2()));
					
					if(nuova.size()>0){
						for (Paint k : toOptimize) {
							lista.get(toOptimize.indexOf(k)).setRemovable();
						}
					}
				}
				toOptimize.clear();
			}
		
		}
		
		for (int i = 0; i < nuova.size(); i++) {
			for (int j = 0; j < nuova.size(); j++) {
				if(nuova.get(i).equals(nuova.get(j)) && i != j){
					nuova.remove(j);
				}
			}
		}
		
		for (Paint k : lista) {
			if(k.isRemovable()){
				toOptimize.add(k);
			}
		}
		lista.removeAll(toOptimize);
		lista.addAll(nuova);		
	}


	//find in command a potenzial rectangle to optimize in other commands
	private static void hOptimize(ArrayList<Paint> lista) {
		ArrayList<Paint> toOptimize = new ArrayList<Paint>();
		ArrayList<Paint> nuova = new ArrayList<Paint>();
		Paint temp = null;
		
		for (Paint i : lista) {
			if(!i.isRemovable()){
				for (Paint j : lista) {
					if(i.getC1() == j.getC1() && i.getC2() == j.getC2()){
						temp = i;
						if(temp.getR1()+1 == j.getR1()){
							//insert first command to optimize						
//							if(!toOptimize.contains(i) && temp == i){
//								toOptimize.add(i);
//							}
							
							temp = j;
							
							if(!toOptimize.contains(temp)){
								toOptimize.add(temp);
							}
						}
						
					}
				}


<<<<<<< Updated upstream
				if(toOptimize.size() > 0){
					int len = toOptimize.get(0).getC2() - toOptimize.get(0).getC1();
//					boolean remove = false;
					
					//squareDetection(toOptimize, r1, r2, )
					//eliminazione di tutte le operazioni incriminate siamo sicuri che square sistema tutto
					//svuoto toOptimize
					
					while (len <= toOptimize.size()-1) {
						if (len >= (toOptimize.size() - 1) / len) {
							int s = 0;
							if (len % 2 != 0) {
								nuova.add(new Paint(toOptimize.get(0).getR1(),
										len, toOptimize.get(len).getR1(), len));
								len--;
							}

							s = len / 2;

							//forma il quadrato ed elimina i comandi rimanenti
							nuova.add(new Paint(toOptimize.get(0).getR1() + s,
									toOptimize.get(0).getC1() + s, s));

							for (int k = 0; k < len; k++) {
								Paint toRem = toOptimize.get(k);
								lista.get(lista.indexOf(toRem)).setRemovable();
							}

						} else {
							for (int k = 0; k < len + 1; k++) {
								int c = toOptimize.get(k).getC1() + k;
								int r1 = toOptimize.get(0).getR1();
								int r2 = toOptimize.get(
										toOptimize.size() - 1).getR2();
								nuova.add(new Paint(r1, r2, c, c));
							}

							for (Paint k : toOptimize) {
								lista.get(toOptimize.indexOf(k)).setRemovable();
							}
						}
						len*=2;
=======
				if(toOptimize.size() > 1){
					int size = toOptimize.size()-1;
					//se non funziona con c1 e c2 provare ad inserire la dimensione
					nuova.addAll(squareDetection(toOptimize.get(0).getR1(), toOptimize.get(0).getC1(), toOptimize.get(size).getR2(), toOptimize.get(size).getC2()));

					if(nuova.size()>0){
						for (Paint k : toOptimize) {
							lista.get(toOptimize.indexOf(k)).setRemovable();
						}
>>>>>>> Stashed changes
					}
				}
				toOptimize.clear();
			}
		
		}
		
		for (int i = 0; i < nuova.size(); i++) {
			for (int j = i; j < nuova.size(); j++) {
				if(nuova.get(i).equals(nuova.get(j)) && i != j){
					nuova.remove(j);
				}
			}
		}
		
		for (Paint k : lista) {
			if(k.isRemovable()){
				toOptimize.add(k);
			}
		}
		
		lista.removeAll(toOptimize);
		lista.addAll(nuova);
		//se è paintable per il quadrato allora aggiungi un quadrato, sennò aggiungi N linee verticale stando attenti che la cosa convenga
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
<<<<<<< Updated upstream
=======
	
	private static ArrayList<Paint> squareDetection(int r1,int c1,int r4,int c4){
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
				
				
					for (int j = 0; j < nuova.size(); j++) {
						for (int i = 0; i < nuova.size(); i++) {
							if(nuova.contains(nuova.get(i)) && i !=nuova.indexOf(nuova.get(j))){
								nuova.remove(i);
							}
						}
					}
					
				return nuova;
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
//		}else{
//			//caso base
//			if (base == altezza && altezza % 2 == 0) {
//				int s = altezza / 2;
//				nuova.add(new Paint(r1 + s, c1 + s, s));
//				return nuova;
//			} else if (base <= altezza) {
//				nuova.add(new Paint(r1, r4, c1, c1));
//				return nuova;
//			} else {
//				//altezza < base
//				nuova.add(new Paint(r1, r1, c1, c4));
//				return nuova;
//			}
		}
		return nuova;
	}

>>>>>>> Stashed changes
}

