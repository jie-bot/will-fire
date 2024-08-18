package jie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jie.dao.FamilyMapper;
import jie.entity.Family;
import jie.service.FamilyService;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午10:42
 * @注释 家庭信息服务类实现
 */
@Service
public class FamilyServiceImpl extends ServiceImpl<FamilyMapper, Family> implements FamilyService {

}
