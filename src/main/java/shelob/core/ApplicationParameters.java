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

// $codepro.audit.disable fieldJavadoc

package shelob.core;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.concurrent.Immutable;

import org.openqa.selenium.remote.RemoteWebDriver;

import shelob.core.interfaces.IWaitDelegate;

/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 *
 * Required parameters for accessing the application.
 */
@Immutable
public class ApplicationParameters {
	
	private final RemoteWebDriver driver;
	private final ApplicationURL url;
	private final User user;
	
	// Optional Global settings
	private volatile int defaultWaitInSeconds = 0;
	private volatile IWaitDelegate delegate;
	
	/**
	 * ApplicationParameter Constructor
	 * 
	 * @param driver The browser-specific RemoteWebDriver to be used for automation
	 * @param url The Application URL
	 * @param user The User credentials being used for the testing session.
	 */
	public ApplicationParameters(RemoteWebDriver driver, ApplicationURL url, User user) {
		this.driver = checkNotNull(driver);
		this.url = checkNotNull(url);
		this.user = checkNotNull(user);
		
		delegate = null;
	}
	
	/**
	 * @return the RemoteWebDriver associated with the test 
	 */
	public RemoteWebDriver getDriver(){
		return driver;
	}
	
	/**
	 * @return the URL associated with the test 
     */
	public ApplicationURL getURL(){
		return url;
	}
	
	/**
	 * @return the User associated with the test 
	 */
	public User getUser(){
		return user;
	}
	
	/**
	 * @param intervalInSeconds the default time used to wait for elements to be visible
	 * @return fluent interface; this
	 */
	public ApplicationParameters setDefaultWait(int intervalInSeconds){
		defaultWaitInSeconds = intervalInSeconds;
		return this;
	}
	
	/**
	 * @return defaultWaitInSeconds for elements to be visible
	 */
	public int getDefaultWait() {
		return defaultWaitInSeconds;
	}
	
	public IWaitDelegate getWaitDelegate() {
		return delegate;
	}
	
	public ApplicationParameters setWaitDelegate(IWaitDelegate delegate) {
		this.delegate = delegate;
		return this;
	}
	
	/**
	 * Method toString.
	 * @return String
	 */
	@Override
	public String toString() {
		return user.toString() + System.getProperty("line.separator") + url.toString();
	}
}
