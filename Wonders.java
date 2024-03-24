import java.io.IOException;
import java.util.TreeMap;
public class Wonders
{
	private int stage;
	private String res;
	private String name;
	TreeMap<Integer, String[]> requirements;
	TreeMap<Integer, String[]> effects;
	AllWonders all;
	
	public Wonders(int p) throws IOException
	{
		all = new AllWonders();
		res = all.getRes(p);
		name = all.getName(p);
		requirements = new TreeMap<Integer, String[]>();
		effects = new TreeMap<Integer, String[]>();
		stage = 0;
		requirements.put(1, all.getRequirements1(p));
		requirements.put(2, all.getRequirements2(p));
		requirements.put(3, all.getRequirements3(p));
		
		effects.put(1, all.getEffects1(p));
		effects.put(2, all.getEffects2(p));
		effects.put(3, all.getEffects3(p));
	}
	public String[] getReq(int p)
	{
		return requirements.get(p);
	}
	public String[] getEff(int p)
	{
		return effects.get(p);
	}
	public String getName()
	{
		return name;
	}
	public String getRes()
	{
		return res;
	}
	public int getStage()
	{
		return stage;
	}
	public void setStage(int s)
	{
		stage = s;
	}
}
/*public String[] getStage1Req()
{
	return requirements.get(1);
}
public String[] getStage2Req()
{
	return requirements.get(2);
}
public String[] getStage3Req()
{
	return requirements.get(3);
}
public String[] getStage1Eff()
{
	return effects.get(1);
}
public String[] getStage2Eff()
{
	return effects.get(2);
}
public String[] getStage3Eff()
{
	return effects.get(3);
}*/