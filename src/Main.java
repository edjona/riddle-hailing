import model.Driver;
import model.Rider;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import util.ExcelReader;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import static util.Utils.getDrivers;
import static util.Utils.getRiders;

public class Main {
    private Integer initialPopulation;
    private Integer iteration;

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) throws IOException, InvalidFormatException {
        File excelFile = new File("D:\\RiddleHailing\\src\\Data.xlsx");
        ExcelReader excelReader = new ExcelReader(excelFile);
        Workbook workbook = excelReader.getWorkbook();

        ArrayList<Driver> drivers = getDrivers(workbook);
        ArrayList<Rider> riders = getRiders(workbook);
    }
}
