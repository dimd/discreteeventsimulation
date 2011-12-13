import java.util.Comparator;


public class DoubleComparator implements Comparator<Event> {

	@Override
	public int compare(Event o1, Event o2) {
		if (o1.getTime()<o2.getTime()){
			return -1;
		}else if(o1.getTime()>o2.getTime()){
			return 1;
		}
		return 0;
	}
	
}
