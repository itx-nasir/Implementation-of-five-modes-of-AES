package ModesOfAES;

import java.math.BigInteger;

public class CTR extends AES
{
	
	
	
	public String IncrementCounter(String counter)
	{	 
		  BigInteger one, UpdatedCounter;
	      one = new BigInteger("1");
	      UpdatedCounter = new BigInteger(counter, 16);
	      UpdatedCounter=UpdatedCounter.add(one);
	      return(""+UpdatedCounter);
	}
	
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
		try
		{	GenerateIV();
			SplitIntoBlocks(PlainText);
			String KeyInHex=Utility.TexttoHEX(Key);
			String counterString=IV;
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
			
			
			
			
			for(int m=0;m<PT_Blocks.length;m++)
			{	String [][]Counter;
				Counter=Utility.StringTo2DArray(counterString);
				Counter=Utility.TransposeMatrix(Counter);
				String [][]Resultant=Counter;
				
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
				String [][]Resultant2=AddRoundKey(PT,Resultant);
				
				CipherText+=Utility.HextoText(Resultant2);
				counterString=IncrementCounter(counterString);
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
		 String [][]Mix= {{"02","03","01","01"},
		   			{"01","02","03","01"},
		   			{"01","01","02","03"},
		   			{"03","01","01","02"}
					};
		try 
		{
			SplitIntoBlocks(CipherText);
			String counterString=IV;

				
			for(int m=0;m<PT_Blocks.length;m++)
				{
					String [][]Counter;
					Counter=Utility.StringTo2DArray(counterString);
					Counter=Utility.TransposeMatrix(Counter);
					String [][]Resultant=Counter;

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
				String [][]Resultant2=AddRoundKey(PT,Resultant);
				Decrypted+=Utility.HextoText(Resultant2);
				counterString=IncrementCounter(counterString);
				}
				
			}
		   catch(Exception e)
			{
				System.out.print(e);
			}
			return null;
	}
}
