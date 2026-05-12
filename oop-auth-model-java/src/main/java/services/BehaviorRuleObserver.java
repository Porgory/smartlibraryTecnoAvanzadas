package services;

/**
 *  aplicar reglas de comportamiento 
 */
public class BehaviorRuleObserver implements SensorObserver {

    private AuthorizationService authService;
    // No se usa directamente, pero podría ser útil para reglas basadas en usuarios

    @Override
    public void onSensorStateChanged(String sensorType, boolean isOn) {
        System.out.println("📋 Aplicando regla de comportamiento para: " + sensorType);
    }

    @Override
    public void onSensorValueChanged(String sensorType, double value) {
        if ("NOISE".equals(sensorType) && value > 60) {
            System.out.println("⚠️ REGLA: Ruido muy alto! Valor: " + value);
        }
    }
}