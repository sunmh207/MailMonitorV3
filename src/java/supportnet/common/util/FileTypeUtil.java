package supportnet.common.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileTypeUtil {
	private static Map<String, String> mimeExtMap = new HashMap<String, String>();
	static {
		mimeExtMap.put("image/bmp", "bmp");
		mimeExtMap.put("image/gif", "gif");
		mimeExtMap.put("image/jpeg", "jpg");
		mimeExtMap.put("image/png", "png");
		mimeExtMap.put("image/tiff", "tif");
		mimeExtMap.put("audio/x-wav", "wav");
		mimeExtMap.put("audio/wav", "wav");
		mimeExtMap.put("audio/mpeg", "mp3");
		mimeExtMap.put("audio/mid", "mid");
	}
	private static Map<String, String> extMIMEMap = new HashMap<String, String>();
	static {
		extMIMEMap.put("gif","image/gif");
		extMIMEMap.put("jpeg","image/jpeg");
		extMIMEMap.put("jpg","image/jpeg");
		extMIMEMap.put("png","image/png");
		extMIMEMap.put("midi","audio/mid");
		extMIMEMap.put("mid","audio/mid");
		extMIMEMap.put("txt","text/plain");
		extMIMEMap.put("amr","application/octet-stream");
	}
	
	private static Set<String> imageTypes = new HashSet<String>();
	static{
		imageTypes.add("gif");
		imageTypes.add("jpeg");
		imageTypes.add("jpg");
		imageTypes.add("png");
	}
	
	private static Set<String> forbiddenUpload = new HashSet<String>();
	static{
		forbiddenUpload.add("jsp");
		forbiddenUpload.add("exe");
	}
	
	public static boolean isImage(String type){
		return imageTypes.contains(type.toLowerCase());
	}

	public static String getExtensionByName(String name) {
		int idx = name.lastIndexOf('.');
		if(idx>=0){
			return name.substring(idx+1);
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getExtensionByName("aam.aa.mm"));
	}

	public static boolean isValidType(String ext) {
		return extMIMEMap.containsKey(ext.toLowerCase());
	}
	
	public static boolean isForbiddenUploadType(String ext) {
		return forbiddenUpload.contains(ext.toLowerCase());
	}
}
