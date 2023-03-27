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
package org.osate.asap.view;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.errormodel.instance.TypeTokenInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.FeatureInstance;
import org.osate.aadl2.instance.InstanceObject;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.slicer.SlicerRepresentation;

/**
 * Singleton that provides access to Slicer related functionality. We use a
 * singleton because a) Sirius's java services can't hold state, and b) the
 * initialization costs of the OSATE slicer are significant
 *
 * See Wikipedia for the code the lazy initialization is adapted from:
 * https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
 * @author Sam Procter
 *
 */
public class SlicerManager {

	private final SlicerRepresentation slicer;
	private SystemInstance currentSystem;

	/**
	 * Private constructor to enforce usage of singleton pattern
	 */
	private SlicerManager() {
		this.slicer = new SlicerRepresentation();
	}

	/**
	 * Holder class for instance -- see class level comments about why we use
	 * a singleton
	 */
	private static class LazyHolder {
		static final SlicerManager INSTANCE = new SlicerManager();
	}

	public static SlicerManager getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * Get all the "Successors" of the specified component, where successor is defined as another
	 * component that receives messages directly (ie, without any intermediaries) from this one.
	 *
	 * @param self The component to get the neighbors of
	 * @return The set of successors to the given component.
	 */
	public Collection<EObject> getSuccessorNeighbors(ComponentInstance component) {
		initGraph(component);
		return slicer.getNeighbors(component, true);
	}

	/**
	 * Get all the "Predecessors" of the specified component, where predecessor is defined as another
	 * component that sends messages directly (ie, without any intermediaries) to this one.
	 *
	 * @param self The component to get the neighbors of
	 * @return The set of predecessors to the given component.
	 */
	public Collection<EObject> getPredecessorNeighbors(ComponentInstance component) {
		initGraph(component);
		return slicer.getNeighbors(component, false);
	}

	/**
	 * Calculates the forward reachability of the given error type from the given port.
	 *
	 * @param et The error type to propagate forward
	 * @param feature The feature to propagate from
	 * @return Reachable components from the given parameters
	 */
	public Collection<EObject> forwardReach(TypeTokenInstance token, FeatureInstance feature) {
		initGraph(feature);
		return slicer.forwardReach(feature, token)
				.stream()
				.filter(p -> p.iobj() instanceof FeatureInstance)
				.map(p -> p.iobj())
				.collect(Collectors.toSet());
	}

	/**
	 * Calculates the backward reachability of the given error type from the given port.
	 *
	 * @param et The error type to propagate backward
	 * @param feature The feature to propagate from
	 * @return Reachable components from the given parameters
	 */
	public Collection<EObject> backwardReach(TypeTokenInstance token, FeatureInstance feature) {
		initGraph(feature);
		return slicer.backwardReach(feature, token)
				.stream()
				.filter(p -> p.iobj() instanceof FeatureInstance)
				.map(p -> p.iobj())
				.collect(Collectors.toSet());
	}

	/**
	 * Perform a simple backward reachability analysis from the given component
	 *
	 * @param component The component to start the analysis at
	 * @return Reachable components from the given parameter
	 */
	public Collection<EObject> backwardReach(ComponentInstance component) {
		initGraph(component);
		return slicer.backwardReach(component);
	}

	/**
	 * Perform a simple forward reachability analysis from the given component
	 *
	 * @param component The component to start the analysis at
	 * @return Reachable components from the given parameter
	 */
	public Collection<EObject> forwardReach(ComponentInstance component) {
		initGraph(component);
		return slicer.forwardReach(component);
	}

	private void initGraph(InstanceObject dobj) {
		var containingSystem = AsapUtil.getTopLevelContainingSystem(dobj);
		if (!containingSystem.equals(currentSystem)) {
			slicer.buildGraph(containingSystem);
			currentSystem = containingSystem;
		}
	}
}
