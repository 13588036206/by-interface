package com.youzan.youzanUtil;
import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA. User: chengang Date: 2007-2-2 Time: 9:37:39 To
 * change this template use File | Settings | File Templates.
 */
public class StringUtil {
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() <= 0;
	}

	public static void appendOrderBy(StringBuffer sb, String orderBy) {
		if (StringUtil.isEmpty(orderBy))
			return;
		if (sb.indexOf(" order by") != -1) {
			int orderInx = sb.indexOf(" order by");
			String orderStr = sb.substring(orderInx, sb.length());
			if (orderStr.indexOf(orderBy) != -1)
				return;
			sb.append(" ," + orderBy);
		} else {
			sb.append(" order by " + orderBy);
		}
	}


	public static String getSfFormatJe(double insureje) {
		DecimalFormat df=new DecimalFormat("#.00");
		String sfinsureje = df.format(insureje);
		return sfinsureje;
	}


	public static String getJsonString(String name) {
		name = getNullStr(name).replace(",", "\\,");
		return name;
	}

	public static boolean isIn(String qxckDm, Long ckdm) {
		String[] cks = qxckDm.split(",");
		for (int i = 0; i < cks.length; i++) {
			if (Long.parseLong(cks[i]) == ckdm.longValue()) {
				return true;
			}
		}

		return false;
	}

	public static String getUploadPath() {
		String result = StringUtil.class.getResource("").toString();
		result=result.substring(6);
		result = result.substring(0, result.indexOf("WEB-INF/classes/"));
		return result + "upload";
	}

	public static String getNullStr(String s) {
		if (s == null)
			return "";
		return s;
	}


	//验证金额
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^[-|+]?\\d*([.]\\d{0,2})?$");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}



	public static String getReverse(String s) {
		String ret = "";
		for (int i = s.length() - 1; i >= 0; i--) {
			ret += s.charAt(i);
		}

		return ret;
	}

	public static String getNullString(Object obj) {
		String s = String.valueOf(obj);
		if (s == null)
			return "";
		if (s.equals("null"))
			return "0";
		// 处理特殊字符
		s = getSafeString(s).replaceAll("\\.", "");
		return s;
	}


	//zx 1025-12-10   期初导入时商品分类中间的点.不能去掉   要以此来区分上下级关系
	public static String getNullString_qcdr(Object obj) {
		String s = String.valueOf(obj);
		if (s == null)
			return "";
		if (s.equals("null"))
			return "0";
		// 处理特殊字符
		s = getSafeString(s);
		return s;
	}

	public static String getDoubleString(Object obj) {
		String s = String.valueOf(obj);
		s = getSafeString(s);
		try {
			s = Double.valueOf(s).toString();
		} catch (Exception e) {
			return "0";
		}
		return s;
	}


	public static String getDoubleStringQc(Object obj) {
		String s = String.valueOf(obj);
		s = getSafeString(s);
		try {
			s = Double.valueOf(s).toString();
		} catch (Exception e) {
			return "1";
		}
		return s;
	}

	public static String getIntegerString(Object obj) {
		String s = String.valueOf(obj);
		s = getSafeString(s);
		try {
			s = String.valueOf(Double.valueOf(s).intValue());
		} catch (Exception e) {
			return "0";
		}
		return s;
	}

	public static String getSafeString(String s) {
		if (s == null)
			return "";

		s = s.trim();
		s = s.replaceAll(" ", " ");
		s = s.replaceAll("—", "-");
		s = s.replaceAll("/", "-");
		s = s.replaceAll("'", "’");
		s = s.replaceAll("\"", "“");
		s = s.replaceAll("，", "_");
		s = s.replaceAll(",", "_");

		// 成对符号的替换
		s = s.replaceAll("\\[\\{\\<", "(");
		s = s.replaceAll("\\]\\}\\>,", ")");

		// 去掉:空白符 ! @ # $ ^ & : ; , . ? = ' " < > [ ] { } < > / | \
		String reg = "!|@|#|\\$|\\^|&|:|;|,|\\?|=|'|\"|<|>|\\[|\\]|\\{|\\}|\\<|\\>|\\/|\\||\\\\";
		s = s.replaceAll(reg, "");
		s = s.replaceAll("\\s{1,}", " ");
		s = s.replaceAll("(^\\s{1,})|(\\s{1,}$)", "");
		s = getPairsParentheses(s);
		return s;
	}

	/**
	 * 验证传入的字符串是否有成对的括弧，如果没有成对，自动补全
	 * @param s
	 * @return
	 */
	public static String getPairsParentheses(String s) {
		int _lcount = 0, _rcount = 0;
		String pairs = "(((((((((())))))))))";

		for (int c : s.toCharArray()) {
			switch (c) {
				case (int) '(':
					_lcount++;
					break;
				case (int) ')':
					_rcount++;
					break;
				default :
					break;
			}
		}
		int j = (_lcount - _rcount);
		if (j > 0) {
			s = s + pairs.substring(10, 10 + j);
		} else if (j < 0) {
			s = pairs.substring(10 + j, 10) + s;
		}

		return s;
	}

	public static String getDecodeParamFromReq(String paramName) {
		String retval = paramName;

		try {
			// 中间用ISO-8859-1过渡
			byte[] b = retval.getBytes("8859_1");

			// 转换成GB2312字符
			retval = new String(b, "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retval;
	}

	/**
	 * 手机号码验证
	 * @param expression
	 * @param text
	 * @return
	 */
	public static boolean matchingText(String expression, String text) {
		Pattern p = Pattern.compile(expression); // 正则表达式
		Matcher m = p.matcher(text); // 操作的字符串
		boolean b = m.matches();
		return b;
	}

	/**
	 * 生成数字随机码 指定生成的位数
	 * @param len
	 * @return
	 */
	public static String generateRandomCode(int len){
		int lena = len/2;
		int lenb = len%2==0?len/2:len/2+1;
		return (String.valueOf(System.currentTimeMillis()).substring(13-lena, 13) + (String.valueOf(Math.random())).substring(15-lenb, 15));
	}


	public static boolean deleteFile(String filePath){
		boolean flag=false;
		File file=new File(filePath);
		if(file.exists()&&file.isFile()){
			file.delete();
			flag=true;
		}
		return flag;
	}

	/**
	 * 0008
	 * @param str
	 * @return
	 */
	public static String spilt(String str) {
		StringBuffer sb = new StringBuffer();
		String[] temp = str.split(",");
		for (int i = 0; i < temp.length; i++) {
			if (!"".equals(temp[i]) && temp[i] != null)
				sb.append("'" + temp[i] + "',");
		}
		String result = sb.toString();
		if(result.length()<=0){
			return "''";
		}
		String tp = result.substring(result.length() - 1);
		if (",".equals(tp))
			return result.substring(0, result.length() - 1);
		else
			return result;
	}


}