package eu.fbk.iv4xr.mbt.efsm4j.labrecruits;

/**
 * 
 * This class implement feasibility path detection. TODO
 * 
 * @author Davide Prandi
 * 
 */

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;

import eu.fbk.iv4xr.mbt.efsm4j.Configuration;
import eu.fbk.iv4xr.mbt.efsm4j.EFSM;
import eu.fbk.iv4xr.mbt.efsm4j.EFSMPath;
import eu.fbk.iv4xr.mbt.efsm4j.JGraphBasedFPALgo;
import eu.fbk.iv4xr.mbt.efsm4j.Transition;

public class LabRecruitsFPAlgo extends 
	JGraphBasedFPALgo<LabRecruitsState, String, LabRecruitsContext, Transition<LabRecruitsState,String,LabRecruitsContext>>{

	public LabRecruitsFPAlgo(
			EFSM<LabRecruitsState, String, LabRecruitsContext, Transition<LabRecruitsState, String, LabRecruitsContext>> efsm) {
		super(efsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EFSMPath<LabRecruitsState, String, LabRecruitsContext, Transition<LabRecruitsState, String, LabRecruitsContext>> getPath(
			Configuration<LabRecruitsState, LabRecruitsContext> config, LabRecruitsState tgt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends EFSMPath<LabRecruitsState, String, LabRecruitsContext, Transition<LabRecruitsState, String, LabRecruitsContext>>> getPaths(
			Configuration<LabRecruitsState, LabRecruitsContext> config, LabRecruitsState tgt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean pathExists(Configuration<LabRecruitsState, LabRecruitsContext> config, LabRecruitsState tgt) {
		// TODO Auto-generated method stub
		return false;
	}

	public EFSMPath<LabRecruitsState, String, LabRecruitsContext, Transition<LabRecruitsState, String, LabRecruitsContext>> getShortestPath (Configuration<LabRecruitsState, LabRecruitsContext> config, LabRecruitsState tgt){
		
		BellmanFordShortestPath<LabRecruitsState, Transition<LabRecruitsState, String, LabRecruitsContext>> algorithm = new BellmanFordShortestPath<LabRecruitsState, Transition<LabRecruitsState, String, LabRecruitsContext>>(efsm.getBaseGraph() );
		GraphPath<LabRecruitsState, Transition<LabRecruitsState, String, LabRecruitsContext>> path = algorithm.getPath(config.getState(), tgt);
		
		EFSMPath<LabRecruitsState, String, LabRecruitsContext, Transition<LabRecruitsState, String, LabRecruitsContext>> 
		shortestPath = new EFSMPath<LabRecruitsState, String, LabRecruitsContext, Transition<LabRecruitsState,String,LabRecruitsContext>>(path);
		return shortestPath;
	}
}