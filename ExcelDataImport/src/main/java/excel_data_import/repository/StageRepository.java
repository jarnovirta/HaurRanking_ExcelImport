package excel_data_import.repository;

import java.util.Map;

import excel_data_import.domain.ClassifierStage;
import excel_data_import.domain.IPSCDivision;
import excel_data_import.domain.Stage;

public interface StageRepository {
	public Stage find(Long id);

	public int getCount();

	public void delete(Stage stage);

	public Stage find(Stage stage);

	public Map<ClassifierStage, Double> getClassifierStagesWithTwoOrMoreResults(IPSCDivision division);
}
