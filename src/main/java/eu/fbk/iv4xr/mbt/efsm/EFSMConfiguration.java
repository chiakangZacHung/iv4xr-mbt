package eu.fbk.iv4xr.mbt.efsm;

import java.io.Serializable;

import com.google.common.base.Objects;

public class EFSMConfiguration<State extends EFSMState, Context extends EFSMContext> 
	implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7881380032384648823L;

	private final State curState;
	private final Context context;

	public EFSMConfiguration(State curState, Context context) {
	    this.curState = curState;
	    this.context = context;
	  }

	public State getState() {
		return curState;
	}

	public Context getContext() {
		return context;
	}

	@Override
	public String toString() {
		return curState + " | " + context;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((curState == null) ? 0 : curState.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		EFSMConfiguration that = (EFSMConfiguration) o;
		return Objects.equal(curState, that.curState) && Objects.equal(context, that.context);
	}


}
