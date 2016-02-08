package main2;

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

//	    aggiungere il lavoro di ottimizzazzione
//	    Ricerca se il valore delle righe � uguale allora
//	    	se il valore delle colonne � consecutivo
//	    		se il numero di righe uguali � uguale al numero di comandi trovati 
//	    		che � uguale ai valori consecutivi � un quadrato
	    
	    optimize(comandiRighe);
	    
	    if(comandiRighe.size()<= comandiColonne.size()){
		    System.out.println(comandiRighe.size());
		    for (Iterator<Paint> it = comandiRighe.iterator(); it.hasNext();) {
				System.out.println(it.next().toString());			
			}    
	    }else{
		    System.out.println(comandiColonne.size());
		    for (Iterator<Paint> it = comandiColonne.iterator(); it.hasNext();) {
				System.out.println(it.next().toString());			
			}    
	    }
        in.close(); 
	}


	//find in command a potenzial rectangle to optimize in other commands
	private static void optimize(ArrayList<Paint> lista) {
		ArrayList<Paint> toOptimize = new ArrayList<Paint>();
		ArrayList<Paint> nuova = new ArrayList<Paint>();
		Paint temp = null;
		
		for (Paint i : lista) {
			if(!i.isRemovable()){
				for (Paint j : lista) {
					if(i.getC1() == j.getC1() && i.getC2() == j.getC2()){
						if(i.getR1() == j.getR2()){
							temp = i;
							
						}else if(temp.getR1()+1 == j.getR1()){
							//insert first command to optimize						
							if(!toOptimize.contains(i) && temp == i){
								toOptimize.add(i);
								System.out.println(i.toString());
							}
							
							temp = j;
							
							if(!toOptimize.contains(temp)){
								toOptimize.add(temp);
								System.out.println(temp.toString());
							}
						}
						
					}
				}

				System.out.println("-------------------");
				if(toOptimize.size() > 0){
					int len = toOptimize.get(0).getC2() - toOptimize.get(0).getC1();
					int s = 0;
					if(len % 2 == 0 && len == toOptimize.size()-1){
						s = len /2;
						nuova.add(new Paint(toOptimize.get(0).getR1() + s, toOptimize.get(0).getC1() + s, s));
					}
					
					if(len < toOptimize.size()-1){
						for(int k = 0; k < len+1; k++){
							int c = toOptimize.get(k).getC1();
							int r1 = toOptimize.get(0).getR1();
							int r2 = toOptimize.get(toOptimize.size()-1).getR2();
							nuova.add(new Paint(r1, r2, c, c));		
						}
					}
					
					for(Paint k : toOptimize){
						lista.get(lista.indexOf(k)).setRemovable();
					}
					
					toOptimize.clear();
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
		//se � paintable per il quadrato allora aggiungi un quadrato, senn� aggiungi N linee verticale stando attenti che la cosa convenga
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



	//d sistemare � tutto sbagliato ragionarci sopra sono invertiti i valori di colonne e righe
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
}

