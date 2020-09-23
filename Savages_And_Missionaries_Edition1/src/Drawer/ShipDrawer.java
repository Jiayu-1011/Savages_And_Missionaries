package Drawer;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class ShipDrawer {
    private double upperLength;     // 主基准
    private double lowerLength;
    private double height;
    private double upperRadian;     // 半角弧度值
    private double lowerRadian;

    private Color color;
    private double lineRadius;

    public ShipDrawer(double length, Color color, double lineRadius) {
        upperLength = length;
        lowerLength = length * 275.0 / 440.0;
        height = length * 80.0 / 440.0;
        upperRadian = 16.0 / 180.0 * 3.14159;
        lowerRadian = 7.0 / 180.0 * 3.14159;
        this.color = color != null ? color : Color.lightGray;
        this.lineRadius = lineRadius > 0 ? lineRadius : 0.0025;
    }

    public void drawShip(double centerX, double topY) {
        StdDraw.setPenRadius(lineRadius);
        StdDraw.setPenColor(color);
        StdDraw.line(centerX - lowerLength / 2, topY - height, centerX + lowerLength / 2, topY - height);
        StdDraw.line(centerX - upperLength / 2, topY, centerX - lowerLength / 2, topY - height);
        StdDraw.line(centerX + upperLength / 2, topY, centerX + lowerLength / 2, topY - height);
        double h = upperLength / 2 / Math.tan(upperRadian);
        double arc = upperLength / 2 / Math.sin(upperRadian);
        double degree = upperRadian / 3.14159 * 180.0;
        StdDraw.arc(centerX, topY + h, arc, 270 - degree, 270 + degree);
        h = upperLength / 2 / Math.tan(lowerRadian);
        arc = upperLength / 2 / Math.sin(lowerRadian);
        degree = lowerRadian / 3.14159 * 180.0;
        StdDraw.arc(centerX, topY - h, arc, 90 - degree, 90 + degree);
    }


}
