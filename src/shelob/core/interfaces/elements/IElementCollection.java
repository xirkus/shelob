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

// $codepro.audit.disable methodJavadoc

package shelob.core.interfaces.elements;

import java.util.List;

import shelob.core.exceptions.NonExistentWebElementException;



/**
 * @author melllaguno
 * @version $Revision: 1.0 $
 * 
 * IElementCollection
 */
public interface IElementCollection {
	
	/**
	 * @return the size of the collection 
	 */
	int size();
	
	/**
	 * @param link the ILink added to the collection
	 * @return this; fluent interface 
	 */
	IElementCollection put(IElement link);
	
	/**
	 * @param label the label for the link
	 * @param link the ILink associated to the label
	 * @return this; fluent interface 
	 */
	IElementCollection put(String label, IElement link);
	
	/**
	 * @param type the element type
	 * @return a List of the Elements by Generic Type 
	 */
	<T extends IElement> List<T> getElementsByType(Class<T> type); 
	
	/**
	 * @param label the label of the element
	 * @return the IElement associated with the label 
	 * @throws NonExistentWebElementException when the element with the given label is not found 
	 */
	<T extends IElement> T find(String label) throws NonExistentWebElementException ; 
	
	/**
	 * @param <T> The Element Type
	 * @param label the ElementCollection label used to identify the element
	 * @param identifiers the labels required for the template
	 * @return the IElement associated with the labels
	 * @throws NonExistentWebElementException
	 */
	<T extends IElement> T find(String label, String...identifiers) throws NonExistentWebElementException;
	
	/**
	 * @param type the element type
	 * @param label the label associated with the element
	 * @return the IElement associated with the element type and label 
	 * @throws NonExistentWebElementException when the element with the give element type and label is not found <!-- // $codepro.audit.disable methodJavadoc -->
	 */
	<T extends IElement> T find(Class<T> type, String label) throws NonExistentWebElementException; // $codepro.audit.disable overloadedMethods
	
	/**
	 * @param <T> The Element Type
	 * @param type the element type
	 * @param label the ElementCollection label used to identify the element
	 * @param identifiers the labels required for the template
	 * @return the IElement associated with the element type and label
	 */
	<T extends IElement> T find(Class<T> type, String label, String...identifiers) throws NonExistentWebElementException;
}
