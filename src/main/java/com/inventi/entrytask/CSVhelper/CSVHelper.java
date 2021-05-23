package com.inventi.entrytask.CSVhelper;

import com.inventi.entrytask.entity.AccountTransaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CSVHelper {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<AccountTransaction> csvToTransactions(InputStream is) throws IOException {

        BufferedReader reader = null;
        var line = "";
        List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();

        try{
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while((line = reader.readLine()) != null){

                String[] row = line.split(";");
                AccountTransaction accountTransaction = new AccountTransaction(
                        UUID.randomUUID(),
                        row[1],
                        LocalDate.now(),
                        row[2],
                        row[0],
                        Double.parseDouble(row[3]),
                        row[4],
                        null
                );

                transactions.add(accountTransaction);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            reader.close();
        }
        return transactions;
    }

}
