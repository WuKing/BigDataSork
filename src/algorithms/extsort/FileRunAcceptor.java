/**
 * 
 */
package algorithms.extsort;

import java.io.IOException;

/**
 * @author yovn
 *
 */
//文件运转
public class FileRunAcceptor extends BaseRunAcceptor
{

	int count = 0;
	private String name;
	private SQL sql;
	/**
	 * 
	 */
	public FileRunAcceptor(String fileName,SQL sql)
	{
		stores = new FileRecordStore[50];
		name = fileName;
		this.sql = sql;
	}

	@Override
	public void acceptRecord(Record rec) throws IOException
	{
		count++;
		((FileRecordStore) stores[index]).storeRecord(rec);
	}

	@Override
	public void closeRun()
	{
		System.out.println("第" + (index + 1) + "组完成,完成了" + count + "条数据");
		((FileRecordStore) stores[index]).compact();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.extsort.BaseRunAcceptor#increamentStoresBy(int)
	 */
	@Override
	protected void increamentStoresBy(int len)
	{
		RecordStore[] tmp = new RecordStore[len + stores.length];
		System.arraycopy(stores, 0, tmp, 0, stores.length);
		stores = tmp;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.extsort.BaseRunAcceptor#initStore(int)
	 */
	@Override
	protected void initStore(int i)
	{
		//System.out.println("run:(1)start");
		// count=0;
		stores[i] = new FileRecordStore(name + "_" + i,sql);

	}

}
