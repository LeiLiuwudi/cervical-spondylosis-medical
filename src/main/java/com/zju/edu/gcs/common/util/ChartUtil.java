package com.zju.edu.gcs.common.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

/**
 * Created on 2021/11/23
 *
 * @author Patric Tian
 */
public class ChartUtil {
    private static final Color[] BAR_COLORS = new Color[]{
            new Color(79,129,189),
            new Color(192, 80, 77),
            new Color(155, 187, 89),
    };

    private static final Color[] LINE_COLORS = new Color[]{
            new Color(237, 123, 46),
            new Color(90, 154, 213),
            new Color(164, 164, 164),
    };

    private static final Color[] PIE_COLORS = new Color[]{
            new Color(75, 172, 198),
            new Color(128, 100, 162),
            new Color(155, 187, 89),
            new Color(192, 80, 77),
            new Color(79, 129, 189),
            new Color(44, 77, 117),
            new Color(247, 150, 70),
            new Color(165, 165, 165),
    };


    private static StandardChartTheme initChartTheme(){
        StandardChartTheme currentTheme = new StandardChartTheme("JFree");
        // 横轴纵轴标题文字大小
        currentTheme.setLargeFont(new java.awt.Font("宋体", java.awt.Font.BOLD, 15));
        // 横轴纵轴数值文字大小
        currentTheme.setRegularFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 13));
        currentTheme.setExtraLargeFont(new java.awt.Font("宋体", java.awt.Font.BOLD, 20));
        // 背景颜色
        currentTheme.setPlotBackgroundPaint(new Color(255, 255, 204, 0));
        // 边框线条
        currentTheme.setPlotOutlinePaint(new Color(0, 0, 0, 0));
        // 网格线条
        currentTheme.setRangeGridlinePaint(new Color(78, 74, 74));
        return currentTheme;
    }

    /**
     * 线图
     *
     * @param title 标题
     * @param categoryAxisLabel 分类标签
     * @param valueAxisLabel 数值标签
     * @param dataset 数据集
     * @return org.jfree.chart.JFreeChart
     * @author Hou_fx
     * @date 2021.8.4 10:39
     */
    public static JFreeChart lineChart(String title, String categoryAxisLabel, String valueAxisLabel, DefaultCategoryDataset dataset){
        ChartFactory.setChartTheme(initChartTheme());

        JFreeChart chart = ChartFactory.createLineChart(
                title,
                categoryAxisLabel,
                valueAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        // 折现点显示数值
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // 更改线条颜色
        for (int i = 0; i < dataset.getRowKeys().size(); i++) {
            renderer.setSeriesPaint(i, LINE_COLORS[i]);
        }
        return chart;
    }
}
