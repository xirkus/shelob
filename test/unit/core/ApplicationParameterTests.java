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

// $codepro.audit.disable fieldJavadoc, typeJavadoc, methodJavadoc, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString

package unit.core;

import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import shelob.core.ApplicationParameters;
import shelob.core.ApplicationURL;
import shelob.core.User;
import shelob.core.interfaces.IWaitDelegate;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ApplicationParameterTests {

	private ApplicationURL url;
	private final String appHostName = "localhost";
	private final String appEnvironment = "cgi-bin/WebObjects";
	private final String appURL= "PWTransition-Zulu.woa";
	
	private User user;
	private final String email = "noone@test.com";
	private final String password = "password";
	
	private RemoteWebDriver driver;
	
	private ApplicationParameters parameters;
	
	@Before
	public void setup() {
		
		driver = mock(RemoteWebDriver.class);
		
		url = new ApplicationURL(appHostName, appEnvironment, appURL);
		
		user = new User.Builder(email, password).build();
		
		parameters = new ApplicationParameters(driver, url, user);
		parameters.setDefaultWait(5);
	}
	
	@After
	public void teardown() {}
	
	@Test
	public void baseApplicationParametersTests(){
		
		assertThat(parameters.getDriver(), is(driver));
		assertThat(parameters.getURL(), is(url));
		assertThat(parameters.getUser(), is(user));
		assertThat(parameters.toString(), is(user.toString() + System.getProperty("line.separator") + url.toString()));
		assertThat(parameters.getDefaultWait(), is(5));
		assertNull(parameters.getWaitDelegate());
	}
	
	@Test
	public void waitDelegateTests(){
		
		// The WaitDelegate should be used to wire client side wait logic in with tests. For example,
		// applications which display a spinner or some other visual device to prevent input or notify 
		// the user of a running operation can call javascript to attempt to evaluate when the test
		// should continue.
		
		parameters.setWaitDelegate(new IWaitDelegate(){

			public void run() {
				
				// Scripts can return values as well, which can be used for post-processing logic
				//String returnValue = (String) ((JavascriptExecutor)driver).executeScript("return true;");
				
				((JavascriptExecutor)driver).executeScript("return true;");
			}
		});
		
		parameters.getWaitDelegate().run();
		verify((JavascriptExecutor)driver).executeScript("return true;");	
	}
}
