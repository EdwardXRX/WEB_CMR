package com.edwardxrx.crm.workbench.dao;

import com.edwardxrx.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue clue);

    Clue detail(String id);

    int unbund(String id);

    int bundActivity(List<Map<String, String>> list);

    Clue getById(String clueId);

    int delete(String clueId);
}
