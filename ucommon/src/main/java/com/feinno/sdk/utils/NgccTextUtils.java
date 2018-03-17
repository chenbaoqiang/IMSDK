package com.feinno.sdk.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NgccTextUtils {
	private static String TAG = "NgccTextUtils";
    
	private static Integer[] COUNTRY_CODES = {
		93, 355, 213, 376, 244, 672, 54, 374, 297, 61,
        43, 994, 973, 880, 375, 32, 501, 229, 975, 591,
        387, 267, 55, 673, 359, 226, 95, 257, 855, 237,
        1, 238, 236, 235, 56, 86, 61, 61, 57, 269, 242,
        243, 682, 506, 385, 53, 357, 420, 45, 253, 670,
        593, 20, 503, 240, 291, 372, 251, 500, 298, 679,
        358, 33, 689, 241, 220, 995, 49, 233, 350, 30,
        299, 502, 224, 245, 592, 509, 504, 852, 36, 91,
        62, 98, 964, 353, 44, 972, 39, 225, 81, 962, 7,
        254, 686, 965, 996, 856, 371, 961, 266, 231, 218,
        423, 370, 352, 853, 389, 261, 265, 60, 960, 223,
        356, 692, 222, 230, 262, 52, 691, 373, 377, 976,
        382, 212, 258, 264, 674, 977, 31, 599, 687, 64,
        505, 227, 234, 683, 850, 47, 968, 92, 680, 507,
        675, 595, 51, 63, 870, 48, 351, 1, 974, 40, 7,
        250, 590, 685, 378, 239, 966, 221, 381, 248, 232,
        65, 421, 386, 677, 252, 27, 82, 34, 94, 290, 508,
        249, 597, 268, 46, 41, 963, 886, 992, 255, 66, 228,
        690, 676, 216, 90, 993, 688, 971, 256, 44, 380, 598,
        1, 998, 678, 39, 58, 84, 681, 967, 260, 263,
	};
	
	private static HashSet<String> sCountryCodeSet = new HashSet<String>();

	static {
		for(Integer i : COUNTRY_CODES) {
			sCountryCodeSet.add(i.toString());
		}
	}

	public static Pattern p = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$"); 
	public static boolean isPhoneMobile(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
		Matcher m = null;
		boolean b = false;
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	public static Pattern PHONE_NUMBER_P = Pattern.compile("^[\\+\\d][\\d]*"); 
	public static boolean isPhoneNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
		Matcher m = null;
		boolean b = false;
		m = PHONE_NUMBER_P.matcher(str);
		b = m.matches();
		return b;
	}

	public static String trimPhoneNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return null;
        }
        char[] tmp = new char[number.length()];
        int index = 0;
        for(int i = 0; i < number.length(); i++){
            char c = number.charAt(i);
            if((index == 0 && c == '+') || (c >= '0' && c <= '9')){
                tmp[index++] = c;
            }
        }
        if(index == number.length()){
            return number;
        }else {
            return new String(tmp, 0, index);
        }
	}

	public static String convertUriToNumber(String number){
		String num = null;
		try {
			if(!TextUtils.isEmpty(number)){
				if(number.contains("sip:") || number.contains("tel:")){
					num = number.replaceAll("<|(@.*)?>|.*:", "");
				} else {
					num = number;
				}
			}	
		} catch(Exception e) {
			LogUtil.w(TAG , "deal number error:" + e.toString());
		}
		return num;
	}

	
	//TODO: only support 00, without configuration support 
	public static String sInternationalCallPrefix = "00";
	
	public static String getNationalNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return null;
        }
			
		//get the national number from the country code set, try three times: 
		// - substring(1, 4)
		// - substring(1, 3)
		// - substring(1, 2)

		number = trimPhoneNumber(number);
		if(number.startsWith("+")) {
			//remove country code
			int n = Math.min(4, number.length());
			for (int i = n; i > 1; i --) {
				String ss = number.substring(1, i);
				if(sCountryCodeSet.contains(ss)) {
					return number.substring(i);
				}
			}
		}
		else {
			//REVIEW: is this OK?
			if(number.startsWith(sInternationalCallPrefix) && number.length() > sInternationalCallPrefix.length()) {
				number = number.substring(sInternationalCallPrefix.length() + 1);
				
				//remove country code
				int n = Math.min(3, number.length());
				for (int i = n; i > 0; i --) {
					String ss = number.substring(0, i);
					if(sCountryCodeSet.contains(ss)) {
						return number.substring(i);
					}
				}	
			}
		}

		//cannot match country code
		return number;
	}

    //change 0086 to +86
    public static String normalizeInternationalNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return number;
        }
        int prefixLength = sInternationalCallPrefix.length();
        if (number.startsWith(sInternationalCallPrefix) && number.length() > prefixLength) {
            return "+" + number.substring(prefixLength);
        }
        return number;
    }

    public static String getInternationalNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return null;
        }
		//NLog.d(TAG, "toInternational number raw:" + number);
		number = NgccTextUtils.trimPhoneNumber(number);
		if (TextUtils.isEmpty(number)) {
			return null;
		}
		//NLog.d(TAG, "toInternational number after:" + number);
		if(!number.startsWith("+") && !number.startsWith(sInternationalCallPrefix)) {
			//NLog.d(TAG, "1:" + "+86" + number);
			if(isPhoneNumberInner(number)) {
				return "+86" + number;
			}
		} else {
            if (number.startsWith("+86") && number.length() > 3) {
                if (!isPhoneNumberInner(number.substring(3))) {
                    return number.replaceFirst("\\+86", "");
                }
            } else if (number.startsWith("0086") && number.length() > 4) {
                if (!isPhoneNumberInner(number.substring(4))) {
                    return number.replaceFirst("0086", "");
                }
            }
        }
        //NLog.d(TAG, "2:" + normalizeInternationalNumber(number));
		return normalizeInternationalNumber(number);
	}

	private static boolean isPhoneNumberInner(String number){
		if(number.length() == 11) {
			char nc0 = number.charAt(0);
			char nc1 = number.charAt(1);
			if(nc0 == '1' && nc1 >= '3' && nc1 <= '8'){
				return true;
			}
		}
		return false;
	}

	public static String base64Decode(String content){
		if(TextUtils.isEmpty(content)) {
			return content;
		}
		byte[] data = Base64.decode(content, Base64.DEFAULT);
		return new String(data);
	}

	public static List<String> getCombinations(String str) {
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		final char[] chars = str.toCharArray();
		List<String> result = new ArrayList<String>();
		int n = (int) Math.pow(2, chars.length);
		StringBuilder sb = new StringBuilder();
		for (int l = 0; l < n; l++) {
			sb.delete(0, sb.length());
			for (int i = 0; i < chars.length; i++) {
				if ((l>>>i&1) == 1) {
					sb.append(chars[i]);
				}
			}
			if (sb.length() > 1) {
				result.add(sb.toString());
			}
		}
		return result;
	}

	private static final char[] LATIN_LETTERS_TO_DIGITS = {
			'2', '2', '2', 		// A,B,C -> 2
			'3', '3', '3', 		// D,E,F -> 3
			'4', '4', '4', 		// G,H,I -> 4
			'5', '5', '5', 		// J,K,L -> 5
			'6', '6', '6', 		// M,N,O -> 6
			'7', '7', '7', '7', // P,Q,R,S -> 7
			'8', '8', '8', 		// T,U,V -> 8
			'9', '9', '9', '9' 	// W,X,Y,Z -> 9
	};

	public static byte getDialpadIndex(char ch) {
		if (ch >= '0' && ch <= '9') {
			return (byte) (ch - '0');
		} else if (ch >= 'a' && ch <= 'z') {
			return (byte) (LATIN_LETTERS_TO_DIGITS[ch - 'a'] - '0');
		} else if (ch >= 'A' && ch <= 'Z') {
			return (byte) (LATIN_LETTERS_TO_DIGITS[ch - 'A'] - '0');
		} else {
			return -1;
		}
	}

	/**
	 * 将字母字符串转化为拨号键盘上对应的数字字符串
	 * @param str
	 * @return
	 */
	public static String convertToNumber(String str) {
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			int result = getDialpadIndex(str.charAt(i));
			if (-1 != result) {
				sb.append(result);
			} else {
				sb.append(" ");
			}
		}

		return sb.toString();
	}

	/**
	 * 判断一个字符串是否只有数字组成
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (TextUtils.isEmpty(str)) {
			return false;
		}
		for (int i = str.length() - 1; i >= 0; --i) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
