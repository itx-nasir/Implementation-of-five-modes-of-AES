package ModesOfAES;
/*
 * the purpose of the class is to implement 
 * functionality of mode of AES named CIPHER BLOCK CHAINING MODE
 */

public class CBC extends AES /* why extends because it uses the same functionality   of AES
                              */
{                            
	@Override
	public String Encryption(String PlainText, String Key) 
	{
		if(Key.length()<16)
		{
			Key=KeyPadding(Key);
		}
		 String [][]Mix= {{"02","03","01","01"},
		   			{"01","02","03","01"},
		   			{"01","01","02","03"},
		   			{"03","01","01","02"}
					}; /* standard matrix required for the mix column operation*/
		try
		{	/*
		      In CBC we have following steps
		      1 - need initial vector
		      2- divide data into block
		      3- key operation
		     */
			GenerateIV();
			SplitIntoBlocks(PlainText);
			String KeyInHex=Utility.TexttoHEX(Key);
			String [][]W; // word
			String [][]iv;
			W=Utility.StringTo2DArray(KeyInHex);
			iv=Utility.StringTo2DArray(IV);
			RoundKeys[0]=W;
			for(int i=1;i<=10;i++)
			{	
				GenerateKey(RoundKeys[i-1],i); /* using function of AES*/
			}
			for(int i=0;i<=10;i++)
			{	
				RoundKeys[i]=Utility.TransposeMatrix(RoundKeys[i]);
			}
			iv=Utility.TransposeMatrix(iv); // transpose of matrix
			String [][]Resultant=iv;
			
			for(int m=0;m<PT_Blocks.length;m++)
			{
				String [][]PT;
				
				PT=Utility.StringTo2DArray(PT_Blocks[m]);
				PT=Utility.TransposeMatrix(PT);
				
				//for XORING two matrices
				Resultant=AddRoundKey(PT,Resultant);
				Resultant=AddRoundKey(RoundKeys[0],Resultant);
				for(int i=1;i<=10;i++)
				{	SubstituteByte(Resultant,S_Box);
					for(int k=0;k<4;k++)
					{
						leftRotate(Resultant[k],k);
					}

					if(i!=10)
					{
						Resultant=MixColumn(Mix,Resultant); // mix column
					}
					Resultant=AddRoundKey(RoundKeys[i],Resultant);
				}
				CipherText+=Utility.HextoText(Resultant); // generating cipher text
			}
		}
		/*Encryption Done here*/
	   catch(Exception e)
		{
			System.out.print(e);
		}
		return null;
	}

	@Override
	public String Decryption() /* we have inverse mix column for decryption*/
	{
		   String [][]Mix= {{"0E","0B","0D","09"},
		   					{"09","0E","0B","0D"},
		   					{"0D","09","0E","0B"},
		   					{"0B","0D","09","0E"}
  						   };
		try       /* inverse mix column standard matrix need for decryption*/
		{
			SplitIntoBlocks(CipherText);
			String [][]iv;
			iv=Utility.StringTo2DArray(IV);
			iv=Utility.TransposeMatrix(iv);
			String [][]Resultant;
				
			for(int m=0;m<PT_Blocks.length;m++)
				{
					String [][]PT;
					PT=Utility.StringTo2DArray(PT_Blocks[m]);
					PT=Utility.TransposeMatrix(PT);
					Resultant=PT;
					for(int i=10;i>=1;i--)
					{	Resultant=AddRoundKey(RoundKeys[i],Resultant);	
						if(i!=10)
						{
							Resultant=MixColumn(Mix,Resultant); // mix column
							
						}
						for(int k=0;k<4;k++)
							{
								RightRotate(Resultant[k],k);
							}
						SubstituteByte(Resultant,Inverse_S_Box); // substitute byte
					}
					Resultant=AddRoundKey(RoundKeys[0],Resultant);
					Resultant=AddRoundKey(iv,Resultant);
					Decrypted+=Utility.HextoText(Resultant); // receive the messase in textual form
					iv=Utility.StringTo2DArray(PT_Blocks[m]);
					iv=Utility.TransposeMatrix(iv);
					
				}
			}
		   catch(Exception e)
			{
				System.out.print(e);
			}
			return null;
	}
}
