package WeatherInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class DiameterInfo {
	private String diameterBuffer[][] = new String[3800][5];

	String[] checkDiameter(String addr) {
		/*
		 * 0: x��ǥ 1: y��ǥ
		 */
		String diameter[] = new String[2];
		diameter[0] = "0";
		diameter[1] = "0";
		String sbuf;

		addr = addr.replace(" ", "");
		addr = addr.replace("Ư����", "��");
		addr = addr.replace("������", "��");
		addr = addr.replace("Ư����ġ��", "��");
		addr = addr.replace("Ư����ġ��", "��");
		for (int i = 0; i < diameterBuffer.length && diameterBuffer[i][0] != null; i++) {
			sbuf = diameterBuffer[i][0].concat(diameterBuffer[i][1]).concat(diameterBuffer[i][2]);

			sbuf = sbuf.replace(" ", "");
			sbuf = sbuf.replace("Ư����", "��");
			sbuf = sbuf.replace("������", "��");
			sbuf = sbuf.replace("Ư����ġ��", "��");
			sbuf = sbuf.replace("Ư����ġ��", "��");

			if (addr.equals(sbuf)) {
				try {
					diameter[0] = diameterBuffer[i][3];
					diameter[1] = diameterBuffer[i][4];
				} catch (NumberFormatException e) {
					continue;
				}

				return diameter;
			}
		}
		return diameter;
	}

	void print() {
		for (int i = 0; i < diameterBuffer.length; i++) {
			for (int j = 0; j < diameterBuffer[i].length; j++) {
				System.out.print(diameterBuffer[i][j] + " ||");
			}
			System.out.println();
		}
	}

	void parseDiameterInfo() {
		FileInputStream fis = null;
		try {
			// fis = new FileInputStream("C:\\DiameterInfo.xlsx");
			 fis = new FileInputStream(getClass().getResource("").getPath() + "../../excel/DiameterInfo.xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int rowindex = 0;
		int columnindex = 0;
		// ��Ʈ �� (ù��°���� �����ϹǷ� 0�� �ش�)
		// ���� �� ��Ʈ�� �б����ؼ��� FOR���� �ѹ��� �����ش�
		XSSFSheet sheet = workbook.getSheetAt(0);
		// ���� ��
		int rows = sheet.getPhysicalNumberOfRows();
		for (rowindex = 1; rowindex < rows && rowindex < diameterBuffer.length; rowindex++) {
			// �����д´�
			XSSFRow row = sheet.getRow(rowindex);
			if (row != null) {
				// ���� ��
				int cells = row.getPhysicalNumberOfCells();
				for (columnindex = 0; columnindex <= cells
						&& columnindex < diameterBuffer[rowindex].length; columnindex++) {
					// ������ �д´�
					XSSFCell cell = row.getCell(columnindex);
					// ���� ���ϰ�츦 ���� ��üũ
					if (cell == null) {
						continue;
					} else {
						// Ÿ�Ժ��� ���� �б�
						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_FORMULA:
							diameterBuffer[rowindex - 1][columnindex] = cell.getCellFormula();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							diameterBuffer[rowindex - 1][columnindex] = cell.getNumericCellValue() + "";
							break;
						case XSSFCell.CELL_TYPE_STRING:
							diameterBuffer[rowindex - 1][columnindex] = cell.getStringCellValue() + "";
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							diameterBuffer[rowindex - 1][columnindex] = cell.getBooleanCellValue() + "";
							break;
						case XSSFCell.CELL_TYPE_ERROR:
							diameterBuffer[rowindex - 1][columnindex] = cell.getErrorCellValue() + "";
							break;
						}
					}
				}
			}
		}

	}
}
