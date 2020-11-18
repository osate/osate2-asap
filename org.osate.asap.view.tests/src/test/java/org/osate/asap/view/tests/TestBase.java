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
package org.osate.asap.view.tests;

import org.eclipse.sirius.tests.swtbot.support.api.AbstractSiriusSwtBotGefTestCase;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class TestBase extends AbstractSiriusSwtBotGefTestCase {

	private static final String PLUGIN_ID = "org.osate.asap.view.tests";

	protected static final String AIRD_FILE = "representations.aird";
	private static final String SAFE2_FILE1 = "pca-interlock.safe2";
	private static final String SAFE2_FILE2 = "pulseox-forwarding.safe2";

	private static final String DATA_UNIT_DIR = "src/test/resources/safe2.1/";
	private static final String AADL_DIAGRAMS_DIR = "diagrams/";
	private static final String POFWRD_DIR = "pulseox-forwarding/";
	private static final String AADL_DIR = POFWRD_DIR + "aadl/";
	private static final String INSTANCE_DIR = AADL_DIR + "instances/";

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	// @formatter:off
	private static final String[] PROJECT_FILES = {
			AIRD_FILE,
			SAFE2_FILE1,
			POFWRD_DIR + SAFE2_FILE2,
			AADL_DIAGRAMS_DIR + "PulseOx_Forwarding_System_PulseOx_Forwarding_System_imp.aadl_diagram",
			AADL_DIR + "MAP_Error_Properties.aadl",
			AADL_DIR + "MAP_Errors.aadl",
			AADL_DIR + "MAP_Properties.aadl",
			AADL_DIR + "PulseOx_Forwarding_Clinician.aadl",
			AADL_DIR + "PulseOx_Forwarding_Display.aadl",
			AADL_DIR + "PulseOx_Forwarding_Errors.aadl",
			AADL_DIR + "PulseOx_Forwarding_Logic.aadl",
			AADL_DIR + "PulseOx_Forwarding_Patient.aadl",
			AADL_DIR + "PulseOx_Forwarding_Properties.aadl",
			AADL_DIR + "PulseOx_Forwarding_System.aadl",
			AADL_DIR + "PulseOx_Forwarding_Types.aadl",
			AADL_DIR + "PulseOx_Interface.aadl",
			AADL_DIR + "ErrorLibrary.aadl",
			INSTANCE_DIR + "PulseOx_Forwarding_Logic_PulseOx_Logic_Process_imp_Instance.aaxl2",
			INSTANCE_DIR + "PulseOx_Forwarding_System_PulseOx_Forwarding_System_imp_Instance.aaxl2"
	};
	// @formatter:on

	@Override
	protected void onSetUpBeforeClosingWelcomePage() throws Exception {
		copyFileToTestProject(PLUGIN_ID, DATA_UNIT_DIR, PROJECT_FILES);

	}

}