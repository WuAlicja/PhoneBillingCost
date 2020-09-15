package com.company;

import java.time.LocalTime;
import java.util.*;

public class TotalCost {
    private int shortCallPrice;
    private int longCallPrice;

    public TotalCost(int shortCallPrice, int longCallPrice) {
        this.shortCallPrice = shortCallPrice;
        this.longCallPrice = longCallPrice;
    }

    public void finalTotalCost(){
        List<LocalTime> callTimes = getLocalTimes(new Billing().getBilling());

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

        int totalCost = getTotalCost(callsLongerThan5Minutes, callsShorterThan5Minutes,longCallPrice,shortCallPrice);

        printDashes();
        printTotalCost(totalCost);
        printDashes();
    }

    public  void printTotalCost(int totalCost) {

        System.out.println("Total cost of phone calls: " + totalCost / 100 + " $ " + totalCost % 100 + " cents");
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


    static void printDashes() {
        System.out.println("");
        System.out.println("--------------------------------------------------------");
        System.out.println("");
    }
}
