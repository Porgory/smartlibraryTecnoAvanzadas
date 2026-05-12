package services;

/**
 * se dispara cuando cambia la luz
 */
public class LightAlertObserver implements SensorObserver {

    @Override
    public void onSensorStateChanged(String sensorType, boolean isOn) {
        if ("LIGHT".equals(sensorType)) {
            if (isOn) {
                System.out.println("💡 Luz ENCENDIDA automáticamente");
            } else {
                System.out.println("💡 Luz APAGADA automáticamente");
            }
        }
    }

    @Override
    public void onSensorValueChanged(String sensorType, double value) {
        
    }
}
