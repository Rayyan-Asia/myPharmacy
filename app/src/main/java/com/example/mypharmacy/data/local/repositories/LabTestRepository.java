package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.LabTest;

import java.util.List;

public interface LabTestRepository {
    public LabTest findLabTest(int id);
    public List<LabTest> listLabTests();
    public void insertLabTest(LabTest labTest);
}
