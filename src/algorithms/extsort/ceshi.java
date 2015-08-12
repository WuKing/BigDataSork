package algorithms.extsort;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ceshi
{

	private ArrayList all;
	
	
	public static void main(String args[])
	{
		CompareArrayList al1 = new CompareArrayList();
		al1.add(new Integer(123));
		al1.add(new String("abcd"));
		al1.add(new Float(4.444));
		
		CompareArrayList al2 = new CompareArrayList();
		al2.add(new Integer(124));
		al2.add(new String("abcde"));
		
		
		System.out.println(al1.compareTo(al2));
	
	}

}

class CompareArrayList extends ArrayList implements Comparable<CompareArrayList>
{

	private String everyClassName;

	@Override
	public int compareTo(CompareArrayList o)
	{
		for(int i=0;i<=size();i++)
		{
			everyClassName = get(i).getClass().getName();
			if(everyClassName.equals("java.lang.Float"))
			{
				if(((float)this.get(i) - (float)o.get(i))!=0)
				{
					return (int)(((float)this.get(i) - (float)o.get(i))*10000); 
				}

			}
			else if(everyClassName.equals("java.lang.Integer"))
			{
				if(((int)this.get(i) - (int)o.get(i))!=0)
				{
					return (int)this.get(i) - (int)o.get(i); 
				}
			}
			else if(everyClassName.equals("java.lang.Integer"))
			{
				return ((String)this.get(i)).compareTo((String)o.get(i));
			}	
		}
		return 0;
	}
	
	
	
}