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

//用于定义规则，以及储存规则
public class SQL
{
	// 表名
	public String tableName;
	// 字段名称
	public String[] tableFieldName;
	// 字段类型
	public String[] tableFiledType;

	// 需要排序的字段
	public int[] useFielRuleVisible;

	// 需要显示的字段
	public int[] useFielRuleDisvisible;
	
	//需要显示的字段类型
	public String[] useFielRuleDisvisibleType;
	
	// 需要显示的字段规则
	public String[] useFielRuleDisvisibleRule;
	// 是否去重
	public boolean distinct = true;

	public BigInteger limit = new BigInteger("-1");
	// 是否已经创建了数据库
	private boolean create = false;

	// 是否已经规定了数据库的排序规则
	private boolean select = false;

	
	
	
	//检测语句是否成功
	public boolean succeed()
	{
		return (create || select);
	}
	
	//检测语句是否成功
	public boolean getCreate()
	{
		return create;
	}
	
	//检测语句是否成功
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

	// 标准化创建的关键字
	private String createRule(String str)
	{
		String[] ruleFalse = { "create", "table", "int", "not", "null",
				"numeric", "char", "varchar" };
		String[] ruleTrue = { "CREATE", "TABLE", "INT", "NOT", "NULL",
				"NUMERIC", "CHAR", "VARCHAR" };
		return importantWord(str, ruleFalse, ruleTrue);
	}

	// 标准化插入关键字
	private String insertRule(String str)
	{
		String[] ruleFalse = { "insert", "into", "values" };
		String[] ruleTrue = { "INSERT", "INTO", "VALUES" };
		return importantWord(str, ruleFalse, ruleTrue);
	}

	// 标准化查询语句
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
			
				// 判断完成
				// System.out.println(now+"CREATE TABLE".length() );
				left = str.indexOf("(");
				// 完成取出数据库名称
				tableName = str.substring(13, left);

				right = str.lastIndexOf(")");
				// 开始分析头部类型
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
					// 修正类型
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
			
				// 创建项目文件夹
				File directory = new File("");// 参数为空
				String courseFile = directory.getCanonicalPath();

				File file = new File(courseFile + "\\" + tableName);
				// 如果文件夹不存在则创建
				if (!file.exists() && !file.isDirectory())
				{
					file.mkdir();
					// 进一步接入表头文件
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
							// 这里完成创建数据库
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

	// 还原表头的所有信息
	// 需要传参传入表名
	private boolean readTableTitle(String str)
	{
		try
		{
			// 创建项目文件夹
			File directory = new File("");// 参数为空
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
				// 这里完成创建数据库
				
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
			
			// 这里要看是规范语句
			try
			{
				// 去重 可选
				if ((now = str.indexOf("DISTINCT")) != -1)
				{
					// 去重
					this.distinct = true;
					now = 16;
				} else
				{
					now = 7;
					this.distinct = false;
				}
				
				// 获取需要排序的字段

				// 读取数据库名称
				tableName = str.substring(str.indexOf("FROM") + 5,
						str.indexOf(" ", str.indexOf("FROM") + 5));
				// 加载头部文件
				readTableTitle(tableName);
				// 加载表头集合
				ArrayList<String> useFielRuleVisibleALL = new ArrayList<>(
						tableFieldName.length);
			
				for (int i = 0; i < tableFieldName.length; i++)
				{
					useFielRuleVisibleALL.add(tableFieldName[i]);
				}
				// 加载所有规则语句
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

				// 分析得到排序规则字段
				String useFielRuleVisibleStr = str.substring(now,
						str.indexOf(" ", now));
				//System.out.println(useFielRuleVisibleStr);
				String[] useFielRuleVisibleStrArray = useFielRuleVisibleStr
						.split(",");
	
				useFielRuleVisible = new int[useFielRuleVisibleStrArray.length];
				// 根据循环找到需要排序的字符
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
				// 开始匹配需要显示的字段
				// 判断是否缺省显示
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
							// 没有规则直接获取
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
						
							// 拥有规则 一共三种 并且要提前检查
							// 没有规则直接获取
							for (int j = 0; j < tableFieldName.length; j++)
							{
								if (tempstrEach[0].equals(tableFieldName[j]))
								{
									useFielRuleDisvisible[i] = j;
									// useFielRuleDisvisibleRule[i] = "";
									// 这里要检测规则
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
				
					//满足需要显示的字段类型
					useFielRuleDisvisibleType = new String[useFielRuleDisvisible.length];
					for(int i =0;i<useFielRuleDisvisible.length;i++)
					{
						useFielRuleDisvisibleType[i] = tableFiledType[useFielRuleDisvisible[i]];
					}
					
				
					
					//检查LIMIT
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
		//测试获取所有数据
		
		// 表名
		System.out.println("数据库名称:" + tableName);
		// 字段名称
		System.out.println("字段名称:");
		for(String s:tableFieldName)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// 字段类型
		System.out.println("字段类型:");
		for(String s:tableFiledType)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// 需要排序的字段
		System.out.println("排序的字段:");
		for(int s:useFielRuleVisible)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// 需要显示的字段
		System.out.println("显示的字段:");
		for(int s:useFielRuleDisvisible)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// 需要显示的字段类型
		System.out.println("显示类型:");
		for(String s:useFielRuleDisvisibleType)
		{
			System.out.print(s + ",");
		}
		
		
		// 需要显示的字段规则
		System.out.println("显示的字段规则:");
		for(String s:useFielRuleDisvisibleRule)
		{
			System.out.print(s + ",");
		}
		System.out.println();
		// 是否去重
		System.out.println("是否去重" + distinct);
		//显示的数据量
		System.out.println("显示的数据量" +limit);

		*/
		
		

		return true;
	}

	private boolean runAll(String str)
	{
		create = runCreate(str);
		select =  runSelect(str);
		return  create|| select;
	}

	// 一切SQL语句的入口
	public SQL(String str)
	{
		// 1.优化空格
		str = replaceBlank(str);

		// 2.优化关键字
		str = ruleAllRule(str);
		
		

		// 3.执行语句
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
