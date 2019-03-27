
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ControllerTests.AttackTest;
import ControllerTests.FortificationTest;
import ControllerTests.MapValidationTest;
import ControllerTests.ReinforcementTest;

@RunWith(Suite.class)
@SuiteClasses({FortificationTest.class,
	MapValidationTest.class,
	ReinforcementTest.class,
	AttackTest.class,
	StartupPhaseTest.class
	
})

/**
 * This class runs all the test classes
 * @author Navjot kaur
 *
 */
public class TestSuite {

}
