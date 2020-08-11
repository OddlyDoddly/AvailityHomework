import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class main {

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

            entry.setUserId(row[0]);
            entry.setName(row[1]);
            entry.setVersion(Integer.parseInt(row[2]));
            entry.setCompany(row[3]);

            if(!entries.containsKey(entry.getCompany())) {
                entries.put(entry.getCompany(), new ArrayList<>());
            }

            entries.get(entry.getCompany()).add(entry);

            for (String col : row) {
                System.out.print(col + "\t");
            }
            System.out.println();
        }
        return entries;
    }

    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("data.csv");
            HashMap<String, List<DataEntry>> entries = parseEntries(fileReader);

            for(Map.Entry<String,List<DataEntry>> item : entries.entrySet()) {
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
