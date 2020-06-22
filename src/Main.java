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
    private static int iteration;
    private static int particleCap;
    private static int fee;
    private static int cost;
    private static Particle[] previousPBest;
    private static Particle[] currentPBest;
    private static Particle previousGBest = null;
    private static Particle currentGBest = null;

    public static void main(String[] args){
        try{
            File excelFile = new File("D:\\RiddleHailing\\src\\Data.xlsx");
            ExcelReader excelReader = new ExcelReader(excelFile);
            Workbook workbook = excelReader.getWorkbook();

            printExcelInformation(workbook);
            ask();
            buildParticles(workbook);
            iterateParticles();


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
        currentPBest = new Particle[particleCap];
        for (int i = 0; i < currentPBest.length; i++) {
            currentPBest[i] = new Particle(i, workbook);
            currentPBest[i].build();
            currentPBest[i].defineMax(cost, fee);
        }

        for (Particle particle : currentPBest) {
            particle.printParticle();
        }

        for (Particle particle : currentPBest) {
            if (currentGBest == null) {
                currentGBest = particle;
            } else {
                if (currentGBest.getMaxValue() < particle.getMaxValue()) {
                    currentGBest = particle;
                }
            }
        }
    }

    private static void iterateParticles() {
        previousPBest = currentPBest;
        previousGBest = currentGBest;

        for (int i = 0; i < iteration; i++) {
            for (Particle particle : currentPBest) {
                particle.defineMax(cost, fee);
                particle.getCurrentParticleBest();
            }
        }

        for (Particle particle : currentPBest) {
            if (currentGBest == null) {
                currentGBest = particle;
            } else {
                if (currentGBest.getMaxValue() < particle.getMaxValue()) {
                    currentGBest = particle;
                }
            }
        }

        for (int i = 0; i < iteration; i++) {
            for (int j = 0; j < currentPBest.length; j++) {
                currentPBest[j].iterate(previousPBest[j], currentPBest[j], previousGBest, currentGBest);
                currentPBest[j].defineMax(cost, fee);
            }
        }
    }
}
