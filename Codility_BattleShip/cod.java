import java.io.*;
import java.util.*;
class cod{
	public static void main(String[] args){
		Solution s=new Solution();
		//Input
		System.out.println(s.solution(3,"1A 1B,2C 2C","1B"));
		System.out.println(s.solution(4,"1B 2C,2D 4D","2B 2D 3D 4D 4A"));

	}
}
class Solution {
    public String solution(int N, String S, String T) {
        int sunk=0,hit=0;
        //ships now contains the starting and ending coordinates of each ship in seperate variables
	    String[] ships=S.split(",");
	   	for(String s:ships){
	   		//Seperating starting and ending coordinates of each ship
	   		String[] shipCoordinates=s.split(" ");
	   		int maxAttack=0;
	   		int hit1=0;
	   		//Splitting each coordinate into number and digit and checking the attack and hits
			String[] coordinatexy=shipCoordinates[0].split("(?<=\\d)(?=\\D)");
			String[] coordinateXY=shipCoordinates[1].split("(?<=\\d)(?=\\D)");
			for(int i=Integer.valueOf(coordinatexy[0]);i<=Integer.valueOf(coordinateXY[0]);i++){
				for(int j=Integer.valueOf(coordinatexy[1].charAt(0));j<=Integer.valueOf(coordinateXY[1].charAt(0));j++){
					//maxattack is the no of attacks needed to sink the ship
					maxAttack++;
					//Splitting each attack coordinates
					String[] attacks=T.split(" ");
					for(String t1 : attacks){
						String[] t2=t1.split("(?<=\\d)(?=\\D)");
						int k=Integer.valueOf(t2[1].charAt(0));
						//Checking if the attack affect the ship or not
						if(i==Integer.valueOf(t2[0]) && j==k){
							hit1++;
						}
					}
				}
			}
			//if hit=max attack ship sunk
			if(hit1==maxAttack){
				sunk++;
			}
			//else if hit and is less then max attack not sunk
			else if(hit1>0 && hit1<maxAttack){
				hit=hit+1;
			}
	   }
	   //Making the return string
	   String ret=sunk+" "+hit;

       return ret;
    }
}
