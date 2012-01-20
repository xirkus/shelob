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

// $codepro.audit.disable fieldJavadoc, typeJavadoc, methodJavadoc, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString
package core;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import shelob.core.ApplicationParameters;
import shelob.core.LookUp;
import shelob.core.elements.Element;
import shelob.core.elements.ElementCollection;
import shelob.core.exceptions.LocalizationMismatchException;
import shelob.core.exceptions.NonExistentWebElementException;
import shelob.core.interfaces.elements.IElement;
import shelob.core.interfaces.elements.IElementCollection;
import shelob.core.interfaces.page.IPage;

import core.examples.element.Button;


public class ElementCollectionTests {
	
	@Mock IPage parent;
	@Mock ApplicationParameters parameters;
	@Mock IPage link;
	@Mock IElement mockElement;
	
	private static final LookUp LOOKUP = LookUp.ByXpath;
	private static final String LOCATOR = "//x/path/to/element";
	
	private IElementCollection collection;
	
	static class TestElement extends Element {

		TestElement(IPage parent, LookUp lookup, String locator, IPage link, String label) {
			super(parent, lookup, locator, label, link);
		}
	}

	static class TestElement2 extends Element {

		TestElement2(IPage parent, LookUp lookup, String locator, IPage link, String label) {
			super(parent, lookup, locator, label, link);
		}	
	}
	
	static class UnusedTestElement extends Element {
		
		UnusedTestElement(IPage parent, LookUp lookup, String locator, IPage link, String label) {
			super(parent, lookup, locator, label, link);
		}
	}
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		when(parent.getParameters()).thenReturn(parameters);
		when(parameters.getDefaultWait()).thenReturn(0);
		
		collection = ElementCollection.create()
									  .put(new TestElement(parent, LOOKUP, LOCATOR, link, "TestElement One").addLocalization("TestElement One Localized"))
									  .put(new TestElement(parent, LOOKUP, LOCATOR, link, "TestElement Two"))
									  
									  .put("Three", new TestElement(parent, LOOKUP, LOCATOR, link, "TestElement Three").addLocalization("TestElement Three Localized"))
									  
									  .put("Four", new TestElement(parent, LOOKUP, LOCATOR, link, "TestElement Four"))
									  .put(new TestElement2(parent, LOOKUP, "/xpath/%s/%s", link, "TestElement2 One"))
									  .put(new TestElement2(parent, LOOKUP, "/xpath/%s/%s/%s", link, "TestElement2 Two"))
									  
									  .put("Mock", mockElement)
									  ;
	}

	@After
	public void teardown() {}
	
	@Test
	public void threeCollectionEntriesCreatedWhenUsingLabelsWithLocalizedElements(){ // $codepro.audit.disable questionableName
		assertThat(collection.find("Three").equals(collection.find("TestElement Three")), is(true));
		assertThat(collection.find("Three").equals(collection.find("TestElement Three Localized")), is(true));
	}
	
	@Test
	public void baseElementCollectionTests(){
		
		assertThat(collection.size(), is(10));
		assertThat(collection.getElementsByType(TestElement.class).size(), is(7));
		assertThat(collection.getElementsByType(TestElement2.class).size(), is(2));
		
		assertThat(collection.find("TestElement One Localized").getLabel(), is("TestElement One"));
		assertThat(collection.find("Four").getLabel(), is("TestElement Four"));
		
		assertThat(collection.find(TestElement.class, "Four").getLabel(), is("TestElement Four"));
		
		IElement element2 = collection.find("TestElement2 Two", "id1", "id2", "id3");
		element2.setIsTemplate();
		assertThat(element2.getLocator(), is("/xpath/id1/id2/id3"));
		
		IElement element1 = collection.find(TestElement2.class, "TestElement2 One", "id1", "id2");
		element1.setIsTemplate();
		assertThat(element1.getLocator(), is("/xpath/id1/id2"));
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void testNonExistentGetElementByLabel(){
		collection.find("Non-existent");		
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void testNonExistentGetElementByTypeAndLabel(){
		collection.find(Button.class, "Non-existent");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void testNonExistentFindWithLabels(){
		collection.find("Non-existant", "identifier1");
	}
	
	@Test(expected = NonExistentWebElementException.class)
	public void testNonExistentWithClassAndLabels(){
		collection.find(Button.class, "Non-existant", "identifier1");
	}
	
	@Test(expected = LocalizationMismatchException.class)
	public void localizationMismatchException(){
		
		when(mockElement.hasLocalizations()).thenReturn(true);
		when(mockElement.getLocalizations()).thenReturn(new ArrayList<String>());
		
		collection.find(IElement.class, "Mock");
	}
	
	@Test
	public void toStringOverride() {
		
		assertThat(collection.toString().contains("ElementCollection : "), is(true));
	}	
}
