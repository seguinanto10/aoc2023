package day2;

import utils.FileProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Day2 {

    Map<Integer, List<SetOfBalls>> game = new HashMap<>();
    SetOfBalls maxValuesInASet = new SetOfBalls(12, 14, 13);

    List<Integer> possibleGameIds = new ArrayList<>();

    List<Integer> gamePowers = new ArrayList<>();

    public Day2() {
    }

    public Map<Integer, List<SetOfBalls>> getGame() {
        List<String> lines = FileProcessor.readFile("day2/input.txt");
        Map<Integer, List<SetOfBalls>> game = new HashMap<>();
        for (var line: lines){
            String[] split = line.split(": ");
            Integer gameId = Integer.valueOf(split[0].split("Game ")[1]);
            String[] sets = split[1].split("; ");
            List<SetOfBalls> allGameSets = new ArrayList<>();
            for (var set: sets){
                SetOfBalls current_set = new SetOfBalls(0, 0, 0);
                String[] colorValues = set.split(", ");
                for (var color: colorValues){
                    if (color.contains("green")){
                        current_set.numberOfGreen = Integer.valueOf(color.split(" green")[0]);
                    }
                    if (color.contains("blue")){
                        current_set.numberOfBlue = Integer.valueOf(color.split(" blue")[0]);
                    }
                    if (color.contains("red")){
                        current_set.numberOfRed = Integer.valueOf(color.split(" red")[0]);
                    }
                }
                allGameSets.add(current_set);
            }
            game.putIfAbsent(gameId, allGameSets);
        }
        return game;
    }

    public void processPart1(){
        this.game = getGame();
        for (var entry: game.entrySet()){
            boolean isGamePossible = true;
            for (var set: entry.getValue()){
                if (!isSetPossible.test(set)){
                    isGamePossible = false;
                }
            }
            if (isGamePossible){
                possibleGameIds.add(entry.getKey());
            }
        }
        System.out.println(possibleGameIds.stream().reduce(0, Integer::sum));
    }

    public void processPart2(){
        this.game = getGame();
        for (var entry: game.entrySet()){
            int gamePower;
            SetOfBalls fewestCubes = new SetOfBalls(0, 0, 0);
            for (var set: entry.getValue()){
                if (set.numberOfRed > fewestCubes.numberOfRed){
                    fewestCubes.numberOfRed = set.numberOfRed;
                }
                if (set.numberOfGreen > fewestCubes.numberOfGreen){
                    fewestCubes.numberOfGreen = set.numberOfGreen;
                }
                if (set.numberOfBlue > fewestCubes.numberOfBlue){
                    fewestCubes.numberOfBlue = set.numberOfBlue;
                }
            }
            gamePower = fewestCubes.numberOfBlue * fewestCubes.numberOfRed * fewestCubes.numberOfGreen;
            this.gamePowers.add(gamePower);
        }
        System.out.println(gamePowers.stream().reduce(0, Integer::sum));

    }


        Predicate<SetOfBalls> isSetPossible = set -> set.numberOfBlue <= maxValuesInASet.numberOfBlue
                    && set.numberOfGreen <= maxValuesInASet.numberOfGreen
                    && set.numberOfRed <= maxValuesInASet.numberOfRed;
}
