/**
 * 
 */
package algorithms.extsort;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * @author yovn
 *
 */
// 用于指定需要排序的文件名
public class FileRecordStore implements RecordStore, ResultAcceptor
{

	// 一个内部类 存放数据结构
	private static class _Record implements Record
	{

		// static final _Record NULL_RECORD = new _Record(Integer.MAX_VALUE,
		// "");
		
		
		static final _Record NULL_RECORD = new _Record(null, "", new SQL(""));
		// int value;
		private String txt;
		private SQL sql;
		public ArrayList<String> all;

		_Record(ArrayList<String> all, String txt, SQL sql)
		{
			this.all = all;
			this.txt = txt;
			this.sql = sql;
		}

		// _Record(int v, String txt)
		// {
		// value = v;
		// this.txt = txt;
		// }

		@Override
		public boolean isNull()
		{
			//System.out.println(this);
			if (all==null || all.size()==0 || all.isEmpty())
			{
				//System.out.println(all + "判断是否null" + true);
				return true;
			}
			//System.out.println(all + "判断是否null" + false);
			return false;
			//return this == NULL_RECORD;
		}

		// @Override
		// public int compareTo(Record o)
		// {
		// _Record other = (_Record) o;
		// if (other == this)
		// return 0;
		//
		// return value - other.value;
		// }

		@Override
		public int compareTo(Record o)
		{
			_Record other = (_Record) o;
			if (other == this)
				return 0;
			// 开启判断模式
			if(other.isNull())
			{
				return -1;
			}
			if(this.isNull())
			{
				return 1;
			}
			for (int i = 0; i < all.size(); i++)
			{
				//System.out.println(i);
				if (sql.useFielRuleDisvisibleType[i].equals("Float"))
				{
					float tempFloat = 0;
					tempFloat = ((float) Float.valueOf(all.get(i)) - (float) Float.valueOf(other.all.get(i)));
					if (tempFloat > 0)
					{
						return 1;
					} else if (tempFloat < 0)
					{
						return -1;
					} else
					{
						return 0;
					}

				} 
				else if (sql.useFielRuleDisvisibleType[i].equals("Integer"))
				{
					return ((int) Integer.valueOf(all.get(i)) - (int) Integer.valueOf(other.all.get(i)));
				} 
				else if (sql.useFielRuleDisvisibleType[i].equals("String"))
				{
					//这里注意判断FIRST LAST CHINESE
					if(sql.useFielRuleDisvisibleRule[i].equals("FIRST"))
					{
						return 1;
					}
					else if(sql.useFielRuleDisvisibleRule[i].equals("LAST"))
					{
						return -1;
					}
					else if(sql.useFielRuleDisvisibleRule[i].equals("CHINESE"))
					{
						GetPinYin.getPingYin((String) this.all.get(i)).compareTo(GetPinYin.getPingYin((String) other.all.get(i)));
					}
					else
					{
						return ((String) this.all.get(i)).compareTo((String) other.all.get(i));
					}
							
				}
			}
			return 0;
		}

		public String toString()
		{
			if (this == NULL_RECORD)
				return "NULL_RECORD";
			return txt;
		}
		
		
		public String  toTrueString()
		{
			if (this == NULL_RECORD)
				return "NULL_RECORD";
			String str = all.get(0);
			for(int i=1;i<all.size();i++)
			{
				str = str + "," +  all.get(i) ; 
			}
			return str;
		}

	}

	private String fileName;
	private BufferedReader reader;
	private PrintStream ps;
	private SQL sql;

	boolean eof;
	
	
	int count = 0;

	Record prev = null;

	// 定义文件名
	// public FileRecordStore(String name)
	// {
	// fileName = name;
	// }

	public FileRecordStore(String name, SQL sql)
	{
		fileName = sql.tableName + "\\" +  name;
		this.sql = sql;
	}

	public void storeRecord(Record r) throws IOException
	{
		if (ps == null)
		{
			OutputStream out = new FileOutputStream(fileName);
			ps = new PrintStream(new BufferedOutputStream(out, 12 * 1024));
		}
		ps.println(r.toString());
	}

	public void compact()
	{
		if (ps != null)
		{
			ps.flush();
			ps.close();
			ps = null;
		}
		if (reader != null)
		{
			try
			{
				reader.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reader = null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.extsort.RecordStore#readNextRecord()
	 */
	@Override
	public Record readNextRecord() throws IOException
	{
		if (eof)
			return _Record.NULL_RECORD;
		if (reader == null)
		{
			//读文件
			//System.out.println(fileName);
			InputStream in = new FileInputStream(fileName);
			reader = new BufferedReader(new InputStreamReader(in), 12 * 1024);
		}
		if (!reader.ready())
		{
			eof = true;
			return _Record.NULL_RECORD;
		}
		String line = reader.readLine();

		//

		// String head = line.substring(0, 10).trim();

		// 分析字符串结构 只截取前半部分数字，还有后部分的结构
		String[] intfirst = line.split(",");
		
		if(sql.getSelect())
		{
			
			//加载需排序的字段 ArrayList
			ArrayList<String> all = new ArrayList<>(sql.useFielRuleDisvisible.length);
			for(int i=0;i<sql.useFielRuleDisvisible.length;i++)
			{
				all.add(intfirst[sql.useFielRuleDisvisible[i]]);
			}
			//生成规则 txt
			String tempStr = "";
			
			for(int i=0;i<sql.useFielRuleVisible.length;i++)
			{
				tempStr = "," + sql.tableFieldName[sql.useFielRuleVisible[i]] + tempStr;
			}
			//tempStr = tempStr.substring(1,tempS)
			
			
			int val = Integer.valueOf(intfirst[0].trim());
			// System.out.println(val);
			 _Record ret = new _Record(all,line,sql);
			 return ret;
		}
	
		return null;
	}

	@Override
	public void acceptRecord(Record rec) throws IOException
	{
		count++;
		if (prev == null)
		{
			prev = rec;
		} else if (prev.compareTo(rec) > 0)
		{
			throw new IOException(" sorted error!!!");

		}
		ps.println(rec.toTrueString());
		prev = rec;
	}

	@Override
	public void end() throws IOException
	{

		if (ps != null)
		{
			ps.flush();
			ps.close();
			ps = null;
		}
	}

	@Override
	public void start() throws IOException
	{
		if (ps == null)
		{
			OutputStream out = new FileOutputStream(fileName);
			ps = new PrintStream(new BufferedOutputStream(out, 12 * 1024));
		}

	}

	@Override
	public void destroy()
	{
		compact();
		File f = new File(fileName);
		f.delete();

	}

}
