package domain;
// GRASP: Protected Variations
// Esta interfaz protege el sistema de cambios en los sensores.
// Si se agrega un nuevo sensor, solo implementa esta interfaz sin romper nada.
public interface Sensor {
    void turnOn();
    void turnOff();
    void automatic();
}
