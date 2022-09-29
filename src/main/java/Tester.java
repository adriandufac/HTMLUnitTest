import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Tester {
	
	
	private WebClient webClient; //utilisé pour simuler un navigateur
	private HtmlPage page;
	
	
	public Tester() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		page = webClient.getPage("https://stackoverflow.com/users/login?ssrc=head");
		
	}
	
	
	public  void submitFormAndGetUserPseudo(String email,String password) throws IOException {

		System.out.println("adresse de la page avant connexion");
		System.out.println(this.page.toString());
		
		//Acces aux differents elements HTML dont nous avons besoin
		//HtmlForm form= (HtmlForm) this.page.getHtmlElementById("login-form");
		HtmlButton buttonSubmit = this.page.getHtmlElementById("submit-button");
		HtmlTextInput emailField = this.page.getHtmlElementById("email");
		HtmlPasswordInput passwordField = this.page.getHtmlElementById("password");
		
		//Remplissage des champs du forulaire
		emailField.type(email);
		passwordField.type(password);
		
		//Simulation d'un appuie sur le bouton submit et redirection vers une autre page
		final HtmlPage page2 = buttonSubmit.click();
		
		//si l'URL de la page contient "captcha" c'est que nous avons fait trop de connexion et nous devons attendre
		if (page2.toString().indexOf("captcha") != -1) {
			System.out.println("Trop de connexions :( veuillez attendre avant de réessayer");
		}
		//si la page n'a pas changé, la connexion a échoué
		else if (page2.toString()== this.page.toString()) {
			System.out.println("connexion échouée (vérifier l'email ou mdp)");
		}
		//si la page n'est pas celle du captcha et est differentes de celle de base, alors nous avons bien était redirigé vers la page d'acceuil et donc bien log
		else if(page2.toString()!= this.page.toString()) {
			System.out.println("connexion réussie");
			System.out.println("adresse de la page après connexion");
			System.out.println(page2.toString());
			
			//acces a la page du profile
			HtmlAnchor buttonProfile = page2.getFirstByXPath("//a[@data-gps-track='profile_summary.click()']");
			final HtmlPage page3 = buttonProfile.click();
			
			// Selection de la div contenant mon pseudo afin de l'afficher
			HtmlDivision pseudoDiv =page3.getFirstByXPath("//div[@class='flex--item mb12 fs-headline2 lh-xs']");
			String myPseudo = pseudoDiv.getTextContent();
			System.out.println("le pseudo de l'utilisateur connecté est :");
			System.out.println(myPseudo);
		}		
		
	}
}


