package algorithms.extsort;

public class TestChinese
{

	public static String bytes2HexString(byte b)
	{
		return bytes2HexString(new byte[] { b });
	}

	// ����ת������λ��
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

	// ����ת������λ��
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

	// ��λ��ת���ɺ���
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

	// ���ָ����Χ�������
	// @param min ��Сֵ
	// @param max ���ֵ
	// @return ����ֵ
	public static int pickRandom(int min, int max)
	{
		return (int) (Math.random() * (max - min + 1) + min);
	}
	

	// ����: ���������λ - 0xA0 λ��: ���������λ - 0xa0
	// ���ţ�01��87�����ִ�16����ʼ,ǰ15��Ϊ���ַ���
	// λ�ţ�01��94
	// ��һ�����֡�����������Ϊ16��λ��Ϊ01 ����
	// ���ֽ����룺����+0xA0 , λ��+0xa0
	// �磺��һ�����֡�����������Ϊ16��λ��Ϊ01����������Ϊ 0xb0,0xa1
	// ======> ���ţ�16---87 λ�ţ�01---94
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

	// ����
	public static void main(String[] args) throws Exception
	{
		String str = "��";
		String s = TestChinese.getString(str);
		System.out.println(s);
		String a = TestChinese.CodeToChinese(s);
		System.out.println(a);
		a = TestChinese.CodeToChinese("1601");
		System.out.println(a);
		// ������������ 2~4 ������
		// ͬ�ټ����ǶԲ��ϵģ����Ҫ��������������Ҫ�����յ�����
		for (int i = 0; i < 100; i++)
			System.out.println(RandomName());

	}
}