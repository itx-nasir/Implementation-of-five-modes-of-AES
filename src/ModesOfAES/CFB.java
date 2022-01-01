package ModesOfAES;
/*
* the purpose of the class is to implement 
 * functionality of mode of AES named CIPHER FEEDBACK MODE
 */
public class CFB extends AES
{
	@Override
	public String Encryption(String PlainText, String Key) 
	{  /* why extends because it uses the same functionality   of AES
         */
		if(Key.length()<16)
		{
			Key=KeyPadding(Key);
		}
		 String [][]Mix= {{"02","03","01","01"},
		   			{"01","02","03","01"},
		   			{"01","01","02","03"},
		   			{"03","01","01","02"}
					};/* standard matrix required for the mix column operation*/
		try
		{		/*
		      In CFB we have following steps
		      1 - need initial vector
		      2- divide data into block
		      3- key operation
		     */
			GenerateIV();
			SplitIntoBlocks(PlainText);
			String KeyInHex=Utility.TexttoHEX(Key);
			String [][]W;
			String [][]iv;
			W=Utility.StringTo2DArray(KeyInHex);
			iv=Utility.StringTo2DArray(IV);
			iv=Utility.TransposeMatrix(iv);
			RoundKeys[0]=W;
			for(int i=1;i<=10;i++)
			{	
				GenerateKey(RoundKeys[i-1],i);
			}
			for(int i=0;i<=10;i++)
			{	
				RoundKeys[i]=Utility.TransposeMatrix(RoundKeys[i]); // taking transpose
			}
			
			
			String [][]Resultant=iv; // generated initial vector
			
			for(int m=0;m<PT_Blocks.length;m++)
			{	
				
				Resultant=AddRoundKey(RoundKeys[0],Resultant);
				
				
				for(int i=1;i<=10;i++)
				{	SubstituteByte(Resultant,S_Box);
					
					for(int k=0;k<4;k++)
					{
						leftRotate(Resultant[k],k);
					}

					if(i!=10)
					{
						Resultant=MixColumn(Mix,Resultant);
					}
					Resultant=AddRoundKey(RoundKeys[i],Resultant);
				}
				String [][]PT;
				PT=Utility.StringTo2DArray(PT_Blocks[m]);
				PT=Utility.TransposeMatrix(PT);
				//for XORING two matrices
				Resultant=AddRoundKey(PT,Resultant);

				CipherText+=Utility.HextoText(Resultant);
			}
		}
	   catch(Exception e)
		{
			System.out.print(e);
		}
		return null;
	}

	@Override
	public String Decryption() 
	{  /*
	    Function responsible to perform the Decryption
	    of CFB mode 
	   */
		 String [][]Mix= {{"02","03","01","01"},
		   			{"01","02","03","01"},
		   			{"01","01","02","03"},
		   			{"03","01","01","02"}
					};
		try 
		{
			SplitIntoBlocks(CipherText);
			String [][]iv;
			iv=Utility.StringTo2DArray(IV); /* using utility string to matrix*/
			iv=Utility.TransposeMatrix(iv); /*taking transpose of a matrix*/
			String [][]Resultant=iv;
				
			for(int m=0;m<PT_Blocks.length;m++)
				{
				Resultant=AddRoundKey(RoundKeys[0],Resultant);
				
				for(int i=1;i<=10;i++)
				{	SubstituteByte(Resultant,S_Box);
					
					for(int k=0;k<4;k++)
					{
						leftRotate(Resultant[k],k);
					}

					if(i!=10)
					{
						Resultant=MixColumn(Mix,Resultant); // mixx column operation
					}
					Resultant=AddRoundKey(RoundKeys[i],Resultant);
				}
				String [][]PT;
				PT=Utility.StringTo2DArray(PT_Blocks[m]);
				PT=Utility.TransposeMatrix(PT);
				//for XORING two matrices
				Resultant=AddRoundKey(PT,Resultant);
				Decrypted+=Utility.HextoText(Resultant);
				Resultant=PT;
					
				}
				
			}
		   catch(Exception e)
			{
				System.out.print(e);
			}
			return null;
	}
}
