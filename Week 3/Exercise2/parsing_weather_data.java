
/**
 * Write a description of parsing_weather_data here.
 * 
 * @author (Rahul) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
public class parsing_weather_data {
    //1st Method
    public CSVRecord coldestHourInFile(CSVParser parser)
    {
        CSVRecord coldestSoFar = null;
        for(CSVRecord currentRow : parser)
        {
            if(coldestSoFar == null)
            {
                coldestSoFar = currentRow;
            }
            else
            {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if(currentTemp < coldestTemp)
                {
                    coldestSoFar = currentRow;
                }
            }
        }
        return coldestSoFar;
    }
    
    public void testColdestHourInFile()
    {
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest Temperature was " + coldest.get("TemperatureF") + " at " + 
            coldest.get("DateUTC"));
    }
    //2nd Method
    public CSVRecord fileWithColdestTemperature()
    {
       CSVRecord coldestSoFar = null;
       //String fileName="";
       DirectoryResource dr = new DirectoryResource();
       for(File f : dr.selectedFiles())
       {
           FileResource fr = new FileResource(f);
           CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
           if(coldestSoFar == null)
           {
               coldestSoFar = currentRow;
           }
           else
           {
              double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
              double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
              if(currentTemp == -9999){continue;}
              if(currentTemp < coldestTemp)
              {
                  coldestSoFar = currentRow;
                  
              }
           }
           //fileName = f.getName();
        }
       //return fileName;
       return coldestSoFar;
    }
    public void printtemp(String filepath)
    {
        FileResource fr = new FileResource(filepath);
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record : parser)
        {
            System.out.println(filepath + record.get("TimeEST")+ record.get("TemperatureF"));
        }
    }
    
    public void testFileWithColdestTemperature()
    {
        CSVRecord coldest= fileWithColdestTemperature();
        System.out.println("Coldest temperature was " + coldest.get("TemperatureF") + " on " + coldest.get("DateUTC"));
        //String name=fileWithColdestTemperature();
        //System.out.println("Collected data in file "+ name);
        //String filepath = "\""+"nc_weather/2014/" + name + "\"";
        //FileResource fr = new FileResource(name);
        //CSVRecord cold = coldestHourInFile(fr.getCSVParser());
        //System.out.println("Coldest temperature on that day was "+cold.get("TemperatureF"));
        //System.out.println("All the temperature on that day was :");
        //printtemp(name);
    }
    // 3rd Method
    public CSVRecord lowestHumidityInFile(CSVParser parser)
    {
        CSVRecord lowestSoFar = null;
        for(CSVRecord currentRow : parser)
        {
            if(lowestSoFar == null)
            {
                lowestSoFar = currentRow;
            }
            else if(currentRow.get("Humidity") == "N/A")
            {
               continue;
            }
            else
            {
                double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double lowestHumidity = Double.parseDouble(lowestSoFar.get("Humidity"));
                if(currentHumidity < lowestHumidity)
                {
                    lowestSoFar = currentRow;
                }
            }
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInFile()
    {
        FileResource fr = new FileResource();
        CSVRecord humidity = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("Lowest Humidity was : " + humidity.get("Humidity") + " on " + humidity.get("DateUTC"));
    }
    // 4th Method
    public CSVRecord lowestHumidityInManyFiles()
    {
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            if(lowestSoFar == null)
            {
                lowestSoFar = currentRow;
            }
            else if(currentRow.get("Humidity") == "N/A")
            {continue;}
            else
            {
                double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double lowestHumidity = Double.parseDouble(lowestSoFar.get("Humidity"));
                if(currentHumidity < lowestHumidity)
                {
                    lowestSoFar = currentRow;
                }    
            }
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInManyFiles()
    {
        CSVRecord lowest = lowestHumidityInManyFiles();
        System.out.println("lowest humidity was : " + lowest.get("Humidity") + " at " + lowest.get("DateUTC"));
    }
    //5th Method
    public double averageTemperatureInFile(CSVParser parser)
    {
        double sum = 0;
        int entries = 0;
        for(CSVRecord currentRow : parser)
        {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            sum = sum + currentTemp;
            entries = entries+1;
        }
        return sum/entries;
    }
    public void testAverageTemperatureInFile()
    {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double averageTemp = averageTemperatureInFile(parser);
        System.out.println("average temperature is : " + averageTemp);
    }
    //6th Method
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value)
    {
        double sum =0;
        int entries = 0;
        for(CSVRecord currentRow : parser)
        {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double currentHum = 0;
            if(currentRow.get("Humidity") != "N/A")
            {
                currentHum = Double.parseDouble(currentRow.get("Humidity"));
            }
            if(currentHum >= value)
            {
                sum += currentTemp;
                entries++;
            }
        }
        return sum/entries;
    }
    public void testAverageTemperatureWithHighHumidityInFile()
    {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double average = averageTemperatureWithHighHumidityInFile(parser,80);
        System.out.println("Average Temperature With High Humidity is : "+average);
    }
}
