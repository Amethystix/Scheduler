
public class RoundRobinProcess extends Process{

	private final int QUANTUM  = 2;
	private boolean addedNow;
	private int timeRunning;
	public RoundRobinProcess()
	{
		super();
	}
	public int getTimeRunning() {
		return timeRunning;
	}
	public void setTimeRunning(int timeRunning) {
		this.timeRunning = timeRunning;
	}
	public int getQUANTUM() {
		return QUANTUM;
	}
	public boolean isAddedNow(){
		return addedNow;
	}
	public void setAddedNow(boolean addedNow){
		this.addedNow = addedNow;
	}
	
}
