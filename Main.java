package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        printDashes();
        System.out.println("PHONE BILLING MANIPULATOR");
        printDashes();

        int myShortCallPrice=3;
        int myLongCallPrice=150;
        
        List<List<String>> billing = getBilling();

        getBillingWithoutMostFrequentPhoneNumber(billing);

        List<LocalTime> callTimes = getLocalTimes(billing);

        List<LocalTime> callsLongerThan5Minutes = new ArrayList<>();
        List<LocalTime> callsShorterThan5Minutes = new ArrayList<>();
        for (LocalTime value : callTimes) {
            if ((value.getHour() != 0 || value.getMinute() > 5)
                    || (value.getMinute() == 5 && value.getSecond() != 0)) {
                callsLongerThan5Minutes.add(value);
            } else {
                callsShorterThan5Minutes.add(value);
            }
        }

        int totalCost = getTotalCost(callsLongerThan5Minutes, callsShorterThan5Minutes,myLongCallPrice,myShortCallPrice);

        printDashes();
        printTotalCost(totalCost);
    }

    public static void printTotalCost(int totalCost) {
        System.out.println("Total cost of phone calls: " + totalCost / 100 + " $ " + totalCost % 100 + " cents");
    }

    public static List<List<String>> getBilling() {
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

    public static int getTotalCost(List<LocalTime> callsLongerThan5Minutes, List<LocalTime> callsShorterThan5Minutes, int longCallPrice, int shortCallPrice) {
        int costOfLongTimeCalls = getCostOfLongTimeCalls(callsLongerThan5Minutes, longCallPrice);
        int costOfShortTimeCalls = getCostOfShortTimeCalls(callsShorterThan5Minutes,shortCallPrice);
        return costOfLongTimeCalls + costOfShortTimeCalls;
    }

    public static void getBillingWithoutMostFrequentPhoneNumber(List<List<String>> billing) {
        List<String> phoneNumbers = getPhoneNumbers(billing);
        Map<String, Integer> frequency = getFrequency(phoneNumbers);
        String maxNumber = findNumberForSpecificFrequency(frequency);
        removeMaxNumber(billing, maxNumber);
    }

    public static int getCostOfShortTimeCalls(List<LocalTime> callsShorterThan5Minutes,int shortCallPrice) {
        int shortCallTotalTime = 0;
        for (LocalTime value : callsShorterThan5Minutes) {
            shortCallTotalTime = shortCallTotalTime + value.getHour() * 3600 + value.getMinute() * 60 + value.getSecond();
        }
        return shortCallTotalTime * shortCallPrice;
    }

    public static int getCostOfLongTimeCalls(List<LocalTime> callsLongerThan5Minutes,int longCallPrice) {
        int longCallTotalTime = 0;
        for (LocalTime value : callsLongerThan5Minutes) {
            longCallTotalTime = longCallTotalTime + value.getHour() * 60 + value.getMinute();
        }
        return longCallTotalTime * longCallPrice;
    }

    public static List<LocalTime> getLocalTimes(List<List<String>> billing) {
        List<LocalTime> callTimes = new ArrayList<>();
        for (List<String> strings : billing) {
            callTimes.add(LocalTime.parse(strings.get(0)));
        }
        return callTimes;
    }

    public static List<String> getPhoneNumbers(List<List<String>> billing) {
        List<String> phoneNumbers = new ArrayList<>();
        for (List<String> strings : billing) {
            phoneNumbers.add(strings.get(1));
        }
        return phoneNumbers;
    }

    public static void removeMaxNumber(List<List<String>> billing, String maxNumber) {
        for (int i = 0; i < billing.size(); i++) {
            if (billing.get(i).get(1).equals(maxNumber)) {
                billing.remove(i);
                i--;
            }
        }
    }

    static Map<String, Integer> getFrequency(List<String> list) {
        Map<String, Integer> numberFrequency = new HashMap<>();
        Collections.sort(list);
        String temp = "";
        for (String i : list) {
            if (!temp.equals(i)) {
                numberFrequency.put(i, Collections.frequency(list, i));
                temp = i;
            }
        }
        return numberFrequency;
    }

    static int findMaximumFrequency(Map<String, Integer> numberFrequency) {
        int max = 0;
        for (Map.Entry<String, Integer> entry : numberFrequency.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        return max;
    }

    static String findNumberForSpecificFrequency(Map<String, Integer> numberFrequency) {
        for (Map.Entry<String, Integer> entry : numberFrequency.entrySet()) {
            if (entry.getValue() == findMaximumFrequency(numberFrequency)) {
                return entry.getKey();
            }
        }
        return "Number not found";
    }

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    static void printDashes() {
        System.out.println("");
        System.out.println("--------------------------------------------------------");
        System.out.println("");
    }
}
