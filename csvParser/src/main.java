import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class main {

    public static List<DataEntry> removeDupes(List<DataEntry> entries) {
        HashMap<String, DataEntry> IdEntryMap = new HashMap<>();

        for(DataEntry entry : entries) {

            if(IdEntryMap.containsKey(entry.getUserId()))
            {
                if(IdEntryMap.get(entry.getUserId()).getVersion() < entry.getVersion()) {
                    IdEntryMap.put(entry.getUserId(), entry);
                }
            } else{
                IdEntryMap.put(entry.getUserId(), entry);
            }
        }

        return new ArrayList<>(IdEntryMap.values());
    }

    // Puts last name first
    public static void reorderNames(List<DataEntry> entries) {
        for(DataEntry entry : entries) {
            String[] nameSplit = entry.getName().split(" ");

            String lastNameFirst;
            if (nameSplit.length > 1) {
                lastNameFirst = nameSplit[nameSplit.length - 1] + " " + nameSplit[0];
            } else {
                lastNameFirst = "";
            }

            entry.setName(lastNameFirst);
        }
    }

    public static void sortByName(List<DataEntry> entries) {

    }

    public static HashMap<String, List<DataEntry>> parseEntries(FileReader fileReader) throws IOException, CsvValidationException {
        HashMap<String, List<DataEntry>> entries = new HashMap<String, List<DataEntry>>();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .build();
        CSVReader csvReader = new CSVReaderBuilder(fileReader)
                .withCSVParser(parser)
                .build();

        // We read first to skip the header
        String[] row = csvReader.readNext();
        while ((row = csvReader.readNext()) != null) {
            DataEntry entry = new DataEntry();

            if(row.length < 4){
                continue;
            }

            entry.setUserId(row[0]);
            entry.setName(row[1]);
            entry.setVersion(Integer.parseInt(row[2]));
            entry.setCompany(row[3]);

            if(!entries.containsKey(entry.getCompany())) {
                entries.put(entry.getCompany(), new ArrayList<>());
            }

            entries.get(entry.getCompany()).add(entry);
        }
        return entries;
    }

    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("data.csv");
            HashMap<String, List<DataEntry>> entries = parseEntries(fileReader);

            for(Map.Entry<String,List<DataEntry>> item : entries.entrySet()) {
                item.setValue(removeDupes(item.getValue()));
                reorderNames(item.getValue());
                Collections.sort(item.getValue());

                String outFileName = item.getKey()
                        .replace(",", "")
                        .replace(" ", "")
                        .replace(".", "")
                        .toLowerCase() + ".csv";

                Writer writer = Files.newBufferedWriter(Paths.get(outFileName));
                CSVWriter csvWriter = new CSVWriter(writer, ',',
                        CSVWriter.DEFAULT_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);

                String[] headerRecord = {"UserId", "name", "version", "country"};
                csvWriter.writeNext(headerRecord);
                for(DataEntry entry : item.getValue()){
                    csvWriter.writeNext(new String[]{
                            entry.getUserId(),
                            entry.getName(),
                            String.valueOf(entry.getVersion()),
                            entry.getCompany()
                    });


                    System.out.print(entry.getUserId() + "," + "\t" + entry.getName() + "," + "\t" + entry.getVersion() + "," + "\t" + entry.getCompany() + "\t");
                    System.out.println();
                }

                csvWriter.close();
            }

        } catch(FileNotFoundException e) {
            System.out.println("CSV file could not be found!");
        } catch(IOException | CsvValidationException e) {
            System.out.println("There was an error parsing a CSV row");
            System.err.println(e.getMessage());
        }
    }
}
