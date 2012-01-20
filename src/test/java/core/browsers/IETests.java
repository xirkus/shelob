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

package core.browsers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import shelob.core.browsers.InternetExplorer;


public class IETests {

	private RemoteWebDriver ie;
	private RemoteWebDriver ieGrid;
	
	@Before
	public void setup() {}
	
	@After
	public void teardown() {}
		
	// Fails due to the fact that we run these tests outside of Windows XP
	@Test(expected=WebDriverException.class)
	public void baseInternetExplorerTest(){
		
		ie = InternetExplorer.getDriver();
		assertThat(ie, is(notNullValue()));
		ie.close();
	}
	
	// Expect failure here since there is no running instance of Grid at the specified URL
	@Test(expected=WebDriverException.class)
	public void baseInternetExplorereGridTest(){
		
		try {
			ieGrid = InternetExplorer.getDriver(new URL("http://localhost"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(ieGrid, is(notNullValue()));
	}
	
	@Test
	public void capabilitiesTest(){
		
		assertThat(InternetExplorer.getDefaultCapabilities().getVersion(), is("6"));
		assertThat(InternetExplorer.getDefaultCapabilities().getPlatform().is(Platform.WINDOWS), is(true));
		assertThat(InternetExplorer.getDefaultCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT).equals(true), is(true));
	}
}
