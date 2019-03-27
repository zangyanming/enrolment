package com.cd.ums.modules.email.api;

import com.cd.ums.common.persistence.JsonData;
import com.cd.ums.modules.email.model.MailConfig;
import com.cd.ums.modules.email.model.MailContent;

import java.util.Properties;

/**
 * @author hqj
 */
public interface IMailService {
    Properties mailInit(MailConfig config);

    JsonData sendMail(MailContent content);
}