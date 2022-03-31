/**
 * Created by IntelliJ IDEA.
 * User: Kyrie
 * DateTime: 2018/7/23 16:59
 **/
package com.wip.service.log.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wip.dao.LogDao;
import com.wip.model.LogDomain;
import com.wip.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志相关Service接口实现
 */
@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;

	@Override
	public void addLog(String action, String data, String ip, Integer authorId) {
		LogDomain logDomain = new LogDomain();
		logDomain.setAuthorId(authorId);
		logDomain.setIp(ip);
		logDomain.setData(data);
		logDomain.setAction(action);
		logDao.addLog(logDomain);
	}

	@Override
	public PageInfo<LogDomain> getLogs(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<LogDomain> logs = logDao.getLogs();
		// 虽然这里得到的logs是所有的logs，但是经过上下两句，会将特定范围的数据裁剪出来
		// 如果分页获取，注意不要从最新开始分页获取，这样假如这个过程中有人新插入一条数据，然后分页得到的数据就可能重复了。比如小白助理的首页，抖音的首页等
		PageInfo<LogDomain> pageInfo = new PageInfo<>(logs);
		return pageInfo;

	}
}
