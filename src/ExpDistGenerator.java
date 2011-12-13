import java.util.Random;


public class ExpDistGenerator implements DistGenerator{
	
	Random rand;
	double mean;
	
	public ExpDistGenerator(double mean){
		rand = new Random();
		this.mean = mean;
	}
	
	public double nextVal() {
		double value,u;
		u = rand.nextDouble();
		value = -mean * Math.log(u);
		return value;
	}
}
