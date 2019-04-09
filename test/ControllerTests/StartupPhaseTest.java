package ControllerTests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.ReadingFiles;
import view.MFrame2;


/**
*Tests startup phase 
*@author navjot kaur
*/

public class StartupPhaseTest 
{
	MFrame2 mf = new MFrame2();
	ReadingFiles rf = new ReadingFiles(mf);
	String str = "Resources/Montreal.map";
	int temp;
	/**
	* method called before all test
	*
	*/
	@Before
	public void onStart()
	{
		temp = ReadingFiles.ArmiesPerPlayer;
	}
	
	/**
	* method called after all test
	*
	*/
	@After
	public void onEnd()
	{
		ReadingFiles.ArmiesPerPlayer = temp;
	}
	
	/**
	*Tests armies of the player
	*
	*/
	@Test
	public void testArmiesPerPlayer() throws IOException
	{
		rf.Reads(str,2);
		assertEquals(40,ReadingFiles.ArmiesPerPlayer);
	}
	
}
