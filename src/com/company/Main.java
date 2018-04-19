package com.company;

import java.util.ArrayList;
import java.util.List;
import java.io.Reader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Main {

    public static void main(String[] args) {
        String filePath = "league_builder.csv";
        List<Map<String, String>> soccerPlayers = new ArrayList<>();

        try {
            Reader in = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("Name", "Height (inches)", "Soccer Experience",
                    "Guardian Name(s)").parse(in);
            for (CSVRecord record : records) {
                Map<String, String> soccerPlayer = new HashMap<>();

                soccerPlayer.put("name", record.get("Name"));
                soccerPlayer.put("height", record.get("Height (inches)"));
                soccerPlayer.put("exp", record.get("Soccer Experience"));
                soccerPlayer.put("guardian", record.get("Guardian Name(s)"));

                soccerPlayers.add(soccerPlayer);
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

        soccerPlayers.remove(0);
        CreateTeams teamList = new CreateTeams(soccerPlayers);
        teamList.createTeamsTxt();
        teamList.createLetters();
    }
}
