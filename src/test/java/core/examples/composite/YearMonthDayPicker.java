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

package core.examples.composite;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.concurrent.NotThreadSafe;

import shelob.core.LookUp;
import shelob.core.elements.Element;
import shelob.core.elements.ElementBuilder;
import shelob.core.interfaces.page.IPage;

import core.examples.composite.interfaces.IYearMonthDayPicker;
import core.examples.element.Dropdown;
import core.examples.element.TextBox;
import core.examples.element.interfaces.IDropdown;
import core.examples.element.interfaces.ITextBox;

/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 * 
 * Year/Month/Day Aggregate element
 */
@NotThreadSafe
public final class YearMonthDayPicker extends Element implements IYearMonthDayPicker {

	private final IDropdown day;
	private final IDropdown month;
	private final ITextBox year;
	
	/** // $codepro.audit.disable typeJavadoc
	 * The YearMonthDay Builder
	 */
	public static class Builder extends ElementBuilder<Builder>{

		private static final LookUp LOOKUP = LookUp.ByXpath;
		private static final String LOCATOR_BASE = "/following-sibling::td"; // the element that wraps the constructed elements
		
		private IDropdown day;
		private IDropdown month;
		private ITextBox year;
		
		/**
		 * The YearMonthDayPicker Builder with Required Fields
		 * 
		 * @param parent the IPage parent where the element is located
		 */
		public Builder(IPage parent) {
			super(parent, LOOKUP, LOCATOR_BASE);
		}

		/**
		 * The YearMonthDayPicker Builder which specifies the LocatorRoot
		 * @param parent the IPage parent where the element is located
		 * @param locatorRoot the locator root you want to use for this aggregate element
		 */
		public Builder(IPage parent, String locatorRoot) {
			super(parent, LOOKUP, locatorRoot);
		}
		
		/**
		 * Factory method
		 * 
		 * @return an instance of the YearMonthDayPicker element 
		 */
	    public IYearMonthDayPicker build() {

	    	// construct dependent controls here
	    	// the locators should be relative to the LOCATOR_BASE
	    	
	    	month = new Dropdown.Builder(parent, LookUp.ByXpath, "/select[@id='_monthField']").build();
	    	day = new Dropdown.Builder(parent, LookUp.ByXpath, "/select[@id='_dayField']").build();	    	
	    	year = new TextBox.Builder(parent, LookUp.ByXpath, "/input[@id='_yearField']").build();
	    	
	    	YearMonthDayPicker control = new YearMonthDayPicker(this);
	    	
	    	configure(control);
	    	
	    	return control;
		}
	}

	/**
	 * Limited Scope Default Constructor
	 * 
	 * @param builder the YearMonthDayPicker.Builder responsible for creating this object
	 */
	private YearMonthDayPicker(Builder builder) {
		super(builder.parent, builder.lookup, builder.locator, builder.label, null);
		
		month = checkNotNull(builder.month);
		month.setRelativeToParent(this);
		
		day = checkNotNull(builder.day);
		day.setRelativeToParent(this);
		
		year = checkNotNull(builder.year);
		year.setRelativeToParent(this);
	}
	
	/**
	 * Method month.
	 * @return IDropdown
	 * @see examples.composite.interfaces.stratahealth.test.framework.core.interfaces.composite.IYearMonthDayPicker#month()
	 */
	public IDropdown month() {
		return month;
	}

	/**
	 * Method day.
	 * @return IDropdown
	 * @see examples.composite.interfaces.stratahealth.test.framework.core.interfaces.composite.IYearMonthDayPicker#day()
	 */
	public IDropdown day() {
		return day;
	}

	/**
	 * Method year.
	 * @return ITextBox
	 * @see examples.composite.interfaces.stratahealth.test.framework.core.interfaces.composite.IYearMonthDayPicker#year()
	 */
	public ITextBox year() {
		return year;
	}
	
	/**
	 * Method goToLink.
	 * @return T * @see com.stratahealth.test.core.interfaces.ICanLinkToOtherPage#goToLink(Class<T>) */
	@Override
	public <T extends IPage> T goToLink() { // $codepro.audit.disable overloadedMethods, methodJavadoc
		throw new UnsupportedOperationException("The YearMonthDayPicker object does not support links.");
	}
	
	/**
	 * Method goToLink.
	 * @param type
	 * @return T * @see com.stratahealth.test.core.interfaces.ICanLinkToOtherPage#goToLink(Class<T>) */
	@Override
	public <T extends IPage> T goToLink(Class <T> type) { // $codepro.audit.disable overloadedMethods, methodJavadoc
		throw new UnsupportedOperationException("The YearMonthDayPicker object does not support links.");
	}
}
