package com.bksx.jzzslzd.tools;

import com.bksx.jzzslzd.common.StaticObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表单校验
 * 
 * @author Administrator
 * 
 */
public class FormCheck {
	/**
	 * 身份证校验
	 * 
	 * @param cardID
	 *            身份证号码
	 * @return String[2]
	 * @0:是否校验通过(true/false)
	 * @1:校验结果(18位身份证号码/错误信息)
	 */
	public static String[] check_Card_ID(String cardID) {
		if (cardID != null && !"".equals(cardID)) { // 不为空
			cardID = cardID.toUpperCase();
			if (cardID.length() == 15 || cardID.length() == 18) { // 必须为15位或18位
				if (cardID.length() == 15) { // 如果为15位，必须为全数字
					if (check_Num(cardID)) {
						cardID = convert_cardID_15to18(cardID); // 15位转成18位
					} else {
						return new String[] { "false", "15位身份证号码必须全部为数字" };
					}
				}
				if (check_Num(cardID.substring(0, 17))) { // 前17位必须为数字
					if (check_Num(cardID.substring(17))
							|| "X".equals(cardID.substring(17))) {
						String birthDay = cardID.substring(6, 14);
						if (is_date(birthDay)) {
							if (!regular(cardID)) {
								return new String[] { "false", "身份证号码校验未通过" };
							}
						} else {
							return new String[] { "false", "身份证号码的出生年月日不正确" };
						}

					} else {
						return new String[] { "false", "身份证号码最后一位错误" };
					}
				} else {
					return new String[] { "false", "身份证号码前17位必须全部为数字" };
				}

			} else {
				return new String[] { "false", "身份证号码必须为15位或18位" };
			}
		} else {
			return new String[] { "false", "身份证号码不能为空" };
		}
		return new String[] { "true", cardID };
	}

	/**
	 * 是否为数字(0~9)组成
	 * 
	 * @param num
	 * @return
	 */
	public static boolean check_Num(String num) {
		Pattern p = Pattern.compile("^[0-9]*$");
		Matcher m = p.matcher(num);
		return m.find();
	}

	/**
	 * 获取数据
	 * 
	 * @param arrs
	 * @param str
	 * @return
	 */
	public static int getIndexOfArray(String[] arrs, String str) {
		for (int i = 0; i < arrs.length; i++) {
			if (str.equals(arrs[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 15位身份证转18位
	 * 
	 * @param cardID
	 * @return
	 */
	private static String convert_cardID_15to18(String cardID) {
		int[] check_code = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
				5, 8, 4, 2, 1 };
		int total = 0;
		String last;
		{
			String cardID_ = cardID.substring(0, 6) + "19"
					+ cardID.substring(6, 15);
			for (int i = 0; i < cardID_.length(); i++) {
				int l_l_temp1 = Integer.parseInt(cardID_.charAt(i) + "")
						* check_code[i];
				total += l_l_temp1;
			}
			total--;
			int lastnum = total % 11;// 最后一位
			if (lastnum == 0) {
				last = "0";
			} else {
				if (lastnum == 1) {
					last = "X";
				} else {
					last = (11 - lastnum) + "";
				}
			}
			cardID_ = cardID_ + last;
			return cardID_;
		}
	}

	/**
	 * 身份证号码是否符合规则
	 * 
	 * @param cardID
	 * @return
	 */
	private static boolean regular(String cardID) {
		int[] l_l_jym = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
				8, 4, 2, 1 };
		int l_l_total = 0;
		for (int i = 0; i < cardID.length() - 1; i++) {
			l_l_total += Integer.parseInt(cardID.charAt(i) + "") * l_l_jym[i];
		}
		if (check_Num(cardID.substring(17))) {
			l_l_total += Integer.parseInt(cardID.substring(17));
		} else if ("X".equals(cardID.substring(17))) {
			l_l_total += 10;
		}
		l_l_total--;
		if (!(l_l_total % 11 == 0)) {
			return false;
		}
		return true;
	}

	/**
	 * 是否为日期
	 * 
	 * @param date
	 *            yyyyMMdd
	 * @return
	 */
	private static boolean is_date(String date) {
		int theYear = Integer.parseInt(date.substring(0, 4));
		int theMonth = Integer.parseInt(date.substring(4, 6));
		int theDay = Integer.parseInt(date.substring(6, 8));
		if (theMonth > 12) {
			return false;
		}
		if (theDay > 31) {
			return false;
		}
		switch (theMonth) {
		case 4:
		case 6:
		case 9:
		case 11:
			if (theDay == 31)
				return false;
			else
				break;
		case 2:
			if ((theYear % 4 == 0 || theYear % 100 == 0) && theYear % 400 != 0)// 润年2月份29天
			{
				if (theDay > 29)
					return false;
			} else {
				if (theDay > 28)
					return false;
			}
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 根据身份证获取性别
	 * 
	 * @param cardID
	 * @return
	 */
	public static String getSex(String cardID) {
		int sex = Integer.parseInt(cardID.substring(16, 17));
		if (sex % 2 == 0) {
			return "女";
		} else {
			return "男";
		}
	}
	/**
	 * 根据身份证获取性别
	 * 
	 * @param cardID
	 * @return
	 */
	public static String getSexCode(String cardID) {
		int sex = Integer.parseInt(cardID.substring(16, 17));
		if (sex % 2 == 0) {
			return "2";
		} else {
			return "1";
		}
	}

	/**
	 * 根据身份证获取出生日期
	 * 
	 * @param cardID
	 * @return yyyy-MM-dd
	 */
	public static String getBirthday(String cardID) {
		String birthday = cardID.substring(6, 14);
		return birthday.substring(0, 4) + "-" + birthday.substring(4, 6) + "-"
				+ birthday.substring(6);
	}

	/**
	 * 校验时间起小于时间止
	 * 
	 * @param fmt
	 *            yyyy-MM-dd之类的格式
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 开始小于结束返回true,大于结束返回false
	 */
	public static boolean check_Date(String fmt, String startDate,
			String endDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.CHINA);

			Date sd = sdf.parse(startDate);
			Date se = sdf.parse(endDate);

			if (se.before(sd)) {
				return false;
			}
		} catch (ParseException e) {
			StaticObject.print("时间校验出错");
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * 判断当前年龄是不是大于18岁
	 * 
	 * @param sfzh
	 * @return 如果小于18岁，返回true
	 */
	public static boolean age_before(String sfzh) {
		try {
			if (sfzh == null || sfzh.length() != 18) {
				return false;
			}
			String year = (Integer.parseInt(sfzh.substring(6, 10)) + 18) + "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",
					Locale.CHINA);
			Date now = new Date();

			Date sd = sdf.parse(year + sfzh.substring(10, 14));

			return now.before(sd);
		} catch (ParseException e) {
			StaticObject.print("时间校验出错");
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * yyyy-MM-dd转成yyyyMMdd
	 * 
	 * @param d
	 * @return
	 */
	public static String getDate(String d) {
		if (d != null && d.length() >= 8 && d.contains("-")) {
			String[] stime = d.split("-");
			if (stime[1].length() == 1) {
				stime[1] = "0" + stime[1];
			}
			if (stime[2].length() == 1) {
				stime[2] = "0" + stime[2];
			}
			return stime[0] + stime[1] + stime[2];
		}
		return "";
	}

	/**
	 * yyyyMMdd转成yyyy-MM-dd
	 * 
	 * @param d
	 * @return
	 */
	public static String getDate1(String d) {
		if (d != null && d.length() >= 8) {
			return d.substring(0, 4) + "-" + d.substring(4, 6) + "-"
					+ d.substring(6, 8);
		}
		return "";
	}

	/**
	 * 字符超长变...
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	public static String subString(String s, int length) {
		if (s != null && s.length() > length) {
			return s.substring(0, length) + "...";
		}
		if (s != null) {
			return s;
		}
		return "";
	}
}
