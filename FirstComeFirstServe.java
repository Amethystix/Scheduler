import java.util.ArrayList;

public class FirstComeFirstServe{
	private ArrayList<Process> p;
	
	public FirstComeFirstServe(ArrayList<Process> p)
	{
		this.p = new ArrayList<Process>();
		for(int i = 0; i < p.size(); i++)
		{
			this.p.add(p.get(i));
		}
	}
	public void doTheThing(boolean verb)
	{
		boolean checkAgain = true;
		int cycles = 0;
		int ioTime = 0;
		int cycleisBlocking = 0;
		boolean runningFirstCycle = false;
		boolean somethingsRunning = false;
		boolean allTerm = false;
		boolean secondCheck = false;
		Process runningProcess = new Process();
		ArrayList<Process> blockedProcess = new ArrayList<Process>();
		ArrayList<Process> ready = new ArrayList<Process>();
		
		printSort();
		if(verb){
			System.out.print("Before Cycle:\t0:\t");
			printProcesses();
		}
		
		while(!allTerm)
		{
			while(checkAgain)
			{
				for(int k = 0; k < p.size(); k++)
				{
					if(p.get(k).getStatus().equals("Unstarted"))
					{
						if(p.get(k).getStartTimeLeft() > 0 && !secondCheck)
						{
							if(cycles != 0)
								p.get(k).setStartTimeLeft(p.get(k).getStartTimeLeft() - 1);
						}
						if(p.get(k).getStartTimeLeft() == 0){
							p.get(k).setStatus("Ready");
							p.get(k).setReadyNow(true);
							ready.add(p.get(k));
						}
					}
				}
				if(ready.isEmpty())
				{
					checkAgain = false;
				}
				else{
					for(int i = 0; i < ready.size(); i++)
					{
						for(int i2 = i; i2 < ready.size(); i2++)
						{
							if(ready.get(i).isReadyNow() && ready.get(i2).isReadyNow())
							{
								if(ready.get(i).getA() == ready.get(i2).getA()){
									if(ready.get(i).getpID() > ready.get(i2).getpID())
									{
										Process temp = ready.get(i);
										ready.set(i, ready.get(i2));
										ready.set(i2, temp);
									}
								}
								else if (ready.get(i).getA() > ready.get(i2).getA())
								{
									Process temp = ready.get(i);
									ready.set(i, ready.get(i2));
									ready.set(i2, temp);
								}
							}
						}
					}
					if(somethingsRunning == false)
					{
						somethingsRunning = true;
						//Here ioTime is used to temporarily store the burst time.
						ioTime = LabTwoMain.randomOS(ready.get(0).getB());
								
						ready.get(0).setCurrentBurstTime(ioTime);
						
						ready.get(0).setCurrentTimeLeft(ready.get(0).getCurrentBurstTime());
						ready.get(0).setStatus("Running");
						runningFirstCycle = true;
						runningProcess = ready.get(0);
						ready.remove(0);
					}
				}
				if(runningProcess.getStatus().equals("Running"))
				{
					if(runningFirstCycle)
					{
						runningFirstCycle = false;
						checkAgain = false;
					}
					else{
						if(runningProcess.getCurrentTimeLeft() > 0 && !secondCheck){
							runningProcess.setCurrentTimeLeft(runningProcess.getCurrentTimeLeft() - 1);
							runningProcess.setCpuTimeLeft(runningProcess.getCpuTimeLeft() - 1);
							checkAgain = false;
						}
						if(secondCheck)
						{
							checkAgain = false;
						}
						if(runningProcess.getCurrentTimeLeft() == 0)
						{
							if(runningProcess.getCpuTimeLeft() > 0)
							{
								runningProcess.setStatus("Blocked");
								runningProcess.setCurrentTimeLeft(ioTime * runningProcess.getM());
								runningProcess.setBlockedFirstCycle(true);
								somethingsRunning = false;
								checkAgain = true;
								blockedProcess.add(runningProcess);
							
							}
							else{
								runningProcess.setStatus("Terminated");
								runningProcess.setTerminated(true);
								runningProcess.setFinishingTime(cycles);
								somethingsRunning = false;
								checkAgain = true;
							}
						}
						if(runningProcess.getCpuTimeLeft() == 0)
						{
							runningProcess.setStatus("Terminated");
							runningProcess.setTerminated(true);
							runningProcess.setCurrentTimeLeft(0);
							runningProcess.setFinishingTime(cycles);
							somethingsRunning = false;
							checkAgain = true;
						}
						
					}
				}
				if(!blockedProcess.isEmpty())
				{
					for(int i = 0; i < blockedProcess.size(); i++)
					{
						if(blockedProcess.get(i).isBlockedFirstCycle())
						{
							blockedProcess.get(i).setBlockedFirstCycle(false);
							checkAgain = true;
						}
						else if(!secondCheck){	
							if(blockedProcess.get(i).getCurrentTimeLeft() > 0)
							{
								blockedProcess.get(i).setCurrentTimeLeft(blockedProcess.get(i).getCurrentTimeLeft() - 1);
								blockedProcess.get(i).setBlockedTime(blockedProcess.get(i).getBlockedTime() + 1);
							}
							if(blockedProcess.get(i).getCurrentTimeLeft() == 0)
							{
								blockedProcess.get(i).setStatus("Ready");
								blockedProcess.get(i).setReadyNow(true);
								ready.add(blockedProcess.get(i));
								blockedProcess.remove(i);
								checkAgain = true;
								i--;
							}
						}
						if(blockedProcess.isEmpty() || blockedProcess.size() < i-1)
							break;
					}
				}
				secondCheck = true;
			}
			for(int k = 0; k < p.size(); k++)
			{
				if(p.get(k).getStatus().equals("Ready"))
				{
					p.get(k).setWaitingTime(p.get(k).getWaitingTime() + 1);
					p.get(k).setReadyNow(false);
				}
			}
			allTerm = true;
			for(int iterate = 0; iterate < p.size(); iterate++)
			{
				if(!(p.get(iterate).getStatus().equals("Terminated")))
				{
					allTerm = false;
				}
			}
			if(!allTerm){
				if(!blockedProcess.isEmpty())
					cycleisBlocking++;
				cycles++;
				if(verb){
					System.out.print("Before Cycle:\t" + cycles + ":\t");
					printProcesses();
				}
				checkAgain = true;
				secondCheck = false;
			}
		}
		System.out.println("The scheduling algorithm used was First Come First Serve");
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
