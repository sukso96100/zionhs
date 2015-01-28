package com.licubeclub.zionhs.data;

import com.orm.SugarRecord;

/**
 * Created by youngbin on 15. 1. 28.
 */
public class ScheduleCache extends SugarRecord<ScheduleCache> {
    String date;
    String content;

    public ScheduleCache(){

    }

    public ScheduleCache(String Date, String Content){
        this.date = Date;
        this.content = Content;
    }
}
