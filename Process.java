
public class Process {
	private int a;
	private int b;
	private int c;
	private int m;
	private int pID;
	
	private int cpuTimeLeft;
	private int currentTimeLeft;
	private String status;
	private boolean isTerminated;
	private boolean blockedFirstCycle;
	private int currentBurstTime;
	private int startTimeLeft;
	private boolean readyNow;
	
	private int waitingTime;
	private int finishingTime;
	private int blockedTime;
	public Process()
	{
		this.status = "Unstarted";
		this.isTerminated = false;
		this.currentTimeLeft = 0;
		this.blockedTime = 0;
		this.waitingTime = 0;
		this.finishingTime = 0;
	}
	public Process(int a, int b, int c, int m, int pID)
	{
		this.a = a;
		this.b = b;
		this.cpuTimeLeft = c;
		this.c = c;
		this.m = m;
		this.pID = pID;
		this.status = "Unstarted";
		this.isTerminated = false;
		this.currentTimeLeft = 0;
		this.startTimeLeft = a;
		this.waitingTime = 0;
		this.finishingTime = 0;
		this.blockedTime = 0;
	}
	
	public boolean isReadyNow() {
		return readyNow;
	}
	public void setReadyNow(boolean readyNow) {
		this.readyNow = readyNow;
	}
	public boolean isBlockedFirstCycle() {
		return blockedFirstCycle;
	}
	public void setBlockedFirstCycle(boolean blockedFirstCycle) {
		this.blockedFirstCycle = blockedFirstCycle;
	}
	public int getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	public int getBlockedTime() {
		return blockedTime;
	}
	public void setBlockedTime(int blockedTime) {
		this.blockedTime = blockedTime;
	}
	public int getStartTimeLeft() {
		return startTimeLeft;
	}
	public void setStartTimeLeft(int startTimeLeft) {
		this.startTimeLeft = startTimeLeft;
	}
	public int getCurrentBurstTime() {
		return currentBurstTime;
	}
	public void setCurrentBurstTime(int currentBurstTime) {
		this.currentBurstTime = currentBurstTime;
	}
	public int getCurrentTimeLeft() {
		return currentTimeLeft;
	}
	public void setCurrentTimeLeft(int currentTimeLeft) {
		this.currentTimeLeft = currentTimeLeft;
	}
	public int getFinishingTime() {
		return finishingTime;
	}
	public void setFinishingTime(int finishingTime) {
		this.finishingTime = finishingTime;
	}
	public int getCpuTimeLeft() {
		return cpuTimeLeft;
	}
	public void setCpuTimeLeft(int cpuTimeLeft) {
		this.cpuTimeLeft = cpuTimeLeft;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isTerminated() {
		return isTerminated;
	}
	public void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
		this.startTimeLeft = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
		this.cpuTimeLeft = c;
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
	}
	public int getpID() {
		return pID;
	}
	public void setpID(int pID) {
		this.pID = pID;
	}
}
