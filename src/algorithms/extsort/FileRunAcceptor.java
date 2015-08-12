/**
 * 
 */
package algorithms.extsort;

import java.io.IOException;

/**
 * @author yovn
 *
 */
//�ļ���ת
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
		System.out.println("\nend run:(" + (index + 1) + ")total:" + count);

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
		System.out.println("start new run:");
		// count=0;
		stores[i] = new FileRecordStore(name + "_" + i,sql);

	}

}
