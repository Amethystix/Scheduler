
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Lauren DiGiovanni
 * Main Class for Lab 2
 *
 */
public class LabTwoMain {
	
	public static File randomNums;
	public static Scanner rs;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		randomNums = new File("random-numbers.txt");
		rs = new Scanner(randomNums);
		ArrayList<Process> p;
		boolean verbose = false;
		Scanner sc = null;
		File f = null;
		
		if(args.length > 1)
		{
			if(args[0].equals("--verbose"))
			{
				verbose = true;
				f = new File(args[1]);
				sc = new Scanner(f);
			}
			else
			{
				System.out.println("Invalid Arguments.  Aborting.");
			}
		}
		else
		{
			f = new File(args[0]);
			 sc = new Scanner(f);
		}
		int numProcesses = sc.nextInt();
		p = new ArrayList<Process>();
		ArrayList<RoundRobinProcess> pr = new ArrayList<RoundRobinProcess>();
		inputData(sc, numProcesses, p);
		sc.close();
		pr = castToRR(p);
		
		FirstComeFirstServe fcfs = new FirstComeFirstServe(p);
		fcfs.doTheThing(verbose);
		System.out.println();
		
		rs.close();
		rs = new Scanner(randomNums);
		
		RoundRobin rr = new RoundRobin(pr);
		rr.doTheThing(verbose);
		System.out.println();
		
		rs.close();
		rs = new Scanner(randomNums); 
		
		redoArray(p);
		
		Uniprocessing u = new Uniprocessing(p);
		u.doTheThing(verbose);
		System.out.println();
		
		redoArray(p);
		
		rs.close();
		rs = new Scanner(randomNums);
		
		ShortestFinishTime sh = new ShortestFinishTime(p);
		sh.doTheThing(verbose);
		rs.close();
	}
	public static void inputData(Scanner sc, int np, ArrayList<Process> p)
	{
		for(int i = 0; i < np; i++)
		{
			p.add(new Process());
			p.get(i).setpID(i);
			for(int j = 0; j < 4; j++)
			{
				if(j == 0)
				{
					String firstnum = sc.next();
					//make sure this catches the entire number and doesn't cut off
					String num = firstnum.substring(1, firstnum.length());
					
					int a = Integer.parseInt(num);
					p.get(i).setA(a);
				}
				else if(j == 1)
				{
					int b = sc.nextInt();
					p.get(i).setB(b);
				}
				else if(j == 2)
				{
					int c = sc.nextInt();
					p.get(i).setC(c);
				}
				else
				{
					String lastnum = sc.next();
					String num = lastnum.substring(0, lastnum.length()-1);
					
					int m = Integer.parseInt(num);
					p.get(i).setM(m);
				}
			}
		}
	}
	public static int randomOS(int b)
	{
		int rand = rs.nextInt();
		return 1 + (rand%b);
	}
	public static ArrayList<RoundRobinProcess> castToRR(ArrayList<Process> p)
	{
		ArrayList<RoundRobinProcess> pr = new ArrayList<RoundRobinProcess>();
		for(int i = 0; i < p.size(); i++)
		{
			RoundRobinProcess r = new RoundRobinProcess();
			r.setA(p.get(i).getA());
			r.setB(p.get(i).getB());
			r.setC(p.get(i).getC());
			r.setM(p.get(i).getM());
			r.setpID(p.get(i).getpID());
			pr.add(r);
		}
		return pr;
	}
	public static void redoArray(ArrayList<Process> p)
	{
		
		for(int i = 0; i < p.size(); i++)
		{
			Process temp = new Process(p.get(i).getA(), p.get(i).getB(), p.get(i).getC(), p.get(i).getM(), p.get(i).getpID() );
			p.set(i, temp);
		}
	}
}
