import org.apache.commons.math3.complex.Complex;

import java.util.*;


public class FFTExample {

    private static int action = 1;
    private final List<Object> arrayData = new ArrayList<>();
    private final List<List<Object>> listOfNewData = new ArrayList<>();
    private final List<List<Double>> evenOddPairs = new ArrayList<>();
    private int frequency;

    public void startProgram() {
        System.out.println("Выберите что хотите подать на вход\n1 - SIN(X)\t\t2 - COS(X)");
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        int inputData;
        while (true) {
            try {
                inputData = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите число 1 или 2.");
                scanner.next();
                continue;
            }

            if (inputData > 2 || inputData < 1) {
                System.out.println("Вы ввели неверное значение. Повторите попытку: ");
            } else {
                break;
            }
        }
        System.out.println("Введите частоту: ");
        while (true) {
            try {
                this.frequency = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Пожалуйста, повторите ввод");
                scanner.next();
                continue;
            }
            double variable = Math.log(this.frequency) / Math.log(2);
            if (variable != Math.floor(variable)) {
                System.out.println("Число должно быть степенью двойки.");
            } else {
                break;
            }
        }
        chooseFunction(inputData);
        System.out.println(arrayData);
        List<Double> transformArray = new ArrayList<>();
        for (Object obj : this.arrayData) {
            transformArray.add((Double) obj);
        }
        divArrayToEvenOdd(transformArray);
        if (this.arrayData.size() == 2) {
            this.evenOddPairs.add(transformArray);
        }
//        System.out.println(this.evenOddPairs);
        processingW(0, 2);
        processingProgram();
//        System.out.println(this.listOfNewData);
        recursiveProcessingData();
//        System.out.println(this.listOfNewData);
    }

    public void recursiveProcessingData() {
        if (this.listOfNewData.size() > 1) {
            List<Object> firstList;
            List<Object> secondList;
            firstList = this.listOfNewData.remove(0);
            secondList = this.listOfNewData.remove(0);
            List<Object> sumList = sumResult(firstList, secondList);
            List<Object> subList = subResult(firstList, secondList);
            writeCorrectData(sumList, subList);
            recursiveProcessingData();
//            System.out.println(firstList + "\n" + secondList);
        }
    }

    public void writeCorrectData(List<Object> firstList, List<Object> secondList) {
        List<Object> finalWrite = new ArrayList<>();
        for (int i = 0; i < firstList.size(); i++) {
            finalWrite.add(firstList.get(i));
            finalWrite.add(secondList.get(i));
        }
        this.listOfNewData.add(finalWrite);
    }

    public List<Object> sumResult(List<Object> firstList, List<Object> secondList) {
        List<Object> sumList = new ArrayList<>();
        for (int i = 0; i < firstList.size(); i++) {
            Complex W = processingW(i, firstList.size() * 2);
            Complex firstElement;
            Complex secondElement;
            if (firstList.get(i) instanceof Double) {
                firstElement = new Complex((Double) firstList.get(i), 0.0);
            } else {
                firstElement = (Complex) firstList.get(i);
            }
            if (secondList.get(i) instanceof Double) {
                secondElement = new Complex((Double) secondList.get(i), 0.0);
            } else {
                secondElement = (Complex) secondList.get(i);
            }
            Complex resultMulti = W.multiply(secondElement);
            Complex result = firstElement.add(resultMulti);
            sumList.add(result);
            System.out.print(action++ + ") " + "(" + firstElement.getReal() + " + " + firstElement.getImaginary() + "i) + (");
            System.out.println(W.getReal() + " + " + W.getImaginary() + "i) * " + secondElement.getReal() +
                    " + " + secondElement.getImaginary() + "i) = (" + result.getReal() + " + " +
                    result.getImaginary() + "i)");
        }
        return sumList;
    }

    public List<Object> subResult(List<Object> firstList, List<Object> secondList) {
        List<Object> subList = new ArrayList<>();
        for (int i = 0; i < firstList.size(); i++) {
            Complex W = processingW(i, firstList.size() * 2);
            Complex firstElement;
            Complex secondElement;
            if (firstList.get(i) instanceof Double) {
                firstElement = new Complex((Double) firstList.get(i), 0.0);
            } else {
                firstElement = (Complex) firstList.get(i);
            }
            if (secondList.get(i) instanceof Double) {
                secondElement = new Complex((Double) secondList.get(i), 0.0);
            } else {
                secondElement = (Complex) secondList.get(i);
            }
            Complex resultMulti = W.multiply(secondElement);
            Complex result = firstElement.subtract(resultMulti);
            subList.add(result);
            System.out.print(action++ + ") " + "(" + firstElement.getReal() + " + " + firstElement.getImaginary() + "i) - (");
            System.out.println(W.getReal() + " + " + W.getImaginary() + "i) * " + secondElement.getReal() +
                    " + " + secondElement.getImaginary() + "i) = (" + result.getReal() + " + " +
                    result.getImaginary() + "i)");
        }
        return subList;
    }

    public void processingProgram() {
        if (!this.evenOddPairs.isEmpty()) {
            for (List<Double> currentList : this.evenOddPairs) {
                List<Object> resultProcess = new ArrayList<>();
                Complex W = processingW(0, 2);
                Double valuePlus = currentList.get(0) + currentList.get(1);
                Double valueMinus = currentList.get(0) - currentList.get(1);
                System.out.println(action++ + ") " + currentList.get(0) + " + " + W.getReal() + " * " + currentList.get(1) + " = " + valuePlus);
                System.out.println(action++ + ") " + currentList.get(0) + " - " + W.getReal() + " * " + currentList.get(1) + " = " + valueMinus);
                resultProcess.add(valuePlus);
                resultProcess.add(valueMinus);
                this.listOfNewData.add(resultProcess);
            }
        }
    }

    public Complex processingW(int k, int N) {
        double angle = -2 * Math.PI * k / N;
        double real = Math.cos(angle);
        double imaginary = Math.sin(angle);
        double threshold = 1e-10;
        real = Math.abs(real) < threshold ? 0.0 : real;
        imaginary = Math.abs(imaginary) < threshold ? 0.0 : imaginary;
        Complex W = new Complex(real, imaginary);
//        System.out.println(W);
        return W;
    }

    public void divArrayToEvenOdd(List<Double> array) {
        if (array.size() > 2) {
            List<Double> even = new ArrayList<>();
            List<Double> odd = new ArrayList<>();
            int i = 0;
            for (double var : array) {
                if (i == 0) {
                    even.add(var);
                    i = 1;
                } else {
                    odd.add(var);
                    i = 0;
                }
            }
            if (even.size() == 2) {
                this.evenOddPairs.add(even);
            }
            if (odd.size() == 2) {
                this.evenOddPairs.add(odd);
            }
            this.divArrayToEvenOdd(even);
            this.divArrayToEvenOdd(odd);
        }
    }

    public void chooseFunction(int data) {
        switch (data) {
            case 1:
                setArraySin();
                break;
            case 2:
                setArrayCos();
                break;
        }
    }


    public void setArraySin() {
        double step = Math.PI * 2 / this.frequency;
        for (int i = 0; i < this.frequency; i++) {
            double value = Math.sin(i * step);
            arrayData.add(value);
        }
    }

    public void setArrayCos() {
        double step = Math.PI * 2 / this.frequency;
        for (int i = 0; i < this.frequency; i++) {
            double value = Math.cos(i * step);
            arrayData.add(value);
        }
    }

    // Метод для получения частотного спектра
    public List<Double> getAmplitudes() {
        List<Double> amplitudes = new ArrayList<>();
        for (Object obj : this.listOfNewData.get(0)) { // Используем первый набор данных
            Complex complex = obj instanceof Double ? new Complex((Double) obj, 0) : (Complex) obj;
            double amplitude = Math.sqrt(Math.pow(complex.getReal(), 2) + Math.pow(complex.getImaginary(), 2));
            amplitudes.add(amplitude);
        }
        return amplitudes;
    }

    // Метод для получения списка частот
    public List<Double> getFrequencies() {
        List<Double> frequencies = new ArrayList<>();
        double sampleRate = frequency; // Частота дискретизации
        int n = this.listOfNewData.get(0).size(); // Размер данных
        for (int i = 0; i < n; i++) {
            double freq = i * (sampleRate / n); // Шаг частоты
            frequencies.add(freq);
        }
        return frequencies;
    }

    public int getFrequency() {
        return this.frequency;
    }
}
