
public class ErlangDistGenerator implements DistGenerator{
	
	private int k;
	private ExpDistGenerator exp;
	
	public ErlangDistGenerator(double mean, int k) {
		this.k = k;
		this.exp = new ExpDistGenerator(mean/k);
	}


	@Override
	public double nextVal() {
		double accumulator=0;
		
		for(int i=0; i<this.k; i++){
			accumulator += this.exp.nextVal();
		}
		return accumulator;
	}
	
}
