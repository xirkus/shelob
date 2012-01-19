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

import java.util.Collection;
import java.util.List;

import org.openqa.selenium.WebElement;

import shelob.core.LookUp;
import shelob.core.interfaces.page.IPage;
import shelob.core.interfaces.webdriver.IWaitable;


/**
 * @author melllaguno
 * @version $Revision : 1.0 $
 *
 * Separate out the IElement interface so that it can evolve independently of WebElement
 */
public interface IElementCore extends IWaitable {

	/**
	 * @return WebElement returns underlying WebElement; needed for Select WebDriver objects
	 */
	WebElement getWebElement();
	
	/**
	 * @return List<WebElement> returns list of WebElements; need for finding multiples of objects
	 */
	List<WebElement> getWebElements();
	
	/**
	 * Convenience method for sendKeys() - which is not that intuitive
	 * @param arg0 CharSequence[]
	 * @return IElement fluent interface; this
	 */
	IElement type(CharSequence... arg0);
	
	/**
	 * @return returns the string locator associated with the Element 
	 */
	String getLocator();
	
	/**
	 * @return returns the LookUp enumeration associated with the Element 
	 */
	LookUp getLookUpType();
	
	/**
	 * @return a flag which indicates whether the element is valid 
	 */
	boolean isValid();
	
	/**
	 * @return a flag which indicates whether the element has an associated label
	 */
	boolean hasLabel();
	
	/**
	 * @return the label associated with the Element 
	 */
	String getLabel();
	
	/**
	 * helper method to set the Element with the Required property
	 * @return IElement fluent interface; this 
	 */
	IElement setRequired();
	
	/**
	 * @return a flag which indicates whether the element is required 
	 */
	boolean isRequired();
	
	/**
	 * helper method to set the Element locator relative to a parent Element
	 * @param parent IElement
	 * @return IElement fluent interface; this
	 */
	IElement setRelativeToParent(IElement parent);
	
	/**
	 * @return a flag which indicates whether the element locator is relative to a parent element 
	 */
	boolean isRelativeToParent();
	
	/**
	 * @param localization adds localization to an element
	 * @return IElement fluent interface; this
	 */
	IElement addLocalization(String localization);
	
	/**
	 * @return a collection of localizations 
	 */
	Collection<String> getLocalizations();
	
	/**
	 * @return a flag which indicates whether the element has any associated localizations 
	 */
	boolean hasLocalizations();
	
	/**
	 * @param <T> the Type of page linked by this element
	 * @return the Page of the type passed in.
	 */
	<T extends IPage> T goToLink();
	
	/**
	 * Convenience Method : preserves fluency by not having to cast the return value.
	 * 
	 * @param <T> the Type of page linked by this element
	 * @param type the class type of the Page
	 * @return the Page of the type passed in.
	 */
	<T extends IPage> T goToLink(Class<T> type);
	
	/**
	 * @return a flag which indicates whether the element has an associated link
	 */
	boolean hasLink();
	
	
	<T extends IPage> T getLink();
	
	<T extends IPage> T getLink(Class<T> type);
	/** 
	 * NOTE : helper method to mark the locator as an xpath expression
	 * 
	 * We rely on the fact that WebDriver WebElements cannot be cached between
	 * page refreshes (which happens quite a bit in our application). We can then
	 * set an xpath expression with a String.format() substitution to find
	 * elements dynamically by passing in a differentiating identifier.
	
	 * By setting this property, we ensure that the dynamic xpath expression is
	 * used to resolve the element; no need to use a dedicated Finder to do the
	 * resolution. All that needs to happen is to use the setLocatorIdentifier()
	 * on the element.
	 * 
	 * @return IElement fluent interface; this
	 */
	IElement setIsTemplate();
	
	/**
	 * @return boolean a flag which indicates whether the element is a template
	 */
	boolean isTemplate();
	
	/**
	 * Sets the identifier component of the element when the locator is an 
	 * xpath expression. See above for usage.
	 * 
	 * @param identifier the string expression used to qualify the element in the xpath expression
	 * @return IElement fluent interface; this
	 */
	IElement setTemplateIdentifier(String identifier);
	
	/**
	 * Sets the identifiers for the element when the locator is an xpath expression
	 * 
	 * @param identifiers the string expressions used to qualify the element in the xpath expression 
	 * @return IElement fluent interface; this
	 */
	IElement setTemplateIdentifiers(String...identifiers);
	
	
	Collection<String> getTemplateIdentifiers();
	
	/**
	 * Sets a pause period where execution is put to sleep
	 * 
	 * @param milliseconds time to pause execution
	 * @return IElement fluent interface; this
	 */
	IElement pause(long milliseconds);
	
	/**
	 * Waits until the element is visible
	 * 
	 * @param waitTimeInSeconds the amount of time to wait in seconds
	 * @return IElement fluent interface; this
	 */
	IElement waitUntilVisible(long waitTimeInSeconds);
	
}
