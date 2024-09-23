import java.util.List;

public class Main {

    public static void main(String[] args) {
        FFTExample fftExample = new FFTExample();
        fftExample.startProgram();
        // Получение частот и амплитуд
        List<Double> frequencies = fftExample.getFrequencies();
        List<Double> amplitudes = fftExample.getAmplitudes();

        // Построение графика
        FrequencyPlotter.showFrequencyPlot(frequencies, amplitudes);
    }
}
