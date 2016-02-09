package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Test {
	private static int[][] matrix = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = null;
		if(args.length != 3){
			System.err.println("Errore!!! Inserisci:");
			System.err.println("java -jar [altezza][lunghezza][perscorso file output]");
			return;
		}		
		
		int height = Integer.parseInt(args[0]);//in.nextInt();
		int length = Integer.parseInt(args[1]); //in.nextInt();
		
		try {
			in = new Scanner(new BufferedReader(new FileReader(args[2])));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(args.length > 0)
				System.err.println("Error: file not found!");
			else
				System.err.println("Error: insert path of file!");
			return;
		}


		int nOperations = in.nextInt();
        in.nextLine();
        
        matrix = new int[height][length];
        
        for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				matrix[i][j] = 0;
			}
		}
        
        String op = null;
        
	    for (int i = 0; i < nOperations; i++) {
	    	 op = in.next();
	    	 if(op.compareTo("PAINT_LINE") == 0){
	    		 int r1 = Integer.parseInt(in.next());
	    		 int c1 = Integer.parseInt(in.next());
	    		 int r2 = Integer.parseInt(in.next());
	    		 int c2 = Integer.parseInt(in.next());
	    		 PaintLine(r1, c1, r2, c2);
	    	 }else if(op.compareTo("PAINT_SQUARE")== 0){
	    		 int r = Integer.parseInt(in.next());
	    		 int c = Integer.parseInt(in.next());
	    		 int s = Integer.parseInt(in.next());
	    		 PaintSquare(r, c, s);
	    	 }
	    	 
	    	 in.nextLine();
	    }

	    in.close();
	    System.out.println(height + " " + length);
	    for(int i = 0; i < height; i++){
	    	for(int j = 0; j< length; j++){
	    		if(matrix[i][j] == 1)
	    			System.out.print("#");
	    		else
	    			System.out.print(".");
	    	}
	    	System.out.println();
	    }

	}

	private static void PaintSquare(int r, int c, int s) {
		int i = r - s;
		while(i < r + s + 1){
			int j = c - s;
			while(j < c + s + 1){
				matrix[i][j]=1;
				j++;
			}
			i++;
		}
	}

	private static void PaintLine(int r1, int c1, int r2, int c2) {
		int i;
		if(r1 == r2){
			i = c1;
			while(i <= c2){
				matrix[r1][i]=1;
				i++;
			}
		} else if(c1 == c2){
			i = r1;
			while (i <= r2){
				matrix[i][c1]=1;
				i++;
			}
		}
	}
	}

