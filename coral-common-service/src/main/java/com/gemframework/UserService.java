package com.gemframework;

import com.gemframework.admin.modules.system.po.User;

import java.util.Set;

public interface UserService {

    Set getRoles(String username);

    Set getPermissions(String username);

    User getByUsername(String username);
}
