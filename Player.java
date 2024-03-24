import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
public class Player 
{
	Wonders wonder;
	ArrayList<Cards> allPlayed;
	ArrayList<Cards> continues;
	ArrayList<String> res;
	ArrayList<Cards> freeCards;
	ArrayList<Cards> hand;
	int totalMPoints;
	int purple;
	int yellow;
	int totalNegPoints;
	int m5Pts, m3Pts,  m1Pts;
	int totalCoins;
	int vPoints;
	int numMCards;
	String sci;
	int costRightB, costLeftB;
	int costRightG, costLeftG;
	int wonderPts;
	boolean discard, played;
	
	public Player()
	{
		allPlayed = new ArrayList<Cards>();
		numMCards=0;
		sci="";
		costRightB=2;
		purple=0;
		yellow=0;
		costLeftB=2;
		costRightG=2;
		costLeftG=2;
		continues = new ArrayList<Cards>();
		res = new ArrayList<String>();
		freeCards = new ArrayList<Cards>();
		hand = new ArrayList<Cards>();
		totalCoins=3;
		played=false;
		discard=false;
	}
	public ArrayList<Cards> getResourceCards()
	{
		ArrayList<Cards> temp=new ArrayList<Cards>();
		for(int i=0; i<getAllPlayed().size(); i++)
		{
			if(getAllPlayed().get(i).getColor().equalsIgnoreCase("brown")||getAllPlayed().get(i).getColor().equalsIgnoreCase("grey"))
				temp.add(getAllPlayed().get(i));
		}
		Collections.sort(temp);
		return temp;
	}
	public int getSciPts()
	  {
		  int math=0;
		  int engineering=0;
		  int writing=0;
		  int total=0;
		  for(int i=0; i<getAllPlayed().size(); i++)
		  {
			  if(getAllPlayed().get(i).getColor().equalsIgnoreCase("green"))
			  {
				  if(getAllPlayed().get(i).getEffect()[0].equalsIgnoreCase("mathematics"))
					  math++;
				  else if(getAllPlayed().get(i).getEffect()[0].equalsIgnoreCase("engineering"))
					  engineering++;
				  else if(getAllPlayed().get(i).getEffect()[0].equalsIgnoreCase("writing"))
					  writing++;
			  }
		  }
		  if(sci.equalsIgnoreCase("mathematics"))
			  math++;
		  else if(sci.equalsIgnoreCase("engineering"))
			  engineering++;
		  else if(sci.equalsIgnoreCase("writing"))
			  writing++;
		if(math>0&&engineering>0&&writing>0)
			  total=7;
		  total+=(int)Math.pow(math, 2);
		  total+=(int)Math.pow(engineering, 2);
		  total+=(int)Math.pow(writing, 2);
		  return total;
	  }
	
	public Wonders getWonders()
	{
		return wonder;
	}
	
	public int getMPoints()
	{
		return totalMPoints;
	}
	public int getWonderPts()
	{
		return wonderPts;
	}
	//public int getSciencePts()
	//{
		
	//}
	public int getTotalNegPoints()
	{
		return totalNegPoints;
	}
	
	public int getM5Pts()
	{
		return m5Pts;
	}
	
	public int getM3Pts()
	{
		return m3Pts;
	}
	
	public int getM1Pts()
	{
		return m1Pts;
	}
	
	public int getTotalCoins()
	{
		return totalCoins;
	}
	
	public int getVPoints()
	{
		return vPoints;
	}
	
	public int getMCards()
	{
		//return numMCards;
		int numMCard= 0;
		for(Cards c: allPlayed)
		{
			if(c.getColor().equals("red"))
			{
				String[] num = c.getEffect();
				numMCard+=num.length;
			}
		}
		return numMCard+numMCards;
	}
	
	public int getRightB()
	{
		return costRightB;
	}
	
	public int getLeftB()
	{
		return costLeftB;
	}
	
	public int getRightG()
	{
		return costRightG;
	}
	
	public int getLeftG()
	{
		return costLeftG;
	}
	
	public boolean getDiscard()
	{
		return discard;
	}
	public int getBluePoints()
	{
		int total=0;
		for(int a=0; a<allPlayed.size(); a++)
		{
			if(allPlayed.get(a).getColor().equalsIgnoreCase("blue"))
				total+=allPlayed.get(a).getEffect()[0].length();
		}
		return total;
	}
	public boolean getPlayed()
	{
		return played;
	}
	public ArrayList<String> getCardNames()
	{
		ArrayList<String> cards=new ArrayList<String>();
		for(int i=0; i<allPlayed.size(); i++)
			cards.add(allPlayed.get(i).getName());
		return cards;
	}
	public void setPurple(int a)
	{
		purple+=a;
	}
	public int getPurple()
	{
		return purple;
	}
	public void setYellow(int a)
	{
		yellow+=a;
	}
	public int getYellow()
	{
		return yellow;
	}
	public ArrayList<Cards> getFree(Deck d)
	{
		ArrayList<Cards> freeCards = new ArrayList<Cards>();
		ArrayList<Cards> ageDeck;
		if(d.getAge()==1)
		{
			ageDeck = d.ageOne();
		}
		else if(d.getAge()==2)
		{
			ageDeck = d.ageTwo();
		}
		else
		{
			ageDeck = d.ageThree();
		}
		
		for(Cards c:allPlayed)
		{
			ArrayList<String> futureBuilds = c.getFutureBuilds();
			for(Cards card:ageDeck)
			{
				if(card.getChain1().equals(futureBuilds.get(0)))
					freeCards.add(card);
				else if(card.getChain2().equals(futureBuilds.get(1)))
					freeCards.add(card);
				
			}
		}
		return freeCards;
	}
	
	public ArrayList<Cards> getAllPlayed()
	{
		return allPlayed;
	}
	
	public ArrayList<Cards> getContinues()
	{
		return continues;
	}
	
	public ArrayList<String> getResources()
	{
		return res;
	}
	
	public void addCoins(int a)
	{
		totalCoins = totalCoins+a;
	}
	
	public void addVPoints(int a)
	{
		vPoints+=a;
	}
	
	public void removeCoins(int r)
	{
		totalCoins = totalCoins - r;
	}
	public void removeResources(int r)
	{
		res.remove(r);
	}
	public void removeResource(String r)
	{
		res.remove(r);
	}
	public void addResource(String r)
	{
		res.add(r);
	}
	public void addToAllPlayed(Cards c) 
	{
		allPlayed.add(c);
	}
	
	public void addToContinues(Cards c)
	{
		continues.add(c);
	}
	
	public void addToFree(Cards c)
	{
		freeCards.add(c);
	}
	public ArrayList<Cards> sort()
	{
		ArrayList<Cards> r=new ArrayList<Cards>(allPlayed);
		Collections.sort(r);
		return r;
	}
	public void buildWonders()
	{
		//System.out.println(wonder.getStage());
		if(wonder.getStage()==0)
		{
			wonder.setStage(1);
		}
		else if(wonder.getStage()==1)
		{
			wonder.setStage(2);
		}
		else if(wonder.getStage()==2)
		{
			wonder.setStage(3);
		}
		
		//System.out.println(wonder.getStage());
	}
	
	public void setHand(ArrayList<Cards> h)
	{
		hand = h;
	}
	
	public void setWonders(int n) throws IOException
	{
		wonder = new Wonders(n);
	}
	
	public void setWins1(int a)
	{
		m1Pts = m1Pts+a;
		setTotalMPoints(a);
		setVPoints(a);
	}
	public void setWins3(int a)
	{
		m3Pts = m3Pts+a;
		setTotalMPoints(a);
		setVPoints(a*3);
	}
	public void setWins5(int a)
	{
		m5Pts = m5Pts+a;
		setTotalMPoints(a);
		setVPoints(a*5);
	}
	public void setNeg(int a)
	{
		totalNegPoints = totalNegPoints+a;
		setTotalMPoints(a);
	}
	
	public void setTotalMPoints(int a)
	{
		totalMPoints = totalMPoints+a;
	}
	
	public void setVPoints(int a)
	{
		vPoints +=a;
	}
	
	public void setMCards(int a)
	{
		numMCards+=a;
	}
	
	public void setRightB(int a)
	{
		costRightB = a;
	}
	
	public void setLeftB(int a)
	{
		costLeftB = a;
	}
	
	public void setRightG(int a)
	{
		costRightG = a;
	}
	
	public void setLeftG(int a)
	{
		costLeftG = a;
	}
	public void setSciPts(String a)
	{
		sci=a;
	}
	public void setDiscard(boolean b)
	{
		discard = b;
	}
	public void setPlayed(boolean b)
	{
		played = b;
	}
	public void setWonderPts(int i)
	{
		wonderPts=i;
	}
}
	
	
	
	