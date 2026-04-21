package services;

import com.fazecast.jSerialComm.SerialPort;

import java.io.OutputStream;

public class ArduinoService {

    private SerialPort port;

    public ArduinoService(String portName) {

        port = SerialPort.getCommPort(portName);
        port.setBaudRate(9600);

        if (!port.openPort()) {
            System.out.println("❌ No se pudo conectar a Arduino");
        } else {
            System.out.println("✅ Arduino conectado");
        }
    }

    public void sendCommand(String command) {
        try {
            OutputStream out = port.getOutputStream();
            out.write((command + "\n").getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}