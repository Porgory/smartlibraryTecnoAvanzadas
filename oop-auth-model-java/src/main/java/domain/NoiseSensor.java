package domain;

public class NoiseSensor implements Sensor {

    private boolean isOn;
    private Mode mode = Mode.AUTO;
    private double noiseLevel;
    private double maxNoise = 50.0;

    public void detectNoise(double level) {
        this.noiseLevel = level;
    }

    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("🔊 Alerta: ruido alto");
    }

    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("🔊 Ruido normal");
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