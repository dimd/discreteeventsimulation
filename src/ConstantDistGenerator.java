
public class ConstantDistGenerator implements DistGenerator{
	
	private double mean;
	
	public ConstantDistGenerator(double mean){
		this.mean = mean;
	}
	
	@Override
	public double nextVal() {
		// TODO Auto-generated method stub
		return mean;
	}

}
