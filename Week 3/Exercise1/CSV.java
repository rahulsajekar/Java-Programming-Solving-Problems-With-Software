
/**
 * Write a description of CSV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
public class CSV {
    public void tester()
    {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        
        String Country = "Nauru";
        String country_info = countryInfo(parser, Country);
        System.out.println(country_info);
        
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser, "fish", "nuts");
        
        parser = fr.getCSVParser();
        int n = numberOfExporters(parser, "sugar");
        System.out.println(n);
        
        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999,999");
    }
    
    String countryInfo(CSVParser parser, String Country)
    {
        for(CSVRecord record:parser)
        {
            String export = record.get("Country");
            if(export.contains(Country))
            {
                String found = record.get("Country") + " : " + record.get("Exports") + " : " + record.get("Value (dollars)");
                return found;
            }
        }
        return "NOT FOUND";
    }
    
    void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2)
    {
       for(CSVRecord record:parser)
       {
           String export = record.get("Exports");
           if(export.contains(exportItem1) && export.contains(exportItem2))
           {
               System.out.println(record.get("Country"));
           }
       }
    }
    
    int numberOfExporters(CSVParser parser, String exportItem)
    {
        int n=0;
        for(CSVRecord record:parser)
        {
            String export = record.get("Exports");
            if(export.contains(exportItem))
            {
                n++;
            }
        }
        return n;
    }
    
    void bigExporters(CSVParser parser, String value)
    {
        for(CSVRecord record:parser)
        {
            String export = record.get("Value (dollars)");
            if(export.length() > value.length())
            {
                System.out.println(record.get("Country") + " " + record.get("Value (dollars)"));
            }
        }
    }
}

