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
import excel_data_import.service.CompetitorService;
import excel_data_import.service.MatchService;

public class ExcelFileService {
	public static void importExcelData(String fileName) {
		Workbook workbook = null;
		try {
			FileInputStream excelFile = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(excelFile);

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

				Sheet sheet = workbook.getSheetAt(i);
				IPSCDivision division = IPSCDivision.valueOf(sheet.getSheetName().toUpperCase());

				System.out.println("\n *** DIVISION: " + division);

				// Generate matches and stages for classifiers in spread sheet.
				List<Match> matches = new ArrayList<Match>();
				Iterator<Cell> cellIterator = sheet.getRow(0).iterator();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						ClassifierStage classifierStage = ClassifierStage
								.parseInt(new Double(currentCell.getNumericCellValue()).intValue());

						String importDateString = dateFormat.format(Calendar.getInstance().getTime());

						String matchAndStageName = classifierStage + " imported from Excel on " + importDateString;
						Match match = new Match(matchAndStageName);

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
						Competitor databaseCompetitor = CompetitorService.find(competitor.getFirstName(),
								competitor.getLastName());
						if (databaseCompetitor != null) {
							competitor = databaseCompetitor;

						} else {
							CompetitorService.persist(competitor);
						}
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

				// Set match dates to before start of WinMSS database on
				// 28.2.2017
				Calendar nextMatchDate = Calendar.getInstance();
				nextMatchDate.setTime(dateFormat.parse("28.2.2017"));
				nextMatchDate.add(Calendar.DATE, -(matches.size()));

				for (Match match : matches) {

					Calendar matchDate = Calendar.getInstance();
					matchDate.setTime(nextMatchDate.getTime());
					match.setDate(matchDate);
					nextMatchDate.add(Calendar.DATE, 1);

					MatchService.persist(match);

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

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
