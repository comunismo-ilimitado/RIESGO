

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import controller.ReadingFiles;
import view.MFrame2;

public class StartupPhaseTest 
{
	MFrame2 mf = new MFrame2();
	ReadingFiles rf = new ReadingFiles(mf);
	String str = "Resources/Montreal.map";
	
	@Before
	public void onStart()
	{
		
	}
	
	@Test
	public void testArmiesPerPlayer() throws IOException
	{
		rf.Reads(str);
		assertEquals(40,ReadingFiles.ArmiesPerPlayer);
	}
	
//	@Test 
//	public void testCountriesPerPlayer()
//	{
//		
//	}
}
