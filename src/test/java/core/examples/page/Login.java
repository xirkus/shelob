// $codepro.audit.disable fieldJavadoc
package core.examples.page;

import javax.annotation.concurrent.NotThreadSafe;

import org.openqa.selenium.remote.RemoteWebDriver;

import shelob.core.ApplicationParameters;
import shelob.core.ApplicationURL;
import shelob.core.LookUp;
import shelob.core.User;
import shelob.core.elements.ElementCollection;
import shelob.core.exceptions.NonExistentWebElementException;
import shelob.core.interfaces.IHasElements;
import shelob.core.interfaces.elements.IElementCollection;
import shelob.core.page.Page;

import core.examples.element.Button;
import core.examples.element.TextBox;


/**
 * @author melllaguno
 * @version $Revision 1.0 $
 *
 * Main Login Page. Required for access to Gateway application.
 * All other functional components can be assessed from this page.
 */
@NotThreadSafe
public final class Login extends Page implements IHasElements {
	
	private final static String TITLE = "Welcome";
	
	/**
	 * Default Login Constructor
	 * 
	 * @param parameters the ApplicationParameters used to connect to the page 
	 */
	public Login(ApplicationParameters parameters) {
		super(parameters, TITLE);
	}
	
	/**
	 * Login Page Constructor
	 * 
	 * @param driver the RemoteWebDriver instance being used to connect to the page
	 * @param url the ApplicationURL being used to access the page
	 * @param user the User credentials accessing the application
	 */
	public Login(RemoteWebDriver driver, ApplicationURL url, User user, int waitInSeconds) {
		this(new ApplicationParameters (driver, url, user).setDefaultWait(waitInSeconds));		
	}
	
	/**
	 * Method getElements.
	 * @return IElementCollection
	 * @see com.stratahealth.test.framework.core.interfaces.IHasElements#getElements()
	 */
	synchronized public IElementCollection getElements() {
		
		if (elements == null) {
			
			elements = ElementCollection.create()
			
										.put(new TextBox.Builder(this, LookUp.ById, "username")
														.label("E-mail")
														.build())
														
										.put(new TextBox.Builder(this, LookUp.ById, "password")
														.label("Password")
														.build())
														
										.put(new Button.Builder(this, LookUp.ByXpath, "//input[@type=\"image\"]")
													   .label("Log In")
													   .build())
													   
										;
		}
		
		return elements;
	}
	
	/**
	 * Uses the supplied ApplicationParameters to log into the application
	 * @return the Home page */
	public Home loginToApplication() {
		
		try {
			
			this.goTo();
			getElements().find("E-mail").clear().type(getUser().getEmail());
			getElements().find("Password").clear().type(getUser().getPassword());
			getElements().find("Log In").click();
			
		} catch (NonExistentWebElementException e) {
			e.printStackTrace();
		}
		
		return new Home(this.getParameters());
	}
}
