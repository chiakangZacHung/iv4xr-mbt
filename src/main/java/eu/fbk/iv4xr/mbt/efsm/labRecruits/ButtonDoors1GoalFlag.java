package eu.fbk.iv4xr.mbt.efsm.labRecruits;

import eu.fbk.iv4xr.mbt.efsm.EFSM;
import eu.fbk.iv4xr.mbt.efsm.EFSMBuilder;
import eu.fbk.iv4xr.mbt.efsm.EFSMContext;
import eu.fbk.iv4xr.mbt.efsm.EFSMGuard;
import eu.fbk.iv4xr.mbt.efsm.EFSMOperation;
import eu.fbk.iv4xr.mbt.efsm.EFSMParameter;
import eu.fbk.iv4xr.mbt.efsm.EFSMState;
import eu.fbk.iv4xr.mbt.efsm.EFSMTransition;
import eu.fbk.iv4xr.mbt.efsm.exp.Assign;
import eu.fbk.iv4xr.mbt.efsm.exp.Const;
import eu.fbk.iv4xr.mbt.efsm.exp.Var;
import eu.fbk.iv4xr.mbt.efsm.exp.bool.BoolNot;
import eu.fbk.iv4xr.mbt.efsm.exp.integer.IntSubt;
import eu.fbk.iv4xr.mbt.efsm.exp.integer.IntSum;

/**
 * 
 * This level add to button_doors_1 used for test mbt a goal flag in
 * the final room
 * @author prandi
 *
 */
public class ButtonDoors1GoalFlag {
	
	/*
	 *  States
	 */
	
	// buttons
	public EFSMState b_0 = new EFSMState("b_0");
	public EFSMState b_1 = new EFSMState("b_1");
	public EFSMState b_2 = new EFSMState("b_2");
	public EFSMState b_3 = new EFSMState("b_3");
	
	// doors
	public EFSMState d_1_m = new EFSMState("d_1_m");
	public EFSMState d_1_p = new EFSMState("d_1_p");
	public EFSMState d_2_m = new EFSMState("d_2_m");
	public EFSMState d_2_p = new EFSMState("d_2_p");
	public EFSMState d_3_m = new EFSMState("d_3_m");
	public EFSMState d_3_p = new EFSMState("d_3_p");
	public EFSMState d_T_m = new EFSMState("d_T_m");
	public EFSMState d_T_p = new EFSMState("d_T_p");
	
	// flag
	public EFSMState f_0 = new EFSMState("f_0");
	
	
	/*
	 * Variables
	 */
	
	// context variables
	// doors
	public Var<Boolean> d_1 = new Var<Boolean>("d_1", false);
	public Var<Boolean> d_2 = new Var<Boolean>("d_2", false);
	public Var<Boolean> d_T = new Var<Boolean>("d_T", false);
	
	// score
	// see a new button: score + 1
	// press a new button: + 10
	// reach a goal flag: + 100
	//public Var<Integer> score = new Var<Integer>("score",0);
	
	// record if a button is already seen
	//public Var<Boolean> visited_b_0 = new Var<Boolean>("visited_b_0",false);
	//public Var<Boolean> visited_b_1 = new Var<Boolean>("visited_b_1",false);
	//public Var<Boolean> visited_b_2 = new Var<Boolean>("visited_b_2",false);
	//public Var<Boolean> visited_b_3 = new Var<Boolean>("visited_b_3",false);
	
	// record if a button is already pressed
	//public Var<Boolean> pressed_b_0 = new Var<Boolean>("pressed_b_0",false);
	//public Var<Boolean> pressed_b_1 = new Var<Boolean>("pressed_b_1",false);
	//public Var<Boolean> pressed_b_2 = new Var<Boolean>("pressed_b_2",false);
	//public Var<Boolean> pressed_b_3 = new Var<Boolean>("pressed_b_3",false);
	
	// record if a flag is already visited
	//public Var<Boolean> visited_f_0 = new Var<Boolean>("visited_f_0",false);
	
	
	// input variables
	public Var<LRActions> action = new Var<LRActions>("action", null );
	
	public ButtonDoors1GoalFlag() {};
	
	
	public EFSM getModel() {
		
		// toggle input parameter
		Var toggleVar = new Var<LRActions>("toggle", LRActions.TOGGLE);
		EFSMParameter inputParToggle = new EFSMParameter(toggleVar);

		// explore input parameter
		Var exploreVar = new Var<LRActions>("explore", LRActions.EXPLORE);
		EFSMParameter inputParExplore = new EFSMParameter(exploreVar);
		
		/*
		 * Context
		 */
		EFSMContext bd1Context = new EFSMContext(
				d_1,d_2,d_T//,score, //  doors
				//visited_b_0, visited_b_1, visited_b_2, visited_b_3, // button visited
				//pressed_b_0, pressed_b_1, pressed_b_2, pressed_b_3, // button pressed
				//visited_f_0 // flag visited
				); 

		
		/*
		 * Guards
		 */
		// check if a door is open
		EFSMGuard is_d_1_open = new EFSMGuard(d_1);
		EFSMGuard is_d_2_open = new EFSMGuard(d_2);
		EFSMGuard is_d_T_open = new EFSMGuard(d_T);
		
		// check if a button is already visited
		// EFSMGuard is_b_0_visited = new EFSMGuard(visited_b_0);
		// EFSMGuard is_b_1_visited = new EFSMGuard(visited_b_1);
		// EFSMGuard is_b_2_visited = new EFSMGuard(visited_b_2);
		// EFSMGuard is_b_3_visited = new EFSMGuard(visited_b_3);
		
		// check if a button is already pressed
		// EFSMGuard is_b_0_pressed = new EFSMGuard(pressed_b_0);
		// EFSMGuard is_b_1_pressed = new EFSMGuard(pressed_b_1);
		// EFSMGuard is_b_2_pressed = new EFSMGuard(pressed_b_2);
		// EFSMGuard is_b_3_pressed = new EFSMGuard(pressed_b_3);
		
		// check if flag is already visited
		// EFSMGuard is_f_0_visited = new EFSMGuard(visited_f_0);

		/*
		 * Assignments
		 */
		// door switch
		Assign<Boolean> not_d_1 = new Assign(d_1, new BoolNot(d_1));
		Assign<Boolean> not_d_2 = new Assign(d_2, new BoolNot(d_2));
		Assign<Boolean> not_d_T = new Assign(d_T, new BoolNot(d_T));
		// score increase for first time button explored
		//Assign<Integer> first_time_button_visited = new Assign(score, new IntSum(score, new Const(1)));
		// score increase for first time button explored
		//Assign<Integer> first_time_button_pressed = new Assign(score, new IntSum(score, new Const(10)));
		// score increase for first time goal flag explored
		//Assign<Integer> first_time_goalflag_visited = new Assign(score, new IntSum(score, new Const(100)));
				
		/*
		 * Operations
		 */
		EFSMOperation trigger_d_1 = new EFSMOperation(not_d_1);
		EFSMOperation trigger_d_1_d_2_d_T = new EFSMOperation(not_d_1, not_d_2, not_d_T);
		
		/*
		 * Transitions
		 */
		
		//// from b_0
		// t_0 : b_0 -> b_1
		EFSMTransition t_0 = new EFSMTransition<>();
		t_0.setInParameter(inputParExplore);
		t_0.setId("t_0");
		
		// t_1 : b_0 -> d_1_m
		EFSMTransition t_1 = new EFSMTransition<>();
		t_1.setInParameter(inputParExplore);
		t_1.setId("t_1");
		
		// t_2 : b_0 -> d_T_m
		EFSMTransition t_2 = new EFSMTransition<>();
		t_2.setInParameter(inputParExplore);
		t_2.setId("t_2");
		
		// t_3 : b_0 -> b_0
		EFSMTransition t_3 = new EFSMTransition<>();
		t_3.setInParameter(inputParToggle);
		t_3.setId("t_3");
		
		//// from b_1
		// t_4 : b_1 -> d_T_m
		EFSMTransition t_4 = new EFSMTransition<>();
		t_4.setInParameter(inputParExplore);
		t_4.setId("t_4");
		
		// t_5 : b_1-> b_0
		EFSMTransition t_5 = new EFSMTransition<>();
		t_5.setInParameter(inputParExplore);
		t_5.setId("t_5");
		
		// t_6 : b_1 -> d_1_m
		EFSMTransition t_6 = new EFSMTransition<>();
		t_6.setInParameter(inputParExplore);
		t_6.setId("t_6");
		
		// t_7 : b_1 -> b_1
		EFSMTransition t_7 = new EFSMTransition<>();
		t_7.setOp(trigger_d_1);
		t_7.setInParameter(inputParToggle);
		t_7.setId("t_7");
		
		//// from d_1_m
		// t_8 : d_1_m -> b_0
		EFSMTransition t_8 = new EFSMTransition<>();
		t_8.setInParameter(inputParExplore);
		t_8.setId("t_8");
		
		// t_9 : d_1_m -> d_T_m
		EFSMTransition t_9 = new EFSMTransition<>();
		t_9.setInParameter(inputParExplore);
		t_9.setId("t_9");
		
		// t_10 : d_1_m -> d_1_p
		EFSMTransition t_10 = new EFSMTransition<>();
		t_10.setGuard(is_d_1_open);
		t_10.setInParameter(inputParExplore);
		t_10.setId("t_10");
		
		// t_11 : d_1_m -> b_1
		EFSMTransition t_11 = new EFSMTransition<>();
		t_11.setInParameter(inputParExplore);
		t_11.setId("t_11");
		
		//// from d_T_m
		// t_12 : d_T_m -> d_1_m
		EFSMTransition t_12 = new EFSMTransition<>();
		t_12.setInParameter(inputParExplore);
		t_12.setId("t_12");
		
		// t_13 : d_T_m -> b_1
		EFSMTransition t_13 = new EFSMTransition<>();
		t_13.setInParameter(inputParExplore);
		t_13.setId("t_13");
		
		// t_14 : d_T_m -> d_T_p
		EFSMTransition t_14 = new EFSMTransition<>();
		t_14.setGuard(is_d_T_open);
		t_14.setInParameter(inputParExplore);
		t_14.setId("t_14");
		
		//t_15 : d_T_m -> d_1_m
		EFSMTransition t_15 = new EFSMTransition<>();
		t_15.setInParameter(inputParExplore);
		t_15.setId("t_15");
		
		//// from d_1_p
		// t_16 : d_1_p -> d_1_m
		EFSMTransition t_16 = new EFSMTransition<>();
		t_16.setGuard(is_d_1_open);
		t_16.setInParameter(inputParExplore);
		t_16.setId("t_16");
		
		// t_17 : d_1_p -> b_2
		EFSMTransition t_17 = new EFSMTransition<>();
		t_17.setInParameter(inputParExplore);
		t_17.setId("t_17");
		
		// t_18 : d_1_p -> d_2_m 
		EFSMTransition t_18 = new EFSMTransition<>();
		t_18.setInParameter(inputParExplore);
		t_18.setId("t_18");
		
		//// from b_2
		// t_19 : b_2 -> d_1_p
		EFSMTransition t_19 = new EFSMTransition<>();
		t_19.setInParameter(inputParExplore);
		t_19.setId("t_19");
		
		// t_20 : b_2 -> b_2
		EFSMTransition t_20 = new EFSMTransition<>();
		t_20.setOp(trigger_d_1_d_2_d_T);
		t_20.setInParameter(inputParToggle);
		t_20.setId("t_20");
		
		// t_21 : b_2 -> d_2_m
		EFSMTransition t_21 = new EFSMTransition<>();
		t_21.setInParameter(inputParExplore);
		t_21.setId("t_21");
		
		//// from d_2_m
		// t_22 : d_2_m -> b_2
		EFSMTransition t_22 = new EFSMTransition<>();
		t_22.setInParameter(inputParExplore);
		t_22.setId("t_22");
		
		// t_23 : d_2_m -> d_1_p 
		EFSMTransition t_23 = new EFSMTransition<>();
		t_23.setInParameter(inputParExplore);
		t_23.setId("t_23");
		
		// t_24 : d_2_m -> d_2_p
		EFSMTransition t_24 = new EFSMTransition<>();
		t_24.setGuard(is_d_2_open);
		t_24.setInParameter(inputParExplore);
		t_24.setId("t_24");
		
		//// from d_2_p
		// t_25 : d_2_p -> d_2_m
		EFSMTransition t_25 = new EFSMTransition<>();
		t_25.setGuard(is_d_2_open);
		t_25.setInParameter(inputParExplore);
		t_25.setId("t_25");
		
		// t_26 : d_2_p -> b_3
		EFSMTransition t_26 = new EFSMTransition<>();
		t_26.setInParameter(inputParExplore);
		t_26.setId("t_26");
		
		//// from b_3
		// t_27 : b_3 -> d_2_p
		EFSMTransition t_27 = new EFSMTransition<>();
		t_27.setInParameter(inputParExplore);
		t_27.setId("t_27");
		
		// t_28 : b_3 -> b_3
		EFSMTransition t_28 = new EFSMTransition<>();
		t_28.setOp(trigger_d_1);
		t_28.setInParameter(inputParToggle);
		t_28.setId("t_28");
		
		// from d_T_p
		// t_29 : d_T_p -> f_0
		EFSMTransition t_29 = new EFSMTransition<>();
		t_29.setInParameter(inputParExplore);
		t_29.setId("t_29");
		
		// t_30 : t_T_p -> d_T_m
		EFSMTransition t_30 = new EFSMTransition<>();
		t_30.setGuard(is_d_T_open);
		t_30.setInParameter(inputParExplore);
		t_30.setId("t_30");
		
		// from f_0
		EFSMTransition t_31 = new EFSMTransition<>();
		t_31.setInParameter(inputParExplore);
		t_31.setId("t_31");
		
		/*
		 * EFSM declaration
		 */
		EFSM buttonDoors1EFSM;
		
	    EFSMBuilder buttonDoors1EFSMBuilder = new EFSMBuilder(EFSM.class);
		
	    LRParameterGenerator lrParameterGenerator = new LRParameterGenerator();

	    buttonDoors1EFSM = buttonDoors1EFSMBuilder
	    		.withTransition(b_0, b_1, t_0)

	    		.withTransition(b_0, d_1_m, t_1)
	    		.withTransition(b_0, d_T_m, t_2)
	    		.withTransition(b_0, b_0, t_3)
	    		.withTransition(b_1, d_T_m,t_4)
	    		.withTransition(b_1, b_0, t_5)
	    		.withTransition(b_1, d_1_m, t_6)
	    		.withTransition(b_1, b_1, t_7)
	    		.withTransition(d_1_m, b_0, t_8)
	    		.withTransition(d_1_m, d_T_m, t_9)
	    		.withTransition(d_1_m, d_1_p, t_10)
	    		.withTransition(d_1_m, b_1, t_11)
	    		.withTransition(d_T_m, d_1_m, t_12)
	    		.withTransition(d_T_m, b_1, t_13)
	    		.withTransition(d_T_m, d_T_p, t_14)
	    		.withTransition(d_T_m, b_0, t_15)
	    		.withTransition(d_1_p, d_1_m, t_16)
	    		.withTransition(d_1_p, b_2, t_17)
	    		.withTransition(d_1_p, d_2_m,t_18)
	    		.withTransition(b_2, d_1_p, t_19)
	    		.withTransition(b_2, b_2, t_20)
	    		.withTransition(b_2, d_2_m, t_21)
	    		.withTransition(d_2_m, b_2, t_22)
	    		.withTransition(d_2_m, d_1_p, t_23)
	    		.withTransition(d_2_m, d_2_p, t_24)
	    		.withTransition(d_2_p, d_2_m, t_25)
	    		.withTransition(d_2_p, b_3, t_26)
	    		.withTransition(b_3, d_2_p, t_27)
	    		.withTransition(b_3, b_3, t_28)
	    		.withTransition(d_T_p, f_0, t_29)
	    		.withTransition(d_T_p, d_T_m, t_30)
	    		.withTransition(f_0, d_T_p, t_31)
	    		.build(b_0, bd1Context, lrParameterGenerator);
	    
	    return(buttonDoors1EFSM);
	}
	
	
	
}
