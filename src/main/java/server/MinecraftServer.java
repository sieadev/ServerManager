package server;

public class MinecraftServer {
    private final MinecraftServerType type;
    private final String name;
    private String ip;
    private String port;
    private Status status;

    public MinecraftServer(MinecraftServerType type, String name){
        this.type = type;
        this.name = name;
        this.status = Status.UNAVAILABLE;
    }

    public MinecraftServer(MinecraftServerType type, String ip, String name, String port){
        this.type = type;
        this.ip = ip;
        this.name = name;
        this.port = port;
        this.status = Status.UNAVAILABLE;
    }

    public MinecraftServerType getType() {
        return type;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status s){
        status = s;
    }

    public String getName() {
        return name;
    }
}
