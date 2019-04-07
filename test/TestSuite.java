
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ControllerTests.AggressiveStrategyTest;
import ControllerTests.AttackTest;
import ControllerTests.BenevolentStrategyTest;
import ControllerTests.CheaterStrategyTest;
import ControllerTests.FortificationTest;
import ControllerTests.MapValidationTest;
import ControllerTests.ReinforcementTest;
import ControllerTests.StartupPhaseTest;

@RunWith(Suite.class)
@SuiteClasses({FortificationTest.class,
	MapValidationTest.class,
	ReinforcementTest.class,
	AttackTest.class,
	StartupPhaseTest.class,
	AggressiveStrategyTest.class,
	BenevolentStrategyTest.class,
	CheaterStrategyTest.class
})

/**
 * This class runs all the test classes
 * @author Navjot kaur
 *
 */
public class TestSuite {

}
