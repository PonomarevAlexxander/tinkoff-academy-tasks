package edu.hw6.task6;

public record Port(Protocol protocol, int port, boolean occupied, String service) {
    public enum Protocol {
        UDP("UDP"),
        TCP("TCP");
        private final String name;

        Protocol(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
