package com.itmy.order.service.dubbo.sentinel;

import com.itmy.entity.User;
import com.itmy.entity.base.Response;
import com.itmy.user.service.IUserExternalService;
import org.springframework.stereotype.Service;

import static com.itmy.enums.FallbackErrorEnum.*;
/**
 * @Author: niusaibo
 * @date: 2023-10-23 14:08
 */
@Service
public class SentinelUserServiceImpl implements IUserExternalService {

    @Override
    public Response selectUserAll() {
        return Response.fail(USER_MODULE_FALL);
    }

    @Override
    public Response insert(User user) {
        return null;
    }

}
