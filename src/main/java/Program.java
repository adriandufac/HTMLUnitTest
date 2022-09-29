import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Program {
	final static String login = "testui35000@gmail.com";
	final static String pw = "Beetween35000!";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Tester tester = new Tester();
			tester.submitFormAndGetUserPseudo(login, pw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
