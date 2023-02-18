package org.example;


import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    List<MyLog> list = new ArrayList<>();

    public void log(int productNum, int amount) {
        list.add(new MyLog(productNum, amount));
    }

    public void exportAsCSV(File txtFile) throws IOException {
        boolean exists = txtFile.exists();
        try (

                CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile, true))
        ) {
            if (!exists) {
                csvWriter.writeNext(new String[]{"productNum", "amount"});
            }
            for (MyLog myLog : list) {
                csvWriter.writeNext(new String[]{String.valueOf(myLog.getProductNum()), String.valueOf(myLog.getAmount())});
            }
        }
    }

}
