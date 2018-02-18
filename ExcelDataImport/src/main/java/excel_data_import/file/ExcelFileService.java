package excel_data_import.file;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import excel_data_import.domain.ClassifierStage;
import excel_data_import.domain.Competitor;
import excel_data_import.domain.IPSCDivision;
import excel_data_import.domain.Match;
import excel_data_import.domain.Stage;
import excel_data_import.domain.StageScoreSheet;

public class ExcelFileService {
	public static void importExcelData(String fileName) {
		try {
			FileInputStream excelFile = new FileInputStream(new File("ranking_data.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

				Sheet sheet = workbook.getSheetAt(i);
				IPSCDivision division = IPSCDivision.valueOf(sheet.getSheetName().toUpperCase());

				System.out.println("\n *** DIVISION: " + division);

				// Generate matches and stages for classifiers in spread sheet.
				List<Match> matches = new ArrayList<Match>();
				Iterator<Cell> cellIterator = sheet.getRow(0).iterator();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

				Calendar matchDatesEnd = Calendar.getInstance();
				matchDatesEnd.setTime(dateFormat.parse("31.12.2014"));

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						ClassifierStage classifierStage = ClassifierStage
								.parseInt(new Double(currentCell.getNumericCellValue()).intValue());

						String importDateString = dateFormat.format(Calendar.getInstance().getTime());

						String matchAndStageName = classifierStage + " imported from Excel on " + importDateString;
						Match match = new Match(matchAndStageName);

						Calendar matchDate = Calendar.getInstance();
						matchDate.setTime(matchDatesEnd.getTime());
						match.setDate(matchDate);
						matchDatesEnd.add(Calendar.DATE, -1);

						List<Stage> stageList = new ArrayList<Stage>();

						Stage stage = new Stage(match, matchAndStageName, classifierStage);
						List<StageScoreSheet> sheetList = new ArrayList<StageScoreSheet>();
						stage.setStageScoreSheets(sheetList);
						stageList.add(stage);

						match.setStages(stageList);
						matches.add(match);
					}
				}

				Iterator<Row> iterator = sheet.iterator();

				int rowCounter = 0;
				while (iterator.hasNext()) {
					Row currentRow = iterator.next();
					rowCounter++;
					if (rowCounter == 1)
						continue;
					Cell currentCell = currentRow.getCell(0);

					String name = currentRow.getCell(0).getStringCellValue();

					Competitor competitor = null;
					if (name != null && name.length() > 0) {
						String[] nameArray = name.split(" ");
						if (nameArray.length != 2)
							throw new Exception("Wrong competitor name format in Excel spread sheet");
						competitor = new Competitor();
						competitor.setFirstName(nameArray[0]);
						competitor.setLastName(nameArray[1]);
					} else
						throw new Exception("Wrong competitor name format in Excel spreadsheet");

					cellIterator = currentRow.iterator();

					while (cellIterator.hasNext()) {

						currentCell = cellIterator.next();
						// getCellTypeEnum shown as deprecated for version 3.15
						// getCellTypeEnum ill be renamed to getCellType
						// starting
						// from version 4.0
						if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							double hitFactor = currentCell.getNumericCellValue();
							if (hitFactor == -1.0)
								continue;
							Stage stage = matches.get(currentCell.getColumnIndex() - 1).getStages().get(0);
							StageScoreSheet scoreSheet = new StageScoreSheet(competitor, hitFactor, stage, division);
							stage.getStageScoreSheets().add(scoreSheet);
						}

					}
				}
				for (Match match : matches) {
					System.out.println("\n\tMatch name: " + match.getName() + " / Saved with match date: "
							+ match.getDate().getTime().toString());
					Stage stage = match.getStages().get(0);
					System.out.println(
							"\tStage name: " + stage.getName() + " / Classifier: " + stage.getClassifierStage() + "\n");
					for (StageScoreSheet scoreSheet : match.getStages().get(0).getStageScoreSheets()) {
						System.out.println("\t" + scoreSheet.getCompetitor().getFirstName() + " "
								+ scoreSheet.getCompetitor().getLastName() + "\t" + scoreSheet.getHitFactor());
					}
					System.out.println("\n\t=================");
				}
			}
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
