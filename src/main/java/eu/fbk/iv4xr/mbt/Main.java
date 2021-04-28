/**
 * 
 */
package eu.fbk.iv4xr.mbt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.evosuite.utils.LoggingUtils;

import eu.fbk.iv4xr.mbt.MBTProperties.Algorithm;
import eu.fbk.iv4xr.mbt.execution.EFSMTestExecutor;
import eu.fbk.iv4xr.mbt.execution.ExecutionResult;
import eu.fbk.iv4xr.mbt.strategy.CoverageTracker;
import eu.fbk.iv4xr.mbt.strategy.GenerationStrategy;
import eu.fbk.iv4xr.mbt.strategy.PlanningBasedStrategy;
import eu.fbk.iv4xr.mbt.strategy.RandomTestStrategy;
import eu.fbk.iv4xr.mbt.strategy.SearchBasedStrategy;
import eu.fbk.iv4xr.mbt.testcase.AbstractTestSequence;
import eu.fbk.iv4xr.mbt.testcase.MBTChromosome;
import eu.fbk.iv4xr.mbt.testsuite.SuiteChromosome;
import eu.fbk.iv4xr.mbt.utils.TestSerializationUtils;

/**
 * @author kifetew
 *
 * Main entry point to the MBT
 */
public class Main {

	private GenerationStrategy generationStrategy;
	
	/**
	 * 
	 */
	public Main() {
	}

	
	private void run () {
		SuiteChromosome solution = generationStrategy.generateTests();
		// print some stats
		System.out.println("Generated: " + solution.size() + " tests");
		
		// write tests to disk
		writeTests (solution);
		
		// write statistics to disk
		CoverageTracker coverageTracker = generationStrategy.getCoverageTracker();
		writeStatistics (coverageTracker.getStatistics(), coverageTracker.getStatisticsHeader());
		System.out.println(coverageTracker.getStatistics());
		
	}
	
	/**
	 * write search statistics to statistics folder defined in MBTProperties
	 * If there exists one, update it. Otherwise, new file should be created.
	 * @param statistics
	 * @param statisticsHeader
	 */
	private void writeStatistics(String statistics, String statisticsHeader) {
		// make sure stats folder exists
		File statsFolder = new File (MBTProperties.STATISTICS_DIR);
		if (!statsFolder.exists()) {
			statsFolder.mkdirs();
		}
		
		File statsFile = new File (MBTProperties.STATISTICS_FILE);
		boolean exists = false;
		if (statsFile.exists()) {
			exists = true;
		}
		
		try {
			FileUtils.writeStringToFile(statsFile, (exists?statistics:statisticsHeader + statistics), Charset.defaultCharset(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Creates a new folder (currenttime) in the default TESTS folder,
	 * writes each test in a separate file (for now in .dot and .txt formats)
	 * @param solution
	 */
	private void writeTests(SuiteChromosome solution) {
		// make sure tests folder exists
		String testFolder = MBTProperties.TESTS_DIR + File.separator + MBTProperties.SUT_EFSM + File.separator + MBTProperties.ALGORITHM + File.separator + System.currentTimeMillis();
		File testsFolder = new File (testFolder);
		testsFolder.mkdirs();
		
		int count = 1;
		for (MBTChromosome testCase : solution.getTestChromosomes()) {
			String dotFileName = testFolder + File.separator + "test_" + count + ".dot";
			String txtFileName = testFolder + File.separator + "test_" + count + ".txt";
			String serFileName = testFolder + File.separator + "test_" + count + ".ser";
			File dotFile = new File (dotFileName);
			File txtFile = new File (txtFileName);
			try {
				FileUtils.writeStringToFile(dotFile, ((AbstractTestSequence)testCase.getTestcase()).toDot(), Charset.defaultCharset());
				FileUtils.writeStringToFile(txtFile, testCase.getTestcase().toString(), Charset.defaultCharset());
				TestSerializationUtils.saveTestSequence((AbstractTestSequence) testCase.getTestcase(), serFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
		}
		
	}


	private void executeForDebug(AbstractTestSequence testcase) {
//		TestExecutor executor = new EFSMTestExecutor<>();
		ExecutionResult executionResult = EFSMTestExecutor.getInstance().executeTestcase(testcase);
		if (!executionResult.isSuccess()) {
			System.err.println("INVALID: " + testcase.toDot());
		}
	}

	/**
	 * TODO should no longer be necessary, options can now be passed as commandline arguments -Doption=value
	 */
	private void setProperties () {
//		MBTProperties.SEARCH_BUDGET = 500;
//		MBTProperties.LR_mean_buttons = 1;
//		MBTProperties.LR_n_buttons = 20;
//		MBTProperties.LR_n_doors = 10 ;
//		MBTProperties.SUT_EFSM = "labrecruits.random_default";
//		MBTProperties.LR_seed = 370327;
		
		
		MBTProperties.LR_seed = 325439;
		MBTProperties.LR_mean_buttons = 0.5;
		MBTProperties.LR_n_buttons = 40;
		MBTProperties.LR_n_doors = 28;
	}
	

	
	/**
	 * TODO add proper optios here
	 * @return
	 */
	public static Options getCommandLineOptions() {
		Options options = new Options();

		Option help = new Option("help", "print this message");

		Option random = Option.builder("random")
				.argName("random")
				.type(String.class)
				.desc("random search generation")
				.build();
		
		Option mosa = Option.builder("mosa")
				.argName("mosa")
				.type(String.class)
				.desc("MOSA")
				.build();
		
		Option tamer = Option.builder("tamer")
				.argName("tamer")
				.type(String.class)
				.desc("TAMER")
				.build();
		
		Option property   = Option.builder("D")
				.numberOfArgs(2)
				.argName("property=value")
				.valueSeparator('=')
				.required(false)
				.optionalArg(false)
				.type(String.class)
				.desc("use value for given property")
				.build();

		
		options.addOption(help);
		options.addOption(mosa);
		options.addOption(random);
		options.addOption(tamer);
		options.addOption(property);
		return options;
	}
	
	public void parseCommandLine(String[] args) {
		Options options = getCommandLineOptions();
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(options, args);
			
			// TODO deal with arguments here ..
			if (line.hasOption("help")) {
				System.out.println(options.toString());
				System.exit(0);
			}
			
			if (line.hasOption("mosa")) {
				generationStrategy = new SearchBasedStrategy<MBTChromosome>();
			}
			
			if (line.hasOption("random")) {
				generationStrategy = new RandomTestStrategy<MBTChromosome>();
			}
			
			if (line.hasOption("tamer")) {
				generationStrategy = new PlanningBasedStrategy<MBTChromosome>();
			}
			
			setGlobalProperties (line);
		} catch (ParseException e) {
			System.err.println("Failed to parse commandline arguments.");
		}
		
	}


	/**
	 * Method adapted from the EvoSuite project
	 * Read commandline arguments and update the global properties classes (both MBT and EvoSuite)
	 * @param line
	 */
	private void setGlobalProperties(CommandLine line) {
		Properties properties = line.getOptionProperties("D");
		Set<String> propertyNames = new HashSet<>(MBTProperties.getParameters());

        for (String propertyName : properties.stringPropertyNames()) {

            if (!propertyNames.contains(propertyName)) {
				LoggingUtils.getEvoLogger().error("* Unknown property: " + propertyName);
//				throw new Error("Unknown property: " + propertyName);
			}

            String propertyValue = properties.getProperty(propertyName);

            try {
				MBTProperties.getInstance().setValue(propertyName, propertyValue);
				
			} catch (Exception e) {
				LoggingUtils.getEvoLogger().error("Invalid value for property " + propertyName+": "+propertyValue+". Exception "+e.getMessage(),e);
			}
            try {
            	//Do this also for Evosuite global properties, if they exsits
            	org.evosuite.Properties.getInstance().setValue(propertyName, propertyValue);
            }catch (Exception e) {
            	System.err.println("Unable to set Evosuite global property: " + propertyName);
            }
		}
		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main ();
		main.parseCommandLine(args);
		//main.setProperties();
		main.run();
		System.exit(0);
	}

}
