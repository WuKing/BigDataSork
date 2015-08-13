/**
 * 
 */
package algorithms.extsort;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;
import algorithms.extsort.TestChinese;
/**
 * @author Jackey
 *
 */
public class CreateFile
{

	private String name;
	private int num;
	/**
	 * @param args
	 */
	final String COUNTRY_CH[] = { "���������ǹ��͹�", "�����������������񹲺͹�", "��������˹����",
			"����͢���͹�", "��������������", "��³��", "�����յ���", "�����ݽ����͹�", "�������������͹�",
			"���������", "������", "��ɳ���ǹ��͹�", "����������", "���������͹�", "������", "����ϺͰͲ���",
			"�µ������͹�", "�Ĵ���������", "����", "�ͰͶ�˹", "�Ͳ����¼����Ƕ�����", "�͹�������",
			"�ͻ�˹̹��˹�����͹�", "�����繲�͹�", "����˹̹��", "���ֹ�", "�������͹�", "��������͹�",
			"�׶���˹���͹�", "��Ľ��Ⱥ��", "�������ǹ��͹�", "������������������", "���͹��͹�", "�������͹�",
			"����ʱ����", "�������͹�", "���������������", "�������͹�", "����ά�ǹ��͹�", "��˹���Ǻͺ�����ά�ǹ��͹�",
			"�������ɹ��͹�", "������", "��������", "�����ɷ���", "��¡�Ϲ��͹�", "��ά��", "���������������񹲺͹�",
			"��������ǹ��͹�", "��������", "����־����͹�", "������", "��繲�͹�", "������ӹ��͹�",
			"�����������", "����˹����", "��϶�����͹�", "���������ǹ�", "���������͹�", "����Ⱥ��",
			"��������������", "����������", "�����ϲ�����", "��ٸԳǹ�", "���ɱ����͹�", "쳼ù��͹�", "�������͹�",
			"��ýǹ��͹�", "�Ա��ǹ��͹�", "�չ����͹�", "���ױ��ǹ��͹�", "��˹����ӹ��͹�", "�����ɴ�", "������",
			"��³���ǹ��͹�", "�Ű͹��͹�", "�ϵ�����", "�ص�", "�����Ǻ������͹�", "������˹̹���͹�", "���ع��͹�",
			"�����", "��������", "����������˹", "�յµ���������ɵ�", "�鶼��˹���͹�", "�����˹���͹�",
			"�����Ṳ�͹�", "������˹���͹�", "�����ǹ��͹�", "�����Ǳ��ܹ��͹�", "���ô�", "���ɹ��͹�", "����͹�",
			"����կ����", "�ݿ˹��͹�", "��Ͳ�Τ���͹�", "����¡���͹�", "��������", "����Ⱥ��", "�ƿ�˹(����)Ⱥ��",
			"��Ħ����˹������͹�", "���ص��߹��͹�", "�����ع�", "���޵��ǹ��͹�", "�����ǹ��͹�", "���Ⱥ��",
			"����ά�ǹ��͹�", "����������", "���������������͹�", "����۹��͹�", "�������ǹ��͹�",
			"��������������������������ڹ�", "�����𹲺͹�", "��֧��ʿ�ǹ���", "������", "¬ɭ���󹫹�", "¬���ﹲ�͹�",
			"��������", "����˹�ӹ��͹�", "��������͹�", "������򹲺͹�", "���ά��˹Ⱥ��(������Ⱥ��)",
			"����ά���͹�", "��������", "���ﹲ�͹�", "����ٹ��͹�", "���ܶ�Ⱥ�����͹�", "�������", "��Լ��",
			"ë����˹���͹�", "ë����˹���͹�", "��������ڹ�", "������Ħ��", "����ά����Ⱥ��", "�ɹŹ�", "����������",
			"�ϼ������񹲺͹�", "��³���͹�", "�ܿ�������������", "�������", "Ħ�����߹��͹�", "Ħ�������",
			"Ħ�ɸ繫��", "Īɣ�ȿ˹��͹�", "ī������ڹ�", "���ױ��ǹ��͹�", "�Ϸǹ��͹�", "�ϼ���",
			"�������ǵ�����ɣ��Τ�浺", "��˹�������˹��͹�", "�³���͹�", "�Ჴ������", "������Ϲ��͹�", "���ն����͹�",
			"������������͹�", "Ŧ��", "Ų������", "ŵ���˵�", "Ƥ�ÿ���Ⱥ��", "���������͹�", "�ձ���",
			"�������", "��ʿ����", "�����߶๲�͹�", "�����������͹�", "���ڼӶ����͹�", "����·˹���͹�",
			"��������͹�", "ɳ�ذ���������", "ʥ����", "ʥ���������������������͹�", "�Ժ�����", "ʥ��ĺ���ά˹����",
			"ʥ¬����", "ʥ����ŵ���͹�", "ʥƤ�������ܿ�¡", "ʥ��ɭ�غ͸����ɶ�˹", "˹����������������干�͹�",
			"˹�工�˹��͹�", "˹�������ǹ��͹�", "˹�߶���Ⱥ��", "˹��ʿ������", "�յ����͹�", "�����Ϲ��͹�",
			"�����ﹲ�͹�", "������Ⱥ��", "������˹̹���͹�", "̩����", "̹ɣ�������Ϲ��͹�", "��������",
			"�ؿ�˹�Ϳ���˹Ⱥ��", "�������Ͷ�͸繲�͹�", "ͻ��˹���͹�", "ͼ��¬", "�����乲�͹�", "������˹̹",
			"�п���", "����˹�͸�ͼ��Ⱥ��", "��Ŭ��ͼ���͹�", "Σ���������͹�", "ί���������͹�", "������³������",
			"�ڸɴﹲ�͹�", "�ڿ���", "�����綫�����͹�", "���ȱ��˹̹���͹�", "������", "��������", "����Ħ�Ƕ�����",
			"ϣ�����͹�", "���", "�¼��¹��͹�", "�¿��������", "������", "���������͹�", "�����������ǹ��͹�",
			"�����", "�������ǹ��͹�", "Ҳ�Ź��͹�", "�����˹��͹�", "������˹�����͹�", "��ɫ�й�", "��������͹�",
			"ӡ�ȹ��͹�", "ӡ�������ǹ��͹�", "���е߼�����������������", "Ӣ��ά����Ⱥ��", "Ӣ��ӡ��������",
			"Լ����ϣķ����", "Խ�������ϯ���͹�", "�ޱ��ǹ��͹�", "���������͹�", "է�ù��͹�", "ֱ������",
			"�������͹�", "�зǹ��͹�", "�л����񹲺͹�" };
	final String COUNTRY_EN[] = { "Republic of Albania",
			"Democratic People's Republic of Algeria",
			"Islamic State of Afghanistan", "Republic of Argentina",
			"United Arab Emirates", "Aruba", "Sultanate of Oman",
			"Republic of Azerbaijan", "Arab Republic of Egypt", "Ethiopia",
			"Ireland", "Republic of Estonia", "Principality of Andorra",
			"Republic of Angola", "Anguilla", "Antigua and Barbuda",
			"Republic of Austria", "Commonwealth of Australia", "Macau",
			"Barbados", "Independent State of Papua New Guinea",
			"Commonwealth of the Bahamas", "Islamic Republic of Pakistan",
			"Republic of Paraguay", "State of Palestine", "State of Bahrain",
			"Republic of Panama", "Federative Republic of Brazil",
			"Republic of Belarus", "Bermuda Islands", "Republic ov Bulgaria",
			"Commonwealth of the Northern Marianas", "Republic of Palau",
			"Republic of Benin", "Kingdom of belgium", "Republic of Iceland",
			"Commonwealth of Puerto Rico", "Republic of Poland",
			"Republic of Bolivia", "Republic of Bosnia and Herzegovina",
			"Republic of Botswana", "Belize", "Kingdom of Bhutan",
			"Burkina Faso", "Republic of Burundi", "Bouvet Island",
			"Democratic People's Republic of Ko-rea",
			"Republic of Equatorial Guinea", "Kingdom of Denmark",
			"Federal Republic of Germany", "East Timor", "Republic of Tago",
			"Dominican Republic", "Commonwealth of Dominica",
			"Russian Federation", "Republic of Ecuador", "State of Eritrea",
			"French Republic", "Faroe Islands", "French Polynesia",
			"French Guiana", "French Southern Territories",
			"Vatican City State", "Republic of the Philippines",
			"Republic of Fiji", "Republic of Finland",
			"Republic of Cape Verde", "Republic of Gambia",
			"Republic of Congo", "Republic of Colombia",
			"Republic of Costa Rica", "Grenada", "Greenland",
			"Republic of Georgia", "Republic of Cuba", "Guadeloupe", "Guam",
			"Cooperative Republic of Guyana", "Republic of Kazakhstan",
			"Republic of Haiti", "Republic of Korea",
			"Kingdom of the Netherlands", "Netherlands Antilles",
			"Heard islands and Mc Donald Islands", "Republic of honduras",
			"Republic of Kiribati", "Republic of Djibouti", "Kyrgyz Republic",
			"Republic of Guinea", "Republic of Guine-bissau", "Canada",
			"Republic of Ghana", "Gabonese Republic", "Kingdom of Cambodia",
			"Czech Republic", "Republic of Zimbabwe", "Republic of Cameroon",
			"State of Qatar", "Cayman Islands", "Cocos(Keeling) Islands",
			"Federal Islamic Republic of the Co-moros",
			"Republic of Cote d'Ivire", "State of Kuwait",
			"Republic of Croatia", "Republic of Kenya", "Cook Islands",
			"Republic of Latvia", "Kingdom of Lesoto",
			"Lao People's Democratic Republic", "Republic of Lebanon",
			"Republic of Liberia",
			"Great Socialist People's Libyan Arab jamahiriya",
			"Republic of Lithuania", "Principality of Liechtenstein",
			"Reunion", "Grand Duchy of Luxembourg", "Republic of Rwanda",
			"Romania", "Republic of Madagascar", "Republic of Malta",
			"Republic of maldives", "Malvinas islands (Falkland Islands)",
			"Republic of Malawi", "Malaysia", "Republic of Mali",
			"Republic of Macedonia", "Republic of the marshall Islands",
			"Martinique", "Mayotte", "Republic of Mauritius",
			"Republic of Mauritius", "United States of America",
			"American Samoa ", "United States Virgin Islands", "Mongolia",
			"Montserrat", "People's Republic of Bangladesh",
			"Republic of Peru", "Federated States of Micronesia",
			"Union of Myanmar", "Republic of Moldova", "Kingdom of Morocco",
			"Principality of Monaco", "Republic of Mozambique",
			"United States of Mexico", "Republic of Namibia",
			"Republic of South Africa", "Antarctica",
			"South Georgia and South Sandwich Islands",
			"Federal Republic of Yugoslavia", "Republic of Nauru",
			"Kingdom of Nepal", "Republic of Nicaragua", "Republic of Niger",
			"Federal Republic of Nigeria", "Niue", "Kingdom of Norway",
			"Norfolk Island", "Pitcairn Islands Group", "Pirtuguese Republic",
			"Japan", "Kingdom of Sweden", "Swiss Confederation",
			"Republic of El Salvador", "Republic of Sierra Leone",
			"Republic of Senegal", "Republic of Cyprus",
			"Republic of Seychelles", "Kingdom of Saudi Arabia",
			"Christmas Island", "Democratic Republic of Sao Tome and Principe",
			"Saint Helena", "Federation of Saint Kitts and nevis",
			"Saint Lucia", "Republic of San Marino",
			"Saint Pierre and Miquelon", "Saint Vincent and the Grenadines",
			"Democratic Socialist Republic of Srilanka", "Slovak Republic",
			"Republic of Slovenia", "Svalbard and Jan mayen islands",
			"Kingdom of Swaziland", "Republic of the Sudan",
			"Republic of Suriname", "Somali Republic", "Solomon Islands",
			"Republic of Tajikistan", "Kingdom of Thailand",
			"United Republic of Tanzania", "Kingdom of Tonga",
			"Turks and Caicos Islands", "Republic of Trinidad and Tobago",
			"Republic of Tunisia", "Tuvalu", "Republic of Turkey",
			"Turkmenistan", "Tokelau", "Wallis and Futuna Islands",
			"Republic of Vanuatu", "Republic of Guatemala",
			"Republic of Venezuela", "Brunei Darussalam", "Republic of Uganda",
			"Ukraine", "Oriental Republic of Uruguay",
			"Republic of Uzbekistan", "Spain", "Western Sahara",
			"Independent State of Western Samoa", "Hellenic Republic",
			"Hong Kong", "Republic of Singapore", "New Caledonia",
			"New Zealand", "Republic of Hungary", "Syrian Arab Republic",
			"Jamaica", "Republic of Armenia", "Republic of Yemen",
			"Republic of Iraq", "Islamic Rupublic of Iran", "State of Israel",
			"Republic of Italy", "Republic of India", "Republic of Indonesia",
			"United Kingdom of Great Britain and Northern ireland",
			"British Virgin Islands", "British Indian Ocean Territory",
			"Hashemite Kingdom of Jordan", "Socialist Republic of Viet Nam",
			"Republic of Zambia", "Republic of Zaire", "Republic of Chad",
			"Gibraltar", "Republic of Chile", "Central African Republic",
			"People's Republic of Chi" };
	final String FIRST_NAME[] = { "��", "Ǯ", "��", "��", "��", "��", "֣", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"ʩ", "��", "��", "��", "��", "��", "��", "κ", "��", "��", "��", "л", "��",
			"��", "��", "ˮ", "�", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"³", "Τ", "��", "��", "��", "��", "��", "��", "��", "��", "Ԭ", "��", "ۺ",
			"��", "ʷ", "��", "��", "��", "�", "Ѧ", "��", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "ʱ", "��", "Ƥ", "��", "��",
			"��", "��", "��", "Ԫ", "��", "��", "��", "ƽ", "��", "��", "��", "��", "��",
			"Ҧ", "��", "տ", "��", "��", "ë", "��", "��", "��", "��", "��", "�", "��",
			"��", "��", "��", "̸", "��", "é", "��", "��", "��", "��", "��", "��", "ף",
			"��", "��", "��", "��", "��", "��", "ϯ", "��", "��", "ǿ", "��", "·", "¦",
			"Σ", "��", "ͯ", "��", "��", "÷", "ʢ", "��", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "֧", "��", "��",
			"��", "¬", "Ī", "��", "��", "��", "��", "��", "��", "Ӧ", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "ʯ", "��", "��", "ť",
			"��", "��", "��", "��", "��", "��", "½", "��", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "ɽ", "��", "��", "��",
			"�", "��", "ȫ", "ۭ", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ղ", "��", "��",
			"Ҷ", "��", "˾", "��", "۬", "��", "��", "��", "ӡ", "��", "��", "��", "��",
			"ۢ", "��", "��", "��", "��", "��", "��", "׿", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "˫", "��", "ݷ", "��", "��", "̷", "��", "��",
			"��", "��", "��", "��", "��", "Ƚ", "��", "۪", "Ӻ", "ȴ", "�", "ɣ", "��",
			"�", "ţ", "��", "ͨ", "��", "��", "��", "��", "��", "��", "ũ", "��", "��",
			"ׯ", "��", "��", "��", "��", "��", "Ľ", "��", "��", "ϰ", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "»", "��", "��", "ŷ",
			"�", "��", "��", "ε", "Խ", "��", "¡", "ʦ", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "ɳ",
			"ؿ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"��", "ۣ", "��", "Ȩ", "��", "��", "��", "��", "��", "��", "��", "��", "˧",
			"��", "��", "��", "�C", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"۳", "Ϳ", "��", "��", "Ĳ", "��", "٦", "��", "��", "ī", "��", "��", "��",
			"��", "��", "��", "١", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"��", "չ", "��", "̴", "��", "��", "��", "��", "˴", "¥", "��", "ð", "��",
			"ֿ", "��", "��", "��", "��", "ԭ", "��", "��", "��", "��", "��", "�", "��",
			"��", "��", "�", "��", "��", "��", "��", "��", "��", "��", "ľ", "��", "��",
			"ۨ", "��", "ö", "��", "��", "�", "��", "��", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"�G", "��ٹ", "˾��", "�Ϲ�", "ŷ��", "�ĺ�", "���", "����", "����", "����", "�ʸ�",
			"����", "ξ��", "����", "�̨", "��ұ", "����", "���", "����", "����", "̫��", "����",
			"����", "����", "��ԯ", "���", "����", "����", "����", "Ľ��", "����", "����", "˾ͽ",
			"˾��", "أ��", "˾��", "����", "����", "�ӳ�", "���", "��ľ", "����", "����", "���",
			"����", "����", "����", "�ذ�", "�й�", "�׸�", "����", "�θ�", "����", "����", "΢��",
			"����", "����", "����", "����", "�Ϲ�", "����", "����", "����", "̫ʷ", "�ٳ�", "����",
			"��ͻ", "����", "����", "����", "��ĸ", "˾��", "����", "Ӻ��", "����", "����", "����",
			"��®", "����", "�Ϲ�", "����", "����" };

	public static int pickRandom(int min, int max)
	{
		return (int) (Math.random() * (max - min + 1) + min);
	}

	public String CreateInt()
	{
		// 2147483647
		return  pickRandom(-Integer.MAX_VALUE/100, Integer.MAX_VALUE/100) + "";
	}

	public String CreateFloat()
	{
		return (pickRandom(0,1) == 0 ? "-" : "")
				+ String.format("%.4f", (Math.random() * 10000));
	}

	public String CreateName()
	{
		this.name ="";
		
		
		if(pickRandom(1,999999)==1)
		{
			return "NULL";
		}
		
		
		
		//517
		if(pickRandom(1,100)!=1)
		{
			num = pickRandom(0,516);
		}
		else
		{
			num = pickRandom(517,this.FIRST_NAME.length-1);
		}
		
		if(num<517)
		{
			if(pickRandom(1,30)!=1)
			{
				return this.FIRST_NAME[num] + TestChinese.RandomName() + TestChinese.RandomName();
			}
			else
			{
				return this.FIRST_NAME[num] + TestChinese.RandomName();
			}
		}
		else
		{

			if(pickRandom(1,30)==1)
			{
				return this.FIRST_NAME[num] + TestChinese.RandomName() + TestChinese.RandomName();
			}
			else
			{
				return this.FIRST_NAME[num] + TestChinese.RandomName();
			}
		}
	}
	
	
	public String CreateCountry()
	{
		return  CreateCountry(",");
	}
	// @param str �ָ���
	// @return ����ֵ
	public String CreateCountry(String str)
	{
		num = pickRandom(0, (this.COUNTRY_CH).length -1);
		return this.COUNTRY_CH[num] + str + this.COUNTRY_EN[num];
	}
	
	public String CreateDateBase()
	{
		return CreateDateBase(",");
	}
	
	public String CreateDateBase(String str)
	{
		return 1 + str + this.CreateFloat() + str + this.CreateName() + str + this.CreateCountry();
	}
	
	public static void main(String[] args) throws Exception
	{

		CreateFile cf = new CreateFile();
		//test_sort
		OutputStream out = new FileOutputStream("unsort.txt");
		PrintStream ps = new PrintStream(new BufferedOutputStream(out,12 * 1024));

		Random r = new Random();

		// for (int i = 0; i < 100000000; i++)//5.5G
		for (int i = 0; i < 1000; i++)//5.5G
		{
			//ps.println(r.nextInt(10000000) + "             this  a line line aaaaaaaaa!");
			ps.println(cf.CreateDateBase());
			//System.out.println(cf.CreateDateBase());
		}
		ps.close();
		System.out.println("Create Succeed");
	}

}
