package util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

public class ExcelReader {
    private final File file;
    private final Workbook workbook;

    public ExcelReader(File file) throws IOException, InvalidFormatException {
        this.file = file;
        this.workbook =  WorkbookFactory.create(this.file);
    }

    public File getPathFile() {
        return file;
    }

    public Workbook getWorkbook() {
        return workbook;
    }
}
