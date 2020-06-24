
/**
 * Write a description of BabyNames here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
public class BabyNames {

    // 1) Total Births
    public void totalBirths()
    {
        FileResource fr = new FileResource();
        int totalBirths = 0;
        int totalGirls = 0;
        int totalBoys = 0;
        for(CSVRecord rec : fr.getCSVParser())
        {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if(rec.get(1).equals("F"))
            {
                totalGirls += numBorn;
            }
            else
            {
                totalBoys += numBorn;
            }
        }
        System.out.println("Total Births = "+totalBirths);
        System.out.println("Total Girls = " + totalGirls);
        System.out.println("Total Boys = " + totalBoys);
    }
    
    // 2) GetRank of the name in particular year
    public int getRank(FileResource year, String name, String gender)
    {
        //String fileName = "\""+"yob"+year+"short"+ "\"";
        int rank = 0;
        //int mrank = 0;
        //FileResource fr = new FileResource(fileName);
        for(CSVRecord rec : year.getCSVParser())
        {
            if(rec.get(1).equals(gender))
            {
                if(rec.get(0).equals(name))
                {
                rank++;
                return rank;
                }
                else
                {
                    rank++;
                }
            }
        }
        return -1;
    }
    public void testgetRank()
    {
        FileResource year = new FileResource();
        int rank = getRank(year,"Frank","M");
        System.out.println("rank = "+rank);
    }
    
    // 3) getName method returns the name of the baby at the particular rank in that year
    public String getName(FileResource year, int rank, String gender)
    {
       
        for(CSVRecord rec : year.getCSVParser())
        {
            if(rec.get(1).equals(gender))
            {
                int currRank = getRank(year,rec.get(0),gender);
                if(currRank == rank)
                {
                    return rec.get(0);
                }
            }
        }
        return "No Name";
    }
    public void testGetName()
    {
        FileResource year = new FileResource();
        String name = getName(year,450,"M");
        System.out.println(name);
    }
    
    // 4) What is the name of baby if born in other year
    public void whatIsNameInYear(FileResource year, String name, FileResource newYear, String gender)
    {
        int rank = 0;
        for(CSVRecord rec : year.getCSVParser())
        {
            if(rec.get(0).equals(name) && rec.get(1).equals(gender))
            {
                rank=getRank(year,name,gender);
            }
        }
        String newName = getName(newYear,rank,gender);
        System.out.println("New name of " +name+" is "+newName);
    }
    public void testWhatIsNameInYear()
    {
        FileResource year = new FileResource();
        FileResource newYear = new FileResource();
        whatIsNameInYear(year,"Owen",newYear,"M");
    }
    
    //5) Returns the year with highest rank of baby name
    public String yearOfHighestRank(String name, String gender)
    {
        DirectoryResource dr = new DirectoryResource();
        int highestSoFar = 99999;
        String fileName ="";
        for(File f : dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            int currRank = getRank(fr,name,gender);
            if(currRank < highestSoFar)
            {
                highestSoFar = currRank;
                fileName = f.getName();
            }
        }
        return fileName;
    }
    public void testYearOfHighestSoFar()
    {
        String fileName = yearOfHighestRank("Mich","M");
        System.out.println("Highest rank in year " + fileName);
    }
    
    // 6) Returns Average rank of baby name from several years of data
    public double getAverageRank(String name, String gender)
    {
        DirectoryResource dr = new DirectoryResource();
        double averageRank = 0;
        double sum =0;
        int entries = 0;
        for(File f : dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            int currRank = getRank(fr,name,gender);
            sum += currRank;
            entries++;
        }
        averageRank = sum/entries;
        return averageRank;
    }
    public void testGetAverageRank()
    {
        double average = getAverageRank("Robert", "M");
        System.out.println("Average Rank" + average);
    }
    
    // 7) Returns total number of births ranked higher than baby name
    public int getTotalBirthsRankedHigher(FileResource year,String name, String gender)
    {
        int total = 0;
        int rank = getRank(year,name,gender);
        for(CSVRecord rec : year.getCSVParser())
        {
            int currRank = getRank(year, rec.get(0), gender);
            
            if(currRank < rank && currRank != -1)
            {
                int birth = Integer.parseInt(rec.get(2));
                
                total += birth;
            }
        }
        return total;
    }
    public void testGetTotalBirthsRankedHigher()
    {
        FileResource year = new FileResource();
        int total = getTotalBirthsRankedHigher(year,"Drew","M");
        System.out.println(total);
    }
}
