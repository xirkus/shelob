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
package core.examples.element;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import shelob.core.LookUp;
import shelob.core.elements.Element;
import shelob.core.elements.ElementBuilder;
import shelob.core.interfaces.elements.ILinkable;
import shelob.core.interfaces.page.IPage;

import core.examples.element.interfaces.IDropdown;

/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 * 
 * The Dropdown primitive Element
 */
@NotThreadSafe
public final class Dropdown extends Element implements IDropdown { // $codepro.audit.disable typeJavadoc
	
	/** // $codepro.audit.disable typeJavadoc
	 * The Dropdown Builder
	 */
	public final static class Builder 
					 	extends ElementBuilder<Builder> 
						implements ILinkable<Builder> { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString
		
		private static final LookUp DEFAULT_LOOKUP = LookUp.ByXpath;
		private static final String WITHOUT_ID_TEMPLATE = "/select";
		private static final String WITH_ID_TEMPLATE = "/select[contains(.,'%s')]";
		
		// Optional parameters
		private IPage linksTo;
		
		/**
		 * The Dropdown Builder with Required Fields
		 * 
		 * @param parent the IPage parent where the element is located	
		 * @param lookup the LookUp strategy used to locate the element
		 * @param locator the locator string used to specify the element
		 */
		public Builder(IPage parent, LookUp lookup, String locator) {
			super(parent, lookup, locator);
		}
		
		/**
		 * The Dropdown Builder that uses the Default Lookup with a string identifier
		 * 
		 * @param parent the IPage parent this element is found on
		 * @param identifier the identifier used to differentiate the element from others like it
		 */
		public Builder(IPage parent, String identifier) {
			super(parent, DEFAULT_LOOKUP, String.format(WITH_ID_TEMPLATE, identifier));
		}
		
		/**
		 * The Dropdown Builder tat uses the Default Lookup/Identifier
		 * 
		 * @param parent the IPage parent this element is found on
		 */
		public Builder(IPage parent) {
			super(parent, DEFAULT_LOOKUP, WITHOUT_ID_TEMPLATE);
		}
		
		/**
		 * The Optional linksTo parameter
		 * 
		 * @param page the IPage this element links to		
		 * @return this; fluent interface  
		 * @see com.stratahealth.test.framework.core.interfaces.elements.ILinkable#linksTo(IPage)
		 */
		public Builder linksTo(IPage page) {
			linksTo = page;
			return this;
		}
		
		/**
		 * The factory method
		 * 
		 * @return a new instance of the Dropdown element  
		 */
	    public IDropdown build() {
	    	
	    	final Dropdown control = new Dropdown(this); 
	    	
	    	configure(control);
	    	
			return control;
		}
	}
	
	/**
	 * Limited Scope Default Constructor
	 * 
	 * @param builder the Dropdown.Builder responsible for creating this object
	 */
	private Dropdown(Builder builder) {
		super(builder.parent, builder.lookup, builder.locator, builder.label, builder.linksTo);
	}

	/**
	 * Method isMultiple.
	 * @return boolean
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#isMultiple()
	 */
	public boolean isMultiple() {
		return getSelection(getWebElement()).isMultiple();
	}

	/**
	 * Method getOptions.
	 * @return List<WebElement>
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#getOptions()
	 */
	public List<WebElement> getOptions() {
		return getSelection(getWebElement()).getOptions();
	}

	/**
	 * Method getAllSelectedOptions.
	 * @return List<WebElement>
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#getAllSelectedOptions()
	 */
	public List<WebElement> getAllSelectedOptions() {
		return getSelection(getWebElement()).getAllSelectedOptions();
	}

	/**
	 * Method getFirstSelectedOption.
	 * @return WebElement
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#getFirstSelectedOption()
	 */
	public WebElement getFirstSelectedOption() {
		return getSelection(getWebElement()).getFirstSelectedOption();
	}

	/**
	 * Method selectByVisibleText.
	 * @param text String
	 * @return IDropdown
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#selectByVisibleText(String)
	 */
	public IDropdown selectByVisibleText(String text) {
		getSelection(getWebElement()).selectByVisibleText(text);
		return this;
	}

	/**
	 * Method selectByIndex.
	 * @param index int
	 * @return IDropdown
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#selectByIndex(int)
	 */
	public IDropdown selectByIndex(int index) {
		getSelection(getWebElement()).selectByIndex(index);
		return this;
	}

	/**
	 * Method selectByValue.
	 * @param value String
	 * @return IDropdown
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#selectByValue(String)
	 */
	public IDropdown selectByValue(String value) {
		getSelection(getWebElement()).selectByValue(value);
		return this;
	}

	/**
	 * Method deselectAll.
	 * @return IDropdown
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#deselectAll()
	 */
	public IDropdown deselectAll() {
		getSelection(getWebElement()).deselectAll();
		return this;
	}

	/**
	 * Method deselectByValue.
	 * @param value String
	 * @return IDropdown
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#deselectByValue(String)
	 */
	public IDropdown deselectByValue(String value) {
		getSelection(getWebElement()).deselectByValue(value);
		return this;
	}

	/**
	 * Method deselectByIndex.
	 * @param index int
	 * @return IDropdown
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#deselectByIndex(int)
	 */
	public IDropdown deselectByIndex(int index) {
		getSelection(getWebElement()).deselectByIndex(index);
		return this;
	}

	/**
	 * Method deselectByVisibleText.
	 * @param text String
	 * @return IDropdown
	 * @see com.stratahealth.test.framework.core.interfaces.webdriver.ISelect#deselectByVisibleText(String)
	 */
	public IDropdown deselectByVisibleText(String text) {
		getSelection(getWebElement()).deselectByVisibleText(text);
		return this;
	}
	
	/** 
	 * Method getSelection.
	 * @param element 
	 * @return Select
	 */
	public Select getSelection(WebElement element) {
		return new Select(element);		
	}

	/**
	 * Method isMultipleWhenVisible.
	 * @return boolean
	 */
	public boolean isMultipleWhenVisible() {
		return isMultipleWhenVisible(getTimeout()); 
	}

	/**
	 * Method isMultipleWhenVisible
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	public boolean isMultipleWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return isMultiple();
	}

	/**
	 * Method getOptionsWhenVisible
	 * @return List<WebElement>
	 */
	public List<WebElement> getOptionsWhenVisible() {
		return getOptionsWhenVisible(getTimeout());
	}

	/**
	 * Method getOptionsWhenVisible
	 * @param waitTimeInSeconds
	 * @return List<WebElement>
	 */
	public List<WebElement> getOptionsWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return getOptions();
	}

	/**
	 * Method getAllSelectedOptionsWhenVisible
	 * @return List<WebElement>
	 */
	public List<WebElement> getAllSelectedOptionsWhenVisible() {
		return getAllSelectedOptionsWhenVisible(getTimeout());
	}

	/**
	 * Method getAllSelectedOptionsWhenVisible
	 * @param waitTimeInSeconds
	 * @return List<WebElement>
	 */
	public List<WebElement> getAllSelectedOptionsWhenVisible(
			int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return getAllSelectedOptions();
	}

	/**
	 * Method getFirstSelectedOptionWhenVisible
	 * @return WebElement
	 */
	public WebElement getFirstSelectedOptionWhenVisible() {
		return getFirstSelectedOptionWhenVisible(getTimeout());
	}

	/**
	 * Method getFirstSelectedOptionWhenVisible
	 * @param waitTimeInSeconds
	 * @return WebElement
	 */
	public WebElement getFirstSelectedOptionWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return getFirstSelectedOption();
	}

	/**
	 * Method selectByVisibleTextWhenVisible
	 * @param text
	 * @return IDropdown
	 */
	public IDropdown selectByVisibleTextWhenVisible(String text) {
		return selectByVisibleTextWhenVisible(text, getTimeout());
	}

	/**
	 * Method selectByVisibleTextWhenVisible
	 * @param text
	 * @param waitTimeInSeconds
	 * @return IDropdown
	 */
	public IDropdown selectByVisibleTextWhenVisible(String text,
			int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return selectByVisibleText(text);
	}

	/**
	 * Method selectByIndexWhenVisible
	 * @param index
	 * @return IDropdown
	 */
	public IDropdown selectByIndexWhenVisible(int index) {
		return selectByIndexWhenVisible(index, getTimeout());
	}

	/**
	 * Method selectByIndexWhenVisible
	 * @param index
	 * @param waitTimeInSeconds
	 * @return IDropdown
	 */
	public IDropdown selectByIndexWhenVisible(int index, int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return selectByIndex(index);
	}

	/**
	 * Method selectByValueWhenVisible
	 * @param value
	 * @return IDropdown
	 */
	public IDropdown selectByValueWhenVisible(String value) {
		return selectByValueWhenVisible(value, getTimeout());
	}

	/**
	 * Method selectByValueWhenVisible
	 * @param value
	 * @param waitTimeInSeconds
	 * @return IDropdown
	 */
	public IDropdown selectByValueWhenVisible(String value,
			int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return selectByValue(value);
	}

	/**
	 * Method deselectAllWhenVisible
	 * @return IDropdown
	 */
	public IDropdown deselectAllWhenVisible() {
		return deselectAllWhenVisible(getTimeout());
	}

	/**
	 * Method deselectAllWhenVisible
	 * @param waitTimeInSeconds
	 * @return IDropdown
	 */
	public IDropdown deselectAllWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return deselectAll();
	}

	/**
	 * Method deselectByValueWhenVisible
	 * @param value
	 * @return IDropdown
	 */
	public IDropdown deselectByValueWhenVisible(String value) {
		return deselectByValueWhenVisible(value, getTimeout());
	}

	/**
	 * Method deselectByValueWhenVisible
	 * @param value
	 * @param waitTimeInSeconds
	 * @return IDropdown
	 */
	public IDropdown deselectByValueWhenVisible(String value,
			int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return deselectByValue(value);
	}

	/**
	 * Method deselectByIndexWhenVisible
	 * @param index
	 * @return IDropdown
	 */
	public IDropdown deselectByIndexWhenVisible(int index) {
		return deselectByIndexWhenVisible(index, getTimeout());
	}

	/**
	 * Method deselectByIndexWhenVisible
	 * @param index
	 * @param waitTimeInSeconds
	 * @return IDropdown
	 */
	public IDropdown deselectByIndexWhenVisible(int index, int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return deselectByIndex(index);
	}

	/**
	 * Method deselectByVisibleTextWhenVisible
	 * @param text
	 * @return IDropdown
	 */
	public IDropdown deselectByVisibleTextWhenVisible(String text) {
		return deselectByVisibleTextWhenVisible(text, getTimeout());
	}

	/**
	 * Method deselectByVisibleTextWhenVisible
	 * @param text
	 * @param waitTimeInSeconds
	 * @return IDropdown
	 */
	public IDropdown deselectByVisibleTextWhenVisible(String text,
			int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return deselectByVisibleText(text);
	}
}
