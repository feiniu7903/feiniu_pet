package com.lvmama.distribution.model.qunar;

import java.util.List;

public class Package_ {
	private String id;
	private String status;
	private List<Team> teams;
	
	@Override
	public String toString() {
		StringBuilder packages = new StringBuilder();
		packages.append("<packages>");
		packages.append("	<package id=\"\" status=\"\">");
		packages.append("		<teams>");
		for (Team team : teams) {
			packages.append(team.toString());
		}
		packages.append("		</teams>");
		packages.append("	</package>");
		packages.append("</packages>");
		return packages.toString();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	
}
