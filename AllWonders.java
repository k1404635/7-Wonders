import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class AllWonders
{
	ArrayList<String> names;
	ArrayList<String> res;
	TreeMap<Integer, String[]> requirements1;
	TreeMap<Integer, String[]> requirements2;
	TreeMap<Integer, String[]> requirements3;
	TreeMap<Integer, String[]> effects1;
	TreeMap<Integer, String[]> effects2;
	TreeMap<Integer, String[]> effects3;
	//Name|Resource|Stage 1 req|Stage 1 eff|Stage 2 req|Stage 2 eff|Stage 3 req|Stage 3 eff|
	public AllWonders() throws IOException
	{
		Scanner scan = new Scanner(new File("WonderBoards.txt"));
		names = new ArrayList<String>();
		res = new ArrayList<String>();
		requirements1 = new TreeMap<Integer, String[]>();
		requirements2 = new TreeMap<Integer, String[]>();
		requirements3 = new TreeMap<Integer, String[]>();
		effects1 = new TreeMap<Integer, String[]>();
		effects2 = new TreeMap<Integer, String[]>();
		effects3 = new TreeMap<Integer, String[]>();
		String[] array = new String[8];
		String line = scan.nextLine();
		array = line.split(",");
		for(int x = 0; x < 6; x++)
		{
			names.add(array[0]);
			res.add(array[1]);
			requirements1.put(x, array[2].split(" "));
			effects1.put(x, array[3].split(" or "));
			requirements2.put(x, array[4].split(" "));
			effects2.put(x, array[5].split(" or "));
			requirements3.put(x, array[6].split(" "));
			effects3.put(x, array[7].split(" or "));
			if(x != 6)
			{
				line = scan.nextLine();
				array = line.split(",");
			}
		}
	}
	public String getName(int p)
	{
		return names.get(p);
	}
	public String getRes(int p)
	{
		return res.get(p);
	}
	public String[] getRequirements1(int p)
	{
		return requirements1.get(p);
	}
	public String[] getRequirements2(int p)
	{
		return requirements2.get(p);
	}
	public String[] getRequirements3(int p)
	{
		return requirements3.get(p);
	}
	public String[] getEffects1(int p)
	{
		return effects1.get(p);
	}
	public String[] getEffects2(int p)
	{
		return effects2.get(p);
	}
	public String[] getEffects3(int p)
	{
		return effects3.get(p);
	}
}