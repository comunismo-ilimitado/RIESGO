package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Continent;
import model.Country;
import view.CreateMap;

/**
 * this class saves the created map
 * @author pazim
 *
 */

public class SaveCreatedMap {

	BufferedWriter bw;

	/**
	 * Saves the map created by user
	 */
	public SaveCreatedMap() {
		File f1 = new File("Resources/UserMap.map");
		try {
			if (f1.createNewFile())
				System.out.println("File created");
			else
				System.out.println("File already exists");
			FileOutputStream fos = new FileOutputStream(f1);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Init();

	}

	/**
	 * Write map into .map file
	 * 
	 * @param s
	 */
	public void WriteIntoFile(String s) {
		try {

			bw.write(s);
			bw.newLine();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Does initialization
	 */
	public void Init() {
		try {
			WriteIntoFile("[Map]");
			WriteIntoFile("author=gree");
			WriteIntoFile("warn=yes");
			WriteIntoFile("image=World.bmp");
			WriteIntoFile("wrap=no");
			WriteIntoFile("scroll=vertical");

			bw.newLine();
			WriteIntoFile("[Continents]");
			for (Continent in : CreateMap.ContinentsObjectList) {
				WriteIntoFile(in.getName() + "=" + in.getControlValue());
			}
			bw.newLine();
			WriteIntoFile("[Territories]");

			Random rand1 = new Random();
			Random rand2 = new Random();

			for (Continent cc : CreateMap.ContinentsObjectList) {
				for (Country cn : cc.getCountries()) {
					int value1 = rand1.nextInt(255);
					int value2 = rand2.nextInt(255);
					bw.write(cn.getName() + "," + value1 + "," + value2 + "," + cc.getName());

					for (Country cin : cn.getNeighbors()) {
						bw.write("," + cin.getName());
					}
					bw.newLine();
				}
				bw.newLine();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
