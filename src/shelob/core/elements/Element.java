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

// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.preferInterfacesToAbstractClasses, fieldJavadoc
package shelob.core.elements;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import shelob.core.LookUp;
import shelob.core.exceptions.InsufficientArgumentsException;
import shelob.core.exceptions.NonExistentWebElementException;
import shelob.core.interfaces.IOpensNewWindow;
import shelob.core.interfaces.elements.IElement;
import shelob.core.interfaces.page.IPage;



/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 * 
 * Element Abstract Template; used as the basis to construct primitive Elements
 * 
 * We use interface inheritance and composition to delegate requests to
 * the WebDriver WebElement.
 * 
 * WARNING : Since WebDriver is NOT ThreadSafe, no Elements can be considered ThreadSafe
 * 			 Oh well ....
 */
public abstract class Element implements IElement {

	private final IPage parent;
	private final String locator;
	private final LookUp lookup;
	private final String label;
	private final IPage link;

	@GuardedBy("this")
	private final Collection<String> localizations; // $codepro.audit.disable fieldAccessProtection
	@GuardedBy("this")
	private final Collection<String> templateIdentifiers;

	@GuardedBy("this")
	private boolean required;
	@GuardedBy("this")
	private boolean isTemplate;
	@GuardedBy("this")
	private int waitTimeInSeconds;
	@GuardedBy("this")
	private IElement parent_element;
	@GuardedBy("this")
	private String multiplesLocator;
	
	
	@GuardedBy("this")
	private WebElement element; // $codepro.audit.disable fieldAccessProtection

	/**
	 * NOTE : We lazy-initialize the internal WebElement on method access to
	 * ensure that we can specify the expected page layout without have to
	 * resolve the elements first-hand.
	 * 
	 * @param parent
	 *            The Page associated with this element
	 * @param lookup
	 *            the Lookup strategy associated with locating the element
	 * @param locator
	 *            the locator string used with the Lookup strategy
	 * @param label
	 *            the label associated with this element
	 * @param link
	 *            the Page this element links to
	 */
	protected Element(IPage parent, LookUp lookup, String locator,
			String label, @Nullable IPage link) {

		this.parent = checkNotNull(parent);
		this.locator = checkNotNull(locator);
		this.lookup = checkNotNull(lookup);

		// optional parameters
		this.label = label;
		this.link = link;

		required = false;
		isTemplate = false;
		parent_element = null;

		localizations = new ArrayList<String>();
		templateIdentifiers = new ArrayList<String>();
	}

	/**
	 * @return WebElement the WebElement wrapped by this Element object
	 */
	synchronized public WebElement getElement() {
		initializeWebElement();
		return element;
	}

	/**
	 * We resolve the element when it is first accessed - lazy initialization
	 */
	private void initializeWebElement() {

		// WARNING : Initially, we tried caching the WebElement, but
		// unfortunately
		// the WebDriver's cache becomes invalid when a page is refreshed;
		// hence, we need to resolve the WebElement FOR EACH REQUEST
		element = getWebElement();
	}

	/**
	 * Finds the WebElement using the LookUp strategy and locator string
	 * 
	 * @return the WebElement resolved through WebDriver
	 */
	private WebElement getWebElement() {
		
		if (isTemplate && getTemplateIdentifiers().size() == 0)
			throw new NonExistentWebElementException(
					"An identifier must be set using setTemplateIdentifier() for any element behaving as a template.");

		try {

			switch (lookup) {

			case ByClassName:
				return parent.getDriver().findElement(By.className(getLocator()));
			case ByCSSSelector:
				return parent.getDriver().findElement(By.cssSelector(getLocator()));
			case ById:
				return parent.getDriver().findElement(By.id(getLocator()));
			case ByLinkText:
				return parent.getDriver().findElement(By.linkText(getLocator()));
			case ByName:
				return parent.getDriver().findElement(By.name(getLocator()));
			case ByPartialLinkText:
				return parent.getDriver().findElement(By.partialLinkText(getLocator()));
			case ByTagName:
				return parent.getDriver().findElement(By.tagName(getLocator()));
			case ByXpath:
				return parent.getDriver().findElement(By.xpath(getLocator()));
			}
		} catch (NoSuchElementException e) {
			return new NonExistentElement(this);
		} catch (WebDriverException e) {
			throw new NonExistentWebElementException(e.getMessage() + this.toString());
		}

		return new NonExistentElement(this);
	}

	/**
	 * delegates clear method call
	 * 
	 * @return IElement fluent interface; this
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	synchronized public IElement clear() {
		initializeWebElement();
		element.clear();
		return this;
	}

	/**
	 * delegates click method call
	 * 
	 * @return IElement fluent interface; this
	 * @see org.openqa.selenium.WebElement#click()
	 */
	synchronized public IElement click() {
		initializeWebElement();
		element.click();
		return this;
	}

	/**
	 * delegates findElement method call
	 * 
	 * @param arg0
	 * @return WebElement 
	 * @see org.openqa.selenium.WebElement#findElement(By)
	 */
	synchronized public WebElement findElement(By arg0) {
		initializeWebElement();
		return element.findElement(arg0);
	}

	/**
	 * delegates findElements method call
	 * 
	 * @param arg0
	 * @return List<WebElement> 
	 * @see org.openqa.selenium.WebElement#findElements(By)
	 */
	synchronized public List<WebElement> findElements(By arg0) {
		initializeWebElement();
		return element.findElements(arg0);
	}

	/**
	 * delegates getAttribute method call
	 * 
	 * @param arg0
	 * @return String 
	 * @see org.openqa.selenium.WebElement#getAttribute(String)
	 */
	synchronized public String getAttribute(String arg0) {
		initializeWebElement();
		return element.getAttribute(arg0);
	}

	/**
	 * delegates getTagName method call
	 * 
	 * @return String 
	 * @see org.openqa.selenium.WebElement#getTagName()
	 */
	synchronized public String getTagName() {
		initializeWebElement();
		return element.getTagName();
	}

	/**
	 * delegates getText method call
	 * 
	 * @return String 
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	synchronized public String getText() {
		initializeWebElement();
		return element.getText();
	}

	/**
	 * delegates isEnabled method call
	 * 
	 * @return boolean 
	 * @see org.openqa.selenium.WebElement#isEnabled()
	 */
	synchronized public boolean isEnabled() {
		initializeWebElement();
		
		try {
			return element.isEnabled();	
		} catch (NonExistentWebElementException e) {
			return isValid();
		} catch (ElementNotVisibleException e) {
			return false;
		}			
	}

	/**
	 * delegates isSelected method call
	 * 
	 * @return boolean 
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	synchronized public boolean isSelected() {
		initializeWebElement();
		return element.isSelected();
	}

	/**
	 * delegates sendKeys method call
	 * 
	 * @param arg0
	 * @return IElement fluent interface; this
	 * @see org.openqa.selenium.WebElement#sendKeys(CharSequence[])
	 */
	synchronized public IElement sendKeys(CharSequence... arg0) {
		initializeWebElement();
		element.sendKeys(arg0);
		return this;
	}

	/**
	 * convenience method
	 * 
	 * @param arg0
	 * @return IElement fluent interface; this
	 */
	public IElement type(CharSequence... arg0) {
		this.sendKeys(arg0);
		return this;
	}

	/**
	 * delegates submit method call
	 * 
	 * @return IElement fluent interface; this
	 * @see org.openqa.selenium.WebElement#submit()
	 */
	synchronized public IElement submit() {
		initializeWebElement();
		element.submit();
		return this;
	}

	/**
	 * Method isValid.
	 * 
	 * @return boolean 
	 */
	synchronized public boolean isValid() {
		initializeWebElement();
		if (element instanceof NonExistentElement)
			return false;
		return true;
	}

	/**
	 * Method getParentPage.
	 * 
	 * @return IPage 
	 */
	public IPage getParentPage() {
		return parent;
	}

	/**
	 * Method getLocator.
	 * 
	 * @return String 
	 */
	public String getLocator() {

		final StringBuilder compoundLocator = new StringBuilder();

		if (isRelativeToParent())
			compoundLocator.append(parent_element.getLocator());

		if (isTemplate()) {
			try {
				compoundLocator.append(String.format(locator,
						templateIdentifiers.toArray()));
			} catch (MissingFormatArgumentException e) {
				throw new InsufficientArgumentsException(
						String.format(
								"Either the type or quantity of arguments supplied for the template [%s] is incorrect -> %s  : %s",
								locator, templateIdentifiers, e.getMessage()));
			}
		} else
			compoundLocator.append(locator);

		return compoundLocator.toString();
	}

	/**
	 * Method getLookUpType.
	 * 
	 * @return LookUp 
	 */
	public LookUp getLookUpType() {
		return lookup;
	}

	/**
	 * Method hasLabel.
	 * 
	 * @return boolean 
	 */
	public boolean hasLabel() {
		return label != null;
	}

	/**
	 * Method getLabel.
	 * 
	 * @return String 
	 */
	public String getLabel() {

		if (label == null)
			throw new NullPointerException(
					"The label for this object was not set through it's Builder.");
		return label;
	}

	/**
	 * Method setRequired.
	 * 
	 * @return IElement fluent interface; this
	 */
	synchronized public IElement setRequired() {
		required = true;
		return this;
	}

	/**
	 * Method isRequired.
	 * 
	 * @return boolean 
	 */
	synchronized public boolean isRequired() {
		return required;
	}

	/**
	 * Method setRelativeToParent.
	 * 
	 * @param parent
	 * @return IElement fluent interface; this
	 */
	synchronized public IElement setRelativeToParent(IElement parent) {
		parent_element = parent;
		return this;
	}

	/**
	 * Method isRelativeToParent.
	 * 
	 * @return boolean 
	 */
	synchronized public boolean isRelativeToParent() {
		return parent_element != null;
	}

	/**
	 * Method addLocalization.
	 * 
	 * @param localization
	 * @return IElement fluent interface; this
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#addLocalization(String)
	 */
	synchronized public IElement addLocalization(String localization) {
		localizations.add(localization);
		return this;
	}

	/**
	 * Method getLocalizations.
	 * 
	 * NOTE : We return a defensive copy
	 * 
	 * @return Collection<String> 
	 */
	synchronized public Collection<String> getLocalizations() {

		// defensive copy
		final List<String> completeLocalizations = new ArrayList<String>(
				localizations);
		completeLocalizations.add(label);

		return completeLocalizations;
	}

	/**
	 * Method hasLocalizations.
	 * 
	 * @return boolean * @see
	 *         com.stratahealth.test.core.interfaces.elements.IElement
	 *         #hasLocalizations()
	 */
	synchronized public boolean hasLocalizations() {
		return localizations.size() > 0;
	}

	/**
	 * @return String friendly string representation of this object
	 */
	@Override
	public String toString() {

		final StringBuilder s = new StringBuilder().append("Parent Page : ")
				.append(parent.getPageTitle()).append(" Element : ")
				.append(this.getClass().getName()).append(" LookUp : ")
				.append(lookup.name()).append(" Locator : ")
				.append(this.getLocator()).append(" HasLocalizations : ")
				.append(this.hasLocalizations()).append(" HasLabel : ")
				.append(this.hasLabel()).append(" IsRelativeToParent : ")
				.append(this.isRelativeToParent()).append(" IsTemplate : ")
				.append(isTemplate);

		return s.toString();
	}

	/**
	 * Method hasLink()
	 * 
	 * @return boolean
	 */
	public boolean hasLink() {
		return link != null;
	}

	/**
	 * Method goToLink.
	 * 
	 * @return T extends IPage
	 */
	@SuppressWarnings("unchecked")
	public <T extends IPage> T goToLink() { // $codepro.audit.disable
											// methodJavadoc

		goToLinkImpl();
		return (T) link;
	}

	public <T extends IPage> T goToLink(Class<T> type) { // $codepro.audit.disable
															// methodJavadoc,
															// overloadedMethods

		goToLinkImpl();
		return type.cast(link);
	}

	private void goToLinkImpl() { // $codepro.audit.disable methodJavadoc

		if (link == null)
			throw new NullPointerException(
					"The linkTo for this object was not set through it's Builder.");

		// WARNING : Naive Implementation : assumes that there are only 2 open
		// windows at any given time.
		//
		// Unfortunately, the use of GUIDs for the window handles precludes the
		// ability to differentiate between windows. The only way to tell is to query 
		// the handles set before the new window is opened.
		//
		// Ideally, it would be cleaner if the getWindowHandle exposed events
		// that you could listen to associate the newly created handle to a specific window.

		String parentWindow = getParentPage().getParameters().getDriver()
				.getWindowHandle();

		this.click().pause(2000);

		if (link instanceof IOpensNewWindow<?>) {

			Set<String> handles = getParentPage().getParameters().getDriver()
					.getWindowHandles();

			for (String s : handles) {

				if (s.equals(parentWindow))
					continue;
				else {
					((IOpensNewWindow<?>) link).setWindowHandle(s);
					break;
				}
			}
		}
	}

	/**
	 * Method setLocatorAsExpression.
	 * 
	 * @return IElement fluent interface; this
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#setIsTemplate()
	 */
	synchronized public IElement setIsTemplate() {
		isTemplate = true;
		return this;
	}

	/**
	 * Method isTemplate.
	 * 
	 * @return boolean a flag which indicates whether this element is a template
	 */
	synchronized public boolean isTemplate() {
		return isTemplate;
	}

	/**
	 * Method setLocatorIdentifier.
	 * 
	 * @param identifier
	 * @return IElement fluent interface; this
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#setTemplateIdentifier(String)
	 */
	synchronized public IElement setTemplateIdentifier(String identifier) {

		templateIdentifiers.clear();
		templateIdentifiers.add(formatIdentifier(identifier)); // 
		return this;
	}

	/**
	 * Method setTemplateIdentifiers
	 * 
	 * @param identifiers
	 * @return IElement fluent interface, this;
	 * @see com.stratahealth.test.framework.core.interfaces.elements.IElement#setTemplateIdentifiers(String...)
	 */
	synchronized public IElement setTemplateIdentifiers(String... identifiers) {

		templateIdentifiers.clear();
		for (String id : identifiers)
			templateIdentifiers.add(formatIdentifier(id));

		return this;
	}
	
	synchronized public Collection<String> getTemplateIdentifiers() {
		
		// HACK : should traverse parents to see if template identifiers are set on the closest parent.
		if (isTemplate && templateIdentifiers.size() == 0 && parent_element != null && parent_element.isTemplate() ) {
			for(String s : parent_element.getTemplateIdentifiers())
				templateIdentifiers.add(formatIdentifier(s));
		}	 
		
		// return defensive copy
		return new ArrayList<String>(templateIdentifiers);
	}

	private String formatIdentifier(String id) {
		return id.trim(); //.replace("'", "\u2019");
	}
	
	/**
	 * @param milliseconds
	 * @return IElement fluent interface; this
	 */
	public IElement pause(long milliseconds) {

		try {
			Thread.sleep(milliseconds); // $codepro.audit.disable disallowSleepUsage
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * @param waitTimeInSeconds
	 * @return IElement fluent interface; this
	 */
	public IElement waitUntilVisible(long waitTimeInSeconds) {
		
		initializeWebElement();
		getWaitHelper(waitTimeInSeconds).until(elementIsVisible());	
		return this;
	}

	/**
	 * @param waitTimeInSeconds
	 * @return Wait<WebDriver> wait object helper
	 */
	public Wait<WebDriver> getWaitHelper(long waitTimeInSeconds) {
		return new WebDriverWait(this.getParentPage().getDriver(),
				waitTimeInSeconds);
	}

	/**
	 * @param waitTimeInSeconds
	 * @return IElement
	 */
	synchronized public IElement setTimeout(int waitTimeInSeconds) {
		this.waitTimeInSeconds = waitTimeInSeconds;
		return this;
	}

	/**
	 * @return int
	 */
	synchronized public int getTimeout() {
		return waitTimeInSeconds;
	}

	/**
	 * @return IElement
	 */
	public IElement clearWhenVisible() {
		return clearWhenVisible(getTimeout());
	}

	/**
	 * @param waitTimeInSeconds
	 * @return IElement
	 */
	public IElement clearWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		clear();
		return this;
	}

	/**
	 * @return IElement
	 */
	public IElement clickWhenVisible() {
		return clickWhenVisible(getTimeout());
	}

	/**
	 * @param waitTimeInSeconds
	 * @return IElement
	 */
	public IElement clickWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		click();
		return this;
	}

	/**
	 * @param arg0
	 * @return WebElement
	 */
	public WebElement findElementWhenVisible(By arg0) {
		return findElementWhenVisible(arg0, getTimeout());
	}

	/**
	 * @param arg0
	 * @param waitTimeInSeconds
	 * @return WebElement
	 */
	public WebElement findElementWhenVisible(By arg0, int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return findElement(arg0);
	}

	/**
	 * @param arg0
	 * @return List<WebElement>
	 */
	public List<WebElement> findElementsWhenVisible(By arg0) {
		return findElementsWhenVisible(arg0, getTimeout());
	}

	/**
	 * @param arg0
	 * @param waitTimeInSeconds
	 * @return List<WebElement>
	 */
	public List<WebElement> findElementsWhenVisible(By arg0,
			int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return findElements(arg0);
	}

	/**
	 * @param arg0
	 * @return String
	 */
	public String getAttributeWhenVisible(String arg0) {
		return getAttributeWhenVisible(arg0, getTimeout());
	}

	/**
	 * @param arg0
	 * @param waitTimeInSeconds
	 * @return String
	 */
	public String getAttributeWhenVisible(String arg0, int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return getAttribute(arg0);
	}

	/**
	 * @return String
	 */
	public String getTagNameWhenVisible() {
		return getTagNameWhenVisible(getTimeout());
	}

	/**
	 * @param waitTimeInSeconds
	 * @return String
	 */
	public String getTagNameWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return getTagName();
	}

	/**
	 * @return String
	 */
	public String getTextWhenVisible() {
		return getTextWhenVisible(getTimeout());
	}

	/**
	 * @param waitTimeInSeconds
	 * @return String
	 */
	public String getTextWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return getText();
	}

	/**
	 * @return boolean
	 */
	public boolean isEnabledWhenVisible() {
		return isEnabledWhenVisible(getTimeout());
	}

	/**
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	public boolean isEnabledWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return isEnabled();
	}

	/**
	 * @return boolean
	 */
	public boolean isSelectedWhenVisible() {
		return isSelectedWhenVisible(getTimeout());
	}

	/**
	 * @param waitTimeInSeconds
	 * @return boolean
	 */
	public boolean isSelectedWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		return isSelected();
	}

	/**
	 * @param arg0
	 * @return IElement
	 */
	public IElement sendKeysWhenVisible(CharSequence... arg0) {
		return sendKeysWhenVisible(getTimeout(), arg0);
	}

	/**
	 * @param waitTimeInSeconds
	 * @param arg0
	 * @return IElement
	 */
	public IElement sendKeysWhenVisible(int waitTimeInSeconds,
			CharSequence... arg0) {
		waitUntilVisible(waitTimeInSeconds);
		sendKeys(arg0);
		return this;
	}

	/**
	 * @param arg0
	 * @return IElement
	 */
	public IElement typeWhenVisible(CharSequence... arg0) {
		return sendKeysWhenVisible(arg0);
	}
	
	/**
	 * @param waitTimeInSeconds
	 * @param arg0 
	 * @return IElement
	 */
	public IElement typeWhenVisible(int waitTimeInSeconds, CharSequence... arg0) {
		return sendKeysWhenVisible(waitTimeInSeconds, arg0);
	}
	
	/**
	 * @return IElement
	 */
	public IElement submitWhenVisible() {
		return submitWhenVisible(getTimeout());
	}

	/**
	 * @param waitTimeInSeconds
	 * @return IElement
	 */
	public IElement submitWhenVisible(int waitTimeInSeconds) {
		waitUntilVisible(waitTimeInSeconds);
		submit();
		return this;
	}

	/**
	 * @param locator
	 * @return IElement
	 */
	public IElement setMultiplesLocator(String locator) {
		multiplesLocator = locator;
		return this;
	}
	
	/**
	 * @return String
	 */
	public String getMultiplesLocator() {
		return nullToEmpty(multiplesLocator);
	}
	
	/**
	 * @return boolean
	 */
	public boolean hasMultiples() {
		return ! isNullOrEmpty(multiplesLocator);
	}
	
	/**
	 * @return boolean
	 */
	public boolean isDisplayed(){
		initializeWebElement();
		return getElement().isDisplayed();
	}
	
	public String getCssValue(String value) {
		initializeWebElement();
		return getElement().getCssValue(value);
	}

	public Point getLocation() {
		initializeWebElement();
		return getElement().getLocation();
	}

	public Dimension getSize() {
		initializeWebElement();
		return getElement().getSize();
	}

	public boolean isDisplayedWhenVisible(){
		return isDisplayedWhenVisible(getTimeout());
	}
	
	public boolean isDisplayedWhenVisible(int waitTimeInSeconds){
		waitUntilVisible(waitTimeInSeconds);
		return isDisplayed();
	}
	
	public String getCssValueWhenVisible(String value) {
		return getCssValueWhenVisible(value, getTimeout());
	}
	
	public String getCssValueWhenVisible(String value, int waitTimeInSeconds){
		waitUntilVisible(waitTimeInSeconds);
		return getCssValue(value);
	}
	
	public Point getLocationWhenVisible(){
		return getLocationWhenVisible(getTimeout());
	}
	
	public Point getLocationWhenVisible(int waitTimeInSeconds){
		waitUntilVisible(waitTimeInSeconds);
		return getLocation();
	}
	
	public Dimension getSizeWhenVisible(){
		return getSizeWhenVisible(getTimeout());
	}
	
	public Dimension getSizeWhenVisible(int waitTimeInSeconds){
		waitUntilVisible(waitTimeInSeconds);
		return getSize();
	}
	
	/*
	 * CONDITIONS
	 */
	private ExpectedCondition<WebElement> elementIsVisible() { // $codepro.audit.disable
																// methodJavadoc

		return new ExpectedCondition<WebElement>() {

			public WebElement apply(WebDriver driver) {

				final WebElement e = getElement();

				if (e instanceof NonExistentElement)
					return null;

				if (e.isDisplayed())
					return e;

				return null;
			}
		};
	}

}
