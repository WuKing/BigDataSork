package algorithms.extsort;

public class TestChinese
{

	public static String bytes2HexString(byte b)
	{
		return bytes2HexString(new byte[] { b });
	}

	// 汉字转换成区位码
	public static String bytes2HexString(byte[] b)
	{
		String ret = "";
		for (int i = 0; i < b.length; i++)
		{
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	// 汉字转换成区位码
	public static String getString(String chinese)
	{
		byte[] bs;
		String s = "";
		try
		{
			bs = chinese.getBytes("GB2312");

			for (int i = 0; i < bs.length; i++)
			{
				int a = Integer.parseInt(bytes2HexString(bs[i]), 16);
				s += (a - 0x80 - 0x20) + "";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}

	// 区位码转换成汉字
	public static String CodeToChinese(String code)
	{
		String Chinese = "";
		for (int i = 0; i < code.length(); i += 4)
		{
			byte[] bytes = new byte[2];
			String lowCode = code.substring(i, i + 2);
			int tempLow = Integer.parseInt(lowCode);
			tempLow += 160;
			bytes[0] = (byte) tempLow;
			String highCode = code.substring(i + 2, i + 4);
			int tempHigh = Integer.parseInt(highCode);
			tempHigh += 160;
			bytes[1] = (byte) tempHigh;
			String chara = new String(bytes);
			Chinese += chara;
		}
		return Chinese;
	}

	// 获得指定范围的随机数
	// @param min 最小值
	// @param max 最大值
	// @return 返回值
	public static int pickRandom(int min, int max)
	{
		return (int) (Math.random() * (max - min + 1) + min);
	}
	

	// 区码: 汉字内码高位 - 0xA0 位码: 汉字内码低位 - 0xa0
	// 区号：01－87，汉字从16区开始,前15区为各种符号
	// 位号：01－94
	// 第一个汉字“啊”的区号为16，位号为01 续：
	// 两字节内码：区号+0xA0 , 位号+0xa0
	// 如：第一个汉字“啊”的区号为16，位号为01，则其内码为 0xb0,0xa1
	// ======> 区号：16---87 位号：01---94
	public static String RandomName()
	{
		String name = "";
		int qu = pickRandom(16, 87);
		int wei = pickRandom(1, 94);
		String code = "";
		if (wei < 10)
		{
			code = String.valueOf(qu) + "0" + String.valueOf(wei);
		} else
		{
			code = String.valueOf(qu) + String.valueOf(wei);
		}
		name = CodeToChinese(code);
		return name;
	}

	// 测试
	public static void main(String[] args) throws Exception
	{
		String str = "创";
		String s = TestChinese.getString(str);
		System.out.println(s);
		String a = TestChinese.CodeToChinese(s);
		System.out.println(a);
		a = TestChinese.CodeToChinese("1601");
		System.out.println(a);
		// 获得随机的名字 2~4 个汉字
		// 同百家姓是对不上的，如果要真正的姓名，需要建立姓的数组
		for (int i = 0; i < 100; i++)
			System.out.println(RandomName());

	}
}