// Ben Smith

import java.util.*;
import java.io.*;

public class CoverLetterBuilder {

  // Dirs for reading and writing
  public static String input_dir = "./Letters_Demo_Input/";
  public static String output_dir = "./Letters_Demo_Output/";
  public static String company_list_dir = "./Company_List_Folder/";

  public static String current_letter = input_dir;

  public static void main(String[] args) {

    // The next if statement is if you are only passing in a company and job
    // and are not looking to make a whole csv file for it. Applying serially.
    if(args.length > 0) {
      args[0] = args[0].substring(0, 1).toUpperCase() + args[0].substring(1);
      String job_line = String.join(" ", args);
      current_letter += "Pre_Graduation_Letter.txt";
      write_a_letter(job_line);
      return;
    }

    Scanner in = new Scanner(System.in);

    System.out.print("Enter the filename of your company list: ");
    String company_list_file = in.nextLine();
    System.out.println();
    System.out.print("Enter the filename of the your template letter: ");
    current_letter += in.nextLine();
    System.out.println();
    System.out.println("Processing...");

    String[] possible_jobs = read_in_company_list(company_list_dir + company_list_file);
    for(String job : possible_jobs) {
      write_a_letter(job);
    }

    System.out.println("Finished!");
  }


  // Program that calls everything together given a line of csv.
  public static void write_a_letter(String company_and_job) {
    String[] job_elements = company_and_job.split(",");
    String[] text = read_in_letter();

    String companyName = remove_excess_space(job_elements[0]);
    String jobTitle = remove_excess_space(job_elements[1]);

    text = categories(text, companyName, jobTitle);

    write(text, job_elements[0]);
  }

  // Program that calls everything together given a company and position name.
  public static void write_a_letter(String companyName, String jobTitle) {
    String[] text = read_in_letter();
    text = categories(text, companyName, jobTitle);
    write(text, companyName);
  }

  // Return a cover letter whose name is included in the cover letter file name
  public static void write(String[] text, String companyName) {
    try {
      File file = new File(output_dir + companyName + "_Letter.doc");

      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);

      for(int i = 0; i < text.length; i++) {
        if(text[i].contains("##")) {
          bw.write('\n');
          bw.write('\n');
          bw.write('\n');
          bw.write('\n');
          bw.write(text[i].substring(2) + " ");
        } else if (text[i].contains("#")) {
          bw.write('\n');
          bw.write('\n');
          if (text.length != 1) {
            bw.write(text[i].substring(1) + " ");
          }
        } else {
          bw.write(text[i] + " ");
        }
      }
      bw.close();
    } catch (IOException e) {
      System.out.println("You are Exceptional!");
    }
  }

  // Used in case there are too many spaces in the input csv.
  public static String remove_excess_space(String line) {
    if (line.charAt(0) == ' ') {
      line = line.substring(1);
    }
    return line.replaceAll("\\s+", " ");
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

  // Read in the list of companies and positions to make letters for
  public static String[] read_in_company_list(String current_company_list) {
    String csvFile = current_company_list;
    String line = "";

    List<String> company_and_job = new ArrayList<String>();

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      while ((line = br.readLine()) != null) {
        if (line.equals("Company name,Position name")) {
          continue;
        }
        // Get count of the number of commas in a line
        int comma_count = line.length() - line.replace(",", "").length();
        if (comma_count != 1) {
          System.out.printf("There should only be 1 comma in line: %s. This "
            + "program cant handle that, yet.\n", line);
          continue;
        }
        company_and_job.add(line);
      }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Switching from a list to an array.
    String[] ret_arr = new String[company_and_job.size()];
    ret_arr = company_and_job.toArray(ret_arr);

    return ret_arr;
  }

  // Read in the file and return an array of the words.
  public static String[] read_in_letter() {
    StringBuilder sb = new StringBuilder();
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(current_letter));
      try {
        String x;
        while ((x = br.readLine()) != null ) {
          // printing out each line in the file
          //System.out.println(x);
          // ret += x;
          sb.append(x);
        }
      } catch (IOException e) {
          e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
        System.out.println(e);
        e.printStackTrace();
    }
    String arr[] = sb.toString().split(" ");
    return arr;
  }
}
