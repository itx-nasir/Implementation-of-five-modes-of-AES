package ModesOfAES;
import java.math.BigInteger;
// Java program to convert ASCII
// string to Hexadecimal format string
import java.util.Scanner;
/*
 * the purpose of class to help other class to:
 * encode character
 * convert string to array
 * changing matrix dimensions like array to matrix
 * perform xor function
 * 
 */
class Utility 
{       /*
           in hexa we get value like A to F and are not case sensitive
         */
		public static int GetIndex(char ch)
		{
			if(ch=='A'||ch=='a')
			{
				return 10;
			}
			if(ch=='B'||ch=='b')
			{
				return 11;
			}
			if(ch=='C'||ch=='c')
			{
				return 12;
			}
			if(ch=='D'||ch=='d')
			{
				return 13;
			}
			if(ch=='E'||ch=='e')
			{
				return 14;
			}
			if(ch=='F'||ch=='f')
			{
				return 15;
			}
		return(((int)ch)-48);
		}
		/* just to create copy*/
		public static String[] MakeCopy(String[] arr)
		{
			String[] a=new String[arr.length];
			for(int i=0;i<arr.length;i++)
			{
				a[i]=arr[i];
			}
			return a;
		}
       /* receive a string and return a matrix*/
		public static String[][] StringTo2DArray(String str)
		{
			String [][]W=new String[4][4];
			int k=0;
			for(int i=0;i<4;i++)
			{
				for(int j=0;j<4;j++)
				{
					W[i][j]=Character.toString(str.charAt(k))+Character.toString(str.charAt(k+1));
					k+=2;
				}
				
			}
			
			return W;
		}
		/*
		 * function going to take transpose of a matrix
		 */
		public static String[][] TransposeMatrix(String W[][])
		{
			String [][]temp=new String[4][4];
			for(int i=0;i<4;i++)
			{
				for(int j=0;j<4;j++)
				{
					temp[i][j]=W[j][i];
				}
			}
			return temp;
		}
		public static String HexXor(String hex1,String hex2)
		{   
			int dec1=Integer.parseInt(hex1,16);
			int dec2=Integer.parseInt(hex2,16);
			String part = Integer.toHexString(dec1^dec2);
			String temp="0";
			if(part.length()==1)
			{
				temp+=part;
				part=temp;
			}
			return part;
		}
		/*
		 * function responsible to convert hexa value 
		 * to text or to string
		 */
	   public static String HextoText(String arr[][])
	   {
		      String result ="";
		      for(int i = 0; i < 4; i++) 
		      {
		    	  for(int j=0;j<4;j++)
		    	  {
		    		  result+= (char)Integer.parseInt(arr[j][i], 16);
		    	  }
		      }
		      return result;
	   }
	/* function to convert ASCII values to HEX */
		public static String TexttoHEX(String ascii)
		{
		
			// Initialize final String
			String hex = "";

			// Make a loop to iterate through
			// every character of ascii string
			for (int i = 0; i < ascii.length(); i++) {

				// take a char from
				// position i of string
				char ch = ascii.charAt(i);

				// cast char to integer and
				// find its ascii value
				int in = (int)ch; 

				// change this ascii value
				// integer to hexadecimal value
				if(in<16)
				{
					hex+="0";
				}
				String part = Integer.toHexString(in);

				// add this hexadecimal value
				// to final string.
				hex += part;
			}
		
			// return the final string hex
			return hex;
		}
		/*
		 * function responsible to convert hexa to byte values
		 */
		static public byte [] HexToByte(String str)
		{	byte[] val = new byte[str.length() / 2];
			for (int i = 0; i < val.length; i++)
			{
				   int index = i * 2;
				   int j = Integer.parseInt(str.substring(index, index + 2), 16);
				   val[i] = (byte) j;
			}
			return val;
		}



}
