package services;

/**
 *  se dispara cuando hay alerta de ruido
 */
public class NoiseAlertObserver implements SensorObserver {

    @Override
    public void onSensorStateChanged(String sensorType, boolean isOn) {
        if ("NOISE".equals(sensorType) && isOn) {
            System.out.println("🚨 ALERTA: Ruido detectado en la zona!");
            
        }
    }

    @Override
    public void onSensorValueChanged(String sensorType, double value) {
        if ("NOISE".equals(sensorType)) {
            System.out.println("📊 Nivel de ruido: " + value + "dB");
            
        }
    }
}