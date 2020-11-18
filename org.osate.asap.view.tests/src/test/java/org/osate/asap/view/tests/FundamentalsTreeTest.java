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
import org.eclipse.sirius.tree.DTree;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;

public class FundamentalsTreeTest extends TestBase {

	private static final String ORIG_REPRESENTATION_NAME = "Fundamentals";
	private static final String ORIG_REPRESENTATION_INSTANCE_NAME = "new Fundamentals";

	private static SWTBot treeBot;
	private static SWTBotEditor treeEditor;

	@Override
	protected void onSetUpAfterOpeningDesignerPerspective() throws Exception {
		super.onSetUpAfterOpeningDesignerPerspective();

		// TODO: This doesn't actually create a new diagram, and it probably should.
		// see org.eclipse.sirius.tests.swtbot.DiagramCreationDescriptionTest.testNewRepresentationFromNode()
		// for a possible implementation.

		sessionAirdResource = new UIResource(designerProject, AIRD_FILE);
		localSession = designerPerspective.openSessionFromFile(sessionAirdResource, true);
		treeEditor = openRepresentation(localSession.getOpenedSession(), ORIG_REPRESENTATION_NAME,
				ORIG_REPRESENTATION_INSTANCE_NAME, DTree.class);

		treeBot = treeEditor.bot();
	}

	@Test
	public void testTreeNodesExist() throws Throwable {
		SWTBotTreeItem doiItem = treeBot.tree().getTreeItem("DeathOrInjury");
		assertNotNull(doiItem);
		doiItem.expand();

		SWTBotTreeItem phItem = doiItem.getNode("PatientHarmed");
		assertNotNull(phItem);
		phItem.expand();

		SWTBotTreeItem bidItem = phItem.getNode("BadInfoDisplayed");
		assertNotNull(bidItem);
		bidItem.expand();
		assertNotNull(bidItem.getNode("ShowGoodInfo"));

		SWTBotTreeItem ilItem = phItem.getNode("InfoLate");
		assertNotNull(ilItem);
		ilItem.expand();
		assertNotNull(ilItem.getNode("ShowInfoOnTime"));
	}

	@Test
	public void testTreeCorrectSize() throws Throwable {
		SWTBotTreeItem doiItem = treeBot.tree().getTreeItem("DeathOrInjury").expand();
		SWTBotTreeItem phItem = doiItem.getNode("PatientHarmed").expand();
		phItem.getNode("BadInfoDisplayed").expand();
		phItem.getNode("InfoLate").expand();
		assertEquals(6, treeBot.tree().visibleRowCount());
	}
}