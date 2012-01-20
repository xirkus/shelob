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
package core;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.remote.RemoteWebDriver;

import shelob.core.ApplicationParameters;
import shelob.core.ApplicationURL;
import shelob.core.LookUp;
import shelob.core.User;
import shelob.core.elements.ElementCollection;
import shelob.core.interfaces.elements.IElementCollection;
import shelob.core.interfaces.page.IPage;
import shelob.core.page.GenericReportWindow;
import shelob.core.page.Page;
import shelob.core.page.StandardNavigationPage;
import shelob.core.page.SubPage;

import core.examples.element.Label;
import core.examples.element.interfaces.ILabel;


public class PageTests {

	@Mock private RemoteWebDriver driver;
	@Mock private User user;
	@Mock private ApplicationURL url;

	private ApplicationParameters parameters;
	
	private TestPage page;
	private StandardNavigationTestPage standard;
	private SubPage sub;
	private GenericReportWindow<?> report;
	
	private final String pageName = "Page Name";
	
	private final static String subPageATitle = "SubPage A";
	private final static String subPageBTitle = "SubPage B";
	private final static String subPageCTitle = "SubSubPage C";
	
	// Concrete test implementation of Page	
	static class TestPage extends Page {

		TestPage(ApplicationParameters parameters, String title) {
			super(parameters, title);
		}

		public IElementCollection getElements() {
			return null;
		}
	}

	// Concrete test implementation of StandardNavigationPage
	static class StandardNavigationTestPage extends StandardNavigationPage {

		StandardNavigationTestPage(ApplicationParameters parameters, String title) {
			super(parameters, title);			
		}

		public IElementCollection getElements() {
			
			if (elements == null) {
				
				elements = ElementCollection.create()
											.put(new Label.Builder(this, LookUp.ByXpath, "//template/%s/%s/%s").label("Template With Multiple IDs").isTemplate().build());
			}
			
			return elements;
		}
	}
	
	// Concrete test implementation of SubPage
	static class SubPageTestPage extends SubPage {

		SubPageTestPage(Page parent, String title) {
			super(parent, title);
		}

		public IElementCollection getElements() {

			if (elements == null) {
				
				elements = ElementCollection.create();
			}
			
			return elements;
		}
	}
	
	// Classes for testing toString Page implementation
	static class SubPageA extends SubPage {

		protected SubPageA(IPage parent, String title) {
			super(parent, title);
		}

		public IElementCollection getElements() {
			return null;
		}
	}
	
	static class SubPageB extends SubPage {

		protected SubPageB(IPage parent, String title) {
			super(parent, title);
		}

		public IElementCollection getElements() {
			return null;
		}
	}
	
	static class SubPageC extends SubPage {

		protected SubPageC(IPage parent, String title) {
			super(parent, title);
		}

		public IElementCollection getElements() {
			return null;
		}
	}
	
	// Concrete test implementation of GenericReportWindow
	static class GenericReportWindowTestPage extends GenericReportWindow<GenericReportWindowTestPage> {

		GenericReportWindowTestPage(Page parent, String title) {
			super(parent, title);
		}

		public IElementCollection getElements() {
			
			if (elements == null) {
				
				elements = ElementCollection.create();
			}
			
			return elements;
		}
	}
	
	
	@Before
	public void setup() {
	
		MockitoAnnotations.initMocks(this);
		
		parameters = new ApplicationParameters(driver, url, user);
		
		page = new TestPage(parameters, pageName);
		
		standard = new StandardNavigationTestPage(parameters, pageName);
		
		sub = new SubPageTestPage(standard, pageName);
		report = new GenericReportWindowTestPage(standard, pageName);
	}
	
	@After
	public void teardown() {}
	
	@Test
	public void basePageTests(){
		
		commonPageTests(page);
	}
	
	private void commonPageTests(IPage page) {
		
		assertThat(page.getDriver(), is(driver));
		assertThat(page.getParameters(), is(parameters));
		assertThat(page.getURL(), is(url));
		assertThat(page.getUser(), is(user));
		assertThat(page.getPageTitle(), is(pageName));
	}
	
	@Test
	public void pageElementTests(){
		
		// ensure that utility methods are exercised
		assertThat(standard.getElements().size(), is(not(0)));
		assertThat(standard.find("Template With Multiple IDs", "id1", "id2", "id3").getLocator(), is("//template/id1/id2/id3"));
		assertThat(standard.find(ILabel.class, "Template With Multiple IDs", "id4", "id5", "id6").getLocator(), is("//template/id4/id5/id6"));
	}
	
	@Test
	public void goToTest(){

		when(url.getURL()).thenReturn("valid/url");
		page.goTo();
		verify(driver).get(url.getURL());
	}
	
	@Test
	public void baseSubPageTests(){
		
		commonPageTests(sub);	
		
		assertThat(sub.getElements().size(), is(0));
	}
	
	@Test
	public void baseGenericReportWindowTests(){
		
		TargetLocator locator = mock(TargetLocator.class);
		
		commonPageTests(report);
		
		assertThat(report.hasWindowHandle(), is(false));
		assertThat(report.getReportWindowHandle(), is(""));
		
		report.setWindowHandle("GUID");
		assertThat(report.getReportWindowHandle(), is("GUID"));
		assertThat(report.hasWindowHandle(), is(true));
		
		when(driver.switchTo()).thenReturn(locator);
		when(locator.window("GUID")).thenReturn(driver);
		
		assertThat(report.switchToWindow().equals(report), is(true));
	}
		
	@Test
	public void testToString() {
		
		assertThat(page.toString(), is("\nLocation : " + page.getPageTitle()));
		
		SubPageA a = new SubPageA(standard, subPageATitle);
		SubPageB b = new SubPageB(standard, subPageBTitle);
		SubPageC c = new SubPageC(a, subPageCTitle);
		
		assertThat(a.toString(), is("\nLocation : " + pageName + "->" + a.getPageTitle()));
		assertThat(b.toString(), is("\nLocation : " + pageName + "->" + b.getPageTitle()));
		assertThat(c.toString(), is("\nLocation : " + pageName + "->" + a.getPageTitle() + "->" + c.getPageTitle()));
	}
}
