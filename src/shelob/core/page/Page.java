/**
	Copyright (c) 2011, Strata Health Solutions Inc.
 	All rights reserved.

	Redistribution and use in source and binary forms, with or without modification, are permitted 
	provided that the following conditions are met:

	Redistributions of source code must retain the above copyright notice, this list of conditions 
	and the following disclaimer.

	Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
	and the following disclaimer in the documentation and/or other materials provided with the distribution.

	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED 
	WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR 
	A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE 
	FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
	BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
	OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
	OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
	EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**/

// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.preferInterfacesToAbstractClasses, fieldJavadoc
package shelob.core.page;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.concurrent.GuardedBy;

import org.openqa.selenium.remote.RemoteWebDriver;

import shelob.core.ApplicationParameters;
import shelob.core.ApplicationURL;
import shelob.core.User;
import shelob.core.exceptions.NonExistentWebElementException;
import shelob.core.interfaces.IHasParentPage;
import shelob.core.interfaces.elements.IElement;
import shelob.core.interfaces.elements.IElementCollection;
import shelob.core.interfaces.page.IPage;


/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 *
 * Abstract Template : Page
 */
public abstract class Page implements IPage {
	
	private final static String DELIMITER = "->";
	private final static boolean VERBOSE = false;
	
	private final ApplicationParameters parameters;
	private final String pageTitle;
	
	@GuardedBy("this") protected IElementCollection elements;
	
	/**
	 * Page Default Constructor
	 * 
	 * @param parameters the ApplicationParameters used to access this page.
	 * @param title the title of the page
	 */
	protected Page (ApplicationParameters parameters, String title) {
		this.parameters = checkNotNull(parameters);
		this.pageTitle = checkNotNull(title);
	}
	
	/**
	 * Method getDriver.
	 * @return RemoteWebDriver
	 * @see com.stratahealth.test.framework.core.interfaces.page.IPage#getDriver()
	 */
	public RemoteWebDriver getDriver() {
		return parameters.getDriver();
	}
	
	/**
	 * Method getURL.
	 * @return ApplicationURL
	 * @see com.stratahealth.test.framework.core.interfaces.page.IPage#getURL()
	 */
	public ApplicationURL getURL() {
		return parameters.getURL();
	}
	
	/**
	 * Method getUser.
	 * @return User
	 * @see com.stratahealth.test.framework.core.interfaces.page.IPage#getUser()
	 */
	public User getUser() {
		return parameters.getUser();
	}
	
	/**
	 * Method getParameters.
	 * @return ApplicationParameters
	 * @see com.stratahealth.test.framework.core.interfaces.page.IPage#getParameters()
	 */
	public ApplicationParameters getParameters() {
		return parameters;
	}
	
	/**
	 * Method goTo.
	 * @return Page
	 * @see com.stratahealth.test.framework.core.interfaces.INavigable#goTo()
	 */
	public Page goTo() {
		parameters.getDriver().get(parameters.getURL().getURL());
		return this;
	}
	
	/**
	 * Method getTitle.
	 * @return String
	 * @see com.stratahealth.test.framework.core.interfaces.page.IPage#getPageTitle()
	 */
	public String getPageTitle() {
		return pageTitle;
	}
	
	/**
	 * Method toString.
	 * @return String
	 */
	@Override
	public String toString(){		
		return getCompleteApplicationPath(this);
	}
	
	/**
	 * Helper Method
	 * @param label
	 * @throws NonExistentWebElementException
	 * @return T 
	 */
	@SuppressWarnings("unchecked")
	public <T extends IElement> T find(String label) throws NonExistentWebElementException { // $codepro.audit.disable methodJavadoc
		return (T)getElements().find(label);
	}
	
	/**
	 * Helper Method
	 * @param label
	 * @param identifiers
	 * @throws NonExistentWebElementException
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public <T extends IElement> T find(String label, String...identifiers) throws NonExistentWebElementException { // $codepro.audit.disable methodJavadoc
		return (T)getElements().find(label, identifiers);
	}
	
	/**
	 * Helper Method
	 * @param type
	 * @param label
	 * @throws NonExistentWebElementException
	 * @return T
	 */
	public <T extends IElement> T find(Class<T> type, String label) throws NonExistentWebElementException { // $codepro.audit.disable overloadedMethods, methodJavadoc
		return getElements().find(type, label);
	}
	
	/**
	 * Helper Method
	 * @param type
	 * @param label
	 * @param identifiers
	 * @throws NonExistentWebElementException
	 * @return T
	 */
	public <T extends IElement> T find(Class<T> type, String label, String...identifiers) throws NonExistentWebElementException { // $codepro.audit.disable overloadedMethods, methodJavadoc
		return getElements().find(type, label, identifiers);
	}
	
	/**
	 * Method getCompleteApplicationPath.
	 * @param page IPage
	 * @return String
	 */
	private String getCompleteApplicationPath(IPage page) {
		
		if (! (page instanceof IHasParentPage))
		{
			final StringBuilder s = new StringBuilder();
			
			if (VERBOSE) {
				s.append(System.getProperty("line.separator") + "Email : ");
				s.append(page.getParameters().getUser().getEmail());
				s.append(System.getProperty("line.separator") + "Application : ");
				s.append(page.getParameters().getURL());	
			}
			
			s.append(System.getProperty("line.separator") + "Location : ");
			s.append(page.getPageTitle());
			
			return s.toString();
		}
		else
			return getCompleteApplicationPath(((IHasParentPage) page).getParentPage()) + DELIMITER + page.getPageTitle();
	}
}
