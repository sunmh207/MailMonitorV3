package supportnet.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

public class SysUtil {
	public static List<String> getVoiceFileList() {
		List<String> list= new ArrayList<String>();
		Session s = null;
		try {
			s = DBtools.getExclusiveSession();
			Connection conn = s.connection();
			conn.setAutoCommit(true);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select file_name from JTMOBILE_VOICEFILE");
			while (rs.next()) {
				list.add(rs.getString("file_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return list;
	}
	
	public synchronized static String generateBusinessCode(String type) {
		String key = null;
		Session s = null;
		try {
			s = DBtools.getExclusiveSession();
			Connection conn = s.connection();
			conn.setAutoCommit(true);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select business_code from JTMOBILE_BUSINESS_CODE  where business_type='" + type	+ "' for update ");
			String currentCode = "";
			boolean isExist = false;
			if (rs.next()) {
				currentCode = rs.getString("business_code");
				isExist=true;
			}
			if (isExist) {
				key = generateNextCode(currentCode); 
				stmt.executeUpdate("update JTMOBILE_BUSINESS_CODE set BUSINESS_CODE = '"+key+"' where Business_Type='"+type+"'");
	        }else{	
	        	key="A";
	            stmt.execute("insert into JTMOBILE_BUSINESS_CODE (Business_Type, BUSINESS_CODE) values ('"+type+"','"+key+"')");
	        }			
			 rs.close();
			 stmt.close();
	         conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return key;

	}
/*	public synchronized static String generateBusinessCode(String type) {
		String key = null;
		Session s = null;
		try {
			s = DBtools.getExclusiveSession();
			Connection conn = s.connection();
			conn.setAutoCommit(true);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select business_code from JTMOBILE_BUSINESS_CODE  where business_type='" + type
					+ "' order by length(business_code) desc ,business_code desc ");
			String currentCode = "";
			if (rs.next()) {
				currentCode = rs.getString("business_code");
			}
			
			key = generateNextCode(currentCode);
			
			PreparedStatement ps = conn.prepareStatement("insert into JTMOBILE_BUSINESS_CODE (Business_Type, BUSINESS_CODE) values (?,?)");
			ps.setString(1, type);
			ps.setString(2, key);
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		return key;
		
	}
*/
	private static String generateNextCode(String code) {
		
		return generateNextCode(code,2);
	}
	private static String generateNextCode(String code, int length) {
		if(length<=0){return "";};
		
		if (StringUtil.isEmpty(code)) {
			return "A";
		}
		//code is lager than MaxCode(length);
		if(code.length()>length){
			return "A";
		}
		
		//if code=ZZZ..ZZ, and ZZZ..ZZ.length=length, then next code = A (reuse)
		String lastStr="";
		for(int i=0;i<length;i++){
			lastStr+="Z";
		}
		if (lastStr.equals(code)){
			return "A";
		}
		
		String uppercode = code.toUpperCase();
		char[] chars = uppercode.toCharArray();
		int len = chars.length;
		for (int i = len - 1; i >= 0; i--) {
			if (chars[i] == 'Z') {// ...Z, ...Z..
				if (i == 0) {// ZZZ
					String next = "A";
					for (int j = 0; j < len; j++) {
						next += "A";
					}
					return next;// AAAA
				} else {// ...Z
					return generateNextCode(uppercode.substring(0, len - 1)) + "A";
				}
			} else {// .....?
				chars[i] = getNextChar(chars[i]);
				return String.valueOf(chars);
			}
		}
		return null;
	}


	private static char getNextChar(char firstChar) {
		int ascFirstChar = new Character(firstChar).hashCode();
		if (ascFirstChar < 65 || (ascFirstChar > 90 && ascFirstChar < 97) || ascFirstChar > 122) {
			return '$';
		}
		if (ascFirstChar == 90) {
			ascFirstChar = 64;
		}
		if (ascFirstChar == 122) {
			ascFirstChar = 96;
		}
		int ascSecondChar = ascFirstChar + 1;
		return (char) ascSecondChar;
	}

	

	
	public static void main(String[] args) throws Exception {
		/*
		 * System.out.println(generateNextCode("A"));
		 * System.out.println(generateNextCode("AB"));
		 * System.out.println(generateNextCode("AC"));
		 * System.out.println(generateNextCode("AZ"));
		 * System.out.println(generateNextCode("BC"));
		 * System.out.println(generateNextCode("BZ"));
		 * System.out.println(generateNextCode("ZZ"));
		 */

		for (int i = 0; i < 100; i++) {
			System.out.println(generateBusinessCode("D"));
		}
	}
}
