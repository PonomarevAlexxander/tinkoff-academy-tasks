package edu.hw6.task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PortsScanner {
    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 49151;
    private static final Map<Integer, String> PORT_SERVICE = Map.ofEntries(
        Map.entry(80, "HTTP"),
        Map.entry(21, "FTP"),
        Map.entry(25, "SMTP"),
        Map.entry(22, "SSH"),
        Map.entry(443, "HTTPS"),
        Map.entry(3306, "MySQL Database"),
        Map.entry(5432, "PostgreSQL Database"),
        Map.entry(49152, "Windows RPC"),
        Map.entry(5353, "mDNS"),
        Map.entry(5672, "AMQP"),
        Map.entry(5355, "LLMNR"),
        Map.entry(49153, "Windows RPC")
    );

    private PortsScanner() {

    }

    public static List<Port> scanAll() {
        List<Port> allPorts = new LinkedList<>();
        for (int port = MIN_PORT; port <= MAX_PORT; port++) {
            allPorts.add(checkPort(port));
        }
        return allPorts;
    }

    public static List<Port> occupiedPorts() {
        return scanAll().stream()
            .filter(Port::occupied)
            .toList();
    }

    private static Port checkPort(int port) {
        String service = PORT_SERVICE.getOrDefault(port, "");
        try (ServerSocket tcp = new ServerSocket(port)) {
        } catch (IOException e) {
            return new Port(Port.Protocol.TCP, port, true, service);
        }
        try (DatagramSocket udp = new DatagramSocket(port)) {
        } catch (IOException e) {
            return new Port(Port.Protocol.UDP, port, true, service);
        }
        return new Port(null, port, false, service);
    }
}
