package domain;

public class LibraryZone {

    private String id;
    private LightingSensor lightingSensor;
    private NoiseSensor noiseSensor;

    public LibraryZone(String id) {
        this.id = id;
        this.lightingSensor = new LightingSensor();
        this.noiseSensor = new NoiseSensor();
    }

    public String getId() {
        return id;
    }

    public void updatePresence(boolean presence) {
        lightingSensor.detectPresence(presence);
        lightingSensor.automatic();
    }

    public void updateNoise(double level) {
        noiseSensor.detectNoise(level);
        noiseSensor.automatic();
    }

    // 🔥 control manual (pro)
    public void turnLightsOn() {
        lightingSensor.turnOn();
    }

    public void turnLightsOff() {
        lightingSensor.turnOff();
    }
}