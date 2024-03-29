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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.table.metamodel.table.DLine;
import org.eclipse.sirius.table.model.business.internal.spec.DCellSpec;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.osate.aadl2.DirectionType;
import org.osate.aadl2.errormodel.instance.ConnectionPath;
import org.osate.aadl2.errormodel.instance.EMV2AnnexInstance;
import org.osate.aadl2.errormodel.instance.TypeInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.ConnectionReference;
import org.osate.aadl2.instance.FeatureInstance;
import org.osate.aadl2.parsesupport.AObject;
import org.osate.asap.model.safe2.Constraint;
import org.osate.xtext.aadl2.errormodel.errorModel.ErrorType;

/**
 * Services / supporting utility methods used by the SAFE2.0 view. Note this
 * portion of the docs:
 * https://www.eclipse.org/sirius/doc/specifier/general/Writing_Queries.html
 *
 * Warning: Java service methods should be stateless. There is no guarantee
 * that two successive invocations of the same service method on two model
 * elements (or even on the same one) will use the same instance of the
 * service class.
 *
 *  @author Sam Procter
 */
public class Services {

	/**
	 * Get all the "Neighbors" of the specified component, where neighbor is defined as another
	 * component that directly communicates (ie, without any intermediaries) with this one
	 *
	 * @param self The component the focus on
	 * @return The set of neighbors for the given component.
	 */
	public static Collection<EObject> getAllNeighbors(EObject self) {
		return Stream.of(getSuccessorNeighbors(self).stream(), getPredecessorNeighbors(self).stream())
				.flatMap(Function.identity()).collect(Collectors.toSet());
	}

	/**
	 * Get all the neighbors of the component's neighbors (cf {@link #getAllNeighbors(EObject)}.
	 * More specifically, get all the successor's successors and the predecessors predecessors.
	 *
	 * @param self The component to focus on
	 * @return The set of one-hop neighbors for the given component
	 */
	public static Collection<EObject> getOneHopNeighbors(EObject self) {
		Set<EObject> ret = new HashSet<>();
		final Collection<EObject> successorNeighbors = getSuccessorNeighbors(self);
		final Collection<EObject> predecessorNeighbors = getPredecessorNeighbors(self);
		for (EObject succ : successorNeighbors) {
			ret.addAll(getSuccessorNeighbors(succ));
		}
		for (EObject pred : predecessorNeighbors) {
			ret.addAll(getPredecessorNeighbors(pred));
		}
		ret.remove(self); // Filter out the object if there's a loop
		ret.removeAll(successorNeighbors); // And filter out any immediate neighbors that,
		ret.removeAll(predecessorNeighbors); // due to weird layouts, are also 1-hop neighbors

		return ret;
	}

	/**
	 * Get all the "Successors" of the specified component, where successor is defined as another
	 * component that receives messages directly (ie, without any intermediaries) from this one
	 *
	 * @param self The component to get the neighbors of
	 * @return The set of successors to the given component.
	 */
	public static Collection<EObject> getSuccessorNeighbors(EObject self) {
		return SlicerManager.getInstance().getSuccessorNeighbors((ComponentInstance) self);
	}

	/**
	 * Get all the "Predecessors" of the specified component, where predecessor is defined as another
	 * component that sends messages directly (ie, without any intermediaries) to this one
	 *
	 * Note that this is private since it is not called by the sirius view
	 *
	 * @param self The component to get the neighbors of
	 * @return The set of predecessors to the given component.
	 */
	private static Collection<EObject> getPredecessorNeighbors(EObject self) {
		return SlicerManager.getInstance().getPredecessorNeighbors((ComponentInstance) self);
	}

	public static EList<ComponentInstance> getAllComponents(EObject self) {
		return ((ComponentInstance) self).getAllComponentInstances();
	}

	public static Collection<EObject> getAllConnectionInstances(EObject self, EObject root) {
		return ((ComponentInstance) root).getConnectionInstances().stream().collect(Collectors.toSet());
	}

	public static boolean isLeafComponent(EObject self) {
		return ((ComponentInstance) self).getAllComponentInstances().size() == 1;
	}

	public static boolean isNestedComponent(EObject self) {
		return ((ComponentInstance) self).getContainingComponentInstance() != null;
	}

	/**
	 * Gets the current object's container.
	 *
	 * Used by: FamilyErrors.Table Creation CreateFamilyErrors.Advanced-Browse Expression
	 *
	 * @param self The object to get the container of
	 * @return The object's container
	 */
	public static EObject getContainer(EObject self) {
		return self.eContainer();
	}

	/**
	 * Returns true if this is the first connection between two components. Used to prevent double-creating
	 * edges between one-hop neighbors when those neighbors "close the loop" ie, are connected to each other.
	 *
	 * Note that this checks both directions -- ie, if the target is already connected to the source, this
	 * will block a connection from the source to the target.
	 *
	 * @param self Required by the Sirius infrastructure, but ignored.
	 * @param source The source node in the diagram
	 * @param target The target node in the diagram
	 * @return True if no connection exists, false otherwise
	 */
	public static boolean isFirstConnection(EObject self, DSemanticDecorator source, DSemanticDecorator target) {
		EdgeTarget eTarget = (EdgeTarget) target; // Explicit cast to avoid nuisance 'unlikely argument type' marker
		EdgeTarget eSource = (EdgeTarget) source; // Explicit cast to avoid nuisance 'unlikely argument type' marker
		for (DEdge edge : eSource.getIncomingEdges()) {
			if ((edge.getSourceNode()).equals(eTarget)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get the "root" error types referenced by a particular EObject. That is,
	 * for every error type referenced by self, we get the root of that type's
	 * hierarchy.
	 *
	 * Used by: TopLevelErrors.ErrorTypesTopLevelErrors.General-Semantic Candidates Expression
	 *
	 * @param self The component to get the error types from
	 * @return A set of root error types
	 */
	public static Collection<ErrorType> getRootErrorTypes(EObject self) {
		var ret = getErrorTypes((ComponentInstance) self, null).stream()
				.map(et -> AsapUtil.getRootType(et))
				.collect(Collectors.toSet());
		return ret;
	}

	/**
	 * Gets the root error types associated with each error type sent on a connection
	 *
	 * Used by: TopLevelErrors.HasError.General-Column Finder Expression
	 *
	 * @param self
	 * @return
	 */
	public static Collection<ErrorType> getRootErrorTypesByConnection(EObject self) {
		var ret = getErrorTypesByConnection(self).stream()
				.map(et -> AsapUtil.getRootType(et))
				.collect(Collectors.toSet());
		return ret;
	}

	/**
	 * Actually calculates the error types associated with either a component or connection instance.
	 * Exactly one of the two parameters should be null
	 *
	 * @param compInst A component instance, or null if a connection instance is provided
	 * @param connInst A connection instance, or null if a component instance is provided
	 * @return Error types sent on this connection or all connections in this component
	 */
	private static Collection<ErrorType> getErrorTypes(ComponentInstance compInst, ConnectionInstance connInst) {
		if (compInst != null && connInst != null) {
			// TODO: Use an exception?
			System.err.println("org.osate.asap.view.Services#getErrorTypes called incorrectly!");
			return null;
		}
		var containingSystem = connInst == null ? AsapUtil.getTopLevelContainingSystem(compInst)
				: AsapUtil.getTopLevelContainingSystem(connInst);
		var optionalAnnex = containingSystem.getAnnexInstances()
				.stream()
				.filter(a -> a instanceof EMV2AnnexInstance)
				.findFirst();
		if (optionalAnnex.isEmpty()) {
			return null; // No emv2 information, so no error types on this (or any) connection
		}
		var emv2Annex = (EMV2AnnexInstance) optionalAnnex.get();
		var errorTypes = emv2Annex.getPropagationPaths()
				.stream()
				.filter(p -> p instanceof ConnectionPath
						&& (connInst == null || ((ConnectionPath) p).getConnection().equals(connInst)))
				.flatMap(p -> Stream.concat(((ConnectionPath) p).getSourcePropagations().stream(),
						((ConnectionPath) p).getDestinationPropagations().stream()))
				.flatMap(p -> Stream.of(p.getInTypeSet(), p.getOutTypeSet()))
				.filter(ats -> ats != null)
				.flatMap(ats -> Stream.of(ats.getElements()))
				.flatMap(Collection::stream)
				.filter(tse -> tse instanceof TypeInstance)
				.map(ti -> ((TypeInstance) ti).getType())
				.collect(Collectors.toSet());
		return errorTypes;
	}

	/**
	 * Gets the error types sent on a connection
	 *
	 * Used by: FamilyErrors.HasError.General-Column Finder Expression
	 *
	 * @param self The connection
	 * @return A set of ErrorTypes sent on the connection
	 */
	public static Collection<ErrorType> getErrorTypesByConnection(EObject self) {
		return getErrorTypes(null, (ConnectionInstance) self);
	}

	/**
	 * Get text appropriate for the error type column header
	 *
	 * Used by:
	 * 	* FamilyErrors.ErrorTypesFamilyErrors.Label-Header Label Expression
	 * 	* TopLevelErrors.ErrorTypesTopLevelErrors.Label-Header Label Expression
	 *
	 * @param self The error type to get the title from
	 * @return Appropriate header text
	 */
	public static String getErrorTypeColumnHeader(EObject self) {
		return ((ErrorType) self).getName();
	}

	/**
	 * Used by:
	 * 	* FamilyErrors.ControlActionsFamilyErrors.Label-Header Label Expression
	 *  * TopLevelErrors.ControlActionsTopLevelErrors.Label-Header Label Expression
	 */
	public static String getControlActionRowHeader(EObject self) {
		return ((ConnectionInstance) self).getName();
	}

	/**
	 * Used by: FamilyErrors.ErrorTypesFamilyErrors.General-Semantic Candidates Expression
	 *
	 * @param self A connection. Currently unused, but required by Sirius.
	 * @param containerView The cell the user clicked, used for determining the error family.
	 * @return All error types that have the error type referenced by the cell's column as a supertype
	 */
	public static Collection<EObject> getFamilyErrorTypes(EObject self, DCellSpec containerView) {
		ErrorType root = (ErrorType) containerView.getColumn().getSemanticElements().get(0);
		ResourceSet rs = root.eResource().getResourceSet();
		ErrorTypeTreeBuilder ettb;

		Collection<EObject> subtypes = new HashSet<>();
		Queue<ErrorType> toProcess = new LinkedList<>();
		ErrorType curType;
		toProcess.add(root);
		while (!toProcess.isEmpty()) {
			ettb = new ErrorTypeTreeBuilder(rs); // Have to re-init for each call of findUsage to avoid NPE
			curType = toProcess.poll();
			Collection<ErrorType> newTypes = ettb.findUsage(curType).stream()
					.map(e -> (ErrorType) e.getEObject())
					.collect(Collectors.toSet());
			toProcess.addAll(newTypes);
			subtypes.addAll(newTypes);
		}
		return subtypes;
	}

	/**
	 * Gets incoming features for the supplied component instance. More
	 * specifically, the features whose DirectionType is IN
	 *
	 * @param self The component to get the in features for
	 * @return The set of features
	 */
	public static Collection<EObject> getInFeatures(EObject self) {
		return ((ComponentInstance) self).getFeatureInstances().stream()
				.filter(f -> f.getDirection() == DirectionType.IN).collect(Collectors.toSet());
	}

	/**
	 * Gets outgoing features for the supplied component instance. More
	 * specifically, the features whose DirectionType is OUT
	 *
	 * @param self The component to get the out features for
	 * @return The set of features
	 */
	public static Collection<EObject> getOutFeatures(EObject self) {
		return ((ComponentInstance) self).getFeatureInstances().stream()
				.filter(f -> f.getDirection() == DirectionType.OUT).collect(Collectors.toSet());
	}

	/**
	 * Gets bidirectional features for the supplied component instance. More
	 * specifically, the features whose DirectionType is IN_OUT
	 *
	 * @param self The component to get the bidirectional features for
	 * @return The set of features
	 */
	public static Collection<EObject> getInOutFeatures(EObject self) {
		return ((ComponentInstance) self).getFeatureInstances().stream()
				.filter(f -> f.getDirection() == DirectionType.IN_OUT).collect(Collectors.toSet());
	}

	/**
	 * Checks to see if the given EObject is part of whatever has been
	 * focused by the user
	 *
	 * @param self The component or connection to check
	 * @return True if the parameter is part of the user's focus, false otherwise
	 */
	@Deprecated
	public static boolean isFocused(EObject self) {
		return FocusManager.getInstance().isFocused(self);
	}

	public static boolean isOnlyForwardFocused(EObject self) {
		return FocusManager.getInstance().isOnlyForwardFocused(self);
	}

	public static boolean isOnlyBackwardFocused(EObject self) {
		return FocusManager.getInstance().isOnlyBackwardFocused(self);
	}

	public static boolean isDoublyFocused(EObject self) {
		return FocusManager.getInstance().isDoublyFocused(self);
	}

	public static boolean isFundamentalFocused(EObject self) {
		return FocusManager.getInstance().isFundamentalFocused(self);
	}

	/**
	 * Used by: UCA-Refinement.HasErrorUCA-Refinement.Label-Label Expression
	 *
	 * This computes the "label" for the UCA refinement tables, that is, the cause and compensation.
	 * Or, if none are found, the lack of documentation is described.
	 *
	 * Note that this also overrides the table's title, since the actual title expression doesn't
	 * have access to the data needed to set a useful title
	 *
	 * @param self The connection the errors are being analyzed for, this is used for the lookup
	 * into the SAFE2 data structure
	 * @param columnSemantic A reference to the errorType being used for the column, we use this to get
	 * the information necessary to set a useful title
	 * @param line A reference to the Sirius line object, this is used to set the table's title
	 * @return A list of user-specified causes or compensations or the phrase
	 * "Undocumented error propagation"
	 */
	public static String getCauseAndCompensation(EObject self, EObject columnSemantic, DLine line) {
		final String NO_DOCUMENTATION = "Undocumented error propagation";

		// This finds constraints which reference the current row in the SAFE2 data structure
		FeatureInstance fi = (FeatureInstance) ((ConnectionInstance) self).getSource();
		CauseAndCompensationFinder cacf = new CauseAndCompensationFinder(self.eResource().getResourceSet());
		Collection<Setting> relatedConstraints = cacf.findUsage(fi);

		String cause = "";
		String compensation = "";
		int i = 1;
		boolean found;
		// This grabs the correct cause and constraint for the the connection and sets the return values
		for (Setting s : relatedConstraints) {
			found = false;
			Constraint c = (Constraint) s.getEObject();
			FeatureInstance hRef = c.getHazard().getSystemElement();

			for (ConnectionReference cr : ((ConnectionInstance) self).getConnectionReferences()) {
				// Check to see if the system element referred to by the hazard associated with this constraint
				// is the same component as the destination of the current connection
				if (hRef == cr.getDestination()) {
					found = true;
				}
			}

			if (found) {
				cause += String.valueOf(i) + ". " + c.getCause() + "|";
				compensation += String.valueOf(i++) + ". " + c.getCompensation() + "|";
			}
		}

		if (!line.getLines().isEmpty()) {
			// These lines are what set the title of the table. Note we only set it in the cause, because
			// line.getContainer() returns the parent line for the compensation rows
//			Disabled Mar 5 2020 -- setName seems to have been removed
//			((DTable) line.getContainer()).setName("UCA-" + ((NamedElement) self.eContainer()).getName() + "-"
//					+ ((ErrorType) getRootErrorType(columnSemantic)).getName());
			return cause.isEmpty() ? NO_DOCUMENTATION : cause;
		} else {
			return compensation.isEmpty() ? NO_DOCUMENTATION : compensation;
		}
	}

	/**
	 * Used by: UCA-Refinement.Advanced-Title Expression
	 *
	 * This sets the table's title to a placeholder. The table's title is overridden / actually set as
	 * part of {@link #getCauseAndCompensation(EObject, EObject, DLine)}, because Sirius won't let me
	 * pass the necessary parameters to this method.
	 *
	 * @param self Unused.
	 * @return The word "PLACEHOLDER"
	 */
	public static String getUCARefinementTitle(EObject self) {
		return "PLACEHOLDER";
	}

	public static EObject duplicate(EObject self) {
		return self;
	}

	public static Collection<EObject> debug(EObject self) {
		return null;
	}

	public static boolean debugPrecondition(EObject self) {
		return true;
	}

	public static String debugLabel(EObject self) {
		return "DEBUG";
	}

	private static class CauseAndCompensationFinder extends UsageCrossReferencer {

		protected CauseAndCompensationFinder(ResourceSet resourceSet) {
			super(resourceSet);
		}

		private static final long serialVersionUID = 1L;

		@Override
		protected boolean containment(EObject eObject) {
			// This restricts the search to SAFE2 objects that point at the feature instance
			// (as opposed to, eg, sirius or AADL objects)
			return eObject instanceof org.osate.asap.model.safe2.Node;
		}

		@Override
		public Collection<Setting> findUsage(EObject eObject) {
			return super.findUsage(eObject);
		}
	}

	/**
	 * This class is used by {@link #getFamilyErrorTypes(EObject, DCellSpec)} to
	 * calculate all subtypes of a particular error type.
	 *
	 * This is lightly adapted from pages 524-525 of "EMF Eclipse Modeling
	 * Foundation" by Steinberg, Budinsky, Paternostro, and Merks (2nd ed)
	 *
	 * @author sprocter
	 *
	 */
	private static class ErrorTypeTreeBuilder extends UsageCrossReferencer {
		protected ErrorTypeTreeBuilder(ResourceSet resourceSet) {
			super(resourceSet);
		}

		private static final long serialVersionUID = 1L;

		@Override
		protected boolean crossReference(EObject eObject, EReference eReference, EObject referencedObj) {
			// We only want supertype references (left hand side) and those types
			// that actually cross-reference our object (right hand side)
			return eReference.getName().equals("superType") && super.crossReference(eObject, eReference, referencedObj);
		}

		@Override
		protected boolean containment(EObject eObject) {
			// This restricts the search to AADL objects that point at the error type
			// (as opposed to, eg, sirius objects)
			return eObject instanceof AObject;
		}

		@Override
		public Collection<Setting> findUsage(EObject eObject) {
			// we have to override this to change its visibility
			return super.findUsage(eObject);
		}
	}
}