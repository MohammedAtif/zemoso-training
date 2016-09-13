import java.io.*;
import java.util.*;

public class readFile{
	
	private Scanner x;
	public void openFile(){
	try{
	x=new Scanner(new File("p096_sudoku.txt"));
	}
	catch(Exception e){
	System.out.println("Could not open");
	}
	}


	public int[][] readfile(int nextval){
		while(nextval>0)
		{
			x.next();
			nextval--;
		}
		int jj=0;
		int limit=11;
		int[][] array=new int[10][10];
		String result;
		boolean t=false;
	while(limit>0){
		
	String a=x.next();

	//Skipping 'Grid number' from the file
	if(Character.isLetter(a.charAt(0))){
		t=true;
		limit--;
		continue;
	}
	else if(t==true){
		t=false;
		limit --;
		continue;

	}
	else{
	int[] resultArray = new int[32];
	Integer ii;
	//Spliting the read line and adding it to an integer array
    String[] temp = a.split("");
    ii = 0;
    for(String s: temp) {
      try {
        resultArray[ii++] = Integer.parseInt(s);
      } catch (NumberFormatException e) {

      }
    }

    int n = ii;

    // converting the sudoku read to a 2d array
    for (ii = 0; ii < n; ii++) {
    
      array[jj][ii]=resultArray[ii];

    }
    jj++;
	
	}
	limit--;
}

//returning each sudoku
return array;
	}

	public void closeFile(){
	x.close();
	}

}
