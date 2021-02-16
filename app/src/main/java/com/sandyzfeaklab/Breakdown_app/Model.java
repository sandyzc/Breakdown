package com.sandyzfeaklab.Breakdown_app;

public class Model {

    String  Equipment_name="Equipment Name",
            Work_Type="Work Type",
            operation="Operation",
            Part= "Part Name",
            problem_desc="Problem"
            ,action_taken="Action Taken"
            ,spares_used="Spares Used"
            ,sap_no="Sap No"
            ,Start_Time=" Start Time"
            ,end_time="End Time"
            ,Action_taken_by="Action taken by "
           ;

    public Model() {
    }

    public Model(String equipment_name, String work_Type, String operation, String part, String problem_desc, String action_taken, String spares_used, String sap_no, String start_Time, String end_time, String action_taken_by) {
        Equipment_name = equipment_name;
        Work_Type = work_Type;
        this.operation = operation;
        Part = part;
        this.problem_desc = problem_desc;
        this.action_taken = action_taken;
        this.spares_used = spares_used;
        this.sap_no = sap_no;
        Start_Time = start_Time;
        this.end_time = end_time;
        Action_taken_by = action_taken_by;
    }

    public String getEquipment_name() {
        return Equipment_name;
    }

    public String getWork_Type() {
        return Work_Type;
    }

    public String getOperation() {
        return operation;
    }

    public String getPart() {
        return Part;
    }

    public String getProblem_desc() {
        return problem_desc;
    }

    public String getAction_taken() {
        return action_taken;
    }

    public String getSpares_used() {
        return spares_used;
    }

    public String getSap_no() {
        return sap_no;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getAction_taken_by() {
        return Action_taken_by;
    }
}
