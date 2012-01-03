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

// $codepro.audit.disable typeJavadoc, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, fieldJavadoc, methodJavadoc
package unit.core;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import shelob.core.ApplicationParameters;
import shelob.core.interfaces.page.IPage;

import examples.composite.YearMonthDayPicker;
import examples.composite.interfaces.IYearMonthDayPicker;

public class YearMonthDayPickerTests {

	@Mock IPage parentPage;
	@Mock ApplicationParameters parameters;
	
	private IYearMonthDayPicker picker;
	private IYearMonthDayPicker pickerWithLocatorRoot;
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		when(parentPage.getParameters()).thenReturn(parameters);
		when(parameters.getDefaultWait()).thenReturn(0);
		
		picker = new YearMonthDayPicker.Builder(parentPage).build();
		pickerWithLocatorRoot = new YearMonthDayPicker.Builder(parentPage, "//root").build();
	}
	
	@After
	public void teardown() {}
	
	@Test
	public void baseYearMonthDayPickerTests() {
		
		assertThat(picker.month().getLocator(), is("/following-sibling::td/select[@id='_monthField']"));
		assertThat(picker.day().getLocator(), is("/following-sibling::td/select[@id='_dayField']"));
		assertThat(picker.year().getLocator(), is("/following-sibling::td/input[@id='_yearField']"));
		
		assertThat(pickerWithLocatorRoot.month().getLocator(), is("//root/select[@id='_monthField']"));
		assertThat(pickerWithLocatorRoot.day().getLocator(), is("//root/select[@id='_dayField']"));
		assertThat(pickerWithLocatorRoot.year().getLocator(), is("//root/input[@id='_yearField']"));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void goToLinkException(){
		picker.goToLink();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void goToLinkWithClassException(){
		picker.goToLink(IPage.class);
	}
}
