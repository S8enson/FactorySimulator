/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory.simulator;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Sam
 */
class Dispatcher implements Runnable{
    
    ConveyorBelt[] conveyorBelts;
    Thread t;
    boolean connected;
    
    public Dispatcher(ConveyorBelt[] conveyorBelts){
    this.conveyorBelts = conveyorBelts;
    t = new Thread(this);
    t.start();
    connected = false;
    }
    
    @Override
    public void run(){
    int i = 0;
        while (t.isAlive()) {
            if (connected) {
                conveyorBelts[i].retrieveParcel(this).consume();
                conveyorBelts[i].disconnectDispatcher(this);
                connected = false;
                i = (i+1)%conveyorBelts.length;
            }
            if (!connected) {
                while(i < conveyorBelts.length) {
                    if (!conveyorBelts[i].isEmpty()) {
                        try {
                            if (conveyorBelts[i].connectDispatcher(this)) {
                                connected = true;
                                break;
                            }
                        } catch (Exception e) {
                        }
                    }
                    i = (i+1)%conveyorBelts.length;
                }

            }
        }
    }
    
    public void drawDispatcher(Graphics g, int x, int y, int radius){
    g.setColor(Color.BLUE);
    g.fillOval(x, y, radius, radius);
    }
    
    public void kill(){
    t.stop();
    }
    
}
