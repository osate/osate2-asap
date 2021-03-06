/*******************************************************************************
 * Architecture Supported Audit Processor
 *
 * Copyright 2020 Carnegie Mellon University.
 *
 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING INSTITUTE
 * MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON UNIVERSITY MAKES NO
 * WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED, AS TO ANY MATTER INCLUDING,
 * BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR PURPOSE OR MERCHANTABILITY,
 * EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF THE MATERIAL. CARNEGIE MELLON
 * UNIVERSITY DOES NOT MAKE ANY WARRANTY OF ANY KIND WITH RESPECT TO FREEDOM FROM
 * PATENT, TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 *
 * Released under an Eclipse Public License – v2.0-style license, please see
 * license.txt or contact permission@sei.cmu.edu for full terms.
 *
 * [DISTRIBUTION STATEMENT A] This material has been approved for public release
 * and unlimited distribution.  Please see Copyright notice for non-US Government
 * use and distribution.
 *
 * This Software includes and/or makes use of the following Third-Party Software
 * subject to its own license:
 *
 * 1. Open Source AADL Tool Environment (https://osate.org) Copyright 2004-2020
 * CMU-SEI.
 *
 * DM20-1063
 *******************************************************************************/
/**
 */
package org.osate.asap.model.safe2;

import org.eclipse.emf.common.util.EList;

import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.FeatureInstance;

import org.osate.xtext.aadl2.errormodel.errorModel.ErrorType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hazard</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.osate.asap.model.safe2.Hazard#getAccident <em>Accident</em>}</li>
 *   <li>{@link org.osate.asap.model.safe2.Hazard#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link org.osate.asap.model.safe2.Hazard#getEnvironmentElement <em>Environment Element</em>}</li>
 *   <li>{@link org.osate.asap.model.safe2.Hazard#getSystemElement <em>System Element</em>}</li>
 *   <li>{@link org.osate.asap.model.safe2.Hazard#getHazardousFactor <em>Hazardous Factor</em>}</li>
 *   <li>{@link org.osate.asap.model.safe2.Hazard#getErrorType <em>Error Type</em>}</li>
 * </ul>
 *
 * @see org.osate.asap.model.safe2.Safe2Package#getHazard()
 * @model
 * @generated
 */
public interface Hazard extends Fundamental {
	/**
	 * Returns the value of the '<em><b>Accident</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.osate.asap.model.safe2.Accident#getHazard <em>Hazard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accident</em>' container reference.
	 * @see #setAccident(Accident)
	 * @see org.osate.asap.model.safe2.Safe2Package#getHazard_Accident()
	 * @see org.osate.asap.model.safe2.Accident#getHazard
	 * @model opposite="hazard" required="true" transient="false"
	 * @generated
	 */
	Accident getAccident();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.Hazard#getAccident <em>Accident</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accident</em>' container reference.
	 * @see #getAccident()
	 * @generated
	 */
	void setAccident(Accident value);

	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' containment reference list.
	 * The list contents are of type {@link org.osate.asap.model.safe2.Constraint}.
	 * It is bidirectional and its opposite is '{@link org.osate.asap.model.safe2.Constraint#getHazard <em>Hazard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint</em>' containment reference list.
	 * @see org.osate.asap.model.safe2.Safe2Package#getHazard_Constraint()
	 * @see org.osate.asap.model.safe2.Constraint#getHazard
	 * @model opposite="hazard" containment="true" required="true"
	 * @generated
	 */
	EList<Constraint> getConstraint();

	/**
	 * Returns the value of the '<em><b>Environment Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Environment Element</em>' reference.
	 * @see #setEnvironmentElement(ComponentInstance)
	 * @see org.osate.asap.model.safe2.Safe2Package#getHazard_EnvironmentElement()
	 * @model
	 * @generated
	 */
	ComponentInstance getEnvironmentElement();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.Hazard#getEnvironmentElement <em>Environment Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Environment Element</em>' reference.
	 * @see #getEnvironmentElement()
	 * @generated
	 */
	void setEnvironmentElement(ComponentInstance value);

	/**
	 * Returns the value of the '<em><b>System Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>System Element</em>' reference.
	 * @see #setSystemElement(FeatureInstance)
	 * @see org.osate.asap.model.safe2.Safe2Package#getHazard_SystemElement()
	 * @model
	 * @generated
	 */
	FeatureInstance getSystemElement();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.Hazard#getSystemElement <em>System Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>System Element</em>' reference.
	 * @see #getSystemElement()
	 * @generated
	 */
	void setSystemElement(FeatureInstance value);

	/**
	 * Returns the value of the '<em><b>Hazardous Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hazardous Factor</em>' attribute.
	 * @see #setHazardousFactor(String)
	 * @see org.osate.asap.model.safe2.Safe2Package#getHazard_HazardousFactor()
	 * @model
	 * @generated
	 */
	String getHazardousFactor();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.Hazard#getHazardousFactor <em>Hazardous Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hazardous Factor</em>' attribute.
	 * @see #getHazardousFactor()
	 * @generated
	 */
	void setHazardousFactor(String value);

	/**
	 * Returns the value of the '<em><b>Error Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Error Type</em>' reference.
	 * @see #setErrorType(ErrorType)
	 * @see org.osate.asap.model.safe2.Safe2Package#getHazard_ErrorType()
	 * @model
	 * @generated
	 */
	ErrorType getErrorType();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.Hazard#getErrorType <em>Error Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Error Type</em>' reference.
	 * @see #getErrorType()
	 * @generated
	 */
	void setErrorType(ErrorType value);

} // Hazard
