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

// $codepro.audit.disable
package unit.core;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import shelob.core.ApplicationParameters;
import shelob.core.LookUp;
import shelob.core.elements.Element;
import shelob.core.elements.NonExistentElement;
import shelob.core.exceptions.InsufficientArgumentsException;
import shelob.core.exceptions.NonExistentWebElementException;
import shelob.core.interfaces.IWaitDelegate;
import shelob.core.interfaces.elements.IElement;
import shelob.core.interfaces.elements.IElementCollection;
import shelob.core.interfaces.page.IPage;
import shelob.core.page.GenericReportWindow;
import shelob.core.page.Page;
import shelob.core.page.StandardNavigationPage;

import examples.element.Button;
import examples.element.Button.Type;
import examples.element.CheckBox;
import examples.element.Dropdown;
import examples.element.GridCell;
import examples.element.Image;
import examples.element.Label;
import examples.element.RadioButton;
import examples.element.TextBox;
import examples.element.interfaces.IButton;
import examples.element.interfaces.ICheckBox;
import examples.element.interfaces.IDropdown;
import examples.element.interfaces.IGridCell;
import examples.element.interfaces.IImage;
import examples.element.interfaces.ILabel;
import examples.element.interfaces.IRadioButton;
import examples.element.interfaces.ITextBox;

public class ElementTests {

	@Mock IPage linkPage;
	@Mock IPage parentPage;
	@Mock WebElement delegate;
	@Mock RemoteWebDriver driver;
	@Mock IPage link;
	@Mock ApplicationParameters parameters;
	@Mock RemoteWebDriver waitMock;
	
	private static final LookUp LOOKUP = LookUp.ByXpath;
	private static final String LOCATOR = "/x/path/to/element";
	private static final String PARENT_LOCATOR = "//parent/element/";
	private static final String NON_EXISTENT_LOCATOR = "?";
	private static final String INVALID_XPATH = "//[contains@";
	private static final String LABEL = "Test";
	
	private static final String PAGE_NAME = "Test Page";
	
	private TestElement element;
	private ParentElement parentElement;
	
	private NonExistentElement nullObject;
	
	static class TestElement extends Element {

		TestElement(IPage parent, LookUp lookup, String locator, IPage link, String label) {
			super(parent, lookup, locator, label, link);
		}
	}
	
	static class ParentElement extends Element {
		
		ParentElement(IPage parent, LookUp lookup, String locator, IPage link, String label) {
			super(parent, lookup, locator, label, link);
		}
	}
	
	static class TestPage extends StandardNavigationPage {

		TestPage(ApplicationParameters parameters) {
			super(parameters, PAGE_NAME);
		}

		public IElementCollection getElements() {
			return null;
		}
	}
	
	static class ReportTestPage extends GenericReportWindow<TestPage> {

		protected ReportTestPage(Page parent, String title) {
			super(parent, title);
		}

		public IElementCollection getElements() {
			return null;
		}
		
	}
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		element = new TestElement(parentPage, LOOKUP, LOCATOR, link, LABEL);
		parentElement = new ParentElement(parentPage, LOOKUP, PARENT_LOCATOR, link, LABEL);
		nullObject = new NonExistentElement(element);
				
		// Configure behavior for delegate tests
		when(parentPage.getPageTitle()).thenReturn(PAGE_NAME);
		when(parentPage.getDriver()).thenReturn(driver);
		when(parentPage.getParameters()).thenReturn(parameters);
		
		when(parameters.getDefaultWait()).thenReturn(0);
		when(parameters.getWaitDelegate()).thenReturn(new IWaitDelegate(){

			public void run() {
				((JavascriptExecutor)driver).executeScript("return true;");
			}
		});
		
		when(linkPage.getDriver()).thenReturn(driver);
		
		when(driver.findElement(By.className(LOCATOR))).thenReturn(delegate);
		when(driver.findElement(By.cssSelector(LOCATOR))).thenReturn(delegate);
		when(driver.findElement(By.id(LOCATOR))).thenReturn(delegate);
		when(driver.findElement(By.linkText(LOCATOR))).thenReturn(delegate);
		when(driver.findElement(By.name(LOCATOR))).thenReturn(delegate);
		when(driver.findElement(By.partialLinkText(LOCATOR))).thenReturn(delegate);
		when(driver.findElement(By.tagName(LOCATOR))).thenReturn(delegate);
		when(driver.findElement(By.xpath(LOCATOR))).thenReturn(delegate);
	}
	
	@After
	public void teardown() {}
	
	@Test
	public void baseElementTest(){ 
		
		assertThat(element.getParentPage(), is(notNullValue()));
		assertThat(element.getParentPage(), is(parentPage));
		
		assertThat(element.getLocator(), is(notNullValue()));
		assertThat(element.getLocator(), is(LOCATOR));
		
		assertThat(element.getLookUpType(), is(notNullValue()));
		assertThat(element.getLookUpType(), is(LOOKUP));
		
		assertThat(element.getLabel(), is(LABEL));
		assertThat(element.hasLabel(), is(true));
		
		assertThat(element.isRequired(), is(false));
		element.setRequired();
		assertThat(element.isRequired(), is(true));
		
		assertThat(element.isRelativeToParent(), is(false));
		element.setRelativeToParent(parentElement);
		assertThat(element.isRelativeToParent(), is(true));
		assertThat(element.getLocator(), is(PARENT_LOCATOR + LOCATOR));
		
		assertThat(element.hasLocalizations(), is(false));
		element.addLocalization("Localization 1");
		assertThat(element.hasLocalizations(), is(true));
		element.addLocalization("Localization 2");
		assertThat(element.getLocalizations().size(), is(3)); //includes original label
				
		// Ensure immutability of Localizations
		final String invalid = "Invalid Localization";
		Collection<String> returnLocalizations = element.getLocalizations();
		returnLocalizations.add(invalid);
		assertThat(element.getLocalizations().size(), is(3)); //includes original label
		assertThat(element.getLocalizations().contains(invalid), is(false));
		
		assertThat(element.pause(1000).equals(element), is(true));
	}
	
	@Test 
	public void baseElementLookUpTest() {
		
		TestElement lookup = new TestElement(parentPage, LookUp.ByClassName, LOCATOR, link, LABEL);
		lookup.click();
		assertThat(lookup.getLookUpType(), is(LookUp.ByClassName));
		
		lookup = new TestElement(parentPage, LookUp.ByCSSSelector, LOCATOR, link, LABEL);
		lookup.click();
		assertThat(lookup.getLookUpType(), is(LookUp.ByCSSSelector));
		
		lookup = new TestElement(parentPage, LookUp.ById, LOCATOR, link, LABEL);
		lookup.click();
		assertThat(lookup.getLookUpType(), is(LookUp.ById));
		
		lookup = new TestElement(parentPage, LookUp.ByLinkText, LOCATOR, link, LABEL);
		lookup.click();
		assertThat(lookup.getLookUpType(), is(LookUp.ByLinkText));
		
		lookup = new TestElement(parentPage, LookUp.ByName, LOCATOR, link, LABEL);
		lookup.click();
		assertThat(lookup.getLookUpType(), is(LookUp.ByName));
		
		lookup = new TestElement(parentPage, LookUp.ByPartialLinkText, LOCATOR, link, LABEL);
		lookup.click();
		assertThat(lookup.getLookUpType(), is(LookUp.ByPartialLinkText));
		
		lookup = new TestElement(parentPage, LookUp.ByTagName, LOCATOR, link, LABEL);
		lookup.click();
		assertThat(lookup.getLookUpType(), is(LookUp.ByTagName));
		
		when(driver.findElement(By.xpath(NON_EXISTENT_LOCATOR))).thenThrow(new NoSuchElementException("Not found"));
		lookup = new TestElement(parentPage, LookUp.ByXpath, NON_EXISTENT_LOCATOR, link, LABEL);
		assertThat(lookup.isValid(), is(false));
		
		try {
			lookup.click();
		} catch (NonExistentWebElementException e) {
			assertThat(e.getMessage().contains("Attempt to call " + "click()" + " on a WebElement that cannot be found."), is(true));
		}
		
		when(driver.findElement(By.xpath(INVALID_XPATH))).thenThrow(new WebDriverException("Invalid xpath expression"));
		lookup = new TestElement(parentPage, LookUp.ByXpath, INVALID_XPATH, link, LABEL);
		
		try {
			lookup.click();
		} catch (NonExistentWebElementException e) {
			assertThat(e.getMessage().contains("Invalid xpath expression"), is(true));
		}
	}
	
	@Test
	public void isDisplayedTests(){
		
		// No longer associated with the depreciated RenderedWebElement interface
		assertThat(element.isDisplayed(), is(false));		
	}
	
	@Test(expected = NullPointerException.class)
	public void parentNullException(){
		element = new TestElement(null, LOOKUP, LOCATOR, link, LABEL);
	}
	
	@Test(expected = NullPointerException.class)
	public void locatorNullException(){
		element = new TestElement(parentPage, LOOKUP, null, link, LABEL);
	}
	
	@Test(expected = NullPointerException.class)
	public void lookUpNullException(){
		element = new TestElement(parentPage, null, LOCATOR, link, LABEL);
	}
	
	@Test
	public void nonExistentElementTests(){
		assertThat(nullObject.isValid(), is(false));
	}
		
	@Test(expected = NonExistentWebElementException.class)
	public void clearNonExistentElement(){
		nullObject.clear();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void clickNonExistentElement(){
		nullObject.click();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void findElementNonExistentElement(){
		nullObject.findElement(null);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void findElementsNonExistentElement(){
		nullObject.findElements(null);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getAttributeNonExistentElement(){
		nullObject.getAttribute(null);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getTagNameNonExistentElement(){
		nullObject.getTagName();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getTextNonExistentElement(){
		nullObject.getText();
	}
		
	@Test(expected = NonExistentWebElementException.class)
	public void isEnabledNonExistentElement(){ 
		nullObject.isEnabled();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void isSelectedNonExistentElement(){
		nullObject.isSelected();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void sendKeysnonExistentElement(){
		nullObject.sendKeys("");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void typeNonExistentElement(){
		nullObject.type("");
	}
		
	@Test(expected = NonExistentWebElementException.class)
	public void submitNonExistentElement(){
		nullObject.submit();
	}
		
	@Test(expected = NonExistentWebElementException.class)
	public void getParentNonExistentElement(){
		nullObject.getParentPage();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getLocatorNonExistentElement(){
		nullObject.getLocator();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getLookUpTypeNonExistentElement(){
		nullObject.getLookUpType();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void hasLabelNonExistentElement(){
		nullObject.hasLabel();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getLabelNonExistentElement(){
		nullObject.getLabel();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void setRequiredNonExistentElement(){
		nullObject.setRequired();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void isRequiredNonExistentElement(){
		nullObject.isRequired();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void setRelativeToParentNonExistentElement(){
		
		TestElement parentElement = mock(TestElement.class);
		nullObject.setRelativeToParent(parentElement);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void isRelativeToParentNonExistentElement(){
		nullObject.isRelativeToParent();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void addLocalizationNonExistentElement(){
		nullObject.addLocalization(null);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getLocalizationsNonExistentElement(){
		nullObject.getLocalizations();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void hasLocalizationsNonExistentElement(){
		nullObject.hasLocalizations();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void hasLinkNonExistentElement(){
		nullObject.hasLink();	
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void goToLinkNonExistentElement(){
		nullObject.goToLink();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void goToLinkOverloadedNonExistentElement(){
		nullObject.goToLink(TestPage.class);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void setIsTemplateNonExistentElement(){
		nullObject.setIsTemplate();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void isTemplateNonExistentElement(){
		nullObject.isTemplate();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void setTemplateIdentifierNonExistentElement(){
		nullObject.setTemplateIdentifier("");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getWebElementNonExistentElement(){
		nullObject.getWebElement();
	}

	@Test(expected = NonExistentWebElementException.class)
	public void getWebElementsNonExistentElement(){
		nullObject.getWebElements();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void pauseNonExistentElement(){
		nullObject.pause(1000);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void waitUntilVisibleNonExistentElement(){
		nullObject.waitUntilVisible(1000);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getWaitHelperNonExistentElement(){
		nullObject.getWaitHelper(1000);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void setTimeOutNonExistentElement(){
		nullObject.setTimeout(5);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getTimeoutNonExistentElement(){
		nullObject.getTimeout();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void clearWhenVisibleNonExistentElement(){
		nullObject.clearWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void clearWhenVisibleWithWaitNonExistentElement(){
		nullObject.clearWhenVisible(5);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void clickWhenVisibleNonExistentElement(){
		nullObject.clickWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void clickWhenVisibleWithWaitNonExistentElement(){
		nullObject.clickWhenVisible(5);
	}
		
	@Test(expected = NonExistentWebElementException.class)
	public void findElementsWhenVisibleNonExistentElement(){
		nullObject.findElementsWhenVisible(By.id(""));
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void findElementsWhenVisibleWithWaitNonExistentElement(){
		nullObject.findElementsWhenVisible(By.id(""), 5);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getAttributeWhenVisibleNonExistentElement(){
		nullObject.getAttributeWhenVisible("");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getAttributeWhenVisibleWithWaitNonExistentElement(){
		nullObject.getAttributeWhenVisible("", 5);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getTagNameWhenVisibleNonExistentElement(){
		nullObject.getTagNameWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getTagNameWhenVisibleWithWaitNonExistentElement(){
		nullObject.getTagNameWhenVisible(5);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getTextWhenVisibleNonExistentElement(){
		nullObject.getTextWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getTextWhenVisibleWithWaitNonExistentElement(){
		nullObject.getTextWhenVisible(5);
	}	
	
	@Test(expected = NonExistentWebElementException.class)
	public void isEnabledWhenVisibleNonExistentElement(){
		nullObject.isEnabledWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void isEnabledWhenVisibleWithWaitNonExistentElement(){
		nullObject.isEnabledWhenVisible(5);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void isSelectedWhenVisibleNonExistentElement(){
		nullObject.isSelectedWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void isSelectedWhenVisibleWithWaitNonExistentElement(){
		nullObject.isSelectedWhenVisible(5);
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void sendKeysWhenVisibleNonExistentElement(){
		nullObject.sendKeysWhenVisible("");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void sendKeysWhenVisibleWithWaitNonExistentElement(){
		nullObject.sendKeysWhenVisible(5, ""); // NOTE : the wait interval MUST come first
	}
		
	@Test(expected = NonExistentWebElementException.class)
	public void submitWhenVisibleExistentElement(){
		nullObject.submitWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void submitWhenVisibleWithWaitNonExistentElement(){
		nullObject.submitWhenVisible(5);
	}
		
	@Test(expected = NonExistentWebElementException.class)
	public void setTemplateIdentifiersNonExistentElement(){
		nullObject.setTemplateIdentifiers("one", "two", "three");
	}
		
	@Test(expected = NonExistentWebElementException.class)
	public void typeWhenVisibleNonExistentElement(){
		nullObject.typeWhenVisible("");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void typeWhenVisibleWithWaitNonExistentElement(){
		nullObject.typeWhenVisible(1, "");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getTemplateIdentifiers(){
		nullObject.getTemplateIdentifiers();
	}
	
	
	@Test(expected = NonExistentWebElementException.class)
	public void getCssValueNonExistentElement(){
		nullObject.getCssValue("");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getCssValueWhenVisibleNonExistentElement(){
		nullObject.getCssValueWhenVisible("");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getLocationNonExistentElement(){
		nullObject.getLocation();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getLocationWhenVisibleNonExistentElement(){
		nullObject.getLocationWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getSizeNonExistentElement(){
		nullObject.getSize();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getSizeWhenVisibleNonExistentElement(){
		nullObject.getSizeWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void isDisplayedNonExistentElement(){
		nullObject.isDisplayed();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void isDisplayedWhenVisibleNonExistentElement(){
		nullObject.isDisplayedWhenVisible();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getLinkNonExistentElement(){
		nullObject.getLink();
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void getLinkWithClassNonExistentElement(){
		nullObject.getLink(IPage.class);
	}
	
	@Test
	public void toStringNonExistentElement(){
		assertThat(nullObject.toString(), is("Non-existent Element."));
	}
	
	private void verifyCalls(IElement element){
			
		element.clear();
		verify(delegate).clear();
		
		element.click();
		verify(delegate).click();
						
		element.getAttribute("");
		verify(delegate).getAttribute("");
		
		element.getTagName();
		verify(delegate).getTagName();
		
		element.getText();
		verify(delegate).getText();
		
		element.isEnabled();
		verify(delegate).isEnabled();
		
		element.isSelected();
		verify(delegate).isSelected();
		
		element.sendKeys("String");
		element.type("String");
		verify(delegate, times(2)).sendKeys("String");
				
		element.submit();
		verify(delegate).submit();
		
		element.isDisplayed();
		verify(delegate).isDisplayed();
		
		element.getCssValue("String");
		verify(delegate).getCssValue("String");
		
		element.getLocation();
		verify(delegate).getLocation();
		
		element.getSize();
		verify(delegate).getSize();
		
		assertThat(element.isValid(), is(true));
	}
	
	@Test(expected = NullPointerException.class)
	public void getLabelNullPointerException() {
		
		final ITextBox anyElement = new TextBox.Builder(parentPage, LOOKUP, LOCATOR)
											   .build();
		
		anyElement.getLabel();
	}
	
	@Test(expected = NullPointerException.class)
	public void goToLinkNullPointerException() {
		
		final ILabel anyElement = new Label.Builder(parentPage, LOOKUP, LOCATOR)
										   .build();
		
		anyElement.goToLink();
	}
	
	@Test
	public void goToLink() {
		
		ApplicationParameters parameters = mock(ApplicationParameters.class);
		RemoteWebDriver driver = mock(RemoteWebDriver.class);
		
		TestPage linkedPage = new TestPage(parameters);
		ReportTestPage pageOpensInNewWindow = new ReportTestPage(linkedPage, "Opens in new window");
		
		final ILabel anyElementWithLink = new Label.Builder(parentPage, LOOKUP, LOCATOR)
												   .linksTo(pageOpensInNewWindow)
												   .build();
		
		assertThat(anyElementWithLink.hasLink(), is(true));
		
		when(parentPage.getParameters()).thenReturn(parameters);
		when(parameters.getDriver()).thenReturn(driver);
		when(driver.getWindowHandle()).thenReturn("GUID3");
		
		Set<String> guids = new HashSet<String>();
		guids.add("GUID");
		guids.add("GUID2");
		guids.add("GUID3");
		
		when(driver.getWindowHandles()).thenReturn(guids);
		
		ReportTestPage returnedPage = anyElementWithLink.goToLink();
		assertThat(returnedPage.getClass().equals(returnedPage.getClass()), is(true));
		assertThat(anyElementWithLink.goToLink(ReportTestPage.class).getClass().equals(returnedPage.getClass()), is(true));
		
		final ILabel elementWithNullLink = new Label.Builder(parentPage, LOOKUP, LOCATOR)
													.linksTo(null)
													.build();
		
		assertThat(elementWithNullLink.hasLink(), is(false));
		
		try {
			elementWithNullLink.goToLink(TestPage.class);
		} catch (NullPointerException e){
			assertThat(e.getMessage().contains("The linkTo for this object was not set through it's Builder."), is(true));
		}
		
	}
	
	@Test
	public void getLink() {
		
		ApplicationParameters parameters = mock(ApplicationParameters.class);
		
		TestPage linkedPage = new TestPage(parameters);
		
		final ILabel anyElementWithLink = new Label.Builder(parentPage, LOOKUP, LOCATOR)
		   .linksTo(linkedPage)
		   .build();
		
		assertThat(anyElementWithLink.getLink().equals(linkedPage), is(true));
		
	}
	
	@Test
	public void templateTests(){
		
		ApplicationParameters parameters = mock(ApplicationParameters.class);
		WebElement foundElement = mock(WebElement.class);
		RemoteWebDriver driver = mock(RemoteWebDriver.class);
		
		TestPage page = new TestPage(parameters);
		
		final ILabel anyElementActingAsATemplate = new Label.Builder(page, LookUp.ByXpath, "/xpath/template/with[@expression='%s']").build();
		
		anyElementActingAsATemplate.setIsTemplate();
		assertThat(anyElementActingAsATemplate.isTemplate(), is(true));
		
		anyElementActingAsATemplate.setTemplateIdentifier("identifier");
		assertThat(anyElementActingAsATemplate.getLocator(), is("/xpath/template/with[@expression='identifier']"));
		anyElementActingAsATemplate.setTemplateIdentifier("other identifier");
		assertThat(anyElementActingAsATemplate.getLocator(), is("/xpath/template/with[@expression='other identifier']"));
		
		when(page.getDriver()).thenReturn(driver);
		when(page.getDriver().findElement(By.xpath(String.format("/xpath/template/with[@expression='%s']", "other identifier")))).thenReturn(foundElement);
		anyElementActingAsATemplate.click();
		
		final ILabel anyElementWithMultipleTemplateIDs = new Label.Builder(page, LookUp.ByXpath, "/xpath/%s/template/with[@expression='%s']").isTemplate().build();
		assertThat(anyElementWithMultipleTemplateIDs.isTemplate(), is(true));
		
		anyElementWithMultipleTemplateIDs.setTemplateIdentifiers("id1", "id2");
		assertThat(anyElementWithMultipleTemplateIDs.getLocator(), is("/xpath/id1/template/with[@expression='id2']"));
		anyElementWithMultipleTemplateIDs.setTemplateIdentifiers("id3", "id4");
		assertThat(anyElementWithMultipleTemplateIDs.getLocator(), is("/xpath/id3/template/with[@expression='id4']"));
		
		final ILabel anyElementWithTemplateArgumentMismatch = new Label.Builder(page, LookUp.ByXpath, "/xpath/%s/%s/%s").isTemplate().build();
		assertThat(anyElementWithTemplateArgumentMismatch.isTemplate(), is(true));
		
		// NOTE : not enough arguments. We do not test for ADDITIONAL arguments as they are generally ignored.
		try{
			anyElementWithTemplateArgumentMismatch.setTemplateIdentifiers("id1", "id2"); 
			anyElementWithTemplateArgumentMismatch.getLocator();
		} catch (InsufficientArgumentsException e) {
			assertThat(e.getMessage().contains("Either the type or quantity of arguments supplied for the template [/xpath/%s/%s/%s] is incorrect"), is(true));
		} catch (Exception e) {
			fail("No other exceptions should be thrown");
		}
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void findTemplateWithoutIdentifier(){
		
		ApplicationParameters parameters = mock(ApplicationParameters.class);
		TestPage page = new TestPage(parameters);
		
		final ILabel anyElementActingAsATemplate = new Label.Builder(page, LookUp.ByXpath, "/xpath/template/with[@expression='%s']").build();
		
		anyElementActingAsATemplate.setIsTemplate();
		assertThat(anyElementActingAsATemplate.isTemplate(), is(true));
		
		anyElementActingAsATemplate.click();
	}
	
	@Test
	public void createElementWithDefaultWaitInterval(){
		
		ApplicationParameters parameters = mock(ApplicationParameters.class);
		TestPage page = new TestPage(parameters);
		
		final ILabel anyElementWithDefaultWaitInterval = new Label.Builder(page, LookUp.ByXpath, "/xpath/template/with[@expression='%s'")
																  .defaultWaitInterval(15)
																  .build();
		
		assertThat(anyElementWithDefaultWaitInterval.getTimeout(), is(15));
		
		anyElementWithDefaultWaitInterval.setTimeout(10);
		
		assertThat(anyElementWithDefaultWaitInterval.getTimeout(), is(10));
		
		ApplicationParameters parametersWithDefaultWait = mock(ApplicationParameters.class);
		TestPage pageWithWaitParameterSet = new TestPage(parametersWithDefaultWait);
		when(parametersWithDefaultWait.getDefaultWait()).thenReturn(7);
		
		final ILabel anyElementWithDefaultWaitSetGlobally = new Label.Builder(pageWithWaitParameterSet, "Identifier")
																     .build();
		
		assertThat(anyElementWithDefaultWaitSetGlobally.getTimeout(), is(7));
		
	}
	
	@Test
	public void textBoxTest(){
		
		final String label = "Text";
		
		assertThat(new TextBox.Builder(parentPage, LOOKUP, LOCATOR).toString(), 
				is("Parent : " + PAGE_NAME + 
				   " Element : " + TextBox.Builder.class.getName() +
				   " LookUp : " + LOOKUP +
				   " Locator : " + LOCATOR));
		
		final ITextBox box = new TextBox.Builder(parentPage, LOOKUP, LOCATOR)
								  .label(label)
								  .required()
								  .build();
		verifyCalls(box);
		
		assertThat(box.isRequired(), is(true));
		
		assertThat(box.toString(), is(new StringBuilder()
									      .append("Parent Page : ")
									      .append(parentPage.getClass().getCanonicalName())
									      .append(" Element : ")
									      .append(box.getClass().getName())
									      .append(" LookUp : ")
									      .append(LOOKUP.name())
									      .append(" Locator : ")
									      .append(LOCATOR)
									      .append(" HasLocalizations : ")
									      .append(false)
									      .append(" HasLabel : ")
									      .append(true)
									      .append(" IsRelativeToParent : ")
									      .append(false)
									      .append(" IsTemplate : ")
									      .append(false)
									      .toString()));
		
		try {
			box.goToLink();
		}
		catch (UnsupportedOperationException e){
			assertThat(e.getMessage(), is("The TextBox object does not support links."));
		}
		catch (Exception e){
			fail("No other exceptions should be thrown");
		}
	}
	
	@Test
	public void textBoxAlternativeConstructorsTest() {
		
		final ITextBox boxWithId = new TextBox.Builder(parentPage, "@attribute='identifier'").build();
		
		assertThat(boxWithId.getLocator(), is("/input[@type='text' and @attribute='identifier']"));
		assertThat(boxWithId.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final ITextBox boxWithDefaults = new TextBox.Builder(parentPage).build();
		
		assertThat(boxWithDefaults.getLocator(), is("/input[@type='text' ]")); // WARNING : the space is significant; padding for unused identifier.
		assertThat(boxWithDefaults.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final ITextBox boxWithTemplate = new TextBox.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
													.isTemplate()
													.build();
		
		assertThat(boxWithTemplate.isTemplate(), is(true));
		
		final ITextBox boxWithLocalizations = new TextBox.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
														 .label("Label")
														 .addLocalization("Localization 1")
														 .addLocalization("Localization 2")
														 .build();
		
		assertThat(boxWithLocalizations.getLocalizations().size(), is(3)); // contains original label
		assertThat(boxWithLocalizations.getLocalizations().contains("Localization 1"), is(true));
		assertThat(boxWithLocalizations.getLocalizations().contains("Localization 2"), is(true));
		assertThat(boxWithLocalizations.isTemplate(), is(true));
	}
	
	@Test
	public void checkBoxTest(){
		
		final String label = "Check";
		
		assertThat(new CheckBox.Builder(parentPage, LOOKUP, LOCATOR).toString(), 
				is("Parent : " + PAGE_NAME + 
				   " Element : " + CheckBox.Builder.class.getName() +
				   " LookUp : " + LOOKUP +
				   " Locator : " + LOCATOR));
		
		final ICheckBox check = new CheckBox.Builder(parentPage, LOOKUP, LOCATOR)
									  .label(label)
									  .build();
		verifyCalls(check);
		
		assertThat(check.toString(), is(new StringBuilder()
	      									.append("Parent Page : ")
	      									.append(parentPage.getClass().getCanonicalName())
	      									.append(" Element : ")
	      									.append(check.getClass().getName())
	      									.append(" LookUp : ")
	      									.append(LOOKUP.name())
	      									.append(" Locator : ")
	      									.append(LOCATOR)
	      									.append(" HasLocalizations : ")
	      									.append(false)
	      									.append(" HasLabel : ")
	      									.append(true)
	      									.append(" IsRelativeToParent : ")
	      									.append(false)
	      									.append(" IsTemplate : ")
	      									.append(false)
	      									.toString()));		
		
		try {
			check.goToLink();
		} catch (UnsupportedOperationException e){
			assertThat(e.getMessage(), is("The CheckBox object does not support links."));
		} catch (Exception e){
			fail("No other exceptions should be thrown");
		}
		
		try {
			check.goToLink(TestPage.class);
		} catch (UnsupportedOperationException e){
			assertThat(e.getMessage(), is("The CheckBox object does not support links."));
		} catch (Exception e){
			fail("No other exceptions should be thrown");
		}
	}
	
	@Test
	public void checkBoxAlternativeConstructorsTest(){
		
		final ICheckBox checkWithIdentifier = new CheckBox.Builder(parentPage, "@attribute='identifier'").build();
		
		assertThat(checkWithIdentifier.getLocator(), is("/input[@type='checkbox' and @attribute='identifier']"));
		assertThat(checkWithIdentifier.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final ICheckBox checkWithDefaults = new CheckBox.Builder(parentPage).build();
		
		assertThat(checkWithDefaults.getLocator(), is("/input[@type='checkbox' ]")); // WARNING : the space is significant; padding for unused identifier.
		assertThat(checkWithDefaults.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final ICheckBox boxWithTemplate = new CheckBox.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
													  .isTemplate()
													  .build();

		assertThat(boxWithTemplate.isTemplate(), is(true)); 

		final ICheckBox boxWithLocalizations = new CheckBox.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
														   .label("Label")
														   .addLocalization("Localization 1")
														   .addLocalization("Localization 2")
														   .build();

		assertThat(boxWithLocalizations.getLocalizations().size(), is(3)); //contains original label
		assertThat(boxWithLocalizations.getLocalizations().contains("Localization 1"), is(true));
		assertThat(boxWithLocalizations.getLocalizations().contains("Localization 2"), is(true));
		assertThat(boxWithLocalizations.isTemplate(), is(true));		
	}
	
	@Test
	public void radioButtonTest(){
		
		final String label = "Radio";
		
		assertThat(new RadioButton.Builder(parentPage, LOOKUP, LOCATOR).toString(), 
				is("Parent : " + PAGE_NAME + 
				   " Element : " + RadioButton.Builder.class.getName() +
				   " LookUp : " + LOOKUP +
				   " Locator : " + LOCATOR));
		
		final IRadioButton radio = new RadioButton.Builder(parentPage, LOOKUP, LOCATOR)
										    .label(label)
										    .build();
		verifyCalls(radio);
		
		assertThat(radio.toString(), is(new StringBuilder()
											.append("Parent Page : ")
											.append(parentPage.getClass().getCanonicalName())
											.append(" Element : ")
											.append(radio.getClass().getName())
											.append(" LookUp : ")
											.append(LOOKUP.name())
											.append(" Locator : ")
											.append(LOCATOR)
											.append(" HasLocalizations : ")
											.append(false)
											.append(" HasLabel : ")
											.append(true)
											.append(" IsRelativeToParent : ")
											.append(false)
											.append(" IsTemplate : ")
											.append(false)
											.toString()));	
		
		try {
			radio.goToLink();
		} catch (UnsupportedOperationException e){
			assertThat(e.getMessage(), is("The RadioButton object does not support links."));
		} catch (Exception e){
			fail("No other exceptions should be thrown");
		}
		
		try {
			radio.goToLink(TestPage.class);
		} catch (UnsupportedOperationException e) {
			assertThat(e.getMessage(), is("The RadioButton object does not support links."));
		}
	}
	
	@Test
	public void radioButtonAlternativeConstructorsTest(){
		
		final IRadioButton buttonWithIdentifer = new RadioButton.Builder(parentPage, "@attribute='identifier'").build();
		
		assertThat(buttonWithIdentifer.getLocator(), is("/input[@type='radio' and @attribute='identifier']"));
		assertThat(buttonWithIdentifer.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IRadioButton buttonWithDefaults = new RadioButton.Builder(parentPage).build();
		
		assertThat(buttonWithDefaults.getLocator(), is("/input[@type='radio' ]"));  // WARNING : the space is significant; padding for unused identifier.
		assertThat(buttonWithDefaults.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IRadioButton radioWithTemplate = new RadioButton.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
															.isTemplate()
															.build();

		assertThat(radioWithTemplate.isTemplate(), is(true));

		final IRadioButton radioWithLocalizations = new RadioButton.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
																 .label("Label")
																 .addLocalization("Localization 1")
																 .addLocalization("Localization 2")
																 .build();

		assertThat(radioWithLocalizations.getLocalizations().size(), is(3)); //contains original label
		assertThat(radioWithLocalizations.getLocalizations().contains("Localization 1"), is(true));
		assertThat(radioWithLocalizations.getLocalizations().contains("Localization 2"), is(true));
		assertThat(radioWithLocalizations.isTemplate(), is(true));
	}
	
	@Test
	public void gridCellTest(){
		
		final String label = "Cell";
		
		assertThat(new GridCell.Builder(parentPage, LOOKUP, LOCATOR).toString(), 
				is("Parent : " + PAGE_NAME + 
				   " Element : " + GridCell.Builder.class.getName() +
				   " LookUp : " + LOOKUP +
				   " Locator : " + LOCATOR));
		
		final IGridCell cell = new GridCell.Builder(parentPage, LOOKUP, LOCATOR)
									 .label(label)
									 .linksTo(linkPage)
									 .build();
		
		verifyCalls(cell);
		
		assertThat(cell.toString(), is(new StringBuilder()
										   .append("Parent Page : ")
										   .append(parentPage.getClass().getCanonicalName())
										   .append(" Element : ")
										   .append(cell.getClass().getName())
										   .append(" LookUp : ")
										   .append(LOOKUP.name())
										   .append(" Locator : ")
										   .append(LOCATOR)
										   .append(" HasLocalizations : ")
										   .append(false)
										   .append(" HasLabel : ")
										   .append(true)
										   .append(" IsRelativeToParent : ")
										   .append(false)
										   .append(" IsTemplate : ")
										   .append(false)
										   .toString()));	
	}
	
	@Test
	public void gridCellAlternativeConstructorsTest(){
		
		final IGridCell cellWithIdentifier = new GridCell.Builder(parentPage, "identifier").build();
		
		assertThat(cellWithIdentifier.getLocator(), is("/td[contains(.,'identifier')]"));
		assertThat(cellWithIdentifier.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IGridCell cellWithTemplate = new GridCell.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
													  .isTemplate()
													  .build();

		assertThat(cellWithTemplate.isTemplate(), is(true));

		final IGridCell cellWithLocalizations = new GridCell.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
														   .label("Label")
														   .addLocalization("Localization 1")
														   .addLocalization("Localization 2")
														   .build();

		assertThat(cellWithLocalizations.getLocalizations().size(), is(3)); //contains original label
		assertThat(cellWithLocalizations.getLocalizations().contains("Localization 1"), is(true));
		assertThat(cellWithLocalizations.getLocalizations().contains("Localization 2"), is(true));
		assertThat(cellWithLocalizations.isTemplate(), is(true));
	}
		
	@Test
	public void buttonTest(){
		
		final String label = "Button";
		
		assertThat(new Button.Builder(parentPage, LOOKUP, LOCATOR).toString(), 
				is("Parent : " + PAGE_NAME + 
				   " Element : " + Button.Builder.class.getName() +
				   " LookUp : " + LOOKUP +
				   " Locator : " + LOCATOR));
		
		final IButton button = new Button.Builder(parentPage, LOOKUP, LOCATOR)
								   .linksTo(linkPage)
								   .label(label)
								   .build();
		verifyCalls(button);
		
		assertThat(button.toString(), is(new StringBuilder()
											.append("Parent Page : ")
											.append(parentPage.getClass().getCanonicalName())
											.append(" Element : ")
											.append(button.getClass().getName())
											.append(" LookUp : ")
											.append(LOOKUP.name())
											.append(" Locator : ")
											.append(LOCATOR)
											.append(" HasLocalizations : ")
											.append(false)
											.append(" HasLabel : ")
											.append(true)
											.append(" IsRelativeToParent : ")
											.append(false)
											.append(" IsTemplate : ")
											.append(false)
											.toString()));	
	}
	
	@Test
	public void buttonAlternativeConstructorsTest(){
		
		final IButton standardWithIdentifier = new Button.Builder(parentPage, Type.Standard, "@attribute='identifier'").build();
		
		assertThat(standardWithIdentifier.getLocator(), is("//input[@type='button' and @attribute='identifier']"));
		assertThat(standardWithIdentifier.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IButton standardWithDefaults = new Button.Builder(parentPage, Button.Type.Standard).build();
		
		assertThat(standardWithDefaults.getLocator(), is("//input[@type='button' ]")); // See above; space is significant
		assertThat(standardWithDefaults.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IButton submitWithIdentifier = new Button.Builder(parentPage, Button.Type.Submit, "@attribute='identifier'").build();
		
		assertThat(submitWithIdentifier.getLocator(), is("//input[@type='submit' and @attribute='identifier']"));
		assertThat(standardWithDefaults.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IButton submitWithDefaults = new Button.Builder(parentPage, Type.Submit).build();
		
		assertThat(submitWithDefaults.getLocator(), is("//input[@type='submit' ]"));
		assertThat(submitWithDefaults.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IButton buttonWithTemplate = new Button.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
													 .isTemplate()
													 .build();

		assertThat(buttonWithTemplate.isTemplate(), is(true));

		final IButton buttonWithLocalizations = new Button.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
													   .label("Label")
													   .addLocalization("Localization 1")
													   .addLocalization("Localization 2")
													   .build();

		assertThat(buttonWithLocalizations.getLocalizations().size(), is(3)); //contains original label
		assertThat(buttonWithLocalizations.getLocalizations().contains("Localization 1"), is(true));
		assertThat(buttonWithLocalizations.getLocalizations().contains("Localization 2"), is(true));
		assertThat(buttonWithLocalizations.isTemplate(), is(true));
	}
	
	@Test
	public void labelTest(){
		
		final String label = "Label";
		
		assertThat(new Label.Builder(parentPage, LOOKUP, LOCATOR).toString(), 
				is("Parent : " + PAGE_NAME + 
				   " Element : " + Label.Builder.class.getName() +
				   " LookUp : " + LOOKUP +
				   " Locator : " + LOCATOR));
		
		final ILabel control = new Label.Builder(parentPage, LOOKUP, LOCATOR)
								.linksTo(linkPage)
								.label(label)
								.build();
		verifyCalls(control);
		
		assertThat(control.toString(), is(new StringBuilder()
											  .append("Parent Page : ")
											  .append(parentPage.getClass().getCanonicalName())
											  .append(" Element : ")
											  .append(control.getClass().getName())
											  .append(" LookUp : ")
											  .append(LOOKUP.name())
											  .append(" Locator : ")
											  .append(LOCATOR)
											  .append(" HasLocalizations : ")
											  .append(false)
											  .append(" HasLabel : ")
											  .append(true)
											  .append(" IsRelativeToParent : ")
											  .append(false)
											  .append(" IsTemplate : ")
											  .append(false)
											  .toString()));	
	}
	
	@Test
	public void labelAlternativeConstructorsTest(){
		
		ILabel labelWithIdentifer = new Label.Builder(parentPage, "identifier").build();
		
		assertThat(labelWithIdentifer.getLocator(), is("//a[contains(.,'identifier')]"));
		assertThat(labelWithIdentifer.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final ILabel labelWithTemplate = new Label.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
												  .isTemplate()
												  .build();

		assertThat(labelWithTemplate.isTemplate(), is(true));

		final ILabel labelWithLocalizations = new Label.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
			 										   .label("Label")
			 										   .addLocalization("Localization 1")
			 										   .addLocalization("Localization 2")
			 										   .build();

		assertThat(labelWithLocalizations.getLocalizations().size(), is(3)); //contains original label
		assertThat(labelWithLocalizations.getLocalizations().contains("Localization 1"), is(true));
		assertThat(labelWithLocalizations.getLocalizations().contains("Localization 2"), is(true));
		assertThat(labelWithLocalizations.isTemplate(), is(true));
	}
	
	@Test
	public void dropdownTest(){
		
		assertThat(new Dropdown.Builder(parentPage, LOOKUP, LOCATOR).toString(), 
				is("Parent : " + PAGE_NAME + 
				   " Element : " + Dropdown.Builder.class.getName() +
				   " LookUp : " + LOOKUP +
				   " Locator : " + LOCATOR));
		
		IDropdown control = new Dropdown.Builder(parentPage, LOOKUP, LOCATOR)
										.linksTo(linkPage)
										.build();
		verifyCalls(control);
		
		assertThat(control.toString(), is(new StringBuilder()
											  .append("Parent Page : ")
											  .append(parentPage.getClass().getCanonicalName())
											  .append(" Element : ")
											  .append(control.getClass().getName())
											  .append(" LookUp : ")
											  .append(LOOKUP.name())
											  .append(" Locator : ")
											  .append(LOCATOR)
											  .append(" HasLocalizations : ")
											  .append(false)
											  .append(" HasLabel : ")
											  .append(false)
											  .append(" IsRelativeToParent : ")
											  .append(false)
											  .append(" IsTemplate : ")
											  .append(false)
											  .toString()));	
	}
	
	@Test
	public void dropdownAlternativeConstructorsTest(){
		
		final IDropdown withOutIdentifier = new Dropdown.Builder(parentPage).build();
		
		assertThat(withOutIdentifier.getLocator(), is("/select"));
		assertThat(withOutIdentifier.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IDropdown withIdentifier = new Dropdown.Builder(parentPage, "identifier").build();
		
		assertThat(withIdentifier.getLocator(), is("/select[contains(.,'identifier')]"));
		assertThat(withIdentifier.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IDropdown dropdownWithTemplate = new Dropdown.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
														   .isTemplate()
														   .build();

		assertThat(dropdownWithTemplate.isTemplate(), is(true));

		final IDropdown dropdownWithLocalizations = new Dropdown.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
			 													.label("Label")
			 													.addLocalization("Localization 1")
			 													.addLocalization("Localization 2")
			 													.build();

		assertThat(dropdownWithLocalizations.getLocalizations().size(), is(3)); //contains original label
		assertThat(dropdownWithLocalizations.getLocalizations().contains("Localization 1"), is(true));
		assertThat(dropdownWithLocalizations.getLocalizations().contains("Localization 2"), is(true));
		assertThat(dropdownWithLocalizations.isTemplate(), is(true));
	}
		
	@Test
	public void waitTests(){
		
		WebElement rendered = mock(WebElement.class);
		
		IDropdown control = new Dropdown.Builder(parentPage, LOOKUP, LOCATOR)
	    								.linksTo(linkPage)
	    								.build();
		
		when(control.getWebElement()).thenReturn(rendered);
		
		try {
			control.waitUntilVisible(2);
		} catch (TimeoutException e) {
			assertThat(e.getMessage().contains("Timed out after 2 seconds"), is(true));
		}	
	}
	
	@Test
	public void waitIntefaceCoverage(){
		
		WebElement rendered = mock(WebElement.class);
		
		IDropdown control = new Dropdown.Builder(parentPage, LOOKUP, LOCATOR)
	    								.linksTo(linkPage)
	    								.defaultWaitInterval(2)
	    								.build();
		
		when(control.getWebElement()).thenReturn(rendered);
		when(rendered.isDisplayed()).thenReturn(true);
		
		control.clearWhenVisible();
		control.clickWhenVisible();
		control.findElementsWhenVisible(By.id(""));
		control.getAttributeWhenVisible("");
		control.getTagNameWhenVisible();
		control.getTextWhenVisible();

		control.isEnabledWhenVisible();
		control.isSelectedWhenVisible();
		control.sendKeysWhenVisible("");

		control.submitWhenVisible();

		control.typeWhenVisible("");
		control.typeWhenVisible(1, "");
		
		control.isDisplayedWhenVisible();
		control.getCssValueWhenVisible("");
		control.getLocationWhenVisible();
		control.getSizeWhenVisible();
		
		control.pause(1000);
	}
	
	@Test
	public void dropdownSelectTests(){
		
		WebElement element = mock(WebElement.class);
		WebElement option = mock(WebElement.class);
		
		List<WebElement> list = new ArrayList<WebElement>();
		list.add(option);
		
		IDropdown control = new Dropdown.Builder(parentPage, LOOKUP, LOCATOR)
									    .linksTo(linkPage)
									    .defaultWaitInterval(3)
									    .build();
		
		when(control.getWebElement()).thenReturn(element);
		when(element.getTagName()).thenReturn("select");
		when(element.isDisplayed()).thenReturn(true);
		when(element.findElements(By.xpath(anyString()))).thenReturn(list);
		when(option.getAttribute("index")).thenReturn("0");
		
		assertThat(control.deselectByVisibleText("").equals(control), is(true));
		assertThat(control.deselectByVisibleTextWhenVisible("").equals(control), is(true));
		assertThat(control.deselectByVisibleTextWhenVisible("", 1).equals(control), is(true));
		
		assertThat(control.deselectByIndex(0).equals(control), is(true));
		assertThat(control.deselectByIndexWhenVisible(0).equals(control), is(true));
		assertThat(control.deselectByIndexWhenVisible(0, 1).equals(control), is(true));
		
		assertThat(control.deselectByValue("").equals(control), is(true));
		assertThat(control.deselectByValueWhenVisible("").equals(control), is(true));
		assertThat(control.deselectByValueWhenVisible("", 1).equals(control), is(true));
		
		try {
			assertThat(control.deselectAll().equals(control), is(true));	
		} catch (UnsupportedOperationException e){
			assertThat(e.getMessage().contains("You may only deselect all options of a multi-select"), is(true));
		}
		
		try {
			assertThat(control.deselectAllWhenVisible().equals(control), is(true));	
		} catch (UnsupportedOperationException e){
			assertThat(e.getMessage().contains("You may only deselect all options of a multi-select"), is(true));
		}
		
		try {
			assertThat(control.deselectAllWhenVisible(1).equals(control), is(true));	
		} catch (UnsupportedOperationException e){
			assertThat(e.getMessage().contains("You may only deselect all options of a multi-select"), is(true));
		}
		
		assertThat(control.selectByValue("").equals(control), is(true));
		assertThat(control.selectByValueWhenVisible("").equals(control), is(true));
		assertThat(control.selectByValueWhenVisible("", 1).equals(control), is(true));
		
		assertThat(control.selectByIndex(0).equals(control), is(true));
		assertThat(control.selectByIndexWhenVisible(0).equals(control), is(true));
		assertThat(control.selectByIndexWhenVisible(0, 1).equals(control), is(true));
		
		assertThat(control.selectByVisibleText("").equals(control), is(true));
		assertThat(control.selectByVisibleTextWhenVisible("").equals(control), is(true));
		assertThat(control.selectByVisibleTextWhenVisible("", 1).equals(control), is(true));
		
		try {
			control.getFirstSelectedOption();	
		} catch (NoSuchElementException e){
			assertThat(e.getMessage().contains("No options are selected"), is(true));
		}
		
		try {
			control.getFirstSelectedOptionWhenVisible();
		} catch (NoSuchElementException e){
			assertThat(e.getMessage().contains("No options are selected"), is(true));
		}
		
		try {
			control.getFirstSelectedOptionWhenVisible(1);
		} catch (NoSuchElementException e){
			assertThat(e.getMessage().contains("No options are selected"), is(true));
		}
		
		assertThat(control.getAllSelectedOptions().size(), is(0));
		assertThat(control.getAllSelectedOptionsWhenVisible().size(), is(0));
		assertThat(control.getAllSelectedOptionsWhenVisible(1).size(), is(0));
		
		assertThat(control.getOptions().size(), is(1));
		assertThat(control.getOptionsWhenVisible().size(), is(1));
		assertThat(control.getOptionsWhenVisible(1).size(), is(1));
		
		assertThat(control.isMultiple(), is(false));
		assertThat(control.isMultipleWhenVisible(), is(false));
		assertThat(control.isMultipleWhenVisible(1), is(false));
	}
	
	@Test
	public void imageTest(){
		
		assertThat(new Image.Builder(parentPage, LOOKUP, LOCATOR).toString(), 
				is("Parent : " + PAGE_NAME + 
				   " Element : " + Image.Builder.class.getName() +
				   " LookUp : " + LOOKUP +
				   " Locator : " + LOCATOR));
		
		IImage control = new Image.Builder(parentPage, LOOKUP, LOCATOR)
								  .linksTo(linkPage)
								  .build();
		verifyCalls(control);
		
		assertThat(control.toString(), is(new StringBuilder()
											  .append("Parent Page : ")
											  .append(parentPage.getClass().getCanonicalName())
											  .append(" Element : ")
											  .append(control.getClass().getName())
											  .append(" LookUp : ")
											  .append(LOOKUP.name())
											  .append(" Locator : ")
											  .append(LOCATOR)
											  .append(" HasLocalizations : ")
											  .append(false)
											  .append(" HasLabel : ")
											  .append(false)
											  .append(" IsRelativeToParent : ")
											  .append(false)
											  .append(" IsTemplate : ")
											  .append(false)
											  .toString()));	
	}
	
	@Test
	public void imageAlternativeConstructorsTest(){
		
		IImage standardWithIdentifier = new Image.Builder(parentPage, Image.Type.Standard, "identifier").build();
		
		assertThat(standardWithIdentifier.getLocator(), is("/img[contains(.,'identifier')]"));
		assertThat(standardWithIdentifier.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		IImage inputWithIdentifier = new Image.Builder(parentPage, Image.Type.Input, "identifier").build();
		
		assertThat(inputWithIdentifier.getLocator(), is("/input[@type='image' and contains(.,'identifier')]"));
		assertThat(inputWithIdentifier.getLookUpType().equals(LookUp.ByXpath), is(true));
		
		final IImage imageWithTemplate = new Image.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
												  .isTemplate()
												  .build();

		assertThat(imageWithTemplate.isTemplate(), is(true));

		final IImage imageWithLocalizations = new Image.Builder(parentPage, LookUp.ByXpath, "/xpath/template/with[@expression='%s']")
			 										   .label("Label")
			 										   .addLocalization("Localization 1")
			 										   .addLocalization("Localization 2")
			 										   .build();

		assertThat(imageWithLocalizations.getLocalizations().size(), is(3)); //contains original label
		assertThat(imageWithLocalizations.getLocalizations().contains("Localization 1"), is(true));
		assertThat(imageWithLocalizations.getLocalizations().contains("Localization 2"), is(true));
		assertThat(imageWithLocalizations.isTemplate(), is(true));
	}
	
}
