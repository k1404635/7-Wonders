import java.util.ArrayList;

public class Cards implements Comparable<Cards> 
{
	private String color;
	private String name;
	private String[] effect;
	private String chain1;
	private String chain2;
	private String cost;
	private String r;
	private String[] req;
	private int age;
	private String[] totalCost;
	private String[] data;
	
	
	public Cards (String str)
	{
		data = str.split(",");
		age = Integer.parseInt(data[0]);
		color = data[1];
		effect = data[2].split(" ");
		name = data[3];
		cost = data[4];
		r = data[5];
		chain1 = data[6];
		chain2 = data[7];
		totalCost = cost.split(" ");
		req = r.split(" or ");
	}
	public int compareTo(Cards c)
	{
		String[] colors= {"brown","grey","blue","green","yellow","red","purple"};
		return loc(colors, this.getColor())-loc(colors, c.getColor());
	}
	public int loc(String[] a, String d)
	{
		for(int b=0; b<a.length; b++)
			if(a[b].equalsIgnoreCase(d))
				return b;
		return -1;
	}
	public String getColor()
	{
		return color;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String[] getFree()
	{
		return req;
	}
	
	public String[] getEffect()
	{
		return effect;
	}
	
	public String getChain1()
	{
		return chain1;
	}
	
	public String getChain2()
	{
		return chain2;
	}
	
	public String getCost()
	{
		return cost;
	}
	
	public String[] getTotalCost()
	{
		return totalCost;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public ArrayList<String> getFutureBuilds()
	{
		ArrayList<String>temp = new ArrayList<String>();
		temp.add(chain1);
		temp.add(chain2);
		return temp;
	}
	public String effective()
	{
		String r="";
		for(int g=0; g<effect.length; g++)
			r+=effect[g];
		return r;
	}
	public String toString()
	{
		return age + "|" + color + "|" +  effective() + "|" + name + "|" + cost + "|" + chain1 + "|" + chain2 + "|\n";
	}
}