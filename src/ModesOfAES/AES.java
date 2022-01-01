package ModesOfAES;
/*
 *  The purpose of this class is to implement the AES
 *  current class contains the steps of AES
 *  We have AES function call the sub functions
 *  WHY ABSTRACT class?
 *      answer is very simple to resuse the code in modes of AES
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

abstract class AES 
{	protected String [][][]RoundKeys;
	String IV="";
	protected String Decrypted="";  /* contains the plain text*/
	protected String CipherText="";  /* contains the cipher text*/
	protected String [] PT_Blocks;   /* to store blocks of plain Text*/
	protected String[][] S_Box = {   /* contains all values of S_BOX*/
		{"63","7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"},  
	    {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0"},  
	    {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"},  
	    {"04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"},  
	    {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"},  
	    {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"},  
	    {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"},  
	    {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"},  
	    {"CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"},  
	    {"60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"},  
	    {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"},  
	    {"E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"},  
	    {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"},  
	    {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"},  
	    {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"},  
	    {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"}  
	};
	protected String Inverse_S_Box[][] = {  /* this is for the inverse function of S_BOX*/
			{"52", "09", "6A", "D5", "30", "36", "A5", "38", "BF", "40", "A3", "9E", "81", "F3", "D7", "FB"},
			{"7C", "E3", "39", "82", "9B", "2F", "FF", "87", "34", "8E", "43", "44", "C4", "DE", "E9", "CB"},
			{"54", "7B", "94", "32", "A6", "C2", "23", "3D", "EE", "4C", "95", "0B", "42", "FA", "C3", "4E"},
			{"08", "2E", "A1", "66", "28", "D9", "24", "B2", "76", "5B", "A2", "49", "6D", "8B", "D1", "25"},
			{"72", "F8", "F6", "64", "86", "68", "98", "16", "D4", "A4", "5C", "CC", "5D", "65", "B6", "92"},
			{"6C", "70", "48", "50", "FD", "ED", "B9", "DA", "5E", "15", "46", "57", "A7", "8D", "9D", "84"},
			{"90", "D8", "AB", "00", "8C", "BC", "D3", "0A", "F7", "E4", "58", "05", "B8", "B3", "45", "06"},
			{"D0", "2C", "1E", "8F", "CA", "3F", "0F", "02", "C1", "AF", "BD", "03", "01", "13", "8A", "6B"},
			{"3A", "91", "11", "41", "4F", "67", "DC", "EA", "97", "F2", "CF", "CE", "F0", "B4", "E6", "73"},
			{"96", "AC", "74", "22", "E7", "AD", "35", "85", "E2", "F9", "37", "E8", "1C", "75", "DF", "6E"},
			{"47", "F1", "1A", "71", "1D", "29", "C5", "89", "6F", "B7", "62", "0E", "AA", "18", "BE", "1B"},
			{"FC", "56", "3E", "4B", "C6", "D2", "79", "20", "9A", "DB", "C0", "FE", "78", "CD", "5A", "F4"},
			{"1F", "DD", "A8", "33", "88", "07", "C7", "31", "B1", "12", "10", "59", "27", "80", "EC", "5F"},
			{"60", "51", "7F", "A9", "19", "B5", "4A", "0D", "2D", "E5", "7A", "9F", "93", "C9", "9C", "EF"},
			{"A0", "E0", "3B", "4D", "AE", "2A", "F5", "B0", "C8", "EB", "BB", "3C", "83", "53", "99", "61"},
			{"17", "2B", "04", "7E", "BA", "77", "D6", "26", "E1", "69", "14", "63", "55", "21", "0C", "7D" }
	};
	/* round constant matrix need to adding round constant*/
	protected String  [][]RC = {{"01","00","00","00"},
					  {"02","00","00","00"},
					  {"04","00","00","00"},
					  {"08","00","00","00"},
					  {"10","00","00","00"},
					  {"20","00","00","00"},
					  {"40","00","00","00"},
					  {"80","00","00","00"},
					  {"1B","00","00","00"},
					  {"36","00","00","00"}};
	//private String RoundKeys[];
	/* we have abstract because we used it in other classes*/
	abstract public String Encryption(String PlainText,String Key);
	abstract public String Decryption();
	public AES() /* why we have in constructor just when object created it generate the round keys */
	{
		RoundKeys=new String [11][4][4]; /* why 3D because of having key addresses*/
		for(int i=0;i<11;i++)
		{	for(int j=0;j<4;j++)
			{
				for(int k=0;k<4;k++)
				{
					RoundKeys[i][j][k]=""; /* going to generate the keys of all 10 rounds*/
				}
			}	
		}
	}
	/*
	 * Here we are at first step of each round
	 *  after adding round key in zero round
	 */
	public void ByteSubstitutions(String [] W,String Box[][])
	{
		
		for(int k=0;k<4;k++)
		{    
			  int i=Utility.GetIndex(W[k].charAt(0)); /*  just to get index from utility class*/
			  int j=Utility.GetIndex(W[k].charAt(1));
			  W[k]=Box[i][j];
		}
	}
	/*
	 * to generate the round key 
	 * performing to add round constant 
	 * using the utility class
	 */
	public void AddRoundConstant(String []W, int RCIndex)
	{
		for(int i=0;i<4;i++)
		{	
			W[i]=Utility.HexXor(W[i], RC[RCIndex][i]); /* HexXor means to XOR with the matrix values*/
			
		}
	} 
	/*
	 *  Function responsible to add the round key
	 *  with the state matrix i-e message hex values
	 *  
	 */
	public String[][] AddRoundKey(String [][] PT,String [][]RK)
	{
		String [][]Res=new String[4][4];
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				Res[i][j]=Utility.HexXor(PT[i][j], RK[i][j]);
				
			}
		}
		return Res;  /* return the Result as a matrix*/
	}
	/* 
	 * Function responsible to print
	 * any given matrix receive in argument
	 * 
	 */
	public void Disp(String A[][])
	{
		for(int j=0;j<4;j++)
		{
			for(int k=0;k<4;k++)
			{
				System.out.print(A[j][k]+" ");
			}
			System.out.print("\n");
		}
	}
	/*
	 * function responsible to add 1B in case
	 * values exceeds from 2 digits 
	 */
	public int MaintainLength(int x)
	{
        String st=Integer.toHexString(x);

        if(st.length()>2)
        {
        	st=Utility.HexXor(st,"1B"); /* Xor with 1 B*/
        	String r="";
        	if(st.length()>2)
        	{
        		r+=st.charAt(1);
        		r+=st.charAt(2);
        		st=r;
        	}
        
        }
		return Integer.parseInt(st,16);
		
	}
	/*
	 * Function responsible to perform the mix column operation
	 * receive  2 matrix of 4x4
	 * return new state matrix 
	 */
	public String[][] MixColumn(String [][]Mix,String STM[][])
	{
		
		  String res[][]=new String[4][4];
		   int i, j, k;
		    for (i = 0; i < 4; i++) 
		    {
		        for (j = 0; j < 4; j++) 
		        {
		            int temp=0;
		            int t=0;
		            for (k = 0; k < 4; k++)
		            {	
		            	int t1=Integer.parseInt(Mix[i][k] ,16);
		            	int t2=Integer.parseInt(STM[k][j] ,16);
		                if(t1>2)
		                {	if(t1==3)
		                	 {
		                		t=(t1-1)*t2;
		                		t=t^t2;
		                	 }
		                	else if(t1==9)
		                	{
		                		 t=t2;
		                		for(int p=0;p<3;p++)
		                		{	
		                			t=MaintainLength(t*2);
		                		}
		                		t=t^t2;
		                	}
		                	else if(t1==11)
		                	{
		                	
		                		t=t2;
		                		t=MaintainLength(t*2);
		                		t=MaintainLength(t*2);
		                		t=t^t2;
		                		t=MaintainLength(t*2);
		                		t=t^t2;
		                	}
		                	else if(t1==13)
		                	{
		                		
		                		t=t2;
		                		t=MaintainLength(t*2);
		                		t=t^t2;
		                		t=MaintainLength(t*2);
		                		t=MaintainLength(t*2);
		                		t=t^t2;
		                	}
		                	else if(t1==14)
		                	{
		                		
		                		t=t2;
		                		t=MaintainLength(t*2);
		                		t=t^t2;
		                		t=MaintainLength(t*2);
		                		t=t^t2;
		                		t=MaintainLength(t*2);
		                		
		                		
		                	}
		                }
		                
		                else
		                {
		                	t=(t1)*t2;
		                }
		                
		                String st=Integer.toHexString(t);
		                
		               
		                if(st.length()>2)
		                {
		                	st=Utility.HexXor(st,"1B");
		                	String r="";
		                	if(st.length()>2)
		                	{
		                		r+=st.charAt(1);
		                		r+=st.charAt(2);
		                		st=r;
		                	}
		                
		                }
		              
		              temp^=Integer.parseInt(st,16);
		            }
		            res[i][j]=Integer.toHexString(temp);
		            //System.out.print("="+res[i][j]+"\n");
		            if(res[i][j].length()<2)
		            {
		            	String y="0";
		            	y+=res[i][j];
		            	res[i][j]=y;
		            }
		        }
		    }
		return res; 
	} /* mix column DONE*/
	
	/*
	 *  Function responsible to generate the initial vector
	 *  The IV is a random value
	 *  it may also includes the space as a character
	 */
	protected void GenerateIV()
	{
		String Hex="";
        Random random = new Random();
        for(int i=0;i<16;i++)
        {	int val = 1+random.nextInt()%200;
        	if(val<0)
        	{
        		val+=200;
        	}
        	if(val<16)
        	{
        		Hex+="0";
        	}
        	Hex += Integer.toHexString(val); /* first generate an integer
        	                                    then going to convert it into hexa values 
        	                                  */
        }
        IV=Hex;   /* IV in hexa form*/
	}
	/*
	 * Function responsible to perform the 
	 *  first step using an helping
	 *  function named ByteSubstitutions 
	 */
	public void SubstituteByte(String W[][],String Box[][])
	{
		for(int i=0;i<4;i++)
		{
			ByteSubstitutions(W[i],Box); /*calling sub function*/
		}
	}
	
	/*
	 *  As the data need to be in 128 bits
	 *  we have to divide the message into blocks
	 *  Function basically makes the blocks of data
	 */
	public void SplitIntoBlocks(String PlanText)
	{	
		while(PlanText.length()%16!=0)
			{
				PlanText+=(char)(11);
			}
		String PTinHex=Utility.TexttoHEX(PlanText);
		PT_Blocks=new String[PTinHex.length()/32]; /* if the message already less then 
		                                            we have to perform the padding */                                           
		int k=32,j=0;
		for(int i=0;i<PT_Blocks.length;i++)
		{	PT_Blocks[i]="";
			while(j<k)
			{
				PT_Blocks[i]+=PTinHex.charAt(j);
				j++;
			}
			k+=32;
		}		
	}
	/*
	 *  Function responsible to generate the key 
	 *  to perform the Generate key
	 *   from other round keys [ using key of previous round ]
	 */
	public void GenerateKey(String[][] PrevRoundKey,int RoundNo)
	{	
		String []temp=Utility.MakeCopy(PrevRoundKey[3]);
		String []W3=PrevRoundKey[3];
		leftRotate(W3, 1);
		PrevRoundKey[3]=temp;
		ByteSubstitutions(W3,S_Box);
		AddRoundConstant(W3,RoundNo-1);
		/*
		 * above we call all sub functions 
		 * to implement aes round
		 * then to generate the round key 
		 */
		try {
		for(int i=0;i<4;i++)
		{	
			for(int j=0;j<4;j++)
			{
				String t=Utility.HexXor(W3[j], PrevRoundKey[i][j]);
				RoundKeys[RoundNo][i][j]=t;
				W3[j]=t;
			}		
		}
		}
		catch(Exception e) /* java exception */
		{
			System.out.print(e);
		}
	}
	/*
	 * function responsible to display
	 * the round key
	 */
	void DisplayRK()
	{ try {
		for(int i=0;i<11;i++)
		{	for(int j=0;j<4;j++)
			{
				for(int k=0;k<4;k++)
				{
					System.out.print(RoundKeys[i][j][k]+" ");
				}
			}			
			System.out.print("\n");		
		}
		 }
	    catch(Exception e) /* in case we get invalid values */
	    {
		System.out.print(e);
	    }
	}
	/*
	 * rotate the value by one byte  
	 * using helping function called leftrotate one byte
	 */
	public  void leftRotate(String arr[], int d) 
	{ 
    for (int i = 0; i < d; i++) 
        leftRotatebyOne(arr); 
	} 
	/*
	 *  
	 */
	public  void leftRotatebyOne(String arr[]) 
	{ 
	    String temp = arr[0]; 
	    int i=0;
	    for (i = 0; i < (arr.length)-1; i++) 
	        arr[i] = arr[i + 1]; 
	    arr[i] = temp; // we need a record them swap value
	} 
	/*
	 *  function responsible to rotate the values right 
	 *  calling helping function named RightRotatebyOne
	 */
	public  void RightRotate(String arr[], int d) 
	{ 
    for (int i = 0; i < d; i++) 
        RightRotatebyOne(arr); 
	} 
	/* sub function of above function named RightRotate */
	public  void RightRotatebyOne(String arr[]) 
	{ 
	    String temp = arr[(arr.length)-1]; 
	    int i=0;
	    for ( i = (arr.length)-1; i >0; i--) 
	        arr[i] = arr[i - 1]; 
	    arr[i] = temp; 
	}
	/* it will return us original plain text aim to exclude padding characters*/
	public String GetPlainText()
	{	String PlaintextDuplicate = "" ; /* eliminate the padding characters */
		try 
	{
		
		for ( int i = 0 ; i<Decrypted.length()&&(this.Decrypted.charAt(i)!= (char) 11) ; i ++ )
		{
			PlaintextDuplicate += this.Decrypted.charAt(i) ; 
		}
	}
	catch(Exception e)
	{
		System.out.print(e);
	}
		return PlaintextDuplicate ;
	}
	/* it will return us original cipher text */
	public String GetCipherText()
	{
		return this.CipherText ;
	}
	public String GetKey()
	{
		String key = "" ; 
		for ( int i = 0 ; i<4 ; i ++ )
		{
			for(int j=0;j<4;j++)
			{
				key += RoundKeys[0][j][i]; 
			}
		}
		return key ;
	}
	public String KeyPadding(String key)
	{
		while(key.length()!=16)
		{
			key+=(char)(11);
		}
		return key;
	}
	public String GetIV()
	{
		return IV ;
	}
}
