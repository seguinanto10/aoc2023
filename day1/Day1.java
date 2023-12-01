package day1;

import utils.FileProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day1 {

    Map<String, Integer> numbersAsString = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9);

    public Day1() {
    }

    public void process(){
        List<String> lines = FileProcessor.readFile("day1/adventofcode.com_2023_day_1_input.txt");
        List<Integer> integers = new ArrayList<>();
        for (var line: lines){
            Integer firstNumber = null;
            Integer lastNumber = null;
            String current_number = "";
            for (char c: line.toCharArray()){

                if (Character.isDigit(c)){
                    current_number = "";
                    lastNumber = Character.getNumericValue(c);
                    if (firstNumber == null){
                        firstNumber = Character.getNumericValue(c);
                    }
                } else {
                    current_number = current_number + c;
                    Map.Entry<String, Integer> number_found;
                    if (numbersAsString.keySet().stream().filter(current_number::contains).toList().size() > 0){
                        String finalCurrent_number = current_number;
                        number_found = numbersAsString.entrySet().stream().filter(stringIntegerEntry -> finalCurrent_number.contains(stringIntegerEntry.getKey())).toList().get(0);
                        current_number = "" + c;
                        if (firstNumber == null){
                            firstNumber = number_found.getValue();
                        }
                        lastNumber = number_found.getValue();
                    }
                }
            }
            Integer twoDigits = Integer.valueOf(firstNumber.toString() + lastNumber.toString());
            integers.add(twoDigits);
        }
        System.out.println(integers.stream().reduce(0, Integer::sum));
    }
}