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

package shelob.core.interfaces.webdriver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import shelob.core.interfaces.elements.IElement;


/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 * 
 * Wrapper interface around WebElement interface to support fluent interactions 
 * with the object
 */
public interface IWebElementFluent {

	/**
	 * @return IElement fluent interface; this - void return method on WebElement
	 */
	IElement clear();

	/**
	 * @return IElement fluent interface; this - void return method on WebElement
	 */
	IElement click();
		
	/**
	 * unmodified method signature from WebElement
	 * 
	 * @param arg0
	 * @return String
	 */
	String getAttribute(String arg0);
	
	/**	 
	 * unmodified method signature from WebElement
	 * 
	 * @return String
	 */
	String getTagName();
	
	/**
	 * unmodified method signature from WebElement
	 * 
	 * @return String
	 */
	String getText();
		
	/**
	 * unmodified method signature from WebElement
	 * 
	 * @return boolean
	 */
	boolean isEnabled();
	
	/**
	 * unmodified method signature from WebElement
	 * 
	 * @return isSelected
	 */
	boolean isSelected();
	
	/**
	 * @param arg0
	 * @return IElement fluent interface; this - void return method on WebElement
	 */
	IElement sendKeys(CharSequence... arg0);
		
	/**
	 * @return IElement fluent interface; this - void return method on WebElement
	 */
	IElement submit();
		
	/**
	 * unmodified method signature from WebElement
	 * 
	 * @return boolean
	 */
	boolean isDisplayed();
	
	/**
	 * unmodified method signature from WebElement
	 * 
	 * @return String
	 */
	public String getCssValue(String value);
	
	/**
	 * unmodified method signature from WebElement
	 * 
	 * @return Point
	 */
	public Point getLocation();
	
	/**
	 * ummodified method signature from WebElement
	 * 
	 * @return Dimension
	 */
	public Dimension getSize();
}
