package model;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Arrays;

import static Constant.Constants.DRIVER_RATING_MINIMUM;
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

    public void iterate() {
        for (int riderNumber = 0; riderNumber < this.value.length; riderNumber++) {
            for (int driverNumber = 0; driverNumber < this.value[riderNumber].length; driverNumber++) {
                if (value[riderNumber][driverNumber] == 1) {
                    Rider selectedRider = this.riders.get(riderNumber);
                    Driver selectedDriver = this.drivers.get(driverNumber);

                    if (!isMeetRequirement(selectedDriver, selectedRider)) {
                        value[riderNumber][driverNumber] = 0;
                        searchNewDriver(riderNumber);
                    }
                }
            }
        }
        iteration++;

        printIteration(iteration);
        printParticle();
    }

    public void printParticle() {
        printParticleHeader(id);
        printSeparator();
        for (Integer[] x : value) {
            for (int val : x) {
                if(val == 0) {
                    System.out.print("[" + val + "] ");
                }
                else {
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

    private void searchNewDriver(int riderNumber) {
        for (int driverNumber = 0; driverNumber < this.value[riderNumber].length; driverNumber++) {
            Rider selectedRider = riders.get(riderNumber);
            Driver selectedDriver = drivers.get(driverNumber);

            if(isMeetRequirement(selectedDriver, selectedRider) && isNotBookedByOtherRider(driverNumber)) {
                value[riderNumber][driverNumber] = 1;
            }
        }
    }

    private boolean isMeetRequirement(Driver driver, Rider rider) {
        return driver.isHaveGoodRating()
            && driver.isAvailablePickUpRider(rider)
            && driver.isAvailableDropOffRider(rider)
            && rider.isAvailablePickUpByDriver(driver);
    }

    private boolean isNotBookedByOtherRider(int driverNumber) {
        boolean availability = true;

        for (int i = 0; i < value.length; i++) {
            if(value[i][driverNumber] == 1) {
                availability = false;
                break;
            }
        }

        return availability;
    }
}
