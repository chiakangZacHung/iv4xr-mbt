/**
 * 
 */
package eu.fbk.iv4xr.mbt.testcase;

import static org.junit.Assert.*;

import org.junit.Test;

//import de.upb.testify.efsm.EFSM;
//import eu.fbk.iv4xr.mbt.efsm4j.EFSM;
//import eu.fbk.iv4xr.mbt.efsm4j.EFSMFactory;
//import eu.fbk.iv4xr.mbt.efsm4j.EFSMPath;
//import eu.fbk.iv4xr.mbt.efsm4j.EFSMState;
//import eu.fbk.iv4xr.mbt.efsm4j.labrecruits.LabRecruitsEFSMFactory;
//import eu.fbk.iv4xr.mbt.efsm4j.labrecruits.LabRecruitsFPAlgo;
//import eu.fbk.iv4xr.mbt.efsm4j.labrecruits.LabRecruitsState;
import eu.fbk.iv4xr.mbt.MBTProperties;
import eu.fbk.iv4xr.mbt.efsm.EFSMFactory;
import eu.fbk.iv4xr.mbt.efsm.EFSMPath;
import eu.fbk.iv4xr.mbt.efsm.EFSMState;
import eu.fbk.iv4xr.mbt.efsm.labRecruits.LRFPAlgo;
import eu.fbk.iv4xr.mbt.execution.EFSMTestExecutor;
import eu.fbk.iv4xr.mbt.execution.ExecutionResult;
import eu.fbk.iv4xr.mbt.efsm.EFSM;

/**
 * @author kifetew
 *
 */
public class RandomLengthTestFactoryTest {

	/**
	 * Test method for {@link eu.fbk.iv4xr.mbt.testcase.RandomLengthTestFactory#RandomLengthTestFactory(de.upb.testify.efsm.EFSM)}.
	 */
	@Test
	public void testRandomLengthTestFactoryEFSM() {
		MBTProperties.SUT_EFSM = "labrecruits.buttons_doors_1";
		EFSMFactory efsmFactory = EFSMFactory.getInstance();
		assertNotNull(efsmFactory);
		EFSM efsm = efsmFactory.getEFSM();
		assertNotNull (efsm);
		RandomLengthTestFactory testFactory = new RandomLengthTestFactory(efsm);
		assertNotNull (testFactory);
	}

	/**
	 * Test method for {@link eu.fbk.iv4xr.mbt.testcase.RandomLengthTestFactory#RandomLengthTestFactory(de.upb.testify.efsm.EFSM, int)}.
	 */
	@Test
	public void testRandomLengthTestFactoryEFSMInt() {
		MBTProperties.SUT_EFSM = "labrecruits.buttons_doors_1";
		EFSMFactory efsmFactory = EFSMFactory.getInstance();
		assertNotNull(efsmFactory);
		EFSM efsm = efsmFactory.getEFSM();
		assertNotNull (efsm);
		int maxLength = 20;
		RandomLengthTestFactory testFactory = new RandomLengthTestFactory(efsm, maxLength);
		assertNotNull (testFactory);
		Testcase t = testFactory.getTestcase();
		assertTrue (t.getLength() <= maxLength);
	}

	/**
	 * Test method for {@link eu.fbk.iv4xr.mbt.testcase.RandomLengthTestFactory#getTestcase()}.
	 */
	@Test
	public void testGetTestcase() {
		MBTProperties.SUT_EFSM = "labrecruits.buttons_doors_1";
		EFSMFactory efsmFactory = EFSMFactory.getInstance();
		assertNotNull(efsmFactory);
		EFSM efsm = efsmFactory.getEFSM();
		assertNotNull (efsm);
		RandomLengthTestFactory testFactory = new RandomLengthTestFactory(efsm);
		assertNotNull(testFactory);
		Testcase testcase = testFactory.getTestcase();
		assertNotNull(testcase);
		System.out.println(((AbstractTestSequence) testcase).toDot());
		
	}

	/**
	 * Test method for {@link eu.fbk.iv4xr.mbt.testcase.RandomLengthTestFactory#getTestcase()}.
	 */
	@Test
	public void testGetTestcaseFromRandomModel() {
		MBTProperties.SUT_EFSM = "labrecruits.random_default";
		EFSMFactory efsmFactory = EFSMFactory.getInstance();
		assertNotNull(efsmFactory);
		EFSM efsm = efsmFactory.getEFSM();
		assertNotNull (efsm);
		RandomLengthTestFactory testFactory = new RandomLengthTestFactory(efsm);
		assertNotNull(testFactory);
		Testcase testcase = testFactory.getTestcase();
		assertNotNull(testcase);
		System.out.println(((AbstractTestSequence) testcase).toDot());
		
	}
	
	
	/**
	 * Test method for {@link eu.fbk.iv4xr.mbt.testcase.RandomLengthTestFactory#getTestcase()}.
	 */
	@Test
	public void testGetFeasibleTestcase() {
		MBTProperties.SUT_EFSM = "labrecruits.buttons_doors_1";
		EFSMFactory efsmFactory = EFSMFactory.getInstance();
		assertNotNull(efsmFactory);
		EFSM efsm = efsmFactory.getEFSM();
		assertNotNull (efsm);
		RandomLengthTestFactory testFactory = new RandomLengthTestFactory(efsm);
		assertNotNull(testFactory);
		
		ExecutionResult executionResult;
		int count = 0;
		do {
			Testcase testcase = testFactory.getTestcase();
			assertNotNull(testcase);
			//System.out.println(((AbstractTestSequence) testcase).toDot());
			executionResult = EFSMTestExecutor.getInstance().executeTestcase(testcase);
			count++;
		} while (!executionResult.isSuccess());
		assertTrue(executionResult.isSuccess());
		System.out.println("Found valid test after trials: " + count);
	}
}
