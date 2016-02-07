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
		ArrayList<PaintLine> listaComandi = new ArrayList<PaintLine>();

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
//        debug
//        System.out.println(height + " " + length);
        
	    for (int i = 0; i < height; i++) {
	        String row = in.nextLine();
	        toPaintHorizontalLine(row, i, length, height, listaComandi);
	    }
	    
	    System.out.println(listaComandi.size());
	    for (Iterator<PaintLine> it = listaComandi.iterator(); it.hasNext();) {
			System.out.println(it.next().ToString());			
		}        
     
        in.close(); 
	}

	//d sistemare � tutto sbagliato ragionarci sopra sono invertiti i valori di colonne e righe
	private static void toPaintHorizontalLine(String row,  int indexRow, int h, int l, ArrayList<PaintLine> lista) {
		int c1 = -1, c2 = -1;
		
		if((c1 = row.indexOf('#', 0)) != -1){
			while(c1 >=0 && c1<row.length() && c2<row.length()){
				//esiste un punto nella riga
				if(row.indexOf('.',c1) >= 0){
					c2=row.indexOf('.',c1)-1;
					lista.add(new PaintLine(indexRow, indexRow, c1, c2));
					c1 = row.indexOf('#', c2+1);
				}else{
				//l'immagine termina con un cancelletto
					lista.add(new PaintLine(indexRow, indexRow, c1, row.length()-1));
					c1 = -1;
				}
			}
		}
	}
}

