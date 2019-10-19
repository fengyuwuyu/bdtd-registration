package com.bdtd.card.registration.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bdtd.card.registration.cache.DepCache;
import com.stylefeng.guns.core.util.ThreadPool;
import com.stylefeng.guns.scmmain.dao.IDtDepDao;

@Service("refreshDtDep")
public class RefreshDtDepTask implements ITask {

	@Autowired
	private IDtDepDao depMapper;
	private Logger log = LoggerFactory.getLogger(getClass());
	@Value("${bdtd.refreshDtDepTime}")
	private Long refreshDtDepTime;

	@Override
	public void run() {
		long begin = System.currentTimeMillis();
		DepCache.refresh(depMapper);
		log.debug(String.format("refresh DtDep success, cost = %d!", (System.currentTimeMillis() - begin)));
		ThreadPool.execute(this, refreshDtDepTime, null);
	}

	@Override
	public void start() {
		this.run();
	}

}
