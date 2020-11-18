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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DNodeSpec;
import org.eclipse.sirius.tests.swtbot.support.api.business.UIResource;
import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusDiagramEditor;
import org.eclipse.sirius.tree.DTree;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefConnectionEditPart;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.Test;

public class ConnectedNeighborDiagramTests extends TestBase {


	private static final String ORIG_REPRESENTATION_NAME = "Containment";
	private static final String ORIG_REPRESENTATION_INSTANCE_NAME = "new Containment";
	private static final String MAIN_REPRESENTATION_NAME = "ConnectedNeighbors";
	private static final String MAIN_REPRESENTATION_INSTANCE_NAME = "Neighbors of appLogic";
	@Override
	protected void onSetUpAfterOpeningDesignerPerspective() throws Exception {
		super.onSetUpAfterOpeningDesignerPerspective();

		sessionAirdResource = new UIResource(designerProject, AIRD_FILE);
		localSession = designerPerspective.openSessionFromFile(sessionAirdResource, true);
		SWTBotEditor treeEditor = openRepresentation(localSession.getOpenedSession(), ORIG_REPRESENTATION_NAME,
				ORIG_REPRESENTATION_INSTANCE_NAME, DTree.class);

		SWTBot treeBot = treeEditor.bot();
		treeBot.tree().getTreeItem("appLogic").contextMenu().contextMenu("New").contextMenu("NewConnectedNeighbors")
				.click();
		treeBot.shell("New ConnectedNeighbors").bot().button("OK").click();

		editor = (SWTBotSiriusDiagramEditor) openRepresentation(localSession.getOpenedSession(),
				MAIN_REPRESENTATION_NAME, MAIN_REPRESENTATION_INSTANCE_NAME, DDiagram.class);
	}

	@Test
	public void testNodesExist() throws Throwable {
		assertNotNull(editor.getEditPart("appLogic"));
		assertNotNull(editor.getEditPart("pulseOx"));
		assertNotNull(editor.getEditPart("appDisplay"));
		assertNotNull(editor.getEditPart("clinician"));
		assertNotNull(editor.getEditPart("patient"));
	}

	@Test
	public void testCorrectEdgesExist() throws Throwable {
		Set<String> edges = getEdges();
		assertEquals(5, edges.size());
		assertTrue(edges.contains("appLogic->appDisplay"));
		assertTrue(edges.contains("appDisplay->clinician"));
		assertTrue(edges.contains("clinician->patient"));

		// This is backwards, but we fudge the arrow ends in the .odesign file so
		// it looks right
		assertTrue(edges.contains("pulseOx->patient"));
		assertTrue(edges.contains("appLogic->pulseOx"));
	}

	@Test
	public void testIncorrectEdgesDontExist() throws Throwable {
		Set<String> edges = getEdges();
		assertFalse(edges.contains("appDisplay->appLogic"));
		assertFalse(edges.contains("appDisplay->patient"));
		assertFalse(edges.contains("appDisplay->pulseOx"));
		assertFalse(edges.contains("appDisplay->appDisplay"));

		assertFalse(edges.contains("clinician->appLogic"));
		assertFalse(edges.contains("clinician->appDisplay"));
		assertFalse(edges.contains("clinician->pulseOx"));
		assertFalse(edges.contains("clinician->clinician"));

		assertFalse(edges.contains("pulseOx->appDisplay"));
		assertFalse(edges.contains("pulseOx->clinician"));
		assertFalse(edges.contains("pulseOx->pulseOx"));

		assertFalse(edges.contains("patient->appLogic"));
		assertFalse(edges.contains("patient->appDisplay"));
		assertFalse(edges.contains("patient->clinician"));
		assertFalse(edges.contains("patient->patient"));

		assertFalse(edges.contains("appLogic->clinician"));
		assertFalse(edges.contains("appLogic->patient"));
		assertFalse(edges.contains("appLogic->appLogic"));

		// Some edges are discovered / routed in reverse order, so we
		// test their opposite here
		assertFalse(edges.contains("patient->pulseOx"));
		assertFalse(edges.contains("pulseOx->appLogic"));
	}

	private Set<String> getEdges() {
		Set<String> edges = new HashSet<>();
		for (SWTBotGefConnectionEditPart cep : editor.getConnectionsEditPart()) {
			DNodeSpec sourceNS = (DNodeSpec) ((NodeImpl) cep.source().part().getModel()).basicGetElement();
			DNodeSpec targetNS = (DNodeSpec) ((NodeImpl) cep.target().part().getModel()).basicGetElement();
			edges.add(sourceNS.getName() + "->" + targetNS.getName());
		}
		return edges;
	}
}