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
		ArrayList<Commands> listaComandi = new ArrayList<Commands>();

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
        
      	int operations = 0;
	    for (int i = 0; i < height; i++) {
	        String row = in.nextLine();
	        operations = operations + toPaintHorizontalLine(row, i, length, height, listaComandi);
	    }
	    
	    System.out.println(operations);
	    for (Iterator<Commands> it = listaComandi.iterator(); it.hasNext();) {
			System.out.println(it.next().ToString());			
		}        
     
        in.close(); 
	}

	//d sistemare è tutto sbagliato ragionarci sopra sono invertiti i valori di colonne e righe
	private static int toPaintHorizontalLine(String row,  int indexRow, int h, int l, ArrayList<Commands> lista) {
		int c1 = -1, c2 = -1;
		int operations = 0;
		
			c1 = row.indexOf('#', 0);
			if(c1 != -1){
				int i = c1;
				while(i < row.length()){
					if(row.charAt(i) == '#'){
						c2=i;
					}else{
						if (PaintLine.isPaintable(indexRow, indexRow, c1, c2, h, l)){
							lista.add(new PaintLine(indexRow, indexRow, c1, c2));
							operations++;
							c1=row.indexOf('#', i);
							c2=-1;
						}else{
							break;
						}
					}
					i++;
				}
			}
			return operations;
		}
	}

