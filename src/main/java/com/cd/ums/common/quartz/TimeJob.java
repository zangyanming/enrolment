package com.cd.ums.common.quartz;

import com.cd.ums.modules.mq.entity.UmsSystem;
import com.cd.ums.modules.mq.service.UmsSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by pan on 2017/6/23.
 */
@Service
public class TimeJob {

    @Autowired
    private UmsSystemService umsSystemService;

    // 每天凌晨0时0分0秒执行
    public void doValidateUmsSystem() {
        // 验证通过申请的应用系统使用有效时间是否过期
        UmsSystem umsSystem = new UmsSystem();
        umsSystem.setAuditStatus("1");
        List<UmsSystem> list = umsSystemService.findList(umsSystem);
        if (list != null && list.size() > 0) {
            Date now = new Date();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null && list.get(i).getValidDate() != null)
                    if (now.getTime() >= list.get(i).getValidDate().getTime()) {
                        list.get(i).setAuditStatus("9");
                        umsSystemService.save(list.get(i));
                    }
            }
        }
    }
}
