// $codepro.audit.disable fieldJavadoc
package examples.page;

import javax.annotation.concurrent.NotThreadSafe;

import org.openqa.selenium.remote.RemoteWebDriver;

import shelob.core.ApplicationParameters;
import shelob.core.ApplicationURL;
import shelob.core.User;
import shelob.core.elements.ElementCollection;
import shelob.core.interfaces.IRequiresLogin;
import shelob.core.interfaces.elements.IElementCollection;
import shelob.core.page.StandardNavigationPage;


/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 * 
 * Main Home Page. Required for access to Gateway application.
 * All other functional components can be accessed from this page.
 *
 */
@NotThreadSafe
public final class Home extends StandardNavigationPage implements IRequiresLogin<Home> {

	private final static String TITLE = "Home";
	
	/**
	 * Default Home Page Constructor.
	 * 
	 * @param parameters the ApplicationParameters used to connect to the page
	 */
	public Home(ApplicationParameters parameters){
		super(parameters, TITLE);
	}
	
	/**
	 * Home Page Constructor.
	 * 
	 * @param driver the RemoteWebDriver instance being used to connect to the page
	 * @param url the ApplicationURL being used to access the page
	 * @param user the User credentials accessing the application
	 */
	public Home(RemoteWebDriver driver, ApplicationURL url, User user) {
		this(new ApplicationParameters(driver, url, user));
	}

	/**
	 * Method getElements.
	 * @return IElementCollection
	 * @see com.stratahealth.test.framework.core.interfaces.IHasElements#getElements()
	 */
	synchronized public IElementCollection getElements() {
		
		if (elements == null) {
			
			elements = ElementCollection.create();
		}
		
		return elements;
	}
	
	/**
	 * Method loginAndNavigateToPage.
	 * @see com.stratahealth.test.framework.core.interfaces.IRequiresLogin#loginAndNavigateToPage() */
	public Home loginAndNavigateToPage() {
		final Login login = new Login(this.getParameters());
		return login.loginToApplication();
	}
}
