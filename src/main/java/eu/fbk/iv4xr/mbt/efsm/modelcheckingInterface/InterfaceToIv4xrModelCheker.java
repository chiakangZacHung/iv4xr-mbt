package eu.fbk.iv4xr.mbt.efsm.modelcheckingInterface;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.fbk.iv4xr.mbt.efsm.EFSM;
import eu.fbk.iv4xr.mbt.efsm.EFSMConfiguration;
import eu.fbk.iv4xr.mbt.efsm.EFSMContext;
import eu.fbk.iv4xr.mbt.efsm.EFSMState;
import eu.fbk.iv4xr.mbt.efsm.EFSMTransition;
import eu.fbk.iv4xr.mbt.efsm.exp.Assign;
import eu.fbk.iv4xr.mbt.efsm.exp.Var;
import eu.fbk.iv4xr.mbt.efsm.exp.VarSet;
import eu.iv4xr.framework.extensions.ltl.IExplorableState;
import eu.iv4xr.framework.extensions.ltl.IState;
import eu.iv4xr.framework.extensions.ltl.ITargetModel;
import eu.iv4xr.framework.extensions.ltl.ITransition;

/**
 * This provides an implementation of an interface called
 * {@link eu.iv4xr.framework.extensions.ltl.ITargetModel}. This implementation
 * will allows iv4xr model checker (e.g.
 * {@link eu.iv4xr.framework.extensions.ltl.BasicModelChecker}) to target an
 * instance of EFSM to be model-checked.
 * 
 * @author Wishnu Prasetya
 *
 */
public class InterfaceToIv4xrModelCheker implements ITargetModel{

	/**
	 * The EFSM to be checked.
	 */
	EFSM efsm ;
	
	/**
	 * To keep track of the EFSM execution so far, during the model checking. 
	 * Also, so that we can backtrack along this execution.
	 */
	List<EFSMStateWrapper> history = new LinkedList<>() ;
	
	/**
	 * Create an instance of this interface, passing to it the EFSM that is to 
	 * be model-checked.
	 */
	public InterfaceToIv4xrModelCheker(EFSM efsm) {
		this.efsm = efsm ;
		this.reset();
	}
	
	@Override
	public void reset() {
		var state = new InterfaceToIv4xrModelCheker.EFSMStateWrapper(InterfaceToIv4xrModelCheker.cloneEFSMconfiguration(efsm.getInitialConfiguration())) ;
		history.clear();
		history.add(state) ;
	}

	@Override
	public EFSMStateWrapper getCurrentState() {
		return history.get(0) ;
	}

	@Override
	public boolean backTrackToPreviousState() {
		if(history.size() == 1) {
			// should contain at least one element!
			return false ;			
		}
		history.remove(0) ;
		return true ;
	}

	@Override
	public List<ITransition> availableTransitions() {
		Set<EFSMTransition> trans = efsm.getTransitons() ;
		List<ITransition> enabledTransitions = new LinkedList<>() ;
		EFSMConfiguration conf = ((InterfaceToIv4xrModelCheker.EFSMStateWrapper) getCurrentState()).conf ;
		EFSMState currentNode = conf.getState() ;
		EFSMContext currentContext = conf.getContext() ;
		for(var tr : trans) {
			if(tr.getSrc().equals(currentNode) && tr.isFeasible(currentContext)) {
				enabledTransitions.add(new InterfaceToIv4xrModelCheker.EFSMTransitionWrapper(tr)) ;
			}
		}
		return enabledTransitions ;
	}

	@Override
	public void execute(ITransition tr) {
		InterfaceToIv4xrModelCheker.EFSMTransitionWrapper tr_ = (InterfaceToIv4xrModelCheker.EFSMTransitionWrapper) tr;
		InterfaceToIv4xrModelCheker.EFSMStateWrapper currentState = (InterfaceToIv4xrModelCheker.EFSMStateWrapper) getCurrentState()
				.clone();

		// System.out.println(">>>> TRANS: " + tr_.tr) ;
		// System.out.println(" from: " + currentState.showState()) ;

		// The assignment-part of an EFSM-transition keeps a pointer to the target-variables of the assignment.
		// Since we keep cloning configurations, we need to first sync the values of these variables
		// with their values according to the current configuration :

		if (tr_.tr.getOp() != null && tr_.tr.getOp().getAssignments() != null) {
			// get the assignments of this transition:
			var assignments = tr_.tr.getOp().getAssignments().getHash();
			EFSMContext ctx = currentState.conf.getContext();
			// Sync the values of their target-vars with the context:
			for (var asg : assignments.entrySet()) {
				var asg_ = (Map.Entry<String,Assign>) asg;
				String vname = asg_.getKey();
				Assign A = asg_.getValue();
				Var v = ctx.getContext().getVariable(vname);
				if (v != null) {
					A.getVariable().setValue(v.getValue());
				}
			}
		}

		// execute the transition:
		tr_.tr.take(currentState.conf.getContext());
		
		// get the new state:
		EFSMConfiguration newState = new EFSMConfiguration(tr_.tr.getTgt(), currentState.conf.getContext());
		// System.out.println(" to : " + new
		// InterfaceToIv4xrModelCheker.EFSMStateWrapper(newState).showState()) ;
		history.add(0, new InterfaceToIv4xrModelCheker.EFSMStateWrapper(newState));
	}
	
	
	
	public static EFSMConfiguration cloneEFSMconfiguration(EFSMConfiguration conf) {
		return  new EFSMConfiguration(conf.getState().clone(),conf.getContext().clone()) ; 
	}
	
	public static class EFSMStateWrapper implements IExplorableState {
		
		public EFSMConfiguration conf ;
						
		public EFSMStateWrapper(EFSMConfiguration conf) {
			this.conf = conf ;
		}

		@Override
		public String showState() {
			return conf.toString() ;
		}
		
		@Override
		public String toString() {
			return conf.toString() ;
		}
	
		@Override
		public IExplorableState clone() {
			EFSMConfiguration conf_ = cloneEFSMconfiguration(conf) ;
			return new EFSMStateWrapper(conf_) ;
		}
		
		@Override 
		public int hashCode() {
			return conf.hashCode() ;  // --> show now produce correct hashing
			
			/* no longer needed:
			 
			var myvars = conf.getContext().getContext().getHash().entrySet() ;
			String z = conf.getState().getId() ;
			for(var e : myvars) {
				Entry<String,Var> e_ = (Entry<String,Var>) e;
				z += e_.getKey() + e_.getValue().eval().getVal() ;
			}
			
			//System.out.println("#### " + z) ;
			
			//return Objects.hashCode(conf.getState(), conf.getContext().getContext().getHash());
			//return conf.getState().getId().hashCode() ;
			return z.hashCode() ;
			*/
		}
		
		@Override
		public boolean equals(Object o) {
			if(!(o instanceof EFSMStateWrapper)) return false ;
			EFSMStateWrapper o_ = (EFSMStateWrapper) o ;
			if (this.conf == null)
				return o_.conf == null ;
			if (! this.conf.getState().equals(o_.conf.getState())) {
				return false ;
			}
	
			var myvars = this.conf.getContext().getContext() ;
			var othervars = o_.conf.getContext().getContext() ;
			
			return myvars.equals(othervars) ;
			
		}
	}

	public static class EFSMTransitionWrapper implements ITransition {
		public EFSMTransition tr ;
		public EFSMTransitionWrapper(EFSMTransition tr) { this.tr = tr ; }
		@Override
		public String getId() {
			return tr.getId() ;
		}
		
		@Override
		public String toString() {
			return tr.getId() + "[" + tr.getSrc().getId() + "->" + tr.getTgt().getId() + "]" ;
		}
	}



}
