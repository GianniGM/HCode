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
//	    Ricerca se il valore delle righe è uguale allora
//	    	se il valore delle colonne è consecutivo
//	    		se il numero di righe uguali è uguale al numero di comandi trovati 
//	    		che è uguale ai valori consecutivi è un quadrato
	    
	    hOptimize(comandiRighe);
	    vOptimize(comandiColonne);
	    
	    PrintWriter out = null;
	    try {
			out = new PrintWriter(new File("/Users/Gianni/Desktop/output.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    if(comandiRighe.size()<= comandiColonne.size()){
		    out.println(comandiRighe.size());
		    for (Iterator<Paint> it = comandiRighe.iterator(); it.hasNext();) {
				out.println(it.next().toString());			
			}    
	    }else{
		    out.println(comandiColonne.size());
		    for (Iterator<Paint> it = comandiColonne.iterator(); it.hasNext();) {
				out.println(it.next().toString());			
			}    
	    }
        in.close();
        out.close();
	}


	private static void vOptimize(ArrayList<Paint> lista) {
		ArrayList<Paint> toOptimize = new ArrayList<Paint>();
		ArrayList<Paint> nuova = new ArrayList<Paint>();
		Paint temp = null;
		
		for (Paint i : lista) {
			if(!i.isRemovable()){
				for (Paint j : lista) {
					if(i.getR1() == j.getR1() && i.getR2() == j.getR2()){
						if(i.getC1() == j.getC2()){
							temp = i;	
						}else if(temp.getC1()+1 == j.getC1()){
							//insert first command to optimize						
							if(!toOptimize.contains(i) && temp == i){
								toOptimize.add(i);
							}
							
							temp = j;
							
							if(!toOptimize.contains(temp)){
								toOptimize.add(temp);
							}
						}
						
					}
				}

				if(toOptimize.size() > 0){
					int len = toOptimize.get(0).getR2() - toOptimize.get(0).getR1();
					
					if (len < toOptimize.size()) {
						if (len >= (toOptimize.size() - 1) / len) {
							int s = 0;
							if (len % 2 != 0 && len < toOptimize.size()) {
								nuova.add(new Paint(len, len, toOptimize.get(0)
										.getC1(), toOptimize.get(0).getC2()));
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
							for (int k = 0; k < len; k++) {
								int r = toOptimize.get(k).getR1() + k;
								int c1 = toOptimize.get(0).getC1();
								int c2 = toOptimize.get(
										toOptimize.size() - 1).getC2();
								nuova.add(new Paint(r, r, c1, c2));
							}

							for (Paint k : toOptimize) {
								lista.get(toOptimize.indexOf(k)).setRemovable();
							}
						}
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
						if(i.getR1() == j.getR2()){
							temp = i;
							
						}else if(temp.getR1()+1 == j.getR1()){
							//insert first command to optimize						
							if(!toOptimize.contains(i) && temp == i){
								toOptimize.add(i);
							}
							
							temp = j;
							
							if(!toOptimize.contains(temp)){
								toOptimize.add(temp);
							}
						}
						
					}
				}


				if(toOptimize.size() > 1){
					int size = toOptimize.size()-1;
					nuova = squareDetection(toOptimize, toOptimize.get(0).getR1(), toOptimize.get(0).getC1(), toOptimize.get(size).getR2(), toOptimize.get(size).getC2());
					
					for (Paint k : toOptimize) {
						lista.get(toOptimize.indexOf(k)).setRemovable();
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
	
	private static ArrayList<Paint> squareDetection(ArrayList<Paint> vecchia,int r1,int c1,int r4,int c4){
		int base = c4 - c1;
		int altezza = r4 - r1;
		ArrayList<Paint> nuova = null;
		
		// controllo utile per capire se conviene disegnare un quadrato o delle paint_line
	
		//ciclo!!! smusso i lati finché non ho base == altezza
		if(base == altezza){
			//quadrato
			//ok è perfetto
		}else if (base < altezza){
			if(base < altezza/base){
				//disegno la prima riga (r1,r1, c1, c4)
				//incremento r1
				//costruisco quadrato ricorsivamente richiamo questa funzione con altezza aggiornata
			}else{
				//costruisco #base righe verticali di lunghezza #altezza
			}
		}else{
			//altezza < base
			if(altezza < base/altezza){
				//disegno la prima riga (r1, r4, c1, c1)
				//incremento c1
				//chiamo questa funziona con le coordinate aggiorante
			}else{
				//costruisco #altezza righe orizzontali di lunghezza#altezza
			}
		}
		
		return nuova;
	}

}

