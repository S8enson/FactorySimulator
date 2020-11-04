/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory.simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sam
 */
public class Parcel<E> implements Comparable<Parcel<?>> {

    private E element;
    private Color colour;
    private int consumeTime;
    private int priority;
    private long timestamp;

    public Parcel(E element, Color colour, int consumeTime, int priority) {
        this.element = element;
        this.colour = colour;
        this.consumeTime = consumeTime;
        this.priority = priority;
        this.timestamp = System.nanoTime();
    }

    public void consume() {
        try {
            Thread.sleep(consumeTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(Parcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String toString() {
        return element + "(" + priority + ")";
    }

    public void drawBox(Graphics g, int x, int y, int width, int height) {
        g.setColor(colour);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawString(this.element+"("+this.priority+")", x + width/2, y + height/2);

    }

    @Override
    public int compareTo(Parcel<?> p) {
        if (this.priority > p.priority) {
            return -1;
        } else if (this.priority < p.priority) {
            return 1;
        } else {
            if (this.timestamp > p.timestamp) {
                return -1;
            } else if (this.timestamp < p.timestamp) {
                return 1;
            } else {
                return 0;
            }

        }
    }

}
