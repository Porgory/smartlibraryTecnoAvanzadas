package domain;

import java.util.ArrayList;
import java.util.List;

import services.SensorObserver;
// modificacion
public class NoiseSensor implements Sensor {

    private boolean isOn;
    private Mode mode = Mode.AUTO;
    private double noiseLevel;
    private double maxNoise = 50.0;

    private List<SensorObserver> observers = new ArrayList<>();
    
    public void addObserver(SensorObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(SensorObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyStateChanged(boolean isOn) {
        for (SensorObserver obs : observers) {
            obs.onSensorStateChanged("NOISE", isOn);
        }
    }
    
    private void notifyValueChanged(double value) {
        for (SensorObserver obs : observers) {
            obs.onSensorValueChanged("NOISE", value);
        }
    }

    public void detectNoise(double level) {
        this.noiseLevel = level;
        notifyValueChanged(level);
    }

    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("🔊 Alerta: ruido alto");
        notifyStateChanged(true);
    }

    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("🔊 Ruido normal");
        notifyStateChanged(false);
    }

    @Override
    public void automatic() {
        if (mode == Mode.AUTO) {
            if (noiseLevel > maxNoise) {
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