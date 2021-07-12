package com.sandyzfeaklab.breakdown_app.dataModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Model {

    String equipment_name ,
    work_Type ,
            operation,
            part,
            problem_desc
            ,action_taken
            ,spares_used
            , start_Time
            ,end_time
            , action_taken_by
            ,status
        ,stoppage_category
        ,problem_category
            ,Date
        ,area
            ,id
            ,pending_remarks
            ,shift
           ;
    int time_taken;

    java.util.Date timestamp;

    public java.util.Date getTimestamp() {
        return timestamp;
    }

    ArrayList<Sap_code_Model> sap_no;

    public Model() {
    }

    public String getId() {
        return id;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Model(String id) {
        this.id = id;
    }

    public String getStoppage_category() {
        return stoppage_category;
    }

    public String getShift() {
        return shift;
    }

    public String getProblem_category() {
        return problem_category;
    }

    public String getArea() {
        return area;
    }

    public Model(String area, String stoppage_category, String problem_category, String equipment_name, String work_Type, String operation, String part, String problem_desc, String action_taken, String spares_used, ArrayList<Sap_code_Model> sap_no, String start_Time, String end_time,
                 String action_taken_by, String status1, String Date, int time, String id, String pending_remarks,String shift,java.util.Date ts) {
        this.equipment_name = equipment_name;
        this.shift=shift;
        this.timestamp=ts;
        this.work_Type = work_Type;
        this.operation = operation;
        this.Date=Date;
        this.part = part;
        this.area=area;
        this.status=status1;
        this.problem_desc = problem_desc;
        this.action_taken = action_taken;
        this.spares_used = spares_used;
        this.sap_no = sap_no;
        this.start_Time = start_Time;
        this.end_time = end_time;
        this.action_taken_by = action_taken_by;
        this.time_taken=time;
        this.pending_remarks=pending_remarks;
        this.stoppage_category=stoppage_category;
        this.problem_category=problem_category;
        this.id=id;
    }

    public String getPending_remarks() {
        return pending_remarks;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public String getStatus() {
        return status;
    }

    public int getTime_taken() {
        return time_taken;
    }

    public void setTime_taken(int time_taken) {
        this.time_taken = time_taken;
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

    public String getaction_taken_by() {
        return action_taken_by;
    }
}
