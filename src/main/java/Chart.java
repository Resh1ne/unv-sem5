import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class Chart extends JFrame {

    private static final int ALPHABET_LENGTH = 62;

    public Chart(String title) {
        super(title);

        // Создание графика
        JFreeChart chart = createChart(createDataset());

        // Настройка логарифмической шкалы для оси Y
        chart.getCategoryPlot().setRangeAxis(new LogarithmicAxis("Среднее время подбора (секунды)"));

        // Создание панели для графика
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private static double calculateBruteForceTime(int length) {
        double possibleCombinations = Math.pow(ALPHABET_LENGTH, length) / 2;
        double timePerAttempt = 0.000001;
        return possibleCombinations * timePerAttempt;
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int length = 1; length <= 20; length++) {
            double bruteForceTime = calculateBruteForceTime(length);
            dataset.addValue(bruteForceTime, "Время подбора", Integer.toString(length));
        }

        return dataset;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        return ChartFactory.createLineChart(
                "Зависимость времени подбора пароля от его длины",  // Заголовок
                "Длина пароля",                                    // Ось X
                "Среднее время подбора (секунды)",                 // Ось Y
                dataset,                                           // Данные
                PlotOrientation.VERTICAL,                         // Ориентация графика
                true,                                              // Легенда
                true,                                              // Инструменты
                false                                              // URL
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Chart example = new Chart("График времени подбора пароля");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
