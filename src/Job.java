
public class Job {
	
	private double arrivedAt, servedAt, DepartedAt;

	public Job(double arrivedAt) {
		this.arrivedAt = arrivedAt;
	}

	public double getServedAt() {
		return servedAt;
	}

	public void setServedAt(double servedAt) {
		this.servedAt = servedAt;
	}

	public double getDepartedAt() {
		return DepartedAt;
	}

	public void setDepartedAt(double departedAt) {
		DepartedAt = departedAt;
	}

	public double getArrivedAt() {
		return arrivedAt;
	}
	
}
