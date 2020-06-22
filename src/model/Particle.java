package model;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Random;

import static Constant.Constants.C1;
import static util.Logger.*;
import static util.Utils.*;

public class Particle {
    private Integer id;
    private Integer[][] value;
    private Integer iteration = 0;
    private Double maxValue;
    private final ArrayList<Rider> riders;
    private final ArrayList<Driver> drivers;

    public Particle(Integer id, Workbook workbook) {
        this.id = id;
        this.riders = getRiders(workbook);
        this.drivers = getDrivers(workbook);
    }

    public void build() {
        int driverSize = drivers.size();
        int riderSize = riders.size();
        this.value = new Integer[riderSize][driverSize];

        int minimumValue = Math.min(driverSize, riderSize);
        Integer[] driverSeed = randomize(driverSize);
        Integer[] riderSeed = randomize(riderSize);

        for (int i = 0; i < riderSize; i++) {
            for (int j = 0; j < driverSize; j++) {
                this.value[i][j] = 0;
            }
        }

        for (int i = 0; i < minimumValue; i++) {
            int rider = riderSeed[i];
            int driver = driverSeed[i];
            this.value[rider][driver] = 1;
        }
    }

    public Particle getCurrentParticleBest() {
        for (int riderNumber = 0; riderNumber < this.value.length; riderNumber++) {
            for (int driverNumber = 0; driverNumber < this.value[riderNumber].length; driverNumber++) {
                if (value[riderNumber][driverNumber] == 1) {
                    Rider selectedRider = this.riders.get(riderNumber);
                    Driver selectedDriver = this.drivers.get(driverNumber);

                    if (!isMeetRequirement(selectedDriver, selectedRider)) {
                        value[riderNumber][driverNumber] = 0;
                        changeDriver(riderNumber, driverNumber);
                    }
                }
            }
        }
        return this;
    }

    public void iterate(Particle prevPBest, Particle currPBest, Particle prevGBest, Particle currGBest) {

        double[][] cognitiveLearning = calculateLearning(prevPBest.getValue(), currPBest.getValue());
        double[][] socialLearning = calculateLearning(prevGBest.getValue(), currGBest.getValue());
        double[][] learning = calculateMatrix(cognitiveLearning, socialLearning);
        double[][] result = calculateMatrix(learning, velocity());
        value = buildNewMatrix(result);

        iteration++;
        printIteration(iteration);
        printParticle();
    }

    public void printParticle() {
        printParticleHeader(id);
        printSeparator();
        for (Integer[] x : value) {
            for (int val : x) {
                if (val == 0) {
                    System.out.print("[" + val + "] ");
                } else {
                    System.out.print(ANSI_RED + "[" + val + "] " + ANSI_RESET);
                }
            }
            System.out.println();
        }
        printImportant("Max Value: " + getMaxValue());
        printSeparator();
    }

    public void defineMax(Integer cost, Integer fee) {
        double temp = 0;
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                Integer XDR = riders.get(i).getXDR();
                Integer X0R = riders.get(i).getX0R();
                Integer YDR = riders.get(i).getYDR();
                Integer Y0R = riders.get(i).getY0R();
                Integer X0D = drivers.get(j).getX0D();
                Integer Y0D = drivers.get(j).getY0D();

                double tMax = fee * value[i][j] * (Math.abs(XDR - X0R) + Math.abs(YDR - Y0R));
                double cMax = cost * value[i][j] * (Math.abs(X0D - X0R) + Math.abs(Y0D - Y0R) + Math.abs(XDR - X0R) + Math.abs(YDR - Y0R));
                double constrain = drivers.get(j).getRD() >= 10 ? 1 : 0;
                temp += (tMax - cMax) * constrain;
            }
        }
        this.maxValue = temp;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public Integer[][] getValue() {
        return this.value;
    }

    private Integer[][] buildNewMatrix(double[][] result) {
        int row = result.length;
        int col = result[0].length;

        if(row > col) {
            for (int j = 0; j < col; j++) {
                double temp = result[0][j];
                int higher = 0;

                for (int i = 0; i < row; i++) {
                    if (result[i][j] >= temp) {
                        temp = result[i][j];
                        higher = i;
                    }
                }

                for (int i = 0; i < row; i++) {
                    if (i == higher) {
                        result[i][j] = 1;
                    } else {
                        result[i][j] = 0;
                    }
                }
            }
        } else {
            for (int i = 0; i < row; i++) {
                double temp = result[i][0];
                int higher = 0;

                for (int j = 0; j < col; j++) {
                    if (result[i][j] >= temp) {
                        temp = result[i][j];
                        higher = i;
                    }
                }

                for (int j = 0; j < col; j++) {
                    if (j == higher) {
                        result[i][j] = 1;
                    } else {
                        result[i][j] = 0;
                    }
                }
            }
        }

       

        Integer[][] newMatrix = new Integer[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                newMatrix[i][j] = (int) result[i][j];
            }
        }
        return newMatrix;
    }

    private double[][] calculateLearning(Integer[][] prevBest, Integer[][] currBest) {
        int x = prevBest.length;
        int y = prevBest[0].length;
        double R1 = Math.random();

        double[][] tempPBest = new double[x][y];

        for (int i = 0; i < tempPBest.length; i++) {
            for (int j = 0; j < tempPBest[i].length; j++) {
                tempPBest[i][j] = (C1 * R1) * (currBest[i][j] - prevBest[i][j]);
            }
        }

        return tempPBest;
    }

    private double[][] calculateMatrix(double[][] x, double[][] y) {
        double[][] temp = new double[riders.size()][drivers.size()];

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                temp[i][j] = x[i][j] + y[i][j];
            }
        }

        return temp;
    }

    private void changeDriver(int selectedRiderNumber, int selectedDriverNumber) {
        for (int driverNumber = 0; driverNumber < value[selectedRiderNumber].length; driverNumber++) {
            if (driverNumber != selectedDriverNumber) {
                boolean driverAvailability = isDriverAvailable(driverNumber);

                if (driverAvailability) {
                    Rider selectedRider = riders.get(selectedRiderNumber);
                    Driver selectedDriver = drivers.get(driverNumber);

                    if (isMeetRequirement(selectedDriver, selectedRider)) {
                        value[selectedRiderNumber][driverNumber] = 1;
                        break;
                    }
                }
            }
        }
    }

    private boolean isMeetRequirement(Driver driver, Rider rider) {
        return driver.isHaveGoodRating()
                && driver.isAvailablePickUpRider(rider)
                && driver.isAvailableDropOffRider(rider)
                && rider.isAvailablePickUpByDriver(driver);
    }

    private boolean isDriverAvailable(int selectedDriver) {
        boolean driverAvailable = true;

        for (Integer[] values : value) {
            if (values[selectedDriver] == 1) {
                driverAvailable = false;
                break;
            }
        }

        return driverAvailable;
    }

    private double[][] velocity() {
        double[][] velocity = new double[riders.size()][drivers.size()];

        for (int i = 0; i < velocity.length; i++) {
            for (int j = 0; j < velocity[i].length; j++) {
                velocity[i][j] = new Random().nextInt(21) - 10;
            }
        }

        return velocity;
    }
}