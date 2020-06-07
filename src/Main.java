import model.Driver;
import model.Rider;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import util.ExcelReader;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static util.Utils.getDrivers;
import static util.Utils.getRiders;

public class Main {
    private Integer initialPopulation;
    private Integer iteration;

    public static void main(String[] args) throws IOException, InvalidFormatException {
        File excelFile = new File("D:\\RiddleHailing\\src\\Data.xlsx");
        ExcelReader excelReader = new ExcelReader(excelFile);
        Workbook workbook = excelReader.getWorkbook();

        ArrayList<Driver> drivers = getDrivers(workbook);
        ArrayList<Rider> riders = getRiders(workbook);

        JOptionPane.showMessageDialog(null, drivers.size() + " drivers & " + riders.size() + " riders");

        for (Driver driver : drivers) {
            System.out.println(driver.getDriverId());
        }

        for (Rider rider : riders) {
            System.out.println(rider.getRiderId());
        }
    }
}
