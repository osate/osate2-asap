/**
 */
package org.osate.asap.model.safe2;

import org.eclipse.emf.common.util.EList;

import org.osate.aadl2.ComponentClassifier;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System Overview</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.osate.asap.model.safe2.SystemOverview#getFundamentals <em>Fundamentals</em>}</li>
 *   <li>{@link org.osate.asap.model.safe2.SystemOverview#getTopLevelElement <em>Top Level Element</em>}</li>
 * </ul>
 *
 * @see org.osate.asap.model.safe2.Safe2Package#getSystemOverview()
 * @model
 * @generated
 */
public interface SystemOverview extends Node {
	/**
	 * Returns the value of the '<em><b>Fundamentals</b></em>' containment reference list.
	 * The list contents are of type {@link org.osate.asap.model.safe2.Fundamental}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fundamentals</em>' containment reference list.
	 * @see org.osate.asap.model.safe2.Safe2Package#getSystemOverview_Fundamentals()
	 * @model containment="true"
	 * @generated
	 */
	EList<Fundamental> getFundamentals();

	/**
	 * Returns the value of the '<em><b>Top Level Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Top Level Element</em>' reference.
	 * @see #setTopLevelElement(ComponentClassifier)
	 * @see org.osate.asap.model.safe2.Safe2Package#getSystemOverview_TopLevelElement()
	 * @model
	 * @generated
	 */
	ComponentClassifier getTopLevelElement();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.SystemOverview#getTopLevelElement <em>Top Level Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Top Level Element</em>' reference.
	 * @see #getTopLevelElement()
	 * @generated
	 */
	void setTopLevelElement(ComponentClassifier value);

} // SystemOverview
