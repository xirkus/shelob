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

package shelob.core.browsers;

import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/** 
 * @author melllaguno
 * @version $Revision: 1.0 $
 *
 * Singleton FirefoxDriver
 */
public enum Firefox {
	
	;
	
	/**
	 * @param gridAddress The URI to the Grid Hub
	 * @return the RemoteWebDriver used against the Grid Hub
	 */
	static public RemoteWebDriver getDriver(URL gridAddress) {
		return new RemoteWebDriver(gridAddress, getDefaultCapabilities());
	}
	
	/**
	 * @return the FirefoxDriver instance
	 */
	static public RemoteWebDriver getDriver() {
		return new FirefoxDriver(getDefaultCapabilities());
	}
	
	static public DesiredCapabilities getDefaultCapabilities() {
		
		// Non-fluent; creates unnecessary intermediate object
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		//capabilities.setVersion("12");
		capabilities.setPlatform(Platform.ANY);
		//capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		return capabilities;
	}
}
