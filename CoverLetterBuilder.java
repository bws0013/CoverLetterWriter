// Ben Smith

import java.util.*;
import java.io.*;

public class CoverLetterBuilder {

	public static String current_letter = "Letters_Blank/Pre_Graduation_Letter"

	public static void main(String[] args) {

		String companyName = "companyName";
		String jobTitle = "jobTitle";

		//System.out.printf("%s %s")

		Scanner in = new Scanner(System.in);

		System.out.print("companyName: ");
		companyName = in.nextLine();

		System.out.print("jobTitle: ");
		jobTitle = in.nextLine();

		fin(companyName, jobTitle);



	}

	// Program that calls everything together.
	public static void fin(String companyName, String jobTitle) {
		String[] text = readInFile();
		text = categories(text, companyName, jobTitle);
		write(text, companyName);
	}

	// Return a cover letter whose name is included in the cover letter file name
	public static void write(String[] text, String companyName) {
		try {
			File file = new File(companyName + "_Letter.doc");

			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			for(int i = 0; i < text.length; i++) {
				if(text[i].equals("#")) {
					bw.write('\n');
					bw.write('\n');
				} else {
					bw.write(text[i] + " ");
				}
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("You are Exceptional!");
		}


	}

	// Basic search for the category names. They cannot end a sentence as a '.' will mess things up
	public static String[] categories(String[] text, String companyName, String jobTitle) {
		for(int i = 0; i < text.length; i++) {
			String word = text[i].toLowerCase();
			if(word.equals("<company>")) {
				text[i] = companyName;
			} else if (word.equals("<position>")) {
				text[i] = jobTitle;
			}
		}
		return text;
	}

	// Read in the file and return an array of the words.
	public static String[] readInFile() {
		String ret = "";
		BufferedReader br;
		try {
            br = new BufferedReader(new FileReader(current_letter));
            try {
                String x;
                while ((x = br.readLine()) != null ) {
                    // printing out each line in the file
                    //System.out.println(x);
                    ret += x;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        String arr[] = ret.split(" ");
		return arr;
	}



}
