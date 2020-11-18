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
package org.osate.asap.model.safe2.builder;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.osate.aadl2.PropertyAssociation;
import org.osate.aadl2.instance.util.InstanceSwitch;
import org.osate.aadl2.modelsupport.modeltraversal.AadlProcessingSwitchWithProgress;

/* see
 * /org.osate.analysis.architecture.tests/src/org/osate/analysis/architecture/tests/CheckBindingConstraintsTest.xtend
 * and
 * /org.osate.core.tests/models/issue1620 + /org.osate.core.tests/src/org/osate/core/tests/issues/Issue1620Test.xtend
 * and
 * http://osate.org/dev/writing-tests.html#examples
 * */
public class MineSAFEAnnotationsSwitchHandler extends AadlProcessingSwitchWithProgress {

	private MineSAFEAnnotationsSwitchHandler(IProgressMonitor pm) {
		super(pm);
		// TODO Auto-generated constructor stub
	}

	public MineSAFEAnnotationsSwitchHandler() {
		this(new NullProgressMonitor());
	}

	public boolean propFound = false;

	@Override
	protected void initSwitches() {
		instanceSwitch = new MineSAFEAnnotationsSwitch();

	}

	private class MineSAFEAnnotationsSwitch extends InstanceSwitch<String> {

		@Override
		public String casePropertyAssociation(PropertyAssociation obj) {
			propFound = true;
			return DONE;
		}
	}
}
