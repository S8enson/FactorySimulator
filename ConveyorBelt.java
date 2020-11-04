/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory.simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 *
 * @author Sam
 */
public class ConveyorBelt {
    
    private int maxCapacity;
    private Machine connectedMachine;
    private Dispatcher connectedDispatcher;
   private PriorityQueue<Parcel<?>> queue;
   
   public ConveyorBelt(int maxCapacity){
   this.maxCapacity = maxCapacity;
   this.connectedMachine = null;
   this.connectedDispatcher = null;
   queue = new PriorityQueue<>(maxCapacity);
   }
   
   public ConveyorBelt(){
   this(10);
   }
   
   public synchronized boolean connectMachine(Machine machine){
       if(connectedMachine == null){
       connectedMachine = machine;
       return true;
       }
       else{
       return false;
       }
   }
   
   public synchronized boolean connectDispatcher(Dispatcher dispatcher){
   if(connectedDispatcher == null){
       connectedDispatcher = dispatcher;
       return true;
       }
       else{
       return false;
       }
   }
   
   public synchronized boolean disconnectMachine(Machine machine){
       if(connectedMachine == machine){
   connectedMachine = null;
   return true;
       }
       else{
       return false;
       }
   }
   
   public synchronized boolean disconnectDispatcher(Dispatcher dispatcher){
       if(connectedDispatcher == dispatcher){
   connectedDispatcher = null;
   return true;
       }
       else{
       return false;
       }
   }
   
   public int size(){
   return maxCapacity;
   }
   
   public boolean isEmpty(){
   return queue.isEmpty();
   }
   
   public boolean isFull(){
   return queue.size() == maxCapacity;
   }
   
   public synchronized boolean postParcel(Parcel<?> p, Machine machine){
       if(connectedMachine == machine){
       queue.offer(p);
   return true;
       }
       else{
       return false;
       }
   }
   
   public synchronized Parcel<?> getFirstParcel(Dispatcher dispatcher){
   if(connectedDispatcher == dispatcher){
   return queue.peek();
   }
   else return null;
   }
   
   public synchronized Parcel<?> retrieveParcel(Dispatcher dispatcher){
   if(connectedDispatcher == dispatcher){
   return queue.poll();
   }
   else return null;
   }
   
   public void drawBelt(Graphics g, int x, int y, int width, int height){
       int space = width/maxCapacity;
   g.setColor(Color.black);
        g.drawRect(x, y, width, height);
        //
        for(int i = 1; i < maxCapacity; i++){
        g.drawLine(x + i * space, y, x + i * space, y + height);
        }
        Parcel<?> parcels[] = new Parcel<?>[queue.size()];
        parcels = (queue.toArray(parcels));
        Arrays.sort(parcels);
        for (int i = 0; i < queue.size(); i++) {
           parcels[i].drawBox(g, x + i * space, y, space, height);
       }
        
        if(connectedMachine != null){
        connectedMachine.drawMachine(g, x + 1 - height, y, height);
        }
        if(connectedDispatcher != null){
        connectedDispatcher.drawDispatcher(g, x + width + 1, y, height);
        }
   }
}
