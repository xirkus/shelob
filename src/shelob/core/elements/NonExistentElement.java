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
package shelob.core.elements;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

import shelob.core.LookUp;
import shelob.core.exceptions.NonExistentWebElementException;
import shelob.core.interfaces.elements.IElement;
import shelob.core.interfaces.elements.IElementCore;
import shelob.core.interfaces.elements.IWhenVisible;
import shelob.core.interfaces.page.IPage;


/**
 * @author melllaguno
 *
 * Null Object : NonExistentElement that is used when a corresponding WebElement is not found
 * @version $Revision: 1.0 $
 */
@Immutable
public final class NonExistentElement implements WebElement, IElementCore, IWhenVisible {

	private final IElement caller;
	
	/**
	 * NonExistentElement Default Constructor
	 * 
	 * @param caller the element which generated the NonExistentElement
	 */
	public NonExistentElement(IElement caller) {
		this.caller = checkNotNull(caller);
	}
	
	/**
	 * delegate method
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	public void clear() {
		throw new NonExistentWebElementException("Attempt to call clear() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @see org.openqa.selenium.WebElement#click()
	 */
	public void click() {
		throw new NonExistentWebElementException("Attempt to call click() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @param arg0 By
	 * @return WebElement
	 * @see org.openqa.selenium.WebElement#findElement(By)
	 */
	public WebElement findElement(By arg0) {
		throw new NonExistentWebElementException("Attempt to call findElement() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @param arg0 By
	 * @return List<WebElement>
	 * @see org.openqa.selenium.WebElement#findElements(By)
	 */
	public List<WebElement> findElements(By arg0) {
		throw new NonExistentWebElementException("Attempt to call findElements() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @param arg0 String
	 * @return String
	 * @see org.openqa.selenium.WebElement#getAttribute(String)
	 */
	public String getAttribute(String arg0) {
		throw new NonExistentWebElementException("Attempt to call getAttribute() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return String
	 * @see org.openqa.selenium.WebElement#getTagName()
	 */
	public String getTagName() {
		throw new NonExistentWebElementException("Attempt to call getTagName() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return String
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	public String getText() {
		throw new NonExistentWebElementException("Attempt to call getText() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return boolean
	 * @see org.openqa.selenium.WebElement#isEnabled()
	 */
	public boolean isEnabled() {
		throw new NonExistentWebElementException("Attempt to call isEnabled() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return boolean
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	public boolean isSelected() {
		throw new NonExistentWebElementException("Attempt to call isSelected() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @param arg0 CharSequence[]
	 * @see org.openqa.selenium.WebElement#sendKeys(CharSequence[])
	 */
	public void sendKeys(CharSequence... arg0) {
		throw new NonExistentWebElementException("Attempt to call sendKeys() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return IElement fluent interface; this;
	 * @param arg0 CharSequence[]
	 */
	public IElement type(CharSequence... arg0) {
		throw new NonExistentWebElementException("Attempt to call type() on a WebElement that cannot be found." + caller.toString());
	}
	
	/**
	 * delegate method
	 * @see org.openqa.selenium.WebElement#submit()
	 */
	public void submit() {
		throw new NonExistentWebElementException("Attempt to call submit() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return boolean
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#isValid()
	 */
	public boolean isValid() {
		return false;
	}

	/**
	 * delegate method
	 * @return IPage
	 * @see com.stratahealth.test.framework.core.interfaces.IHasParentPage#getParentPage()
	 */
	public IPage getParentPage() {
		throw new NonExistentWebElementException("Attempt to call getParentPage() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return String
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#getLocator()
	 */
	public String getLocator() {
		throw new NonExistentWebElementException("Attempt to call getLocator() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return LookUp
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#getLookUpType()
	 */
	public LookUp getLookUpType() {
		throw new NonExistentWebElementException("Attempt to call getLookUpType() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return boolean
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#hasLabel()
	 */
	public boolean hasLabel() {
		throw new NonExistentWebElementException("Attempt to call hasLabel() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return String
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#getLabel()
	 */
	public String getLabel() {
		throw new NonExistentWebElementException("Attempt to call getLabel() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return IElement fluent interface; this
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#setRequired()
	 */
	public IElement setRequired() {
		throw new NonExistentWebElementException("Attempt to call setRequired() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return boolean
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#isRequired()
	 */
	public boolean isRequired() {
		throw new NonExistentWebElementException("Attempt to call isRequired() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @param parent IElement
	 * @return IElement fluent interface; this
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#setRelativeToParent(IElement)
	 */
	public IElement setRelativeToParent(IElement parent) {
		throw new NonExistentWebElementException("Attempt to call setRelativeToParent() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return boolean
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#isRelativeToParent()
	 */
	public boolean isRelativeToParent() {
		throw new NonExistentWebElementException("Attempt to call isRelativeToParent() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @param localization String
	 * @return IElement fluent interface; this
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#addLocalization(String)
	 */
	public IElement addLocalization(String localization) {
		throw new NonExistentWebElementException("Attempt to call addLocalization() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return Collection<String>
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#getLocalizations()
	 */
	public Collection<String> getLocalizations() {
		throw new NonExistentWebElementException("Attempt to call getLocalizations() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * delegate method
	 * @return boolean
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#hasLocalizations()
	 */
	public boolean hasLocalizations() {
		throw new NonExistentWebElementException("Attempt to call hasLocalizations() on a WebElement that cannot be found." + caller.toString());
	}
	
	/**
	 * delegate method
	 * @return String
	 */
	@Override
	public String toString() {
		return "Non-existent Element.";
	}

	/**
	 * Method hasLink.
	 * @return boolean
	 */
	public boolean hasLink() {
		throw new NonExistentWebElementException("Attmept to call hasLink() on a WebElement that cannot be found." + caller.toString());
	}
	
	/**
	 * Method goToLink.
	 * @return T
	 */
	public <T extends IPage> T goToLink() { // $codepro.audit.disable methodJavadoc, overloadedMethods
		throw new NonExistentWebElementException("Attempt to call goToLink() on a WebElement that cannot be found." + caller.toString());
	}
	
	/**
	 * Method goToLink
	 * @param <T>
	 * @param type
	 * @return T
	 */
	public <T extends IPage> T goToLink(Class<T> type) { // $codepro.audit.disable overloadedMethods
		throw new NonExistentWebElementException("Attempt to call goToLink() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method setLocatorAsExpression
	 * @return IElement fluent interface; this
	 */
	public IElement setIsTemplate() {
		throw new NonExistentWebElementException("Attempt to call setLocatorAsExpress() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method isTemplate()
	 * @return boolean
	 */
	public boolean isTemplate() {
		throw new NonExistentWebElementException("Attempt to call isTemplate() on a WebElement that cannot be found." + caller.toString());
	}
	
	/**
	 * Method setLocatorIdentifier
	 * @param identifier
	 * @return IElement fluent interface; this
	 */
	public IElement setTemplateIdentifier(String identifier) {
		throw new NonExistentWebElementException("Attempt to call setLocatorIdentifier() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getElement
	 * @return  WebElement
	 */
	public WebElement getElement() {
		throw new NonExistentWebElementException("Attempt to call getElement() on a WebElement that cannot be found." + caller.toString());
	}
	
	/**
	 * Method pause
	 * @param milliseconds
	 * @return IElement fluent interface; this
	 */
	public IElement pause(long milliseconds) {
		throw new NonExistentWebElementException("Attempt to call pause() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method waitUntilVisible
	 * @param waitTimeInSeconds
	 * @return IElement fluent interface; this
	 */
	public IElement waitUntilVisible(long waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call waitUntilVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getWaitHelper
	 * @param waitTimeInSeconds
	 * @return Wait<WebDriver> the Wait Helper object
	 */
	public Wait<WebDriver> getWaitHelper(long waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call getWaitHelper() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method setTimeout
	 * @param waitTimeInSeconds
	 * @return IElement
	 */
	public IElement setTimeout(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call setTimeout() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getTimeout
	 * @return IElement
	 */
	public int getTimeout() {
		throw new NonExistentWebElementException("Attempt to call getTimeout() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method clearWhenVisible
	 * @return IElement
	 */
	public IElement clearWhenVisible() {
		throw new NonExistentWebElementException("Attempt to call clearWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method clearWhenVisible
	 * @param waitTimeInSeconds
	 * @return IElement
	 */
	public IElement clearWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call clearWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method clickWhenVisible
	 * @return IElement
	 */
	public IElement clickWhenVisible() {
		throw new NonExistentWebElementException("Attempt to call clickWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method clickWhenVisible
	 * @param waitTimeInSeconds
	 * @return IElement
	 */
	public IElement clickWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call clickWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method findElementWhenVisible
	 * @param arg0
	 * @return WebElement
	 */
	public WebElement findElementWhenVisible(By arg0) {
		throw new NonExistentWebElementException("Attempt to call findElementWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method findElementWhenVisible
	 * @param arg0
	 * @param waitTimeInSeconds
	 * @return WebElement
	 */
	public WebElement findElementWhenVisible(By arg0, int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call findElementWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method findElementsWhenVisible
	 * @param arg0
	 * @return List<WebElement>
	 */
	public List<WebElement> findElementsWhenVisible(By arg0) {
		throw new NonExistentWebElementException("Attempt to call findElementsWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method findElementsWhenVisible
	 * @param arg0
	 * @param waitTimeInSeconds
	 * @return List<WebElement>
	 */
	public List<WebElement> findElementsWhenVisible(By arg0, int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call findElementsWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getAttributeWhenVisible
	 * @param arg0
	 * @return String
	 */
	public String getAttributeWhenVisible(String arg0) {
		throw new NonExistentWebElementException("Attempt to call getAttributeWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getAttributeWhenVisible
	 * @param arg0
	 * @param waitTimeInSeconds
	 * @return String
	 */
	public String getAttributeWhenVisible(String arg0, int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call getAttributeWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getTagNameWhenVisible
	 * @return String
	 */
	public String getTagNameWhenVisible() {
		throw new NonExistentWebElementException("Attempt to call getTagNameWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getTagNameWhenVisible
	 * @param waitTimeInSeconds
	 * @return String
	 */
	public String getTagNameWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call getTagNameWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getTextWhenVisible
	 * @return String
	 */
	public String getTextWhenVisible() {
		throw new NonExistentWebElementException("Attempt to call getTextWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getTextWhenVisible
	 * @param waitTimeInSeconds
	 * @return String
	 */
	public String getTextWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call getTextWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method isEnabledWhenVisible
	 * @return boolean
	 */
	public boolean isEnabledWhenVisible() {
		throw new NonExistentWebElementException("Attempt to call isEnabledWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method isEnabledWhenVisible
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	public boolean isEnabledWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call isEnabledWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method isSelectedWhenVisible
	 * @return boolean
	 */
	public boolean isSelectedWhenVisible() {
		throw new NonExistentWebElementException("Attempt to call isSelectedWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method isSelectedWhenVisible
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	public boolean isSelectedWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call isSelectedWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method sendKeysWhenVisible
	 * @param arg0
	 * @return IElement
	 */
	public IElement sendKeysWhenVisible(CharSequence... arg0) {
		throw new NonExistentWebElementException("Attempt to call sendKeysWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method sendKeysWhenVisible
	 * @param waitTimeInSeconds
	 * @param arg0
	 * @return IElement
	 */
	public IElement sendKeysWhenVisible(int waitTimeInSeconds, CharSequence... arg0) {
		throw new NonExistentWebElementException("Attempt to call sendKeysWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method submitWhenVisible
	 * @return IElement
	 */
	public IElement submitWhenVisible() {
		throw new NonExistentWebElementException("Attmept to call submitWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method submitWhenVisible
	 * @param waitTimeInSeconds
	 * @return IElement
	 */
	public IElement submitWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call submitWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method setTemplateIdentifiers
	 * @param identifiers
	 * @return IElement
	 */
	public IElement setTemplateIdentifiers(String... identifiers) {
		throw new NonExistentWebElementException("Attempt to call setTemplateIdentifiers() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method setMultiplesLocator
	 * @param locator
	 * @return IElement
	 */
	public IElement setMultiplesLocator(String locator) {
		throw new NonExistentWebElementException("Attempt to call setMultiplesLocator() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getMultiplesLocator
	 * @return String
	 */
	public String getMultiplesLocator() {
		throw new NonExistentWebElementException("Attempt to call getMultiplesLocator() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method hasMultiples
	 * @return boolean
	 */
	public boolean hasMultiples() {
		throw new NonExistentWebElementException("Attempt to call hasMultiples() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method typeWhenVisible
	 * @param arg0
	 * @return IElement
	 */
	public IElement typeWhenVisible(CharSequence... arg0) {
		throw new NonExistentWebElementException("Attempt to call typeWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method typeWhenVisible
	 * @param waitTimeInSeconds
	 * @param arg0
	 * @return IElement
	 */
	public IElement typeWhenVisible(int waitTimeInSeconds, CharSequence... arg0) {
		throw new NonExistentWebElementException("Attempt to call typeWhenVisible() on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getTextWhenVisible
	 * @return Collection<String>
	 */
	public Collection<String> getTemplateIdentifiers() {
		throw new NonExistentWebElementException("Attempt to call getTemplateIdentifiers on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getCssValue
	 * @param arg0
	 * @return String
	 */
	public String getCssValue(String arg0) {
		throw new NonExistentWebElementException("Attempt to call getCssValue on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getLocation
	 * @return Point
	 */
	public Point getLocation() {
		throw new NonExistentWebElementException("Attempt to call getLocation on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getSize
	 * @return Dimension
	 */
	public Dimension getSize() {
		throw new NonExistentWebElementException("Attempt to call getSize on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method isDisplayed
	 * @return boolean
	 */
	public boolean isDisplayed() {
		throw new NonExistentWebElementException("Attempt to call isDisplayed on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method isDisplayedWhenVisible
	 * @return boolean
	 */
	public boolean isDisplayedWhenVisible() {
		throw new NonExistentWebElementException("Attempt to call isDisplayedWhenVisible on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method isDisplayedWhenVisible
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	public boolean isDisplayedWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call isDisplayedWhenVisible on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getCssValueWhenVisible
	 * @param value
	 * @return String
	 */
	public String getCssValueWhenVisible(String value) {
		throw new NonExistentWebElementException("Attempt to call getCssValueWhenVisible on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getCssValueWhenVisible
	 * @param value
	 * @param waitTimeInSeconds
	 * @return String
	 */
	public String getCssValueWhenVisible(String value, int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call getCssValueWhenVisible on a WebElement that cannot be found." + caller.toString());
	}

	/**
	 * Method getLocationWhenVisible
	 * @return Point
	 */
	public Point getLocationWhenVisible() {
		throw new NonExistentWebElementException("Attempt to call getLocationWhenVisible on a WebElement that cannot be found" + caller.toString());
	}

	/**
	 * Method getLocationWhenVisible
	 * @param waitTimeInSeconds
	 * @return Point
	 */
	public Point getLocationWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call getLocationWhenVisible on a WebElement that cannot be found" + caller.toString());
	}

	/**
	 * Method getSizeWhenVisible
	 * @return Dimension
	 */
	public Dimension getSizeWhenVisible() {
		throw new NonExistentWebElementException("Attempt to call getSizeWhenVisible on a WebElement that cannot be found" + caller.toString());
	}

	/**
	 * Method getSizeWhenVisible
	 * @param waitTimeInSeconds
	 * @return Dimension
	 */
	public Dimension getSizeWhenVisible(int waitTimeInSeconds) {
		throw new NonExistentWebElementException("Attempt to call getSizeWhenVisible on a WebElement that cannot be found" + caller.toString());
	}
}
