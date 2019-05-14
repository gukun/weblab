/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.iim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njrz.common.persistence.Page;
import com.njrz.common.service.CrudService;
import com.njrz.modules.iim.dao.MailBoxDao;
import com.njrz.modules.iim.dao.MailComposeDao;
import com.njrz.modules.iim.entity.MailBox;
import com.njrz.modules.iim.entity.MailCompose;
import com.njrz.modules.iim.entity.MailPage;

/**
 * 发件箱Service
 * @author
 * @version 2016-03-28
 */
@Service
@Transactional(readOnly = true)
public class MailComposeService extends CrudService<MailComposeDao, MailCompose> {
	@Autowired
	private MailComposeDao mailComposeDao;
	public MailCompose get(String id) {
		return super.get(id);
	}
	
	public List<MailCompose> findList(MailCompose mailCompose) {
		return super.findList(mailCompose);
	}
	
	public Page<MailCompose> findPage(MailPage<MailCompose> page, MailCompose mailCompose) {
		return super.findPage(page, mailCompose);
	}
	
	@Transactional(readOnly = false)
	public void save(MailCompose mailCompose) {
		super.save(mailCompose);
	}
	
	@Transactional(readOnly = false)
	public void delete(MailCompose mailCompose) {
		super.delete(mailCompose);
	}

	public int getCount(MailCompose mailCompose) {
		return mailComposeDao.getCount(mailCompose);
	}
	
}