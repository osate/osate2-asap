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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.errormodel.instance.TypeTokenInstance;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.FeatureInstance;
import org.osate.asap.model.safe2.Accident;
import org.osate.asap.model.safe2.AccidentLevel;
import org.osate.asap.model.safe2.Constraint;
import org.osate.asap.model.safe2.Fundamental;
import org.osate.asap.model.safe2.Hazard;

/**
 * Singleton that provides access to focus related functionality. We use a
 * singleton because a) Sirius's java services can't hold state, and b) we
 * never want multiple things focused at the same time.
 *
 * See Wikipedia for the code the lazy initialization is adapted from:
 * https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
 * @author Sam Procter
 *
 */
public class FocusManager {

	/**
	 * The user's focus
	 */
	private EObject focus;

	/**
	 * The set of elements included as part of the user's focus
	 */
	// Use hash sets since we mostly do lookups (less, but still sometimes: inserts & clears)
	private Set<EObject> forwardFocusSet = new HashSet<>();
	private Set<EObject> backwardFocusSet = new HashSet<>();
	private Set<Fundamental> fundamentalFocusSet = new HashSet<>();

	/**
	 * Private constructor to enforce singleton pattern. Note that we also
	 * initialize the focus to null.
	 */
	private FocusManager() {
		focus = null;
	}

	private static class LazyHolder {
		static final FocusManager INSTANCE = new FocusManager();
	}

	public static FocusManager getInstance() {
		return LazyHolder.INSTANCE;
	}

	public EObject getFocus() {
		return focus;
	}

	@Deprecated
	public boolean isFocused(EObject element) {
		return false;
	}

	public boolean isOnlyForwardFocused(EObject element) {
		return forwardFocusSet.contains(element) && (!backwardFocusSet.contains(element));
	}

	public boolean isOnlyBackwardFocused(EObject element) {
		return (!forwardFocusSet.contains(element)) && backwardFocusSet.contains(element);
	}

	public boolean isDoublyFocused(EObject element) {
		return forwardFocusSet.contains(element) && backwardFocusSet.contains(element);
	}

	public boolean isFundamentalFocused(EObject element) {
		return fundamentalFocusSet.contains(element);
	}

	public void setFocus(EObject newFocus) {
		focus = newFocus;
		clearFocus();

		if (newFocus instanceof Fundamental) {
			handleFocusedFundamental((Fundamental) newFocus);
		} else if (newFocus instanceof ComponentInstance) {
			handleFocusedComponent((ComponentInstance) newFocus);
		}


		// components need to add themselves, as well as connections between themselves

		/*-

		 * Expandable Things
		 	* Under Fundamentals: (probz collapsable under their own thing, since Accident Levels would have like a trillion)
		 		* Affected Components
		 	* Under Components on Containment view:
				* Affected Fundamentals
		 * Focusable Things
		 	* Fundamentals
		 	* Components
		 * Calkalations
			 * Fundamental -> Error Type @ Component -> Forward and Backward Slice
			 * Component -> Error Type set -> For each, forward and backward slice -> Fundamentals (based on constraint-Error Type links)

		 */
	}

	private void handleFocusedComponent(ComponentInstance newFocus) {
		backwardFocusSet.addAll(SlicerManager.getInstance().backwardReach(newFocus));
		forwardFocusSet.addAll(SlicerManager.getInstance().forwardReach(newFocus));
	}

	private void handleFocusedFundamental(Fundamental newFocus) {
		fundamentalFocusSet.add(newFocus);
		if (newFocus instanceof AccidentLevel) {
			for (Accident a : ((AccidentLevel) newFocus).getAccident()) {
				handleFocusedFundamental(a);
			}
		}
		if (newFocus instanceof Accident) {
			for (Hazard h : ((Accident) newFocus).getHazard()) {
				handleFocusedFundamental(h);
			}
		}
		if (newFocus instanceof Hazard) {
			handleFocusedErrorType(((Hazard) newFocus).getErrorType(), ((Hazard) newFocus).getSystemElement());
			for (Constraint c : ((Hazard) newFocus).getConstraint()) {
				handleFocusedFundamental(c);
			}
		}
		if (newFocus instanceof Constraint) {
			handleFocusedErrorType(((Constraint) newFocus).getHazard().getErrorType(),
					((Constraint) newFocus).getPort());
		}
	}

	private void handleFocusedErrorType(TypeTokenInstance token, FeatureInstance feature) {
		backwardFocusSet.addAll(SlicerManager.getInstance().backwardReach(token, feature));
		forwardFocusSet.addAll(SlicerManager.getInstance().forwardReach(token, feature));
	}

	public void clearFocus() {
		forwardFocusSet.clear();
		backwardFocusSet.clear();
		fundamentalFocusSet.clear();
	}
}
