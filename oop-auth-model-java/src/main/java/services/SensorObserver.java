package services;

public interface SensorObserver {
    void onSensorStateChanged(String sensorType, boolean isOn);
    void onSensorValueChanged(String sensorType, double value);
}
