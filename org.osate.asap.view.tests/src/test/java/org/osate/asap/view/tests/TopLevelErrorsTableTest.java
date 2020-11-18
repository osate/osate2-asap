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

import org.eclipse.sirius.tests.swtbot.support.api.business.UIResource;
import org.eclipse.sirius.tests.swtbot.support.api.business.UITableRepresentation;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;

public class TopLevelErrorsTableTest extends TestBase {

	private static final String ORIG_REPRESENTATION_NAME = "TopLevelErrors";
	private static final String ORIG_REPRESENTATION_INSTANCE_NAME = "new TopLevelErrors";

	@Override
	protected void onSetUpAfterOpeningDesignerPerspective() throws Exception {
		super.onSetUpAfterOpeningDesignerPerspective();

		sessionAirdResource = new UIResource(designerProject, AIRD_FILE);
		localSession = designerPerspective.openSessionFromFile(sessionAirdResource, true);

		localSession.getLocalSessionBrowser().perCategory().selectViewpoint("Hazard Analyst")
				.selectRepresentation(ORIG_REPRESENTATION_NAME)
				.selectRepresentationInstance(ORIG_REPRESENTATION_INSTANCE_NAME, UITableRepresentation.class).delete();

		final UITableRepresentation table = localSession.getLocalSessionBrowser().perCategory()
				.selectViewpoint("Hazard Analyst").selectRepresentation(ORIG_REPRESENTATION_NAME)
				.selectRepresentationInstance(ORIG_REPRESENTATION_INSTANCE_NAME, UITableRepresentation.class).open();
		SWTBotTreeItem[] items = table.getTable().getAllItems();
		System.err.println("items[0].cell(0) is " + items[0].cell(0));
		System.err.println("items[1].cell(1) is " + items[1].cell(1));
	}

	@Test
	public void testNothing() throws Throwable {
		assertTrue(false);
	}
}