package edu.hw6.task6;

public class Main {
    private Main() {

    }

    @SuppressWarnings("RegexpSinglelineJava")
    public static void main(String[] args) {
        String columnNameFormat = "%-10s  %-20s  %-20s\n";
        System.out.printf(columnNameFormat, "Protocol", "Port", "Service");
        String portFormat = "%-10s  %-20d  %-20s\n";
        for (Port port : PortsScanner.occupiedPorts()) {
            System.out.printf(portFormat, port.protocol(), port.port(), port.service());
        }
    }
}
