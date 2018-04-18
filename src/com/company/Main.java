package com.company;

import java.io.Reader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Main {

    public static void main(String[] args) {
        String filePath = "league_builder.csv";

        try {
            Reader in = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("Name", "Height (inches)", "Soccer Experience",
                    "Guardian Name(s)").parse(in);
            for (CSVRecord record : records) {
                String name = record.get("Name");
                String height = record.get("Height (inches)");
                String exp = record.get("Soccer Experience");
                String guardian = record.get("Guardian Name(s)");
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            filePath + "'");
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
