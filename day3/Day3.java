package day3;

import utils.FileProcessor;

import java.util.ArrayList;
import java.util.List;

public class Day3 {

    char[][] matrix;
    List<SpecificNumber> numbers = new ArrayList<>();

    public Day3() {
    }

    public char[][] getMatrix() {
        List<String> lines = FileProcessor.readFile("day3/input.txt");
        int x_max = lines.size();
        int y_max = lines.get(0).length();
        char[][] matrix = new char[x_max][y_max];

        for (int i = 0; i < lines.size(); i++) {
            System.arraycopy(lines.get(i).toCharArray(), 0, matrix[i], 0, lines.get(i).length());
        }
        return matrix;
    }

    public void processPart1() {
        this.matrix = getMatrix();
        //printMatrix(this.matrix);
        SpecificNumber current_number = new SpecificNumber(-1, false);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                char cell = matrix[i][j];
                if (Character.isDigit(cell)) {
                    if (current_number.number == -1) {
                        current_number.number = Character.getNumericValue(cell);
                    } else {
                        current_number.number = Integer.parseInt(String.valueOf(current_number.number) + cell);
                    }
                    if (!current_number.isPartNumber && isNumberCloseToSymbol(matrix, i, j)) {
                        current_number.isPartNumber = true;
                    }
                } else {
                    if (current_number.number != -1) {
                        numbers.add(current_number);
                    }
                    current_number = new SpecificNumber(-1, false);
                }
            }
        }

        System.out.println(numbers.stream()
                .filter(specificNumber -> specificNumber.isPartNumber)
                .map(specificNumber -> specificNumber.number)
                .reduce(0, Integer::sum));
    }

    public void processPart2() {
        this.matrix = getMatrix();
        //printMatrix(this.matrix);
        List<Gear> gears = new ArrayList<>();
        GearNumber numberA = new GearNumber(-1);
        GearNumber numberB = new GearNumber(-1);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                char cell = matrix[i][j];
                if (cell == '*') {
                    //FIRST ROW
                    if (i - 1 >= 0 && j - 1 >= 0 && Character.isDigit(matrix[i-1][j-1])){
                        int k = i-1;
                        int l = j-1;
                        numberA.isAbove = true;
                        getFullNumber(numberA, k, l);
                    }
                    if (i - 1 >= 0 && Character.isDigit(matrix[i-1][j]) && !numberA.exists()){
                        int k = i-1;
                        int l = j;
                        numberA.isAbove = true;
                        getFullNumber(numberA, k, l);
                    }
                    if (i - 1 >= 0 && j + 1 <= matrix[i].length && Character.isDigit(matrix[i-1][j+1]) && numberA.exists()){
                        int k = i-1;
                        int l = j+1;
                        if (numberA.exists() && !Character.isDigit(matrix[i-1][j])){
                            numberB.isAbove = true;
                            getFullNumber(numberB, k, l);
                        }
                    }
                    if (i - 1 >= 0 && j + 1 <= matrix[i].length && Character.isDigit(matrix[i-1][j+1]) && !numberA.exists()){
                        int k = i-1;
                        int l = j+1;
                        numberA.isAbove = true;
                        getFullNumber(numberA, k, l);
                    }
                    // SECOND ROW
                    if (j - 1 >= 0 && Character.isDigit(matrix[i][j-1]) && numberA.exists() && !numberB.exists() && !numberA.isLeft){
                        int k = i;
                        int l = j-1;
                        numberB.isLeft = true;
                        getFullNumber(numberB, k, l);
                    }
                    if (j - 1 >= 0 && Character.isDigit(matrix[i][j-1]) && !numberA.exists()){
                        int k = i;
                        int l = j-1;
                        numberA.isLeft = true;
                        getFullNumber(numberA, k, l);
                    }
                    if (j + 1 <= matrix[i].length && Character.isDigit(matrix[i][j+1]) && numberA.exists() && !numberB.exists() && !numberA.isRight){
                        int k = i;
                        int l = j+1;
                        numberB.isRight = true;
                        getFullNumber(numberB, k, l);
                    }
                    if (j + 1 <= matrix[i].length && Character.isDigit(matrix[i][j+1]) && !numberA.exists()){
                        int k = i;
                        int l = j+1;
                        numberA.isRight = true;
                        getFullNumber(numberA, k, l);
                    }

                    //THIRD ROW
                    if (i + 1 <= matrix.length && j - 1 >= 0 && Character.isDigit(matrix[i+1][j-1]) && numberA.exists() && !numberB.exists()){
                        int k = i+1;
                        int l = j-1;
                        numberB.isBelow = true;
                        getFullNumber(numberB, k, l);
                    }
                    if (i + 1 <= matrix.length && j - 1 >= 0 && Character.isDigit(matrix[i+1][j-1]) && !numberA.exists()){
                        int k = i+1;
                        int l = j-1;
                        numberA.isBelow = true;
                        getFullNumber(numberA, k, l);
                    }
                    if (i + 1 <= matrix.length && Character.isDigit(matrix[i+1][j]) && numberA.exists() && !numberB.exists() && !numberA.isBelow){
                        int k = i+1;
                        int l = j;
                        numberB.isBelow = true;
                        getFullNumber(numberB, k, l);
                    }
                    if (i + 1 <= matrix.length && Character.isDigit(matrix[i+1][j]) && !numberA.exists()){
                        int k = i+1;
                        int l = j;
                        numberA.isBelow = true;
                        getFullNumber(numberA, k, l);
                    }
                    if (i + 1 <= matrix.length && j + 1 <= matrix[i].length && Character.isDigit(matrix[i+1][j+1]) && numberA.exists() && !numberB.exists()){
                        int k = i+1;
                        int l = j+1;
                        if (numberA.exists() && !Character.isDigit(matrix[i+1][j])){
                            numberB.isBelow = true;
                            getFullNumber(numberB, k, l);
                        }
                    }
                    if (i + 1 <= matrix.length && j + 1 <= matrix[i].length && Character.isDigit(matrix[i+1][j+1]) && !numberA.exists()){
                        int k = i+1;
                        int l = j+1;
                        numberA.isBelow = true;
                        getFullNumber(numberA, k, l);
                    }

                    if (numberA.exists() && numberB.exists()){
                        Gear gear = new Gear(numberA, numberB);
                        gears.add(gear);
                    }
                    numberA = new GearNumber(-1);
                    numberB = new GearNumber(-1);
                }
            }

        }

        for (var gear: gears){
            System.out.println(gear.numberA.number);
            System.out.println(gear.numberB.number);
        }
        System.out.println(gears.stream()
                .map(gear -> gear.ratio)
                .reduce(0L, Long::sum));

    }

    private void getFullNumber(GearNumber numberA, int i, int j) {
        int k = i;
        int l = j;

        StringBuilder value = new StringBuilder("" + matrix[i][j]);

        while (l - 1 >= 0 && Character.isDigit(matrix[k][l-1])){
            l = l - 1;
            value.insert(0, matrix[k][l]);
        }

        k = i;
        l = j;
        while (l + 1 <= matrix[i].length - 1 && Character.isDigit(matrix[k][l+1])){
            l = l + 1;
            value.append(matrix[k][l]);
        }

        numberA.number = Integer.parseInt(value.toString());
    }

    public boolean isSymbol(char[][] matrix, int i, int j) {
        return !Character.isDigit(matrix[i][j]) && matrix[i][j] != '.';
    }

    public boolean isNumberCloseToSymbol(char[][] matrix, int i, int j) {
        return (i - 1 > 0 && j - 1 > 0 && isSymbol(matrix, i - 1, j - 1))
                || (i - 1 > 0 && isSymbol(matrix, i - 1, j))
                || (i - 1 > 0 && j + 1 < matrix[i].length && isSymbol(matrix, i - 1, j + 1))
                || (j - 1 > 0 && isSymbol(matrix, i, j - 1))
                || (j + 1 < matrix[i].length && isSymbol(matrix, i, j + 1))
                || (i + 1 < matrix.length && j - 1 > 0 && isSymbol(matrix, i + 1, j - 1))
                || (i + 1 < matrix.length && isSymbol(matrix, i + 1, j))
                || (i + 1 < matrix.length && j + 1 < matrix[i].length && isSymbol(matrix, i + 1, j + 1));
    }

    public static void printMatrix(char matrix[][]) {
        for (char[] row : matrix) {
            // traverses through number of rows
            for (char element : row) {
                // 'element' has current element of row index
                System.out.print(element + "\t");
            }
            System.out.println();
        }
    }


}
