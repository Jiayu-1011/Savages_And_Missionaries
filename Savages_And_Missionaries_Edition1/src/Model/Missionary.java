package Model;

import Drawer.PersonAttributes;
import Drawer.PersonDrawer;

import java.awt.*;

public class Missionary extends Person {
    public Missionary(double x, double y) {
        super(x, y);
    }

    private static PersonDrawer drawer = new PersonDrawer(
            PersonAttributes.fromTemplate(Color.blue, 0.002, 10, false));

    public static void setDrawer(PersonDrawer newDrawer) {
        drawer = newDrawer;
    }

    @Override
    public void draw() {
        drawer.drawPerson(centerX, standOnY);
    }

    @Override
    public PersonDrawer getDrawer() {
        return drawer;
    }
}
