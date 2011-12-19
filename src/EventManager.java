import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

 
public class EventManager {
	
	private Server server1, server2;
	private Queue<Event> eventList;
	private Comparator<Event> comparator = new DoubleComparator();
	private DistGenerator arrivalGenerator;
	

	public EventManager(double meanInterArrivalTime, double meanServiceTime1, double meanServiceTime2) {
		eventList = new PriorityQueue<Event>(3,comparator);
		arrivalGenerator = new ExpDistGenerator(meanInterArrivalTime);
		server1 = new Server(ServerState.IDLE, meanServiceTime1);
		server2 = new Server(ServerState.IDLE, meanServiceTime2);
	}
	
	public Event next(){
		Event e = eventList.poll();
		Clock.time = e.getTime();
		return e;
	}
	
	public void arrive(Event e){
		
		//Create the next arrival event.
		createNextArrivalEvent();
		foo(server1);
	}
	
	public void depart(Event e){
		
		Server server = e.getServer();
		
		if(server.equals(server1)){
			foo(server2);
		}
		
		//Delete current Job from server and set server state to idle.
		server.deleteCurrentJob();
		if(server.isServerJobListEmpty()){
			server.setState(ServerState.IDLE);
		}else{
			server.setCurrentJob(server.getHead());
			//Create next departure event.
			createNextDepartureEvent(e.getServer());
		}
	}
	
	public void createNextArrivalEvent(){
		Event nextArvEvt = new Event(Type.ARRIVAL,Clock.time+arrivalGenerator.nextVal(), server1);
		eventList.offer(nextArvEvt);
	}
	
	public void createNextDepartureEvent(Server server){
		Event nextDepEvt = new Event(Type.DEPARTURE,Clock.time+server.getServiceGenerator().nextVal(), server);
		eventList.offer(nextDepEvt);
		//System.out.println(nextDepEvt.getTime());
	}

	public void foo(Server server){
		Job j = new Job(Clock.time);
		
		//Add current job to the server JobList.
		server.addJob(j);
		//Serve top job if server is idle.
		if(server.getState().equals(ServerState.IDLE)){
			server.setCurrentJob(server.getHead());
			server.setState(ServerState.BUSY);
			createNextDepartureEvent(server);
		}
	}
	
	public void printStats(){
		System.out.println("Mean queue length:");
		System.out.println("\tserver 1: " + server1.getStayInQueue()/Clock.time);
		System.out.println("\tserver 2: " + server2.getStayInQueue()/Clock.time);
		System.out.println();
		System.out.println("Mean queue time:");
		System.out.println("\tserver 1: " + server1.getStayInQueue()/server1.getNumOfJobs());
		System.out.println("\tserver 2: " + server2.getStayInQueue()/server2.getNumOfJobs());
		System.out.println();
		System.out.println("Mean turnaround time:");
		System.out.println("\tserver 1: " + server1.getStayInSystem()/server1.getNumOfJobs());
		System.out.println("\tserver 2: " + server2.getStayInSystem()/server2.getNumOfJobs());
		System.out.println();
		System.out.println("Server Utilization:");
		System.out.println("\tServer 1: " + (1-(server1.getIdle()/Clock.time)));
		System.out.println("\tServer 2: " + (1-(server2.getIdle()/Clock.time)));
		System.out.println();
		//System.out.println("Stay in System for all jobs: "+(server1.getStayInSystem()+server2.getStayInSystem()));
		//System.out.println();
		System.out.println("Number of Jobs:");
		System.out.println("\tServer 1: "+server1.getNumOfJobs());
		System.out.println("\tServer 2: "+server2.getNumOfJobs());
	}
	
	
}
