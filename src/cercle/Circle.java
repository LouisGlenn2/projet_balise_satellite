package cercle;

import java.awt.Color;
import src.nicellipse.component.NiEllipse;

public class Circle {
    private int x, y;
    private int space;
    private int size;
    boolean visible;

    public Circle(int x, int y) {
        this.x = x;
        this.y = y;
        this.size = 340;
        this.space = 30;
    }

    /**
     * Création des cercles de signal autour de la balise.
     */
    public NiEllipse createSignal() {

        NiEllipse first = createCircle(0, this.x, this.y, size);

        NiEllipse current = first;
        for (int i = 1; i < 10; i++) {
            int s = size - (i * (2 * space));
            NiEllipse next = createCircle(i, space, space, s);
            current.add(next);
            current = next;
        }

        return first;
    }

    /**
     * Création d'un cercle
     */
    private NiEllipse createCircle(int index, int x, int y, int size) {
        NiEllipse c = new NiEllipse();
        c.setBounds(x, y, size, size);
        c.setBorderColor(Color.BLACK);
        c.setOpaque(false);
        c.setBackground(new Color(0, 0, 0, 0));
        return c;
    }

    public void setLocalisation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setVisible(boolean b) {
        this.visible = b;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int spacing) {
        this.space = spacing;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isVisible() {
        return visible;
    }
}