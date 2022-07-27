package com.skye.libra.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取excel.
 * @author : gaiH
 * 2022/6/14
 * @Description :
 */
public class ExcelReader {
	private ExcelReader excelReader;
	/**
	 * 读取excel.
	 * @param file file
	 * @param readStartRow readStartRow
	 * @return list message
	 */
	public static List<Map<String, Object>> excelRead(MultipartFile file, Integer readStartRow) {
		if (null == readStartRow) {
			readStartRow = 0;
		}
		String fileName = file.getOriginalFilename();
		if (fileName.toLowerCase().matches("^.*(xls|XLS|XLs|xlsx)$")) {
			String edition_old = "^.+\\.(?i)(xls)$";
			String edition_new = "^.+\\.(?i)(xlsx)$";
			try {
				Workbook wb = null;
				if (fileName.matches(edition_old)) {
					wb = new HSSFWorkbook(file.getInputStream());
				} else {
					if (fileName.matches(edition_new)) {
						wb = new XSSFWorkbook(file.getInputStream());
					}
				}
				file.getInputStream().close();
				if (null == wb) {
					System.out.println("empty workbook");
				}
				Sheet sheet = wb.getSheetAt(0);
				int totalRows = sheet.getPhysicalNumberOfRows();
				int totalCells = 0;
				if (totalRows > 1 && sheet.getRow(0) != null) {
					totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
				}
				List<Map<String, Object>> listMap = new ArrayList<>();
				for (int r = readStartRow; r < totalRows; r++) {
					Row row = sheet.getRow(r);
					if (null == row) {
						continue;
					}
					Map<String, Object> map = new HashMap<>();
					for (int c = 0; c < totalCells; c++) {
						Cell cell = row.getCell(c);
						if (null == cell) {
							continue;
						}
						CellType cellType = cell.getCellType();
						if (null == cellType) {
							DataFormatter dataFormatter = new DataFormatter();
							dataFormatter.addFormat("###########", null);
							String str = dataFormatter.formatCellValue(cell);
							if (StringUtils.isNotBlank(str)) {
								map.put(c + "", str);
							}
						} else {
							String str = String.valueOf(cell);
							if (StringUtils.isNotBlank(str)) {
								map.put(c + "", str);
							}
						}
					}
					if (map.size() > 0) {
						listMap.add(map);
					}
				}
				List<Map<String, Object>> resultList = new ArrayList<>();
				Map<String, Object> keyMap = listMap.get(0);
				String[] keyArr = new String[totalCells];
				for (int i = 0; i < totalCells; i++) {
					if (null != keyMap.get(i + "")) {
						keyArr[i] = keyMap.get(i + "").toString();
					}
				}
				int dataSize = totalRows - readStartRow;
				for (int i = 1; i < dataSize; i++) {
					Map<String, Object> valueMap = new HashMap<>();
					for (int j = 0; j < totalCells; j++) {
						String key = keyArr[j];
						String value = listMap.get(i).get(j + "") == null ? "" : listMap.get(i).get(j + "").toString();
						valueMap.put(key, value);
					}
					resultList.add(valueMap);
				}
				return resultList;
			} catch (Exception e) {
				System.out.println("false");
			}
		}
		return null;
	}
}
