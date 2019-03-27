package ControllerTests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.ReadingFiles;
import view.MFrame2;

public class StartupPhaseTest 
{
	MFrame2 mf = new MFrame2();
	ReadingFiles rf = new ReadingFiles(mf);
	String str = "Resources/Montreal.map";
	int temp;
	
	@Before
	public void onStart()
	{
		temp = ReadingFiles.ArmiesPerPlayer;
	}
	
	@After
	public void onEnd()
	{
		ReadingFiles.ArmiesPerPlayer = temp;
	}
	
	@Test
	public void testArmiesPerPlayer() throws IOException
	{
		rf.Reads(str);
		assertEquals(40,ReadingFiles.ArmiesPerPlayer);
	}
	
}
