package algorithms.extsort;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//���ڶ�������Լ��������
public class SQL
{
	// ����
	public String tableName;
	// �ֶ�����
	public String[] tableFieldName;
	// �ֶ�����
	public String[] tableFiledType;

	// ��Ҫ������ֶ�
	public int[] useFielRuleVisible;

	// ��Ҫ��ʾ���ֶ�
	public int[] useFielRuleDisvisible;
	
	//��Ҫ��ʾ���ֶ�����
	public String[] useFielRuleDisvisibleType;
	
	// ��Ҫ��ʾ���ֶι���
	public String[] useFielRuleDisvisibleRule;
	// �Ƿ�ȥ��
	public boolean distinct = true;

	public BigInteger limit = new BigInteger("-1");
	// �Ƿ��Ѿ����������ݿ�
	private boolean create = false;

	// �Ƿ��Ѿ��涨�����ݿ���������
	private boolean select = false;

	
	
	
	//�������Ƿ�ɹ�
	public boolean succeed()
	{
		return (create || select);
	}
	
	//�������Ƿ�ɹ�
	public boolean getCreate()
	{
		return create;
	}
	
	//�������Ƿ�ɹ�
	public boolean getSelect()
	{
		return select;
	}
	
	private String trimWord(String str, String[] rule)
	{

		Pattern p;
		Matcher m;

		for (int i = 0; i < rule.length; i++)
		{
			p = Pattern.compile("\\s*" + rule[i] + "\\s*");
			m = p.matcher(str);
			str = m.replaceAll(rule[i]);
		}

		return str;
	}

	private String importantWord(String str, String ruleFalse[],
			String ruleTrue[])
	{
		for (int i = 0; i < ruleFalse.length; i++)
		{
			str = str.replaceAll(" " + ruleFalse[i] + " ", " " + ruleTrue[i]
					+ " ");
		}
		return str;
	}

	// ��׼�������Ĺؼ���
	private String createRule(String str)
	{
		String[] ruleFalse = { "create", "table", "int", "not", "null",
				"numeric", "char", "varchar" };
		String[] ruleTrue = { "CREATE", "TABLE", "INT", "NOT", "NULL",
				"NUMERIC", "CHAR", "VARCHAR" };
		return importantWord(str, ruleFalse, ruleTrue);
	}

	// ��׼������ؼ���
	private String insertRule(String str)
	{
		String[] ruleFalse = { "insert", "into", "values" };
		String[] ruleTrue = { "INSERT", "INTO", "VALUES" };
		return importantWord(str, ruleFalse, ruleTrue);
	}

	// ��׼����ѯ���
	private String SELECTRule(String str)
	{
		String[] ruleFalse = { "select", "distinct", "from", "order", "by",
				"nulls", "first", "last", "limit", "collate", "chinese","utf8"};
		String[] ruleTrue = { "SELECT", "DISTINCT", "FROM", "ORDER", "BY",
				"NULLS", "FIRST", "LAST", "LIMIT", "COLLATE", "CHINESE","UTF8"};
		return importantWord(str, ruleFalse, ruleTrue);
	}

	private String replaceBlank(String str)
	{
		str = str.replaceAll(";", "");
		String dest = "";
		if (str != null)
		{
			Pattern p = Pattern.compile("\\s+|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll(" ");
			String[] trimRule = { ",", "\\(", "\\)", ";" };
			dest = trimWord(dest, trimRule);
		}
		return dest.trim();

	}

	private String ruleAllRule(String str)
	{
		str = this.createRule(str);
		str = this.insertRule(str);
		str = this.SELECTRule(str);
		return str;
	}

	private boolean runCreate(String str)
	{

		
		
		try
		{
			int now = -1;
			int left = -1;
			int right = -1;
			String tempStr = "";
			if ((now = str.indexOf("CREATE TABLE")) == 0)
			{
			
				// �ж����
				// System.out.println(now+"CREATE TABLE".length() );
				left = str.indexOf("(");
				// ���ȡ�����ݿ�����
				tableName = str.substring(13, left);

				right = str.lastIndexOf(")");
				// ��ʼ����ͷ������
				tempStr = str.substring(left + 1, right);
				if (tempStr.lastIndexOf(",") + 1 == tempStr.length())
				{
					tempStr = tempStr.substring(0, tempStr.lastIndexOf(","));
				}
				Pattern p = Pattern.compile("\\([\\d|\\d,\\d]*\\)|NOT NULL");
				Matcher m = p.matcher(tempStr);
				tempStr = m.replaceAll("");
				
				tableFieldName = tempStr.split(",");
				tableFiledType = tempStr.split(",");

				
				for (int i = 0; i < tableFieldName.length; i++)
				{
					tableFieldName[i] = tableFieldName[i].split(" ")[0];
					// ��������
					tableFiledType[i] = tableFiledType[i].split(" ")[1];
					//System.out.println(tableFiledType[i]);
					switch (tableFiledType[i])
					{
					case "INT":
						tableFiledType[i] = "Integer";
						break;
					case "CHAR":
					case "VARCHAR":
						tableFiledType[i] = "String";
						break;
					case "NUMERIC":
						tableFiledType[i] = "Float";
						break;
					default:
						throw new Exception();
					}
				}
			
				// ������Ŀ�ļ���
				File directory = new File("");// ����Ϊ��
				String courseFile = directory.getCanonicalPath();

				File file = new File(courseFile + "\\" + tableName);
				// ����ļ��в������򴴽�
				if (!file.exists() && !file.isDirectory())
				{
					file.mkdir();
					// ��һ�������ͷ�ļ�
					File document = new File(courseFile + "\\" + tableName
							+ "\\" + "title.tn");
					if (!document.exists())
					{
						try
						{

//							BufferedWriter writer = new BufferedWriter(
//									new FileWriter(document));
//
//							writer.write(str);
//							// writer.flush();
//							writer.close();
							// ������ɴ������ݿ�
							OutputStream out = new FileOutputStream(document);
							PrintStream ps = new PrintStream(new BufferedOutputStream(out, 12 * 1024),false,"UTF8");
							ps.print(str);
							ps.flush();
							ps.close();
							
							
							return true;
						} catch (IOException e)
						{
							return false;
						}
					}

				}
			}

		} catch (Exception ex)
		{
			return false;
		}
		return false;

	}

	// ��ԭ��ͷ��������Ϣ
	// ��Ҫ���δ������
	private boolean readTableTitle(String str)
	{
		try
		{
			// ������Ŀ�ļ���
			File directory = new File("");// ����Ϊ��
			String courseFile = directory.getCanonicalPath();
			File document = new File(courseFile + "\\" + str + "\\"
					+ "title.tn");
			File file = new File(courseFile + "\\" + str);
			if (document.exists())
			{

//				BufferedReader reader = new BufferedReader(new FileReader(
//						document));
//
//				runCreate(reader.readLine());
//				// writer.flush();
//				reader.close();
				// ������ɴ������ݿ�
				
				InputStream in = new FileInputStream(document);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in), 12 * 1024);
				
				runCreate(reader.readLine());
				// writer.flush();
				reader.close();
				
				
				
				
				return true;
			}

		} catch (Exception e)
		{
			return false;
		}
		return false;
	}

	private boolean runSelect(String str)
	{

		int now = -1;
		int left = -1;
		int right = -1;
		String tempStr = "";
		if ((now = str.indexOf("SELECT")) == 0)
		{
			
			// ����Ҫ���ǹ淶���
			try
			{
				// ȥ�� ��ѡ
				if ((now = str.indexOf("DISTINCT")) != -1)
				{
					// ȥ��
					this.distinct = true;
					now = 16;
				} else
				{
					now = 7;
					this.distinct = false;
				}
				
				// ��ȡ��Ҫ������ֶ�

				// ��ȡ���ݿ�����
				tableName = str.substring(str.indexOf("FROM") + 5,
						str.indexOf(" ", str.indexOf("FROM") + 5));
				// ����ͷ���ļ�
				readTableTitle(tableName);
				// ���ر�ͷ����
				ArrayList<String> useFielRuleVisibleALL = new ArrayList<>(
						tableFieldName.length);
			
				for (int i = 0; i < tableFieldName.length; i++)
				{
					useFielRuleVisibleALL.add(tableFieldName[i]);
				}
				// �������й������
				ArrayList<String> useFielRuleDisvisibleALL = new ArrayList<>(3);
				useFielRuleDisvisibleALL.add("FIRST");
				useFielRuleDisvisibleALL.add("LAST");
				useFielRuleDisvisibleALL.add("CHINESE");
				useFielRuleDisvisibleALL.add("UTF8");
				String[] useFielRuleDisvisibleArray = { "FIRST", "LAST",
						"CHINESE","UTF8"};
				
				for (int i = 0; i < tableFieldName.length; i++)
				{
					useFielRuleVisibleALL.add(tableFieldName[i]);
				}

				// �����õ���������ֶ�
				String useFielRuleVisibleStr = str.substring(now,
						str.indexOf(" ", now));
				//System.out.println(useFielRuleVisibleStr);
				String[] useFielRuleVisibleStrArray = useFielRuleVisibleStr
						.split(",");
	
				useFielRuleVisible = new int[useFielRuleVisibleStrArray.length];
				// ����ѭ���ҵ���Ҫ������ַ�
				for (int i = 0; i < useFielRuleVisibleStrArray.length; i++)
				{
					int j = 0;
					for (j = 0; j < tableFieldName.length; j++)
					{
						if (useFielRuleVisibleStrArray[i]
								.equals(tableFieldName[j]))
						{
							useFielRuleVisible[i] = j;
							useFielRuleVisibleALL.add(tableFieldName[j]);
							continue;
						}
						if (!useFielRuleVisibleALL
								.contains(useFielRuleVisibleStrArray[i]))
						{
							return false;
						}

					}
				}
				
				now = str.indexOf(tableName) + tableName.length() + 1;
				// ��ʼƥ����Ҫ��ʾ���ֶ�
				// �ж��Ƿ�ȱʡ��ʾ
				if (str.indexOf("ORDER BY") == -1)
				{
					useFielRuleDisvisible = new int[useFielRuleVisible.length];
					for (int i = 0; i < useFielRuleVisible.length; i++)
					{
						 useFielRuleVisible[i] = i;
					}
				} 
				else
				{
					now = now + "ORDER BY".length() + 1;
					String tempuseFielRuleDisvisibleStr = str.substring(now);
					int tempNow = tempuseFielRuleDisvisibleStr.indexOf("LIMIT");
					if (tempNow == -1)
					{
						tempNow = tempuseFielRuleDisvisibleStr.length();
					} else
					{
						tempNow--;
					}
					
					
					tempuseFielRuleDisvisibleStr = tempuseFielRuleDisvisibleStr
							.substring(0, tempNow);
					String[] tempuseFielRuleDisvisibleArray = tempuseFielRuleDisvisibleStr
							.split(",");
					useFielRuleDisvisible = new int[tempuseFielRuleDisvisibleArray.length];
					useFielRuleDisvisibleRule = new String[tempuseFielRuleDisvisibleArray.length];
					String[] tempstrEach;
				
					for (int i = 0; i < tempuseFielRuleDisvisibleArray.length; i++)
					{
						tempstrEach = tempuseFielRuleDisvisibleArray[i]
								.split(" ");
						if (tempstrEach.length == 1)
						{
							// û�й���ֱ�ӻ�ȡ
							for (int j = 0; j < tableFieldName.length; j++)
							{
								if (tempstrEach[0].equals(tableFieldName[j]))
								{
									useFielRuleDisvisible[i] = j;
									useFielRuleDisvisibleRule[i] = "";
									continue;
								}
								if (!useFielRuleVisibleALL
										.contains(tempstrEach[0]))
								{
									return false;
								}

							}
						} else if (tempstrEach.length == 3)
						{
						
							// ӵ�й��� һ������ ����Ҫ��ǰ���
							// û�й���ֱ�ӻ�ȡ
							for (int j = 0; j < tableFieldName.length; j++)
							{
								if (tempstrEach[0].equals(tableFieldName[j]))
								{
									useFielRuleDisvisible[i] = j;
									// useFielRuleDisvisibleRule[i] = "";
									// ����Ҫ������
									// useFielRuleDisvisibleALL
									// useFielRuleDisvisibleArray

									for (int k = 0; k < useFielRuleDisvisibleArray.length; k++)
									{
										if (tempstrEach[2]
												.equals(useFielRuleDisvisibleArray[k]))
										{
											useFielRuleDisvisibleRule[i] = useFielRuleDisvisibleArray[k];
											continue;
										}
										if (!useFielRuleDisvisibleALL
												.contains(tempstrEach[2]))
										{
											return false;
										}

									}

									continue;
								}
								if (!useFielRuleVisibleALL
										.contains(tempstrEach[0]))
								{
									return false;
								}

							}
						} else
						{
							return false;
						}
					}
				
					//������Ҫ��ʾ���ֶ�����
					useFielRuleDisvisibleType = new String[useFielRuleDisvisible.length];
					for(int i =0;i<useFielRuleDisvisible.length;i++)
					{
						useFielRuleDisvisibleType[i] = tableFiledType[useFielRuleDisvisible[i]];
					}
					
				
					
					//���LIMIT
					if((now = str.indexOf("LIMIT"))==-1)
					{
						limit = new BigInteger("-1");
					}
					else
					{
						limit = new BigInteger(str.substring(now + "LIMIT".length()+1));
					}
					
					
					

				}

			} catch (Exception ex)
			{
				return false;
			}

		}
		/*
		//���Ի�ȡ��������
		
		// ����
		System.out.println("���ݿ�����:" + tableName);
		// �ֶ�����
		System.out.println("�ֶ�����:");
		for(String s:tableFieldName)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// �ֶ�����
		System.out.println("�ֶ�����:");
		for(String s:tableFiledType)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// ��Ҫ������ֶ�
		System.out.println("������ֶ�:");
		for(int s:useFielRuleVisible)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// ��Ҫ��ʾ���ֶ�
		System.out.println("��ʾ���ֶ�:");
		for(int s:useFielRuleDisvisible)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// ��Ҫ��ʾ���ֶ�����
		System.out.println("��ʾ����:");
		for(String s:useFielRuleDisvisibleType)
		{
			System.out.print(s + ",");
		}
		
		
		// ��Ҫ��ʾ���ֶι���
		System.out.println("��ʾ���ֶι���:");
		for(String s:useFielRuleDisvisibleRule)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// �Ƿ�ȥ��
		System.out.println("�Ƿ�ȥ��" + distinct);
		//��ʾ��������
		System.out.println("��ʾ��������" +limit);

		*/
		
		

		return true;
	}

	private boolean runAll(String str)
	{
		create = runCreate(str);
		select =  runSelect(str);
		return  create|| select;
	}

	// һ��SQL�������
	public SQL(String str)
	{
		// 1.�Ż��ո�
		str = replaceBlank(str);

		// 2.�Ż��ؼ���
		str = ruleAllRule(str);
		
		

		// 3.ִ�����
		runAll(str);
	}
//	public static void main(String args[])
//	{
//		SQL sql = new
//		 SQL("CREATE TABLE mytable(column1      INT NOT NULL, column2      NUMERIC ( 8,4),column3      CHAR(32),column4      VARCHAR(256) NOT NULL,column5      VARCHAR(256));");
//		SQL sql = new SQL(
//				" SELECT  DISTINCT column1,column5,column3 FROM mytable ORDER BY column1 ,column5 NULLS FIRST LIMIT 1000;");
//	}
}
