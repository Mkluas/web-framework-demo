package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.service.ApiService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author klaus
 * @date 2018/9/4 上午11:43
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Override
    public List<String> listApi() {
        return Arrays.asList("/api/qiniu/token");
    }

}
