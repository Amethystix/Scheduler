import java.util.ArrayList;

public class Uniprocessing {
	private ArrayList<Process> p;
	
	public Uniprocessing(ArrayList<Process> pr)
	{
		this.p = new ArrayList<Process>();
		for(int i = 0; i < pr.size(); i++)
		{
			this.p.add(pr.get(i));
		}
	}
	
	public void doTheThing(boolean verb)
	{
		int cycles = 0;
		int ioTime = 0;
		int cycleisBlocking = 0;
		boolean checkAgain = true;
		boolean runningFirstCycle = false;
		boolean blockingFirstCycle = false;
		boolean secondCheck = false;
		
		printSort();
		if(verb){
			System.out.print("Before Cycle:\t0:\t");
			printProcesses();
		}
	
		for(int i = 0; i < p.size(); i++)
		{
			Process temp = p.get(i);
			int cpuTemp = temp.getCpuTimeLeft();
			
			while(!(p.get(i).isTerminated()))
			{
				while(checkAgain){
					for(int k = 0; k < p.size(); k++)
					{
						if(p.get(k).getStatus().equals("Unstarted"))
						{
							if(p.get(k).getStartTimeLeft() > 0 && !secondCheck)
							{
								p.get(k).setStartTimeLeft(p.get(k).getStartTimeLeft() - 1);
								if(k == 0)
									checkAgain = false;
							}
							else if(p.get(k).getStartTimeLeft() == 0 && !secondCheck){
								p.get(k).setStatus("Ready");
							}
						}
					}
					if (temp.getStatus().equals("Ready")){
						temp.setCurrentTimeLeft(LabTwoMain.randomOS(temp.getB()));
						ioTime = temp.getCurrentTimeLeft();
						//fix this, wont work for more than one example
						if(temp.getCpuTimeLeft() < temp.getCurrentTimeLeft())
						{
							temp.setCurrentTimeLeft(temp.getCpuTimeLeft());
							ioTime = temp.getCurrentTimeLeft();
						}
						temp.setStatus("Running");
						runningFirstCycle = true;
						checkAgain = false;
					}
					if (temp.getStatus().equals("Running"))
					{
						if(!runningFirstCycle){
							temp.setCurrentTimeLeft(temp.getCurrentTimeLeft()-1);
							cpuTemp--;
							checkAgain = false;
						}
						else if (runningFirstCycle)
						{
							checkAgain = false;
							runningFirstCycle = false;
						}
						if(temp.getCurrentTimeLeft() == 0 && cpuTemp != 0)
						{
							temp.setCurrentTimeLeft(ioTime * temp.getM());
							temp.setStatus("Blocked");
							blockingFirstCycle = true;
						}
					}
					if(temp.getStatus().equals("Blocked"))
					{
						if(blockingFirstCycle)
						{
							blockingFirstCycle = false;
						}
						else if(!blockingFirstCycle)
						{
							checkAgain = false;
							temp.setCurrentTimeLeft(temp.getCurrentTimeLeft()-1);
							temp.setBlockedTime(temp.getBlockedTime() + 1);
						}
						if(temp.getCurrentTimeLeft() == 0)
						{
							temp.setStatus("Ready");
							checkAgain = true;
						}
					}			
					if(cpuTemp == 0)
					{
						temp.setTerminated(true);
						temp.setStatus("Terminated");
						temp.setFinishingTime(cycles);
						checkAgain = false;
					}
					secondCheck = true;
				}
				if(temp.isTerminated())
					checkAgain = true;
				else{
					for(int k = 0; k < p.size(); k++)
					{
						if(p.get(k).getStatus().equals("Ready"))
						{
							p.get(k).setWaitingTime(p.get(k).getWaitingTime() + 1);
						}
					}
					
					if(temp.getStatus().equals("Blocked"))
					{
						cycleisBlocking++;
					}
					cycles++;
					
					secondCheck = false;
					if(verb){
						System.out.print("Before Cycle:\t" + cycles + ":\t");
						printProcesses();
					}
					checkAgain = true;
				}
			}
		}
		System.out.println("The scheduling algorithm used was Uniprocessing");
		printResults(cycles, cycleisBlocking);
	}
	public void printProcesses()
	{
		for(int i = 0; i < p.size(); i++)
		{
			if(p.get(i).getStatus().equals("Running")|| p.get(i).getStatus().equals("Ready") || p.get(i).getStatus().equals("Blocked"))
			{
				System.out.print(p.get(i).getStatus() + "\t\t" + p.get(i).getCurrentTimeLeft() + "\t");
			}
			else{
				System.out.print(p.get(i).getStatus() + "\t" + p.get(i).getCurrentTimeLeft() + "\t");
			}
		}
		System.out.println();
	}
	public void printResults(int ft, int cib)
	{
		int finishingTime = ft;
		double avgTurnaround = 0;
		double avgWaiting = 0;
		int cpuTime = 0;
		for(int i = 0; i < p.size(); i++)
		{
			System.out.println();
			System.out.println("Process " + i + ":");
			System.out.println("\t(A, B, C, M) = \t(" + p.get(i).getA() + ", " + p.get(i).getB() + ", " + p.get(i).getC() + ", " + p.get(i).getM() + ")");
			System.out.println("\tFinishing Time: " + p.get(i).getFinishingTime());
			int turnAroundTime = p.get(i).getFinishingTime() - p.get(i).getA();
			System.out.println("\tTurnaround Time: " + turnAroundTime);
			System.out.println("\tI/O Time: " + p.get(i).getBlockedTime());
			System.out.println("\tWaiting Time: " + p.get(i).getWaitingTime());
			
			avgWaiting += p.get(i).getWaitingTime();
			avgTurnaround += turnAroundTime;
			cpuTime += p.get(i).getC();
		}
		avgWaiting = ((double)(avgWaiting)) / ((double)(p.size()));
		avgTurnaround = ((double)(avgTurnaround)) / ((double)(p.size()));
		double ioUtilization = ((double)(cib)) / ((double)(finishingTime));
		double cpuUtilization = ((double)(cpuTime)) / ((double)(finishingTime));
		double throughput = ((double)(p.size() * 100)) / ((double)(finishingTime));
		System.out.println();
		System.out.println("Summary Data:");
		System.out.println("\tFinishing time: " + finishingTime);
		System.out.printf("\tCPU Utilization: %.6f\n", cpuUtilization);
		System.out.printf("\tI/O Utilization: %.6f\n", ioUtilization);
		System.out.printf("\tThroughput: %.6f processes per hundred cycles\n", throughput);
		System.out.printf("\tAverage turnaround time: %.6f\n", avgTurnaround);
		System.out.printf("\tAverage waiting time: %.6f\n", avgWaiting);
	}
	public void printSort()
	{
		System.out.print("The original input was:\t");
		System.out.print(p.size());
		for(int i = 0; i < p.size(); i++)
		{
			System.out.print(" (" + p.get(i).getA() + " " + p.get(i).getB() + " " + p.get(i).getC() + " " + p.get(i).getM() + ") ");
		}
		System.out.println();
		System.out.print("The sorted input is:\t");
		System.out.print(p.size());
		for(int sort = 0; sort < p.size(); sort++)
		{
			for(int sort2 = sort; sort2 < p.size(); sort2++)
			{
				if (p.get(sort).getA() > p.get(sort2).getA())
				{
					Process temp = p.get(sort);
					p.set(sort, p.get(sort2));
					p.set(sort2, temp);
				}
				else if(p.get(sort).getpID() > p.get(sort2).getpID() && p.get(sort).getA() == p.get(sort2).getA())
				{
					Process temp = p.get(sort);
					p.set(sort, p.get(sort2));
					p.set(sort2, temp);
				}
			}
		}
		for(int i = 0; i < p.size(); i++)
		{
			System.out.print(" (" + p.get(i).getA() + " " + p.get(i).getB() + " " + p.get(i).getC() + " " + p.get(i).getM() + ") ");
		}
		System.out.println();
		System.out.println();
	}
	
}
