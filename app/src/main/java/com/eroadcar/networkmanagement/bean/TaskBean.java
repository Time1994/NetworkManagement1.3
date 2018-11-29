package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 注册bean
 *
 * @author amos
 */
public class TaskBean implements Serializable {
    private String wj_id;
    private String wj_shopcode;
    private String wj_job_name;
    private String wj_job_context;
    private String wj_employer;
    private String wj_employee_ids;
    private String wj_employee_names;
    private String wj_job_state;
    private String wj_remark;
    private String wj_create_time;
    private String wj_distri_time;
    private String wj_finish_time;
    private ArrayList<Worker> workers;

    private String jw_id;
    private String jw_shopcode;
    private String jw_employee_id;
    private String jw_employer_id;
    private String jw_employee_name;
    private String jw_employer_name;
    private String jw_job_id;
    private String jw_job_name;
    private String jw_distri_time;
    private String jw_accepte_time;
    private String jw_job_state;
    private String jw_job_remark;
    private String jw_finish_time;
    private String jw_create_time;
    private String jw_job_context;

    public String getJw_job_context() {
        return jw_job_context;
    }

    public void setJw_job_context(String jw_job_context) {
        this.jw_job_context = jw_job_context;
    }


    public String getJw_id() {
        return jw_id;
    }

    public void setJw_id(String jw_id) {
        this.jw_id = jw_id;
    }

    public String getJw_shopcode() {
        return jw_shopcode;
    }

    public void setJw_shopcode(String jw_shopcode) {
        this.jw_shopcode = jw_shopcode;
    }

    public String getJw_employee_id() {
        return jw_employee_id;
    }

    public void setJw_employee_id(String jw_employee_id) {
        this.jw_employee_id = jw_employee_id;
    }

    public String getJw_employer_id() {
        return jw_employer_id;
    }

    public void setJw_employer_id(String jw_employer_id) {
        this.jw_employer_id = jw_employer_id;
    }

    public String getJw_employee_name() {
        return jw_employee_name;
    }

    public void setJw_employee_name(String jw_employee_name) {
        this.jw_employee_name = jw_employee_name;
    }

    public String getJw_employer_name() {
        return jw_employer_name;
    }

    public void setJw_employer_name(String jw_employer_name) {
        this.jw_employer_name = jw_employer_name;
    }

    public String getJw_job_id() {
        return jw_job_id;
    }

    public void setJw_job_id(String jw_job_id) {
        this.jw_job_id = jw_job_id;
    }

    public String getJw_job_name() {
        return jw_job_name;
    }

    public void setJw_job_name(String jw_job_name) {
        this.jw_job_name = jw_job_name;
    }

    public String getJw_distri_time() {
        return jw_distri_time;
    }

    public void setJw_distri_time(String jw_distri_time) {
        this.jw_distri_time = jw_distri_time;
    }

    public String getJw_accepte_time() {
        return jw_accepte_time;
    }

    public void setJw_accepte_time(String jw_accepte_time) {
        this.jw_accepte_time = jw_accepte_time;
    }

    public String getJw_job_state() {
        return jw_job_state;
    }

    public void setJw_job_state(String jw_job_state) {
        this.jw_job_state = jw_job_state;
    }

    public String getJw_job_remark() {
        return jw_job_remark;
    }

    public void setJw_job_remark(String jw_job_remark) {
        this.jw_job_remark = jw_job_remark;
    }

    public String getJw_finish_time() {
        return jw_finish_time;
    }

    public void setJw_finish_time(String jw_finish_time) {
        this.jw_finish_time = jw_finish_time;
    }

    public String getJw_create_time() {
        return jw_create_time;
    }

    public void setJw_create_time(String jw_create_time) {
        this.jw_create_time = jw_create_time;
    }


    public String getWj_id() {
        return wj_id;
    }

    public void setWj_id(String wj_id) {
        this.wj_id = wj_id;
    }

    public String getWj_shopcode() {
        return wj_shopcode;
    }

    public void setWj_shopcode(String wj_shopcode) {
        this.wj_shopcode = wj_shopcode;
    }

    public String getWj_job_name() {
        return wj_job_name;
    }

    public void setWj_job_name(String wj_job_name) {
        this.wj_job_name = wj_job_name;
    }

    public String getWj_job_context() {
        return wj_job_context;
    }

    public void setWj_job_context(String wj_job_context) {
        this.wj_job_context = wj_job_context;
    }

    public String getWj_employer() {
        return wj_employer;
    }

    public void setWj_employer(String wj_employer) {
        this.wj_employer = wj_employer;
    }

    public String getWj_employee_ids() {
        return wj_employee_ids;
    }

    public void setWj_employee_ids(String wj_employee_ids) {
        this.wj_employee_ids = wj_employee_ids;
    }

    public String getWj_employee_names() {
        return wj_employee_names;
    }

    public void setWj_employee_names(String wj_employee_names) {
        this.wj_employee_names = wj_employee_names;
    }

    public String getWj_job_state() {
        return wj_job_state;
    }

    public void setWj_job_state(String wj_job_state) {
        this.wj_job_state = wj_job_state;
    }

    public String getWj_remark() {
        return wj_remark;
    }

    public void setWj_remark(String wj_remark) {
        this.wj_remark = wj_remark;
    }

    public String getWj_create_time() {
        return wj_create_time;
    }

    public void setWj_create_time(String wj_create_time) {
        this.wj_create_time = wj_create_time;
    }

    public String getWj_distri_time() {
        return wj_distri_time;
    }

    public void setWj_distri_time(String wj_distri_time) {
        this.wj_distri_time = wj_distri_time;
    }

    public String getWj_finish_time() {
        return wj_finish_time;
    }

    public void setWj_finish_time(String wj_finish_time) {
        this.wj_finish_time = wj_finish_time;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Worker> workers) {
        this.workers = workers;
    }

    public class Worker implements Serializable {
        public String jw_id;
        public String jw_shopcode;
        public String jw_employee_id;
        public String jw_employor_id;
        public String jw_employee_name;
        public String jw_employor_name;
        public String jw_job_id;
        public String jw_job_name;
        public String jw_distri_time;
        public String jw_accepte_time;
        public String jw_job_state;
        public String jw_job_remark;
        public String jw_finish_time;

        public String getJw_id() {
            return jw_id;
        }

        public void setJw_id(String jw_id) {
            this.jw_id = jw_id;
        }

        public String getJw_shopcode() {
            return jw_shopcode;
        }

        public void setJw_shopcode(String jw_shopcode) {
            this.jw_shopcode = jw_shopcode;
        }

        public String getJw_employee_id() {
            return jw_employee_id;
        }

        public void setJw_employee_id(String jw_employee_id) {
            this.jw_employee_id = jw_employee_id;
        }

        public String getJw_employor_id() {
            return jw_employor_id;
        }

        public void setJw_employor_id(String jw_employor_id) {
            this.jw_employor_id = jw_employor_id;
        }

        public String getJw_employee_name() {
            return jw_employee_name;
        }

        public void setJw_employee_name(String jw_employee_name) {
            this.jw_employee_name = jw_employee_name;
        }

        public String getJw_employor_name() {
            return jw_employor_name;
        }

        public void setJw_employor_name(String jw_employor_name) {
            this.jw_employor_name = jw_employor_name;
        }

        public String getJw_job_id() {
            return jw_job_id;
        }

        public void setJw_job_id(String jw_job_id) {
            this.jw_job_id = jw_job_id;
        }

        public String getJw_job_name() {
            return jw_job_name;
        }

        public void setJw_job_name(String jw_job_name) {
            this.jw_job_name = jw_job_name;
        }

        public String getJw_distri_time() {
            return jw_distri_time;
        }

        public void setJw_distri_time(String jw_distri_time) {
            this.jw_distri_time = jw_distri_time;
        }

        public String getJw_accepte_time() {
            return jw_accepte_time;
        }

        public void setJw_accepte_time(String jw_accepte_time) {
            this.jw_accepte_time = jw_accepte_time;
        }

        public String getJw_job_state() {
            return jw_job_state;
        }

        public void setJw_job_state(String jw_job_state) {
            this.jw_job_state = jw_job_state;
        }

        public String getJw_job_remark() {
            return jw_job_remark;
        }

        public void setJw_job_remark(String jw_job_remark) {
            this.jw_job_remark = jw_job_remark;
        }

        public String getJw_finish_time() {
            return jw_finish_time;
        }

        public void setJw_finish_time(String jw_finish_time) {
            this.jw_finish_time = jw_finish_time;
        }

    }


}
