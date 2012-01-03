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
package shelob.core.page;

import shelob.core.interfaces.IOpensNewWindow;

/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 *
 * @param <T> the Page type which is Navigable
 *
 * Abstract Template : Generic Report Window
 */
public abstract class GenericReportWindow<T> extends SubPage implements IOpensNewWindow<T> {

	private String handle;
	
	/**
	 * GenericReportWindow Default Constructor
	 * 
	 * @param parent the IPage parent which this page was invoked from
	 * @param title the title of the page
	 */
	protected GenericReportWindow(Page parent, String title) {
		super(parent, title);
	}
		
	/**
	 * @param handle
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setWindowHandle(String handle) {
		this.handle = handle;
		return (T) this; // $codepro.audit.disable unnecessaryCast
	}
	
	/**
	 * @return String
	 */
	public String getReportWindowHandle() {
		
		if (handle == null)
			return "";
		return handle;
	}	
	
	/**
	 * @return boolean
	 */
	public boolean hasWindowHandle() {
		return handle != null;
	}
	
	/**
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T switchToWindow() {
		getDriver().switchTo().window(getReportWindowHandle());
		return (T) this; // $codepro.audit.disable unnecessaryCast
	}
}
