package server;

public class DiscordBot {

    public DiscordBot(String name){
        this.name = name;
        this.status = Status.UNAVAILABLE;
    }
    private String name;

    private String token;
    private Status status;

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
