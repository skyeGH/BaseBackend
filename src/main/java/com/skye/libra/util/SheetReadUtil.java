package com.skye.libra.util;

import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : gaiH
 * 2022/7/27
 * @Description :
 */
public class SheetReadUtil {

	public static List<Map<String, Object>> read(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		Workbook wb = new XSSFWorkbook(file.getInputStream());
		file.getInputStream().close();
		int index = wb.getNumberOfSheets();
		List<Map<String, Object>> list = Lists.newArrayList();
		for (int i = 0; i < index - 1; i++) {
			list.addAll(Objects.requireNonNull(readSheet(wb.getSheetAt(i))));
		}
		return list;
	}

	private static List<Map<String, Object>> readSheet(Sheet sheet) {
		return null;
	}

}
