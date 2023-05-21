package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScoreList {
	private int[] score;
	private String[] name;

	/*** load HighScores from a file at startup.
	 * @see HighScoreList#load()*/
	public HighScoreList() {
		score = new int[ENTRIES];
		name = new String[ENTRIES];
		this.load();
	}


	public String getScore(int index) {
		return score[index] + "";
	}

	
	public String getName(int index) {
		if (name[index] != null) {
			return name[index];
		} else
			return "";
	}

	
	public void load() {
		String line = null;
		int i = 0;

		try {
			// point the filereader to a file.
			FileReader fileReader = new FileReader(FILENAME);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// loop through the file while there is data left.
			while ((line = bufferedReader.readLine()) != null && line != "") {
				name[i] = line;

				if ((line = bufferedReader.readLine()) != null) {
					score[i] = Integer.parseInt(line);
				}
				i++;
			}
			
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
	}

	public void save() {
		try {
			FileWriter fileWriter = new FileWriter(FILENAME);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (int i = 0; i < ENTRIES; i++) {
				if (name[i] != null) {
					bufferedWriter.write(name[i]);
					bufferedWriter.newLine();
					bufferedWriter.write(String.valueOf(score[i]));
					bufferedWriter.newLine();
				}
			}

			// close the file.. :(
			bufferedWriter.close();
		} catch (IOException ex) {
			
		}
	}

	
	private void pack(int index) {
		for (int i = ENTRIES - 1; i > index; i--) {
			name[i] = name[i - 1];
			score[i] = score[i - 1];
		}
	}

	
	public void update(String name, int score) {
		int i = -1;

		
		do {
			i++;
		} while (i < ENTRIES && score < this.score[i]);

	
		if (i < ENTRIES && score >= this.score[i]) {
			this.pack(i);
			this.name[i] = name;
			this.score[i] = score;
			this.save();
		}
	}

	public static final int ENTRIES = 10;
	private static final String FILENAME = "HalloweenMemory-HighScore";
}
