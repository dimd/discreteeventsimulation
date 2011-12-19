
import java.util.LinkedList;
import java.util.Queue;

public class Server {

	private ServerState state;
	private Queue<Job> jobList;
	private Job currentJob;
	private int numOfJobsInQueue=0,numOfJobs=0;
	private double stayInQueue=0 ,stayInSystem=0, idle=0, idleSince;
	private DistGenerator serviceGenerator;

	public Server(ServerState state, double meanServiceTime) {
		jobList = new LinkedList<Job>();
		this.state = state;
		this.serviceGenerator = new ExpDistGenerator(meanServiceTime);
	}
	
	public void addJob(Job j){
		jobList.offer(j);
		numOfJobs++;
	}
	
	public Job getHead(){
		return jobList.poll();
	}
	
	public ServerState getState() {
		return state;
	}

	public void setState(ServerState state) {
		this.state = state;
		if(state.equals(ServerState.IDLE)){
			idleSince = Clock.time;
		}
	}

	public void setCurrentJob(Job currentJob) {
		this.currentJob = currentJob;
		this.currentJob.setServedAt(Clock.time);
		if(idleSince!=0){
			idle += Clock.time - idleSince;
			idleSince = 0;
		}
	}

	public void deleteCurrentJob(){
		currentJob.setDepartedAt(Clock.time);
		stayInQueue += currentJob.getServedAt() - currentJob.getArrivedAt();
		stayInSystem += currentJob.getDepartedAt() - currentJob.getArrivedAt();
		currentJob = null;
	}
	
	public boolean isServerJobListEmpty(){
		if(jobList.isEmpty()){
			return true;
		}
		return false;
	}

	public int getNumOfJobsInQueue() {
		return numOfJobsInQueue;
	}

	public int getNumOfJobs() {
		return numOfJobs;
	}

	public double getStayInQueue() {
		return stayInQueue;
	}

	public double getStayInSystem() {
		return stayInSystem;
	}

	public double getIdle() {
		return idle;
	}

	public DistGenerator getServiceGenerator() {
		return serviceGenerator;
	}
	
}
