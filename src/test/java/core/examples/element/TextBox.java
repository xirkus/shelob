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

import javax.annotation.concurrent.NotThreadSafe;

import shelob.core.LookUp;
import shelob.core.elements.Element;
import shelob.core.elements.ElementBuilder;
import shelob.core.interfaces.page.IPage;

import core.examples.element.interfaces.ITextBox;

/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 *
 * The TextBox primitive Element
 */
@NotThreadSafe
public final class TextBox extends Element implements ITextBox { // $codepro.audit.disable typeJavadoc

	/** // $codepro.audit.disable typeJavadoc
	 * The TextBox Builder
	 */
	public final static class Builder 
					    extends ElementBuilder<Builder> { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString
		
		private static final LookUp DEFAULT_LOOKUP = LookUp.ByXpath;
		private static final String TEMPLATE = "/input[@type='text' %s]";
		private static final String DEFAULT_IDENTIFIER = "";
		
		/**
		 * The TextBox Builder with Required Parameters
		 * 
		 * @param parent the IPage parent this element is found on
		 * @param lookup the LookUp strategy used to find the element
		 * @param locator the locator string used to specify the element
		 */
		public Builder(IPage parent, LookUp lookup, String locator) {
			super(parent, lookup, locator);
		}
		
		/**
		 * The TextBox Builder that uses the Default Lookup with a string identifier
		 * 
		 * @param parent the IPage parent this element is found on
		 * @param identifier the identifier used to differentiate the element from others like it
		 */
		public Builder(IPage parent, String identifier) {
			super(parent, DEFAULT_LOOKUP, String.format(TEMPLATE, String.format("and %s", identifier)));
		}
		
		/**
		 * The TextBox Builder tat uses the Default Lookup/Identifier
		 * 
		 * @param parent the IPage parent this element is found on
		 */
		public Builder(IPage parent) {
			super(parent, DEFAULT_LOOKUP, String.format(TEMPLATE, DEFAULT_IDENTIFIER));
		}
		
		/**
		 * The factory method
		 * 
		 * @return a new instance of the TextBox element */
		public ITextBox build() {
			
			final TextBox control = new TextBox(this); 
	    	
			configure(control);
	    	
			return control;
		} 
	}
	
	/**
	 * Limited Scope Default Constructor
	 * 
	 * @param builder the TextBox.Builder responsible for creating this object
	 */
	private TextBox(Builder builder) {
		super(builder.parent, builder.lookup, builder.locator, builder.label, null);
	}

	/**
	 * Method goToLink.
	 * @return T * @see com.stratahealth.test.core.interfaces.ICanLinkToOtherPage#goToLink(Class<T>) */
	@Override
	public <T extends IPage> T goToLink() { // $codepro.audit.disable overloadedMethods, methodJavadoc
		throw new UnsupportedOperationException("The TextBox object does not support links.");
	}
}
