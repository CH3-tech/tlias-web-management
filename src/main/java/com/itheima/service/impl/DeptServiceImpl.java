package com.itheima.service.impl;

import com.itheima.mapper.DeptMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }


    @Transactional
    @Override
    public void delete(Integer id) {
        deptMapper.deleteById(id);//根据id删除部门数据

        empMapper.deleteByDeptId(id);//根据部门id删除员工数据
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }

    @Override
    public Dept search(Integer id) {

       Dept dept=deptMapper.search(id);
        return dept;
    }

    @Override
    public void modify(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.modify(dept);
    }
}
