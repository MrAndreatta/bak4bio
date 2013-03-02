package br.ufpr.bioinfo.bak4bio.android.model;

import java.io.Serializable;
import java.util.Date;

public class Blast implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private User owner;
	private String title;
	private String status;
	private Content entry;
	private Content output;
	private Date startAt;
	private Date endAt;
	
	private String dataBase;
	private String program;
	
	private String expect;
	private String word_size;
	private String gapCosts;
	private String mScores;
	private String maxMatchesRange;
	private String maxTargetSequence;
	
	public Blast() {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Content getEntry() {
		return entry;
	}

	public void setEntry(Content entry) {
		this.entry = entry;
	}

	public Content getOutput() {
		return output;
	}

	public void setOutput(Content output) {
		this.output = output;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getExpect() {
		return expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}

	public String getWord_size() {
		return word_size;
	}

	public void setWord_size(String word_size) {
		this.word_size = word_size;
	}

	public String getGapCosts() {
		return gapCosts;
	}

	public void setGapCosts(String gapCosts) {
		this.gapCosts = gapCosts;
	}

	public String getmScores() {
		return mScores;
	}

	public void setmScores(String mScores) {
		this.mScores = mScores;
	}

	public String getMaxMatchesRange() {
		return maxMatchesRange;
	}

	public void setMaxMatchesRange(String maxMatchesRange) {
		this.maxMatchesRange = maxMatchesRange;
	}

	public String getMaxTargetSequence() {
		return maxTargetSequence;
	}

	public void setMaxTargetSequence(String maxTargetSequence) {
		this.maxTargetSequence = maxTargetSequence;
	}

	@Override
	public String toString() {
		return this.title;
	}
}
