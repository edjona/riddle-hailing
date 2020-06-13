import model.Driver;
import model.Rider;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import model.Particle;
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
    private static final Scanner scanner = new Scanner(System.in);
    private static Integer iteration;
    private static Integer particleCap;
    private static Integer fee;
    private static Integer cost;
    private static Particle[][] particles;

    public static void main(String[] args){
        try{
            File excelFile = new File("D:\\RiddleHailing\\src\\Data.xlsx");
            ExcelReader excelReader = new ExcelReader(excelFile);
            Workbook workbook = excelReader.getWorkbook();

            printExcelInformation(workbook);
            ask();
            buildParticles(workbook);


        } catch ( IOException | InvalidFormatException error) {
            Logger.printError(error.getMessage());
        }
    }

    private static void printExcelInformation(Workbook workbook) {
        ArrayList<Driver> drivers = getDrivers(workbook);
        ArrayList<Rider> riders = getRiders(workbook);

        printHeader("== Application Riddle Hailing Start ==");
        printImportant("Total Driver: " + drivers.size());
        printImportant("Total Rider: " + riders.size());
        printSeparator();
    }

    private static void ask() {
        printQuestion("Total iteration ? ");
        iteration = scanner.nextInt();

        printQuestion("Total Particle Cap ? ");
        particleCap = scanner.nextInt();

        printQuestion("Driver fee ? ");
        fee = scanner.nextInt();

        printQuestion("Travel cost ? ");
        cost = scanner.nextInt();
        printSeparator();
    }

    private static void buildParticles(Workbook workbook) {
        particles = new Particle[iteration][particleCap];

        for (int i = 0; i < iteration; i++) {
            printIteration(i+1);
            printSeparator();
            for (int j = 0; j < particleCap; j++) {
                Particle particle = new Particle(workbook);
                particle.defineMax(workbook, cost, fee);
                particles[i][j] = particle;
                printParticle(j+1);
                particle.printParticle();
                printImportant("Max Value: " + particle.getMaxValue());
                printSeparator();
            }
        }
    }
}
