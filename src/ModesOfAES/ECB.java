package ModesOfAES;
/*
 * Purpose of this class is to implement mode named
 * Electronic 
 */

class ECB extends AES
{
/*
	public ECB()
	{
		RoundKeys=new String [11][4][4];
	}*/
	

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
  					};
		try {
			SplitIntoBlocks(PlainText);
			String KeyInHex=Utility.TexttoHEX(Key);
			String [][]W;
			W=Utility.StringTo2DArray(KeyInHex);
			RoundKeys[0]=W;
			for(int i=1;i<=10;i++)
			{	
				GenerateKey(RoundKeys[i-1],i);
			}
			for(int i=0;i<=10;i++)
			{	
				RoundKeys[i]=Utility.TransposeMatrix(RoundKeys[i]);
			}
			
			String [][]Resultant;
			
			for(int m=0;m<PT_Blocks.length;m++)
			{
				String [][]PT;
				PT=Utility.StringTo2DArray(PT_Blocks[m]);
				PT=Utility.TransposeMatrix(PT);
				Resultant=PT;
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
	{
		   String [][]Mix= {{"0E","0B","0D","09"},
		   					{"09","0E","0B","0D"},
		   					{"0D","09","0E","0B"},
		   					{"0B","0D","09","0E"}
  						   };
		try 
		{
			SplitIntoBlocks(CipherText);
				
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
							Resultant=MixColumn(Mix,Resultant);
							
						}
						for(int k=0;k<4;k++)
							{
								RightRotate(Resultant[k],k);
							}
						SubstituteByte(Resultant,Inverse_S_Box);
					}
					Resultant=AddRoundKey(RoundKeys[0],Resultant);
					Decrypted+=Utility.HextoText(Resultant);
					
				}
			}
		   catch(Exception e)
			{
				System.out.print(e);
			}
			return null;
	}
	
}
