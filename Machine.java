/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory.simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Sam
 */
class Machine implements Runnable {

    public static long MIN_CONSUMPTION_TIME = 100;
    public static long MAX_CONSUMPTION_TIME = 1000;
    public static long MIN_PRODUCTION_TIME = 100;
    public static long MAX_PRODUCTION_TIME = 1000;
    public static ConveyorBelt[] conveyorBelts;
    Thread t;
    Random r;
    boolean connected;

    public Machine(ConveyorBelt[] conveyorBelts) {
        this.conveyorBelts = conveyorBelts;
        r = new Random();
        t = new Thread(this);
        t.start();
        connected = false;
    }

    @Override
    public void run() {
        int i = 0;
        while (t.isAlive()) {
            if (connected && !conveyorBelts[i].isFull()) {
                
                long sleep = r.nextInt((int) (MAX_PRODUCTION_TIME - MIN_PRODUCTION_TIME)) + MIN_PRODUCTION_TIME;
                try {
                    t.sleep(sleep);
                } catch (Exception e) {
                }
                conveyorBelts[i].postParcel(newParcel(), this);
            } else if (connected && conveyorBelts[i].isFull()) {
                conveyorBelts[i].disconnectMachine(this);
                connected = false;
            }
            if (!connected) {
                
                for (i = 0; i < conveyorBelts.length; i++) {
                    if (!conveyorBelts[i].isFull()) {
                        try {
                            if (conveyorBelts[i].connectMachine(this)) {
                                connected = true;
                                break;
                            }
                        } catch (Exception e) {
                        }
                    }
                }

            }
        }
    }

    public Parcel newParcel() {
        char c = (char) (r.nextInt(26) + 'A');
        Color colour = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
        int consumeTime = (int) (r.nextInt((int) MAX_CONSUMPTION_TIME) + MIN_CONSUMPTION_TIME);
        int priority = r.nextInt(3) + 1;
        Parcel parcel = new Parcel(c, colour, consumeTime, priority);
        return parcel;
    }

    public void drawMachine(Graphics g, int x, int y, int radius) {
        g.setColor(Color.RED);
        g.fillOval(x, y, radius, radius);
        
        
    }
    
    public void kill(){
    t.stop();
    }
    

}
