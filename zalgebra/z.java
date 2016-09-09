import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.lang.Math; 
//import java.util.Matcher;
public class z{
	public static void main(String[] args) {
			ZDigit zd = new ZDigit('A');
			//ZDigit zd1 = new ZDigit('a');
			ZNumber zn = new ZNumber('A','B','C','D');
			ZNumber zn1 = new ZNumber("AB");
			//ZNumber zn2 = new ZNumber(['A','B']);
			ArrayList<Character> ar=new ArrayList<Character>();
			ar.add('A');
			ar.add('B');
			ZNumber zn2 = new ZNumber(ar);
       		System.out.println(zn);
       		System.out.println(zn1);
       		System.out.println(zn2);
       		System.out.printf("%.0f\n",zn1.toDecimal());
       		ZDigit[] z=zn.getDigits("ABCa");
       		/*for (int i = 0; i < z.length;i++) {
       			if(z[i]!=null)
			    System.out.print(z[i] + " ");
			  }*/
	}
}

class ZDigit{
	ZDigit(char a){
		String p="[A-Z0]";
		Pattern r=Pattern.compile(p);
		Matcher m =r.matcher(Character.toString(a));
		if(m.find()){
			System.out.println(a +" Accepted");
		}
		else{
			throw new IllegalArgumentException("Not ZDigit");
		}

	}
}

class ZNumber{
	private StringBuilder sb=new StringBuilder("");
	//Constructor 1
	ZNumber(char... c){
		for(char i:c){
			ZDigit z=new ZDigit(i);
			sb.append(i);	
		}
	}

	//Constructor 2
	ZNumber(String c){
		for(int i=0;i<c.length();i++){
			ZDigit z=new ZDigit(c.charAt(i));
			sb.append(c.charAt(i));	
		}
	}

	//Constructor 3
	ZNumber(ArrayList<Character> c){
		for(char i:c){
			ZDigit z=new ZDigit(i);
			sb.append(i);	
		}
	}

	@Override
    public String toString() {
    	return sb.substring(0);
    }

    int value(char a){
    	if(a=='0')
    		return 0;
    	else
    	return (int)a-64;
    }

    double toDecimal(){
    	double d=0;
    	for(int i=sb.length()-1,j=0;i>=0;i--,j++){
    		d=d+Math.pow(27,i)*value(sb.charAt(j));
    	}
    	return d;
    }
    public ZDigit[] getDigits(String s){
    	int p=s.length();
    	ZDigit[] z=new ZDigit[p]; 
    	for(int i=0;i<s.length();i++){
    		try{
    			ZDigit zt=new ZDigit(s.charAt(i));
    			z[i]=zt;
    		}
    		catch(Exception e){
    			continue;
    		}
    	}
    	return z;
    }
}
