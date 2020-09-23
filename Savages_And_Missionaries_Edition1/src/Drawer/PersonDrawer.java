package Drawer;

import edu.princeton.cs.algs4.StdDraw;

public class PersonDrawer {

    private PersonAttributes personAttr;

    public void setPersonAttr(PersonAttributes personAttr) {
        this.personAttr = personAttr;
    }

    public PersonDrawer(PersonAttributes attr) {
        personAttr = attr;
    }

    public void drawPerson(double centerX, double standOnY) {
        // 设置笔刷
        StdDraw.setPenColor(personAttr.lineColor);
        StdDraw.setPenRadius(personAttr.lineRadius);
        // 计算中间属性
        final double legTop = standOnY + personAttr.legHeight;
        final double neckTop = legTop + personAttr.bodyHeight + personAttr.neckHeight;
        final double handArcCenterY = legTop + personAttr.bodyHeight - personAttr.handArcRadius;
        // 绘制双手
        drawHand(centerX, handArcCenterY, personAttr.handsUpward);
        // 绘制双腿
        StdDraw.line(centerX - personAttr.legWidth, standOnY, centerX, legTop);
        StdDraw.line(centerX + personAttr.legWidth, standOnY, centerX, legTop);
        // 绘制支干
        StdDraw.line(centerX, legTop, centerX, neckTop);
        // 绘制头
        StdDraw.circle(centerX, neckTop + personAttr.headRadius, personAttr.headRadius);
    }

    private void drawHand(double x, double y, boolean upward) {
        if (upward) StdDraw.arc(x, y + personAttr.handArcRadius * 1.8, personAttr.handArcRadius, 225, 315);
        else        StdDraw.arc(x, y, personAttr.handArcRadius, 45, 135);
    }

}