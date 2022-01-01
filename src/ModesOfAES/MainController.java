package ModesOfAES;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController implements Initializable
{
	@FXML private TextField PlainText;
	@FXML private TextField Key;
	@FXML private Label keyLabel,modeLabel,PLabel;
	@FXML private Label CipherText;
	@FXML private Label plainText;
	@FXML private Label IV;
	@FXML public ComboBox<String> modes=null;
	ObservableList<String> list=FXCollections.observableArrayList("ECB","CBC","CFB","OFB","CTR");
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		modes.setItems(list);
	}
	public void EncryptButton(Event event)
	{ 	if(PlainText.getText().length()<=0)
		{
			PLabel.setText("Please Enter PlainText.");
		}
		else if(Key.getText().length()>16 || Key.getText().length()<=0 )
			{
				keyLabel.setText("Please Enter a valid size Key(16 or less character.");
			}
	
		else
		{
			if(!modes.getSelectionModel().isEmpty())
			{
				AES A=null;
				if(modes.getValue().equalsIgnoreCase("ECB"))
				{
					A=new ECB();
				}
				else if(modes.getValue().equalsIgnoreCase("CBC"))
				{
					A=new CBC();
				}
				else if(modes.getValue().equalsIgnoreCase("CFB"))
				{
					A=new CFB();
				}
				else if(modes.getValue().equalsIgnoreCase("OFB"))
				{
					A=new OFB();
				}
				else if(modes.getValue().equalsIgnoreCase("CTR"))
				{
					A=new CTR();
				}
				A.Encryption(PlainText.getText(), Key.getText());
				CipherText.setText(A.GetCipherText());
				if(modes.getValue().equalsIgnoreCase("ECB")||modes.getValue().equalsIgnoreCase("CTR"))
				{
					IV.setText("   Key in Hex:    "+A.GetKey());
				}
				else
				{
					IV.setText("Key in Hex: "+A.GetKey()+"\n"+"Initial Vector: "+A.GetIV()+"\n");
				}
				Main.SetAes(A);
			}
			else
			{
				modeLabel.setText("Please Select a Mode");
			}
		}
	}
	public void DecryptButton(Event event)
	{
		AES A=Main.GetAES();
		A.Decryption();
		plainText.setText(A.GetPlainText());
		//CipherText.setText(A.getCipherText());
		
	}
	
}
