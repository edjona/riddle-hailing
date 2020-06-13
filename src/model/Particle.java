package model;

import model.Driver;
import model.Rider;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Random;

import static util.Utils.getDrivers;
import static util.Utils.getRiders;

public class Particle {
    private Integer[][] value;
    private Double maxValue;

    public Particle() {}

    public Particle(Workbook workbook) {
        ArrayList<Driver> drivers = getDrivers(workbook);
        ArrayList<Rider> riders = getRiders(workbook);

        int driverSize = drivers.size();
        int riderSize = riders.size();
        this.value = new Integer[riderSize][driverSize];

        for (int i = 0; i < riderSize; i++) {
            for (int j = 0; j < driverSize; j++) {
                this.value[i][j] = randomInitialization();
            }
        }
    }

    public void printParticle() {
        for (Integer[] x : value) {
            for (int val : x) {
                System.out.print("[" + val + "] ");
            }
            System.out.println();
        }
    }

    private static int randomInitialization() {
        return new Random().nextInt(2);
    }

    public void defineMax(Workbook workbook, Integer cost, Integer fee) {
        ArrayList<Driver> drivers = getDrivers(workbook);
        ArrayList<Rider> riders = getRiders(workbook);

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
}
