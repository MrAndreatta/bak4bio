package br.ufpr.bioinfo.bak4bio.android.model;

public class Content {
	private Integer id;
	private String sourceFileName;
	private String sourceContentType;
	private int sourceFileSize;
	private String description;
	
	public Content() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
	public String getSourceContentType() {
		return sourceContentType;
	}
	public void setSourceContentType(String sourceContentType) {
		this.sourceContentType = sourceContentType;
	}
	public int getSourceFileSize() {
		return sourceFileSize;
	}
	public void setSourceFileSize(int sourceFileSize) {
		this.sourceFileSize = sourceFileSize;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.sourceFileName +  " - " + this.description;
	}
	
}
