
public class Event {
	
	private Type type;
	private double time;
	private Server server;
	
	public Event(Type type, double time, Server server) {
		this.type = type;
		this.time = time;
		this.server = server;
	}

	public Type getType() {
		return type;
	}

	public double getTime() {
		return time;
	}

	public Server getServer() {
		return server;
	}

}
