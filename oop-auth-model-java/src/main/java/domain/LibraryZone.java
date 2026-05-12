package domain;

import services.BehaviorRuleObserver;
import services.LightAlertObserver;
import services.NoiseAlertObserver;

public class LibraryZone {

    private String id;
    private LightingSensor lightingSensor;
    private NoiseSensor noiseSensor;

    public LibraryZone(String id) {
        this.id = id;
        this.lightingSensor = new LightingSensor();
        this.noiseSensor = new NoiseSensor();
        setupObservers();
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

    private void setupObservers() {
        // Observadores para ruido
        noiseSensor.addObserver(new NoiseAlertObserver());
        noiseSensor.addObserver(new BehaviorRuleObserver());
        
        // Observadores para luz
        lightingSensor.addObserver(new LightAlertObserver());
        lightingSensor.addObserver(new BehaviorRuleObserver());
    }
}