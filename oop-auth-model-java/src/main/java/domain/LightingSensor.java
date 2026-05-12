package domain;

import java.util.ArrayList;
import java.util.List;

import services.SensorObserver;

public class LightingSensor implements Sensor {

    private boolean isOn;
    private Mode mode = Mode.AUTO;
    private boolean presenceDetected;

    private List<SensorObserver> observers = new ArrayList<>();

    public void addObserver(SensorObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SensorObserver observer) {
        observers.remove(observer);
    }

    private void notifyStateChanged(boolean isOn) {
        for (SensorObserver obs : observers) {
            obs.onSensorStateChanged("LIGHTING", isOn);  
        }
    }
    // ───────────────────────────────────────────────────────────

    public void detectPresence(boolean presence) {
        this.presenceDetected = presence;
    }

    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("💡 Luz encendida");
        notifyStateChanged(true);   
    }

    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("💡 Luz apagada");
        notifyStateChanged(false); 
    }

    @Override
    public void automatic() {
        if (mode == Mode.AUTO) {
            if (presenceDetected) {
                turnOn();
            } else {
                turnOff();
            }
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}