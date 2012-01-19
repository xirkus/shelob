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

package shelob.core.interfaces.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

/**
 * @author melllaguno
 * @version $Revision 1.0 $
 * 
 * Utility methods to allow for Element operations with implicit waits 
 * for visible elements
 */
public interface IWhenVisible {

	/**
	 * @param waitTimeInSeconds
	 * @return IElement fluent interface; this
	 */
	IElement setTimeout(int waitTimeInSeconds);
	
	/**
	 * @return IElement fluent interface; this
	 */
	int getTimeout();
	
	/**
	 * @return IElement fluent interface; this
	 */
	IElement clearWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return IElement fluent interface; this
	 */
	IElement clearWhenVisible(int waitTimeInSeconds);
	
	/**	 
	 * @return IElement fluent interface; this
	 */
	IElement clickWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return IElement fluent interface;  this
	 */
	IElement clickWhenVisible(int waitTimeInSeconds);
		
	/**
	 * @param arg0
	 * @return List<WebElement>
	 */
	List<WebElement> findElementsWhenVisible(By arg0);
	
	/**
	 * @param arg0
	 * @param waitTimeInSeconds
	 * @return List<WebElement>
	 */
	List<WebElement> findElementsWhenVisible(By arg0, int waitTimeInSeconds);
	
	/**
	 * @param arg0
	 * @return String
	 */
	String getAttributeWhenVisible(String arg0);
	
	/**
	 * @param arg0
	 * @param waitTimeInSeconds
	 * @return String
	 */
	String getAttributeWhenVisible(String arg0, int waitTimeInSeconds);
	
	/**
	 * @return String
	 */
	String getTagNameWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return String
	 */
	String getTagNameWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @return String
	 */
	String getTextWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return String
	 */
	String getTextWhenVisible(int waitTimeInSeconds);
		
	/**
	 * @return boolean
	 */
	boolean isEnabledWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	boolean isEnabledWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @return boolean
	 */
	boolean isSelectedWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	boolean isSelectedWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @param arg0
	 * @return IElement fluent interface; this
	 */
	IElement sendKeysWhenVisible(CharSequence... arg0);
	
	/**
	 * @param waitTimeInSeconds
	 * @param arg0
	 * @return IElement fluent interface; this
	 */
	IElement sendKeysWhenVisible(int waitTimeInSeconds, CharSequence...arg0);
	
	/**
	 * Delegates to sendKeys
	 * @param arg0
	 * @return IElement fluent interface; this
	 */
	IElement typeWhenVisible(CharSequence... arg0);
	
	/**
	 * Delegates to sendKeys
	 * @param waitTimeInSeconds
	 * @param arg0
	 * @return IElement fluent interface; this
	 */
	IElement typeWhenVisible(int waitTimeInSeconds, CharSequence...arg0);
		
	/**
	 * @return IElement fluent interface; this
	 */
	IElement submitWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return IElement fluent interface; this
	 */
	IElement submitWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @return boolean
	 */
	boolean isDisplayedWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	boolean isDisplayedWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @param value
	 * @return String
	 */
	String getCssValueWhenVisible(String value);
	
	/**
	 * @param value
	 * @param waitTimeInSeconds
	 * @return String
	 */
	String getCssValueWhenVisible(String value, int waitTimeInSeconds);
	
	/**
	 * @return Point
	 */
	Point getLocationWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return Point
	 */
	Point getLocationWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @return Dimension
	 */
	Dimension getSizeWhenVisible();
	
	/**
	 * @param waitTimeInSeconds
	 * @return Dimension
	 */
	Dimension getSizeWhenVisible(int waitTimeInSeconds);
}
