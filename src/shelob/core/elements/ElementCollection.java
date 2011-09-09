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

// $codepro.audit.disable fieldJavadoc, methodJavadoc
package shelob.core.elements;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shelob.core.exceptions.LocalizationMismatchException;
import shelob.core.exceptions.NonExistentWebElementException;
import shelob.core.interfaces.elements.IElement;
import shelob.core.interfaces.elements.IElementCollection;



/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 *
 * The ElementCollection used by pages to reference static elements.
 */
public class ElementCollection implements IElementCollection {

	private final Map<String, IElement> map;
	
	/**
	 * Limited Scope Default Constructor
	 */
	private ElementCollection() {
		map = new HashMap<String, IElement>();
	}

	/**
	 * Factory Method
	 * @return a new instance of the ElementCollection;
	 */
	public static IElementCollection create() {
		return new ElementCollection();
	}
	
	public IElementCollection put(IElement element) {
		
		checkNotNull(element);
		
		// use the label as the key
		if (element.hasLocalizations())
			createLocalizedEntries(element);
		else
			map.put(element.getLabel(), element);
		
		return this;
	}
	
	public IElementCollection put(String label, IElement element) {
		
		checkNotNull(element);
		
		createLocalizedEntries(element);
		map.put(label, element);
				
		return this;
	}

	/**
	 * Create separate key/value pairs for localizations; makes it easier to find a control
	 * associated with a localized name
	 *	
	 * @param element The element with the localizations
	 */
	private void createLocalizedEntries(IElement element) {
		
		if (element.hasLocalizations()) {		
			for(String s : element.getLocalizations())
				map.put(s, element);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IElement> List<T> getElementsByType(Class<T> type) { // $codepro.audit.disable overloadedMethods
		
		checkNotNull(type);
		
		final List<T> list = new ArrayList<T>();
		
		for (IElement link : map.values()) {
			
			if (type.isInstance(link))
				list.add((T) link);
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T extends IElement> T find(String label) throws NonExistentWebElementException {
		
		checkNotNull(label);
		
		if (map.containsKey(label)) {
			final IElement element = map.get(label);
			
			if (element.hasLocalizations() && element.getLocalizations().contains(label))
				setLocalization(label, element);
			
			return (T) element;
		}
			
		throw new NonExistentWebElementException("The ElementCollection does not contain an associated IElement with the label : " + label );
	}

	@SuppressWarnings("unchecked")
	public <T extends IElement> T find(String label, String... identifiers) throws NonExistentWebElementException {
		
		checkNotNull(label);
		
		if (map.containsKey(label)) {
			
			final IElement element = map.get(label);
			element.setTemplateIdentifiers(identifiers);
			
			return (T) element;
		}
		
		throw new NonExistentWebElementException("The ElementColleciton does not contains an associated IElment with the label : " + identifiers);
	}

	public <T extends IElement> T find(Class<T> type, String label) throws NonExistentWebElementException { // $codepro.audit.disable overloadedMethods
		
		checkNotNull(type);
		checkNotNull(label);
		
		if (map.containsKey(label)){
			
			final IElement element = map.get(label);
			
			if (type.isInstance(element)){
				setLocalization(label, element);
				return type.cast(element);
			}
		}
		
		throw new NonExistentWebElementException("The ElementCollection does not contain an associated IElement with the type : " 
												  + type.getSimpleName() + " and the label : " + label);
	}

	public <T extends IElement> T find(Class<T> type, String label, String...identifiers) throws NonExistentWebElementException { // $codepro.audit.disable overloadedMethods
		
		checkNotNull(type);
		checkNotNull(label);
		
		if (map.containsKey(label)) {
			
			final IElement element = map.get(label);
			element.setTemplateIdentifiers(identifiers);
			
			return type.cast(element);
		}
		
		throw new NonExistentWebElementException("The ElementColleciton does not contains an associated IElment with the label : " + identifiers);
	}
	
	private void setLocalization(String label, IElement element) {
		
		if(element.hasLocalizations()) {
			
			if (element.getLocalizations().contains(label))
				element.setTemplateIdentifier(label);
			else
				throw new LocalizationMismatchException(String.format("The label : %s is not a Localization of the element : %s", label, element.toString()));
		}
	}
	
	public int size() {
		return map.size();
	}
	
	@Override
	public String toString() {
		
		final StringBuilder s = new StringBuilder();
		
		s.append("ElementCollection : ");
		
		for(Map.Entry<String, IElement> entry : map.entrySet() ) {
			
			s.append(System.getProperty("line.separator") + "Key : ");
			s.append(entry.getKey());
			s.append(System.getProperty("line.separator") + "Value : ");
			s.append(entry.getValue().toString());
		}
		
		return s.toString();
	}
}
