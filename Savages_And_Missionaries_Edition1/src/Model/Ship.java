package Model;

import Drawer.ShipDrawer;
import Interface.Drawable;

import java.awt.*;
import java.util.ArrayList;

public class Ship implements Drawable {
    private double centerX = 0.0;
    private double topY = 0.0;
    private double length = 60;
    private ArrayList<Person> people;
    private ShipDrawer drawer;
    private int K;

    public Ship(double x, double y, double length, int K) {
        this.centerX = x;
        this.topY = y;
        this.length = length;
        this.K = K;
        people = new ArrayList<>(K);
//        System.out.println(this.length);
        this.drawer = new ShipDrawer(this.length, Color.lightGray, 0.0025);
    }

    public void setDrawer(ShipDrawer newDrawer) {
        drawer = newDrawer;
    }

    public Ship() {
        // nothing to do
        people = new ArrayList<>(16);
    }

    public void draw() {
        drawer.drawShip(centerX, topY);
        // 对于人来说，人的位置就是相对于船的位置
        for (Person p : people) {
            p.getDrawer().drawPerson(p.centerX + centerX, topY);
        }
    }

    public void setPos(double x, double y) {
        centerX = x;
        topY = y;
    }

    public void addPerson(Person p, double x) {
        p.setPos(x, 0);
        people.add(p);
    }

    public void kickOffAllPeople() {
        people.clear();
    }

    public double getX() {
        return centerX;
    }

    public double getY() {
        return topY;
    }
}
