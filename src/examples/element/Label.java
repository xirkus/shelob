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
package examples.element;

import javax.annotation.concurrent.NotThreadSafe;

import shelob.core.LookUp;
import shelob.core.elements.Element;
import shelob.core.elements.ElementBuilder;
import shelob.core.interfaces.elements.ILinkable;
import shelob.core.interfaces.page.IPage;

import examples.element.interfaces.ILabel;

/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 *
 * The Label primitive Element
 */
@NotThreadSafe
public final class Label extends Element implements ILabel { // $codepro.audit.disable typeJavadoc

	/** // $codepro.audit.disable typeJavadoc
	 * The Label Builder
	 */
	public final static class Builder 
						extends ElementBuilder<Builder> 
						implements ILinkable<Builder> { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString

		private static final LookUp DEFAULT_LOOKUP = LookUp.ByXpath;
		private static final String TEMPLATE = "//a[contains(.,'%s')]";
		
		// Optional parameters
		private IPage linkTo;
		
		/**
		 * The Label Builder with Required Parameters
		 * 
		 * @param parent the IPage parent this element is found on
		 * @param lookup the LookUp strategy used to look up the element
		 * @param locator the locator string used to specify the element
		 */
		public Builder(IPage parent, LookUp lookup, String locator) {
			super(parent, lookup, locator);
		}
		
		/**
		 * The Label Builder that uses the Default Lookup with a string identifier
		 * 
		 * @param parent the IPage parent this element is found on
		 * @param identifier the identifier used to differentiate the element from others like it
		 */
		public Builder(IPage parent, String identifier) {
			super(parent, DEFAULT_LOOKUP, String.format(TEMPLATE, identifier));
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
		 * @return a new instance of the Label element 
		 */
	    public ILabel build() {
	    	
	    	final Label control = new Label(this); 
	    	
	    	configure(control);
	    	
			return control;
		}
	}
	
	/**
	 * Limited Scope Default Constructor
	 * 
	 * @param builder the Label.Builder responsible for creating this object
	 */
	protected Label(Builder builder){
		super(builder.parent, builder.lookup, builder.locator, builder.label, builder.linkTo);
	}	
}
