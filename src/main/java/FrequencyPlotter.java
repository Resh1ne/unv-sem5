import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.List;

public class FrequencyPlotter extends JFrame {

    public FrequencyPlotter(String title, List<Double> frequencies, List<Double> amplitudes) {
        super(title);

        // Создание графика
        JFreeChart chart = createChart(frequencies, amplitudes);

        // Панель для графика
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    // Метод для создания графика
    private JFreeChart createChart(List<Double> frequencies, List<Double> amplitudes) {
        // Данные для графика
        XYSeries series = new XYSeries("Amplitude Spectrum");

        for (int i = 0; i < frequencies.size()-1; i++) {
            series.add(frequencies.get(i)*2, amplitudes.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Создание линейного графика
        return ChartFactory.createXYLineChart(
                "Frequency Spectrum",
                "Frequency (Hz)",
                "Amplitude",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    // Метод для отображения окна с графиком
    public static void showFrequencyPlot(List<Double> frequencies, List<Double> amplitudes) {
        SwingUtilities.invokeLater(() -> {
            FrequencyPlotter example = new FrequencyPlotter("Frequency Spectrum", frequencies, amplitudes);
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}