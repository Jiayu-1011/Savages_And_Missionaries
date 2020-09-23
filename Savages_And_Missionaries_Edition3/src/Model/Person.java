package Model;

import Drawer.PersonDrawer;
import Interface.Drawable;
import edu.princeton.cs.algs4.Draw;

abstract public class Person implements Drawable {
    double centerX;
    double standOnY;

    public Person(double x, double y) {
        centerX = x;
        standOnY = y;
    }

    public abstract void draw();
    public abstract PersonDrawer getDrawer();

    public void setPos(double x, double y) {
        centerX = x;
        standOnY = y;
    }

    public double getX() {
        return centerX;
    }

    public double getY() {
        return standOnY;
    }
}
