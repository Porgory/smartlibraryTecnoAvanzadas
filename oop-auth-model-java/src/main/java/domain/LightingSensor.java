package domain;

public class LightingSensor implements Sensor {

    private boolean isOn;
    private Mode mode = Mode.AUTO;
    private boolean presenceDetected;

    public void detectPresence(boolean presence) {
        this.presenceDetected = presence;
    }

    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("💡 Luz encendida");
    }

    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("💡 Luz apagada");
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