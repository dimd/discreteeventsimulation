import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

 
public class EventManager {
	
	private Server server;
	private Queue<Event> eventList;
	private Comparator<Event> comparator = new DoubleComparator();
	private DistGenerator arrivalGenerator, serviceGenerator;
	

	public EventManager(double meanInterArrivalTime, double meanServiceTime) {
		eventList = new PriorityQueue<Event>(2,comparator);
		arrivalGenerator = new ExpDistGenerator(meanInterArrivalTime);
		serviceGenerator = new ExpDistGenerator(meanServiceTime);
		server = new Server(ServerState.IDLE);
	}
	
	public Event next(){
		Event e = eventList.poll();
		Clock.time = e.getTime();
		return e;
	}
	
	public void arrive(Event e){
		
		Job j = new Job(e.getTime());
		
		//Create the next arrival event.
		createNextArrivalEvent();
		//Add current job to the server JobList.
		server.addJob(j);
		//Serve top job if server is idle.
		if (server.getState().equals(ServerState.IDLE)){
			server.setCurrentJob(server.getHead());
			server.setState(ServerState.BUSY);
			createNextDepartureEvent();
		}
	}
	
	public void depart(Event e){
		//Delete current Job from server and set server state to idle.
		server.deleteCurrentJob();
		if(server.isServerJobListEmpty()){
			server.setState(ServerState.IDLE);
		}else{
			server.setCurrentJob(server.getHead());
			//Create next departure event.
			createNextDepartureEvent();
		}
	}
	
	public void createNextArrivalEvent(){
		Event nextArvEvt = new Event(Type.ARRIVAL,Clock.time+arrivalGenerator.nextVal());
		eventList.offer(nextArvEvt);
	}
	
	public void createNextDepartureEvent(){
		Event nextDepEvt = new Event(Type.DEPARTURE,Clock.time+serviceGenerator.nextVal());
		eventList.offer(nextDepEvt);
		//System.out.println(nextDepEvt.getTime());
	}
	
	public void printStats(){
		System.out.println("Average number of jobs in queue: " + server.getStayInQueue()/Clock.time);
		System.out.println("Average stay in queue: " + server.getStayInQueue()/server.getNumOfJobs());
		System.out.println("Average stay in system: " + server.getStayInSystem()/server.getNumOfJobs());
		System.out.println("Server utilization: " + (1-(server.getIdle()/Clock.time)));
		System.out.println("Stay in System for all jobs: "+server.getStayInSystem());
		System.out.println("Number Of Jobs: "+server.getNumOfJobs());
	}
	
	
}
