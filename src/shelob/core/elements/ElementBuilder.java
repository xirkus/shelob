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

// $codepro.audit.disable fieldJavadoc, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.preferInterfacesToAbstractClasses

package shelob.core.elements;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import shelob.core.LookUp;
import shelob.core.interfaces.elements.IElement;
import shelob.core.interfaces.page.IPage;


/**
 * @author melllaguno
 * @version $Revision : 1.0 $
 *
 * @param <T> The Builder Type being used for the fluent interface
 */
public abstract class ElementBuilder<T> {

	// Required parameters
	public final IPage parent;
	public final LookUp lookup;
	public final String locator;
	
	// Optional parameters
	// HACK : making this public due to visibility problems is probably a bad idea ...
	public String label;
	protected int defaultWaitIntervalInSeconds = 0;
	
	protected List<String> localizations;
	
	protected boolean isTemplate;
	protected boolean isRequired;
	protected String multiplesLocator;
	
	/**
	 * Generic Element Builder
	 * 
	 * @param parent the IPage which this element is found on
	 * @param lookup the LookUp strategy used to locate the element
	 * @param locator the locator string used to specify the element
	 */
	protected ElementBuilder(IPage parent, LookUp lookup, String locator) {	
		
		this.parent = checkNotNull(parent);
		this.lookup = checkNotNull(lookup);
		this.locator = checkNotNull(locator);
		
		localizations = new ArrayList<String>();
	}
	
	/**
	 * The Optional label parameter
	 * 
	 * @param label the label name
	 * @return this; fluent interface */
	@SuppressWarnings("unchecked")
	public T label(String label) {
		this.label = label;
		return (T) this; // $codepro.audit.disable unnecessaryCast
	}
	
	/**
	 * Localization Configuration Parameters
	 * 
	 * @param localization the localization name
	 * @return this; fluent interface
	 */
	@SuppressWarnings("unchecked")
	public T addLocalization(String localization) {
		localizations.add(localization);
		return (T) this; // $codepro.audit.disable unnecessaryCast
	}
	
	/**
	 * @return this; fluent interface
	 */
	@SuppressWarnings("unchecked")
	public T isTemplate() { // $codepro.audit.disable booleanMethodNamingConvention
		isTemplate = true;
		return (T) this; // $codepro.audit.disable unnecessaryCast
	}
	
	/**
	 * @return this; fluent interface
	 */
	@SuppressWarnings("unchecked")
	public T required() {
		isRequired = true;
		return (T) this; // $codepro.audit.disable unnecessaryCast
	}
	
	/**
	 * @param interval
	 * @return this; fluent interface
	 */
	@SuppressWarnings("unchecked")
	public T defaultWaitInterval(int interval){
		defaultWaitIntervalInSeconds = interval;
		return (T) this; // $codepro.audit.disable unnecessaryCast
	}
	
	/**
	 * @param locator
	 * @return this; fluent interface
	 */
	@SuppressWarnings("unchecked")
	public T hasMultiples(String locator){
		multiplesLocator = locator;
		return (T) this; 
	}
	
	/**
	 * @return String friendly string representation of this object
	 */
	@Override
	public String toString() {
		return "Parent : " + parent.getPageTitle() + 
			   " Element : " + this.getClass().getName() + 
			   " LookUp : " + lookup.name() + 
			   " Locator : " + locator; 
	}
	
	/**
	 * Helper method
	 * 
	 * @param control the newly created Element
	 */
	protected void configure(IElement control) {
		
		addLocalizations(control);
    	
    	if (isTemplate)
    		control.setIsTemplate();
    	
    	if (isRequired)
    		control.setRequired();
    	
    	if (multiplesLocator != null)
    		control.setMultiplesLocator(multiplesLocator);
    	
    	setWaitTimeOut(control);
	}

	/**
	 * Helper method
	 * 
	 * @param control the newly created Element
	 */
	private void setWaitTimeOut(IElement control) {
		
		// The builder defaultWaitIntervalInSeconds ALWAYS takes precedence
		if (defaultWaitIntervalInSeconds > 0)
    		control.setTimeout(defaultWaitIntervalInSeconds);
    	else {
    		
    		// Set the timeout globally using the ApplicationParameter setting
    		if (parent.getParameters().getDefaultWait() > 0)
    			control.setTimeout(parent.getParameters().getDefaultWait());
    	}
	}
	
	/**
	 * Helper method : adds localizations to a newly instantiated element
	 * 
	 * @param control the newly instantiated element
	 */
	private void addLocalizations(IElement control) {
		
		if (localizations.size() > 0){
    		    		
    		// NOTE: localizations can ONLY BE XPATH TEMPLATE EXPRESSIONS; 
    		//       otherwise there is no way to specify the alternatives
    		//		 @see com.stratahealth.test.core.IElementCollection#find
    		
    		control.setIsTemplate();
    		
    		for(String s : localizations)
    			control.addLocalization(s);
    	}
	}
}
