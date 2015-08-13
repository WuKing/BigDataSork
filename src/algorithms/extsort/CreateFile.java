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
	final String COUNTRY_CH[] = { "阿尔马尼亚共和国", "阿尔及利亚民主人民共和国", "阿富汗伊斯兰国",
			"阿根廷共和国", "拉伯联合酋长国", "阿鲁巴", "阿曼苏丹国", "阿塞拜疆共和国", "阿拉伯埃及共和国",
			"埃塞俄比亚", "爱尔兰", "爱沙尼亚共和国", "安道尔公国", "安哥拉共和国", "安圭拉", "安提瓜和巴布达",
			"奥地利共和国", "澳大利亚联邦", "澳门", "巴巴多斯", "巴布亚新几内亚独立国", "巴哈马联邦",
			"巴基斯坦伊斯兰共和国", "巴拉圭共和国", "巴勒斯坦国", "巴林国", "巴拿马共和国", "巴西联邦共和国",
			"白俄罗斯共和国", "百慕大群岛", "保加利亚共和国", "北马里亚纳自由联邦", "贝劳共和国", "贝宁共和国",
			"比利时王国", "冰岛共和国", "波多黎各自由联邦", "波兰共和国", "玻利维亚共和国", "波斯尼亚和黑塞哥维那共和国",
			"博茨瓦纳共和国", "伯利兹", "不丹王国", "布基纳法索", "布隆迪共和国", "布维岛", "朝鲜民主主义人民共和国",
			"赤道几内亚共和国", "丹麦王国", "德意志联邦共和国", "东帝汶", "多哥共和国", "多米尼加共和国",
			"多米尼克联邦", "俄罗斯联邦", "厄瓜多尔共和国", "厄立特里亚国", "法兰西共和国", "法罗群岛",
			"法属波利尼西亚", "法属圭亚那", "法属南部领土", "梵蒂冈城国", "菲律宾共和国", "斐济共和国", "芬兰共和国",
			"佛得角共和国", "冈比亚共和国", "刚果共和国", "哥伦比亚共和国", "哥斯达黎加共和国", "格林纳达", "格陵兰",
			"格鲁吉亚共和国", "古巴共和国", "瓜德罗普", "关岛", "圭亚那合作共和国", "哈萨克斯坦共和国", "海地共和国",
			"大韩民国", "荷兰王国", "荷属安的列斯", "赫德岛和麦克唐纳岛", "洪都拉斯共和国", "基里巴斯共和国",
			"吉布提共和国", "吉尔吉斯共和国", "几内亚共和国", "几内亚比绍共和国", "加拿大", "加纳共和国", "加蓬共和国",
			"柬埔寨王国", "捷克共和国", "津巴布韦共和国", "喀麦隆共和国", "卡塔尔国", "开曼群岛", "科科斯(基林)群岛",
			"科摩罗伊斯兰联邦共和国", "科特迪瓦共和国", "科威特国", "克罗地亚共和国", "肯尼亚共和国", "库克群岛",
			"拉脱维亚共和国", "莱索托王国", "老挝人民民主共和国", "黎巴嫩共和国", "利比里亚共和国",
			"大阿拉伯利比亚人民社会主义民众国", "立陶宛共和国", "列支敦士登公国", "留尼汪", "卢森堡大公国", "卢旺达共和国",
			"罗马尼亚", "马达加斯加共和国", "马耳他共和国", "马尔代夫共和国", "马尔维纳斯群岛(福克兰群岛)",
			"马拉维共和国", "马来西亚", "马里共和国", "马其顿共和国", "马绍尔群岛共和国", "马提尼克", "马约特",
			"毛里求斯共和国", "毛里求斯共和国", "美利坚合众国", "美属萨摩亚", "美属维尔京群岛", "蒙古国", "蒙特塞拉特",
			"孟加拉人民共和国", "秘鲁共和国", "密克罗尼西亚联邦", "缅甸联邦", "摩尔多瓦共和国", "摩洛哥王国",
			"摩纳哥公国", "莫桑比克共和国", "墨西哥合众国", "纳米比亚共和国", "南非共和国", "南极洲",
			"南乔治亚岛和南桑德韦奇岛", "南斯拉夫联盟共和国", "瑙鲁共和国", "尼泊尔王国", "尼加拉瓜共和国", "尼日尔共和国",
			"尼日利亚联邦共和国", "纽埃", "挪威王国", "诺福克岛", "皮竺凯恩群岛", "葡萄牙共和国", "日本国",
			"瑞典王国", "瑞士联邦", "萨尔瓦多共和国", "塞拉利昂共和国", "塞内加尔共和国", "塞浦路斯共和国",
			"塞舌尔共和国", "沙特阿拉伯王国", "圣诞岛", "圣多美和普林西比民主共和国", "对赫勒拿", "圣革茨和尼维斯联邦",
			"圣卢西亚", "圣马力诺共和国", "圣皮埃尔和密克隆", "圣文森特和格林纳丁斯", "斯里兰卡民主社会主义共和国",
			"斯洛伐克共和国", "斯洛文尼亚共和国", "斯瓦尔巴群岛", "斯威士兰王国", "苏丹共和国", "苏里南共和国",
			"索马里共和国", "年罗门群岛", "塔吉克斯坦共和国", "泰王国", "坦桑尼亚联合共和国", "汤加王国",
			"特克斯和凯科斯群岛", "特立尼达和多巴哥共和国", "突尼斯共和国", "图瓦卢", "土耳其共和国", "土库曼斯坦",
			"托克劳", "瓦利斯和富图纳群岛", "瓦努阿图共和国", "危地马拉共和国", "委内瑞拉共和国", "文莱达鲁萨兰国",
			"乌干达共和国", "乌克兰", "乌拉圭东岸共和国", "乌兹别克斯坦共和国", "西班牙", "西撒哈拉", "西萨摩亚独立国",
			"希腊共和国", "香港", "新加坡共和国", "新喀里多尼亚", "新西兰", "匈牙利共和国", "阿拉伯叙利亚共和国",
			"牙买加", "亚美尼亚共和国", "也门共和国", "伊拉克共和国", "伊朗伊斯兰共和国", "以色列国", "意大利共和国",
			"印度共和国", "印度尼西亚共和国", "大不列颠及北爱尔兰联合王国", "英属维尔京群岛", "英属印度洋领土",
			"约旦哈希姆王国", "越南社会主席共和国", "赞比亚共和国", "扎伊尔共和国", "乍得共和国", "直布罗陀",
			"智利共和国", "中非共和国", "中华人民共和国" };
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
	final String FIRST_NAME[] = { "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯",
			"陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许", "何", "吕",
			"施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹",
			"喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
			"鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆",
			"鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
			"罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐",
			"康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧", "尹",
			"姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计",
			"伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒", "屈", "项", "祝",
			"董", "梁", "杜", "阮", "蓝", "闵", "席", "季", "麻", "强", "贾", "路", "娄",
			"危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁", "钟", "徐", "邱", "骆",
			"高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝",
			"管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗", "丁", "宣",
			"贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉", "钮",
			"龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀", "羊", "于", "惠",
			"甄", "曲", "家", "封", "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井",
			"段", "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗", "山", "谷", "车", "侯",
			"宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫", "宁", "仇", "栾",
			"暴", "甘", "钭", "厉", "戎", "祖", "武", "符", "刘", "景", "詹", "束", "龙",
			"叶", "幸", "司", "韶", "郜", "黎", "蓟", "溥", "印", "宿", "白", "怀", "蒲",
			"邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠", "蒙", "池", "乔",
			"阴", "郁", "胥", "能", "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳",
			"逄", "姬", "申", "扶", "堵", "冉", "宰", "郦", "雍", "却", "璩", "桑", "桂",
			"濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "浦", "尚", "农", "温", "别",
			"庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习", "宦", "艾", "鱼",
			"容", "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居", "衡", "步",
			"都", "耿", "满", "弘", "匡", "国", "文", "寇", "广", "禄", "阙", "东", "欧",
			"殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩", "厍", "聂", "晁", "勾",
			"敖", "融", "冷", "訾", "辛", "阚", "那", "简", "饶", "空", "曾", "毋", "沙",
			"乜", "养", "鞠", "须", "丰", "巢", "关", "蒯", "相", "查", "后", "荆", "红",
			"游", "郏", "竺", "权", "逯", "盖", "益", "桓", "公", "仉", "督", "岳", "帅",
			"缑", "亢", "况", "郈", "有", "琴", "归", "海", "晋", "楚", "闫", "法", "汝",
			"鄢", "涂", "钦", "商", "牟", "佘", "佴", "伯", "赏", "墨", "哈", "谯", "篁",
			"年", "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官", "冼",
			"真", "展", "繁", "檀", "祭", "密", "敬", "揭", "舜", "楼", "疏", "冒", "浑",
			"挚", "胶", "随", "高", "皋", "原", "种", "练", "弥", "仓", "眭", "蹇", "覃",
			"阿", "门", "恽", "来", "綦", "召", "仪", "风", "介", "巨", "木", "京", "狐",
			"郇", "虎", "枚", "抗", "达", "杞", "苌", "折", "麦", "庆", "过", "竹", "端",
			"鲜", "皇", "亓", "老", "是", "秘", "畅", "邝", "还", "宾", "闾", "辜", "纵",
			"侴", "万俟", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "赫连", "皇甫",
			"羊舌", "尉迟", "公羊", "澹台", "公冶", "宗正", "濮阳", "淳于", "单于", "太叔", "申屠",
			"公孙", "仲孙", "轩辕", "令狐", "钟离", "宇文", "长孙", "慕容", "鲜于", "闾丘", "司徒",
			"司空", "兀官", "司寇", "南门", "呼延", "子车", "颛孙", "端木", "巫马", "公西", "漆雕",
			"车正", "壤驷", "公良", "拓跋", "夹谷", "宰父", "谷梁", "段干", "百里", "东郭", "微生",
			"梁丘", "左丘", "东门", "西门", "南宫", "第五", "公仪", "公乘", "太史", "仲长", "叔孙",
			"屈突", "尔朱", "东乡", "相里", "胡母", "司城", "张廖", "雍门", "毋丘", "贺兰", "綦毋",
			"屋庐", "独孤", "南郭", "北宫", "王孙" };

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
	// @param str 分隔符
	// @return 返回值
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
