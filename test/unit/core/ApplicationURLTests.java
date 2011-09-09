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

import shelob.core.ApplicationURL;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ApplicationURLTests {

	private ApplicationURL app;
	
	private final String appHostName = "localhost";
	private final Integer appPort = 8443;
	private final String appEnvironment = "cgi-bin/WebObjects";
	private final String appURL= "PWTransition-Zulu.woa";
	
	@Test
	public void baseApplicationURLTest(){
		
		app = new ApplicationURL(appHostName, appPort, appEnvironment, appURL);
		
		assertThat(app.getBaseURL(), is("/" + appEnvironment + "/" + appURL));
		assertThat(app.getEnvironmentPrefix(), is(appEnvironment));
		assertThat(app.getHostname(), is(appHostName));
		assertThat(app.getPort(), is(8443));
		assertThat(app.getURL(), is("https://" + appHostName + ":" + appPort + "/" + appEnvironment + "/" + appURL ));
		assertThat(app.toString(), is("Application URL : " + app.getURL()));
	}
	
	@Test
	public void testDefaultPort(){
		
		final ApplicationURL defaultPort = new ApplicationURL(appHostName, appEnvironment, appURL);
		
		assertThat(defaultPort.getPort(), is(443));
		assertThat(defaultPort.getURL(), is("https://" + appHostName + "/" + appEnvironment + "/" + appURL));
	}
	
}
