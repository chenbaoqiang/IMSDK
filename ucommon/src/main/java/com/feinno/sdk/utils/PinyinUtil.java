package com.feinno.sdk.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class PinyinUtil {
	public static final String TAG = "PinyinUtil";
	public static Hanyu mHanyu = new Hanyu();
	
	public static List<String> pinyin (String hanyu) {
		List<String> py = mHanyu.getStringPinYin(hanyu);

		return py;
	}

	public static String[] getCharacterPinYin (Character c) {
		return mHanyu.getCharacterPinYin(c);
	}
	
	static class Hanyu {

		public Hanyu() {
		}

		public String[] getCharacterPinYin(Character c) {
			String[] pinyin = null;
			try {
                pinyin = HanziToPinyin.getPinyinArray(c);
			} catch (Exception e) {
				
			}
			if (pinyin == null) {
				return null;
			} else {
				List<String> ps = new ArrayList<String>();
				for (int i = 0; i < pinyin.length; ++i) {
					String p = pinyin[i];
					Character ch = p.charAt(0);
	                ch = Character.toUpperCase(ch);
	                p = ch.toString().concat(p.substring(1));
	                
	                if (!ps.contains(p)) {
	                	ps.add(p);
	                }
				}
				String[] arr = (String[])ps.toArray(new String[ps.size()]);
				return arr;
			}
		}

		public List<String> getStringPinYin(String s) {
			if (TextUtils.isEmpty(s)) {
				return null;
			}
			String str = getContactName(s);
			if (TextUtils.isEmpty(str)) {
				return null;
			}
			int lengthS = str.length();

			List<String> sl = new ArrayList<String>();
			List<String[]> pinyin = new ArrayList<String[]>();
			int index = 0;
			StringBuilder builder = null;
			
			Character c = str.charAt(index);
			while (index < lengthS) {
				if (isAlphaOrDigit(c)) {
					builder = new StringBuilder();
					while (index < lengthS && isAlphaOrDigit(c)) {
						builder.append(c);
						index++;
						if (index < lengthS) {
							c = str.charAt(index);
						}
					}
					String tmp = builder.toString();
					if (!TextUtils.isEmpty(tmp)) {
						pinyin.add(new String[] { tmp });
					}
				} else {
					String[] tempPinyin = getCharacterPinYin(c);
					while(index < lengthS && tempPinyin == null && !isAlphaOrDigit(c)) {
						index++;
						if (index < lengthS) {
							c = str.charAt(index);
							tempPinyin = getCharacterPinYin(c);
						}
					}
					if (tempPinyin != null && !isAlphaOrDigit(c)) {
						pinyin.add(tempPinyin);
						index++;
						if (index < lengthS) {
							c = str.charAt(index);
						}
					}
				}
			}
			String ss[] = new String[pinyin.size()];
			getPY(sl, pinyin, ss, 0);
			return sl;
		}
		
		public int getPY(List<String> sl, List<String[]> pinyin, String[] ss, int level) {
			if (level >= pinyin.size()) {
				return -1;
			}
			
			int ll = pinyin.get(level).length;
			for (int i = 0; i < ll; ++i) {
				ss[level] = pinyin.get(level)[i];
				int tmp = getPY(sl, pinyin, ss, level + 1);
				if (tmp < 0) {
					StringBuilder builder = new StringBuilder();
					for (int j = 0; j < ss.length; ++j) {
						builder.append(ss[j]);
						builder.append(" ");
					}
					builder.deleteCharAt(builder.length() - 1);
					sl.add(builder.toString());
				}
			}
			return 1;
		}

		/**
		 * 去掉联系人姓名中的多余字符，只保留汉字，字母，数字，空格
		 * 用于获取联系人姓名的拼音
		 * @param s
		 * @return
		 */
		public static String getContactName(String str) {
			if (TextUtils.isEmpty(str)) {
				return null;
			}
			String ss = str.trim();
			int l = ss.length();
			StringBuilder builder = new StringBuilder();
			Character c = null;
			for (int i = 0; i < l; ++i) {
				c = ss.charAt(i);
				if (Character.isLetterOrDigit(c) || Character.isSpaceChar(c) || c == '\'') {
					builder.append(c);
				} else {
					builder.append(" ");
				}
			}

			String tmp = builder.toString();
			tmp = tmp.trim().replace(" +", " ");

			if (!TextUtils.isEmpty(tmp)) {
				return tmp;
			} else {
				return null;
			}
		}

		/**
		 * 判断字符是否为数字或字母
		 * @param c
		 * @return
		 */
		public static boolean isAlphaOrDigit(Character c) {
			if ('a' <= c && c <= 'z') {
				return true;
			} else if ('A' <= c && c <= 'Z') {
				return true;
			} else if ('0' <= c && c <= '9') {
				return true;
			}
			return false;
		}
	}
}
