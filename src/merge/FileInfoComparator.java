package merge;

import java.util.Comparator;

/**
 * 
 * �ļ��Ƚ���
 * 
 * @author jia.hej
 * 
 * @version $Id: FileInfoComparator.java, v 0.1 2009-8-7 ����01:42:05 jia.hej Exp
 *          $
 */
public class FileInfoComparator implements Comparator<FileInfo>
{

	public int compare(FileInfo o1, FileInfo o2)
	{
		if (Integer.parseInt(o1.getValue()) != Integer.parseInt(o2.getValue()))
		{
			return Integer.parseInt(o1.getValue())
					- Integer.parseInt(o2.getValue());
		}
		// ��������ظ�ֵ��ʹ���ļ��űȽ�
		else
		{
			return o1.getFileNum() - o2.getFileNum();
		}
	}

}
