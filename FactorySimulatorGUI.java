/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory.simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Sam
 */
public class FactorySimulatorGUI extends JPanel implements ActionListener, ChangeListener{

    /**
     * @param args the command line arguments
     */
    
    private JButton addMachineButton;
    private JButton addDispatcherButton;
    private JButton removeMachineButton;
    private JButton removeDispatcherButton;
    private JSlider maxConsumptionSlider;
    private JSlider maxProductionSlider;
    private Timer timer;
    private Random random;
    DrawPanel drawPanel;
    Machine machine;
    Dispatcher dispatcher;
    ArrayList<Machine> machines;
    ArrayList<Dispatcher> dispatchers;
    static ConveyorBelt[] conveyorBelts;
    JLabel numLabel;
    
    public FactorySimulatorGUI() {
        super(new BorderLayout());
        
        addDispatcherButton = new JButton("Add Dispatcher");
        removeDispatcherButton = new JButton("Remove Dispatcher");
        removeMachineButton = new JButton("Remove Machine");
        addMachineButton = new JButton("Add Machine");
        maxConsumptionSlider = new JSlider(10, 3000);
        maxProductionSlider = new JSlider(10, 3000);
        
        JLabel conSliderLabel = new JLabel("Max Consumption Time");
        JLabel prodSliderLabel = new JLabel("Max Prodcution Time");
        
        numLabel = new JLabel("0 Dispatchers, 0 Machines");
        
        
        addDispatcherButton.addActionListener((ActionListener) this);
        removeDispatcherButton.addActionListener((ActionListener) this);
        removeMachineButton.addActionListener((ActionListener) this);
        addMachineButton.addActionListener((ActionListener) this);
        maxConsumptionSlider.addChangeListener((ChangeListener) this);
        maxProductionSlider.addChangeListener((ChangeListener) this);
        
        JPanel southPanel = new JPanel();
        JPanel northPanel = new JPanel();
        
        JPanel eastPanel = new JPanel();
        JPanel slide1Panel = new JPanel();
        
        JPanel westPanel = new JPanel();
        JPanel slide2Panel = new JPanel();
       
        eastPanel.add(addDispatcherButton);
        eastPanel.add(removeDispatcherButton);
        slide1Panel.add(maxConsumptionSlider);
        westPanel.add(addMachineButton);
        westPanel.add(removeMachineButton);
        slide2Panel.add(maxProductionSlider);
        
        slide1Panel.add(conSliderLabel);
        slide2Panel.add(prodSliderLabel);
        
        southPanel.add(eastPanel);
        southPanel.add(slide1Panel);
        southPanel.add(westPanel);
        southPanel.add(slide2Panel);
        
        northPanel.add(numLabel);
        
        add(southPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);
        machines = new ArrayList<Machine>();
        dispatchers = new ArrayList<Dispatcher>();
        timer = new Timer(5, this);

        timer.start();

    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addMachineButton) {
            machine = new Machine(conveyorBelts);

            machines.add(machine);

        }
        if (source == addDispatcherButton) {
            dispatcher = new Dispatcher(conveyorBelts);

            dispatchers.add(dispatcher);
        }
        if (source == removeMachineButton) {
            int i = machines.size()-1;
            //machines.get(i).
            machine = machines.get(i);
            machine.kill();
            for (int j = 0; j < conveyorBelts.length; j++) {
                conveyorBelts[j].disconnectMachine(machine);
            }
            
            
            machines.remove(i);
            

        }
        if (source == removeDispatcherButton) {
            int i = dispatchers.size()-1;

            dispatcher = dispatchers.get(i);
            dispatcher.kill();
            for (int j = 0; j < conveyorBelts.length; j++) {
                conveyorBelts[j].disconnectDispatcher(dispatcher);
            }
            
            dispatchers.remove(i);
        }
        if (source == timer) {

            drawPanel.repaint();

        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if(source == maxConsumptionSlider){
            machine.MAX_CONSUMPTION_TIME = source.getValue();
        }
        if(source == maxProductionSlider){
            machine.MAX_PRODUCTION_TIME = source.getValue();
        }

    
    }
    
        private class DrawPanel extends JPanel {

        public DrawPanel() {
            super();
            setPreferredSize(new Dimension(1000, 1000));
            setBackground(Color.white);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            numLabel.setText(dispatchers.size() + " Dispatchers, " + machines.size() + " Machines");

            for (int i = 0; i < conveyorBelts.length; i++) {
                conveyorBelts[i].drawBelt(g, 150, 100 + i * 100, 1000, 100);
                
            }
//            for (int i = 0; i < machines.size(); i++) {
//                machines.get(i).
//                
//            }
            

        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        conveyorBelts = new ConveyorBelt[5];
        for (int i = 0; i <5; i++) {
            conveyorBelts[i] = new ConveyorBelt(8);
        }
        
        JFrame frame = new JFrame("Bouncing Balls");
        // kill all threads when frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new FactorySimulatorGUI());

        frame.pack();
        // position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width - frameDimension.width) / 2,
                (screenDimension.height - frameDimension.height) / 2);
        frame.setVisible(true);
    }
    
}
