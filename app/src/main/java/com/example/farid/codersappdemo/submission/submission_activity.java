package com.example.farid.codersappdemo.submission;


public class submission_activity {
    String handle,solution_id, solution_time,  problem_name, solution_status,  problem_link, solution_link, judge, soulution_language, solution_execution_time, usage_memory, problem_difficulty;

    public submission_activity() {}

    public submission_activity(String handle, String solution_id, String solution_time, String problem_name, String solution_status, String problem_link, String soltuon_link, String judge, String soulution_language, String solution_execution_time, String usage_memory, String problem_difficulty) {
        this.handle = handle;
        this.solution_id = solution_id;
        this.solution_time = solution_time;
        this.problem_name = problem_name;
        this.solution_status = solution_status;
        this.problem_link = problem_link;
        this.solution_link = soltuon_link;
        this.judge = judge;
        this.soulution_language = soulution_language;
        this.solution_execution_time = solution_execution_time;
        this.usage_memory = usage_memory;
        this.problem_difficulty = problem_difficulty;
    }

    public String getHandle() {
        return handle;
    }

    public String getSolution_link() {
        return solution_link;
    }

    public String getProblem_difficulty() {
        return problem_difficulty;
    }

    public void setProblem_difficulty(String problem_difficulty) {
        this.problem_difficulty = problem_difficulty;
    }

    public String getSolution_id() {
        return solution_id;
    }

    public String getSolution_time() {
        return solution_time;
    }

    public String getProblem_name() {
        return problem_name;
    }

    public String getSolution_status() {
        return solution_status;
    }

    public String getProblem_link() {
        return problem_link;
    }

    public String getSoltuon_link() {
        return solution_link;
    }

    public String getJudge() {
        return judge;
    }

    public void setSolution_id(String solution_id) {
        this.solution_id = solution_id;
    }

    public void setSolution_time(String solution_time) {
        this.solution_time = solution_time;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name;
    }

    public void setSolution_status(String solution_status) {
        this.solution_status = solution_status;
    }

    public void setProblem_link(String problem_link) {
        this.problem_link = problem_link;
    }

    public void setSoltuon_link(String soltuon_link) {
        this.solution_link = soltuon_link;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public void setSoulution_language(String soulution_language) {
        this.soulution_language = soulution_language;
    }

    public void setSolution_execution_time(String solution_execution_time) {
        this.solution_execution_time = solution_execution_time;
    }

    public void setUsage_memory(String usage_memory) {
        this.usage_memory = usage_memory;
    }

    public String getSoulution_language() {
        return soulution_language;
    }

    public String getSolution_execution_time() {
        return solution_execution_time;
    }

    public String getUsage_memory() {
        return usage_memory;
    }
}
