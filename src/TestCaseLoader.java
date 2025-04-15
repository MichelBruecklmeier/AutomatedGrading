import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class TestCaseLoader {
    public ArrayList<String> testCase = new ArrayList<>();
    String rawData;
    public TestCaseLoader() {

    }
    public void loadFile() {
        rawData = "";
        try{
            File file = new File(Paths.get("res/TestCases").toAbsolutePath().toString());
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                rawData += scanner.nextLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            processData();
        }
    }
    public void processData(){

    }
}


