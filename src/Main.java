import model.Driver;
import model.Rider;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import util.ExcelReader;
import util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static util.Logger.*;
import static util.Utils.getDrivers;
import static util.Utils.getRiders;

public class Main {
    private static Integer initialPopulation;
    private static Integer iteration;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        try{
            File excelFile = new File("D:\\RiddleHailing\\src\\Data.xlsx");
            ExcelReader excelReader = new ExcelReader(excelFile);
            Workbook workbook = excelReader.getWorkbook();

            ArrayList<Driver> drivers = getDrivers(workbook);
            ArrayList<Rider> riders = getRiders(workbook);

            printHeader("== Application Riddle Hailing Start ==");
            printImportant("Total Driver: " + drivers.size());
            printImportant("Total Rider: " + riders.size());
            printSeparator();

            printQuestion("Initial Population ? ");
            initialPopulation = scanner.nextInt();

            printQuestion("Total iteration ? ");
            iteration = scanner.nextInt();
            printSeparator();

            printResult(drivers.get(0), riders.get(0));
            printResult(drivers.get(1),null);

        } catch ( IOException | InvalidFormatException error) {
            Logger.printError(error.getMessage());
        }
    }
}
