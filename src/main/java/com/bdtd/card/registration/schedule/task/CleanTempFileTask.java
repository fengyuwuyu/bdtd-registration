package com.bdtd.card.registration.schedule.task;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.core.util.ThreadPool;

@Service("cleanTempFile")
public class CleanTempFileTask implements ITask {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void run() {
		long begin = System.currentTimeMillis();
		FileUtil.deleteDir(new File(Consts.UPDATE_FILE), (file) -> { 
		    if (file.getPath().endsWith(Consts.EXCEL_FILE_SUFFIX)) {
		        return true;
		    }
		    return false;
		    }, true);
		log.debug(String.format("clean TempFile success, cost = %d!", (System.currentTimeMillis() - begin)));
		ThreadPool.execute(this, DateUtil.getCurrTime2OneDayEnd(), DateUtil.ONE_DAY_TIME, null);
	}
	
	@Override
	public void start() {
		this.run();
	}

}
