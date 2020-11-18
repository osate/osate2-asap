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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Accident Level</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.osate.asap.model.safe2.AccidentLevel#getAccident <em>Accident</em>}</li>
 * </ul>
 *
 * @see org.osate.asap.model.safe2.Safe2Package#getAccidentLevel()
 * @model
 * @generated
 */
public interface AccidentLevel extends Fundamental {
	/**
	 * Returns the value of the '<em><b>Accident</b></em>' containment reference list.
	 * The list contents are of type {@link org.osate.asap.model.safe2.Accident}.
	 * It is bidirectional and its opposite is '{@link org.osate.asap.model.safe2.Accident#getAccidentlevel <em>Accidentlevel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accident</em>' containment reference list.
	 * @see org.osate.asap.model.safe2.Safe2Package#getAccidentLevel_Accident()
	 * @see org.osate.asap.model.safe2.Accident#getAccidentlevel
	 * @model opposite="accidentlevel" containment="true" required="true"
	 * @generated
	 */
	EList<Accident> getAccident();

} // AccidentLevel
