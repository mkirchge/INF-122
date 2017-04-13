import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.DefaultCategoryItemRenderer;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

public class LineChart extends ApplicationFrame {

    public LineChart(String applicationTitle , String chartTitle , DefaultCategoryDataset ds) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle, "Time","Number of Tokens",
                ds, PlotOrientation.VERTICAL, true,true,false);

        CategoryPlot cp = (CategoryPlot) ((JFreeChart) lineChart).getPlot();
        CategoryAxis ca = cp.getDomainAxis();
        ca.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);

        lineChart.setBackgroundPaint(Color.white);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600 , 400));
        setContentPane(chartPanel);
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) cp.getRenderer();
        renderer.setBaseShapesVisible(true);

    }

}