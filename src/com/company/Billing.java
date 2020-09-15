package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Billing {

    public List<List<String>> getBilling() {
        List<List<String>> billing = new ArrayList<>();
        System.out.println("Enter input file location path:");
        Scanner scanner1 = new Scanner(System.in);
        String pathName = scanner1.next(); ///Users/alicja/Downloads/JavaSzkolenie/Billing.txt

        try (Scanner scanner = new Scanner(new File(pathName));) {
            while (scanner.hasNextLine()) {
                billing.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found or wrong path");
        }
        return billing;
    }
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
