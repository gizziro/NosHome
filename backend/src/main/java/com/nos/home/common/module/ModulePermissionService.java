package com.nos.home.common.module;

import com.nos.home.common.module.repository.ModulePermissionRepository;
import com.nos.home.common.module.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModulePermissionService
{
    private final ModulePermissionRepository    modulePermissionRepository;
    private final ModuleRepository              moduleRepository;

}
