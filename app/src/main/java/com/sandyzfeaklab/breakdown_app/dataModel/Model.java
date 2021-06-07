package com.sandyzfeaklab.breakdown_app.dataModel;

import java.util.ArrayList;

public class Model {

    String equipment_name ="Equipment Name",
    work_Type ="Work Type",
            operation="Operation",
            part= "Part Name",
            problem_desc="Problem"
            ,action_taken="Action Taken"
            ,spares_used="Spares Used"
            , start_Time =" Start Time"
            ,end_time="End Time"
            , action_taken_by ="Action taken by "
            ,status=""
            ,Date=""
           ;
    ArrayList<Sap_code_Model> sap_no;

    public Model() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Model(String equipment_name, String work_Type, String operation, String part, String problem_desc, String action_taken, String spares_used, ArrayList<Sap_code_Model> sap_no, String start_Time, String end_time,
                 String action_taken_by, String status1, String Date) {
        this.equipment_name = equipment_name;
        this.work_Type = work_Type;
        this.operation = operation;
        this.Date=Date;
        this.part = part;
        this.status=status1;
        this.problem_desc = problem_desc;
        this.action_taken = action_taken;
        this.spares_used = spares_used;
        this.sap_no = sap_no;
        this.start_Time = start_Time;
        this.end_time = end_time;
        this.action_taken_by = action_taken_by;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public String getStatus() {
        return status;
    }


    public String getWork_Type() {
        return work_Type;
    }

    public String getOperation() {
        return operation;
    }

    public String getPart() {
        return part;
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

    public ArrayList<Sap_code_Model> getSap_no() {
        return sap_no;
    }

    public String getStart_Time() {
        return start_Time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getAction_taken_by() {
        return action_taken_by;
    }
}
