package day4;

import utils.FileProcessor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4 {

    List<Double> scores = new ArrayList<>();
    public void processPart1(){
        List<String> lines = FileProcessor.readFile("day4/input.txt");
        for (var line: lines){
            String values = line.split(":")[1];
            String[] numbers = values.split("\\|");
            String winningNumbersString = numbers[0];
            String currentNumberString = numbers[1];

            List<Integer> winningNumbers = Arrays
                    .stream(winningNumbersString
                            .substring(1, winningNumbersString.length() - 1)
                            .split(" "))
                    .filter(s -> !Objects.equals(s, ""))
                    .map(Integer::valueOf).toList();

            List<Integer> myNumbers = Arrays
                    .stream(currentNumberString
                            .substring(1)
                            .split(" "))
                    .filter(s -> !Objects.equals(s, ""))
                    .map(Integer::valueOf).toList();


            AtomicInteger countMyWinningNumbers = new AtomicInteger();
            winningNumbers.forEach(number -> {
                if (myNumbers.contains(number)){
                    countMyWinningNumbers.getAndIncrement();
                }
            });
            double score = countMyWinningNumbers.get() == 0 ? 0 : Math.pow(2, countMyWinningNumbers.get() - 1);
            scores.add(score);
        }

        System.out.println(scores.stream().reduce(0D, Double::sum).intValue());
    }

    public void processPart2(){
        List<String> lines = FileProcessor.readFile("day4/input.txt");
        List<Integer> cardsIds = new ArrayList<>();
        Set<Integer> cardsIdsNoDuplicates = new HashSet<>();
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            String[] cardValue = line.split(":")[0].split(" ");
            Integer cardId = Integer.valueOf(cardValue[cardValue.length - 1]);
            cardsIds.add(cardId);
            cardsIdsNoDuplicates.add(cardId);
            String values = line.split(":")[1];
            String[] numbers = values.split("\\|");
            String winningNumbersString = numbers[0];
            String currentNumberString = numbers[1];

            List<Integer> winningNumbers = Arrays
                    .stream(winningNumbersString
                            .substring(1, winningNumbersString.length() - 1)
                            .split(" "))
                    .filter(s -> !Objects.equals(s, ""))
                    .map(Integer::valueOf).toList();

            List<Integer> myNumbers = Arrays
                    .stream(currentNumberString
                            .substring(1)
                            .split(" "))
                    .filter(s -> !Objects.equals(s, ""))
                    .map(Integer::valueOf).toList();

            AtomicInteger countMyWinningNumbers = new AtomicInteger();
            int finalI = i;
            winningNumbers.forEach(number -> {
                if (myNumbers.contains(number)){
                    countMyWinningNumbers.getAndIncrement();
                    if (finalI != lines.size() - 1){
                        lines.add(lines.get(cardId + countMyWinningNumbers.get() - 1));
                    }
                }
            });


        }

        List<Integer> occurrences = new ArrayList<>();
        for (var cardId: cardsIdsNoDuplicates){
            occurrences.add(Collections.frequency(cardsIds, cardId));
        }
        System.out.println(occurrences.stream().reduce(0, Integer::sum).intValue());
    }

}
