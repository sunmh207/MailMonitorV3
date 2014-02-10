package supportnet.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import supportnet.common.Constants;
import supportnet.common.exception.JTException;
import supportnet.common.form.ComparatorField;
import supportnet.common.form.JTField;

public class FileUtil {
	public static String exportFile(String path, String extention, Blob blobData, boolean delFile) throws JTException {
		try {
			File dirPath = new File(path);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}

			String dir = path + File.separator;
			String filename = "";
			if (delFile) {
				delDiskFile(path);
			}

			filename = DateUtil.getCurrentTime("yyyyMMddHHmmss") + new Random().nextInt(10000);
			if (extention != null && !"".equals(extention.trim())) {
				filename += "." + extention;
			}
			File f = new File(dir + filename);

			InputStream inputStream = blobData.getBinaryStream();
			FileOutputStream fileOutputStream = new FileOutputStream(f);
			byte[] buf = new byte[1];
			int len = 0;
			while ((len = inputStream.read(buf)) != -1) {
				fileOutputStream.write(buf, 0, len);
			}
			inputStream.close();
			fileOutputStream.close();

			return filename;
		} catch (Exception e) {
			throw new JTException("导出Excel失败", e, FileUtil.class);
		}

	}

	public static String exportFile(String path, List<?> list, boolean delFile) throws JTException {
		try {
			File dirPath = new File(path);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}

			String dir = path + File.separator;
			String filename = "";
			if (delFile) {
				delDiskFile(path);
			}

			filename = DateUtil.getCurrentTime("yyyyMMddHHmmss") + new Random().nextInt(10000) + ".xls";
			File f = new File(dir + filename);

			WritableWorkbook book = Workbook.createWorkbook(f);
			WritableSheet sheet = book.createSheet("sheet1", 0);

			if (list != null && !list.isEmpty()) {
				List<Method> validMethods = new ArrayList<Method>();
				// sort
				Method[] methods = list.get(0).getClass().getDeclaredMethods();
				for (Method method : methods) {
					boolean hasAnnotation = method.isAnnotationPresent(JTField.class);
					if (hasAnnotation) {
						validMethods.add(method);

					}
				}
				ComparatorField comparator = new ComparatorField();
				Collections.sort(validMethods, comparator);

				// header
				int col = 0;
				for (Method method : validMethods) {
					JTField annotation = method.getAnnotation(JTField.class);
					sheet.addCell(new Label(col, 0, annotation.chineseName()));
					col++;
				}

				// body
				int row = 1;
				for (Object o : list) {
					col = 0;
					for (Method method : validMethods) {
						Object value = method.invoke(o, new Object[] {});
						JTField annotation = method.getAnnotation(JTField.class);
						switch (annotation.fieldType()) {
						/*case UserID:
							value = SysCache.interpertUserName(value == null ? "" : value.toString());
							break;
						case RoleID:
							value = SysCache.interpertRoleName(value == null ? "" : value.toString());
							break;
						case StringBool:
							if (JitongConstants.STRING_TRUE.equals(value)) {
								value = "是";
							} else if (JitongConstants.STRING_FALSE.equals(value)) {
								value = "否";
							}
							break;
						case PhoneNumber2UserName:
							value = SysCache.interpertUserNameByPhone(value.toString());*/
						case String:
							;
						}
						sheet.addCell(new Label(col, row, value == null ? "" : value.toString()));
						col++;
					}
					row++;
				}

			}
			book.write();
			book.close();
			return filename;
		} catch (Exception e) {
			throw new JTException("导出Excel失败", e, FileUtil.class);
		}
	}

	public static void delDiskFile(String path) {
		File dir = new File(path);
		String[] filename = dir.list();
		if (filename == null)
			return;
		int count = filename.length;
		String date = DateUtil.getCurrentTime("yyyyMMdd");
		for (int i = 0; i < count; i++) {
			String str = filename[i];
			if (str.indexOf(date) == -1 && str.indexOf("xls") > -1 || str.indexOf("txt") > -1) {
				File f = new File(path + File.separator + filename[i]);
				f.delete();
			}
		}
	}

}
