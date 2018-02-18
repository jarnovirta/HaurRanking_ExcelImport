package excel_data_import.repository;

import java.util.List;
import java.util.Set;

import excel_data_import.domain.ClassifierStage;
import excel_data_import.domain.Competitor;
import excel_data_import.domain.IPSCDivision;
import excel_data_import.domain.StageScoreSheet;

public interface StageScoreSheetRepository {
	public List<StageScoreSheet> find(IPSCDivision division, ClassifierStage classifier);

	public StageScoreSheet find(Long id);

	public List<StageScoreSheet> find(String firstName, String lastName, IPSCDivision division,
			ClassifierStage classifierStage);

	public List<StageScoreSheet> find(String firstName, String lastName, IPSCDivision division);

	public List<StageScoreSheet> find(String firstName, String lastName, IPSCDivision division,
			Set<ClassifierStage> classifiers);

	public List<StageScoreSheet> find(Competitor competitor);

	public List<StageScoreSheet> findAll();

	public int getCount();

	public void removeInBatch(List<Long> idList);

	public StageScoreSheet save(StageScoreSheet sheet);

	public int getCompetitorStageScoreSheetCount(Competitor competitor);

}
