package excel_data_import.core;

import excel_data_import.file.ExcelFileService;
import excel_data_import.repository.implementation.CompetitorRepositoryImpl;
import excel_data_import.repository.implementation.HaurRankingDatabaseUtils;
import excel_data_import.repository.implementation.MatchRepositoryImpl;
import excel_data_import.service.CompetitorService;
import excel_data_import.service.MatchService;

public class App {
	public static void main(String[] args) {
		CompetitorService.init(new CompetitorRepositoryImpl());
		HaurRankingDatabaseUtils.initialize();
		MatchService.init(new MatchRepositoryImpl());

		ExcelFileService.importExcelData("Lohi file");
		HaurRankingDatabaseUtils.createEntityManager().close();
		HaurRankingDatabaseUtils.closeEntityManagerFactory();
	}
}
