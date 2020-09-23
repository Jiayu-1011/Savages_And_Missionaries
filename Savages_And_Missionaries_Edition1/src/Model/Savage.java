package Model;

import Drawer.PersonAttributes;
import Drawer.PersonDrawer;

import java.awt.*;

public class Savage extends Person {
    public Savage(double x, double y) {
        super(x, y);
    }

    private static PersonDrawer drawer = new PersonDrawer(
            PersonAttributes.fromTemplate(Color.red, 0, 10, true));

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
