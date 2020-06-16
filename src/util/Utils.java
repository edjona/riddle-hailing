package util;

import model.Driver;
import model.Rider;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

public class Utils {
    public static ArrayList<Driver> getDrivers(Workbook workbook) {
        ArrayList<Driver> drivers = new ArrayList<>();

        Sheet driverSheet = workbook.getSheet("Driver");
        driverSheet.forEach(row -> {
            if(row.getRowNum() != 0) {
                Driver driver = new Driver();

                try {
                    Integer driverId = Integer.valueOf(getCellValue(row.getCell(0)));
                    driver.setDriverId(driverId);

                    Integer X0D = Integer.valueOf(getCellValue(row.getCell(1)));
                    driver.setX0D(X0D);

                    Integer Y0D = Integer.valueOf(getCellValue(row.getCell(2)));
                    driver.setY0D(Y0D);

                    Integer JD = Integer.valueOf(getCellValue(row.getCell(3)));
                    driver.setJD(JD);

                    Integer KM = Integer.valueOf(getCellValue(row.getCell(4)));
                    driver.setKM(KM);

                    Integer RD = Integer.valueOf(getCellValue(row.getCell(5)));
                    driver.setRD(RD);

                    drivers.add(driver);

                } catch ( NumberFormatException ignored) { }
            }
        });

        return drivers;
    }

    public static ArrayList<Rider> getRiders(Workbook workbook) {
        ArrayList<Rider> riders = new ArrayList<>();

        Sheet riderSheet = workbook.getSheet("Rider");
        riderSheet.forEach(row -> {
            if(row.getRowNum() != 0) {
                Rider rider = new Rider();

                try {
                    Integer riderId = Integer.valueOf(getCellValue(row.getCell(0)));
                    rider.setRiderId(riderId);

                    Integer X0R = Integer.valueOf(getCellValue(row.getCell(1)));
                    rider.setX0R(X0R);

                    Integer Y0R = Integer.valueOf(getCellValue(row.getCell(2)));
                    rider.setY0R(Y0R);

                    Integer XDR = Integer.valueOf(getCellValue(row.getCell(3)));
                    rider.setXDR(XDR);

                    Integer YDR = Integer.valueOf(getCellValue(row.getCell(4)));
                    rider.setYDR(YDR);

                    Integer JR = Integer.valueOf(getCellValue(row.getCell(5)));
                    rider.setJR(JR);

                    riders.add(rider);

                } catch ( NumberFormatException ignored) { }
            }
        });

        return riders;
    }

    public static Integer[] randomize(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        List<Integer> integers = Arrays.asList(array);
        Collections.shuffle(integers);
        return integers.toArray(array);
    }

    private static String getCellValue(Cell cell) {
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(cell);
    }
}
