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

import org.osate.aadl2.instance.FeatureInstance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.osate.asap.model.safe2.Constraint#getHazard <em>Hazard</em>}</li>
 *   <li>{@link org.osate.asap.model.safe2.Constraint#getPort <em>Port</em>}</li>
 *   <li>{@link org.osate.asap.model.safe2.Constraint#getCause <em>Cause</em>}</li>
 *   <li>{@link org.osate.asap.model.safe2.Constraint#getCompensation <em>Compensation</em>}</li>
 * </ul>
 *
 * @see org.osate.asap.model.safe2.Safe2Package#getConstraint()
 * @model
 * @generated
 */
public interface Constraint extends Fundamental {
	/**
	 * Returns the value of the '<em><b>Hazard</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.osate.asap.model.safe2.Hazard#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hazard</em>' container reference.
	 * @see #setHazard(Hazard)
	 * @see org.osate.asap.model.safe2.Safe2Package#getConstraint_Hazard()
	 * @see org.osate.asap.model.safe2.Hazard#getConstraint
	 * @model opposite="constraint" required="true" transient="false"
	 * @generated
	 */
	Hazard getHazard();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.Constraint#getHazard <em>Hazard</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hazard</em>' container reference.
	 * @see #getHazard()
	 * @generated
	 */
	void setHazard(Hazard value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' reference.
	 * @see #setPort(FeatureInstance)
	 * @see org.osate.asap.model.safe2.Safe2Package#getConstraint_Port()
	 * @model
	 * @generated
	 */
	FeatureInstance getPort();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.Constraint#getPort <em>Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' reference.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(FeatureInstance value);

	/**
	 * Returns the value of the '<em><b>Cause</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cause</em>' attribute.
	 * @see #setCause(String)
	 * @see org.osate.asap.model.safe2.Safe2Package#getConstraint_Cause()
	 * @model default="" unique="false" ordered="false"
	 * @generated
	 */
	String getCause();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.Constraint#getCause <em>Cause</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cause</em>' attribute.
	 * @see #getCause()
	 * @generated
	 */
	void setCause(String value);

	/**
	 * Returns the value of the '<em><b>Compensation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compensation</em>' attribute.
	 * @see #setCompensation(String)
	 * @see org.osate.asap.model.safe2.Safe2Package#getConstraint_Compensation()
	 * @model unique="false" ordered="false"
	 * @generated
	 */
	String getCompensation();

	/**
	 * Sets the value of the '{@link org.osate.asap.model.safe2.Constraint#getCompensation <em>Compensation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compensation</em>' attribute.
	 * @see #getCompensation()
	 * @generated
	 */
	void setCompensation(String value);

} // Constraint
