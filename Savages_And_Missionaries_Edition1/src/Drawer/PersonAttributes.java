package Drawer;

import java.awt.*;

// 不可变对象
public class PersonAttributes {
    final boolean handsUpward;
    final Color lineColor;
    final double lineRadius;
    final double height;
    final double width;
    final double headRadius;
    final double neckHeight;
    final double handArcRadius;
    final double bodyHeight;
    final double legWidth;
    final double legHeight;

    /*
    1.使用默认构造函数进行创建，所有属性均有默认值，若需要可以更改
    2.是生成PersonAttributes的参数
    */
    public static class FlexibleAttributes {
        public Color color = Color.black;
        public double lineRadius = 0.002;
        public boolean handsUpward = false;            // 手是否朝上
        public double height = 30.0;
        /* 标准人物尺寸 */
        public double frameRatio = 180.0 / 410.0;      // 人物宽
        public double headRadiusRatio = 80.0 / 410.0;  // 头
        public double neckRatio = 70.0 / 410.0;        // 脖子
        public double handArcRatio = 140.0 / 410.0;    // 手的弧半径
        public double bodyRatio = 100.0 / 410.0;       // 身体高度
        public double legWidthRatio = 70.0 / 410.0;    // 腿宽
        public double legHeightRatio = 80.0 / 410.0;   // 腿高
    }

    private PersonAttributes(FlexibleAttributes attr) {
        handsUpward = attr.handsUpward;
        lineColor = attr.color;
        lineRadius = attr.lineRadius;
        height = attr.height;
        width = attr.height * attr.frameRatio;
        headRadius = attr.height * attr.headRadiusRatio;
        neckHeight = attr.height * attr.neckRatio;
        handArcRadius = attr.height * attr.handArcRatio;
        bodyHeight = attr.height * attr.bodyRatio;
        legWidth = attr.height * attr.legWidthRatio;
        legHeight = attr.height * attr.legHeightRatio;
    }

    public static PersonAttributes fromFlexibleAttributes(FlexibleAttributes attr) {
        return new PersonAttributes(attr);
    }

    public static PersonAttributes fromTemplate(Color color, double lineRadius, double height, boolean handsUpward) {
        FlexibleAttributes attr = new FlexibleAttributes();
        attr.color = color;
        attr.height = height;
        attr.handsUpward = handsUpward;
        attr.lineRadius = lineRadius > 0 ? lineRadius : 0.002;
        return new PersonAttributes(attr);
    }
}