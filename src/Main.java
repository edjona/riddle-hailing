import model.Driver;
import model.Rider;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import model.Particle;
import util.ExcelReader;
import util.Logger;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
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
    private static Particle finalGBest = null;

    public static void main(String[] args){
        try{
            File excelFile = new File("D:\\RiddleHailing\\src\\Data.xlsx");
            ExcelReader excelReader = new ExcelReader(excelFile);
            Workbook workbook = excelReader.getWorkbook();

            printExcelInformation(workbook);
            ask();

            Instant start = Instant.now();
            buildParticles(workbook);
            iterateParticles();

            System.out.println();
            System.out.println();

            printImportant("Final Result");
            printSeparator();
            finalGBest.printFinalResult();
            finalGBest.printParticle();
            
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            printImportant("PSO processing time is " + timeElapsed + " millis.");
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
            currentPBest[i] = new Particle((i+1), workbook);
            currentPBest[i].build();
            currentPBest[i].defineMax(cost, fee);
            currentPBest[i].validate();
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

        printImportant("Global Best for Iteration 0");
        currentGBest.printParticle();
        finalGBest = currentGBest;
    }

    private static void iterateParticles() {
        previousPBest = currentPBest;
        previousGBest = currentGBest;

        //Search Personal Best
        for (int i = 0; i < iteration; i++) {
            for (int j = 0; j < currentPBest.length; j++) {
                currentPBest[j].defineMax(cost, fee);
                currentPBest[j] = currentPBest[j].getCurrentParticleBest();
            }
        }

        //Search Global Best
        for (Particle particle : currentPBest) {
            if (currentGBest == null) {
                currentGBest = particle;
            } else {
                if (currentGBest.getMaxValue() < particle.getMaxValue()) {
                    currentGBest = particle;
                }
            }
        }

        for (int i = 1; i <= iteration; i++) {
            for (int j = 0; j < currentPBest.length; j++) {
                currentPBest[j].iterate(previousPBest[j], currentPBest[j], previousGBest, currentGBest);
                currentPBest[j].defineMax(cost, fee);
                currentPBest[j].validate(true);
                currentPBest[j].checkDuplicate(true);
                currentPBest[j].printParticle();
            }
            printImportant("Global Best for Iteration " + i);

            for (Particle particle : currentPBest) {
                if (currentGBest == null) {
                    currentGBest = particle;
                } else {
                    if (currentGBest.getMaxValue() < particle.getMaxValue()) {
                        currentGBest = particle;
                    }
                }
            }

            currentGBest.printParticle();
            if(finalGBest.getMaxValue() < currentGBest.getMaxValue()) {
                finalGBest = currentGBest;
            }
        }
    }
}
