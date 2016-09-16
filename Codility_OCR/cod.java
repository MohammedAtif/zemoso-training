import java.io.*;
import java.util.*;
public class cod{
	public static void main(String[] args){
		Solution s=new Solution();
		System.out.println(s.solution("a2le i1","2ple2s"));
	}
}

class Solution {

 public boolean solution(String S, String T){
 	int num=0;
 	int digit=0;
 	//int i=0;
 	int nd=0;
 	//changing the S into *
 	StringBuilder sb=new StringBuilder(S);
 	StringBuilder tb=new StringBuilder(T);
 	for(int i=0;i<S.length();i++){
 		if(Character.isDigit(S.charAt(i))){
 			nd++;
 			int t=Character.getNumericValue(S.charAt(i));

 			num=num*10+t;
 		}
 		else{
 			if(num>0){
 				digit=num;
 				int t1=0;
 				while(!Character.isDigit(sb.charAt(t1))){
 					t1++;
 				}

 				while(num>0){

 					sb.insert(t1,"*");
 					num--;	
 				}
 				sb.delete(t1+digit,t1+digit+nd);
 				digit=0;
 				nd=0;
 			}
 		}
 	}

 			if(num>0){
 				digit=num;
 				int t1=0;
 				while(!Character.isDigit(sb.charAt(t1))){
 					t1++;
 				}

 				while(num>0){
 					//System.out.println("hello");
 					sb.insert(t1,"*");
 					num--;	
 				}
 				//System.out.println(sb);
 				sb.delete(t1+digit,sb.length());
 				digit=0;
 			}

 			//***********************Changing T

 	num=0;
 	digit=0;
 	
 	nd=0;
 			


 	for(int i=0;i<T.length();i++){
 		if(Character.isDigit(T.charAt(i))){
 			nd++;
 			int t=Character.getNumericValue(T.charAt(i));

 			num=num*10+t;
 		}
 		else{
 			if(num>0){
 				digit=num;
 				int t1=0;
 				while(!Character.isDigit(tb.charAt(t1))){
 					t1++;
 				}

 				while(num>0){

 					tb.insert(t1,"*");
 					num--;	
 				}
 				tb.delete(t1+digit,t1+digit+nd);
 				digit=0;
 				nd=0;
 			}
 		}
 	}

 			if(num>0){
 				digit=num;
 				int t1=0;
 				while(!Character.isDigit(tb.charAt(t1))){
 					t1++;
 				}

 				while(num>0){
 					//System.out.println("hello");
 					tb.insert(t1,"*");
 					num--;	
 				}
 				//System.out.println(sb);
 				tb.delete(t1+digit,tb.length());
 				digit=0;
 			}

//System.out.println(sb);
//System.out.println(tb);
 	boolean t=true;
//checking if they are similar
 	if(sb.length()!=tb.length()){
 		t=false;
 	}
 	else{
 		for(int i=0;i<sb.length();i++){
 			if(sb.charAt(i)!=tb.charAt(i)){
 				if(sb.charAt(i)=='*'||tb.charAt(i)=='*'){
 					continue;
 				}
 				else{
 					t=false;
 				}
 			}
 		}
 	}

 	return t;
 	} 

}