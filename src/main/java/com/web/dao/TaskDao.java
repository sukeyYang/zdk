package com.web.dao;


import com.common.BaseDao;
import com.common.SearchTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyang on 16/2/29.
 */
@Repository
public class TaskDao extends BaseDao{

    /**
     * 查询
     * @param map
     * @return
     */
    public SearchTemplate searchTask(Map map){
        StringBuffer sql =new StringBuffer();
        sql.append("SELECT t.id,t.name,t.status,t.description,DATE_FORMAT(t.begintime,'%Y-%m-%d %H:%i') AS begintime,DATE_FORMAT(t.endtime,'%Y-%m-%d %H:%i') AS endtime, ");
        sql.append("t.keyword,t.unitprice,t.total,t.type,t.logoimg,t.imgfile ");
        sql.append("FROM task t ");
        sql.append("where 1=1 ");
        Map p=new HashMap();
        if (map.containsKey("name")){
            sql.append(" and t.name like :queryname");
            p.put("queryname", "%" + map.get("queryname") + "%");
        }
        sql.append("order by create_time desc ");
        return super.search(sql.toString(),p);
    }

    public void removeTaskClickByTaskId(int taskid){
        StringBuffer sql=new StringBuffer("delete from Task_check where taskid=:taskid ");
        Map p=new HashMap();
        p.put("taskid",taskid);
        super.executeSql(sql.toString(),p);
    }

    public List<Map> searchTaskClickByTaskId(int taskid){
        StringBuffer sql=new StringBuffer("select * from Task_check where taskid=:taskid ");
        Map p=new HashMap();
        p.put("taskid",taskid);
        return super.findResult(sql.toString(),p);
    }

    public void removeTask(List<Integer> ids) {
        StringBuffer sql = new StringBuffer("delete from Task where id in(:ids)");
        super.removeByIds(sql.toString(), ids);
    }


    public List<Map> searchTaskByUser(int userid, int tasktype){
        StringBuffer sql=new StringBuffer("SELECT t.id,t.name,t.unitprice,t.keyword,t.total,t.logoimg,t.description,t.content,a.status,t.imgfile,a.id as utid FROM task t ");
        sql.append(" LEFT JOIN user_task a ON t.id=a.taskid AND a.userid=:userid ");
        sql.append(" where 1=1 and t.type=:type ");
        Map p=new HashMap();
        p.put("userid",userid);
        p.put("type",tasktype);
        sql.append(" order by create_time DESC ");
        return super.findResult(sql.toString(),p);
    }

}
