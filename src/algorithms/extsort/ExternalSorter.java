/**
 * 
 */
package algorithms.extsort;

import java.io.IOException;
import java.math.BigInteger;

import algorithms.MinHeap;

/**
 * @author yovn
 *
 */
public class ExternalSorter
{

	public void sort(int heapSize, RecordStore source, RunAcceptor mediator,
			ResultAcceptor ra) throws IOException
	{
		BigInteger nowLimit = new BigInteger("1");
		BigInteger bigOne = new BigInteger("1");
		BigInteger bigMinusOne = new BigInteger("-1");
		BigInteger indexOnce= new BigInteger("0");
		BigInteger tempLimit = ((FileRecordStore)source).sql.limit;
		MinHeap<Record> heap = new MinHeap<Record>(Record.class, heapSize);
		for (int i = 0; i < heapSize; i++)
		{
			Record r = source.readNextRecord(indexOnce =indexOnce.add(bigOne));//******
			if (r.isNull())
				break;
			heap.insert(r);
		}

		Record readR = source.readNextRecord(indexOnce = indexOnce.add(bigOne));//****
		
		
		while (!readR.isNull() || !heap.isEmpty())
		{

			Record curR = null;
			// begin output one run
			mediator.startNewRun();
			
			while (!heap.isEmpty())
			{
				curR = heap.removeMin();

				mediator.acceptRecord(curR);

				if (!readR.isNull())
				{
					//System.out.println(readR);
					if (readR.compareTo(curR) < 0 )
					{
						heap.addToTail(readR);
					} else
						heap.insert(readR);
				}
				readR = source.readNextRecord(indexOnce = indexOnce.add(bigOne));//*****

			}
			// done one run
			mediator.closeRun();
			//System.out.println(indexOnce);
			// prepare for next run
			heap.reverse();
			while (!heap.isFull() && !readR.isNull())
			{
				heap.insert(readR);
				readR = source.readNextRecord();

			}

		}
	
		System.out.println("开始归并");
		RecordStore[] stores=mediator.getProductedStores();
//		LoserTree  lt=new LoserTree(stores);

		WinnerTree  lt=new WinnerTree(stores);
		
		
		Record least=lt.nextLeastRecord();
		
		ra.start();
		//now<=temp   temp!=-1
		while(   (  !least.isNull()  ) && (  (tempLimit.compareTo(bigMinusOne)!=-1)   && (nowLimit.compareTo(tempLimit)<=0)  ) )
		{
			nowLimit = nowLimit.add(bigOne);
			ra.acceptRecord(least);
			least=lt.nextLeastRecord();
		}
		ra.end();
		
		for(int i=0;i<stores.length;i++)
		{
			//stores[i].destroy();
		}
	}
	public static void main(String[] args) throws IOException
	{
		// RecordStore store=new MemRecordStore(60004,true);
		// RunAcceptor mediator=new MemRunAcceptor();
		// ResultAcceptor ra=new MemResultAcceptor();
		SQL sql1 = new SQL("CREATE TABLE mytable(column1 INT NOT NULL,column2 NUMERIC(8,4),column3 CHAR(32),column4 VARCHAR(256) NOT NULL,column5 VARCHAR(256));");
		SQL sql = new SQL("SELECT  DISTINCT column1,column2 FROM mytable ORDER BY column1, LIMIT 1000;");
		if(sql.succeed())
		{
			if(sql.getCreate())
			{
				System.out.println("数据库创建成功");
			}
			else if(sql.getSelect())
			{
				System.out.println("开始排序");
				ExternalSorter sorter = new ExternalSorter();
				//需要排序的文件名
				RecordStore store = new FileRecordStore("unsort.txt",sql);
				//排序的零食文件名
				RunAcceptor mediator = new FileRunAcceptor("unsort",sql);
				//排序完成的文件名  test_sorted
				ResultAcceptor ra = new FileRecordStore("unsorted.txt",sql);
				//700000 80M
				sorter.sort(3, store, mediator, ra);
				System.out.println("归并完成");
			}
			
		}

	}

}
