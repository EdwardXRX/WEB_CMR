package com.edwardxrx.crm.workbench.dao;

import com.edwardxrx.crm.workbench.domain.Clue;

public interface ClueDao {


    int save(Clue clue);

    Clue detail(String id);
}
