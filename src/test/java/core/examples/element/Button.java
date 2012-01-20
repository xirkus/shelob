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
import shelob.core.interfaces.elements.ILinkable;
import shelob.core.interfaces.page.IPage;

import core.examples.element.interfaces.IButton;

/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 *
 * The Button primitive Element 
 */
@NotThreadSafe
public final class Button extends Element implements IButton { // $codepro.audit.disable typeJavadoc

	/**
	 * The different classes of buttons
	 */
	public enum Type {
		
		/**
		 * the type attribute for the element is button
		 */
		Standard, 
		
		/**
		 * the type attribute for the element is submit
		 */
		Submit
	}
	
	/**  // $codepro.audit.disable typeJavadoc
	 * The Button Builder
	 */
	public final static class Builder 
						extends ElementBuilder<Builder> 
						implements ILinkable<Builder> { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString

		private static final LookUp DEFAULT_LOOKUP = LookUp.ByXpath;
		private static final String STANDARD_TEMPLATE = "//input[@type='button' %s]";
		private static final String SUBMIT_TEMPLATE = "//input[@type='submit' %s]";
		private static final String DEFAULT_IDENTIFIER = "";
		
		// Optional parameters
		private IPage linkTo;
		
		/**
		 * The Button Builder with Required Fields
		 * 
		 * @param parent the IPage parent where the element is located
		 * @param lookup the LookUp strategy used to locate the element
		 * @param locator the locator string used to specify the element
		 */
		public Builder(IPage parent, LookUp lookup, String locator) {
			super(parent, lookup, locator);
		}
		
		/**
		 * The Button Builder that uses the Default Lookup with a string identifier
		 * 
		 * @param parent the IPage parent this element is found on
		 * @param type The Button.Type for this element
		 * @param identifier the identifier used to differentiate the element from others like it
		 */
		public Builder(IPage parent, Type type, String identifier) {
			super(parent, 
				  DEFAULT_LOOKUP, 
				  (type.equals(Type.Standard) ? String.format(STANDARD_TEMPLATE, String.format("and %s", identifier))
						               		  : String.format(SUBMIT_TEMPLATE, String.format("and %s", identifier)))
				  );
		}
		
		/**
		 * The Button Builder tat uses the Default Lookup/Identifier
		 * 
		 * @param parent the IPage parent this element is found on
		 * @param type the Button.Type for this element
		 */
		public Builder(IPage parent, Type type) {
			super(parent, 
				  DEFAULT_LOOKUP, 
				  (type.equals(Type.Standard) ? String.format(STANDARD_TEMPLATE, DEFAULT_IDENTIFIER)
						  					  : String.format(SUBMIT_TEMPLATE, DEFAULT_IDENTIFIER))
				  );
		}
		
		/**
		 * The Optional linksTo parameter
		 * 
		 * @param page the IPage this element links to
		 * @return this; fluent interface 
		 */
		public Builder linksTo(IPage page) {
			linkTo = page;
			return this;
		}
		
		/**
		 * The factory method
		 * 
		 * @return an instance of the Button Element 
		 */
	    public IButton build() {
	    	
	    	final Button control = new Button(this);
	    	
	    	configure(control);
	    	
			return control;
		}
	}
	
	/**
	 * Limited Scope Default Constructor
	 * 
	 * @param builder the Button.Builder responsible for creating this object
	 */
	private Button(Builder builder) {
		super(builder.parent, builder.lookup, builder.locator, builder.label, builder.linkTo);
	}
}
