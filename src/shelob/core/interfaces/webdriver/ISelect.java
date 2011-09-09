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

import java.util.List;

import org.openqa.selenium.WebElement;

/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 * 
 * The Select Utility Wrapper Interface for WebElements
 * 
 * @param <T> the fluent interface type
 */
public interface ISelect<T> {

	/**
	 * @see org.openqa.selenium.WebElement.Select#isMultiple
	 * @return boolean
	 */
	boolean isMultiple();
	
	/**
	 * Helper Method : wraps function with Wait
	 * @return boolean
	 */
	boolean isMultipleWhenVisible();

	/**
	 * Helper Method : wraps function with Wait
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	boolean isMultipleWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#getOptions
	 * @return List<WebElement>
	 */
	List<WebElement> getOptions();
	
	/**
	 * Helper Method : wraps function with Wait
	 * @return List<WebElement>
	 */
	List<WebElement> getOptionsWhenVisible();
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param waitTimeInSeconds
	 * @return List<WebElement
	 */
	List<WebElement> getOptionsWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#getAllSelectedOptions
	 * @return List<WebElement>
	 */
	List<WebElement> getAllSelectedOptions();
	
	/**
	 * Helper Method : wraps function with Wait
	 * @return List<WebElement
	 */
	List<WebElement> getAllSelectedOptionsWhenVisible();
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param waitTimeInSeconds
	 * @return List<WebElement>
	 */
	List<WebElement> getAllSelectedOptionsWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#getFirstSelectedOption
	 * @return WebElement
	 */
	WebElement getFirstSelectedOption();
	
	/**
	 * Helper Method : wraps function with Wait
	 * @return WebElement
	 */
	WebElement getFirstSelectedOptionWhenVisible();
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param waitTimeInSeconds
	 * @return WebElement
	 */
	WebElement getFirstSelectedOptionWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#selectByVisibleText
	 * @param text
	 * @return T the fluent interface type
	 */
	T selectByVisibleText(String text);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param text
	 * @return T the fluent interface type
	 */
	T selectByVisibleTextWhenVisible(String text);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param text
	 * @param waitTimeInSeconds
	 * @return T the fluent interface type
	 */
	T selectByVisibleTextWhenVisible(String text, int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#selectByIndex
	 * @param index
	 * @return T the fluent interface type
	 */
	T selectByIndex(int index);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param index
	 * @return T the fluent interface type
	 */
	T selectByIndexWhenVisible(int index);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param index
	 * @param waitTimeInSeconds
	 * @return T the fluent interface type
	 */
	T selectByIndexWhenVisible(int index, int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#selectByValue
	 * @param value
	 * @return T the fluent interface type
	 */
	T selectByValue(String value);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param value
	 * @return T the fluent interface type
	 */
	T selectByValueWhenVisible(String value);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param value
	 * @param waitTimeInSeconds
	 * @return T the fluent interface type
	 */
	T selectByValueWhenVisible(String value, int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#deselectAll
	 * @return T the fluent interface type
	 */
	T deselectAll();
	
	/**
	 * Helper Method : wraps function with Wait
	 * @return T the fluent interface type
	 */
	T deselectAllWhenVisible();
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param waitTimeInSeconds
	 * @return T the fluent interface type
	 */
	T deselectAllWhenVisible(int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#deselectByValue
	 * @param value
	 * @return T the fluent interface type
	 */
	T deselectByValue(String value);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param value
	 * @return T the fluent interface type
	 */
	T deselectByValueWhenVisible(String value);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param value
	 * @param waitTimeInSeconds
	 * @return T the fluent interface type
	 */
	T deselectByValueWhenVisible(String value, int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#deselectByIndex
	 * @param index
	 * @return T the fluent interface type
	 */
	T deselectByIndex(int index);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param index
	 * @return T the fluent interface type
	 */
	T deselectByIndexWhenVisible(int index);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param index
	 * @param waitTimeInSeconds
	 * @return T the fluent interface type
	 */
	T deselectByIndexWhenVisible(int index, int waitTimeInSeconds);
	
	/**
	 * @see org.openqa.selenium.WebElement.Select#deselectByVisibleText
	 * @param text
	 * @return T the fluent interface type
	 */
	T deselectByVisibleText(String text);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param text
	 * @return T the fluent interface type
	 */
	T deselectByVisibleTextWhenVisible(String text);
	
	/**
	 * Helper Method : wraps function with Wait
	 * @param text
	 * @param waitTimeInSeconds
	 * @return T the fluent interface type
	 */
	T deselectByVisibleTextWhenVisible(String text, int waitTimeInSeconds);
}
