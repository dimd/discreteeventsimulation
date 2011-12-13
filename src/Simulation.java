
public class Simulation {

	private EventManager evtManager = new EventManager(1.0,0.7);
	
	public void initialize(){
		evtManager.createNextArrivalEvent();
	}
	
	public void run(){
		Event e;
		while(Clock.time<10000){
			e = evtManager.next();
			if(e.getType().equals(Type.ARRIVAL)){
				evtManager.arrive(e);	
			}else{ 
				evtManager.depart(e);
			}
		}
	}
	
	public void printResults(){
		evtManager.printStats();
	}
	
}
